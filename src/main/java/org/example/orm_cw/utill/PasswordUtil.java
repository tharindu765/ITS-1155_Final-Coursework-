package org.example.orm_cw.utill;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hashing a password with salt
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Checking the password with the stored hash
    public static boolean checkPassword(String password, String storedHash) {
        return BCrypt.checkpw(password, storedHash);
    }
}
