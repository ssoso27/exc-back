package com.swordmaster.excalibur.util;

import com.swordmaster.excalibur.enumclass.AccountType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class AccountTypeConverter implements AttributeConverter<AccountType, String> {

    @Override
    public String convertToDatabaseColumn(AccountType accountType) {
        if (accountType == null) {
            return null;
        }

        return accountType.getName();
    }

    @Override
    public AccountType convertToEntityAttribute(String name) {
        if (name == null) {
            return null;
        }

        return Stream.of(AccountType.values())
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
