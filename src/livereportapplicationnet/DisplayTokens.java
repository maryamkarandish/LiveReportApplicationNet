/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livereportapplicationnet;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;
import livereportapplicationnet.ComboFilters.ApplicationName;
import livereportapplicationnet.ComboFilters.CompanyName;
import livereportapplicationnet.entities.Token;
import livereportapplicationnet.entities.TppTotal;

/**
 *
 * @author M_Karandish
 */
public class DisplayTokens extends JFrame implements ActionListener{
     private JTable table;
     JComboBox comboCompnay;
     JComboBox comboAppNames ;
     Connection con;
     Statement st;
     private String filter;
     private ArrayList<Token> tokens = new ArrayList<Token>();

    public DisplayTokens() {
        // Create Form Frame
        setSize(950, 600);
        setLocation(150, 50);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        getContentPane().setLayout(null);

        // Label Result
        final JLabel lblResult = new JLabel("گزارش نهادهای ثالث ثبت شده", JLabel.CENTER);
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
                        Logger.getLogger(DisplayTxnDetails.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    JScrollPane scroll = new JScrollPane(table);
//                    scroll.setBounds(84, 98, 506, 79);
                     scroll.setBounds(84, 200, 800, 300);
                    getContentPane().add(scroll);
//                }
            }
        });
        getContentPane().add(btnButton);
    }
    
    public void createModel(DefaultTableModel model){
            model.addColumn("TokenId");
            model.addColumn("ApplicationName");
            model.addColumn("Username");
            model.addColumn("clientId");
            model.addColumn("bank");
            model.addColumn("amount"); 
            model.addColumn("account");
    }
    
    public void fetchProcessing(DefaultTableModel model, JLabel lblResult) throws SQLException{

        ArrayList<Token> result;
             try {
                  st = createConnection();
                                  
                 PreparedStatement pst =(PreparedStatement) con.prepareStatement("select obh_token.* from obh_token");
//                    pst.setString(1,"BSI");
                    System.out.println(pst);
                    ResultSet rs = pst.executeQuery();
                        int row = 0;
                        while (rs.next()) {
                            Token tokenrow = new Token();
                            tokenrow = parseColumnValue(rs);
                            model.addRow(new Object[0]);
                            model.setValueAt(tokenrow.getTokenId(), row, 0);
                            model.setValueAt(tokenrow.getAppName(), row, 1);
                            model.setValueAt(tokenrow.getUsername(), row, 2);
                            model.setValueAt(tokenrow.getClientId(), row, 3);
                            model.setValueAt(tokenrow.getBank(), row, 4);
                            model.setValueAt(tokenrow.getAmount(), row, 5);
                            model.setValueAt(tokenrow.getAccount(), row, 6);
                            row++;
                            tokens.add(tokenrow);
                         }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }             
    }
     
    private Token parseColumnValue(ResultSet rs){
        ArrayList<Token> token1ist = new ArrayList<Token>();
        Token tokenMap = new Token();
        try{
            tokenMap.setTokenId(rs.getString("token_id"));
            tokenMap.setAppName(rs.getString("app_name"));
            tokenMap.setUsername(rs.getString("username"));
            tokenMap.setClientId(rs.getString("client_id"));
            tokenMap.setBank(rs.getString("bank"));
            tokenMap.setAmount(rs.getString("amount"));
            tokenMap.setAccount(rs.getString("account"));
//            tppTotalsMap.setNationalCode(rs.getString("account_no")); //todo
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
        return  tokenMap;
        
    }
    
    public Statement createConnection() throws SQLException{
       con = DriverManager.getConnection("jdbc:postgresql://192.168.28.179:5433/ShahinLive", "postgres", "123");
        st = con.createStatement();
        return st;
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
            if (filter==null || filter.equals("")){
                st = (PreparedStatement) con.prepareStatement("select company_name , username from obh_tpptotal\n" +
                                        "where company_name <> '' "); 
            }else if(!filter.isEmpty()){
                st = (PreparedStatement) con.prepareStatement("select company_name , username from obh_tpptotal\n" +
                                        "where company_name <> '' and username=? ");
                 st.setString(1,filter);
            }
            
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
            comboCompnay.setRenderer( new DisplayTokens.ItemRenderer() );
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

            if (index == -1)
            {
                ApplicationName item = (ApplicationName) value;
                setText( "" + item!=null && item.getAppName()!=null?item.getAppName():" ");
            }


            return this;
        }
    }
      
       public void actionPerformed(ActionEvent e) {
         
//        if(comboAppNames!=null){
//            comboAppNames.removeAllItems();
//        }
           JComboBox cb = (JComboBox)e.getSource();
        String appName;
        String appId;
        ArrayList<String> appNameList = new ArrayList<String>();
        CompanyName cname = (CompanyName)cb.getSelectedItem();
        DefaultComboBoxModel categoryAppItems = new DefaultComboBoxModel();
            PreparedStatement st2;
         try {
             st2 = (PreparedStatement) con.prepareStatement("select application_name , client_id from obh_application\n" +
                     "where client_user_name = ? ");
        
            st2.setString(1,cname.getCompanyId());
            System.out.println(st2);
            
            ResultSet rs2 = st2.executeQuery();

            if(categoryAppItems!=null)categoryAppItems.removeAllElements();
             while (rs2.next()) {

                appName = rs2.getString("application_name");
                appId = rs2.getString("client_id");  
                
                categoryAppItems.addElement(new ApplicationName(appName,appId));

            }
             
            comboAppNames = new JComboBox(categoryAppItems);           
            comboAppNames.setRenderer( new DisplayTokens.AppItemRenderer());
            comboAppNames.revalidate();
       
                    
            comboAppNames.setBounds(600, 52, 150, 20);
            add(comboAppNames);
            ApplicationName coAppSelected= (ApplicationName) comboAppNames.getSelectedItem();
            System.out.println(coAppSelected.getAppName());
             
            rs2.close();
            st2.close();
             } catch (SQLException ex) {
             Logger.getLogger(DisplayTxnDetails.class.getName()).log(Level.SEVERE, null, ex);
         }
    } 
    
}
