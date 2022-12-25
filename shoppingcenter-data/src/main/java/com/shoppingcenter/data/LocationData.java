package com.shoppingcenter.data;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class LocationData implements Serializable {

	private static final long serialVersionUID = 1L;

	private double latitude;

	private double longitude;

	public LocationData() {
		super();
	}

	public LocationData(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

}
