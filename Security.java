import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Security extends Bank{

    private Map<String, String> passwordMap;
    private Map<String, Integer> otpMap;


    public Security(String name){
        super(name);
    }

    public void registerUser(String username, String password){
    }
    public boolean authenticateWithOTP(String username, String otp){
    }
    public String generateOTP(String username){
    }
    public boolean resetPassword(String username, String newPassword){
    }
    public boolean validatePassword(String password){
        String regexPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        Pattern pattern = Pattern.compile(regexPassword);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    public void logActivity(String user, int activityNumber){
        LocalDateTime dateTimeObj = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDt = dateTimeObj.format(dateTimeFormatObj);
        switch (activityNumber){
            case 1:
                System.out.println("User logged in at " + formattedDt);
                break;
            case 2:
                System.out.println("User initiate bank transfer " + formattedDt);
                break;
            case 3:
                System.out.println("User logged out at " + formattedDt);
                break;
            case 4:
                System.out.println("User initiate deposit at " + formattedDt);
            case 5:
                System.out.println("User initiate withdraw at " + formattedDt);
        }
    }


}
