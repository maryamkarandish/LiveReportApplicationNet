/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livereportapplicationnet.entities;

/**
 *
 * @author M_Karandish
 */
public class Payesh {
    private String date;
    private String service;
    private String tpp;
    private String bank;
    private String total;
    private String successful;
    private String unsuccessful;
    private String userError;
    private String systemError;
    private String coreError;
    private String cardError;

    public Payesh() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTpp() {
        return tpp;
    }

    public void setTpp(String tpp) {
        this.tpp = tpp;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSuccessful() {
        return successful;
    }

    public void setSuccessful(String successful) {
        this.successful = successful;
    }

    public String getUnsuccessful() {
        return unsuccessful;
    }

    public void setUnsuccessful(String unsuccessful) {
        this.unsuccessful = unsuccessful;
    }

    public String getUserError() {
        return userError;
    }

    public void setUserError(String userError) {
        this.userError = userError;
    }

    public String getSystemError() {
        return systemError;
    }

    public void setSystemError(String systemError) {
        this.systemError = systemError;
    }

    public String getCoreError() {
        return coreError;
    }

    public void setCoreError(String coreError) {
        this.coreError = coreError;
    }

    public String getCardError() {
        return cardError;
    }

    public void setCardError(String cardError) {
        this.cardError = cardError;
    }
    
    
    
}
