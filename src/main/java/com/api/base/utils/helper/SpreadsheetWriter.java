package com.api.base.utils.helper;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellReference;

public class SpreadsheetWriter {
    private final Writer writer;
    private int rowNum;

    SpreadsheetWriter(Writer out) {
        writer = out;
    }

    void beginSheet(int xSplit, int ySplit, String cellFreeze) throws IOException {
        writer.write("<?xml version=\"1.0\" encoding=\"" + StandardCharsets.UTF_8 + "\"?>" + "<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">");

        writer.write("<sheetViews>");
        writer.write("<sheetView tabSelected=\"1\" workbookViewId=\"0\">");
        writer.write("<pane xSplit=\"" + xSplit + "\" ySplit=\"" + ySplit + "\" topLeftCell=\"" + cellFreeze + "\" activePane=\"bottomRight\" state=\"frozen\"/>");
        writer.write("</sheetView>");
        writer.write("</sheetViews>");

        writer.write("<cols>");
        writer.write("<col min=\"1\" max=\"100\" width=\"20\" bestFit=\"1\" customWidth=\"1\"/>");
        writer.write("</cols>");

        writer.write("<sheetData>\n");
    }

    void endSheet() throws IOException {
        writer.write("</sheetData></worksheet>");
    }

    void endSheet(Map<String, String> formulas) throws IOException {
        writer.write("</sheetData>");

        if (!formulas.isEmpty()) {
            writer.write("<dataValidations>");
            Iterator<Entry<String, String>> it = formulas.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, String> entry = it.next();
                String listData = entry.getValue();
                if (!entry.getValue().contains("Ref")) {
                    listData = "\"" + listData + "\"";
                }
                writer.write("<dataValidation type=\"list\" allowBlank=\"1\" showInputMessage=\"1\" showErrorMessage=\"1\" sqref=\"" + entry.getKey() + "\"><formula1>"
                        + listData + "</formula1></dataValidation>");
            }
            writer.write("</dataValidations>");
        }

        writer.write("</worksheet>");
    }

    void endSheet(List<String> mergeCells) throws IOException {
        writer.write("</sheetData>");

        if (CollectionUtils.isNotEmpty(mergeCells)) {
            writer.write("<mergeCells count=\"" + mergeCells.size() + "\">");
            for (String cellRef : mergeCells) {
                writer.write("<mergeCell ref=\"" + cellRef + "\"/>");
            }
            writer.write("</mergeCells>");
        }

        writer.write("</worksheet>");
    }

    void insertRow(int rownum) throws IOException {
        writer.write("<row r=\"" + (rownum + 1) + "\">\n");
        this.rowNum = rownum;
    }

    void endRow() throws IOException {
        writer.write("</row>\n");
    }

    public void createCell(int columnIndex, String value, int styleIndex) throws IOException {
        String ref = new CellReference(rowNum, columnIndex).formatAsString();
        writer.write("<c r=\"" + ref + "\" t=\"inlineStr\"");
        if (styleIndex != -1) {
            writer.write(" s=\"" + styleIndex + "\"");
        }
        writer.write(">");
        writer.write("<is><t>" + value + "</t></is>");
        writer.write("</c>");
    }

    public void createCell(int columnIndex, String value) throws IOException {
        createCell(columnIndex, value, -1);
    }

    public void createCell(int columnIndex, double value, int styleIndex) throws IOException {
        String ref = new CellReference(rowNum, columnIndex).formatAsString();
        writer.write("<c r=\"" + ref + "\" t=\"n\"");
        if (styleIndex != -1) {
            writer.write(" s=\"" + styleIndex + "\"");
        }
        writer.write(">");
        writer.write("<v>" + value + "</v>");
        writer.write("</c>");
    }

    public void createCell(int columnIndex, double value) throws IOException {
        createCell(columnIndex, value, -1);
    }

    public void createCell(int columnIndex, Calendar value, int styleIndex) throws IOException {
        createCell(columnIndex, DateUtil.getExcelDate(value, false), styleIndex);
    }
}
