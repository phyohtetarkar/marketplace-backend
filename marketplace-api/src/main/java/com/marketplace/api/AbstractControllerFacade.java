package com.marketplace.api;

import java.lang.reflect.Type;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractControllerFacade {

	protected ModelMapper modelMapper;

	protected <D> D map(Object source, Type destinationType) {
		if (source == null) {
			return null;
		}
		return modelMapper.map(source, destinationType);
	}

	@Autowired
	public final void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

}
