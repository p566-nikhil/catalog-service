package com.polarbookshop.catalogservice.domain;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsbnValidator implements ConstraintValidator<ValidIsbn, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || isValidIsbn(value);
    }

    static boolean isValidIsbn(String isbn) {
        String s = isbn.replaceAll("[\\s-]", "").toUpperCase();
        return switch (s.length()) {
            case 10 -> isIsbn10(s);
            case 13 -> isIsbn13(s);
            default -> false;
        };
    }

    private static boolean isIsbn10(String s) {
        if (!s.matches("\\d{9}[\\dX]")) return false;
        int sum = 0;
        for (int i = 0; i < 10; i++) {
        int digit = s.charAt(i) == 'X' ? 10 : s.charAt(i) - '0';
        sum += digit * (10 - i);
        }
        return sum % 11 == 0;
    }

    private static boolean isIsbn13(String s) {
        if (!s.matches("\\d{13}")) return false;
        int sum = 0;
        for (int i = 0; i < 13; i++) {
            int d = s.charAt(i) - '0';
            sum += (i % 2 == 0) ? d : 3 * d;
        }
        return sum % 10 == 0;
    }
}