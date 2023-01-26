package com.shoppingcenter.service.storage;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.shoppingcenter.service.UploadFile;

public interface FileStorageService {

    List<String> write(Set<Entry<String, UploadFile>> files, String dir) throws IOException;

    String write(UploadFile file, String dir, String name) throws IOException;

    CompletableFuture<Integer> delete(String dir, List<String> fileNames);

    CompletableFuture<Integer> delete(String dir, String fileName);

}
