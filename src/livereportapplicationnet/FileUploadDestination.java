package livereportapplicationnet;
import livereportapplicationnet.entities.AccountTotal;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import livereportapplicationnet.entities.Application;
import livereportapplicationnet.entities.Payesh;
import livereportapplicationnet.entities.Token;
import livereportapplicationnet.entities.TppTotal;
import livereportapplicationnet.entities.TransferDetail;
import livereportapplicationnet.entities.TxnDetails;

public class FileUploadDestination extends JFrame {
        
    public static final String TXN_DETAILS = "TxnDetails";
    public static final String TRANSFER_DETAILS = "TransferDetails";
    public static final String TPP_TOTAL = "TPPTotal";
    public static final String APPLICATION_TOTAL = "ApplicationTotal";
    public static final String ACCOUNT_TOTAL = "AccountTotal";
    public static final String TOKEN = "Token";
    public static final String PAYESH = "PAYESH";

    private Connection connect = null;//DriverManager.getConnection("jdbc:postgresql://192.168.28.179:5433/ShahinLive", "postgres", "123");

    private JTable table;
    ArrayList<AccountTotal> accountTotals = new ArrayList<AccountTotal>();
    ArrayList<TxnDetails> txnDetails = new ArrayList<TxnDetails>();
    ArrayList<TransferDetail> trasferDetails = new ArrayList<TransferDetail>();
    ArrayList<Application> applications = new ArrayList<Application>();
    ArrayList<TppTotal> tppTotals = new ArrayList<TppTotal>();
    ArrayList<Token> tokens = new ArrayList<Token>();
    ArrayList<Payesh> payesh = new ArrayList<Payesh>();

    public FileUploadDestination(){

        
        // Create Form Frame
        setSize(668, 345);
        setLocation(500, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // Label Result
        final JLabel lblResult = new JLabel("Result", JLabel.CENTER);
        lblResult.setBounds(150, 22, 370, 14);
        getContentPane().add(lblResult);

        // Create Button Open JFileChooser
        JButton btnButton = new JButton("Open File Chooser");
        btnButton.setBounds(268, 47, 135, 23);
        btnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter(
                        "Text/CSV file", "txt", "csv");
                fileopen.addChoosableFileFilter(filter);
                int ret = fileopen.showDialog(null, "Choose file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                // Read Text file
                    File file = fileopen.getSelectedFile();
                     table = new JTable();
                     getContentPane().add(table);

                    DefaultTableModel model = (DefaultTableModel)table.getModel();
                    createModel(file.getName(), model);
                    fileProcessing(file, model, lblResult);
                    JScrollPane scroll = new JScrollPane(table);
                    scroll.setBounds(84, 98, 506, 79);
                    getContentPane().add(scroll);
                }
            }
        });
        getContentPane().add(btnButton);
        // Button Save
        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DatabaseServiceImp service = null;
                if (accountTotals!=null && accountTotals.size()>0){
                    service = new DatabaseServiceImp();
                    service.SaveData(accountTotals, table.getRowCount()); //save data
                }else if(txnDetails!=null && txnDetails.size()>0){
                     service = new DatabaseServiceImp();
                    service.SaveDataTxn(txnDetails, table.getRowCount()); //save data
                }else if(trasferDetails!=null && trasferDetails.size()>0){
                    service = new DatabaseServiceImp();
                    service.SaveDataTransferDtl(trasferDetails, table.getRowCount()); //save data

                }else if(applications!=null && applications.size()>0){
                    service = new DatabaseServiceImp();
                    service.SaveDataApplication(applications, table.getRowCount()); //save data
                    

                }else if(tppTotals!=null && tppTotals.size()>0){
                    service = new DatabaseServiceImp();
                    service.SaveDataTppTotal(tppTotals, table.getRowCount()); //save data

                    
                }else if(tokens!=null && tokens.size()>0){
                    service = new DatabaseServiceImp();
                    service.SaveDataToken(tokens, table.getRowCount()); //save data

                }else if(payesh!=null && payesh.size()>0){
                    service = new DatabaseServiceImp();
                    service.SaveDataPayesh(payesh, table.getRowCount()); //save data
                }
               
            }
        });
        btnSave.setBounds(292, 228, 89, 23);
        getContentPane().add(btnSave);

    }

    private AccountTotal parseColumnValue(String[] arr){
        ArrayList<AccountTotal> accountTotals = new ArrayList<AccountTotal>();
        String[] result = arr[0].split("\\|");
        AccountTotal accountTotalMap = new AccountTotal();
        try{
            accountTotalMap.setId(Integer.valueOf(result[0]));
            accountTotalMap.setBank(result[1]);
            accountTotalMap.setCustomerNo(result[2]);
            accountTotalMap.setAccountOwnerName(result[3]);
            accountTotalMap.setAccountType(result[4]);
            accountTotalMap.setBranch(result[5]);
            accountTotalMap.setNationalCode(result[6]);
            accountTotalMap.setCreationTime(result[7]); //todo
            accountTotalMap.setEnabled(result[8].equals("1")?true:false);
            accountTotalMap.setSurgeChargeAcc(result[9].equals("1")?true:false);
            accountTotalMap.setCustomerType(result[10].equals("01")?"individual":"organization");
            accountTotalMap.setTrusteeClient(result[11]);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  accountTotalMap;
    }    
    private TxnDetails parseColumnValueTxnDetails(String arr){
        ArrayList<TxnDetails> txnDetails1 = new ArrayList<TxnDetails>();
        String[] result = arr.split("\\|");
        TxnDetails txnDetailsMap = new TxnDetails();
        try{
            txnDetailsMap.setId(Integer.valueOf(result[0]));
            txnDetailsMap.setClientId(result[1]);
            txnDetailsMap.setUuid(result[2]);
            txnDetailsMap.setRequestedUuid(result[3]);
            txnDetailsMap.setClientTimestamp(Long.valueOf(result[4]));
            txnDetailsMap.setServiceCode(result[5]);
            txnDetailsMap.setBank(result[6]);
            txnDetailsMap.setNationalCode(result[7]); //todo
            txnDetailsMap.setAccountNo(result[8]);
            txnDetailsMap.setInLogDate(result[9]);
            txnDetailsMap.setOutLogDate(result[10]);
            txnDetailsMap.setState(result[11]);
            txnDetailsMap.setTokenId(result[12]);
            txnDetailsMap.setLatitude(result[13]);
            txnDetailsMap.setLongitude(result[14]);
            txnDetailsMap.setCoreRespCode(result[15]);
            txnDetailsMap.setCoreRespDesc(result[16]);
            txnDetailsMap.setObhRespCode(result[17]);
            txnDetailsMap.setObhRespDesc(result[18]);
            txnDetailsMap.setSignature(result[19]);
            txnDetailsMap.setSignature(result[19]);
                   

        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        }
        return  txnDetailsMap;
    }
    private TransferDetail parseColumnValueTransferDetails(String arr){
        ArrayList<TransferDetail> transDtl = new ArrayList<TransferDetail>();
        String[] result = arr.split("\\|");
        TransferDetail transDtlMap = new TransferDetail();
        try{
            transDtlMap.setId(Integer.valueOf(result[0]));
            transDtlMap.setClientId(result[1]);
            transDtlMap.setUuid(result[2]);
            transDtlMap.setRequestedUuid(result[3]);
            transDtlMap.setClientTimestamp(result[4]);
            transDtlMap.setServiceCode(result[5]);
            transDtlMap.setBank(result[6]);
            transDtlMap.setNationalCode(result[7]); //todo
            transDtlMap.setAccountNo(result[8]);
            transDtlMap.setInLogDate(result[9]);
            transDtlMap.setOutLogDate(result[10]);
            transDtlMap.setState(result[11]);
            transDtlMap.setTokenId(result[12]);
            transDtlMap.setLatitude(result[13]);
            transDtlMap.setLongitude(result[14]);
            transDtlMap.setCoreRespCode(result[15]);
            transDtlMap.setCoreRespDesc(result[16]);
            transDtlMap.setObhRespCode(result[17]);
            transDtlMap.setObhRespDesc(result[18]);
            transDtlMap.setSignature(result[19]); 
            transDtlMap.setAmount(result[20]); 
            transDtlMap.setToAccount(result[21]); 
            transDtlMap.setToBank(result[22]); 
            transDtlMap.setSignCountLimit(result[23]); 
            transDtlMap.setTransferType(result[24]); 
            transDtlMap.setPaymentID(result[25]); 
            transDtlMap.setDocumentID(result[26]); 
            transDtlMap.setDestinationAccName(result[27]); 
            transDtlMap.setReferenceNumber(result[28]);          

        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        }
        return  transDtlMap;
    }
    private Application parseColumnValueApplication(String arr){
        ArrayList<Application> apps = new ArrayList<Application>();
        String[] result = arr.split("\\|");
        Application applicationMap = new Application();
        try{
            applicationMap.setId(Integer.valueOf(result[0]));
            applicationMap.setClientUserName(result[1]);
            applicationMap.setClientId(result[2]);
            applicationMap.setAppType(result[3]);
            applicationMap.setApplicationName(result[4]);
            applicationMap.setCreatedAt(result[5]);
            applicationMap.setGrants(result[6]);
            applicationMap.setPermissions(result[7]); //todo
            applicationMap.setRedirectUri(result[8]);
            applicationMap.setActivityType(result[9]);
            applicationMap.setWebsite(result[10]);
            applicationMap.setAccessTokenValiditySeconds(result[11]);
                             

        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        }
        return  applicationMap;
    }
    private TppTotal parseColumnValueTppTotal(String arr){
        ArrayList<TppTotal> tpptotal = new ArrayList<TppTotal>();
        String[] result = arr.split("\\|");
        TppTotal tppTotalMap = new TppTotal();
        try{
            tppTotalMap.setId(Integer.valueOf(result[0]));
            tppTotalMap.setUsername(result[1]);
            tppTotalMap.setLegalPersonsNationalID(result[2]);
            tppTotalMap.setCompanyName(result[3]);
            tppTotalMap.setRepresentativeNationalCode(result[4]);
            tppTotalMap.setRepresentativeMobileNo(result[5]);
            tppTotalMap.setEnabled(result[6]);
            tppTotalMap.setActivated(result[7]); //todo
            tppTotalMap.setRepresentativeFirstName(result[8]);
            tppTotalMap.setRepresentativeLastName(result[9]);
            tppTotalMap.setAddress(result[10]);
            tppTotalMap.setPhoneNumber(result[11]);            
            tppTotalMap.setEmail(result[12]);
            tppTotalMap.setWebsite( result[13]);
            tppTotalMap.setAccountNonExpired(result[14]);
            tppTotalMap.setCredentialsNonExpired(result[15]);
            tppTotalMap.setAccountNonLocked(result[16]);
            tppTotalMap.setIndebtable(result[17]);
            tppTotalMap.setTotalNumberOfApps(result[18]);
            tppTotalMap.setTotalDailyTwoWayTrnAmount(result[19]);
            tppTotalMap.setTokenAmountLimit(result[20]);
            tppTotalMap.setTransferThreshold(result[21]);
            tppTotalMap.setNoOfActiveTokensPerUser(result[22]);
            tppTotalMap.setActiveBanks(result[23]);
            tppTotalMap.setCategory(result[24]);
            tppTotalMap.setApplications(result[25]);
            
           
            
            
                             

        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        }
        return  tppTotalMap;
    }
    private Token parseColumnValueToken(String arr){
        ArrayList<Token> tpptotal = new ArrayList<Token>();
        String[] result = arr.split("\\|");
        Token tokenMap = new Token();
        try{
            tokenMap.setId(Integer.valueOf(result[0]));
            tokenMap.setTokenId(result[1]);
            tokenMap.setAppName(result[2]);
            tokenMap.setUsername(result[3]);
            tokenMap.setClientId(result[4]);
            tokenMap.setCreationDate(result[5]);
            tokenMap.setExpiresIn(result[6]);
            tokenMap.setBank(result[7]); //todo
            tokenMap.setAccount(result[8]);
            tokenMap.setPermissions(result[9]);
            tokenMap.setStatus(result[10]);
            tokenMap.setCount(result[11]);
            tokenMap.setUsedAmount(result[12]);
            tokenMap.setRevoked(result[13]);
            tokenMap.setAmount(result[14]);
//            tokenMap.setAuthenticationId(result[15]);          
        
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        }
        return  tokenMap;
    }    
    private Payesh parseColumnValuePayesh(String arr){
        ArrayList<Payesh> tpptotal = new ArrayList<Payesh>();
        String[] result = arr.split("\\|");
        Payesh payeshMap = new Payesh();
        try{
            payeshMap.setDate(result[0]);
            payeshMap.setService(result[1]);
            payeshMap.setTpp(result[2]);
            payeshMap.setBank(result[3]);
            payeshMap.setTotal(result[4]);
            payeshMap.setSuccessful(result[5]);
            payeshMap.setUnsuccessful(result[6]);
            payeshMap.setUserError(result[7]); //todo
            payeshMap.setSystemError(result[8]);
            payeshMap.setCoreError(result[9]);
            payeshMap.setCardError(result[10]);
                
        
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        }
        return  payeshMap;
    }
    
    public void fileProcessing(File file,DefaultTableModel model, JLabel lblResult){
        String filename = file.getName();
        ArrayList<AccountTotal> result;
        System.out.println("file name:" + filename);
        if (filename.contains(TOKEN)){
            System.out.println("TOKEN");
             try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;
                        int row = 0;
                        while ((line = br.readLine()) != null) {
                            Token tokenrow = new Token();
                            tokenrow = parseColumnValueToken(line);
                            model.addRow(new Object[0]);
                            model.setValueAt(tokenrow.getId(), row, 0);
                            model.setValueAt(tokenrow.getAppName(), row, 1);
                            model.setValueAt(tokenrow.getClientId(), row, 2);
                            model.setValueAt(tokenrow.getBank(), row, 3);
                            model.setValueAt(tokenrow.getAccount(), row, 4);
                            model.setValueAt(tokenrow.getStatus(), row, 5);
                            model.setValueAt(tokenrow.getAmount(), row, 6);
                            row++;
                            tokens.add(tokenrow);
                         }
                        br.close();
                    } catch (IOException ex) {
                        // TODO Auto-generated catch block
                        ex.printStackTrace();
                    }
              
                    lblResult.setText(file.getName().toString());
                    
        }else if(filename.contains(TRANSFER_DETAILS)){
            System.out.println("TRANSFER_DETAILS");
             try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;
                        int row = 0;
                        while ((line = br.readLine()) != null) {
                            TransferDetail transDtlrow = new TransferDetail();
                            transDtlrow = parseColumnValueTransferDetails(line);
                            model.addRow(new Object[0]);
                            model.setValueAt(transDtlrow.getId(), row, 0);
                            model.setValueAt(transDtlrow.getClientId(), row, 1);
                            model.setValueAt(transDtlrow.getBank(), row, 2);
                            model.setValueAt(transDtlrow.getUuid(), row, 3);
                            model.setValueAt(transDtlrow.getRequestedUuid(), row, 4);
                            model.setValueAt(transDtlrow.getServiceCode(), row, 5);
                            model.setValueAt(transDtlrow.getNationalCode(), row, 6);
                            model.setValueAt(transDtlrow.getAccountNo(), row, 7);
                            model.setValueAt(transDtlrow.getState(), row, 8);
                            model.setValueAt(transDtlrow.getObhRespDesc(), row, 9);
                            row++;
                            trasferDetails.add(transDtlrow);
                         }
                        br.close();
                    } catch (IOException ex) {
                        // TODO Auto-generated catch block
                        ex.printStackTrace();
                    }              
                    lblResult.setText(file.getName().toString());
        }else if(filename.contains(TPP_TOTAL)){
            System.out.println("TPP_TOTAL");
            try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;
                        int row = 0;
                        while ((line = br.readLine()) != null) {
                            TppTotal accountTotalrow = new TppTotal();
                            accountTotalrow = parseColumnValueTppTotal(line);
                            model.addRow(new Object[0]);
                            model.setValueAt(accountTotalrow.getId(), row, 0);
                            model.setValueAt(accountTotalrow.getUsername(), row, 1);
                            model.setValueAt(accountTotalrow.getCompanyName(), row, 2);
                            model.setValueAt(accountTotalrow.getActiveBanks(), row, 3);
                            model.setValueAt(accountTotalrow.getTotalNumberOfApps(), row, 4);
                            model.setValueAt(accountTotalrow.getWebsite(), row, 5);
                            row++;
                            tppTotals.add(accountTotalrow);
                         }
                        br.close();
                    } catch (IOException ex) {
                        // TODO Auto-generated catch block
                        ex.printStackTrace();
                    }
              
                    lblResult.setText(file.getName().toString());
                    
            
            
        }else if(filename.contains(APPLICATION_TOTAL)){
            System.out.println("APPLICATION_TOTAL");
             try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;
                        int row = 0;
                        while ((line = br.readLine()) != null) {
                            Application appTotalrow = new Application();
                            appTotalrow = parseColumnValueApplication(line);
                            model.addRow(new Object[0]);
                            model.setValueAt(appTotalrow.getId(), row, 0);
                            model.setValueAt(appTotalrow.getClientUserName(), row, 1);
                            model.setValueAt(appTotalrow.getClientId(), row, 2);
                            model.setValueAt(appTotalrow.getAppType(), row, 3);
                            model.setValueAt(appTotalrow.getApplicationName(), row, 4);
                            model.setValueAt(appTotalrow.getActivityType(), row, 5);
                            row++;
                            applications.add(appTotalrow);
                         }
                        br.close();
                    } catch (IOException ex) {
                        // TODO Auto-generated catch block
                        ex.printStackTrace();
                    }
              
                    lblResult.setText(file.getName().toString());
            
        }else if(filename.contains(ACCOUNT_TOTAL)){
            System.out.println("ACCOUNT_TOTAL");
              try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;
                        int row = 0;
                        while ((line = br.readLine()) != null) {
                            String[] arr = line.split(",");
                            AccountTotal accountTotalrow = new AccountTotal();
                            accountTotalrow = parseColumnValue(arr);
                            model.addRow(new Object[0]);
                            model.setValueAt(accountTotalrow.getId(), row, 0);
                            model.setValueAt(accountTotalrow.getBank(), row, 1);
                            model.setValueAt(accountTotalrow.getAccountOwnerName(), row, 2);
                            model.setValueAt(accountTotalrow.getCustomerNo(), row, 3);
                            model.setValueAt(accountTotalrow.getCustomerType(), row, 4);
                            model.setValueAt(accountTotalrow.getNationalCode(), row, 5);
                            row++;
                            accountTotals.add(accountTotalrow);
                         }
                        br.close();
                    } catch (IOException ex) {
                        // TODO Auto-generated catch block
                        ex.printStackTrace();
                    }
              
                    lblResult.setText(file.getName().toString());
                    
            
        }else if(filename.contains(PAYESH)){
            System.out.println("PAYESH");
             try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;
                        int row = 0;
                        while ((line = br.readLine()) != null) {
                            Payesh payeshrow = new Payesh();
                            payeshrow = parseColumnValuePayesh(line);
                            model.addRow(new Object[0]);
                            model.setValueAt(payeshrow.getDate(), row, 0);
                            model.setValueAt(payeshrow.getService(), row, 1);
                            model.setValueAt(payeshrow.getTpp(), row, 2);
                            model.setValueAt(payeshrow.getBank(), row, 3);
                            model.setValueAt(payeshrow.getTotal(), row, 4);
                            model.setValueAt(payeshrow.getSuccessful(), row, 5);
                            model.setValueAt(payeshrow.getUnsuccessful(), row, 6);
                            row++;
                            payesh.add(payeshrow);
                         }
                        br.close();
                    } catch (IOException ex) {
                        // TODO Auto-generated catch block
                        ex.printStackTrace();
                    }
              
                    lblResult.setText(file.getName().toString());
            
        }else if(filename.contains(TXN_DETAILS)){
            System.out.println("TXN_DETAILS");
            try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;
                        int row = 0;
                        while ((line = br.readLine()) != null) {
                            //String[] arr = line.split("");
                            TxnDetails txnDtlrow = new TxnDetails();
                            txnDtlrow = parseColumnValueTxnDetails(line);
//                            model.addRow(new Object[]{String.valueOf(txnDtlrow.getId()),txnDtlrow.getBank(),txnDtlrow.getClientId()});
                            model.addRow(new Object[0]);
                            model.setValueAt(String.valueOf(txnDtlrow.getId()), row, 0);
                            model.setValueAt(txnDtlrow.getBank(), row, 1);
                            model.setValueAt(txnDtlrow.getClientId(), row, 2);
                            model.setValueAt(txnDtlrow.getUuid(), row, 3);
                            model.setValueAt(txnDtlrow.getRequestedUuid(), row, 4);
                            model.setValueAt(txnDtlrow.getServiceCode(), row, 5);
                            model.setValueAt(txnDtlrow.getNationalCode(), row, 6);
                            model.setValueAt(txnDtlrow.getAccountNo(), row, 7);
                            model.setValueAt(txnDtlrow.getState(), row, 8);
                            model.setValueAt(txnDtlrow.getObhRespDesc(), row, 9);
                            row++;
                            txnDetails.add(txnDtlrow);
                         }
                        br.close();
                    } catch (IOException ex) {
                        // TODO Auto-generated catch block
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        ex.printStackTrace();
                    }
              
                    lblResult.setText(file.getName().toString());
            
        }
    }
    
    public void createModel(String filename,DefaultTableModel model){
         if (filename.contains(TOKEN)){
            System.out.println("token");
            model.addColumn("id");
            model.addColumn("appName");
            model.addColumn("clientId");
            model.addColumn("bank");
            model.addColumn("account");
            model.addColumn("status"); 
            model.addColumn("amount");
            
        }else if(filename.contains(TRANSFER_DETAILS)){
            System.out.println("TRANSFER_DETAILS"); 
            model.addColumn("id");
            model.addColumn("bank");
            model.addColumn("clientId");
            model.addColumn("uuid");
            model.addColumn("requestedUuid");
            model.addColumn("serviceCode");
            model.addColumn("nationalCode");
            model.addColumn("accountNo");
            model.addColumn("state");
            model.addColumn("desc");

        }else if(filename.contains(TPP_TOTAL)){
            System.out.println("TPP_TOTAL");
            model.addColumn("id");
            model.addColumn("username");
            model.addColumn("companyName");
            model.addColumn("activeBanks");
            model.addColumn("totalNumberOfApps");
            model.addColumn("website"); 
            
        }else if(filename.contains(APPLICATION_TOTAL)){
            System.out.println("APPLICATION_TOTAL");
            model.addColumn("id");
            model.addColumn("clientUserName");
            model.addColumn("clientId");
            model.addColumn("appType");
            model.addColumn("applicationName");
            model.addColumn("activityType");   
            
        }else if(filename.contains(ACCOUNT_TOTAL)){
            System.out.println("ACCOUNT_TOTAL");
            model.addColumn("id");
            model.addColumn("bank");
            model.addColumn("accountOwnerName");
            model.addColumn("customerNo");
            model.addColumn("customerType");
            model.addColumn("nationalCode"); 
            
        }else if(filename.contains(PAYESH)){
            System.out.println("PAYESH");
            model.addColumn("date");
            model.addColumn("service");
            model.addColumn("tpp");
            model.addColumn("bank");
            model.addColumn("total");
            model.addColumn("successful"); 
            model.addColumn("unsuccessful");
            
        }else if(filename.contains(TXN_DETAILS)){
            System.out.println("TXN_DETAILS");
            model.addColumn("id");
            model.addColumn("bank");
            model.addColumn("clientId");
            model.addColumn("uuid");
            model.addColumn("requestedUuid");
            model.addColumn("serviceCode");
            model.addColumn("nationalCode");
            model.addColumn("accountNo");
            model.addColumn("state");
            model.addColumn("desc");
            
        }
    }
    


}
