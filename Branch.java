/**
 * Represent a branch for the bank, including details such as ID, name of the branch, address of the branch
 * as well as its opening and closing time.
 */
public class Branch{
    /**
     * The unique identifier of the branch.
     */
    private int branchId;
    /**
     * The name of the branch.
     */
    private String branchName;
    /**
     * The address of the branch.
     */
    private String branchAddress;
    /**
     * The opening hour for the branch.
     */
    private int openingTime;
    /**
     * The closing hour for the branch.
     */
    private int closingTime;
    
    /**
     * Construct a Branch object with ID, name address as well as operating hours.
     * @param branchId The unique identifier of the branch
     * @param branchName The name of the branch
     * @param branchAddress The address of the branch
     * @param openingTime The opening hour for the branch
     * @param closingTime The closing hour for the branch
     */
    public Branch(int branchId, String branchName, String branchAddress, int openingTime, int closingTime){
        this.setBranchID(branchId);
        this.setBranchName(branchName);
        this.setBranchAddress(branchAddress);
        this.setOpeningTime(openingTime);
        this.setClosingTime(closingTime);
    }

    /**
     * Return the Branch ID
     * @return The branch ID
     */
    public int getBranchID(){
        return this.branchId;
    }
    
    /**
     * Return the Branch Name
     * @return The branch Name
     */
    public String getBranchName(){
        return this.branchName;
    }
    
    /**
     * Return the Branch Address
     * @return The branch address
     */
    public String getBranchAddress(){
        return this.branchAddress;
    }

    /**
     * Return the Branch Opening Hour
     * @return The branch opening time
     */
    public int getOpeningTime(){
        return this.openingTime;
    }

    /**
     * Return the Branch Closing Hour
     * @return The branch closing time
     */
    public int getClosingTime(){
        return this.closingTime;
    }

    /**
     * Set the Branch ID
     * @param branchId The branch ID is set
     */
    public void setBranchID(int branchId){
        this.branchId = branchId;
    }

     /**
     * Set the Branch Name
     * @param branchId The branch name is set
     */
    public void setBranchName(String branchName){
        this.branchName = branchName;
    }

     /**
     * Set the Branch Address
     * @param branchId The branch Address is set
     */
    public void setBranchAddress(String branchAddress){
        this.branchAddress = branchAddress;
    }

     /**
     * Set the Branch Opening Hour
     * @param branchId The branch opening hour is set
     */
    public void setOpeningTime(int openingTime){
        this.openingTime = openingTime; 
    }

     /**
     * Set the Branch Closing Hour
     * @param branchId The branch closing time is set
     */
    public void setClosingTime(int closingTime){
        this.closingTime = closingTime;
    }
    
}
