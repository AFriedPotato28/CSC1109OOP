import java.security.SecureRandom;
import java.time.Period;
import java.time.YearMonth;

/**
 * Utility class for generating credit card information such as card numbers, CVV codes, and expiry dates.
 */
public interface CreditCardGenerator {
    // issue identifier number
    String IIN = "4";

    // ID of the bank
    String BANK_ID = "12345";

    /**
     * Generates a unique and valid credit card number.
     */
    public default String generateCardNumber(int accountNo) {
        // Logic to generate a unique and valid card number
        StringBuilder creditNo = new StringBuilder();

        String zeros = getZeros(accountNo);

        creditNo.append(IIN + BANK_ID).append(zeros).append(String.valueOf(accountNo)).append("1");

        return creditNo.toString();
    }

    private String getZeros(int accountNo) {
        StringBuilder zeros = new StringBuilder();
        int zeroToGenerate = 9 - String.valueOf(accountNo).length();
        for (int i = 0; i < zeroToGenerate; i++) {
            zeros.append("0");
        }

        return zeros.toString();
    }

    /**
     * Generates a CVV (Card Verification Value) code for a credit card.
     *
     * @return An integer representing the generated CVV code.
     */
    public default int generateCVV() {
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
    public default YearMonth generateExpiryDate() {
        // Logic to generate an expiry date
        YearMonth yearMonth = YearMonth.now();

        return yearMonth.plus(Period.of(5, 0, 0));
    }
}