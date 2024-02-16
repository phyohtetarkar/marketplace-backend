package com.marketplace.api.consumer.general;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.TypeToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityDTO {

	private long id;

	private String name;
	
	public static Type listType() {
        return new TypeToken<List<CityDTO>>() {
        }.getType();
    }

}
