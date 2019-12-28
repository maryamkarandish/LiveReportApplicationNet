package livereportapplicationnet.entities;

public class Bank {
    private String bankCode;
    private String bankDescription;

    public Bank(String bank_code,String bank_description) {
        this.bankCode = bank_code;
        this.bankDescription = bank_description;
    }
    public Bank() {
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bank_code) {
        this.bankCode = bank_code;
    }

    public String getBankDescription() {
        return bankDescription;
    }

    public void setBankDescription(String bank_description) {
        this.bankDescription = bank_description;
    }
}
