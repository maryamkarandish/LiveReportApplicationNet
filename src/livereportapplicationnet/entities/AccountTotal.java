package livereportapplicationnet.entities;

public class AccountTotal {
    private int id;
    private String bank;
    private String customerNo;
    private String accountOwnerName;
    private String accountType;
    private String branch;
    private String nationalCode;
    private String creationTime;
    private boolean enabled;
    private boolean isSurgeChargeAcc;
    private String customerType;
    private String  trusteeClient;
    
    private String bankDescription;
    public AccountTotal(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getAccountOwnerName() {
        return accountOwnerName;
    }

    public void setAccountOwnerName(String accountOwnerName) {
        this.accountOwnerName = accountOwnerName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isSurgeChargeAcc() {
        return isSurgeChargeAcc;
    }

    public void setSurgeChargeAcc(boolean surgeChargeAcc) {
        isSurgeChargeAcc = surgeChargeAcc;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String  getTrusteeClient() {
        return trusteeClient;
    }

    public void setTrusteeClient(String trusteeClient) {
        this.trusteeClient = trusteeClient;
    }

    public boolean isIsSurgeChargeAcc() {
        return isSurgeChargeAcc;
    }

    public void setIsSurgeChargeAcc(boolean isSurgeChargeAcc) {
        this.isSurgeChargeAcc = isSurgeChargeAcc;
    }

    public String getBankDescription() {
        return bankDescription;
    }

    public void setBankDescription(String bankDescrioption) {
        this.bankDescription = bankDescrioption;
    }
    
}
