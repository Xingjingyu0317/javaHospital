package com.hospital.www.view;

import com.hospital.www.model.Patient;
import com.hospital.www.controller.PatientController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// patient management
public class PatientManagePanel extends JPanel {
    private JTable patientTable;
    private DefaultTableModel tableModel;
    private PatientController controller;

    public PatientManagePanel() {
        this.controller = new PatientController();
        initUI();
        loadPatientData();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(createTopPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JLabel titleLabel = new JLabel("Patient Records");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel);
        
        return panel;
    }

    private JScrollPane createTablePanel() {
        String[] columnNames = {
            "Patient ID", "First Name", "Last Name", "DOB", 
            "NHS Number", "Gender", "Phone", "Email"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        patientTable = new JTable(tableModel);
        patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        patientTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(patientTable);
        return scrollPane;
    }

    private void loadPatientData() {
        tableModel.setRowCount(0);
        List<Patient> patients = controller.getAllPatients();
        
        for (Patient p : patients) {
            Object[] row = {
                p.getPatientId(),
                p.getFirstName(),
                p.getLastName(),
                p.getDateOfBirth(),
                p.getNhsNumber(),
                p.getGender(),
                p.getPhoneNumber(),
                p.getEmail()
            };
            tableModel.addRow(row);
        }
    }

    public void refreshData() {
        loadPatientData();
    }
}
