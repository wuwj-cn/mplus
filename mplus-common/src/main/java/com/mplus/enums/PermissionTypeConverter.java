package com.mplus.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PermissionTypeConverter implements AttributeConverter<PermissionType, String> {
	@Override
	public String convertToDatabaseColumn(PermissionType type) {
		return type.getCode();
	}

	@Override
	public PermissionType convertToEntityAttribute(String dbData) {
		return PermissionType.fromString(dbData);
	}
}
