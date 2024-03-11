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
    public String generateCardNumber() {
        // Logic to generate a unique and valid card number
        return "";
    }

    /**
     * Generates a CVV (Card Verification Value) code for a credit card.
     *
     * @return An integer representing the generated CVV code.
     */
    public static int generateCVV() {
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
    public static YearMonth generateExpiryDate() {
        // Logic to generate an expiry date
        YearMonth yearMonth = YearMonth.now();
        
        return yearMonth.plus(Period.of(5,0,0));
    }
}