
package com.gfclacademy.payslipdispatch;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
/**
 *
 * @author DownTown
 */
public class PayslipDispatch implements ActionListener{
    JFrame frame = new JFrame("PAYSLIP MAIL DISPATCHER");
    JPanel main_body = new JPanel();
    JLabel labelDate = new JLabel("Date Uploaded");
    JLabel labelNewCombo = new JLabel("                    ");
    JLabel labelEmail = new JLabel("Upload Email");
    JLabel labelBatch = new JLabel("Upload Batch Pdf");
    JLabel labelUploadedEmail = new JLabel("file name of excel file");
    JLabel labelUploadedBatch = new JLabel("file name of batch pdf");
    JButton buttonMail = new JButton("Mail Slips");
    JButton buttonCancel = new JButton("Cancel");
    JButton buttonEmail = new JButton("Upload Email Excel Sheet (.xls)");
    JButton buttonBatch = new JButton("Upload Batch Payslips (.pdf)");
    JRadioButton radioSave = new JRadioButton("Save To Database");
    JRadioButton radioDontSave = new JRadioButton("Don't Save To Database");
    JTextField fileDate = new JTextField("yyyy-mm-dd");
    JTextField textNewComboItem;
    
    static JComboBox comboParastatal;
    static File batchPdf;
    static File emailsFile;
    
    private static final File TEMP_DIRECTORY = new File(System.getProperty("java.io.tmpdir"));
    
    public PayslipDispatch(){
        String para[] = {"Select a Ministry","Federal Ministry of Internal Affairs", "Federal Ministry of Justice",
                "Federal Ministry of Labour and "
                + "Productivity","Federal Ministry of Petroleum Resources","Federal Ministry of Agriculture and Natural Resources",
                "Federal Ministry of Aviation","Federal Minsitry of Commerce and Tourism","Federal Ministry of Information and "
                + "Communication","Federal Ministry of Power and Steel","Federal Ministry of Defence","Federal Ministry of Science "
                + "and Technology","Federal Ministry of Education and Youth Development","Federal Ministry of Solid Minerals Development",
                "Federal Ministry of Environment","Federal Ministry for Federal Capital Territory","Federal Ministry of Special Duties",
                "Federal Ministry of Finance and Economic Development","Federal Ministry of Transport","Federal Ministry of Foreign Affairs",
                "Federal Minsitry of Water Resources and Rural Development","Federal Ministry of Health and Social Services","Federal Ministry"
                + "of Women Affairs and Social Development","Federal Ministry of Industries","Federal Ministry of Works","Federal Minsitry of"
                + " Culture Tourism and National Orientation", "Federal Minsitry of Youth and Sport","Abubakar Tafawa Balewa University, Bauch",
                "Ahmadu Bello University, Zaria", "Bayero University, Kano","Federal University Gashau, Yobe", "Federal University of Petroleum "
                + "Resources, Effurun","Federal University of Technology, Akure","Federal University of Technology, Minna", "Federal University "
                + "of Technology, Owerri", "Federal University, Dutse, Jigawa State", "Federa University, Dutsin-Ma, Katsina","Federal University, "
                + "Kashere, Gombe State", "Federal University, Lafia, Nasarawa State", "Federal University, Lokoja, Kogi State", "Federal University "
                + "Ndifu-Alike, EBonyi State", "Federal University, Otuake, Bayelsa State", "Federal University, Oye-Ekitti, Ekitti State",
                "Federal University, Wukari, Taraba State", "Federal University, Birnin Kebbi", "Federal University, Gusau, Zamfara State",
                "Michael Okpara University of Agriculture, Umudike", "Modibbo Adama University of Technology, Yola","Nigerian Open University"
                + " of Nigeria", "Nigeria Police Academy, Wudil", "Nigerian Defence Academy, Kaduna", "Nnamdi Azikiwe University, Awka",
                "Obafemi Awolowo University, Ile-Ife","University of Abuja, Gwagwalada", "Federal University of Agriculture, Abeokuta",
                "University of Agriculture, Makurdi","University of Benin","University of Calabar","University of Ibadan","University of "
                + "Ilorin", "University of Jos", "University of Lagos", "University of Maiduguri", "University of Nigeria, Nsukka","University"
                + " of Port-Harcourt","University of Uyo","Usuman Dan Fodio University","Nigerian Maritime University Okerenkoko, Delta State",
                "Airforce Institute of Technology, Kaduna","Nigerian Army University, BIU","University of Health Technology, Otukpo Benue State",
                 "Federal University of Agriculture, Zuru, Kebbi State", "Federal University of Technology, Babura, Jigawa State", "Federal "
                + "University of Technology, Ikot Abasi, Akwa Ibom State ", "Federal University Health Sciences, Azare Bauchi State",
                "Federal University Health Sciences, Ila Orogun, Osun State", "Others Not Listed"
        };
        comboParastatal = new JComboBox(para);
        
        
        //add radio buttons to buttongroup
        ButtonGroup bg = new ButtonGroup();
        bg.add(radioSave);
        bg.add(radioDontSave);
        
        JPanel panelContainer = new JPanel();
        JPanel panelCombo = new JPanel();
        JPanel panelUpload = new JPanel();
        JPanel panelHistorical = new JPanel();
        JPanel panelButtons = new JPanel();
        JPanel panelEast = new JPanel();
        JPanel panelWest = new JPanel();
        JPanel panelNorth = new JPanel();
        JPanel panelSouth = new JPanel();
        
        //set listeners
        
        buttonEmail.addActionListener(this);
        buttonBatch.addActionListener(this);
        buttonCancel.addActionListener(this);
        buttonMail.addActionListener(this);
        
        fileDate.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e){
                if(fileDate.getText().equalsIgnoreCase("yyyy-mm-dd")){
                    fileDate.setText("");
                    fileDate.setForeground(Color.BLACK);
                    fileDate.setFont(new Font("Verdena",Font.BOLD,12));
                }
            }
            
            public void focusLost(FocusEvent e){
                Pattern p = Pattern.compile("[1-2][0-9][0-9][0-9]-[0-1][0-9]-[0-9][0-9]");
                Matcher m = p.matcher(fileDate.getText());
                boolean found = m.find();
                
                if(fileDate.getText().equalsIgnoreCase("")){
                    fileDate.setText("yyyy-mm-dd");
                    fileDate.setForeground(Color.lightGray);
                    fileDate.setFont(new Font("Verdena",Font.PLAIN,10));
                     JOptionPane.showMessageDialog(frame, "Date Required","DATE INPUT",JOptionPane.WARNING_MESSAGE);
                        fileDate.requestFocus();
                }
                else{
                    if(!found){
                        JOptionPane.showMessageDialog(frame, "Wrong Date Format","DATE FORMAT",JOptionPane.WARNING_MESSAGE);
                        fileDate.requestFocus();
                    }
                }
            }
        });
        
        //set fonts
        Font labelFont = new Font("Verdana",Font.ITALIC,10);
        labelUploadedEmail.setFont(labelFont);
        labelUploadedBatch.setFont(labelFont);
        
        Font dateFont = new Font("Tahoma",Font.PLAIN,10);
        fileDate.setFont(dateFont);
        fileDate.setForeground(Color.LIGHT_GRAY);
        
        // set layouts on all panels
        panelCombo.setLayout(new FlowLayout());
        panelUpload.setLayout(new GridLayout(6,2));
        panelHistorical.setLayout(new FlowLayout());
        panelButtons.setLayout(new FlowLayout());
        panelWest.setLayout(new FlowLayout());
        panelEast.setLayout(new FlowLayout());
        panelContainer.setLayout(new GridLayout(2,1));
        frame.setLayout(new BorderLayout());
        
        //add items to panel
        panelCombo.add(comboParastatal);
        comboParastatal.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if(e.getItem().toString().equalsIgnoreCase("Others Not Listed")){
                    System.out.println(e.getItem().toString());
                    textNewComboItem = new JTextField("Type a new Ministry/Parastatal");
                    textNewComboItem.setFont(new Font("Verdana",Font.PLAIN,10));
                    textNewComboItem.setForeground(Color.LIGHT_GRAY);
                    panelCombo.add(textNewComboItem);
                }
            }
        });
        
        panelUpload.add(labelDate);
        panelUpload.add(fileDate);
        panelUpload.add(new JLabel(          ));
        panelUpload.add(new JLabel(          ));
        panelUpload.add(labelEmail);
        panelUpload.add(buttonEmail);
        panelUpload.add(new JLabel());
        panelUpload.add(labelUploadedEmail);
        panelUpload.add(labelBatch);
        panelUpload.add(buttonBatch);
        panelUpload.add(new JLabel());
        panelUpload.add(labelUploadedBatch);

        panelHistorical.add(radioSave);
        panelHistorical.add(radioDontSave);
        
        panelButtons.add(buttonMail);
        panelButtons.add(buttonCancel);
        
        panelEast.add(new JLabel("                              "));
        panelWest.add(new JLabel("                              "));
        
        
        
        //add panels to Frame
        panelContainer.add(panelUpload);
        panelContainer.add(panelHistorical);
        
        frame.add(panelWest,BorderLayout.WEST);
        frame.add(panelEast,BorderLayout.EAST);
        frame.add(panelCombo,BorderLayout.NORTH);
        frame.add(panelButtons,BorderLayout.SOUTH);
        frame.add(panelContainer, BorderLayout.CENTER);
        
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.pack();
        frame.setVisible(true);
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                comboParastatal.grabFocus();
                comboParastatal.requestFocus();
            }
        });
    }
    
    public void actionPerformed(ActionEvent e){
        String btn = e.getActionCommand().toString();
        //create Directory for splited pdf files
//        if(comboParastatal.getSelectedIndex()>0){    
//            
//        }
//        else{
//             JOptionPane.showMessageDialog(frame, "Please Select a Ministry","Ministry/Parastatal",JOptionPane.WARNING_MESSAGE);
//        }
        
       
        if(e.getActionCommand().equalsIgnoreCase("Cancel")){
           System.exit(0);
       }
       else if(e.getActionCommand().equalsIgnoreCase("Mail Slips")){
           if(comboParastatal.getSelectedIndex()==0){
               JOptionPane.showMessageDialog(frame, "Please Select a Ministry","Ministry/Parastatal",JOptionPane.WARNING_MESSAGE);
               comboParastatal.requestFocus();
           }
           else{
               String dirname = comboParastatal.getSelectedItem().toString();
               
               
               
               String trxndate = fileDate.getText().toString();
               File newDirectory = new File(TEMP_DIRECTORY, dirname);
                if(newDirectory.exists()){
                    newDirectory.mkdir();
                    System.out.println("Directory Created Successfully");
                }
                else{
                    System.out.println("Unable to Create Directory "+dirname + ".");
                }
                File subDirectory = new File(newDirectory, trxndate);
                if(subDirectory.exists()){
                    subDirectory.mkdir();
                }
               SendMails s = new SendMails();
               try {
                   s.splitPdf(batchPdf,emailsFile,dirname,trxndate);
               } catch (IOException ex) {
                   Logger.getLogger(PayslipDispatch.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
       }
       else{
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int r = j.showOpenDialog(null);

            if(r==JFileChooser.APPROVE_OPTION){
                String filename = j.getSelectedFile().getName();
                String pathname = j.getSelectedFile().getAbsolutePath();
                String command = e.getActionCommand();
                if(e.getActionCommand().equalsIgnoreCase("Upload Email Excel Sheet (.xls)")){
                    if(comboParastatal.getSelectedIndex() == 0){
                        JOptionPane.showMessageDialog(frame, "Please Select a Ministry","Ministry/Parastatal",JOptionPane.WARNING_MESSAGE);
                        comboParastatal.requestFocus();
                    }
                    else{
                        if(filename.endsWith(".xls")||filename.endsWith(".xlsx")||filename.endsWith(".csv")){
                            emailsFile = j.getSelectedFile();
                             labelUploadedEmail.setText(filename);
                        }
                        else{
                            filename = "";
                            JOptionPane.showMessageDialog(frame,"Please select only .xls files","Wrong Emails File format",JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
                else if(e.getActionCommand().equalsIgnoreCase("Upload Batch Payslips (.pdf)")){
                    if(comboParastatal.getSelectedIndex() == 0){
                        JOptionPane.showMessageDialog(frame, "Please Select a Ministry","Ministry/Parastatal",JOptionPane.WARNING_MESSAGE);
                        comboParastatal.requestFocus();
                    }
                    else{
                        if(filename.endsWith(".pdf")){
                            batchPdf = j.getSelectedFile();
                             labelUploadedBatch.setText(filename);
                        }
                        else{
                            filename = "";
                            JOptionPane.showMessageDialog(frame,"Please select only .pdf files","Wrong Batch File format",JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        }
       
    }
    
    
    
    public static void main(String[] args) {
        PayslipDispatch m = new PayslipDispatch();
    }
    
}
