package com.api.base.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import com.api.base.exception.BusinessException;
import com.api.base.exception.ErrorDetail;
import com.api.base.utils.enumerate.ErrorCommon;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
    private FileUtils() {
    }

    private static final int BUFFER_SIZE = 4096;

    public static String uploadFiles(String bucket, List<MultipartFile> files) {
        try {
            deleteFileOrFolder(new File(bucket));
            for (MultipartFile file : files) {
                String fileName = bucket + Constants.SLASH + file.getOriginalFilename();
                Files.deleteIfExists(Paths.get(fileName));
                Path root = Paths.get(bucket);
                Files.createDirectories(root);
                Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
            }
            return MessageFormat.format(Constants.HAVE_FILE, files.size());
        } catch (Exception e) {
            throw new BusinessException(new ErrorDetail(ErrorCommon.ERR_FILE_UPLOAD.name(), ErrorCommon.ERR_FILE_UPLOAD.getValue(), bucket));
        }
    }

    private static void deleteFileOrFolder(File file) {
        try {
            if (file.exists()) {
                if (file.listFiles() != null) {
                    for (File f : file.listFiles()) {
                        Files.delete(Paths.get(f.getPath()));
                    }
                }
                Files.delete(Paths.get(file.getPath()));
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCommon.ERR_FILE_DELETE.name(), ErrorCommon.ERR_FILE_DELETE.getValue(), file.getPath());
        }
    }

    public static void deleteFileIfExist(String filePath) {
        deleteFileOrFolder(new File(filePath));
    }

    public static Resource getFileAsResource(String path) throws IOException {
        if (StringUtils.isAllBlank(path)) {
            return null;
        }
        Path filePath = Paths.get(path);
        return new UrlResource(filePath.toUri());
    }

    public static void copyFileToAnOtherBucket(String soureFullPath, String targetPath) {
        try {
            File sourceFiles = new File(soureFullPath);
            File destinationFile = new File(targetPath);
            Path backupFolder = Paths.get(targetPath);
            Files.createDirectories(backupFolder);
            // copy nhieu file
            File[] filesList = sourceFiles.listFiles();
            for (int i = 0; i < filesList.length; i++) {
                FileInputStream fileInputStream = new FileInputStream(filesList[i]);
                FileOutputStream fileOutputStream = new FileOutputStream(destinationFile + Constants.SLASH + filesList[i].getName());
                int bufferSize;
                byte[] bufffer = new byte[512];
                while ((bufferSize = fileInputStream.read(bufffer)) > 0) {
                    fileOutputStream.write(bufffer, 0, bufferSize);
                }
                fileInputStream.close();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCommon.ERR_FILE_COPY.name(), ErrorCommon.ERR_FILE_COPY.getValue(), targetPath);
        }
    }

    public static void zipFile(String filePath, String destZipFile) throws IOException {
        File file = new File(filePath);
        File fileDestZipFile = new File(destZipFile);
        fileDestZipFile.getParentFile().mkdirs();
        fileDestZipFile.createNewFile();
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destZipFile));) {
            zipDirectory(file, file.getName(), zos, false);
            zos.flush();
            zos.close();
        }
    }

    private static void zipDirectory(File folder, String parentFolder, ZipOutputStream zos, boolean includedParentFolder) throws IOException {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                String filename = file.getName();
                if (includedParentFolder) {
                    filename = parentFolder + Constants.SLASH + filename;
                }
                zipDirectory(file, filename, zos, true);
                continue;
            }
            String filename = file.getName();
            if (includedParentFolder) {
                filename = parentFolder + Constants.SLASH + filename;
            }
            zos.putNextEntry(new ZipEntry(filename));
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
                byte[] bytesIn = new byte[BUFFER_SIZE];
                int read = 0;
                while ((read = bis.read(bytesIn)) != -1) {
                    zos.write(bytesIn, 0, read);
                }
                zos.closeEntry();
            }
        }
    }

    public static void exportFile(HttpServletResponse response, File file) throws IOException, FileNotFoundException {
        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(file);
        IOUtils.copy(in, out);
        out.close();
        in.close();
        file.delete();
    }
}
