/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg195_3populateprivate_idenney;

import Common.CommandItem;
import Common.Comment;
import Common.ListItem;
import Common.PrivateInfo;
import Common.Student;
import DataTable.DataRow;
import DataTable.DataTable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Isaac Denney
 */
public class DialogPopulateStudentPrivateInfo extends javax.swing.JDialog {

    private boolean getIsEditable() {
        return false; //return !this.panelMajorsList.isEnabled();
    } 
    private void setIsEditable(boolean value) {
        
        this.getList().setEnabled(!value);
        this.textfieldSSN1.setEnabled(value);
        this.textfieldSSN2.setEnabled(value);
        this.textfieldSSN3.setEnabled(value);
        
        this.buttonEditStudent.setEnabled(!value);
        this.buttonConfirmSaveStudent.setEnabled(value);
        this.buttonCancelSaveStudent.setEnabled(value);
        
        this.checkboxNoContact.setEnabled(value);
        this.checkboxDonate.setEnabled(value);
        this.checkboxPHDTrack.setEnabled(value);
        
        this.textareaNewComment.setEnabled(value);
        this.buttonCommentSave.setEnabled(value);
    }
     
    private JList getList() {
        return this.mainList;
    }
    
    private boolean getIsValidSSN() {
        return getSSN().length()==11;
    }
    private String getSSN() {
        return getSSN1() + "-" + getSSN2() + "-" + getSSN3();
    }
    
    private String getSSN1() {
        return this.textfieldSSN1.getText();
    }
    private void setSSN1(String value) {
        this.textfieldSSN1.setText(value);
    }
    private String getSSN2() {
        return this.textfieldSSN2.getText();
    }
    private void setSSN2(String value) {
        this.textfieldSSN2.setText(value);
    }
    private String getSSN3() {
        return this.textfieldSSN3.getText();
    }
    private void setSSN3(String value) {
        this.textfieldSSN3.setText(value);
    }
    
    private boolean getNoContact() {
        return this.checkboxNoContact.isSelected();
    }
    private void setNoContact(boolean value) {
        this.checkboxNoContact.setSelected(value);
    }
    private String getNewCommentText() {
        return this.textareaNewComment.getText();
    }
    
    private boolean getWillDonate() {
        return this.checkboxDonate.isSelected();
    }
    private void setWillDonate(boolean value) {
        this.checkboxDonate.setSelected(value);
    }
    
    private boolean getPHDTrack() {
        return this.checkboxPHDTrack.isSelected();
    }
    private void setPHDTrack(boolean value) {
        this.checkboxPHDTrack.setSelected(value);
    }
    
    private void refreshStudents() {
        CommandItem[] commands=Main.SELECT_ROWS_TABLE_STUDENTS();
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
     * Creates new form DialogPopulateStudents
     */
    public DialogPopulateStudentPrivateInfo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void init() {
        this.setIsEditable(false);
        setListeners();
        refreshStudents();
        updateViewPanel();
        refreshComments();
    }
    private void setListeners() {
        getList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateViewPanel();
            }
        });
    }
    private void updateViewPanel() {
        ListItem value = (ListItem)getList().getSelectedValue();
        if (value==null) {
            this.buttonEditStudent.setEnabled(false);
            this.setSSN1("");
            this.setSSN2("");
            this.setSSN3("");
            this.setNoContact(false);
            this.setWillDonate(false);
            this.setPHDTrack(false);
        } else {
            PrivateInfo info = getPrivate(value.getIndex());
            if (info!=null) {
                this.buttonEditStudent.setEnabled(true);
                this.setSSN1(info.getSSN()[0]);
                this.setSSN2(info.getSSN()[1]);
                this.setSSN3(info.getSSN()[2]);
                this.setNoContact(info.getCanContact());
                this.setWillDonate(info.getCanDonate());
                this.setPHDTrack(info.getTrackPHD());
                refreshComments();
            }
        }
    }
    private PrivateInfo getPrivate(int id) {
        PrivateInfo value = null;
        CommandItem[] commands=Main.SELECT_ROW_TABLE_PRIVATE();
        commands[0].setQuery(commands[0].getQuery().replace("{0}", String.valueOf(id)));
        
        Main.showCommands(commands);
        ArrayList values = Main.runCommands(commands);
        
        if (values!=null&&values.size()>0) {
            DataTable dt = (DataTable)values.get(0);
            if (dt!=null) {
                DataRow dr=dt.getRow(0);
                int colId = dt.getColumnIndex("studentId");
                int colSSN=dt.getColumnIndex("ssn");
                int colContact=dt.getColumnIndex("noContact");
                int colDonate=dt.getColumnIndex("canDonate");
                int colTrackPHD=dt.getColumnIndex("trackPHD");
                
                try {
                    value=new PrivateInfo(
                        (int)dr.getValue(colId),(String)dr.getValue(colSSN),
                        (boolean)dr.getValue(colContact),(boolean)dr.getValue(colDonate),(boolean)dr.getValue(colTrackPHD));
                } catch (Exception ex) {
                    java.util.logging.Logger.getLogger(DialogPopulateStudentPrivateInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            } 
        }
        return value;
    }
    
    private void updatePrivateView() {
        this.updateViewPanel();
    }
    private void editPrivate() {
        this.setIsEditable(true);
    }
    private void confirmSavePrivate() {
        CommandItem[] commands=null;
        if (getList().getSelectedValue()!=null) {
            int sId = ((ListItem)getList().getSelectedValue()).getIndex();
            commands=Main.UPDATE_ROW_TABLE_PRIVATE();
            String ssn; if (this.getIsValidSSN()) {ssn=this.getSSN();} else {ssn="";}
            commands[0].setQuery(commands[0].getQuery()
                    .replace("{0}", ssn)
                    .replace("{1}", String.valueOf(this.getNoContact()))
                    .replace("{2}", String.valueOf(this.getWillDonate()))
                    .replace("{3}", String.valueOf(this.getPHDTrack()))
                    .replace("{4}", String.valueOf(sId))
            );
        }
        if (commands!=null) {
            Main.showCommands(commands);
            ArrayList result = Main.runCommands(commands);
            if (result!=null&&result.size()>0) {
                if ((int)result.get(0)==1) {
                    this.updatePrivateView();
                    this.setIsEditable(false);
                }
            }
        }
        
    }
    private void cancelSavePrivate() {
        this.setIsEditable(false);
    }
    
    private void refreshComments() {
        DefaultListModel list = null;
        try {
            list=(DefaultListModel)this.listComments.getModel(); 
        } catch (Exception ex) {

        }
        if (list==null) {list=new DefaultListModel();}
        if (getList().getSelectedValue()!=null) { 
            int id = ((ListItem)getList().getSelectedValue()).getIndex();
            CommandItem[] commands=Main.GET_ROWS_TABLE_COMMENTS();
            commands[0].setQuery(commands[0].getQuery().replace("{0}", String.valueOf(id)));
            Main.showCommands(commands);
            ArrayList value = Main.runCommands(commands);
            if (value!=null&&value.size()>0) {
                DataTable dt = (DataTable)value.get(0);
                if (dt!=null) {
                    updateComments(list,dt);
                    addComments(list,dt);
                    removeComments(list,dt);
                } 
            }
        } else {
            list.removeAllElements();
        }
        this.listComments.setModel(list);
    }
    private void updateComments(DefaultListModel list, DataTable newList) {
        if (list!=null&&newList!=null) {
            int colId = newList.getColumnIndex("id");
            int colDate=newList.getColumnIndex("entryDate");
            int colComment=newList.getColumnIndex("comments");
            for (int i=0;i<list.size();i++) {
                ListItem lItem=(ListItem)list.get(i);
                for (int r=0;r<newList.getRowCount();r++) {
                    DataRow row =newList.getRow(r);
                    if (lItem.getIndex()==(int)row.getValue(colId)) {
                        Comment comment=new Comment(
                                (int)row.getValue(colId),
                                (Date)row.getValue(colDate),
                                (String)row.getValue(colComment));
                        
                        lItem.setDetail(comment.toString());
                        break;
                    }
                }
            }
        }
    }
    private void addComments(DefaultListModel list, DataTable newList) {
        if (list!=null&&newList!=null) {
            ArrayList listId = new ArrayList();
            int colId = newList.getColumnIndex("id");
            int colDate=newList.getColumnIndex("entryDate");
            int colComment=newList.getColumnIndex("comments");
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
                        Comment comment=new Comment(
                                (int)row.getValue(colId),
                                (Date)row.getValue(colDate),
                                (String)row.getValue(colComment));
                        
                        ListItem item = new ListItem(
                        (int)row.getValue(colId),
                            comment.toString()
                        );
                        list.addElement(item);
                        break;
                    }
                }
            }
        }        
    }
    private void removeComments(DefaultListModel list, DataTable newList) {
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
    private void addComment() {
        if (getList().getSelectedValue()!=null) { 
            int id = ((ListItem)getList().getSelectedValue()).getIndex();
            String commentText=this.getNewCommentText().trim();
            if (commentText.length()>0) {
                LocalDateTime currentTime = LocalDateTime.now();
                String commentDate = currentTime.getYear() + "-" + currentTime.getMonthValue() + "-" + currentTime.getDayOfMonth();
                String commentTime = currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond();
                CommandItem[] commands=Main.INSERT_ROW_TABLE_COMMENTS();
                commands[0].setQuery(
                        commands[0].getQuery()
                                .replace("{0}", String.valueOf(id))
                                .replace("{1}", commentDate + " " + commentTime)
                                .replace("{2}", commentText)
                );
                Main.showCommands(commands);
                ArrayList value = Main.runCommands(commands);
                if (value!=null&&value.size()>0) {
                    this.textareaNewComment.setText("");
                    refreshComments();
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

        jPanel4 = new javax.swing.JPanel();
        panel_StudentList = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        mainList = new javax.swing.JList<>();
        panelAddEditDelete = new javax.swing.JPanel();
        buttonEditStudent = new javax.swing.JButton();
        panel_StudentView = new javax.swing.JPanel();
        panelStudentName = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        textfieldSSN1 = new javax.swing.JTextField();
        textfieldSSN2 = new javax.swing.JTextField();
        textfieldSSN3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        checkboxNoContact = new javax.swing.JCheckBox();
        checkboxDonate = new javax.swing.JCheckBox();
        checkboxPHDTrack = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listComments = new javax.swing.JList<>();
        buttonCommentSave = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textareaNewComment = new javax.swing.JTextArea();
        panelSaveCancel = new javax.swing.JPanel();
        buttonCancelSaveStudent = new javax.swing.JButton();
        buttonConfirmSaveStudent = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit Students");
        setMinimumSize(null);

        panel_StudentList.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panel_StudentList.setMaximumSize(new java.awt.Dimension(256, 32768));

        jLabel1.setText("Students");

        jScrollPane3.setPreferredSize(new java.awt.Dimension(300, 0));

        mainList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        mainList.setPreferredSize(new java.awt.Dimension(300, 0));
        jScrollPane3.setViewportView(mainList);

        panelAddEditDelete.setName("panelAddEditDelete"); // NOI18N

        buttonEditStudent.setText("Edit");
        buttonEditStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonEditStudentMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelAddEditDeleteLayout = new javax.swing.GroupLayout(panelAddEditDelete);
        panelAddEditDelete.setLayout(panelAddEditDeleteLayout);
        panelAddEditDeleteLayout.setHorizontalGroup(
            panelAddEditDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddEditDeleteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonEditStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelAddEditDeleteLayout.setVerticalGroup(
            panelAddEditDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAddEditDeleteLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(buttonEditStudent))
        );

        javax.swing.GroupLayout panel_StudentListLayout = new javax.swing.GroupLayout(panel_StudentList);
        panel_StudentList.setLayout(panel_StudentListLayout);
        panel_StudentListLayout.setHorizontalGroup(
            panel_StudentListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_StudentListLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_StudentListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_StudentListLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panel_StudentListLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_StudentListLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(panelAddEditDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        panel_StudentListLayout.setVerticalGroup(
            panel_StudentListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_StudentListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                .addGap(17, 17, 17)
                .addComponent(panelAddEditDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panel_StudentView.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panel_StudentView.setMaximumSize(new java.awt.Dimension(512, 32768));

        jLabel6.setText("Student Information");

        jLabel2.setText("SSN");

        textfieldSSN1.setColumns(3);
        textfieldSSN1.setName("textfield_SSN1"); // NOI18N

        textfieldSSN2.setColumns(2);
        textfieldSSN2.setName("textfield_SSN2"); // NOI18N

        textfieldSSN3.setColumns(4);
        textfieldSSN3.setName("textfield_SSN3"); // NOI18N

        jLabel3.setText("No Contact?");

        jLabel4.setText("Willing to Donate?");

        jLabel5.setText("PHD Track?");

        checkboxNoContact.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        checkboxDonate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        checkboxPHDTrack.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel7.setText("Comments");

        listComments.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(listComments);

        buttonCommentSave.setText("Save Comment");
        buttonCommentSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonCommentSaveMouseClicked(evt);
            }
        });

        textareaNewComment.setColumns(20);
        textareaNewComment.setRows(5);
        jScrollPane1.setViewportView(textareaNewComment);

        javax.swing.GroupLayout panelStudentNameLayout = new javax.swing.GroupLayout(panelStudentName);
        panelStudentName.setLayout(panelStudentNameLayout);
        panelStudentNameLayout.setHorizontalGroup(
            panelStudentNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStudentNameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelStudentNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelStudentNameLayout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelStudentNameLayout.createSequentialGroup()
                        .addGroup(panelStudentNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelStudentNameLayout.createSequentialGroup()
                                .addComponent(textfieldSSN1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textfieldSSN2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textfieldSSN3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelStudentNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(checkboxNoContact, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelStudentNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(checkboxDonate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelStudentNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(checkboxPHDTrack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(panelStudentNameLayout.createSequentialGroup()
                .addGroup(panelStudentNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelStudentNameLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelStudentNameLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonCommentSave)))
                .addContainerGap())
        );
        panelStudentNameLayout.setVerticalGroup(
            panelStudentNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStudentNameLayout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelStudentNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelStudentNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelStudentNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                        .addComponent(textfieldSSN1)
                        .addComponent(textfieldSSN2)
                        .addComponent(textfieldSSN3)
                        .addComponent(checkboxNoContact))
                    .addComponent(checkboxDonate)
                    .addComponent(checkboxPHDTrack))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelStudentNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonCommentSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel3.getAccessibleContext().setAccessibleName("");

        panelSaveCancel.setName("panelSaveCancel"); // NOI18N

        buttonCancelSaveStudent.setText("Cancel");
        buttonCancelSaveStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonCancelSaveStudentMouseClicked(evt);
            }
        });

        buttonConfirmSaveStudent.setText("Save");
        buttonConfirmSaveStudent.setMaximumSize(new java.awt.Dimension(65, 23));
        buttonConfirmSaveStudent.setMinimumSize(new java.awt.Dimension(65, 23));
        buttonConfirmSaveStudent.setPreferredSize(new java.awt.Dimension(65, 23));
        buttonConfirmSaveStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonConfirmSaveStudentMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelSaveCancelLayout = new javax.swing.GroupLayout(panelSaveCancel);
        panelSaveCancel.setLayout(panelSaveCancelLayout);
        panelSaveCancelLayout.setHorizontalGroup(
            panelSaveCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSaveCancelLayout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addComponent(buttonConfirmSaveStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCancelSaveStudent)
                .addContainerGap())
        );
        panelSaveCancelLayout.setVerticalGroup(
            panelSaveCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSaveCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(buttonCancelSaveStudent)
                .addComponent(buttonConfirmSaveStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout panel_StudentViewLayout = new javax.swing.GroupLayout(panel_StudentView);
        panel_StudentView.setLayout(panel_StudentViewLayout);
        panel_StudentViewLayout.setHorizontalGroup(
            panel_StudentViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_StudentViewLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(panelStudentName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_StudentViewLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelSaveCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panel_StudentViewLayout.setVerticalGroup(
            panel_StudentViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_StudentViewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelStudentName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSaveCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(panel_StudentList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_StudentView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_StudentView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel_StudentList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonEditStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonEditStudentMouseClicked
        if (buttonEditStudent.isEnabled()) {
            editPrivate();
        }
    }//GEN-LAST:event_buttonEditStudentMouseClicked

    private void buttonConfirmSaveStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonConfirmSaveStudentMouseClicked
        if (buttonConfirmSaveStudent.isEnabled()) {
            this.confirmSavePrivate();
        }
    }//GEN-LAST:event_buttonConfirmSaveStudentMouseClicked

    private void buttonCancelSaveStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonCancelSaveStudentMouseClicked
        if (buttonCancelSaveStudent.isEnabled()) {
            this.cancelSavePrivate();
        }
    }//GEN-LAST:event_buttonCancelSaveStudentMouseClicked

    private void buttonCommentSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonCommentSaveMouseClicked
        if (buttonCommentSave.isEnabled()) {
            addComment();
        }
    }//GEN-LAST:event_buttonCommentSaveMouseClicked

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
            java.util.logging.Logger.getLogger(DialogPopulateStudentPrivateInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogPopulateStudentPrivateInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogPopulateStudentPrivateInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogPopulateStudentPrivateInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogPopulateStudentPrivateInfo dialog = new DialogPopulateStudentPrivateInfo(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton buttonCancelSaveStudent;
    private javax.swing.JButton buttonCommentSave;
    private javax.swing.JButton buttonConfirmSaveStudent;
    private javax.swing.JButton buttonEditStudent;
    private javax.swing.JCheckBox checkboxDonate;
    private javax.swing.JCheckBox checkboxNoContact;
    private javax.swing.JCheckBox checkboxPHDTrack;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> listComments;
    private javax.swing.JList<String> mainList;
    private javax.swing.JPanel panelAddEditDelete;
    private javax.swing.JPanel panelSaveCancel;
    private javax.swing.JPanel panelStudentName;
    private javax.swing.JPanel panel_StudentList;
    private javax.swing.JPanel panel_StudentView;
    private javax.swing.JTextArea textareaNewComment;
    private javax.swing.JTextField textfieldSSN1;
    private javax.swing.JTextField textfieldSSN2;
    private javax.swing.JTextField textfieldSSN3;
    // End of variables declaration//GEN-END:variables
}
