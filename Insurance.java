import java.util.ArrayList;

public class Insurance {
    private int insuranceID;
    private String insuranceType;
    private ArrayList<String> beneficiaryNames;
    private double coverageAmount;
    private double insurancePremium;
    private int premiumAge = 55;

    public Insurance(int insuranceID, String insuranceType) {
        this.setInsuranceID(insuranceID);
        this.setInsuranceType(insuranceType);
        this.setBeneficiaryNames(beneficiaryNames);
        this.setCoverageAmount(5000.00);
        this.setInsurancePremium(150.0);
    }

    public int getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(int insuranceID) {
        this.insuranceID = insuranceID;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public ArrayList<String> getBeneficiaryNames() {
        return beneficiaryNames;
    }

    public void setBeneficiaryNames(ArrayList<String> beneficiaryNames) {
        this.beneficiaryNames = beneficiaryNames;
    }

    public double getCoverageAmount() {
        return coverageAmount;
    }

    public void setCoverageAmount(double coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    public double getInsurancePremium() {
        return insurancePremium;
    }

    public void setInsurancePremium(double insurancePremium) {
        this.insurancePremium = insurancePremium;
    }

    public void displayInsuranceDetails() {
        System.out.println("Current Insurance Details:");
        System.out.println("Insurance ID: " + this.insuranceID);
        System.out.println("Insurance Type: " + this.insuranceType);
        System.out.println("Beneficiary Member: " + this.beneficiaryNames);
        System.out.println("Coverage Amount: " + this.coverageAmount);
    }

    public double calculatePremium(int premiumAge) {
        double premiumAgeAmount = this.insurancePremium;
        if (premiumAge > this.premiumAge) {
            premiumAgeAmount *= 1.15;
        }

        return premiumAgeAmount;
    }

    public void updateCoverageAmount(double newCoverageAmount) {
        this.coverageAmount = newCoverageAmount;
    }

    public boolean isCoverageSufficient(double claimAmount) {
        return this.coverageAmount >= claimAmount;
    }

    public void processClaim(double claimAmount) {
        if (this.isCoverageSufficient(claimAmount)) {
            System.out.println("Successfully processed Insurance Claim.");
            this.coverageAmount -= claimAmount;
        } else {
            System.out.println("Claim denied. Insufficient Coverage Amount");
        }
    }

    public void addBeneficiary(String beneficiaryNames) {
        this.beneficiaryNames.add(beneficiaryNames);
    }

    public void removeBeneficiary(String beneficiaryNames) {
        this.beneficiaryNames.remove(beneficiaryNames);
    }
}