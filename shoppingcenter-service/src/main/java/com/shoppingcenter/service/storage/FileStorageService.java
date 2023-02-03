package com.shoppingcenter.service.storage;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.shoppingcenter.service.UploadFile;

public interface FileStorageService {

    List<String> write(Set<Entry<String, UploadFile>> files, String dir) throws IOException;

    String write(UploadFile file, String dir, String name) throws IOException;

    void delete(String dir, List<String> fileNames);

    void delete(String dir, String fileName);

}
