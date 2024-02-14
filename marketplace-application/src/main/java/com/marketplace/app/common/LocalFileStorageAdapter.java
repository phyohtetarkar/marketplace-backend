package com.marketplace.app.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.marketplace.domain.UploadFile;
import com.marketplace.domain.common.FileStorageAdapter;

public class LocalFileStorageAdapter implements FileStorageAdapter {

    private static final Logger log = LoggerFactory.getLogger(LocalFileStorageAdapter.class);

    private String basePath;

    public LocalFileStorageAdapter(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public void write(Map<String, UploadFile> files, String dir) {
        for (var en : files.entrySet()) {
            if (en.getValue() == null) {
                continue;
            }

            write(en.getValue(), dir, en.getKey());
        }
    }

    @Override
    public void write(UploadFile file, String dir, String name) {
        try {
			var rootDir = Paths.get(basePath, dir);

			if (!Files.exists(rootDir)) {
				Files.createDirectory(rootDir);
			}
			
			var absoluteDir = rootDir.resolve(name);
			
			Files.deleteIfExists(absoluteDir);
			
			var destPath = Files.createFile(rootDir.resolve(name));
			
			try (var is = file.getResource().getInputStream(); var os = new FileOutputStream(destPath.toFile())) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            }
		} catch (IOException e) {
			log.error("Error writing file: {}", e.getMessage());
			throw new RuntimeException(e);
		}
    }

    @Override
    public void delete(String dir, List<String> fileNames) {
    	try {
			if (fileNames == null) {
				return;
			}

			@SuppressWarnings("unused")
			int deleted = 0;
			var rootDir = Paths.get(basePath, dir);

			for (String fileName : fileNames) {
				if (!StringUtils.hasText(fileName)) {
					continue;
				}

				boolean result = Files.deleteIfExists(rootDir.resolve(fileName));

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
			var rootDir = Paths.get(basePath, dir);

			Files.deleteIfExists(rootDir.resolve(fileName));
		} catch (Exception e) {
			log.error("Error deleting file: {}", e.getMessage());
		}

    }
    
    @SuppressWarnings("unused")
	private File createFile(String parent, String fileName) {
    	if (!StringUtils.hasText(fileName)) {
    		return null;
    	}
    	int lastIndex = fileName.lastIndexOf(".");
    	var name = fileName.substring(0, lastIndex);
    	var extension = fileName.substring(lastIndex);
    	
    	var file = new File(parent, fileName);
    	
    	if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    	
    	int count = 0;
    	
    	while (file.exists()) {
    		file = new File(parent, String.format("%s-%d.%s", name, count, extension));
    		count += 1;
		}
    	
    	return file;
    	
    }

}
