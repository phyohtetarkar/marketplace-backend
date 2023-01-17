package com.shoppingcenter.core.storage;

import java.io.IOException;
import java.util.List;

import com.shoppingcenter.core.UploadFile;

public interface FileStorageService {

    List<String> write(List<UploadFile> files, String dir, String name) throws IOException;

    String write(UploadFile file, String dir, String name) throws IOException;

    void delete(String dir, List<String> fileNames) throws IOException;

    void delete(String dir, String fileName) throws IOException;

}
