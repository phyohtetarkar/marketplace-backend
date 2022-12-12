package com.shoppingcenter.core;

import java.io.File;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFile {
	
	private String originalFileName;
	
	private long size;
	
	private File file;
	
	public UploadFile() {
	}
	
}