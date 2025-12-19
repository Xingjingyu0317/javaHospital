package com.hospital.www.view;

import com.hospital.www.model.Referral;
import com.hospital.www.controller.TransferController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TransferManagePanel extends JPanel {
    private JTable referralTable;
    private DefaultTableModel tableModel;
    private TransferController controller;
    private JButton refreshBtn, exportBtn, exportEmailBtn;

    public TransferManagePanel() {
        this.controller = new TransferController();
        buildUI();
        loadReferrals();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(createHeader(), BorderLayout.NORTH);
        add(createTable(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JLabel title = new JLabel("Referral Records");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(title, BorderLayout.WEST);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exportBtn = new JButton("Export Referral");
        exportEmailBtn = new JButton("Generate Email");
        refreshBtn = new JButton("Refresh");
        
        exportBtn.addActionListener(e -> exportReferral());
        exportEmailBtn.addActionListener(e -> generateEmail());
        refreshBtn.addActionListener(e -> loadReferrals());
        
        buttonPanel.add(exportBtn);
        buttonPanel.add(exportEmailBtn);
        buttonPanel.add(refreshBtn);
        
        panel.add(buttonPanel, BorderLayout.EAST);
        
        return panel;
    }

    private JScrollPane createTable() {
        String[] columns = {
            "Referral ID", "Patient ID", "From Clinician", "To Clinician",
            "Date", "Urgency", "Reason", "Status"
        };
        
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        referralTable = new JTable(tableModel);
        referralTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        referralTable.setRowHeight(25);
        
        return new JScrollPane(referralTable);
    }

    private void loadReferrals() {
        tableModel.setRowCount(0);
        List<Referral> referrals = controller.getAllReferrals();
        
        for (Referral ref : referrals) {
            Object[] row = {
                ref.getReferralId(),
                ref.getPatientId(),
                ref.getReferringClinicianId(),
                ref.getReferredToClinicianId(),
                ref.getReferralDate(),
                ref.getUrgencyLevel(),
                ref.getReferralReason(),
                ref.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    private void exportReferral() {
        int selectedRow = referralTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a referral to export",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String referralId = (String) tableModel.getValueAt(selectedRow, 0);
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("referral_" + referralId + ".txt"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            
            if (controller.exportToFile(referralId, filePath)) {
                JOptionPane.showMessageDialog(this,
                    "Referral exported successfully to:\n" + filePath,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to export referral",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void generateEmail() {
        int selectedRow = referralTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a referral to generate email",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String referralId = (String) tableModel.getValueAt(selectedRow, 0);
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("referral_email_" + referralId + ".txt"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            
            if (controller.generateEmailAndSave(referralId, filePath)) {
                JOptionPane.showMessageDialog(this,
                    "Referral email generated successfully to:\n" + filePath,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to generate email",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void refresh() {
        loadReferrals();
    }
}
