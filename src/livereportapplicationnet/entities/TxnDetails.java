/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livereportapplicationnet.entities;

import java.sql.Timestamp;

/**
 *
 * @author M_Karandish
 */
public class TxnDetails {
    
    private long id;
    private String clientId;
    private String uuid;
    private String requestedUuid;
    private long clientTimestamp;
    private String serviceCode;
    private String bank;
    private String nationalCode;
    private String accountNo;
    private String inLogDate;
    private String outLogDate;
    private String state;
    private String tokenId;
    private String longitude;
    private String latitude;
    private String coreRespCode;
    private String coreRespDesc;
    private String obhRespCode;
    private String obhRespDesc;
    private String signature;

    public TxnDetails() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRequestedUuid() {
        return requestedUuid;
    }

    public void setRequestedUuid(String requestedUuid) {
        this.requestedUuid = requestedUuid;
    }

    public long getClientTimestamp() {
        return clientTimestamp;
    }

    public void setClientTimestamp(long clientTimestamp) {
        this.clientTimestamp = clientTimestamp;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getInLogDate() {
        return inLogDate;
    }

    public void setInLogDate(String inLogDate) {
        this.inLogDate = inLogDate;
    }

    public String getOutLogDate() {
        return outLogDate;
    }

    public void setOutLogDate(String outLogDate) {
        this.outLogDate = outLogDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCoreRespCode() {
        return coreRespCode;
    }

    public void setCoreRespCode(String coreRespCode) {
        this.coreRespCode = coreRespCode;
    }

    public String getCoreRespDesc() {
        return coreRespDesc;
    }

    public void setCoreRespDesc(String coreRespDesc) {
        this.coreRespDesc = coreRespDesc;
    }

    public String getObhRespCode() {
        return obhRespCode;
    }

    public void setObhRespCode(String obhRespCode) {
        this.obhRespCode = obhRespCode;
    }

    public String getObhRespDesc() {
        return obhRespDesc;
    }

    public void setObhRespDesc(String obhRespDesc) {
        this.obhRespDesc = obhRespDesc;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
    
    
}
