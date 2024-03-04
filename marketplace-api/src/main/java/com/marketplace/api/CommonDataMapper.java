package com.marketplace.api;

import org.springframework.web.multipart.MultipartFile;

import com.marketplace.domain.UploadFile;

public interface CommonDataMapper {

	default UploadFile map(MultipartFile source) {
		return MultipartFileConverter.toUploadFile(source);
	}
	
}
