package com.marketplace.api.admin.banner;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerEditDTO {
	
    private int id;

    private String link;

    private int position;

    private MultipartFile file;
}
