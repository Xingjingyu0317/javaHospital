package com.hospital.www.view;

import com.hospital.www.model.Prescription;
import com.hospital.www.controller.PrescriptionController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PrescriptionViewPanel extends JPanel {
    private JTable prescriptionTable;
    private DefaultTableModel tableModel;
    private PrescriptionController controller;
    private JButton refreshBtn, exportBtn;

    public PrescriptionViewPanel() {
        this.controller = new PrescriptionController();
        buildUI();
        loadPrescriptions();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(createHeader(), BorderLayout.NORTH);
        add(createTable(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JLabel title = new JLabel("Prescription Records");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(title, BorderLayout.WEST);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exportBtn = new JButton("Export to File");
        refreshBtn = new JButton("Refresh");
        
        exportBtn.addActionListener(e -> exportPrescription());
        refreshBtn.addActionListener(e -> loadPrescriptions());
        
        buttonPanel.add(exportBtn);
        buttonPanel.add(refreshBtn);
        
        panel.add(buttonPanel, BorderLayout.EAST);
        
        return panel;
    }

    private JScrollPane createTable() {
        String[] columns = {
            "Prescription ID", "Patient ID", "Clinician ID", "Date",
            "Medication", "Dosage", "Frequency", "Pharmacy", "Status"
        };
        
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        prescriptionTable = new JTable(tableModel);
        prescriptionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        prescriptionTable.setRowHeight(25);
        
        return new JScrollPane(prescriptionTable);
    }

    private void loadPrescriptions() {
        tableModel.setRowCount(0);
        List<Prescription> prescriptions = controller.getAllPrescriptions();
        
        for (Prescription rx : prescriptions) {
            Object[] row = {
                rx.getPrescriptionId(),
                rx.getPatientId(),
                rx.getClinicianId(),
                rx.getPrescriptionDate(),
                rx.getMedicationName(),
                rx.getDosage(),
                rx.getFrequency(),
                rx.getPharmacyName(),
                rx.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    private void exportPrescription() {
        int selectedRow = prescriptionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a prescription to export",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String prescriptionId = (String) tableModel.getValueAt(selectedRow, 0);
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("prescription_" + prescriptionId + ".txt"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            
            if (controller.exportToFile(prescriptionId, filePath)) {
                JOptionPane.showMessageDialog(this,
                    "Prescription exported successfully to:\n" + filePath,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to export prescription",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void refresh() {
        loadPrescriptions();
    }
}
