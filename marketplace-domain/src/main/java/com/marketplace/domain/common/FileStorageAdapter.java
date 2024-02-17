package com.marketplace.domain.common;

import java.util.List;
import java.util.Map;

import com.marketplace.domain.UploadFile;

public interface FileStorageAdapter {

    void write(Map<String, UploadFile> files, String dir);

    void write(UploadFile file, String dir, String name);

    void delete(String dir, List<String> fileNames);

    void delete(String dir, String fileName);

}
