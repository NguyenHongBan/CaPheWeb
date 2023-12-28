package com.api.cafe.services;

import java.util.UUID;

public class ValidationUtils {
    public static boolean existsFormById(UUID id) {
        try {
            final UUID uuid = UUID.fromString(String.valueOf(id));
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
