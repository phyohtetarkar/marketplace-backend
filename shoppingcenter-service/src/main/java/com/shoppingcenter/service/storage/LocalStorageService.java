package com.shoppingcenter.service.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.shoppingcenter.service.FileIOException;
import com.shoppingcenter.service.UploadFile;

public class LocalStorageService implements FileStorageService {

    private static final Logger log = LoggerFactory.getLogger(LocalStorageService.class);

    @Override
    public List<String> write(Set<Entry<String, UploadFile>> files, String dir) {
        List<String> fileNames = new ArrayList<>();
        for (Entry<String, UploadFile> en : files) {
            if (en.getValue() == null) {
                continue;
            }

            fileNames.add(write(en.getValue(), dir, en.getKey()));
        }

        return fileNames;
    }

    @Override
    public String write(UploadFile file, String dir, String name) {
        try {
            File destFile = new File(dir, name);

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

            return name;
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileIOException(e);
        }
    }

    @Override
    public void delete(String dir, List<String> fileNames) {
        try {
            if (fileNames == null) {
                return;
            }

            int deleted = 0;

            for (String fileName : fileNames) {
                if (!StringUtils.hasText(fileName)) {
                    continue;
                }

                File sourceFile = new File(dir, fileName);

                boolean result = Files.deleteIfExists(sourceFile.toPath());

                if (!result) {
                    continue;
                }

                deleted += 1;
            }

        } catch (Exception e) {
            log.error("Error deleting files: {}", e.getMessage());
        }
    }

    @Override
    public void delete(String dir, String fileName) {
        try {

            File sourceFile = new File(dir, fileName);

            boolean result = Files.deleteIfExists(sourceFile.toPath());
        } catch (Exception e) {
            log.error("Error deleting file: {}", e.getMessage());
        }

    }

}
