/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg195_2populatetables_idenney;

import Common.CommandItem;
import Common.ListItem;
import Common.Major;
import DataTable.DataRow;
import DataTable.DataTable;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Isaac Denney
 */
public class DialogPopulateMajorsInfo extends javax.swing.JDialog {

    private boolean getIsEditable() {
        return !this.panelMajorsList.isEnabled();
    } 
    private void setIsEditable(boolean value) {
        this.getList().setEnabled(!value);
        this.buttonAdd.setEnabled(!value);
        this.buttonEdit.setEnabled(!value);
        this.buttonDelete.setEnabled(!value);
        this.buttonSave.setEnabled(value);
        this.buttonCancel.setEnabled(value);
        this.textfieldMajorAbbr.setEnabled(value);
        this.textfieldMajorName.setEnabled(value);
    }
    private JList getList() {
        return this.mainList;
    }
    
    private String getMajorAbbr() {
        return this.textfieldMajorAbbr.getText();
    }
    private void setMajorAbbr(String value) {
        this.textfieldMajorAbbr.setText(value);
    }
    
    private String getMajorName() {
        return this.textfieldMajorName.getText();
    }
    private void setMajorName(String value) {
        this.textfieldMajorName.setText(value);
    }
    
    /**
     * Creates new form DialogMajorsInfo
     */
    public DialogPopulateMajorsInfo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void init() {
        setIsEditable(false);
        refreshList();
        setListener();
        updateEditPanel();
    }
    private void refreshList() {
        getList().removeAll();
        CommandItem[] commands = Main.SELECT_ROWS_TABLE_MAJORSINFO();
        Main.showCommands(commands);
        ArrayList value = Main.runCommands(commands);
        if (value!=null&&value.size()>0) {
            DataTable dt = (DataTable)value.get(0);
            if (dt!=null) {
                DefaultListModel list = null;
                try {
                    list=(DefaultListModel)getList().getModel(); 
                } catch (Exception ex) {
                        
                }
                if (list==null) {list=new DefaultListModel();}
                
                updateMajors(list,dt);
                removeMajors(list,dt);
                addMajors(list,dt);

                this.getList().setModel(list);
            } 
        }
    }
    private void updateMajors(DefaultListModel list, DataTable newList) {
        if (list!=null&&newList!=null) {
            int colId=newList.getColumnIndex("id");
            int colAbbr=newList.getColumnIndex("majorAbbr");
            int colName=newList.getColumnIndex("majorName");
            for (int i=0;i<list.size();i++) {
                ListItem lItem=(ListItem)list.get(i);
                for (int r=0;r<newList.getRowCount();r++) {
                    DataRow row =newList.getRow(r);
                    if (lItem.getIndex()==(int)row.getValue(colId)) {
                        lItem.setDetail(
                            (String)row.getValue(colAbbr) + " - " +
                            (String)row.getValue(colName)
                        );
                        break;
                    }
                }
            }
        }
    }
    private void addMajors(DefaultListModel list, DataTable newList) {
        if (list!=null&&newList!=null) {
            ArrayList listId = new ArrayList();
            int colId=newList.getColumnIndex("id");
            int colAbbr=newList.getColumnIndex("majorAbbr");
            int colName=newList.getColumnIndex("majorName");
            for (int r=0;r<newList.getRowCount();r++) {
                boolean hasRow = false;
                DataRow row =newList.getRow(r);
                for (int i=0;i<list.size();i++) {
                    ListItem lItem=(ListItem)list.get(i);
                    if (lItem.getIndex()==(int)row.getValue(colId)) {
                        hasRow=true;
                        break;
                    }
                }
                if (!hasRow) {
                    listId.add(row.getValue(colId));
                }
            }
            for (int i=0;i<listId.size();i++) {
                for (int r=0;r<newList.getRowCount();r++) {
                    DataRow row = newList.getRow(r);
                    if (row.getValue(colId)==listId.get(i)) {
                        ListItem item = new ListItem(
                        (int)row.getValue(colId),
                        (String)row.getValue(colAbbr) + " - " +
                                (String)row.getValue(colName)
                        );
                        list.addElement(item);
                        break;
                    }
                }
            }
        }
        
        /*for (int i=0;i<dt.getRowCount();i++) {
            DataRow row = dt.getRow(i);
            ListItem item = new ListItem(
                (int)row.getValue(colId),
                (String)row.getValue(colAbbr) + " - " +
                        (String)row.getValue(colName)
            );
            list.add(i, item);
        }*/
        
    }
    private void removeMajors(DefaultListModel list, DataTable newList) {
        if (list!=null&&newList!=null) {
            ArrayList listId = new ArrayList();
            int colId=newList.getColumnIndex("id");
            int colAbbr=newList.getColumnIndex("majorAbbr");
            int colName=newList.getColumnIndex("majorName");
            
            for (int i=0;i<list.size();i++) {
                boolean hasItem=false;    
                ListItem lItem=(ListItem)list.get(i);
                for (int r=0;r<newList.getRowCount();r++) {
                    DataRow row =newList.getRow(r);
                    if (lItem.getIndex()==(int)row.getValue(colId)) {
                        hasItem=true;
                        break;
                    }
                }
                if (!hasItem) {
                    listId.add(lItem.getIndex());
                }
            }
            
            for (int i=0;i<listId.size();i++) {
                for (int l=0;l<list.size();l++) {
                    ListItem lItem=(ListItem)list.get(l);
                    if ((int)listId.get(i)==lItem.getIndex()) {
                        list.remove(l);
                        break;
                    }
                }
            }
        }
    }
    private void setListener() {
        getList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateEditPanel();
            }
        });
    }
    private void addItem() {
        getList().clearSelection();
        this.setIsEditable(true);
    }
    private void editItem() {
        this.setIsEditable(true);
    }
    private void deleteItem(){
        CommandItem[] commands=null;
        if (getList().getSelectedValue()!=null) {
            int mId = ((ListItem)getList().getSelectedValue()).getIndex();
            commands=Main.DELETE_ROW_TABLE_MAJORSINFO();
            commands[0].setQuery(commands[0].getQuery().replace("{0}",String.valueOf(mId)));
        }
        if (commands!=null) {
            Main.showCommands(commands);
            ArrayList result = Main.runCommands(commands);
            if (result!=null&&result.size()>0) {
                if ((int)result.get(0)==1) {
                    refreshList();
                    this.updateEditPanel();
                    setIsEditable(false);

                }
            }
        }
    }
    
    private void confirmSaveItem() {
        String mAbbr=this.getMajorAbbr().trim();
        String mName=this.getMajorName().trim();
        if (mAbbr.length()>0&&mName.length()>0)  {
            CommandItem[] commands=null;
            if (getList().getSelectedValue()==null) {
                commands=Main.INSERT_ROW_TABLE_MAJORSINFO();
                commands[0].setQuery(commands[0].getQuery().replace("{0}",mAbbr).replace("{1}", mName));
            } else {
                int mId = ((ListItem)getList().getSelectedValue()).getIndex();
                commands=Main.UPDATE_ROW_TABLE_MAJORSINFO();
                commands[0].setQuery(commands[0].getQuery().replace("{0}",mAbbr).replace("{1}", mName).replace("{2}", String.valueOf(mId)));
            }
            if (commands!=null) {
                Main.showCommands(commands);
                ArrayList result = Main.runCommands(commands);
                if (result!=null&&result.size()>0) {
                    if ((int)result.get(0)==1) {
                        refreshList();
                        this.updateEditPanel();
                        setIsEditable(false);
                        
                    }
                }
            }
        }
    }
    private void cancelSaveItem() {
        this.setIsEditable(false);
        this.updateEditPanel();
    }
            
    private void updateEditPanel() {
        ListItem value = (ListItem)getList().getSelectedValue();
        if (value==null) {
            this.setMajorAbbr("");
            this.setMajorName("");
            this.buttonEdit.setEnabled(false);
            this.buttonDelete.setEnabled(false);
        } else {
            Major major = getMajor(value.getIndex());
            if (major!=null) {
                this.setMajorAbbr(major.getAbbr());
                this.setMajorName(major.getName());
                this.buttonEdit.setEnabled(true);
                this.buttonDelete.setEnabled(true);
            }
        }
    }
    private Major getMajor(int id) {
        Major value = null;
        CommandItem[] commands=Main.SELECT_ROW_TABLE_MAJORSINFO();
        commands[0].setQuery(commands[0].getQuery().replace("{0}", String.valueOf(id)));
        
        Main.showCommands(commands);
        ArrayList values = Main.runCommands(commands);
        
        if (values!=null&&values.size()>0) {
            DataTable dt = (DataTable)values.get(0);
            if (dt!=null) {
                DataRow dr=dt.getRow(0);
                int colId = dt.getColumnIndex("id");
                int colAbbr=dt.getColumnIndex("majorAbbr");
                int colName=dt.getColumnIndex("majorName");
                
                
                value=new Major((int)dr.getValue(colId),(String)dr.getValue(colAbbr),(String)dr.getValue(colName));
            } 
        }
        return value;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        panelMajorsList = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelAddEditDelete = new javax.swing.JPanel();
        buttonAdd = new javax.swing.JButton();
        buttonEdit = new javax.swing.JButton();
        buttonDelete = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        mainList = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        textfieldMajorAbbr = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        textfieldMajorName = new javax.swing.JTextField();
        panelSaveCancel = new javax.swing.JPanel();
        buttonCancel = new javax.swing.JButton();
        buttonSave = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Populate Major Information");
        setMaximumSize(new java.awt.Dimension(626, 2147483647));
        setMinimumSize(new java.awt.Dimension(626, 324));
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setPreferredSize(new java.awt.Dimension(626, 324));
        setResizable(false);
        setSize(new java.awt.Dimension(626, 324));

        jPanel2.setMaximumSize(new java.awt.Dimension(626, 32767));
        jPanel2.setMinimumSize(new java.awt.Dimension(626, 300));
        jPanel2.setPreferredSize(new java.awt.Dimension(626, 300));

        panelMajorsList.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel1.setText("Majors");

        panelAddEditDelete.setName("panelAddEditDelete"); // NOI18N

        buttonAdd.setText("Add");
        buttonAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonAddMouseClicked(evt);
            }
        });

        buttonEdit.setText("Edit");
        buttonEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonEditMouseClicked(evt);
            }
        });

        buttonDelete.setText("Delete");
        buttonDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonDeleteMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelAddEditDeleteLayout = new javax.swing.GroupLayout(panelAddEditDelete);
        panelAddEditDelete.setLayout(panelAddEditDeleteLayout);
        panelAddEditDeleteLayout.setHorizontalGroup(
            panelAddEditDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddEditDeleteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonDelete)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAddEditDeleteLayout.setVerticalGroup(
            panelAddEditDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddEditDeleteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelAddEditDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonAdd)
                    .addComponent(buttonEdit)
                    .addComponent(buttonDelete))
                .addContainerGap())
        );

        mainList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(mainList);

        javax.swing.GroupLayout panelMajorsListLayout = new javax.swing.GroupLayout(panelMajorsList);
        panelMajorsList.setLayout(panelMajorsListLayout);
        panelMajorsListLayout.setHorizontalGroup(
            panelMajorsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMajorsListLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMajorsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMajorsListLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                        .addGap(33, 33, 33))
                    .addGroup(panelMajorsListLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelMajorsListLayout.createSequentialGroup()
                        .addComponent(panelAddEditDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 33, Short.MAX_VALUE))))
        );
        panelMajorsListLayout.setVerticalGroup(
            panelMajorsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMajorsListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAddEditDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel3.setText("Name");

        textfieldMajorAbbr.setName("textfield_FirstName"); // NOI18N

        jLabel2.setText("Abbreviation");

        textfieldMajorName.setName("textfield_MiddleName"); // NOI18N

        panelSaveCancel.setName("panelSaveCancel"); // NOI18N

        buttonCancel.setText("Cancel");
        buttonCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonCancelMouseClicked(evt);
            }
        });

        buttonSave.setText("Save");
        buttonSave.setMaximumSize(new java.awt.Dimension(65, 23));
        buttonSave.setMinimumSize(new java.awt.Dimension(65, 23));
        buttonSave.setPreferredSize(new java.awt.Dimension(65, 23));
        buttonSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonSaveMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelSaveCancelLayout = new javax.swing.GroupLayout(panelSaveCancel);
        panelSaveCancel.setLayout(panelSaveCancelLayout);
        panelSaveCancelLayout.setHorizontalGroup(
            panelSaveCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSaveCancelLayout.createSequentialGroup()
                .addContainerGap(119, Short.MAX_VALUE)
                .addComponent(buttonSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCancel)
                .addContainerGap())
        );
        panelSaveCancelLayout.setVerticalGroup(
            panelSaveCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSaveCancelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSaveCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCancel)
                    .addComponent(buttonSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setText("Major Information");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(panelSaveCancel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                            .addComponent(textfieldMajorAbbr))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textfieldMajorName)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textfieldMajorAbbr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textfieldMajorName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 158, Short.MAX_VALUE)
                .addComponent(panelSaveCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 626, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panelMajorsList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(422, Short.MAX_VALUE)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(273, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panelMajorsList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonAddMouseClicked
        if (buttonAdd.isEnabled()) {
            addItem();
        }
    }//GEN-LAST:event_buttonAddMouseClicked

    private void buttonEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonEditMouseClicked
        if (buttonEdit.isEnabled()) {
            editItem();
        }
    }//GEN-LAST:event_buttonEditMouseClicked

    private void buttonDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonDeleteMouseClicked
        if (buttonDelete.isEnabled()) {
            deleteItem();
        }
    }//GEN-LAST:event_buttonDeleteMouseClicked

    private void buttonCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonCancelMouseClicked
        if (buttonCancel.isEnabled()) {
            cancelSaveItem();
        }
    }//GEN-LAST:event_buttonCancelMouseClicked

    private void buttonSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonSaveMouseClicked
        if (buttonSave.isEnabled()) {
            confirmSaveItem();
        }
    }//GEN-LAST:event_buttonSaveMouseClicked

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
            java.util.logging.Logger.getLogger(DialogPopulateMajorsInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogPopulateMajorsInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogPopulateMajorsInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogPopulateMajorsInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogPopulateMajorsInfo dialog = new DialogPopulateMajorsInfo(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton buttonAdd;
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonDelete;
    private javax.swing.JButton buttonEdit;
    private javax.swing.JButton buttonSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> mainList;
    private javax.swing.JPanel panelAddEditDelete;
    private javax.swing.JPanel panelMajorsList;
    private javax.swing.JPanel panelSaveCancel;
    private javax.swing.JTextField textfieldMajorAbbr;
    private javax.swing.JTextField textfieldMajorName;
    // End of variables declaration//GEN-END:variables
}
