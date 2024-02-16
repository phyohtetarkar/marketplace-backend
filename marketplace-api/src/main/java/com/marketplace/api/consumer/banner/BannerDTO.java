package com.marketplace.api.consumer.banner;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

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

    public static Type listType() {
        return new TypeToken<List<BannerDTO>>() {
        }.getType();
    }
}
