package com.shoppingcenter.service;

import java.io.InputStream;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFile {

	private String originalFileName;

	private long size;

	private InputStream inputStream;

	public UploadFile() {
	}

	public String getExtension() {
		String[] names = originalFileName.split("\\.");
		return names[names.length - 1];
	}

}
