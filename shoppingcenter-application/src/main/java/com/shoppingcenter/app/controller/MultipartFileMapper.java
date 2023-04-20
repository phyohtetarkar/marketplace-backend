package com.shoppingcenter.app.controller;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.shoppingcenter.domain.UploadFile;

public class MultipartFileMapper {

	public static UploadFile toUploadFile(MultipartFile source) {
		try {
			if (source == null || source.isEmpty()) {
				return null;
			}
			UploadFile file = new UploadFile();
			file.setInputStream(source.getInputStream());
			file.setSize(source.getSize());
			file.setOriginalFileName(source.getOriginalFilename());
			return file;
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
	
}
