package com.hospital.www.view;

import com.hospital.www.model.Clinician;
import com.hospital.www.controller.ClinicianController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClinicianManagePanel extends JPanel {
    private JTable clinicianTable;
    private DefaultTableModel tableData;
    private ClinicianController ctrl;
    private JButton addBtn, editBtn, deleteBtn, refreshBtn;

    public ClinicianManagePanel() {
        this.ctrl = new ClinicianController();
        setupInterface();
        loadData();
    }

    private void setupInterface() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildHeader(), BorderLayout.NORTH);
        add(buildTable(), BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        
        JLabel title = new JLabel("Clinician Records");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(title, BorderLayout.WEST);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addBtn = new JButton("Add");
        editBtn = new JButton("Edit");
        deleteBtn = new JButton("Delete");
        refreshBtn = new JButton("Refresh");
        
        addBtn.addActionListener(e -> addClinician());
        editBtn.addActionListener(e -> editClinician());
        deleteBtn.addActionListener(e -> deleteClinician());
        refreshBtn.addActionListener(e -> loadData());
        
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(refreshBtn);
        
        headerPanel.add(btnPanel, BorderLayout.EAST);
        
        return headerPanel;
    }

    private JScrollPane buildTable() {
        String[] cols = {
            "Clinician ID", "Title", "First Name", "Last Name",
            "Speciality", "GMC Number", "Workplace Type", "Status"
        };
        
        tableData = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        clinicianTable = new JTable(tableData);
        clinicianTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        clinicianTable.setRowHeight(25);
        
        return new JScrollPane(clinicianTable);
    }

    private void loadData() {
        tableData.setRowCount(0);
        List<Clinician> clinicians = ctrl.getAll();
        
        for (Clinician c : clinicians) {
            Object[] rowData = {
                c.getClinicianId(),
                c.getTitle(),
                c.getFirstName(),
                c.getLastName(),
                c.getSpeciality(),
                c.getGmcNumber(),
                c.getWorkplaceType(),
                c.getEmploymentStatus()
            };
            tableData.addRow(rowData);
        }
    }

    private void addClinician() {
        ClinicianFormDialog dialog = new ClinicianFormDialog(
            (Frame) SwingUtilities.getWindowAncestor(this), null);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            Clinician newClinician = dialog.getClinician();
            if (ctrl.create(newClinician)) {
                loadData();
                JOptionPane.showMessageDialog(this, "Clinician added", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editClinician() {
        int selectedRow = clinicianTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a clinician to edit", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String clinicianId = (String) tableData.getValueAt(selectedRow, 0);
        Clinician clinician = ctrl.getById(clinicianId);
        
        if (clinician != null) {
            ClinicianFormDialog dialog = new ClinicianFormDialog(
                (Frame) SwingUtilities.getWindowAncestor(this), clinician);
            dialog.setVisible(true);
            
            if (dialog.isConfirmed()) {
                if (ctrl.update(clinician)) {
                    loadData();
                    JOptionPane.showMessageDialog(this, "Clinician updated", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void deleteClinician() {
        int selectedRow = clinicianTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a clinician to delete", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String clinicianId = (String) tableData.getValueAt(selectedRow, 0);
        String name = tableData.getValueAt(selectedRow, 2) + " " + tableData.getValueAt(selectedRow, 3);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete clinician: " + name + "?",
            "Confirm",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (ctrl.delete(clinicianId)) {
                loadData();
                JOptionPane.showMessageDialog(this, "Clinician deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void refresh() {
        loadData();
    }
}
