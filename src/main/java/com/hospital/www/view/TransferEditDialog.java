package com.hospital.www.view;

import com.hospital.www.model.Referral;
import com.hospital.www.controller.TransferController;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransferEditDialog extends JDialog {
    private JTextField patientIdField, fromClinicianField, toClinicianField;
    private JTextField fromFacilityField, toFacilityField, dateField;
    private JTextField urgencyField, reasonField, summaryField;
    private JTextField investigationsField, statusField, notesField;
    private Referral referral;
    private boolean confirmed = false;

    public TransferEditDialog(Frame owner, Referral referral) {
        super(owner, referral == null ? "Add Referral" : "Edit Referral", true);
        this.referral = referral;
        buildUI();
        if (referral != null) {
            fillFields();
        } else {
            dateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        setSize(500, 600);
        setLocationRelativeTo(getOwner());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        int row = 0;
        addField(formPanel, gbc, row++, "Patient ID:", patientIdField = new JTextField(20));
        addField(formPanel, gbc, row++, "From Clinician ID:", fromClinicianField = new JTextField(20));
        addField(formPanel, gbc, row++, "To Clinician ID:", toClinicianField = new JTextField(20));
        addField(formPanel, gbc, row++, "From Facility ID:", fromFacilityField = new JTextField(20));
        addField(formPanel, gbc, row++, "To Facility ID:", toFacilityField = new JTextField(20));
        addField(formPanel, gbc, row++, "Date (yyyy-MM-dd):", dateField = new JTextField(20));
        addField(formPanel, gbc, row++, "Urgency Level:", urgencyField = new JTextField(20));
        addField(formPanel, gbc, row++, "Reason:", reasonField = new JTextField(20));
        addField(formPanel, gbc, row++, "Clinical Summary:", summaryField = new JTextField(20));
        addField(formPanel, gbc, row++, "Investigations:", investigationsField = new JTextField(20));
        addField(formPanel, gbc, row++, "Status:", statusField = new JTextField(20));
        addField(formPanel, gbc, row++, "Notes:", notesField = new JTextField(20));

        JScrollPane scrollPane = new JScrollPane(formPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");

        saveBtn.addActionListener(e -> saveReferral());
        cancelBtn.addActionListener(e -> dispose());

        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int row, String label, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(field, gbc);
    }

    private void fillFields() {
        patientIdField.setText(referral.getPatientId());
        fromClinicianField.setText(referral.getReferringClinicianId());
        toClinicianField.setText(referral.getReferredToClinicianId());
        fromFacilityField.setText(referral.getReferringFacilityId());
        toFacilityField.setText(referral.getReferredToFacilityId());
        dateField.setText(referral.getReferralDate());
        urgencyField.setText(referral.getUrgencyLevel());
        reasonField.setText(referral.getReferralReason());
        summaryField.setText(referral.getClinicalSummary());
        investigationsField.setText(referral.getRequestedInvestigations());
        statusField.setText(referral.getStatus());
        notesField.setText(referral.getNotes());
    }

    private void saveReferral() {
        if (!validateInput()) {
            return;
        }

        if (referral == null) {
            referral = new Referral();
        }

        referral.setPatientId(patientIdField.getText().trim());
        referral.setReferringClinicianId(fromClinicianField.getText().trim());
        referral.setReferredToClinicianId(toClinicianField.getText().trim());
        referral.setReferringFacilityId(fromFacilityField.getText().trim());
        referral.setReferredToFacilityId(toFacilityField.getText().trim());
        referral.setReferralDate(dateField.getText().trim());
        referral.setUrgencyLevel(urgencyField.getText().trim());
        referral.setReferralReason(reasonField.getText().trim());
        referral.setClinicalSummary(summaryField.getText().trim());
        referral.setRequestedInvestigations(investigationsField.getText().trim());
        referral.setStatus(statusField.getText().trim());
        referral.setNotes(notesField.getText().trim());
        
        if (referral.getCreatedDate() == null || referral.getCreatedDate().isEmpty()) {
            String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            referral.setCreatedDate(today);
            referral.setLastUpdated(today);
        }

        confirmed = true;
        dispose();
    }

    private boolean validateInput() {
        if (patientIdField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Patient ID required", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (fromClinicianField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "From Clinician ID required", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (toClinicianField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "To Clinician ID required", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Referral getReferral() {
        return referral;
    }
}
