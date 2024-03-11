import java.security.SecureRandom;
import java.time.Period;
import java.time.YearMonth;

/**
 * Utility class for generating credit card information such as card numbers, CVV codes, and expiry dates.
 */
public class CreditCardGenerator {
    /**
     * Generates a unique and valid credit card number.
     *
     * @return A String representing the generated credit card number.
     */
    public String generateCardNumber(int accountNo) {
        // Logic to generate a unique and valid card number
        StringBuilder creditNo = new StringBuilder();

        String zeros = getZeros(accountNo);
        
        creditNo.append("412345" + zeros + String.valueOf(accountNo) + "1" ); 

        return creditNo.toString();
    }

    private String getZeros(int accountNo) {
        StringBuilder zeros = new StringBuilder();
        int zeroToGenerate = 9 - String.valueOf(accountNo).length();
        for (int i = 0; i < zeroToGenerate; i ++ ){
            zeros.append("0");
        }
        
        return zeros.toString();
    }

    /**
     * Generates a CVV (Card Verification Value) code for a credit card.
     *
     * @return An integer representing the generated CVV code.
     */
    public int generateCVV() {
        // Logic to generate a CVV code
        SecureRandom random = new SecureRandom();
        String code = String.format("%03d", random.nextInt(1000));
        return Integer.parseInt(code);
    }

    /**
     * Generates an expiry date for a credit card.
     *
     * @return A Date object representing the generated expiry date.
     */
    public YearMonth generateExpiryDate() {
        // Logic to generate an expiry date
        YearMonth yearMonth = YearMonth.now();
        
        return yearMonth.plus(Period.of(5,0,0));
    }
}