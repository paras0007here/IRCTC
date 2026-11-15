package org.example.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class UserServiceUtil {

    public static String hashPassword(String plainPassword){
        if (plainPassword == null || plainPassword.isBlank()) {
            throw new IllegalArgumentException("Password must not be blank");
        }
        return BCrypt.withDefaults().hashToString(12, plainPassword.toCharArray());
    }

    public static boolean checkPassword(String plainPassword,String hashedPassword){
        if (plainPassword == null || hashedPassword == null || hashedPassword.isBlank()) {
            return false;
        }
        try {
            return BCrypt.verifyer().verify(plainPassword.toCharArray(), hashedPassword).verified;
        } catch (IllegalArgumentException e) {
            // Stored data may contain legacy or malformed hashes.
            return false;
        }
    }
}
