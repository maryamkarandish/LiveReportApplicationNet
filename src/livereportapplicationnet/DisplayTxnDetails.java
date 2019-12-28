/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livereportapplicationnet;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;
import livereportapplicationnet.ComboFilters.ApplicationName;
import livereportapplicationnet.ComboFilters.CompanyName;
import static livereportapplicationnet.FileUploadDestination.TOKEN;
import livereportapplicationnet.entities.AccountTotal;
import livereportapplicationnet.entities.Bank;
import livereportapplicationnet.entities.Token;
import livereportapplicationnet.entities.TxnDetails;
import utils.JalaliCalendar;
import static utils.JalaliCalendar.gregorianToJalali;

/**
 *
 * @author M_Karandish
 */
public class DisplayTxnDetails extends JFrame implements ActionListener{
    
     private JTable table;
     JComboBox comboCompnay;
     JComboBox comboAppNames ;
     JButton btnDropButton;
     Connection con;
     Statement st;
     private String filter;
     private ArrayList<TxnDetails> txnDetails = new ArrayList<TxnDetails>();

    public DisplayTxnDetails() throws ParseException {
        // Create Form Frame
        setSize(950, 600);
        setLocation(150, 50);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        getContentPane().setLayout(null);

        // Label Result
        final JLabel lblResult = new JLabel("گزارش تراکنش ها", JLabel.CENTER);
        lblResult.setBounds(150, 22, 370, 14);
        getContentPane().add(lblResult);
        
         // Label CompanyName
        final JLabel lblCompanyName = new JLabel("نام شرکت", JLabel.RIGHT);
        lblCompanyName.setBounds(800, 22, 50, 14);
        getContentPane().add(lblCompanyName);
        
         // Label ApplicationName
        final JLabel lblAppName = new JLabel("نام برنامه", JLabel.RIGHT);
        lblAppName.setBounds(800, 52, 50, 14);
        getContentPane().add(lblAppName);

        
        createCompanyCombo();
        
//        convertStringTimestampToDate();
        // Create Button Open JFileChooser
        JButton btnButton = new JButton("جستجو");
        btnButton.setBounds(268, 47, 135, 23);
        btnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                     table = new JTable();
                     getContentPane().add(table);

                    DefaultTableModel model = (DefaultTableModel)table.getModel();
                    createModel(model);
                    try {
                        fetchProcessing( model, lblResult);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        Logger.getLogger(DisplayTxnDetails.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                    Logger.getLogger(DisplayTxnDetails.class.getName()).log(Level.SEVERE, null, ex);
                }
                    JScrollPane scroll = new JScrollPane(table);
//                    scroll.setBounds(84, 98, 506, 79);
                     scroll.setBounds(84, 200, 800, 300);
                    getContentPane().add(scroll);
            }
        });
        getContentPane().add(btnButton);
    }
    
    public void createModel(DefaultTableModel model){
//            model.addColumn("id");
//            model.addColumn("appName");
//            model.addColumn("clientId");
//            model.addColumn("bank");
//            model.addColumn("account");
//            model.addColumn("status"); 
//            model.addColumn("amount");
            
             model.addColumn("شماره حساب");
            model.addColumn("بانک");
            model.addColumn("سرویس");
            model.addColumn("تاریخ ثبت");
            model.addColumn("کد منحصر به فرد سرویس");
            model.addColumn("شناسه کاربر"); 
            model.addColumn("ردیف");
    }
    
    public void fetchProcessing(DefaultTableModel model, JLabel lblResult) throws SQLException, ParseException{
        ArrayList<TxnDetails> result;
             try {
                  st = createConnection();
                                  
                 PreparedStatement pst =(PreparedStatement) con.prepareStatement("select obh_txn_details.*, banks.bank_description description from obh_txn_details,banks \n" +
                            "where banks.bank_code = obh_txn_details.bank\n" +
                            "and bank=?");
                    pst.setString(1,"BSI");
                    System.out.println(pst);
                    ResultSet rs = pst.executeQuery();
                        int row = 0;
                         
                        while (rs.next()) {
                            TxnDetails txnDtlrow = new TxnDetails();
                            txnDtlrow = parseColumnValueTxnDetails(rs);
                            model.addRow(new Object[0]);
//                            model.setValueAt(txnDtlrow.getId(), row, 0);
//                            model.setValueAt(txnDtlrow.getClientId(), row, 1);
//                            model.setValueAt(txnDtlrow.getUuid(), row, 2);
//                            model.setValueAt(txnDtlrow.getClientTimestamp(), row, 3);
//                            model.setValueAt(txnDtlrow.getServiceCode(), row, 4);
//                            model.setValueAt(txnDtlrow.getBank(), row, 5);
//                            model.setValueAt(txnDtlrow.getNationalCode(), row, 6);
                             model.setValueAt(txnDtlrow.getAccountNo(), row, 0);
                            model.setValueAt(txnDtlrow.getBank(), row, 1);
                            model.setValueAt(txnDtlrow.getServiceCode(), row, 2);
                            model.setValueAt(convertStringTimestampToDate(txnDtlrow.getClientTimestamp()), row, 3);
                            model.setValueAt(txnDtlrow.getUuid(), row, 4);
                            model.setValueAt(txnDtlrow.getClientId(), row, 5);
                            model.setValueAt(row, row, 6);
                            row++;
                            txnDetails.add(txnDtlrow);
                         }
                    } catch (SQLException ex) {
                        // TODO Auto-generated catch block
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        ex.printStackTrace();
                    }
              
                    lblResult.setText("TxnDetails reports");
    }
     
    private TxnDetails parseColumnValueTxnDetails(ResultSet rs){
        ArrayList<TxnDetails> txnDetails1 = new ArrayList<TxnDetails>();
//        String[] result = arr.split("\\|");
        TxnDetails txnDetailsMap = new TxnDetails();
        try{
            txnDetailsMap.setId(rs.getLong("id"));
            txnDetailsMap.setClientId(rs.getString("client_id"));
            txnDetailsMap.setUuid(rs.getString("uuid"));
//            txnDetailsMap.setClientTimestamp(rs.getString("client_timestamp"));
            txnDetailsMap.setClientTimestamp(Long.valueOf(rs.getString("client_timestamp")));
            txnDetailsMap.setServiceCode(rs.getString("service_code"));
            txnDetailsMap.setBank(rs.getString("description"));
            txnDetailsMap.setAccountNo(rs.getString("account_no")); //todo
//            txnDetailsMap.setAccountNo(result[8]);
//            txnDetailsMap.setInLogDate(result[9]);
//            txnDetailsMap.setOutLogDate(result[10]);
//            txnDetailsMap.setState(result[11]);
//            txnDetailsMap.setTokenId(result[12]);
//            txnDetailsMap.setLatitude(result[13]);
//            txnDetailsMap.setLongitude(result[14]);
//            txnDetailsMap.setCoreRespCode(result[15]);
//            txnDetailsMap.setCoreRespDesc(result[16]);
//            txnDetailsMap.setObhRespCode(result[17]);
//            txnDetailsMap.setObhRespDesc(result[18]);
//            txnDetailsMap.setSignature(result[19]);
//            txnDetailsMap.setSignature(result[19]);
                  

        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        }
        return  txnDetailsMap;
        
    }
    
    public Statement createConnection() throws SQLException{
       con = DriverManager.getConnection("jdbc:postgresql://192.168.28.179:5433/ShahinLive", "postgres", "123");
        st = con.createStatement();
        return st;
    }
    
    public void createFilters(){
        
    }
    
    public void createCompanyCombo(){
         try {

             String coName;
             String coId;
             
             String appName;
             String appId;
             
            DefaultComboBoxModel categoryItems = new DefaultComboBoxModel();
            st = createConnection();
            PreparedStatement st =null;

            st = (PreparedStatement) con.prepareStatement("select company_name , username from obh_tpptotal\n" +
                                    "where company_name in (select tpp.company_name from obh_tpptotal tpp\n" +
                                    "right join obh_application on obh_application.client_user_name = tpp.username)\n" +
                                    "and  obh_tpptotal.company_name <> '' "); 
            
            System.out.println(st);
            ResultSet rs = st.executeQuery();

            Vector v = new Vector();

            while (rs.next()) {

                coName = rs.getString("company_name");
                coId = rs.getString("username");
                v.add(coName);
                categoryItems.addElement(new CompanyName(coName,coId));

            }

            comboCompnay = new JComboBox(categoryItems);
            comboCompnay.setRenderer( new DisplayTxnDetails.ItemRenderer() );
            comboCompnay.setBounds(600, 22, 150, 20);
            add(comboCompnay);
            CompanyName coSelected= (CompanyName) comboCompnay.getSelectedItem();
            System.out.println(coSelected.getCompanyName());
            filter = coSelected.getCompanyId();
            comboCompnay.addActionListener(this);
              
            st.close();
            rs.close();

        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
    }
    
     class ItemRenderer extends BasicComboBoxRenderer
    {
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus)
        {
            super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);

            if (value != null)
            {
                CompanyName item = (CompanyName) value;
                setText( item.getCompanyName().toUpperCase() );
            }

            if (index == -1)
            {
                CompanyName item = (CompanyName) value;
                setText( "" + item.getCompanyName());
            }


            return this;
        }
    }
     
      class AppItemRenderer extends  BasicComboBoxRenderer
    {
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus)
        {
                super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);

                if (value != null)
                {
                    ApplicationName item = (ApplicationName) value;
                    setText( item.getAppName().toUpperCase() );
                }

//                if (index == -1)
//                {
//                    ApplicationName item = (ApplicationName) value;
//                     setText( "" + item.getAppName());
//                }
      
            return this;
        }
    }
       
       public void actionPerformed(ActionEvent  e) {
         
        if(e.getSource()==comboCompnay)
        {
         String appName;
         String appId;
         ArrayList<String> appNameList = new ArrayList<String>();
         CompanyName cname = (CompanyName)comboCompnay.getSelectedItem();
         DefaultComboBoxModel categoryAppItems = new DefaultComboBoxModel();
             PreparedStatement st2;
          try {
              st2 = (PreparedStatement) con.prepareStatement("select application_name , client_id from obh_application\n" +
                      "where client_user_name = ? ");

             st2.setString(1,cname.getCompanyId());
             System.out.println(st2);

             ResultSet rs2 = st2.executeQuery();

             if(comboAppNames!=null){
                 comboAppNames.removeAllItems();
                 comboAppNames.setModel(categoryAppItems);
             }
          
              while (rs2.next()) {

                 appName = rs2.getString("application_name");
                 appId = rs2.getString("client_id");  

                 categoryAppItems.addElement(new ApplicationName(appName,appId));

             }
             if(categoryAppItems.getSize()<=0){
                 JOptionPane.showMessageDialog(null, "هیچ برنامه ای برای شرکت "+ cname.getCompanyName() + "یافت نشد");                  
//                 return;
             }
              if(comboAppNames!=null)
              {
                  comboAppNames.setModel(categoryAppItems);
              } else{
                 comboAppNames = new JComboBox(categoryAppItems);
              }  
              
             comboAppNames.setRenderer( new DisplayTxnDetails.AppItemRenderer());
             comboAppNames.revalidate();


             comboAppNames.setBounds(600, 52, 150, 20);
             add(comboAppNames);
             ApplicationName coAppSelected= (ApplicationName) comboAppNames.getSelectedItem();
             System.out.println(coAppSelected.getAppName());

             rs2.close();
             st2.close();
              } catch (SQLException ex) {
              JOptionPane.showMessageDialog(null, ex.getMessage());
              Logger.getLogger(DisplayTxnDetails.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
    } 
   public String convertStringTimestampToDate(long timestamp) throws ParseException{
   

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS",Locale.US);

//        long timestamp = 1575692677163l; //Example -> in ms
//        Date d = new Date(timestamp );
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        JalaliCalendar.YearMonthDate yearMonthDate = new JalaliCalendar.YearMonthDate(calendar.get(YEAR), calendar.get(MONTH), calendar.get(DATE));
        yearMonthDate = gregorianToJalali(yearMonthDate);
        System.out.println("date: " + yearMonthDate);
        return yearMonthDate.toString();


   } 
     
    
}
