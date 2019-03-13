/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg195_5alumniqueries_idenney;

import Common.CommandItem;
import DataTable.DataTable;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Isaac Denney
 */
public class DialogAlumniQueries extends javax.swing.JDialog {

    /**
     * Creates new form DialogAlumniQueries
     * @param parent
     * @param modal
     */
    public DialogAlumniQueries(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.resultTabs.removeAll();
        this.splitpaneMain.setResizeWeight(0);
        
    }

    private ResultPanel getTab(String name) {
        ResultPanel value=null;
        int tabIndex=-1;
        
        for (int i=0;i<this.resultTabs.getTabCount();i++) {
            String title = this.resultTabs.getTitleAt(i);
            if (title.equals(name)) {
                tabIndex=i;
                break;
            }
        }
        
        if (tabIndex==-1) {
            try {
                value = (ResultPanel)this.resultTabs.add(name,createTab(name));
            } catch (Exception ex) {
                if (ex!=null) {
                    
                }
            }
        } else {
            value=(ResultPanel)this.resultTabs.getComponentAt(tabIndex);
        }
        
        return value;
    }
    private ResultPanel createTab(String name) {
        ResultPanel value = new ResultPanel();
        return value;
    }
    private void setOutput(ResultPanel panel,CommandItem[] commands, DataTable dt) {
        
        panel.setQueryText("Running the following commands:\n" + Main.getCommands(commands));
        if (dt!=null) {
            try {
                dt.setShowRowIdEnabled(false);
                String strVal=dt.toString();
                panel.setResultText(strVal);
            } catch (Exception ex) {
                if (ex!=null) {

                }
            }
        } 
    }
    
    private void runQueryA() {
        ResultPanel tab = getTab("Query A");
        if (tab!=null) {
            CommandItem[] commands=Main.SELECT_ROWS_FULLNAME_SSN_TABLE_STUDENTS_PRIVATE();
            ArrayList value = Main.runCommands(commands);
            DataTable dt=null;
            if (value!=null&&value.size()>0) {dt = (DataTable)value.get(0);}
            setOutput(tab,commands,dt);
        }
    }
    private void runQueryB() {
        ResultPanel tab = getTab("Query B");
        if (tab!=null) {
            CommandItem[] commands=Main.SELECT_ROWS_EMAIL_TABLE_STUDENTS_EMAILS();
            
            String fName=this.textfieldFirstName.getText().trim();
            String mName=this.textfieldMiddleName.getText().trim();
            String lName=this.textfieldLastName.getText().trim();
                    
            if (fName.length()==0) {fName=" IS NULL";} else {fName="='"+fName+"'";}
            if (mName.length()==0) {mName=" IS NULL";} else {mName="='"+mName+"'";}
            if (lName.length()==0) {lName=" IS NULL";} else {lName="='"+lName+"'";}
            
            commands[0].setQuery(commands[0].getQuery().replace("{0}", fName).replace("{1}", mName).replace("{2}", lName));
            ArrayList value = Main.runCommands(commands);
            DataTable dt=null;
            
            if (value!=null&&value.size()>0) {dt = (DataTable)value.get(0);}
            setOutput(tab,commands,dt);
        }
    }
    private void runQueryC() {
        ResultPanel tab = getTab("Query C");
        if (tab!=null) {
            CommandItem[] commands=Main.SELECT_ROWS_MAJORS_TABLE_MAJORSINFO();
            
            String fName=this.textfieldFirstName.getText().trim();
            String mName=this.textfieldMiddleName.getText().trim();
            String lName=this.textfieldLastName.getText().trim();
                    
            if (fName.length()==0) {fName=" IS NULL";} else {fName="='"+fName+"'";}
            if (mName.length()==0) {mName=" IS NULL";} else {mName="='"+mName+"'";}
            if (lName.length()==0) {lName=" IS NULL";} else {lName="='"+lName+"'";}
            
            commands[0].setQuery(commands[0].getQuery().replace("{0}", fName).replace("{1}", mName).replace("{2}", lName));
            ArrayList value = Main.runCommands(commands);
            DataTable dt = null;
            
            if (value!=null&&value.size()>0) {dt = (DataTable)value.get(0);}
            setOutput(tab,commands,dt);
        }
    }
    private void runQueryD() {
        ResultPanel tab = getTab("Query D");
        if (tab!=null) {
            CommandItem[] commands=Main.SELECT_ROWS_FULLNAME_TABLE_MAJORS();
            ArrayList value = Main.runCommands(commands);
            DataTable dt = null;
            
            if (value!=null&&value.size()>0) {dt = (DataTable)value.get(0);}
            setOutput(tab,commands,dt);
        }
    }
    private void runQueryE() {
        ResultPanel tab = getTab("Query E");
        if (tab!=null) {
            CommandItem[] commands=Main.SELECT_ROW_ISBARBARACS_TABLE_STUDENTS();
            ArrayList value = Main.runCommands(commands);
            DataTable dt = null;
            
            if (value!=null&&value.size()>0) {dt = (DataTable)value.get(0);}
            setOutput(tab,commands,dt);
        } 
    }
    private void runQueryF() {
        ResultPanel tab = getTab("Query F");
        if (tab!=null) {
            CommandItem[] commands=Main.SELECT_ROWS_ONEMAJOR_TABLE_STUDENTS();
            ArrayList value = Main.runCommands(commands);
            DataTable dt = null;
            
            if (value!=null&&value.size()>0) {dt = (DataTable)value.get(0);}
            setOutput(tab,commands,dt);
        } 
    }
    private void runQueryG() {
        ResultPanel tab = getTab("Query G");
        if (tab!=null) {
            CommandItem[] commands=Main.SELECT_ROWS_NAME_EMAILS_TABLE_STUDENTS();
            ArrayList value = Main.runCommands(commands);
            DataTable dt = null;
            
            if (value!=null&&value.size()>0) {dt = (DataTable)value.get(0);}
            setOutput(tab,commands,dt);
        } 
    }
    private void runQueryH() {
        ResultPanel tab = getTab("Query H");
        if (tab!=null) {
            CommandItem[] commands=Main.SELECT_ROWS_NAME_NOTCS_TABLE_STUDENTS();
            ArrayList value = Main.runCommands(commands);
            DataTable dt = null;
            
            if (value!=null&&value.size()>0) {dt = (DataTable)value.get(0);}
            setOutput(tab,commands,dt);
        } 
    }
    private class ResultPanel extends JPanel {
        
        private javax.swing.JSplitPane splitpaneTabItem;
        
        private javax.swing.JScrollPane scrollpaneQueryTabItem;
        private javax.swing.JPanel panelQueryTabItem;
        private javax.swing.JLabel labelQueryTabItem;
        private javax.swing.JTextArea textareaQueryTabItem;
        
        private javax.swing.JScrollPane scrollpaneResultTabItem;
        private javax.swing.JPanel panelResultTabItem;
        private javax.swing.JLabel labelResultTabItem;
        private javax.swing.JTextArea textareaResultTabItem;
        
        public String getQueryText() {
            return this.textareaQueryTabItem.getText();
        }
        public void setQueryText(String value) {
            textareaQueryTabItem.setText(value);
        }
        
        public String getResultText() {
            return this.textareaResultTabItem.getText();
        }
        public void setResultText(String value) {
            this.textareaResultTabItem.setText(value);
        }
        
        public ResultPanel() {
            init();
        }
        
        private void init() {
            ///*
            splitpaneTabItem = new javax.swing.JSplitPane();
            panelQueryTabItem = new javax.swing.JPanel();
            labelQueryTabItem = new javax.swing.JLabel();
            scrollpaneQueryTabItem = new javax.swing.JScrollPane();
            textareaQueryTabItem = new javax.swing.JTextArea();
            panelResultTabItem = new javax.swing.JPanel();
            labelResultTabItem = new javax.swing.JLabel();
            scrollpaneResultTabItem = new javax.swing.JScrollPane();
            textareaResultTabItem = new javax.swing.JTextArea();
            
            splitpaneTabItem.setDividerLocation(200);
            splitpaneTabItem.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

            labelQueryTabItem.setText("Queries");

            textareaQueryTabItem.setColumns(20);
            textareaQueryTabItem.setLineWrap(true);
            textareaQueryTabItem.setRows(1);
            textareaQueryTabItem.setTabSize(1);
            textareaQueryTabItem.setWrapStyleWord(true);
            scrollpaneQueryTabItem.setViewportView(textareaQueryTabItem);

            javax.swing.GroupLayout panelQueryTabItemLayout = new javax.swing.GroupLayout(panelQueryTabItem);
            panelQueryTabItem.setLayout(panelQueryTabItemLayout);
            panelQueryTabItemLayout.setHorizontalGroup(
                panelQueryTabItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelQueryTabItemLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(panelQueryTabItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(scrollpaneQueryTabItem, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                        .addGroup(panelQueryTabItemLayout.createSequentialGroup()
                            .addComponent(labelQueryTabItem)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())
            );
            panelQueryTabItemLayout.setVerticalGroup(
                panelQueryTabItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelQueryTabItemLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(labelQueryTabItem)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scrollpaneQueryTabItem, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addContainerGap())
            );

            splitpaneTabItem.setTopComponent(panelQueryTabItem);

            labelResultTabItem.setText("Results");

            textareaResultTabItem.setColumns(20);
            textareaResultTabItem.setLineWrap(true);
            textareaResultTabItem.setRows(1);
            textareaResultTabItem.setTabSize(1);
            textareaResultTabItem.setWrapStyleWord(true);
            scrollpaneResultTabItem.setViewportView(textareaResultTabItem);

            javax.swing.GroupLayout panelResultTabItemLayout = new javax.swing.GroupLayout(panelResultTabItem);
            panelResultTabItem.setLayout(panelResultTabItemLayout);
            panelResultTabItemLayout.setHorizontalGroup(
                panelResultTabItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelResultTabItemLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(panelResultTabItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(scrollpaneResultTabItem, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                        .addGroup(panelResultTabItemLayout.createSequentialGroup()
                            .addComponent(labelResultTabItem)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())
            );
            panelResultTabItemLayout.setVerticalGroup(
                panelResultTabItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelResultTabItemLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(labelResultTabItem)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scrollpaneResultTabItem, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addContainerGap())
            );

            splitpaneTabItem.setRightComponent(panelResultTabItem);

            javax.swing.GroupLayout thisLayout = new javax.swing.GroupLayout(this);
            this.setLayout(thisLayout);
            thisLayout.setHorizontalGroup(
                thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(thisLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(splitpaneTabItem, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                    .addContainerGap())
            );
            thisLayout.setVerticalGroup(
                thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(thisLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(splitpaneTabItem)
                    .addContainerGap())
            );

            resultTabs.addTab("Query A", this);

            splitpaneMain.setRightComponent(resultTabs);
            resultTabs.getAccessibleContext().setAccessibleName("");
            resultTabs.getAccessibleContext().setAccessibleDescription("");

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(splitpaneMain, javax.swing.GroupLayout.DEFAULT_SIZE, 1045, Short.MAX_VALUE)
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(splitpaneMain)
                    .addContainerGap())
            );
            
            Font font = new Font(Font.MONOSPACED, Font.PLAIN, 14);
            this.textareaQueryTabItem.setFont(font);
            this.textareaResultTabItem.setFont(font);

            pack();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitpaneMain = new javax.swing.JSplitPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextArea1 = new javax.swing.JTextArea();
        jTextArea3 = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jTextArea4 = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jTextArea5 = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jTextArea6 = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jTextArea7 = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jTextArea8 = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jTextArea2 = new javax.swing.JTextArea();
        buttonRunQueryA = new javax.swing.JButton();
        buttonRunQueryB = new javax.swing.JButton();
        buttonRunQueryC = new javax.swing.JButton();
        buttonRunQueryD = new javax.swing.JButton();
        buttonRunQueryE = new javax.swing.JButton();
        buttonRunQueryF = new javax.swing.JButton();
        buttonRunQueryG = new javax.swing.JButton();
        buttonRunQueryH = new javax.swing.JButton();
        textfieldFirstName = new javax.swing.JTextField();
        textfieldMiddleName = new javax.swing.JTextField();
        textfieldLastName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        resultTabs = new javax.swing.JTabbedPane();
        panelTabItem = new javax.swing.JPanel();
        splitpaneTabItem = new javax.swing.JSplitPane();
        panelQueryTabItem = new javax.swing.JPanel();
        labelQueryTabItem = new javax.swing.JLabel();
        scrollpaneQueryTabItem = new javax.swing.JScrollPane();
        textareaQueryTabItem = new javax.swing.JTextArea();
        panelResultTabItem = new javax.swing.JPanel();
        labelResultTabItem = new javax.swing.JLabel();
        scrollpaneResultTabItem = new javax.swing.JScrollPane();
        textareaResultTabItem = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Alumni Queries");
        setMinimumSize(new java.awt.Dimension(1005, 216));

        splitpaneMain.setDividerLocation(750);
        splitpaneMain.setEnabled(false);
        splitpaneMain.setMaximumSize(new java.awt.Dimension(912, 32768));
        splitpaneMain.setMinimumSize(new java.awt.Dimension(912, 256));
        splitpaneMain.setPreferredSize(new java.awt.Dimension(912, 256));

        jPanel5.setMaximumSize(new java.awt.Dimension(749, 192));
        jPanel5.setMinimumSize(new java.awt.Dimension(749, 192));

        jScrollPane1.setHorizontalScrollBar(null);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(712, 32767));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(712, 200));
        jScrollPane1.setRequestFocusEnabled(false);

        jLabel1.setText("Query A");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel1.setMaximumSize(new java.awt.Dimension(50, 100));
        jLabel1.setMinimumSize(new java.awt.Dimension(50, 100));
        jLabel1.setPreferredSize(new java.awt.Dimension(50, 100));
        jLabel1.setRequestFocusEnabled(false);

        jLabel2.setText("Query B");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel2.setMaximumSize(new java.awt.Dimension(50, 100));
        jLabel2.setMinimumSize(new java.awt.Dimension(50, 100));
        jLabel2.setPreferredSize(new java.awt.Dimension(50, 100));
        jLabel2.setRequestFocusEnabled(false);

        jTextArea1.setEditable(false);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Show the student names and their matching SSNs.");
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setAutoscrolls(false);
        jTextArea1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextArea1.setCaretPosition(0);
        jTextArea1.setFocusable(false);
        jTextArea1.setMaximumSize(new java.awt.Dimension(256, 100));
        jTextArea1.setMinimumSize(new java.awt.Dimension(256, 100));
        jTextArea1.setName(""); // NOI18N
        jTextArea1.setPreferredSize(new java.awt.Dimension(256, 100));

        jTextArea3.setEditable(false);
        jTextArea3.setLineWrap(true);
        jTextArea3.setRows(5);
        jTextArea3.setText("Input an alumni name from the keyboard and display that person's emails.");
        jTextArea3.setWrapStyleWord(true);
        jTextArea3.setAutoscrolls(false);
        jTextArea3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextArea3.setFocusable(false);
        jTextArea3.setMaximumSize(new java.awt.Dimension(256, 100));
        jTextArea3.setMinimumSize(new java.awt.Dimension(256, 100));
        jTextArea3.setName(""); // NOI18N
        jTextArea3.setPreferredSize(new java.awt.Dimension(256, 100));
        jTextArea3.setSelectionEnd(0);
        jTextArea3.setSelectionStart(0);

        jLabel5.setText("Query C");
        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel5.setMaximumSize(new java.awt.Dimension(50, 100));
        jLabel5.setMinimumSize(new java.awt.Dimension(50, 100));
        jLabel5.setPreferredSize(new java.awt.Dimension(50, 100));
        jLabel5.setRequestFocusEnabled(false);

        jTextArea4.setEditable(false);
        jTextArea4.setLineWrap(true);
        jTextArea4.setRows(5);
        jTextArea4.setText("Using the same alumni name, display their majors.");
        jTextArea4.setWrapStyleWord(true);
        jTextArea4.setAutoscrolls(false);
        jTextArea4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextArea4.setFocusable(false);
        jTextArea4.setMaximumSize(new java.awt.Dimension(256, 100));
        jTextArea4.setMinimumSize(new java.awt.Dimension(256, 100));
        jTextArea4.setName(""); // NOI18N
        jTextArea4.setPreferredSize(new java.awt.Dimension(256, 100));
        jTextArea4.setSelectionEnd(0);
        jTextArea4.setSelectionStart(0);

        jLabel7.setText("Query D");
        jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel7.setMaximumSize(new java.awt.Dimension(50, 100));
        jLabel7.setMinimumSize(new java.awt.Dimension(50, 100));
        jLabel7.setPreferredSize(new java.awt.Dimension(50, 100));
        jLabel7.setRequestFocusEnabled(false);

        jTextArea5.setEditable(false);
        jTextArea5.setLineWrap(true);
        jTextArea5.setRows(5);
        jTextArea5.setText("Show who majored in CS.");
        jTextArea5.setWrapStyleWord(true);
        jTextArea5.setAutoscrolls(false);
        jTextArea5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextArea5.setFocusable(false);
        jTextArea5.setMaximumSize(new java.awt.Dimension(256, 100));
        jTextArea5.setMinimumSize(new java.awt.Dimension(256, 100));
        jTextArea5.setName(""); // NOI18N
        jTextArea5.setPreferredSize(new java.awt.Dimension(256, 100));
        jTextArea5.setSelectionEnd(0);
        jTextArea5.setSelectionStart(0);

        jLabel8.setText("Query E");
        jLabel8.setToolTipText("");
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel8.setMaximumSize(new java.awt.Dimension(50, 100));
        jLabel8.setMinimumSize(new java.awt.Dimension(50, 100));
        jLabel8.setPreferredSize(new java.awt.Dimension(50, 100));
        jLabel8.setRequestFocusEnabled(false);

        jTextArea6.setEditable(false);
        jTextArea6.setLineWrap(true);
        jTextArea6.setRows(5);
        jTextArea6.setText("Did Barbara Johnson major in CS? Answer 'yes' or 'no' (nothing else).");
        jTextArea6.setWrapStyleWord(true);
        jTextArea6.setAutoscrolls(false);
        jTextArea6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextArea6.setFocusable(false);
        jTextArea6.setMaximumSize(new java.awt.Dimension(256, 100));
        jTextArea6.setMinimumSize(new java.awt.Dimension(256, 100));
        jTextArea6.setName(""); // NOI18N
        jTextArea6.setPreferredSize(new java.awt.Dimension(256, 100));
        jTextArea6.setSelectionEnd(0);
        jTextArea6.setSelectionStart(0);

        jLabel9.setText("Query F");
        jLabel9.setToolTipText("");
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel9.setMaximumSize(new java.awt.Dimension(50, 100));
        jLabel9.setMinimumSize(new java.awt.Dimension(50, 100));
        jLabel9.setPreferredSize(new java.awt.Dimension(50, 100));
        jLabel9.setRequestFocusEnabled(false);

        jTextArea7.setEditable(false);
        jTextArea7.setLineWrap(true);
        jTextArea7.setRows(5);
        jTextArea7.setText("Show name of everyone that had only 1 major.");
        jTextArea7.setWrapStyleWord(true);
        jTextArea7.setAutoscrolls(false);
        jTextArea7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextArea7.setFocusable(false);
        jTextArea7.setMaximumSize(new java.awt.Dimension(256, 100));
        jTextArea7.setMinimumSize(new java.awt.Dimension(256, 100));
        jTextArea7.setName(""); // NOI18N
        jTextArea7.setPreferredSize(new java.awt.Dimension(256, 100));
        jTextArea7.setSelectionEnd(0);
        jTextArea7.setSelectionStart(0);

        jLabel10.setText("Query G");
        jLabel10.setToolTipText("");
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel10.setMaximumSize(new java.awt.Dimension(50, 100));
        jLabel10.setMinimumSize(new java.awt.Dimension(50, 100));
        jLabel10.setPreferredSize(new java.awt.Dimension(50, 100));
        jLabel10.setRequestFocusEnabled(false);

        jTextArea8.setEditable(false);
        jTextArea8.setLineWrap(true);
        jTextArea8.setRows(5);
        jTextArea8.setText("Show names and emails of CS majors.");
        jTextArea8.setWrapStyleWord(true);
        jTextArea8.setAutoscrolls(false);
        jTextArea8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextArea8.setFocusable(false);
        jTextArea8.setMaximumSize(new java.awt.Dimension(256, 100));
        jTextArea8.setMinimumSize(new java.awt.Dimension(256, 100));
        jTextArea8.setName(""); // NOI18N
        jTextArea8.setPreferredSize(new java.awt.Dimension(256, 100));
        jTextArea8.setSelectionEnd(0);
        jTextArea8.setSelectionStart(0);

        jLabel6.setText("Query H");
        jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel6.setMaximumSize(new java.awt.Dimension(50, 100));
        jLabel6.setMinimumSize(new java.awt.Dimension(50, 100));
        jLabel6.setPreferredSize(new java.awt.Dimension(50, 100));

        jTextArea2.setEditable(false);
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jTextArea2.setText("Show names of everyone that did not major in CS.\nDo not assume anything about their major such as 'it must be law or sociology or math'.");
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setAutoscrolls(false);
        jTextArea2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextArea2.setFocusable(false);
        jTextArea2.setMaximumSize(new java.awt.Dimension(256, 100));
        jTextArea2.setMinimumSize(new java.awt.Dimension(256, 100));
        jTextArea2.setName(""); // NOI18N
        jTextArea2.setPreferredSize(new java.awt.Dimension(256, 100));
        jTextArea2.setSelectionEnd(0);
        jTextArea2.setSelectionStart(0);

        buttonRunQueryA.setText("Run Query A");
        buttonRunQueryA.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRunQueryA.setMinimumSize(new java.awt.Dimension(100, 100));
        buttonRunQueryA.setPreferredSize(new java.awt.Dimension(100, 100));
        buttonRunQueryA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonRunQueryAMouseClicked(evt);
            }
        });

        buttonRunQueryB.setText("Run Query B");
        buttonRunQueryB.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRunQueryB.setMinimumSize(new java.awt.Dimension(100, 100));
        buttonRunQueryB.setPreferredSize(new java.awt.Dimension(100, 100));
        buttonRunQueryB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonRunQueryBMouseClicked(evt);
            }
        });

        buttonRunQueryC.setText("Run Query C");
        buttonRunQueryC.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRunQueryC.setMinimumSize(new java.awt.Dimension(100, 100));
        buttonRunQueryC.setPreferredSize(new java.awt.Dimension(100, 100));
        buttonRunQueryC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonRunQueryCMouseClicked(evt);
            }
        });

        buttonRunQueryD.setText("Run Query D");
        buttonRunQueryD.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRunQueryD.setMinimumSize(new java.awt.Dimension(100, 100));
        buttonRunQueryD.setPreferredSize(new java.awt.Dimension(100, 100));
        buttonRunQueryD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonRunQueryDMouseClicked(evt);
            }
        });

        buttonRunQueryE.setText("Run Query E");
        buttonRunQueryE.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRunQueryE.setMinimumSize(new java.awt.Dimension(100, 100));
        buttonRunQueryE.setPreferredSize(new java.awt.Dimension(100, 100));
        buttonRunQueryE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonRunQueryEMouseClicked(evt);
            }
        });

        buttonRunQueryF.setText("Run Query F");
        buttonRunQueryF.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRunQueryF.setMinimumSize(new java.awt.Dimension(100, 100));
        buttonRunQueryF.setPreferredSize(new java.awt.Dimension(100, 100));
        buttonRunQueryF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonRunQueryFMouseClicked(evt);
            }
        });

        buttonRunQueryG.setText("Run Query G");
        buttonRunQueryG.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRunQueryG.setMinimumSize(new java.awt.Dimension(100, 100));
        buttonRunQueryG.setPreferredSize(new java.awt.Dimension(100, 100));
        buttonRunQueryG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonRunQueryGMouseClicked(evt);
            }
        });

        buttonRunQueryH.setText("Run Query H");
        buttonRunQueryH.setMaximumSize(new java.awt.Dimension(100, 100));
        buttonRunQueryH.setMinimumSize(new java.awt.Dimension(100, 100));
        buttonRunQueryH.setPreferredSize(new java.awt.Dimension(100, 100));
        buttonRunQueryH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonRunQueryHMouseClicked(evt);
            }
        });

        textfieldFirstName.setMaximumSize(new java.awt.Dimension(100, 20));
        textfieldFirstName.setMinimumSize(new java.awt.Dimension(100, 20));
        textfieldFirstName.setPreferredSize(new java.awt.Dimension(100, 20));

        textfieldMiddleName.setMaximumSize(new java.awt.Dimension(100, 20));
        textfieldMiddleName.setMinimumSize(new java.awt.Dimension(100, 20));
        textfieldMiddleName.setPreferredSize(new java.awt.Dimension(100, 20));

        textfieldLastName.setMaximumSize(new java.awt.Dimension(100, 20));
        textfieldLastName.setMinimumSize(new java.awt.Dimension(100, 20));
        textfieldLastName.setPreferredSize(new java.awt.Dimension(100, 20));

        jLabel3.setText("First Name");
        jLabel3.setMaximumSize(new java.awt.Dimension(80, 14));
        jLabel3.setMinimumSize(new java.awt.Dimension(80, 14));
        jLabel3.setPreferredSize(new java.awt.Dimension(80, 14));

        jLabel4.setText("Middle Name");
        jLabel4.setMaximumSize(new java.awt.Dimension(80, 14));
        jLabel4.setMinimumSize(new java.awt.Dimension(80, 14));
        jLabel4.setPreferredSize(new java.awt.Dimension(80, 14));

        jLabel11.setText("Last Name");
        jLabel11.setMaximumSize(new java.awt.Dimension(80, 14));
        jLabel11.setMinimumSize(new java.awt.Dimension(80, 14));
        jLabel11.setPreferredSize(new java.awt.Dimension(80, 14));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jTextArea3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(textfieldFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(textfieldMiddleName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(textfieldLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jTextArea4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(89, 89, 89)))
                        .addGap(190, 190, 190))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jTextArea1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(277, 277, 277)
                                .addComponent(buttonRunQueryA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jTextArea7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(277, 277, 277)
                                .addComponent(buttonRunQueryF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jTextArea8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(277, 277, 277)
                                .addComponent(buttonRunQueryG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jTextArea2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(280, 280, 280)
                                .addComponent(buttonRunQueryH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(jTextArea6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(jTextArea5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(277, 277, 277)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(buttonRunQueryD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buttonRunQueryE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buttonRunQueryC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buttonRunQueryB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextArea1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonRunQueryA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextArea3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textfieldFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(textfieldMiddleName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textfieldLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonRunQueryB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextArea4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonRunQueryC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextArea5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonRunQueryD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextArea6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonRunQueryE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextArea7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonRunQueryF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextArea8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonRunQueryG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextArea2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonRunQueryH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel6.getAccessibleContext().setAccessibleName("Query ");
        buttonRunQueryA.getAccessibleContext().setAccessibleName("");

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))
        );

        splitpaneMain.setLeftComponent(jPanel5);

        splitpaneTabItem.setDividerLocation(200);
        splitpaneTabItem.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        labelQueryTabItem.setText("Queries");

        textareaQueryTabItem.setEditable(false);
        textareaQueryTabItem.setColumns(20);
        textareaQueryTabItem.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        textareaQueryTabItem.setLineWrap(true);
        textareaQueryTabItem.setRows(1);
        textareaQueryTabItem.setTabSize(1);
        textareaQueryTabItem.setWrapStyleWord(true);
        scrollpaneQueryTabItem.setViewportView(textareaQueryTabItem);

        javax.swing.GroupLayout panelQueryTabItemLayout = new javax.swing.GroupLayout(panelQueryTabItem);
        panelQueryTabItem.setLayout(panelQueryTabItemLayout);
        panelQueryTabItemLayout.setHorizontalGroup(
            panelQueryTabItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelQueryTabItemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelQueryTabItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollpaneQueryTabItem, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panelQueryTabItemLayout.createSequentialGroup()
                        .addComponent(labelQueryTabItem)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelQueryTabItemLayout.setVerticalGroup(
            panelQueryTabItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelQueryTabItemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelQueryTabItem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollpaneQueryTabItem, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                .addContainerGap())
        );

        splitpaneTabItem.setTopComponent(panelQueryTabItem);

        labelResultTabItem.setText("Results");

        textareaResultTabItem.setEditable(false);
        textareaResultTabItem.setColumns(20);
        textareaResultTabItem.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        textareaResultTabItem.setRows(1);
        textareaResultTabItem.setTabSize(1);
        textareaResultTabItem.setWrapStyleWord(true);
        scrollpaneResultTabItem.setViewportView(textareaResultTabItem);

        javax.swing.GroupLayout panelResultTabItemLayout = new javax.swing.GroupLayout(panelResultTabItem);
        panelResultTabItem.setLayout(panelResultTabItemLayout);
        panelResultTabItemLayout.setHorizontalGroup(
            panelResultTabItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResultTabItemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelResultTabItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollpaneResultTabItem, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addGroup(panelResultTabItemLayout.createSequentialGroup()
                        .addComponent(labelResultTabItem)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelResultTabItemLayout.setVerticalGroup(
            panelResultTabItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResultTabItemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelResultTabItem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollpaneResultTabItem, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                .addContainerGap())
        );

        splitpaneTabItem.setRightComponent(panelResultTabItem);

        javax.swing.GroupLayout panelTabItemLayout = new javax.swing.GroupLayout(panelTabItem);
        panelTabItem.setLayout(panelTabItemLayout);
        panelTabItemLayout.setHorizontalGroup(
            panelTabItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTabItemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(splitpaneTabItem)
                .addContainerGap())
        );
        panelTabItemLayout.setVerticalGroup(
            panelTabItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTabItemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(splitpaneTabItem)
                .addGap(0, 0, 0))
        );

        resultTabs.addTab("Query A", panelTabItem);

        splitpaneMain.setRightComponent(resultTabs);
        resultTabs.getAccessibleContext().setAccessibleName("");
        resultTabs.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(splitpaneMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(splitpaneMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonRunQueryAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonRunQueryAMouseClicked
        runQueryA();
    }//GEN-LAST:event_buttonRunQueryAMouseClicked

    private void buttonRunQueryBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonRunQueryBMouseClicked
        runQueryB();
    }//GEN-LAST:event_buttonRunQueryBMouseClicked

    private void buttonRunQueryCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonRunQueryCMouseClicked
        runQueryC();
    }//GEN-LAST:event_buttonRunQueryCMouseClicked

    private void buttonRunQueryDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonRunQueryDMouseClicked
        runQueryD();
    }//GEN-LAST:event_buttonRunQueryDMouseClicked

    private void buttonRunQueryEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonRunQueryEMouseClicked
        runQueryE();
    }//GEN-LAST:event_buttonRunQueryEMouseClicked

    private void buttonRunQueryFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonRunQueryFMouseClicked
        runQueryF();
    }//GEN-LAST:event_buttonRunQueryFMouseClicked

    private void buttonRunQueryGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonRunQueryGMouseClicked
        runQueryG();
    }//GEN-LAST:event_buttonRunQueryGMouseClicked

    private void buttonRunQueryHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonRunQueryHMouseClicked
        runQueryH();
    }//GEN-LAST:event_buttonRunQueryHMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DialogAlumniQueries.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogAlumniQueries.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogAlumniQueries.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogAlumniQueries.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogAlumniQueries dialog = new DialogAlumniQueries(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonRunQueryA;
    private javax.swing.JButton buttonRunQueryB;
    private javax.swing.JButton buttonRunQueryC;
    private javax.swing.JButton buttonRunQueryD;
    private javax.swing.JButton buttonRunQueryE;
    private javax.swing.JButton buttonRunQueryF;
    private javax.swing.JButton buttonRunQueryG;
    private javax.swing.JButton buttonRunQueryH;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextArea jTextArea6;
    private javax.swing.JTextArea jTextArea7;
    private javax.swing.JTextArea jTextArea8;
    private javax.swing.JLabel labelQueryTabItem;
    private javax.swing.JLabel labelResultTabItem;
    private javax.swing.JPanel panelQueryTabItem;
    private javax.swing.JPanel panelResultTabItem;
    private javax.swing.JPanel panelTabItem;
    private javax.swing.JTabbedPane resultTabs;
    private javax.swing.JScrollPane scrollpaneQueryTabItem;
    private javax.swing.JScrollPane scrollpaneResultTabItem;
    private javax.swing.JSplitPane splitpaneMain;
    private javax.swing.JSplitPane splitpaneTabItem;
    private javax.swing.JTextArea textareaQueryTabItem;
    private javax.swing.JTextArea textareaResultTabItem;
    private javax.swing.JTextField textfieldFirstName;
    private javax.swing.JTextField textfieldLastName;
    private javax.swing.JTextField textfieldMiddleName;
    // End of variables declaration//GEN-END:variables
}
