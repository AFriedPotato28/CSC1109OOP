public class Branch{
    private int branchId;
    private String branchName;
    private String branchAddress;
    private int openingTime;
    private int closingTime;
    

    public Branch(int branchId, String branchName, String branchAddress, int openingTime, int closingTime){
        this.branchId = branchId;
        this.branchName = branchName;
        this.branchAddress = branchAddress;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public int getBranchID(){
        return this.branchId;
    }
    
    public String setBranchID(){
        return this.branchName;
    }
    
    public String getBranchAddress(){
        return this.branchAddress;
    }

    public int getOpeningTime(){
        return this.openingTime;
    }

    public int getClosingTime(){
        return this.closingTime;
    }

    public void setBranchID(int branchId){
        this.branchId = branchId;
    }

    public void setBranchName(String branchName){
        this.branchName = branchName;
    }

    public void setBranchAddress(String branchAddress){
        this.branchAddress = branchAddress;
    }

    public void setOpeningTime(int openingTime){
        this.openingTime = openingTime; 
    }

    public void setClosingTime(int closingTime){
        this.closingTime = closingTime;
    }


}
