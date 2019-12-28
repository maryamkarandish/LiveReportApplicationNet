/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livereportapplicationnet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import livereportapplicationnet.entities.AccountTotal;
import livereportapplicationnet.entities.Application;
import livereportapplicationnet.entities.Payesh;
import livereportapplicationnet.entities.Token;
import livereportapplicationnet.entities.TppTotal;
import livereportapplicationnet.entities.TransferDetail;
import livereportapplicationnet.entities.TxnDetails;

/**
 *
 * @author M_Karandish
 */
public class DatabaseServiceImp implements DatabaseService{
    
     private Connection connect = null;//DriverManager.getConnection("jdbc:postgresql://192.168.28.179:5433/ShahinLive", "postgres", "123");

      public Statement createConnection() throws SQLException{
        Statement s = null;
        connect = DriverManager.getConnection("jdbc:postgresql://localhost:5433/ShahinLive", "postgres", "123");
        s = connect.createStatement();
        return s;
        
    }
      
     public void SaveData(ArrayList<AccountTotal> accountTotals,long count)
    {
        
        Statement s = null;
        try {
            s = createConnection();          
            for(int i = 0; i<count;i++)
            {
                // SQL Insert
                String sql = "INSERT INTO obh_account "
                        + "(id,bank,customer_number,account_owner_name,account_type,branch,national_code,enabled,is_surgecharge_acc,customer_type, trustee_client) "
                        + "VALUES ('" + accountTotals.get(i).getId() + "','"
                        + accountTotals.get(i).getBank() + "','"
                        + accountTotals.get(i).getCustomerNo() + "'" + ",'"
                        + accountTotals.get(i).getAccountOwnerName() + "','"
                        + accountTotals.get(i).getAccountType() + "','"
                        + accountTotals.get(i).getBranch() + "','"
                        + accountTotals.get(i).getNationalCode() + "','"
                        + accountTotals.get(i).isEnabled() + "','"
                        + accountTotals.get(i).isSurgeChargeAcc() + "','"
                        + accountTotals.get(i).getCustomerType() + "','"
                        + accountTotals.get(i).getTrusteeClient() + "') ";
                System.out.println(sql.toUpperCase());
                s.execute(sql.toUpperCase());
            }
            JOptionPane.showMessageDialog(null,
                    "Import Data Successfully");
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        }
        try {
            if (s != null) {
                s.close();
                connect.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
      public void SaveDataTxn(ArrayList<TxnDetails> txnDetails,long count)
    {
        
        Statement s = null;
        try {
            s = createConnection();          
            for(int i = 0; i<count;i++)
            {
                // SQL Insert
                String sql = "INSERT INTO obh_txn_details "
                        + "(id,client_id,uuid,requested_uuid,client_timestamp,service_code,bank,national_code,"
                        + "account_no,in_log_date, out_log_date,state,token_id,latitude,longitude,core_resp_code,"
                        + "core_resp_desc,obh_resp_code,obh_resp_desc,signiture) "
                        + "VALUES ('" + txnDetails.get(i).getId() + "','"
                        + txnDetails.get(i).getClientId()+ "','"
                        + txnDetails.get(i).getUuid()+ "'" + ",'"
                        + txnDetails.get(i).getRequestedUuid()+ "','"
                        + txnDetails.get(i).getClientTimestamp()+ "','"
                        + txnDetails.get(i).getServiceCode()+ "','"
                        + txnDetails.get(i).getBank()+ "','"
                        + txnDetails.get(i).getNationalCode()+ "','"
                        + txnDetails.get(i).getAccountNo()+ "','"
                        + txnDetails.get(i).getInLogDate()+ "','"
                         + txnDetails.get(i).getOutLogDate()+ "','"
                        + txnDetails.get(i).getState()+ "','"
                        + txnDetails.get(i).getTokenId()+ "','"
                        + txnDetails.get(i).getLatitude()+ "','"
                        + txnDetails.get(i).getLongitude()+ "','"
                        + txnDetails.get(i).getCoreRespCode()+ "','"
                        + txnDetails.get(i).getCoreRespDesc()+ "','"
                        + txnDetails.get(i).getObhRespCode()+ "','"
                        + txnDetails.get(i).getObhRespDesc()+ "','"
                        + txnDetails.get(i).getSignature()+ "')";
                System.out.println(sql.toUpperCase());
                s.execute(sql.toUpperCase());
            }
            JOptionPane.showMessageDialog(null,
                    "Import Data Successfully");
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        }
        try {
            if (s != null) {
                s.close();
                connect.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
      
        public void SaveDataTransferDtl(ArrayList<TransferDetail> transdferDtl,long count)
    {
        
        Statement s = null;
        try {
            s = createConnection();          
            for(int i = 0; i<count;i++)
            {
                // SQL Insert
                String sql = "INSERT INTO obh_transfer_details "
                        + "(id,client_id,uuid,requested_uuid,client_timestamp,service_code,bank,national_code,"
                        + "account_no,in_log_date, out_log_date,state,token_id,latitude,longitude,core_resp_code,"
                        + "core_resp_desc,obh_resp_code,obh_resp_desc,signature,amount,to_account,to_bank,"
                        + "sign_count_limit,transfer_type,payment_id,document_id,destination_acc_name,reference_number) "
                        + "VALUES ('" + transdferDtl.get(i).getId() + "','"
                        + transdferDtl.get(i).getClientId()+ "','"
                        + transdferDtl.get(i).getUuid()+ "'" + ",'"
                        + transdferDtl.get(i).getRequestedUuid()+ "','"
                        + transdferDtl.get(i).getClientTimestamp()+ "','"
                        + transdferDtl.get(i).getServiceCode()+ "','"
                        + transdferDtl.get(i).getBank()+ "','"
                        + transdferDtl.get(i).getNationalCode()+ "','"
                        + transdferDtl.get(i).getAccountNo()+ "','"
                        + transdferDtl.get(i).getInLogDate()+ "','"
                        + transdferDtl.get(i).getOutLogDate()+ "','"
                        + transdferDtl.get(i).getState()+ "','"
                        + transdferDtl.get(i).getTokenId()+ "','"
                        + transdferDtl.get(i).getLatitude()+ "','"
                        + transdferDtl.get(i).getLongitude()+ "','"
                        + transdferDtl.get(i).getCoreRespCode()+ "','"
                        + transdferDtl.get(i).getCoreRespDesc()+ "','"
                        + transdferDtl.get(i).getObhRespCode()+ "','"
                        + transdferDtl.get(i).getObhRespDesc()+ "','"
                        + transdferDtl.get(i).getSignature()+ "','"
                        + transdferDtl.get(i).getAmount()+ "','"
                        + transdferDtl.get(i).getToAccount()+ "','"
                        + transdferDtl.get(i).getToBank()+ "','"
                        + transdferDtl.get(i).getSignCountLimit()+ "','"
                        + transdferDtl.get(i).getTransferType()+ "','"
                        + transdferDtl.get(i).getPaymentID()+ "','"
                        + transdferDtl.get(i).getDocumentID()+ "','"
                        + transdferDtl.get(i).getDestinationAccName()+ "','"
                        + transdferDtl.get(i).getReferenceNumber()+ "')";
                System.out.println(sql.toUpperCase());
                s.execute(sql.toUpperCase());
            }
            JOptionPane.showMessageDialog(null,
                    "Import Data Successfully");
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        }
        try {
            if (s != null) {
                s.close();
                connect.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
        
         public void SaveDataApplication(ArrayList<Application> applicationDtl,long count)
    {
        
        Statement s = null;
        try {
            s = createConnection();          
            for(int i = 0; i<count;i++)
            {
                // SQL Insert
                String sql = "INSERT INTO obh_application "
                        + "(id,client_user_name,client_id,app_type,application_name,create_at,grants,permissions,"
                        + "redirect_uri,activity_type, website,"
                        + "access_token_validity_seconds) "
                        + "VALUES ('" + applicationDtl.get(i).getId() + "','"
                        + applicationDtl.get(i).getClientUserName()+ "','"
                        + applicationDtl.get(i).getClientId()+ "'" + ",'"
                        + applicationDtl.get(i).getAppType()+ "','"
                        + applicationDtl.get(i).getApplicationName()+ "','"
                        + applicationDtl.get(i).getCreatedAt()+ "','"
                        + applicationDtl.get(i).getGrants()+ "','"
                        + applicationDtl.get(i).getPermissions()+ "','"
                        + applicationDtl.get(i).getRedirectUri()+ "','"
                        + applicationDtl.get(i).getActivityType()+ "','"
                        + applicationDtl.get(i).getWebsite()+ "','"
//                        + applicationDtl.get(i).getAccessTokenValiditySeconds()+ "','"                        
                        + applicationDtl.get(i).getAccessTokenValiditySeconds()+ "')";
                System.out.println(sql.toUpperCase());
                s.execute(sql.toUpperCase());
            }
            JOptionPane.showMessageDialog(null,
                    "Import Data Successfully");
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        }
        try {
            if (s != null) {
                s.close();
                connect.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
         
         public void SaveDataTppTotal(ArrayList<TppTotal> tppTotalDtl,long count)
    {
        
        Statement s = null;
        try {
            s = createConnection();          
            for(int i = 0; i<count;i++)
            {
                // SQL Insert
                String sql = "INSERT INTO obh_tpptotal "
                        + "(id,username,legal_persons_national_id,company_name,representative_national_code,"
                        + "representative_mobile_no,enabled,activated,"
                        + "representative_first_name,representative_last_name, address,"
                        + "phone_number,email,website,account_non_expired,credentials_non_expired,"
                        + "account_non_locked,indebtable,total_number_of_apps,"
                        + "total_daily_two_way_trn_amount,token_amount_limit,transfer_threshold,"
                        + "no_of_active_tokens_per_user,active_banks,"
                        + "category) "
                        + "VALUES ('" + tppTotalDtl.get(i).getId() + "','"
                        + tppTotalDtl.get(i).getUsername()+ "','"
                        + tppTotalDtl.get(i).getLegalPersonsNationalID()+ "'" + ",'"
                        + tppTotalDtl.get(i).getCompanyName()+ "','"
                        + tppTotalDtl.get(i).getRepresentativeNationalCode()+ "','"
                        + tppTotalDtl.get(i).getRepresentativeMobileNo()+ "','"
                        + tppTotalDtl.get(i).getEnabled()+ "','"
                        + tppTotalDtl.get(i).getActivated()+ "','"
                        + tppTotalDtl.get(i).getRepresentativeFirstName()+ "','"
                        + tppTotalDtl.get(i).getRepresentativeLastName()+ "','"
                        + tppTotalDtl.get(i).getAddress()+ "','"
                        + tppTotalDtl.get(i).getPhoneNumber()+ "','"
                        + tppTotalDtl.get(i).getEmail()+ "','"
                        + tppTotalDtl.get(i).getWebsite()+ "','"
                        + tppTotalDtl.get(i).getAccountNonExpired()+ "','"
                        + tppTotalDtl.get(i).getCredentialsNonExpired()+ "','"
                        + tppTotalDtl.get(i).getAccountNonLocked()+ "','"
                        + tppTotalDtl.get(i).getIndebtable()+ "','"
                        + tppTotalDtl.get(i).getTotalNumberOfApps()+ "','"
                        + tppTotalDtl.get(i).getTotalDailyTwoWayTrnAmount()+ "','"
                        + tppTotalDtl.get(i).getTokenAmountLimit()+ "','"
                        + tppTotalDtl.get(i).getTransferThreshold()+ "','"
                        + tppTotalDtl.get(i).getNoOfActiveTokensPerUser()+ "','"
                        + tppTotalDtl.get(i).getActiveBanks()+ "','"
                        + tppTotalDtl.get(i).getCategory()+ "')";
                System.out.println(sql.toUpperCase());
                s.execute(sql.toUpperCase());
            }
            JOptionPane.showMessageDialog(null,
                    "Import Data Successfully");
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        }
        try {
            if (s != null) {
                s.close();
                connect.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
         
           public void SaveDataToken(ArrayList<Token> tokenDtl,long count)
    {
        
        Statement s = null;
        try {
            s = createConnection();          
            for(int i = 0; i<count;i++)
            {
                // SQL Insert
                String sql = "INSERT INTO obh_token "
                        + "(id,token_id,app_name,username,client_id,"
                        + "creation_date,expires_in,bank,"
                        + "account,permissions, status,count,"
                        + "used_amount,revoked,"
                        + "amount) "
                        + "VALUES ('" + tokenDtl.get(i).getId() + "','"
                        + tokenDtl.get(i).getTokenId()+ "','"
                        + tokenDtl.get(i).getAppName()+ "'" + ",'"
                        + tokenDtl.get(i).getUsername()+ "','"
                        + tokenDtl.get(i).getClientId()+ "','"
                        + tokenDtl.get(i).getCreationDate()+ "','"
                        + tokenDtl.get(i).getExpiresIn()+ "','"
                        + tokenDtl.get(i).getBank()+ "','"
                        + tokenDtl.get(i).getAccount()+ "','"
                        + tokenDtl.get(i).getPermissions()+ "','"
                        + tokenDtl.get(i).getStatus()+ "','"
                        + tokenDtl.get(i).getCount()+ "','"
                        + tokenDtl.get(i).getUsedAmount()+ "','"
                        + tokenDtl.get(i).getRevoked()+ "','"
//                        + tokenDtl.get(i).getAmount()+ "','"                        
                        + tokenDtl.get(i).getAmount()+ "')";
                System.out.println(sql.toUpperCase());
                s.execute(sql.toUpperCase());
            }
            JOptionPane.showMessageDialog(null,
                    "Import Data Successfully");
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        }
        try {
            if (s != null) {
                s.close();
                connect.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
           
            public void SaveDataPayesh(ArrayList<Payesh> payeshDtl,long count)
    {
        
        Statement s = null;
        try {
            s = createConnection();          
            for(int i = 0; i<count;i++)
            {
                // SQL Insert
                String sql = "INSERT INTO obh_payesh "
                        + "(date,service,tpp,bank,total,"
                        + "successful,unsuccessful,user_error,"
                        + "system_error,core_error,"
                        + "card_error) "
                        + "VALUES ('" + payeshDtl.get(i).getDate()+ "','"
                        + payeshDtl.get(i).getService()+ "','"
                        + payeshDtl.get(i).getTpp()+ "'" + ",'"
                        + payeshDtl.get(i).getBank()+ "','"
                        + payeshDtl.get(i).getTotal()+ "','"
                        + payeshDtl.get(i).getSuccessful()+ "','"
                        + payeshDtl.get(i).getUnsuccessful()+ "','"
                        + payeshDtl.get(i).getUserError()+ "','"
                        + payeshDtl.get(i).getSystemError()+ "','"
                        + payeshDtl.get(i).getCoreError()+ "','"
                        + payeshDtl.get(i).getCardError()+ "')";
                System.out.println(sql.toUpperCase());
                s.execute(sql.toUpperCase());
            }
            JOptionPane.showMessageDialog(null,
                    "Import Data Successfully");
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        }
        try {
            if (s != null) {
                s.close();
                connect.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
      
       
    
}
