package livereportapplicationnet;

import livereportapplicationnet.entities.Bank;
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class DisplayAccountData extends JFrame  {
    JFrame frame1;
    JLabel l0, l1, l2, lCompanyName, lSandboxUsername;
    JComboBox c1;
    JComboBox c2;
    JTextField t1, tCompanyName, tSandboxUserName;
    JButton b1, b2;
    Connection con;
    ResultSet rs, rs1;
    Statement st, st1;
    PreparedStatement pst;
    String ids;
    String desc;
    private JTable table;
    String from;

    private String userName;
    private String companyName;
    private String sandboxUname;

    String[] columnNames = {"نام کاربری", "رمز عبور", "شناسه حقوقی", "لینک بازگشت"};

    DisplayAccountData() {

        setLocation(100, 0);
        l0 = new JLabel("گزارش حسابهای ثبت شده کاربران نهایی");
        l0.setForeground(Color.blue);
        l0.setFont(new Font("Serif", Font.BOLD, 20));

        l1 = new JLabel("کد ملی");
        l2 = new JLabel("انتخاب بانک");
        t1 = new JTextField(this.userName);

        b2 = new JButton("گزارش");

        lCompanyName = new JLabel("شماره حساب کاربر");
        tCompanyName = new JTextField(this.companyName);

        l0.setBounds(400, 5, 350, 40);
        l1.setBounds(800, 80, 150, 20);
        l2.setBounds(800, 110, 150, 20);
        b2.setBounds(400, 150, 150, 20);
        t1.setBounds(600, 80, 150, 20);
        lCompanyName.setBounds(300, 80, 150, 20);
        tCompanyName.setBounds(100, 80, 150, 20);
        t1.setBounds(600, 80, 150, 20);

         
        

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               Bank item= (Bank) c1.getSelectedItem();
                from = item.getBankCode();
                String uname = "";
                String email = "";
                String pass = "";
                String cou = "";
                String nationalCode = "";
                
                table = new JTable();
                Font font = new Font("Verdana", Font.PLAIN, 12);
                table.setFont(font);
                table.setRowHeight(30);
                float[] hsb1 = RGBtoHSB(212,212,212);
                table.setBackground(Color.getHSBColor(hsb1[0],hsb1[1],hsb1[2]));
                //table.setBackground(Color.lightGray);
                table.setForeground(Color.BLACK);
                JTableHeader tableHeader = table.getTableHeader();
                float[] hsb = RGBtoHSB(99,166,220);
                //tableHeader.setBackground(Color.getHSBColor(140,200,235));
                tableHeader.setBackground(Color.getHSBColor(hsb[0],hsb[1],hsb[2]));
                tableHeader.setForeground(Color.black);
                add(table);  
                

                        
                try {
//                  pst = con.prepareStatement("select * from obh.application where client_Id='" + from + "'");
                    pst = con.prepareStatement("select obh_txn_details.*, banks.bank_description description from obh_txn_details,banks \n" +
                            "where banks.bank_code = obh_txn_details.bank\n" +
                            "and bank='BSI'" +
                            "and bank=?");
                    pst.setString(1,from);
                    System.out.println(pst);
                    ResultSet rs = pst.executeQuery();
                    int i = 0;
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    // model.removeTableModelListener(table);
                    model.addColumn("id");
                    model.addColumn("bank");
                    model.addColumn("accountOwnerName");
                    model.addColumn("customerType");
                    model.addColumn("nationalCode");    
                    while (rs.next()) {

                        cou = rs.getString("id");
                        email = rs.getString("description");
                        pass = rs.getString("account_owner_name");
                        uname = rs.getString("customer_type");
                        nationalCode = rs.getString("national_code");

                        model.addRow(new Object[]{cou,email, pass, uname, nationalCode});
                        i++;
                    }
                    model.fireTableDataChanged();
                    if (i < 1) {
                        JOptionPane.showMessageDialog(null, "No Record Found", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    System.out.println(i + " Records Found");
                    // ScrollPane
                    JScrollPane scroll = new JScrollPane(table);
                    scroll.setBounds(200, 200, 600, 200);
                    add(scroll);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }               
            }
        });

    
        setTitle("Fetching Applications Info From DataBase");
//
        setLayout(null);
//
        setVisible(true);
//
        setSize(1000, 500);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(l0);
        add(l1);
        add(l2);
        add(t1);
        add(b2);
        add(lCompanyName);
        add(tCompanyName);

        try {

            DefaultComboBoxModel categoryItems = new DefaultComboBoxModel();

//            JOptionPane.showMessageDialog(null, "before Connection");
            con = DriverManager.getConnection("jdbc:postgresql://192.168.28.179:5433/ShahinLive", "postgres", "123");
            st = con.createStatement();
            PreparedStatement st = (PreparedStatement) con.prepareStatement("Select bank_description,bank_code from banks");
            System.out.println(st);
            //st.setString(1,this.userName);

            rs = st.executeQuery();

            Vector v = new Vector();

            while (rs.next()) {

                ids = rs.getString(2);
                desc = rs.getString(1);
                v.add(desc);
                categoryItems.addElement(new Bank(ids,desc));

            }

            c1 = new JComboBox(categoryItems);
            c1.setRenderer( new ItemRenderer() );
            c1.setBounds(600, 110, 150, 20);
            add(c1);
            Bank code= (Bank) c1.getSelectedItem();
            System.out.println(code.getBankCode());
            c2 = new JComboBox();

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
                Bank item = (Bank) value;
                setText( item.getBankDescription().toUpperCase() );
            }

            if (index == -1)
            {
                Bank item = (Bank) value;
                setText( "" + item.getBankDescription() );
            }


            return this;
        }
    }
  
    public float[] RGBtoHSB(int red,int green,int blue){
        float[] hsb = Color.RGBtoHSB(red, green, blue, null);
        float hue = hsb[0];
        float saturation = hsb[1];
        float brightness = hsb[2];
        return hsb;
    }

}

