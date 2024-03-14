import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Represents the security system of the bank and its functions such as user
 * registration, authentication, password reset, and OTP generation.
 */
public class Security {
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

    public static String hashPassword(String password, String Salt) {
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

    // Encryption method for CVV
    public static String encryptCVV(String cvv) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(cvv.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decryption method for CVV
    public static String decryptCVV(String encryptedCVV) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedCVV));
        return new String(decryptedBytes);
    }

    // Generate secret key
    private static SecretKey generateKey() throws NoSuchAlgorithmException {
        // Create a KeyGenerator object for AES
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");

        // Set the key length (128, 192, or 256 bits)
        int keySize = 128; // Change to 192 or 256 for larger key sizes
        keyGen.init(keySize);

        // Generate a secret key
        return keyGen.generateKey();
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
        String hashedPassword = hashPassword(newPassword, salt);
        setLoginAccount(username, newPassword, salt);
        ArrayList<String> result = new ArrayList<String>();
        result.add(hashedPassword);
        result.add(salt);

        return result;
    }

    public void setLoginAccount(String username, String password, String salt) {
        String hashPass = hashPassword(password, salt);

        if (!passwordMap.isEmpty()) {
            passwordMap.replace(username, hashPass);
        }

        passwordMap.put(username, hashPass);
    }

    public boolean authenticateUser(String username, String password, String salt) {
        String hashPass = hashPassword(password, salt);
        if (passwordMap.containsKey(username) && passwordMap.containsValue(hashPass)) {
            return true;
        }
        return false;
    }

    public boolean validateUsername(String Username) {

        if (passwordMap.containsKey(Username)) {
            return true;
        }

        return false;
    }


      /**
     * Logs the activity of the user.
     * 
     * @param accountID      The accountID of the user.
     * @param activityNumber The activity number to be logged.
     */
    public void logActivity(int accountID, int activityNumber) {
        /*
         * Log the activity based on the activity number
         * 1 - User logged in
         * 2 - User initiate bank transfer
         * 3 - User logged out
         * 4 - User initiate deposit
         * 5 - User initiate withdraw
         * Show the activity and the date and time
         */
        switch (activityNumber) {
            case 1:
                csv_help.generateCSVofSecurity("Login", accountID);
                break;
            case 2:
                // Log the user bank transfer activity
                csv_help.generateCSVofSecurity("Transfer Initialized", accountID);
                // Break the switch statement if the activity number is 2
                break;
            case 3:
                // Log the user logout activity
                // Break the switch statement if the activity number is 3
                csv_help.generateCSVofSecurity("Logout", accountID);
                break;
            case 4:
                // Log the user deposit activity
                csv_help.generateCSVofSecurity("Deposit", accountID);
                break;
            case 5:
                // Log the user withdraw activity
                csv_help.generateCSVofSecurity("Withdraw", accountID);
                break;
        }
    }
}
