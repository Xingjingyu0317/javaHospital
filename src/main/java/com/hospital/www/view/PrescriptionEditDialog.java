package com.hospital.www.view;

import com.hospital.www.model.Prescription;
import com.hospital.www.controller.PrescriptionController;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrescriptionEditDialog extends JDialog {
    private JTextField idField, patientIdField, clinicianIdField, appointmentIdField;
    private JTextField dateField, medicationField, dosageField, frequencyField;
    private JTextField durationField, quantityField, instructionsField;
    private JTextField pharmacyField, statusField;
    private Prescription prescription;
    private boolean confirmed = false;
    private PrescriptionController controller;

    public PrescriptionEditDialog(Frame owner, Prescription prescription) {
        super(owner, prescription == null ? "Add Prescription" : "Edit Prescription", true);
        this.prescription = prescription;
        this.controller = new PrescriptionController();
        buildUI();
        if (prescription != null) {
            fillFields();
        } else {
            idField.setText(controller.getNewId());
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
        addField(formPanel, gbc, row++, "Prescription ID:", idField = new JTextField(20));
        idField.setEditable(false);
        addField(formPanel, gbc, row++, "Patient ID:", patientIdField = new JTextField(20));
        addField(formPanel, gbc, row++, "Clinician ID:", clinicianIdField = new JTextField(20));
        addField(formPanel, gbc, row++, "Appointment ID:", appointmentIdField = new JTextField(20));
        addField(formPanel, gbc, row++, "Date (yyyy-MM-dd):", dateField = new JTextField(20));
        addField(formPanel, gbc, row++, "Medication Name:", medicationField = new JTextField(20));
        addField(formPanel, gbc, row++, "Dosage:", dosageField = new JTextField(20));
        addField(formPanel, gbc, row++, "Frequency:", frequencyField = new JTextField(20));
        addField(formPanel, gbc, row++, "Duration (days):", durationField = new JTextField(20));
        addField(formPanel, gbc, row++, "Quantity:", quantityField = new JTextField(20));
        addField(formPanel, gbc, row++, "Instructions:", instructionsField = new JTextField(20));
        addField(formPanel, gbc, row++, "Pharmacy:", pharmacyField = new JTextField(20));
        addField(formPanel, gbc, row++, "Status:", statusField = new JTextField(20));

        JScrollPane scrollPane = new JScrollPane(formPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");

        saveBtn.addActionListener(e -> savePrescription());
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
        idField.setText(prescription.getPrescriptionId());
        patientIdField.setText(prescription.getPatientId());
        clinicianIdField.setText(prescription.getClinicianId());
        appointmentIdField.setText(prescription.getAppointmentId());
        dateField.setText(prescription.getPrescriptionDate());
        medicationField.setText(prescription.getMedicationName());
        dosageField.setText(prescription.getDosage());
        frequencyField.setText(prescription.getFrequency());
        durationField.setText(String.valueOf(prescription.getDurationDays()));
        quantityField.setText(String.valueOf(prescription.getQuantity()));
        instructionsField.setText(prescription.getInstructions());
        pharmacyField.setText(prescription.getPharmacyName());
        statusField.setText(prescription.getStatus());
    }

    private void savePrescription() {
        if (!validateInput()) {
            return;
        }

        if (prescription == null) {
            prescription = new Prescription();
        }

        prescription.setPrescriptionId(idField.getText().trim());
        prescription.setPatientId(patientIdField.getText().trim());
        prescription.setClinicianId(clinicianIdField.getText().trim());
        prescription.setAppointmentId(appointmentIdField.getText().trim());
        prescription.setPrescriptionDate(dateField.getText().trim());
        prescription.setMedicationName(medicationField.getText().trim());
        prescription.setDosage(dosageField.getText().trim());
        prescription.setFrequency(frequencyField.getText().trim());
        prescription.setDurationDays(Integer.parseInt(durationField.getText().trim()));
        prescription.setQuantity(quantityField.getText().trim());
        prescription.setInstructions(instructionsField.getText().trim());
        prescription.setPharmacyName(pharmacyField.getText().trim());
        prescription.setStatus(statusField.getText().trim());
        
        if (prescription.getIssueDate() == null || prescription.getIssueDate().isEmpty()) {
            prescription.setIssueDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }

        confirmed = true;
        dispose();
    }

    private boolean validateInput() {
        if (patientIdField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Patient ID required", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (medicationField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Medication name required", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Prescription getPrescription() {
        return prescription;
    }
}
