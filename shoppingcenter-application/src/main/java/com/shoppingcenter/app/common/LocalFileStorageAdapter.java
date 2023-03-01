package com.shoppingcenter.app.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.shoppingcenter.domain.FileIOException;
import com.shoppingcenter.domain.UploadFile;
import com.shoppingcenter.domain.common.FileStorageAdapter;

public class LocalFileStorageAdapter implements FileStorageAdapter {

    private static final Logger log = LoggerFactory.getLogger(LocalFileStorageAdapter.class);

    private String basePath;

    public LocalFileStorageAdapter(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public List<String> write(Set<Entry<String, UploadFile>> files, String dir) {
        var fileNames = new ArrayList<String>();
        for (var en : files) {
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
            var rootDir = basePath + File.separator + dir;
            File destFile = new File(rootDir, name);

            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }

            destFile.createNewFile();

            try (var is = file.getInputStream(); var os = new FileOutputStream(destFile);) {
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
            var rootDir = basePath + File.separator + dir;

            for (String fileName : fileNames) {
                if (!StringUtils.hasText(fileName)) {
                    continue;
                }

                File sourceFile = new File(rootDir, fileName);

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
            var rootDir = basePath + File.separator + dir;
            var sourceFile = new File(rootDir, fileName);

            boolean result = Files.deleteIfExists(sourceFile.toPath());
        } catch (Exception e) {
            log.error("Error deleting file: {}", e.getMessage());
        }

    }

}
