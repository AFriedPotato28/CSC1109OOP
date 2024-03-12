import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Represents the security system of the bank and its functions such as user
 * registration, authentication, password reset, and OTP generation.
 */
public class Security implements csv_help {
    /**
     * Map for storing the username and password of the users.
     */
    private Map<String, String> passwordMap;
    private Map<String, Integer> otpMap;

    /**
     * Constructs a new Security object.
     */
    public Security() {
        otpMap = new HashMap<String, Integer>();
        passwordMap = new HashMap<String, String>();
    }

    /**
     * Authenticates a user with the Bank using One-Time Password (OTP).
     * 
     * @param accountID The account ID of the user.
     * @param otp       The OTP entered by the user.
     * @return True if user enters correct OTP, false otherwise.
     */
    public boolean authenticateWithOTP(String username, int otp) {

        // implementation of authentication with OTP goes here
        if (otpMap.containsKey(username) && otpMap.containsValue(otp)) {
            return true;
        }
        return false;
    }

    /**
     * Generates an OTP for the user.
     * 
     * @param accountID The accountID of the user.
     * @return The generated OTP.
     */
    public int generateOTP(String username) {
        SecureRandom rand = new SecureRandom();
        int otp = rand.nextInt(1000000);
        // Store inside OTP map to validate
        otpMap.put(username, otp);
        System.out.println(otp);
        // implementation of the otp generation goes here
        return otp;
    }

    /**
     * Validates the password of the user.
     * 
     * @param password The password to be validated.
     * @return True if the password is valid, false otherwise.
     */
    public boolean validatePassword(String password) {
        /*
         * The password must contain at least one digit [0-9].
         * The password must contain at least one lowercase letter [a-z].
         * The password must contain at least one uppercase letter [A-Z].
         * The password must contain at least one special character [*[@#$%^&+=!].
         * The password must be eight characters or longer.
         * The password must be less than 20 characters.
         */
        String regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,20}$";
        /*
         * Compile the regular expression to the pattern
         */
        Pattern pattern = Pattern.compile(regexPassword);
        /*
         * Create a matcher object using the pattern
         */
        Matcher matcher = pattern.matcher(password);
        /*
         * Check if the password matches the pattern
         * Return true if it matches, false otherwise
         */
        return matcher.matches();
    }

    public static String hashPasword(String password, String Salt) {
        try {

            KeySpec spec = new PBEKeySpec(password.toCharArray(), Salt.getBytes(), 65536, 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // Handle the error appropriately
            throw new RuntimeException("Error during password hashing", e);
        }
    }

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Resets the password of the user.
     * 
     * @param username    The username of an account.
     * @param newPassword The new password to be set.
     * @return True if the password is valid, false otherwise.
     */
    public ArrayList<String> resetPassword(String username, String newPassword) {

        String salt = generateSalt();
        String hashedPassword = hashPasword(newPassword, salt);
        setLoginAccount(username, newPassword, salt);
        ArrayList<String> result = new ArrayList<String>();
        result.add(hashedPassword);
        result.add(salt);

        return result;
    }

    public void setLoginAccount(String username, String password, String salt) {
        String hashPass = hashPasword(password, salt);

        if (!passwordMap.isEmpty()) {
            passwordMap.replace(username, hashPass);
        }

        passwordMap.put(username, hashPass);
    }

    public boolean authenticateUser(String username, String password, String salt) {
        String hashPass = hashPasword(password, salt);
        if (passwordMap.containsKey(username) && passwordMap.containsValue(hashPass)) {
            return true;
        }
        return false;
    }

    public boolean getAccount(String Username) {

        if (passwordMap.containsKey(Username)) {
            return true;
        }

        return false;
    }
}
