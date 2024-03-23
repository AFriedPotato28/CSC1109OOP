package implementations;
import java.security.SecureRandom;
import java.time.Period;
import java.time.YearMonth;

/**
 * Utility class for generating credit card information such as card numbers,
 * CVV codes, and expiry dates.
 */
public final class CreditCardGenerator {
    // issue identifier number
    private static final String IIN = "4";
    // ID of the bank
    private static final String BANK_ID = "12345";

    private CreditCardGenerator() {
        // Private constructor to prevent instantiation
    }

    /**
     * Generates a unique and valid credit card number.
     *
     * @param accountNo     The account number associated with the credit card.
     * @param creditCardId  The unique identifier of the credit card.
     * @return              A String representing the generated credit card number.
     */

    public static String generateCardNumber(int accountNo, int creditCardId) {
        // Logic to generate a unique and valid card number
        StringBuilder cardNumber = new StringBuilder();

        String uniqueId = accountNo + String.valueOf(creditCardId);
        String randomValue = getRandomNumber(uniqueId);

        cardNumber.append(IIN).append(BANK_ID).append(randomValue).append(accountNo).append(creditCardId);
        int checkDigit = calculateCheckDigit(cardNumber.toString());
        cardNumber.append(checkDigit);

        return cardNumber.toString();
    }

    private static String getRandomNumber(String uniqueId) {
        int totalLength = 9;
        StringBuilder randomNumber = new StringBuilder();
        int spacesToGenerate = totalLength - uniqueId.length();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < spacesToGenerate; i++) {
            randomNumber.append(random.nextInt(10));
        }

        return randomNumber.toString();
    }

    // Calculates the check digit for the given card number using the Luhn algorithm
    private static int calculateCheckDigit(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));
            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            alternate = !alternate;
        }
        int remainder = sum % 10;
        return (remainder == 0) ? 0 : (10 - remainder);
    }

    /**
     * Generates a CVV (Card Verification Value) code for a credit card.
     *
     * @return An integer representing the generated CVV code.
     */
    public static String generateCVV() {
        // Logic to generate a CVV code
        SecureRandom random = new SecureRandom();
        String code = String.format("%03d", random.nextInt(1000));

        // Print cvv for debugging purposes only
        System.out.println("Your cvv for this credit card is: " + code);
        return code;
    }

    /**
     * Generates an expiry date for a credit card.
     *
     * @return A Date object representing the generated expiry date.
     */
    public static YearMonth generateExpiryDate() {
        // Logic to generate an expiry date
        YearMonth yearMonth = YearMonth.now();
        return yearMonth.plus(Period.of(5, 0, 0));
    }
}
