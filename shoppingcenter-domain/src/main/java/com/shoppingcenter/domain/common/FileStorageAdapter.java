package com.shoppingcenter.domain.common;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.shoppingcenter.domain.UploadFile;

public interface FileStorageAdapter {

    List<String> write(Set<Entry<String, UploadFile>> files, String dir);

    String write(UploadFile file, String dir, String name);

    void delete(String dir, List<String> fileNames);

    void delete(String dir, String fileName);

}
