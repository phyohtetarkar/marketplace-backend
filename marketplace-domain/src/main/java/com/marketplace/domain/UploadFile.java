package com.marketplace.domain;

import org.springframework.core.io.Resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFile {

	private String originalFileName;

	private long size;

	private Resource resource;

	public UploadFile() {
	}

	public String getExtension() {
		String[] names = originalFileName.split("\\.");
		if (names.length > 1) {
			return names[names.length - 1];
		}
		return "png";
	}

	public boolean isEmpty() {
		return size <= 0;
	}

}
