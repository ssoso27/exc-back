package com.swordmaster.excalibur.util;

import com.swordmaster.excalibur.enumclass.SignUpType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class AccountTypeConverter implements AttributeConverter<SignUpType, String> {

    @Override
    public String convertToDatabaseColumn(SignUpType accountType) {
        if (accountType == null) {
            return null;
        }

        return accountType.getName();
    }

    @Override
    public SignUpType convertToEntityAttribute(String name) {
        if (name == null) {
            return null;
        }

        return Stream.of(SignUpType.values())
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
