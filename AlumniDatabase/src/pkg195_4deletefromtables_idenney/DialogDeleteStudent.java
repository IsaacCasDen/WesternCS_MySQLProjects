/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg195_4deletefromtables_idenney;

import Common.CommandItem;
import Common.ListItem;
import Common.Student;
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
public class DialogDeleteStudent extends javax.swing.JDialog {

    private JList getList() {
        return this.mainList;
    }
    
    /**
     * Creates new form DialogDeleteStudent
     */
    public DialogDeleteStudent(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void init() {
        refreshStudents();
        getList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                refreshViewPanel();
            }
            
        });
        refreshViewPanel();
    }
    private void refreshViewPanel() {
        this.buttonDeleteStudent.setEnabled(getList().getSelectedValue()!=null);
    }
    
    private void deleteStudent() {
        CommandItem[] commands=null;
        if (this.getList().getSelectedValue()!=null) {
            int mId = ((ListItem)this.getList().getSelectedValue()).getIndex();
            commands=Main.DELETE_ROW_TABLE_STUDENTS();
            commands[0].setQuery(commands[0].getQuery().replace("{0}",String.valueOf(mId)));
        }
        if (commands!=null) {
            Main.showCommands(commands);
            ArrayList result = Main.runCommands(commands);
            if (result!=null&&result.size()>0) {
                if ((int)result.get(0)==1) {
                    this.refreshStudents();
                }
            }
        }
    }
    
    private void refreshStudents() {
        CommandItem[] commands = Main.SELECT_ROWS_TABLE_STUDENTS();
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
                
                updateStudents(list,dt);
                addStudents(list,dt);
                removeStudents(list,dt);

                this.getList().setModel(list);
            } 
        }
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

        panel_StudentList = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelAddEditDelete = new javax.swing.JPanel();
        buttonDeleteStudent = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        mainList = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel_StudentList.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panel_StudentList.setMaximumSize(new java.awt.Dimension(256, 32768));
        panel_StudentList.setMinimumSize(new java.awt.Dimension(256, 384));

        jLabel1.setText("Students");

        panelAddEditDelete.setName("panelAddEditDelete"); // NOI18N

        buttonDeleteStudent.setText("Delete");
        buttonDeleteStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonDeleteStudentMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelAddEditDeleteLayout = new javax.swing.GroupLayout(panelAddEditDelete);
        panelAddEditDelete.setLayout(panelAddEditDeleteLayout);
        panelAddEditDeleteLayout.setHorizontalGroup(
            panelAddEditDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddEditDeleteLayout.createSequentialGroup()
                .addContainerGap(169, Short.MAX_VALUE)
                .addComponent(buttonDeleteStudent)
                .addContainerGap())
        );
        panelAddEditDeleteLayout.setVerticalGroup(
            panelAddEditDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddEditDeleteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonDeleteStudent)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        mainList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(mainList);

        javax.swing.GroupLayout panel_StudentListLayout = new javax.swing.GroupLayout(panel_StudentList);
        panel_StudentList.setLayout(panel_StudentListLayout);
        panel_StudentListLayout.setHorizontalGroup(
            panel_StudentListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_StudentListLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_StudentListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_StudentListLayout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addContainerGap())
                    .addGroup(panel_StudentListLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(panelAddEditDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panel_StudentListLayout.setVerticalGroup(
            panel_StudentListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_StudentListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelAddEditDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_StudentList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_StudentList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonDeleteStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonDeleteStudentMouseClicked
        if (buttonDeleteStudent.isEnabled()) {
            deleteStudent();
        }
    }//GEN-LAST:event_buttonDeleteStudentMouseClicked

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
            java.util.logging.Logger.getLogger(DialogDeleteStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogDeleteStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogDeleteStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogDeleteStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogDeleteStudent dialog = new DialogDeleteStudent(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton buttonDeleteStudent;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> mainList;
    private javax.swing.JPanel panelAddEditDelete;
    private javax.swing.JPanel panel_StudentList;
    // End of variables declaration//GEN-END:variables
}
