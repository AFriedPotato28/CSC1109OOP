import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the security system of the bank and its functions such as user registration, authentication, password reset, and OTP generation.
 * Security Class polyporphism from Bank class
 */
public class Security extends Bank{
    /**
     * Map for storing the username and password of the users.
     */
    private Map<String, String> passwordMap;
    private Map<String, Integer> otpMap;

    /**
     * Constructs a new Security object.
     * @param name The name of the bank.
     */
    public Security(String name){
        super(name);
    }

    /**
     * Registers a new user with the bank.
     * @param username The username of the new registered user.
     * @param password The password of the newly registered user.
     */
    public void registerUser(String username, String password){
    }

    /**
     * Authenticats a user with the Bank using OTP.
     * @param username The username of the user.
     * @param otp The OTP entered by the user.
     */
    public boolean authenticateWithOTP(String username, String otp){
    }

    /**
     * Generates an OTP for the user.
     * @param username The username of the user.
     * @return The generated OTP.
     */
    public String generateOTP(String username){
    }

    /**
     * Resets the password of the user.
     * @param username The username of the user.
     * @param newPassword The new password to be set.
     */
    public boolean resetPassword(String username, String newPassword){
    }

    /**
     * Validates the password of the user.
     * @param password The password to be validated.
     * @return True if the password is valid, false otherwise.
     */
    public boolean validatePassword(String password){
        /**
         * The password must contain at least one digit [0-9].
         * The password must contain at least one lowercase letter [a-z].
         * The password must contain at least one uppercase letter [A-Z].
         * The password must contain at least one special character [!@#&()–[{}]:;',?/*~$^+=<>].
         * The password must be eight characters or longer.
         * The password must be less than 20 characters.
         */
        String regexPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        /**
         * Compile the regular expression to the pattern
         */
        Pattern pattern = Pattern.compile(regexPassword);
        /**
         * Create a matcher object using the pattern
         */
        Matcher matcher = pattern.matcher(password);
        /**
         * Check if the password matches the pattern
         * Return true if it matches, false otherwise
         */
        return matcher.matches();
    }

    /**
     * Logs the activity of the user.
     * @param user The username of the user.
     * @param activityNumber The activity number to be logged.
     */
    public void logActivity(String user, int activityNumber){
        LocalDateTime dateTimeObj = LocalDateTime.now();
        /**
         * Format the date and time
         * The format is dd-MM-yyyy HH:mm:ss
         */
        DateTimeFormatter dateTimeFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDt = dateTimeObj.format(dateTimeFormatObj);
        /**
         * Log the activity based on the activity number
         * 1 - User logged in
         * 2 - User initiate bank transfer
         * 3 - User logged out
         * 4 - User initiate deposit
         * 5 - User initiate withdraw
         * Show the activity and the date and time
         */
        switch (activityNumber){
            case 1:
                /**
                 * Log the user login activity
                 */
                System.out.println("User logged in at " + formattedDt);
                /**
                 * Break the switch statement if the activity number is 1
                 */
                break;
            case 2:
                /**
                 * Log the user bank transfer activity
                 */
                System.out.println("User initiate bank transfer " + formattedDt);
                /** 
                 * Break the switch statement if the activity number is 2
                 */
                break;
            case 3:
                /**
                 * Log the user logout activity
                 */
                System.out.println("User logged out at " + formattedDt);
                /**
                 * Break the switch statement if the activity number is 3
                 */
                break;
            case 4:
                /**
                 * Log the user deposit activity
                 */
                System.out.println("User initiate deposit at " + formattedDt);
            case 5:
                /**
                 * Log the user withdraw activity
                 */
                System.out.println("User initiate withdraw at " + formattedDt);
        }
    }


}
