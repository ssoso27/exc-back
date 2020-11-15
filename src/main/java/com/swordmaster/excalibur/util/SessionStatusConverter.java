package com.swordmaster.excalibur.util;

import com.swordmaster.excalibur.enumclass.SessionStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class SessionStatusConverter implements AttributeConverter<SessionStatus, String> {

    @Override
    public String convertToDatabaseColumn(SessionStatus sessionStatus) {
        if (sessionStatus == null) {
            return null;
        }
        return sessionStatus.getName();
    }

    @Override
    public SessionStatus convertToEntityAttribute(String name) {
        if (name == null) {
            return null;
        }

        return Stream.of(SessionStatus.values())
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
