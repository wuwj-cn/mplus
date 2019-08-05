package com.mplus.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DataStateConverter implements AttributeConverter<Status, String> {

	@Override
	public String convertToDatabaseColumn(Status state) {
		return state.getCode();
	}

	@Override
	public Status convertToEntityAttribute(String dbData) {
		return Status.fromString(dbData);
	}
	
}
