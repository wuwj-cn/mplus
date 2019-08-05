package com.mplus.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UserStateConverter implements AttributeConverter<UserState, String> {

	@Override
	public String convertToDatabaseColumn(UserState state) {
		return state.getCode();
	}

	@Override
	public UserState convertToEntityAttribute(String dbData) {
		return UserState.fromString(dbData);
	}
}
