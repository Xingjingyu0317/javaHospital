package com.hospital.www.view;

import com.hospital.www.model.Patient;
import com.hospital.www.controller.PatientController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientManagePanel extends JPanel {
    private JTable patientTable;
    private DefaultTableModel tableModel;
    private PatientController controller;
    private JButton addBtn, editBtn, deleteBtn, refreshBtn;

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
        JPanel panel = new JPanel(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Patient Records");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.WEST);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addBtn = new JButton("Add");
        editBtn = new JButton("Edit");
        deleteBtn = new JButton("Delete");
        refreshBtn = new JButton("Refresh");
        
        addBtn.addActionListener(e -> addPatient());
        editBtn.addActionListener(e -> editPatient());
        deleteBtn.addActionListener(e -> deletePatient());
        refreshBtn.addActionListener(e -> loadPatientData());
        
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(refreshBtn);
        
        panel.add(btnPanel, BorderLayout.EAST);
        
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

    private void addPatient() {
        PatientEditDialog dialog = new PatientEditDialog((Frame) SwingUtilities.getWindowAncestor(this), null);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            Patient newPatient = dialog.getPatient();
            if (controller.addPatient(newPatient)) {
                loadPatientData();
                JOptionPane.showMessageDialog(this, "Patient added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add patient", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editPatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to edit", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String patientId = (String) tableModel.getValueAt(selectedRow, 0);
        Patient patient = controller.findPatientById(patientId);
        
        if (patient != null) {
            PatientEditDialog dialog = new PatientEditDialog((Frame) SwingUtilities.getWindowAncestor(this), patient);
            dialog.setVisible(true);
            
            if (dialog.isConfirmed()) {
                if (controller.modifyPatient(patient)) {
                    loadPatientData();
                    JOptionPane.showMessageDialog(this, "Patient updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update patient", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void deletePatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to delete", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String patientId = (String) tableModel.getValueAt(selectedRow, 0);
        String patientName = tableModel.getValueAt(selectedRow, 1) + " " + tableModel.getValueAt(selectedRow, 2);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete patient: " + patientName + "?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.removePatient(patientId)) {
                loadPatientData();
                JOptionPane.showMessageDialog(this, "Patient deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete patient", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void refreshData() {
        loadPatientData();
    }
}
