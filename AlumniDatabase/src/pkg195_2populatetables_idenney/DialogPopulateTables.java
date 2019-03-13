/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg195_2populatetables_idenney;

import Common.CommandItem;
import Common.ListItem;
import Common.Major;
import Common.Student;
import DataTable.DataRow;
import DataTable.DataTable;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 *
 * @author Isaac Denney
 */
public class DialogPopulateTables extends javax.swing.JDialog {

    /**
     * Creates new form DialogPopulateTables
     */
    public DialogPopulateTables(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initialize();
    }

    private void initialize() {
        refreshMajors();
        refreshStudents();
    }
    
    private void refreshMajors() {
        DefaultListModel list = null;
        try {
            list=(DefaultListModel)this.list_Majors.getModel(); 
        } catch (Exception ex) {

        }
        if (list==null) {list=new DefaultListModel();}
        
        CommandItem[] commands=Main.SELECT_ROWS_TABLE_MAJORSINFO();
        Main.showCommands(commands);
        ArrayList value = Main.runCommands(commands);
        if (value!=null&&value.size()>0) {
            DataTable dt = (DataTable)value.get(0);
            if (dt!=null) {
                
                
                updateMajors(list,dt);
                addMajors(list,dt);
                removeMajors(list,dt);
            } 
        } else {
            list.removeAllElements();
        }
        
        this.list_Majors.setModel(list);
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
                    Major major=new Major((int)row.getValue(colId),(String)row.getValue(colAbbr),(String)row.getValue(colName));
                    if (lItem.getIndex()==(int)row.getValue(colId)) {
                        lItem.setDetail(
                            major.toString()
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
                    Major major=new Major((int)row.getValue(colId),(String)row.getValue(colAbbr),(String)row.getValue(colName));
                    if (row.getValue(colId)==listId.get(i)) {
                        ListItem item = new ListItem(
                        (int)row.getValue(colId),
                        major.toString()
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
    
    private void refreshStudents() {
        DefaultListModel list = null;
        try {
            list=(DefaultListModel)this.list_Students.getModel(); 
        } catch (Exception ex) {

        }
        if (list==null) {list=new DefaultListModel();}
                
        CommandItem[] commands=Main.SELECT_ROWS_TABLE_STUDENTS();
        Main.showCommands(commands);
        ArrayList value = Main.runCommands(commands);
        if (value!=null&&value.size()>0) {
            DataTable dt = (DataTable)value.get(0);
            if (dt!=null) {
                
                
                updateStudents(list,dt);
                addStudents(list,dt);
                removeStudents(list,dt);

                
            } 
        } else {
            list.removeAllElements();
        }
        
        this.list_Students.setModel(list);
    }
    private void updateStudents(DefaultListModel list, DataTable newList) {
        if (list!=null&&newList!=null) {
            int colId = newList.getColumnIndex("id");
            int colNameF=newList.getColumnIndex("firstName");
            int colNameM=newList.getColumnIndex("middleName");
            int colNameL=newList.getColumnIndex("lastName");
            for (int i=0;i<list.size();i++) {
                ListItem lItem=(ListItem)list.get(i);
                for (int r=0;r<newList.getRowCount();r++) {
                    DataRow row =newList.getRow(r);
                    if (lItem.getIndex()==(int)row.getValue(colId)) {
                        Student student=new Student(
                                (int)row.getValue(colId),
                                (String)row.getValue(colNameF),
                                (String)row.getValue(colNameM),
                                (String)row.getValue(colNameL));
                        
                        lItem.setDetail(student.toString());
                        break;
                    }
                }
            }
        }
    }
    private void addStudents(DefaultListModel list, DataTable newList) {
        if (list!=null&&newList!=null) {
            int colNameF=newList.getColumnIndex("firstName");
            int colNameM=newList.getColumnIndex("middleName");
            int colNameL=newList.getColumnIndex("lastName");
            ArrayList listId = new ArrayList();
            int colId = newList.getColumnIndex("id");
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
                        Student student=new Student(
                                (int)row.getValue(colId),
                                (String)row.getValue(colNameF),
                                (String)row.getValue(colNameM),
                                (String)row.getValue(colNameL));
                        
                        ListItem item = new ListItem(
                        (int)row.getValue(colId),
                            student.toString()
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
    private void removeStudents(DefaultListModel list, DataTable newList) {
        if (list!=null&&newList!=null) {
            ArrayList listId = new ArrayList();
            int colId=newList.getColumnIndex("id");
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        list_Majors = new javax.swing.JList<>();
        buttonEditMajors = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        list_Students = new javax.swing.JList<>();
        buttonEditStudents = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Majors");

        list_Majors.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        list_Majors.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jScrollPane1.setViewportView(list_Majors);

        buttonEditMajors.setText("Edit Majors");
        buttonEditMajors.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonEditMajorsMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(buttonEditMajors))
                        .addGap(0, 137, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonEditMajors)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jLabel2.setText("Students");

        list_Students.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        list_Students.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jScrollPane2.setViewportView(list_Students);

        buttonEditStudents.setText("Edit Students");
        buttonEditStudents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonEditStudentsMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(buttonEditStudents))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonEditStudents)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonEditMajorsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonEditMajorsMouseClicked
        setVisible(false);
        DialogPopulateMajorsInfo dialog = new DialogPopulateMajorsInfo(null,true);
        dialog.setVisible(true);
        refreshMajors();
        setVisible(true);
    }//GEN-LAST:event_buttonEditMajorsMouseClicked

    private void buttonEditStudentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonEditStudentsMouseClicked
        setVisible(false);
        DialogPopulateStudents dialog = new DialogPopulateStudents(null,true);
        dialog.setVisible(true);
        refreshStudents();
        setVisible(true);
    }//GEN-LAST:event_buttonEditStudentsMouseClicked

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
            java.util.logging.Logger.getLogger(DialogPopulateTables.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogPopulateTables.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogPopulateTables.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogPopulateTables.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogPopulateTables dialog = new DialogPopulateTables(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton buttonEditMajors;
    private javax.swing.JButton buttonEditStudents;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> list_Majors;
    private javax.swing.JList<String> list_Students;
    // End of variables declaration//GEN-END:variables
}
