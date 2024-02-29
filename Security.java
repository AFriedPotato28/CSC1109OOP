import java.util.Map;

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
    }
    public void logActivity(String activity){

    }


}
