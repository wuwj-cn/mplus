package com.mplus.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DataStateConverter implements AttributeConverter<DataStatus, String> {

	@Override
	public String convertToDatabaseColumn(DataStatus state) {
		return state.getCode();
	}

	@Override
	public DataStatus convertToEntityAttribute(String dbData) {
		return DataStatus.fromString(dbData);
	}
	
}
