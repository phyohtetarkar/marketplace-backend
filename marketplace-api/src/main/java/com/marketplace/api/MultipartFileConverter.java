package com.marketplace.api;

import org.springframework.web.multipart.MultipartFile;

import com.marketplace.domain.UploadFile;

public interface MultipartFileConverter {

	static UploadFile toUploadFile(MultipartFile source) {
		if (source == null || source.isEmpty()) {
			return null;
		}
		UploadFile file = new UploadFile();
		file.setResource(source.getResource());
		file.setSize(source.getSize());
		file.setOriginalFileName(source.getOriginalFilename());
		return file;
	}
	
}
