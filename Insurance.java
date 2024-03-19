/**
 * Represents insurance policy with various attributes such as ID, type,
 * beneficiaries,
 * Coverage amount, and premium. This class provide methods for managing
 * insurance details
 * such as updating coverage, updating premiums and processing claim.
 */

public class Insurance {
    private static int lastAssignedId = 0;
    /**
     * Identifier Number for the Insurance.
     */
    private int insuranceID;
    /**
     * Type of Insurance.
     */
    private int customerId;
    private String insuranceType;
    /**
     * List of the beneficiary name to be included inside the insurance plan.
     */
    private String beneficiaryName;
    /**
     * Amount that can be claimed by the beneficiary in the insurance plan.
     */
    private double coverageAmount;
    /**
     * Amount policy holder has to pay for the insurance plan.
     */
    private double insurancePremium;
    /**
     * Age for the beneficiary to be in the insurance plan.
     */
    private int premiumAge = 55;

    /**
     * Constructs a new Insurance instance with specified ID and Type.
     * Initialize the beneficiary list and sets default value for coverage amiount
     * and insurance premium.
     * 
     * @param insuranceID   The unique indentifier for the insurance.
     * @param insuranceType The type of insurance.
     */
    public Insurance(int customerId, String insuranceType, String beneficiaryName) {
        this.insuranceID = ++lastAssignedId;
        this.customerId = customerId;
        this.insuranceType = insuranceType;
        this.beneficiaryName = beneficiaryName;
        this.setCoverageAmount(5000.00);
        this.setInsurancePremium(150.0);
    }

    /**
     * Returns the insurance ID.
     * 
     * @return The insurance ID.
     */
    public int getInsuranceID() {
        return insuranceID;
    }

    /**
     * Return the insurance type.
     * 
     * @return The insurance type.
     */
    public String getInsuranceType() {
        return insuranceType;
    }

    /**
     * Return the list of beneficiary names.
     * 
     * @return The list of beneficiary names.
     */
    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    /**
     * Return the coverage amount.
     * 
     * @return The coverage amount.
     */
    public double getCoverageAmount() {
        return coverageAmount;
    }

    /**
     * Set the coverage amount.
     * 
     * @param coverageAmount Coverage Amount to set.
     */
    public void setCoverageAmount(double coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    /**
     * Return the insurance premium.
     * 
     * @return The insurance premium.
     */
    public double getInsurancePremium() {
        return insurancePremium;
    }

    /**
     * Set the insurance premium.
     * 
     * @param insurancePremium The insurance premium to set.
     */
    public void setInsurancePremium(double insurancePremium) {
        this.insurancePremium = insurancePremium;
    }

    /**
     * Display the current insurance detail including ID, Type, Beneficiary names as
     * well as coverage amount.
     */
    public void displayInsuranceDetails() {
        System.out.println("Current Insurance Details:");
        System.out.println("Insurance ID: " + this.insuranceID);
        System.out.println("Insurance Type: " + this.insuranceType);
        System.out.println("Beneficiary Member: " + this.beneficiaryName);
        System.out.println("Coverage Amount: " + this.coverageAmount);
    }

    /**
     * Calculate the insurance premium based on the age of the premium holder.
     * Applies additional charge if age is above a certain threshold.
     * 
     * @param premiumAge The age of the premium holder.
     * @return The calculated amount.
     */
    public double calculatePremium(int premiumAge) {
        double premiumAgeAmount = this.insurancePremium;
        if (premiumAge > this.premiumAge) {
            premiumAgeAmount *= 1.15;
        }

        return premiumAgeAmount;
    }

    /**
     * Update the coverage amount to a new value.
     * 
     * @param newCoverageAmount The new coverage amount.
     */
    public void updateCoverageAmount(double newCoverageAmount) {
        this.coverageAmount = newCoverageAmount;
    }

    /**
     * Check if coverage is sufficient for a given claim amount.
     * 
     * @param claimAmount The claim amount.
     * @return True if coverage is sufficient, else false.
     */
    public boolean isCoverageSufficient(double claimAmount) {
        return this.coverageAmount >= claimAmount;
    }

    /**
     * Process an insurance claim by deducting the claim amount from coverage
     * if the coverage is sufficient, else the claim is denied.
     * 
     * @param claimAmount The claim amount to process.
     */
    public void processClaim(double claimAmount) {
        if (this.isCoverageSufficient(claimAmount)) {
            System.out.println("Successfully processed Insurance Claim.");
            this.coverageAmount -= claimAmount;
        } else {
            System.out.println("Claim denied. Insufficient Coverage Amount");
        }
    }

    public double getCustomerId() {
        return customerId;
    }
}