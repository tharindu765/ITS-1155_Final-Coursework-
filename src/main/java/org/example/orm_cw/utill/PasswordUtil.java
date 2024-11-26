package org.example.orm_cw.utill;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hash a password with bcrypt
    public static String hashPassword(String password) {
        // Generate a salt and hash the password
        return BCrypt.hashpw(password, BCrypt.gensalt(12)); // 12 rounds for bcrypt
    }

    // Check if a password matches the stored hash
    public static boolean checkPassword(String password, String storedHash) {
        // Check if the given password matches the hashed password
        return BCrypt.checkpw(password, storedHash);
    }
}
