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
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Isaac Denney
 */
public class DialogPopulateStudents extends javax.swing.JDialog {

    private boolean getIsEditable() {
        return false; //return !this.panelMajorsList.isEnabled();
    }

    private void setIsEditable(boolean value) {

        boolean hasStudent = getList().getSelectedValue() != null;

        this.getList().setEnabled(!value);
        
        this.buttonAddStudent.setEnabled(!value);
        this.buttonEditStudent.setEnabled(!value);
        
        this.textfieldNameFirst.setEnabled(value);
        this.textfieldNameMiddle.setEnabled(value);
        this.textfieldNameLast.setEnabled(value);

        this.setIsEmailEditing(false);
        this.setIsMajorEditing(false);

        this.buttonConfirmSaveStudent.setEnabled(value);
        this.buttonCancelSaveStudent.setEnabled(value);

        this.button_EmailAdd.setEnabled(value && hasStudent);
        updateEmailView();

        this.button_MajorAdd.setEnabled(value && hasStudent);
        updateMajorView();
    }

    private boolean getIsEmailEditing() {
        return false;
    }

    private void setIsEmailEditing(boolean value) {
        this.list_EmailAddresses.setEnabled(!value);
        this.button_EmailAdd.setEnabled(!value);
        this.textfield_EmailAddress.setEnabled(value);
        this.panel_EditEmail.setVisible(value);
        this.panel_EmailSaveCancel.setVisible(value);
        this.updateEmailView();
    }

    private boolean getIsMajorEditing() {
        return false;
    }

    private void setIsMajorEditing(boolean value) {
        this.list_Majors.setEnabled(!value);
        this.button_MajorAdd.setEnabled(!value);
        this.comboboxMajor.setEnabled(value);
        this.panel_MajorSaveCancel.setVisible(value);
        this.panel_EditMajor.setVisible(value);
        this.updateMajorView();
    }

    private JList getList() {
        return this.mainList;
    }

    private String getFirstName() {
        return this.textfieldNameFirst.getText();
    }

    private void setFirstName(String value) {
        this.textfieldNameFirst.setText(value);
    }

    private String getMiddleName() {
        return this.textfieldNameMiddle.getText();
    }

    private void setMiddleName(String value) {
        this.textfieldNameMiddle.setText(value);
    }

    private String getLastName() {
        return this.textfieldNameLast.getText();
    }

    private void setLastName(String value) {
        this.textfieldNameLast.setText(value);
    }

    private String getEmailAddress() {
        return this.textfield_EmailAddress.getText();
    }

    private void setEmailAddress(String value) {
        this.textfield_EmailAddress.setText(value);
    }

    private int getMajorId() {
        int value = -1;
        Object item = this.comboboxMajor.getSelectedItem();
        if (item != null) {
            value = ((ListItem) item).getIndex();
        }
        return value;
    }

    private void setMajorId(int value) {
        for (int i = 0; i < this.comboboxMajor.getItemCount(); i++) {
            //ListItem item = ;
        }
    }

    private void refreshStudents() {
        CommandItem[] commands = Main.SELECT_ROWS_TABLE_STUDENTS();
        Main.showCommands(commands);
        ArrayList value = Main.runCommands(commands);
        if (value != null && value.size() > 0) {
            DataTable dt = (DataTable) value.get(0);
            if (dt != null) {
                DefaultListModel list = null;
                try {
                    list = (DefaultListModel) getList().getModel();
                } catch (Exception ex) {

                }
                if (list == null) {
                    list = new DefaultListModel();
                }

                updateStudents(list, dt);
                addStudents(list, dt);
                removeStudents(list, dt);

                this.getList().setModel(list);
            }
        }
        refreshEmails();
        refreshMajors();
    }

    private void updateStudents(DefaultListModel list, DataTable newList) {
        if (list != null && newList != null) {
            int colId = newList.getColumnIndex("id");
            int colNameF = newList.getColumnIndex("firstName");
            int colNameM = newList.getColumnIndex("middleName");
            int colNameL = newList.getColumnIndex("lastName");
            for (int i = 0; i < list.size(); i++) {
                ListItem lItem = (ListItem) list.get(i);
                for (int r = 0; r < newList.getRowCount(); r++) {
                    DataRow row = newList.getRow(r);
                    if (lItem.getIndex() == (int) row.getValue(colId)) {
                        Student student = new Student(
                                (int) row.getValue(colId),
                                (String) row.getValue(colNameF),
                                (String) row.getValue(colNameM),
                                (String) row.getValue(colNameL));

                        lItem.setDetail(student.toString());
                        break;
                    }
                }
            }
        }
    }

    private void addStudents(DefaultListModel list, DataTable newList) {
        if (list != null && newList != null) {
            int colNameF = newList.getColumnIndex("firstName");
            int colNameM = newList.getColumnIndex("middleName");
            int colNameL = newList.getColumnIndex("lastName");
            ArrayList listId = new ArrayList();
            int colId = newList.getColumnIndex("id");
            for (int r = 0; r < newList.getRowCount(); r++) {
                boolean hasRow = false;
                DataRow row = newList.getRow(r);
                for (int i = 0; i < list.size(); i++) {
                    ListItem lItem = (ListItem) list.get(i);
                    if (lItem.getIndex() == (int) row.getValue(colId)) {
                        hasRow = true;
                        break;
                    }
                }
                if (!hasRow) {
                    listId.add(row.getValue(colId));
                }
            }
            for (int i = 0; i < listId.size(); i++) {
                for (int r = 0; r < newList.getRowCount(); r++) {
                    DataRow row = newList.getRow(r);
                    if (row.getValue(colId) == listId.get(i)) {
                        Student student = new Student(
                                (int) row.getValue(colId),
                                (String) row.getValue(colNameF),
                                (String) row.getValue(colNameM),
                                (String) row.getValue(colNameL));

                        ListItem item = new ListItem(
                                (int) row.getValue(colId),
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
        if (list != null && newList != null) {
            ArrayList listId = new ArrayList();
            int colId = newList.getColumnIndex("id");
            for (int i = 0; i < list.size(); i++) {
                boolean hasItem = false;
                ListItem lItem = (ListItem) list.get(i);
                for (int r = 0; r < newList.getRowCount(); r++) {
                    DataRow row = newList.getRow(r);
                    if (lItem.getIndex() == (int) row.getValue(colId)) {
                        hasItem = true;
                        break;
                    }
                }
                if (!hasItem) {
                    listId.add(lItem.getIndex());
                }
            }

            for (int i = 0; i < listId.size(); i++) {
                for (int l = 0; l < list.size(); l++) {
                    ListItem lItem = (ListItem) list.get(l);
                    if ((int) listId.get(i) == lItem.getIndex()) {
                        list.remove(l);
                        break;
                    }
                }
            }
        }
    }

    private void refreshEmails() {
        ListItem value = (ListItem) getList().getSelectedValue();
        if (value != null) {
            setEmails(value.getIndex());
        } else {
            setEmails(null);
        }
    }

    private void setEmails(int studentId) {
        CommandItem[] commands = Main.SELECT_ROWS_STUDENT_TABLE_EMAILS();
        commands[0].setQuery(commands[0].getQuery().replace("{0}", String.valueOf(studentId)));
        Main.showCommands(commands);
        ArrayList value = Main.runCommands(commands);
        if (value != null && value.size() > 0) {
            DataTable dt = (DataTable) value.get(0);
            setEmails(dt);
        }
    }

    private void setEmails(DataTable values) {
        DefaultListModel list = null;
        try {
            list = (DefaultListModel) this.list_EmailAddresses.getModel();
        } catch (Exception ex) {

        }
        if (list == null) {
            list = new DefaultListModel();
        }

        updateEmails(list, values);
        removeEmails(list, values);
        addEmails(list, values);

        this.list_EmailAddresses.setModel(list);
    }

    private void updateEmails(DefaultListModel list, DataTable newList) {
        if (list != null && newList != null) {
            int colId = newList.getColumnIndex("id");
            int colEmailAddress = newList.getColumnIndex("emailAddress");
            for (int i = 0; i < list.size(); i++) {
                ListItem lItem = (ListItem) list.get(i);
                for (int r = 0; r < newList.getRowCount(); r++) {
                    DataRow row = newList.getRow(r);
                    if (lItem.getIndex() == (int) row.getValue(colId)) {
                        lItem.setDetail(
                                (String) row.getValue(colEmailAddress)
                        );
                        break;
                    }
                }
            }
        }
    }

    private void addEmails(DefaultListModel list, DataTable newList) {
        if (list != null && newList != null) {
            ArrayList listId = new ArrayList();
            int colId = newList.getColumnIndex("id");
            int colEmailAddress = newList.getColumnIndex("emailAddress");
            for (int r = 0; r < newList.getRowCount(); r++) {
                boolean hasRow = false;
                DataRow row = newList.getRow(r);
                for (int i = 0; i < list.size(); i++) {
                    ListItem lItem = (ListItem) list.get(i);
                    if (lItem.getIndex() == (int) row.getValue(colId)) {
                        hasRow = true;
                        break;
                    }
                }
                if (!hasRow) {
                    listId.add(row.getValue(colId));
                }
            }
            for (int i = 0; i < listId.size(); i++) {
                for (int r = 0; r < newList.getRowCount(); r++) {
                    DataRow row = newList.getRow(r);
                    if (row.getValue(colId) == listId.get(i)) {
                        ListItem item = new ListItem(
                                (int) row.getValue(colId),
                                (String) row.getValue(colEmailAddress)
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

    private void removeEmails(DefaultListModel list, DataTable newList) {
        if (list != null) {
            if (newList != null) {
                ArrayList listId = new ArrayList();
                int colId = newList.getColumnIndex("id");
                for (int i = 0; i < list.size(); i++) {
                    boolean hasItem = false;
                    ListItem lItem = (ListItem) list.get(i);
                    for (int r = 0; r < newList.getRowCount(); r++) {
                        DataRow row = newList.getRow(r);
                        if (lItem.getIndex() == (int) row.getValue(colId)) {
                            hasItem = true;
                            break;
                        }
                    }
                    if (!hasItem) {
                        listId.add(lItem.getIndex());
                    }
                }

                for (int i = 0; i < listId.size(); i++) {
                    for (int l = 0; l < list.size(); l++) {
                        ListItem lItem = (ListItem) list.get(l);
                        if ((int) listId.get(i) == lItem.getIndex()) {
                            list.remove(l);
                            break;
                        }
                    }
                }
            } else {
                list.removeAllElements();
            }
        }
    }

    private void refreshMajors() {
        ListItem value = (ListItem) getList().getSelectedValue();
        if (value != null) {
            setMajors(value.getIndex());
        } else {
            setMajors(null);
        }
    }

    private void setMajors(int studentId) {
        CommandItem[] commands = Main.SELECT_ROWS_STUDENT_TABLE_MAJORS();
        commands[0].setQuery(commands[0].getQuery().replace("{0}", String.valueOf(studentId)));
        Main.showCommands(commands);
        ArrayList value = Main.runCommands(commands);
        if (value != null && value.size() > 0) {
            DataTable dt = (DataTable) value.get(0);
            setMajors(dt);
        }
    }

    private void setMajors(DataTable values) {
        DefaultListModel list = null;
        try {
            list = (DefaultListModel) this.list_Majors.getModel();
        } catch (Exception ex) {

        }
        if (list == null) {
            list = new DefaultListModel();
        }

        updateMajors(list, values);
        removeMajors(list, values);
        addMajors(list, values);

        this.list_Majors.setModel(list);
    }

    private void updateMajors(DefaultListModel list, DataTable newList) {
        if (list != null && newList != null) {
            int colId = newList.getColumnIndex("id");
            int colAbbr = newList.getColumnIndex("majorAbbr");
            int colName = newList.getColumnIndex("majorName");
            for (int i = 0; i < list.size(); i++) {
                ListItem lItem = (ListItem) list.get(i);
                for (int r = 0; r < newList.getRowCount(); r++) {
                    DataRow row = newList.getRow(r);
                    Major major = new Major((int) row.getValue(colId), (String) row.getValue(colAbbr), (String) row.getValue(colName));
                    if (lItem.getIndex() == (int) row.getValue(colId)) {
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
        if (list != null && newList != null) {
            ArrayList listId = new ArrayList();
            int colId = newList.getColumnIndex("id");
            int colAbbr = newList.getColumnIndex("majorAbbr");
            int colName = newList.getColumnIndex("majorName");
            for (int r = 0; r < newList.getRowCount(); r++) {
                boolean hasRow = false;
                DataRow row = newList.getRow(r);
                for (int i = 0; i < list.size(); i++) {
                    ListItem lItem = (ListItem) list.get(i);
                    if (lItem.getIndex() == (int) row.getValue(colId)) {
                        hasRow = true;
                        break;
                    }
                }
                if (!hasRow) {
                    listId.add(row.getValue(colId));
                }
            }
            for (int i = 0; i < listId.size(); i++) {
                for (int r = 0; r < newList.getRowCount(); r++) {
                    DataRow row = newList.getRow(r);
                    Major major = new Major((int) row.getValue(colId), (String) row.getValue(colAbbr), (String) row.getValue(colName));
                    if (row.getValue(colId) == listId.get(i)) {
                        ListItem item = new ListItem(
                                (int) row.getValue(colId),
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
        if (list != null) {
            if (newList != null) {
                ArrayList listId = new ArrayList();
                int colId = newList.getColumnIndex("id");
                for (int i = 0; i < list.size(); i++) {
                    boolean hasItem = false;
                    ListItem lItem = (ListItem) list.get(i);
                    for (int r = 0; r < newList.getRowCount(); r++) {
                        DataRow row = newList.getRow(r);
                        if (lItem.getIndex() == (int) row.getValue(colId)) {
                            hasItem = true;
                            break;
                        }
                    }
                    if (!hasItem) {
                        listId.add(lItem.getIndex());
                    }
                }

                for (int i = 0; i < listId.size(); i++) {
                    for (int l = 0; l < list.size(); l++) {
                        ListItem lItem = (ListItem) list.get(l);
                        if ((int) listId.get(i) == lItem.getIndex()) {
                            list.remove(l);
                            break;
                        }
                    }
                }
            } else {
                list.removeAllElements();
            }
        }
    }

    private void setMajorsList() {
        JComboBox cb = this.comboboxMajor;
        cb.removeAll();

        CommandItem[] commands = Main.SELECT_ROWS_TABLE_MAJORSINFO();
        Main.showCommands(commands);
        ArrayList value = Main.runCommands(commands);
        if (value != null && value.size() > 0) {
            DataTable dt = (DataTable) value.get(0);
            if (dt != null) {

                int colId = dt.getColumnIndex("id");
                int colAbbr = dt.getColumnIndex("majorAbbr");
                int colName = dt.getColumnIndex("majorName");
                for (int i = 0; i < dt.getRowCount(); i++) {
                    DataRow row = dt.getRow(i);
                    Major major = new Major((int) row.getValue(colId), (String) row.getValue(colAbbr), (String) row.getValue(colName));
                    ListItem item = new ListItem(major.getId(), major.toString());
                    cb.addItem(item);
                }
            }
        }
    }

    /**
     * Creates new form DialogPopulateStudents
     */
    public DialogPopulateStudents(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void init() {
        this.buttonDeleteStudent.setVisible(false);
        this.setIsEditable(false);
        this.setIsEmailEditing(false);
        this.setIsMajorEditing(false);

        setListeners();
        refreshStudents();
        setMajorsList();
        updateViewPanel();
    }

    private void setListeners() {
        getList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateViewPanel();
            }
        });
        this.list_EmailAddresses.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateEmailView();
            }
        });
        this.list_Majors.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateMajorView();
            }

        });
    }

    private void updateViewPanel() {
        ListItem value = (ListItem) getList().getSelectedValue();
        if (value == null) {
            this.setFirstName("");
            this.setMiddleName("");
            this.setLastName("");
            this.buttonEditStudent.setEnabled(false);
        } else {
            Student student = getStudent(value.getIndex());
            if (student != null) {
                this.setFirstName(student.getFirstName());
                this.setMiddleName(student.getMiddleName());
                this.setLastName(student.getLastName());
                this.buttonEditStudent.setEnabled(true);
            }
        }
        this.refreshEmails();
        this.refreshMajors();
    }

    private Student getStudent(int id) {
        Student value = null;
        CommandItem[] commands = Main.SELECT_ROW_TABLE_STUDENTS();
        commands[0].setQuery(commands[0].getQuery().replace("{0}", String.valueOf(id)));

        Main.showCommands(commands);
        ArrayList values = Main.runCommands(commands);

        if (values != null && values.size() > 0) {
            DataTable dt = (DataTable) values.get(0);
            if (dt != null) {
                DataRow dr = dt.getRow(0);
                int colId = dt.getColumnIndex("id");
                int colFirstName = dt.getColumnIndex("firstName");
                int colMiddleName = dt.getColumnIndex("middleName");
                int colLastName = dt.getColumnIndex("lastName");

                value = new Student((int) dr.getValue(colId), (String) dr.getValue(colFirstName), (String) dr.getValue(colMiddleName), (String) dr.getValue(colLastName));
            }
        }
        return value;
    }

    private void updateStudentView() {
        this.updateViewPanel();
    }

    private void addStudent() {
        this.getList().clearSelection();
        this.setIsEditable(true);
    }

    private void editStudent() {
        this.setIsEditable(true);
    }

    private void deleteStudent() {
        CommandItem[] commands = null;
        if (this.getList().getSelectedValue() != null) {
            int mId = ((ListItem) this.getList().getSelectedValue()).getIndex();
            //commands=Main.DELETE_ROW_STUDENT_EMAIL_TABLE_EMAILS();
            //commands[0].setQuery(commands[0].getQuery().replace("{0}",String.valueOf(mId)));
        }
        if (commands != null) {
            Main.showCommands(commands);
            ArrayList result = Main.runCommands(commands);
            if (result != null && result.size() > 0) {
                if ((int) result.get(0) == 1) {
                    this.refreshStudents();
                    this.updateStudentView();
                    this.setIsEditable(false);
                }
            }
        }
    }

    private void confirmSaveStudent() {
        CommandItem[] commands = null;
        String fName = this.getFirstName().trim();
        String mName = this.getMiddleName().trim();
        String lName = this.getLastName().trim();
        if (lName.length() > 0) {
            if (this.getList().getSelectedValue() == null) {
                commands = Main.INSERT_ROW_TABLE_STUDENTS();
                if (fName.length() == 0) {
                    fName = "NULL";
                } else {
                    fName = "'" + fName + "'";
                }
                commands[0].setQuery(commands[0].getQuery().replace("'{0}'", fName));

                if (mName.length() == 0) {
                    mName = "NULL";
                } else {
                    mName = "'" + mName + "'";
                }
                commands[0].setQuery(commands[0].getQuery().replace("'{1}'", mName));

                if (lName.length() == 0) {
                    lName = "NULL";
                } else {
                    lName = "'" + lName + "'";
                }
                commands[0].setQuery(commands[0].getQuery().replace("'{2}'", lName));
            } else {
                int sId = ((ListItem) this.getList().getSelectedValue()).getIndex();
                commands = Main.UPDATE_ROW_TABLE_STUDENTS();
                if (fName.length() == 0) {
                    fName = "NULL";
                } else {
                    fName = "'" + fName + "'";
                }
                commands[0].setQuery(commands[0].getQuery().replace("'{0}'", fName));

                if (mName.length() == 0) {
                    mName = "NULL";
                } else {
                    mName = "'" + mName + "'";
                }
                commands[0].setQuery(commands[0].getQuery().replace("'{1}'", mName));

                if (lName.length() == 0) {
                    lName = "NULL";
                } else {
                    lName = "'" + lName + "'";
                }
                commands[0].setQuery(commands[0].getQuery().replace("'{2}'", lName).replace("{3}", String.valueOf(sId)));
            }
        }
        if (commands != null) {
            Main.showCommands(commands);
            ArrayList result = Main.runCommands(commands);
            if (result != null && result.size() > 0) {
                if ((int) result.get(0) == 1) {
                    this.refreshStudents();
                    this.updateStudentView();
                    this.setIsEditable(false);
                }
            }
        }
    }

    private void cancelSaveStudent() {
        this.setIsEditable(false);
    }

    private void updateEmailView() {
        ListItem item = (ListItem) ((JList) this.list_EmailAddresses).getSelectedValue();
        if (item != null) {
            this.textfield_EmailAddress.setText(item.getDetail());
            this.button_EmailEdit.setEnabled(true);
            this.button_EmailDelete.setEnabled(true);
        } else {
            this.textfield_EmailAddress.setText("");
            this.button_EmailEdit.setEnabled(false);
            this.button_EmailDelete.setEnabled(false);
        }
    }

    private void addEmail() {
        this.list_EmailAddresses.clearSelection();
        this.setIsEmailEditing(true);
    }

    private void editEmail() {
        this.setIsEmailEditing(true);
    }

    private void deleteEmail() {
        CommandItem[] commands = null;
        if (list_EmailAddresses.getSelectedValue() != null) {
            int mId = ((ListItem) this.list_EmailAddresses.getSelectedValue()).getIndex();
            commands = Main.DELETE_ROW_STUDENT_EMAIL_TABLE_EMAILS();
            commands[0].setQuery(commands[0].getQuery().replace("{0}", String.valueOf(mId)));
        }
        if (commands != null) {
            Main.showCommands(commands);
            ArrayList result = Main.runCommands(commands);
            if (result != null && result.size() > 0) {
                if ((int) result.get(0) == 1) {
                    this.refreshEmails();
                    this.updateEmailView();
                    this.setIsEmailEditing(false);
                }
            }
        }
    }

    private void confirmSaveEmail() {
        CommandItem[] commands = null;
        String emailAddr = this.getEmailAddress().trim();
        if (emailAddr.length() > 0) {
            if (getList().getSelectedValue() != null) {
                if (this.list_EmailAddresses.getSelectedValue() == null) {
                    int sId = ((ListItem) getList().getSelectedValue()).getIndex();
                    commands = Main.INSERT_ROW_TABLE_EMAILS();
                    commands[0].setQuery(commands[0].getQuery().replace("{0}", String.valueOf(sId)).replace("{1}", emailAddr));
                } else {
                    int eId = ((ListItem) list_EmailAddresses.getSelectedValue()).getIndex();
                    commands = Main.UPDATE_ROW_TABLE_EMAILS();
                    commands[0].setQuery(commands[0].getQuery().replace("{0}", emailAddr).replace("{1}", String.valueOf(eId)));
                }
            }
        }
        if (commands != null) {
            Main.showCommands(commands);
            ArrayList result = Main.runCommands(commands);
            if (result != null && result.size() > 0) {
                if ((int) result.get(0) == 1) {
                    this.refreshEmails();
                    this.updateEmailView();
                }
            }
        }
        this.setIsEmailEditing(false);
    }

    private void cancelSaveEmail() {
        this.setIsEmailEditing(false);
    }

    private void updateMajorView() {
        ListItem item = (ListItem) ((JList) this.list_Majors).getSelectedValue();
        if (item != null) {
            this.button_MajorDelete.setEnabled(true);
        } else {
            this.button_MajorDelete.setEnabled(false);
        }
    }

    private void addMajor() {
        this.list_Majors.clearSelection();
        this.setIsMajorEditing(true);
    }

    private void editMajor() {
        this.setIsMajorEditing(true);
    }

    private void deleteMajor() {
        CommandItem[] commands = null;
        if (getList().getSelectedValue() != null && this.list_Majors.getSelectedValue() != null) {
            int sId = ((ListItem) getList().getSelectedValue()).getIndex();
            int mId = ((ListItem) list_Majors.getSelectedValue()).getIndex();
            commands = Main.DELETE_ROW_STUDENT_MAJOR_TABLE_MAJORS();
            commands[0].setQuery(commands[0].getQuery().replace("{0}", String.valueOf(sId)).replace("{1}", String.valueOf(mId)));
        }
        if (commands != null) {
            Main.showCommands(commands);
            ArrayList result = Main.runCommands(commands);
            if (result != null && result.size() > 0) {
                if ((int) result.get(0) == 1) {
                    this.refreshMajors();
                    this.updateMajorView();
                    this.setIsMajorEditing(false);
                }
            }
        }
    }

    private void confirmSaveMajor() {
        CommandItem[] commands = null;
        int mId = this.getMajorId();
        if (mId >= 0) {
            if (getList().getSelectedValue() != null) {
                int sId = ((ListItem) getList().getSelectedValue()).getIndex();
                commands = Main.INSERT_ROW_TABLE_MAJORS();
                commands[0].setQuery(commands[0].getQuery().replace("{0}", String.valueOf(sId)).replace("{1}", String.valueOf(mId)));
            }
        }
        if (commands != null) {
            Main.showCommands(commands);
            ArrayList result = Main.runCommands(commands);
            if (result != null && result.size() > 0) {
                if ((int) result.get(0) == 1) {
                    this.refreshMajors();
                    this.updateMajorView();
                }
            }
        }
        this.setIsMajorEditing(false);

    }

    private void cancelSaveMajor() {
        this.setIsMajorEditing(false);
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
        panelAddEditDelete = new javax.swing.JPanel();
        buttonAddStudent = new javax.swing.JButton();
        buttonEditStudent = new javax.swing.JButton();
        buttonDeleteStudent = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        mainList = new javax.swing.JList<>();
        panel_StudentView = new javax.swing.JPanel();
        panelStudentName = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        textfieldNameFirst = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        textfieldNameMiddle = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        textfieldNameLast = new javax.swing.JTextField();
        panelSaveCancel = new javax.swing.JPanel();
        buttonCancelSaveStudent = new javax.swing.JButton();
        buttonConfirmSaveStudent = new javax.swing.JButton();
        panelAdditionalInfo = new javax.swing.JPanel();
        panel_EmailInfo = new javax.swing.JPanel();
        panelEmailsView = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        list_EmailAddresses = new javax.swing.JList<>();
        panelEmailsEdit = new javax.swing.JPanel();
        panelEmailAddEditDelete = new javax.swing.JPanel();
        button_EmailAdd = new javax.swing.JButton();
        button_EmailEdit = new javax.swing.JButton();
        button_EmailDelete = new javax.swing.JButton();
        panel_EditEmail = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        textfield_EmailAddress = new javax.swing.JTextField();
        panel_EmailSaveCancel = new javax.swing.JPanel();
        button_EmailCancel = new javax.swing.JButton();
        button_EmailConfirm = new javax.swing.JButton();
        panel_EmailInfo1 = new javax.swing.JPanel();
        panelEmailsView1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        list_Majors = new javax.swing.JList<>();
        panelEmailsEdit1 = new javax.swing.JPanel();
        panelEmailAddEditDelete1 = new javax.swing.JPanel();
        button_MajorAdd = new javax.swing.JButton();
        button_MajorDelete = new javax.swing.JButton();
        panel_EditMajor = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        comboboxMajor = new javax.swing.JComboBox<>();
        panel_MajorSaveCancel = new javax.swing.JPanel();
        button_MajorCancel = new javax.swing.JButton();
        button_MajorConfirm = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit Students");
        setMinimumSize(new java.awt.Dimension(900, 472));
        setPreferredSize(new java.awt.Dimension(800, 468));

        jPanel4.setMinimumSize(new java.awt.Dimension(848, 400));
        jPanel4.setPreferredSize(new java.awt.Dimension(1064, 368));

        panel_StudentList.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panel_StudentList.setMaximumSize(new java.awt.Dimension(256, 32768));
        panel_StudentList.setMinimumSize(new java.awt.Dimension(256, 384));
        panel_StudentList.setPreferredSize(new java.awt.Dimension(256, 384));

        jLabel1.setText("Students");

        panelAddEditDelete.setName("panelAddEditDelete"); // NOI18N

        buttonAddStudent.setText("Add");
        buttonAddStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonAddStudentMouseClicked(evt);
            }
        });

        buttonEditStudent.setText("Edit");
        buttonEditStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonEditStudentMouseClicked(evt);
            }
        });

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
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(buttonAddStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonEditStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonDeleteStudent)
                .addContainerGap())
        );
        panelAddEditDeleteLayout.setVerticalGroup(
            panelAddEditDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddEditDeleteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAddEditDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonAddStudent)
                    .addComponent(buttonEditStudent)
                    .addComponent(buttonDeleteStudent))
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

        panel_StudentView.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panel_StudentView.setMaximumSize(new java.awt.Dimension(512, 32768));
        panel_StudentView.setMinimumSize(new java.awt.Dimension(512, 384));
        panel_StudentView.setPreferredSize(new java.awt.Dimension(532, 384));

        jLabel6.setText("Student Information");

        jLabel2.setText("First Name");

        textfieldNameFirst.setName("textfield_FirstName"); // NOI18N

        jLabel3.setText("Middle Name");

        textfieldNameMiddle.setName("textfield_MiddleName"); // NOI18N

        jLabel4.setText("Last Name");

        textfieldNameLast.setName("textfield_LastName"); // NOI18N

        javax.swing.GroupLayout panelStudentNameLayout = new javax.swing.GroupLayout(panelStudentName);
        panelStudentName.setLayout(panelStudentNameLayout);
        panelStudentNameLayout.setHorizontalGroup(
            panelStudentNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStudentNameLayout.createSequentialGroup()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelStudentNameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelStudentNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textfieldNameFirst)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelStudentNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textfieldNameMiddle)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelStudentNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textfieldNameLast, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(113, 113, 113))
        );
        panelStudentNameLayout.setVerticalGroup(
            panelStudentNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStudentNameLayout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelStudentNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelStudentNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textfieldNameFirst, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addComponent(textfieldNameMiddle)
                    .addComponent(textfieldNameLast))
                .addGap(42, 42, 42))
        );

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonConfirmSaveStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCancelSaveStudent)
                .addContainerGap())
        );
        panelSaveCancelLayout.setVerticalGroup(
            panelSaveCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSaveCancelLayout.createSequentialGroup()
                .addGroup(panelSaveCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCancelSaveStudent)
                    .addComponent(buttonConfirmSaveStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelAdditionalInfo.setLayout(new java.awt.GridLayout(1, 0));

        panel_EmailInfo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_EmailInfo.setMinimumSize(new java.awt.Dimension(276, 256));
        panel_EmailInfo.setPreferredSize(new java.awt.Dimension(276, 359));

        jLabel8.setText("Email Addresses");

        list_EmailAddresses.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list_EmailAddresses.setName("list_EmailAddresses"); // NOI18N
        jScrollPane2.setViewportView(list_EmailAddresses);

        javax.swing.GroupLayout panelEmailsViewLayout = new javax.swing.GroupLayout(panelEmailsView);
        panelEmailsView.setLayout(panelEmailsViewLayout);
        panelEmailsViewLayout.setHorizontalGroup(
            panelEmailsViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmailsViewLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelEmailsViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEmailsViewLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(155, 155, 155))
                    .addGroup(panelEmailsViewLayout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())))
        );
        panelEmailsViewLayout.setVerticalGroup(
            panelEmailsViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmailsViewLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelEmailAddEditDelete.setName("panelEmailAddEditDelete"); // NOI18N

        button_EmailAdd.setText("Add");
        button_EmailAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_EmailAddMouseClicked(evt);
            }
        });

        button_EmailEdit.setText("Edit");
        button_EmailEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_EmailEditMouseClicked(evt);
            }
        });

        button_EmailDelete.setText("Delete");
        button_EmailDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_EmailDeleteMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelEmailAddEditDeleteLayout = new javax.swing.GroupLayout(panelEmailAddEditDelete);
        panelEmailAddEditDelete.setLayout(panelEmailAddEditDeleteLayout);
        panelEmailAddEditDeleteLayout.setHorizontalGroup(
            panelEmailAddEditDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmailAddEditDeleteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button_EmailAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_EmailEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_EmailDelete)
                .addContainerGap())
        );
        panelEmailAddEditDeleteLayout.setVerticalGroup(
            panelEmailAddEditDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmailAddEditDeleteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEmailAddEditDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_EmailEdit)
                    .addComponent(button_EmailAdd)
                    .addComponent(button_EmailDelete))
                .addContainerGap())
        );

        panel_EditEmail.setPreferredSize(new java.awt.Dimension(130, 69));

        jLabel9.setText("Email Address");

        textfield_EmailAddress.setEnabled(false);

        javax.swing.GroupLayout panel_EditEmailLayout = new javax.swing.GroupLayout(panel_EditEmail);
        panel_EditEmail.setLayout(panel_EditEmailLayout);
        panel_EditEmailLayout.setHorizontalGroup(
            panel_EditEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_EditEmailLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panel_EditEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textfield_EmailAddress)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_EditEmailLayout.setVerticalGroup(
            panel_EditEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_EditEmailLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(6, 6, 6)
                .addComponent(textfield_EmailAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_EmailSaveCancel.setName("panelEmailSaveCancel"); // NOI18N

        button_EmailCancel.setText("Cancel");
        button_EmailCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_EmailCancelMouseClicked(evt);
            }
        });

        button_EmailConfirm.setText("Confirm");
        button_EmailConfirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_EmailConfirmMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panel_EmailSaveCancelLayout = new javax.swing.GroupLayout(panel_EmailSaveCancel);
        panel_EmailSaveCancel.setLayout(panel_EmailSaveCancelLayout);
        panel_EmailSaveCancelLayout.setHorizontalGroup(
            panel_EmailSaveCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_EmailSaveCancelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_EmailSaveCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_EmailConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_EmailCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_EmailSaveCancelLayout.setVerticalGroup(
            panel_EmailSaveCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_EmailSaveCancelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(button_EmailConfirm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_EmailCancel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelEmailsEditLayout = new javax.swing.GroupLayout(panelEmailsEdit);
        panelEmailsEdit.setLayout(panelEmailsEditLayout);
        panelEmailsEditLayout.setHorizontalGroup(
            panelEmailsEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panelEmailsEditLayout.createSequentialGroup()
                .addComponent(panel_EditEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_EmailSaveCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelEmailsEditLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelEmailAddEditDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelEmailsEditLayout.setVerticalGroup(
            panelEmailsEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmailsEditLayout.createSequentialGroup()
                .addComponent(panelEmailAddEditDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEmailsEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEmailsEditLayout.createSequentialGroup()
                        .addComponent(panel_EditEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(panel_EmailSaveCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout panel_EmailInfoLayout = new javax.swing.GroupLayout(panel_EmailInfo);
        panel_EmailInfo.setLayout(panel_EmailInfoLayout);
        panel_EmailInfoLayout.setHorizontalGroup(
            panel_EmailInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelEmailsView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelEmailsEdit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_EmailInfoLayout.setVerticalGroup(
            panel_EmailInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_EmailInfoLayout.createSequentialGroup()
                .addComponent(panelEmailsView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelEmailsEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelAdditionalInfo.add(panel_EmailInfo);

        panel_EmailInfo1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_EmailInfo1.setMinimumSize(new java.awt.Dimension(276, 256));

        jLabel10.setText("Majors");

        list_Majors.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list_Majors.setName("list_Majors"); // NOI18N
        jScrollPane5.setViewportView(list_Majors);

        javax.swing.GroupLayout panelEmailsView1Layout = new javax.swing.GroupLayout(panelEmailsView1);
        panelEmailsView1.setLayout(panelEmailsView1Layout);
        panelEmailsView1Layout.setHorizontalGroup(
            panelEmailsView1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmailsView1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelEmailsView1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEmailsView1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(155, 155, 155))
                    .addGroup(panelEmailsView1Layout.createSequentialGroup()
                        .addComponent(jScrollPane5)
                        .addContainerGap())))
        );
        panelEmailsView1Layout.setVerticalGroup(
            panelEmailsView1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmailsView1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelEmailAddEditDelete1.setName("panelEmailAddEditDelete"); // NOI18N

        button_MajorAdd.setText("Add");
        button_MajorAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_MajorAddMouseClicked(evt);
            }
        });

        button_MajorDelete.setText("Remove");
        button_MajorDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_MajorDeleteMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelEmailAddEditDelete1Layout = new javax.swing.GroupLayout(panelEmailAddEditDelete1);
        panelEmailAddEditDelete1.setLayout(panelEmailAddEditDelete1Layout);
        panelEmailAddEditDelete1Layout.setHorizontalGroup(
            panelEmailAddEditDelete1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmailAddEditDelete1Layout.createSequentialGroup()
                .addContainerGap(83, Short.MAX_VALUE)
                .addComponent(button_MajorAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_MajorDelete)
                .addContainerGap())
        );
        panelEmailAddEditDelete1Layout.setVerticalGroup(
            panelEmailAddEditDelete1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmailAddEditDelete1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEmailAddEditDelete1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_MajorAdd)
                    .addComponent(button_MajorDelete))
                .addContainerGap())
        );

        panel_EditMajor.setPreferredSize(new java.awt.Dimension(130, 69));

        jLabel11.setText("Major");

        javax.swing.GroupLayout panel_EditMajorLayout = new javax.swing.GroupLayout(panel_EditMajor);
        panel_EditMajor.setLayout(panel_EditMajorLayout);
        panel_EditMajorLayout.setHorizontalGroup(
            panel_EditMajorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_EditMajorLayout.createSequentialGroup()
                .addGroup(panel_EditMajorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_EditMajorLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_EditMajorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(comboboxMajor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_EditMajorLayout.setVerticalGroup(
            panel_EditMajorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_EditMajorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboboxMajor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_MajorSaveCancel.setName("panelEmailSaveCancel"); // NOI18N

        button_MajorCancel.setText("Cancel");
        button_MajorCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_MajorCancelMouseClicked(evt);
            }
        });

        button_MajorConfirm.setText("Confirm");
        button_MajorConfirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_MajorConfirmMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panel_MajorSaveCancelLayout = new javax.swing.GroupLayout(panel_MajorSaveCancel);
        panel_MajorSaveCancel.setLayout(panel_MajorSaveCancelLayout);
        panel_MajorSaveCancelLayout.setHorizontalGroup(
            panel_MajorSaveCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_MajorSaveCancelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_MajorSaveCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_MajorConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_MajorCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_MajorSaveCancelLayout.setVerticalGroup(
            panel_MajorSaveCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_MajorSaveCancelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(button_MajorConfirm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_MajorCancel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelEmailsEdit1Layout = new javax.swing.GroupLayout(panelEmailsEdit1);
        panelEmailsEdit1.setLayout(panelEmailsEdit1Layout);
        panelEmailsEdit1Layout.setHorizontalGroup(
            panelEmailsEdit1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEmailsEdit1Layout.createSequentialGroup()
                .addComponent(panel_EditMajor, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_MajorSaveCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEmailsEdit1Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(panelEmailAddEditDelete1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelEmailsEdit1Layout.setVerticalGroup(
            panelEmailsEdit1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmailsEdit1Layout.createSequentialGroup()
                .addComponent(panelEmailAddEditDelete1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEmailsEdit1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEmailsEdit1Layout.createSequentialGroup()
                        .addComponent(panel_EditMajor, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(panel_MajorSaveCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout panel_EmailInfo1Layout = new javax.swing.GroupLayout(panel_EmailInfo1);
        panel_EmailInfo1.setLayout(panel_EmailInfo1Layout);
        panel_EmailInfo1Layout.setHorizontalGroup(
            panel_EmailInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelEmailsView1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelEmailsEdit1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_EmailInfo1Layout.setVerticalGroup(
            panel_EmailInfo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_EmailInfo1Layout.createSequentialGroup()
                .addComponent(panelEmailsView1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelEmailsEdit1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelAdditionalInfo.add(panel_EmailInfo1);

        javax.swing.GroupLayout panel_StudentViewLayout = new javax.swing.GroupLayout(panel_StudentView);
        panel_StudentView.setLayout(panel_StudentViewLayout);
        panel_StudentViewLayout.setHorizontalGroup(
            panel_StudentViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_StudentViewLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panel_StudentViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelStudentName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelSaveCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(panelAdditionalInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_StudentViewLayout.setVerticalGroup(
            panel_StudentViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_StudentViewLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(panelStudentName, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAdditionalInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSaveCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(panel_StudentList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_StudentView, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_StudentView, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addComponent(panel_StudentList, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button_EmailAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_EmailAddMouseClicked
        if (button_EmailAdd.isEnabled()){
            addEmail();
        }
    }//GEN-LAST:event_button_EmailAddMouseClicked

    private void button_EmailEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_EmailEditMouseClicked
        if (button_EmailEdit.isEnabled()){
            editEmail();
        }
    }//GEN-LAST:event_button_EmailEditMouseClicked

    private void button_EmailDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_EmailDeleteMouseClicked
        if (button_EmailDelete.isEnabled()){
            deleteEmail();
        }
    }//GEN-LAST:event_button_EmailDeleteMouseClicked

    private void button_EmailConfirmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_EmailConfirmMouseClicked
        if (button_EmailConfirm.isEnabled()){
            confirmSaveEmail();
        }
    }//GEN-LAST:event_button_EmailConfirmMouseClicked

    private void button_EmailCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_EmailCancelMouseClicked
        if (button_EmailCancel.isEnabled()){
            cancelSaveEmail();
        }
    }//GEN-LAST:event_button_EmailCancelMouseClicked

    private void button_MajorAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_MajorAddMouseClicked
        if (button_MajorAdd.isEnabled()){
            addMajor();
        }
    }//GEN-LAST:event_button_MajorAddMouseClicked

    private void button_MajorDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_MajorDeleteMouseClicked
        if (button_MajorDelete.isEnabled()){
            deleteMajor();
        }
    }//GEN-LAST:event_button_MajorDeleteMouseClicked

    private void button_MajorConfirmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_MajorConfirmMouseClicked
        if (button_MajorConfirm.isEnabled()) {
            confirmSaveMajor();
        }
    }//GEN-LAST:event_button_MajorConfirmMouseClicked

    private void button_MajorCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_MajorCancelMouseClicked
        if (button_MajorCancel.isEnabled()) {
            cancelSaveMajor();
        }
    }//GEN-LAST:event_button_MajorCancelMouseClicked

    private void buttonAddStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonAddStudentMouseClicked
        if (buttonAddStudent.isEnabled()) {
            addStudent();
        }
    }//GEN-LAST:event_buttonAddStudentMouseClicked

    private void buttonEditStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonEditStudentMouseClicked
        if (buttonEditStudent.isEnabled()) {
            editStudent();
        }
    }//GEN-LAST:event_buttonEditStudentMouseClicked

    private void buttonDeleteStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonDeleteStudentMouseClicked
        if (buttonDeleteStudent.isEnabled()) {
            deleteStudent();
        }
    }//GEN-LAST:event_buttonDeleteStudentMouseClicked

    private void buttonConfirmSaveStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonConfirmSaveStudentMouseClicked
        if (buttonConfirmSaveStudent.isEnabled()) {
            this.confirmSaveStudent();
        }
    }//GEN-LAST:event_buttonConfirmSaveStudentMouseClicked

    private void buttonCancelSaveStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonCancelSaveStudentMouseClicked
        if (buttonCancelSaveStudent.isEnabled()) {
            this.cancelSaveStudent();
        }
    }//GEN-LAST:event_buttonCancelSaveStudentMouseClicked

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
            java.util.logging.Logger.getLogger(DialogPopulateStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogPopulateStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogPopulateStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogPopulateStudents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogPopulateStudents dialog = new DialogPopulateStudents(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton buttonAddStudent;
    private javax.swing.JButton buttonCancelSaveStudent;
    private javax.swing.JButton buttonConfirmSaveStudent;
    private javax.swing.JButton buttonDeleteStudent;
    private javax.swing.JButton buttonEditStudent;
    private javax.swing.JButton button_EmailAdd;
    private javax.swing.JButton button_EmailCancel;
    private javax.swing.JButton button_EmailConfirm;
    private javax.swing.JButton button_EmailDelete;
    private javax.swing.JButton button_EmailEdit;
    private javax.swing.JButton button_MajorAdd;
    private javax.swing.JButton button_MajorCancel;
    private javax.swing.JButton button_MajorConfirm;
    private javax.swing.JButton button_MajorDelete;
    private javax.swing.JComboBox<ListItem> comboboxMajor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JList<ListItem> list_EmailAddresses;
    private javax.swing.JList<ListItem> list_Majors;
    private javax.swing.JList<String> mainList;
    private javax.swing.JPanel panelAddEditDelete;
    private javax.swing.JPanel panelAdditionalInfo;
    private javax.swing.JPanel panelEmailAddEditDelete;
    private javax.swing.JPanel panelEmailAddEditDelete1;
    private javax.swing.JPanel panelEmailsEdit;
    private javax.swing.JPanel panelEmailsEdit1;
    private javax.swing.JPanel panelEmailsView;
    private javax.swing.JPanel panelEmailsView1;
    private javax.swing.JPanel panelSaveCancel;
    private javax.swing.JPanel panelStudentName;
    private javax.swing.JPanel panel_EditEmail;
    private javax.swing.JPanel panel_EditMajor;
    private javax.swing.JPanel panel_EmailInfo;
    private javax.swing.JPanel panel_EmailInfo1;
    private javax.swing.JPanel panel_EmailSaveCancel;
    private javax.swing.JPanel panel_MajorSaveCancel;
    private javax.swing.JPanel panel_StudentList;
    private javax.swing.JPanel panel_StudentView;
    private javax.swing.JTextField textfieldNameFirst;
    private javax.swing.JTextField textfieldNameLast;
    private javax.swing.JTextField textfieldNameMiddle;
    private javax.swing.JTextField textfield_EmailAddress;
    // End of variables declaration//GEN-END:variables
}
