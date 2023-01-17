package com.shoppingcenter.core.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.shoppingcenter.core.UploadFile;

@Service("local")
public class LocalStorageService implements FileStorageService {

    @Override
    public List<String> write(List<UploadFile> files, String dir, String name) throws IOException {
        List<String> fileNames = new ArrayList<>();
        for (UploadFile file : files) {
            if (file == null) {
                continue;
            }

            fileNames.add(write(file, dir, name));
        }

        return fileNames;
    }

    @Override
    public String write(UploadFile file, String dir, String name) throws IOException {
        long millis = System.currentTimeMillis();
        String fullFileName = String.format("%s-%d.%s", name, millis, file.getExtension());

        File destFile = new File(dir, fullFileName);

        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }

        destFile.createNewFile();

        try (InputStream is = file.getInputStream(); OutputStream os = new FileOutputStream(destFile);) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }

        return fullFileName;
    }

    @Override
    public void delete(String dir, List<String> fileNames) throws IOException {
        if (fileNames == null) {
            return;
        }

        for (String fileName : fileNames) {
            delete(dir, fileName);
        }
    }

    @Override
    public void delete(String dir, String fileName) throws IOException {
        if (!StringUtils.hasText(fileName)) {
            return;
        }

        File sourceFile = new File(dir, fileName);

        Files.deleteIfExists(sourceFile.toPath());
    }

}
