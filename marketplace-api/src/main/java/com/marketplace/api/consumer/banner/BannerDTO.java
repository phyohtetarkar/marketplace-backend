package com.marketplace.api.consumer.banner;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.marketplace.api.AbstractImageFieldSerializer.BannerImageSerializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerDTO {
    private int id;

    @JsonSerialize(using = BannerImageSerializer.class)
    private String image;

    private String link;

    private int position;
}
