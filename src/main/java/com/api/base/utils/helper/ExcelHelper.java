package com.api.base.utils.helper;

import com.api.base.utils.Constants;
import com.api.base.domain.excel.ExportRefData;
import com.api.base.domain.excel.ExportRefValidation;
import com.api.base.utils.Annotations;
import com.api.base.utils.DateTimeUtils;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.opc.internal.ZipHelper;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcelHelper {
    private ExcelHelper() {}

    public static String writeDataToExcel(List<?> lstData, List<String> lsFields, String tmpFolderPrefix, ExportRefData refData) throws Exception {
        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            String tmpFileName = "report" + currentTime + ".xlsx";
            XSSFSheet sheet = wb.createSheet("Data");
            Map<String, XSSFCellStyle> styles = createStyles(wb);
            String sheetRef = sheet.getPackagePart().getPartName().getName();

            XSSFSheet sheet2 = wb.createSheet("Ref");
            String sheet2Ref = sheet2.getPackagePart().getPartName().getName();

            File file = new File(tmpFolderPrefix + Constants.XLSX_BLANK_TEMPLATE);
            file.getParentFile().mkdirs();

            try (FileOutputStream os = new FileOutputStream(tmpFolderPrefix + Constants.XLSX_BLANK_TEMPLATE)) {
                wb.write(os);
            }
            String fileName = tmpFolderPrefix + tmpFileName;
            File tmp = File.createTempFile("sheet", ".xml");
            Map<String, String> formulas = new HashMap<>();
            for (ExportRefValidation ref : refData.getValidations()) {
                formulas.put(ref.getCell(), ref.getRange());
            }
            try (FileOutputStream stream = new FileOutputStream(tmp); Writer fw = new OutputStreamWriter(stream, StandardCharsets.UTF_8)) {
                generateData(fw, styles, lstData, lsFields, formulas);
            }
            File tmpRef = File.createTempFile("sheet", ".xml");
            try (FileOutputStream stream = new FileOutputStream(tmpRef); Writer fw = new OutputStreamWriter(stream, StandardCharsets.UTF_8)) {
                generateData(fw, styles, refData.getObjects(), refData.getFields());
            }
            try (FileOutputStream out = new FileOutputStream(fileName)) {
                mergeTemplate(new File(tmpFolderPrefix + Constants.XLSX_BLANK_TEMPLATE), tmp, tmpRef, sheetRef.substring(1), sheet2Ref.substring(1), out);
            }
            return fileName;
        }
    }

    private static void mergeTemplate(File zipfile, File tmpfile, File tmpRef, String entry, String entryRef, OutputStream out) throws IOException {
        try (ZipSecureFile zip = ZipHelper.openZipFile(zipfile)) {
            try (ZipArchiveOutputStream zos = new ZipArchiveOutputStream(out)) {
                Enumeration<? extends ZipArchiveEntry> en = zip.getEntries();
                while (en.hasMoreElements()) {
                    ZipArchiveEntry ze = en.nextElement();
                    if (!ze.getName().equals(entry) && !ze.getName().equals(entryRef)) {
                        zos.putArchiveEntry(new ZipArchiveEntry(ze.getName()));
                        try (InputStream is = zip.getInputStream(ze)) {
                            copyStream(is, zos);
                        }
                        zos.closeArchiveEntry();
                    }
                }
                zos.putArchiveEntry(new ZipArchiveEntry(entryRef));
                try (InputStream is = new FileInputStream(tmpRef)) {
                    copyStream(is, zos);
                }
                zos.putArchiveEntry(new ZipArchiveEntry(entry));
                try (InputStream is = new FileInputStream(tmpfile)) {
                    copyStream(is, zos);
                }
                zos.closeArchiveEntry();
            }
        }
    }

    public static String writeDataToExcel(List<?> lstData, List<String> lsFields, String tmpFolderPrefix) throws Exception {
        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            String tmpFileName = "report" + currentTime + ".xlsx";
            XSSFSheet sheet = wb.createSheet("Data");
            Map<String, XSSFCellStyle> styles = createStyles(wb);
            String sheetRef = sheet.getPackagePart().getPartName().getName();
            File file = new File(tmpFolderPrefix + Constants.XLSX_BLANK_TEMPLATE);
            file.getParentFile().mkdirs();

            try (FileOutputStream os = new FileOutputStream(tmpFolderPrefix + Constants.XLSX_BLANK_TEMPLATE)) {
                wb.write(os);
            }
            File tmp = File.createTempFile("sheet", ".xml");
            try (FileOutputStream stream = new FileOutputStream(tmp); Writer fw = new OutputStreamWriter(stream, StandardCharsets.UTF_8)) {
                generateData(fw, styles, lstData, lsFields);
            }
            String fileName = tmpFolderPrefix + tmpFileName;
            try (FileOutputStream out = new FileOutputStream(fileName)) {
                mergeTemplate(new File(tmpFolderPrefix + Constants.XLSX_BLANK_TEMPLATE), tmp, sheetRef.substring(1), out);
            }
            return fileName;
        }
    }

    private static void mergeTemplate(File zipfile, File tmpfile, String entry, OutputStream out) throws IOException {
        try (ZipSecureFile zip = ZipHelper.openZipFile(zipfile)) {
            try (ZipArchiveOutputStream zos = new ZipArchiveOutputStream(out)) {
                Enumeration<? extends ZipArchiveEntry> en = zip.getEntries();
                while (en.hasMoreElements()) {
                    ZipArchiveEntry ze = en.nextElement();
                    if (!ze.getName().equals(entry)) {
                        zos.putArchiveEntry(new ZipArchiveEntry(ze.getName()));
                        try (InputStream is = zip.getInputStream(ze)) {
                            copyStream(is, zos);
                        }
                        zos.closeArchiveEntry();
                    }
                }
                zos.putArchiveEntry(new ZipArchiveEntry(entry));
                try (InputStream is = new FileInputStream(tmpfile)) {
                    copyStream(is, zos);
                }
                zos.closeArchiveEntry();
            }
        }
    }

    private static void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] chunk = new byte[1024];
        int count;
        while ((count = in.read(chunk)) >= 0) {
            out.write(chunk, 0, count);
        }
    }

    @SuppressWarnings({ "rawtypes", "deprecation" })
    private static void generateData(Writer out, Map<String, XSSFCellStyle> styles, List<?> objects, List<String> props, Map<String, String> ...formula) throws Exception {
        int rowNum = 0;
        SpreadsheetWriter sw = new SpreadsheetWriter(out);
        sw.beginSheet(0, 1, "A2");
        // write data rows
        if (objects != null && !objects.isEmpty()) {
            Class<?> clazz = objects.get(0).getClass();
            List<Field> fields = new ArrayList<>();
            // insert header
            sw.insertRow(rowNum++);
            int i = 0;
            for (String prop : props) {
                try {
                    Field f = clazz.getDeclaredField(prop);
                    fields.add(f);
                    Annotations.ColumnHeader header = f.getDeclaredAnnotation(Annotations.ColumnHeader.class);
                    if (StringUtils.isNotBlank(header.value())) {
                        sw.createCell(i, header.value(), styles.get("header").getIndex());
                    }
                    i++;
                } catch (Exception e) {}
            }
            sw.endRow();

            // write data
            int colLength = props.size();
            String value = "";
            Type type;
            Calendar cal = Calendar.getInstance();
            Timestamp timestamp;
            Object oValue;
            for (Object o : objects) {
                sw.insertRow(rowNum);
                for (int col = 0; col < colLength; col++) {
                    try {
                        Field fieldAtColumn = fields.get(col);
                        Annotations.ColumnHeader header = fieldAtColumn.getDeclaredAnnotation(Annotations.ColumnHeader.class);
                        String styleCell = header.style();
                        if (StringUtils.isBlank(styleCell)) {
                            styleCell = "default";
                        }
                        int indexStyle = styles.get(styleCell).getIndex();
                        fieldAtColumn.setAccessible(true);
                        type = fieldAtColumn.getGenericType();
                        oValue = fieldAtColumn.get(o);
                        if (type.equals(Timestamp.class) && oValue != null) {
                            timestamp = (Timestamp) oValue;
                            cal.setTimeInMillis(timestamp.getTime());
                            sw.createCell(col, cal, indexStyle);
                        } else if (((Class) type).getSuperclass().equals(Enum.class) && oValue != null) {
                            sw.createCell(col, oValue.toString(), indexStyle);
                        } else if (Instant.class == type && oValue != null) {
                            Instant dateInUTC = (Instant) oValue;
                            sw.createCell(col, DateTimeUtils.convertInstantToString(dateInUTC, Constants.SHORT_DATETIME_SLASH), indexStyle);
                        } else if (((Class) type).getSuperclass().equals(Number.class) && oValue != null) {
                            if (type.equals(Integer.class)) {
                                sw.createCell(col, Integer.parseInt(oValue.toString()), indexStyle);
                            } else {
                                sw.createCell(col, Double.parseDouble(oValue.toString()), indexStyle);
                            }
                        } else {
                            value = oValue != null ? String.valueOf(oValue) : "";
                            sw.createCell(col, StringEscapeUtils.escapeXml10(value), indexStyle);
                        }
                    } catch (Exception e) {
                        // do nothing
                    }
                }
                sw.endRow();
                rowNum++;
            }
        }

        if (formula.length > 0) {
            sw.endSheet(formula[0]);
        } else {
            sw.endSheet();
        }
    }

    private static Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb) {
        Map<String, XSSFCellStyle> styles = new HashMap<>();
        XSSFDataFormat fmt = wb.createDataFormat();

        XSSFCellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HorizontalAlignment.RIGHT);
        style1.setDataFormat(fmt.getFormat("0.0%"));
        style1.setBorderTop(BorderStyle.THIN);
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBorderLeft(BorderStyle.THIN);
        style1.setBorderRight(BorderStyle.THIN);
        styles.put("percent", style1);

        XSSFCellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setDataFormat(fmt.getFormat("0.0X"));
        style2.setBorderTop(BorderStyle.THIN);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        styles.put("coeff", style2);

        XSSFCellStyle style3 = wb.createCellStyle();
        style3.setAlignment(HorizontalAlignment.RIGHT);
        style3.setDataFormat(fmt.getFormat("#,##0.00 �?"));
        style3.setBorderTop(BorderStyle.THIN);
        style3.setBorderBottom(BorderStyle.THIN);
        style3.setBorderLeft(BorderStyle.THIN);
        style3.setBorderRight(BorderStyle.THIN);
        styles.put("currency", style3);

        XSSFCellStyle style4 = wb.createCellStyle();
        style4.setAlignment(HorizontalAlignment.RIGHT);
        style4.setDataFormat(fmt.getFormat(Constants.SHORT_DATETIME_SLASH));
        style4.setBorderTop(BorderStyle.THIN);
        style4.setBorderBottom(BorderStyle.THIN);
        style4.setBorderLeft(BorderStyle.THIN);
        style4.setBorderRight(BorderStyle.THIN);
        styles.put("date", style4);

        XSSFCellStyle style5 = wb.createCellStyle();
        XSSFFont headerFont = wb.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style5.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style5.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style5.setFont(headerFont);
        style5.setAlignment(HorizontalAlignment.CENTER);
        style5.setBorderTop(BorderStyle.THIN);
        style5.setBorderBottom(BorderStyle.THIN);
        style5.setBorderLeft(BorderStyle.THIN);
        style5.setBorderRight(BorderStyle.THIN);
        style5.setVerticalAlignment(VerticalAlignment.CENTER);
        styles.put("header", style5);

        XSSFCellStyle style6 = wb.createCellStyle();
        style6.setBorderTop(BorderStyle.THIN);
        style6.setBorderBottom(BorderStyle.THIN);
        style6.setBorderLeft(BorderStyle.THIN);
        style6.setBorderRight(BorderStyle.THIN);
        styles.put("default", style6);

        XSSFCellStyle style7 = wb.createCellStyle();
        style7.setAlignment(HorizontalAlignment.RIGHT);
        style7.setDataFormat(fmt.getFormat("0.0#"));
        style7.setBorderTop(BorderStyle.THIN);
        style7.setBorderBottom(BorderStyle.THIN);
        style7.setBorderLeft(BorderStyle.THIN);
        style7.setBorderRight(BorderStyle.THIN);
        styles.put("number", style7);

        XSSFCellStyle style8 = wb.createCellStyle();
        style8.setAlignment(HorizontalAlignment.RIGHT);
        style8.setDataFormat(fmt.getFormat("0"));
        style8.setBorderTop(BorderStyle.THIN);
        style8.setBorderBottom(BorderStyle.THIN);
        style8.setBorderLeft(BorderStyle.THIN);
        style8.setBorderRight(BorderStyle.THIN);
        styles.put("integer", style8);

        XSSFCellStyle style9 = wb.createCellStyle();
        style9.setVerticalAlignment(VerticalAlignment.CENTER);
        style9.setBorderTop(BorderStyle.THIN);
        style9.setBorderBottom(BorderStyle.THIN);
        style9.setBorderLeft(BorderStyle.THIN);
        style9.setBorderRight(BorderStyle.THIN);
        styles.put("vertical", style9);

        XSSFCellStyle style10 = wb.createCellStyle();
        XSSFFont titleFont = wb.createFont();
        titleFont.setBold(true);
        titleFont.setColor(IndexedColors.RED.getIndex());
        style10.setFont(titleFont);
        styles.put("title", style10);

        XSSFCellStyle style11 = wb.createCellStyle();
        style11.setAlignment(HorizontalAlignment.LEFT);
        styles.put("condition", style11);

        XSSFCellStyle style12 = wb.createCellStyle();
        style12.setBorderTop(BorderStyle.THIN);
        style12.setBorderBottom(BorderStyle.THIN);
        style12.setBorderLeft(BorderStyle.THIN);
        style12.setBorderRight(BorderStyle.THIN);
        styles.put("text", style12);

        return styles;
    }
}
