package com.hospital.www.view;

import com.hospital.www.model.Patient;
import com.hospital.www.controller.PatientController;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PatientEditDialog extends JDialog {
    private JTextField idField, firstNameField, lastNameField;
    private JTextField dobField, nhsField, genderField;
    private JTextField phoneField, emailField, addressField;
    private JTextField postcodeField, emergencyNameField, emergencyPhoneField;
    private JTextField surgeryIdField;
    private Patient patient;
    private boolean confirmed = false;
    private PatientController controller;

    public PatientEditDialog(Frame owner, Patient patient) {
        super(owner, patient == null ? "Add Patient" : "Edit Patient", true);
        this.patient = patient;
        this.controller = new PatientController();
        setupUI();
        if (patient != null) {
            populateFields();
        } else {
            idField.setText(controller.generateNextId());
        }
    }

    private void setupUI() {
        setLayout(new BorderLayout(10, 10));
        setSize(500, 600);
        setLocationRelativeTo(getOwner());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        int row = 0;
        
        addField(formPanel, gbc, row++, "Patient ID:", idField = new JTextField(20));
        idField.setEditable(false);
        addField(formPanel, gbc, row++, "First Name:", firstNameField = new JTextField(20));
        addField(formPanel, gbc, row++, "Last Name:", lastNameField = new JTextField(20));
        addField(formPanel, gbc, row++, "Date of Birth:", dobField = new JTextField(20));
        addField(formPanel, gbc, row++, "NHS Number:", nhsField = new JTextField(20));
        addField(formPanel, gbc, row++, "Gender (M/F):", genderField = new JTextField(20));
        addField(formPanel, gbc, row++, "Phone:", phoneField = new JTextField(20));
        addField(formPanel, gbc, row++, "Email:", emailField = new JTextField(20));
        addField(formPanel, gbc, row++, "Address:", addressField = new JTextField(20));
        addField(formPanel, gbc, row++, "Postcode:", postcodeField = new JTextField(20));
        addField(formPanel, gbc, row++, "Emergency Contact:", emergencyNameField = new JTextField(20));
        addField(formPanel, gbc, row++, "Emergency Phone:", emergencyPhoneField = new JTextField(20));
        addField(formPanel, gbc, row++, "GP Surgery ID:", surgeryIdField = new JTextField(20));

        JScrollPane scrollPane = new JScrollPane(formPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");

        saveBtn.addActionListener(e -> savePatient());
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

    private void populateFields() {
        idField.setText(patient.getPatientId());
        firstNameField.setText(patient.getFirstName());
        lastNameField.setText(patient.getLastName());
        dobField.setText(patient.getDateOfBirth());
        nhsField.setText(patient.getNhsNumber());
        genderField.setText(patient.getGender());
        phoneField.setText(patient.getPhoneNumber());
        emailField.setText(patient.getEmail());
        addressField.setText(patient.getAddress());
        postcodeField.setText(patient.getPostcode());
        emergencyNameField.setText(patient.getEmergencyContactName());
        emergencyPhoneField.setText(patient.getEmergencyContactPhone());
        surgeryIdField.setText(patient.getGpSurgeryId());
    }

    private void savePatient() {
        if (!validateInput()) {
            return;
        }

        if (patient == null) {
            patient = new Patient();
        }

        patient.setPatientId(idField.getText().trim());
        patient.setFirstName(firstNameField.getText().trim());
        patient.setLastName(lastNameField.getText().trim());
        patient.setDateOfBirth(dobField.getText().trim());
        patient.setNhsNumber(nhsField.getText().trim());
        patient.setGender(genderField.getText().trim());
        patient.setPhoneNumber(phoneField.getText().trim());
        patient.setEmail(emailField.getText().trim());
        patient.setAddress(addressField.getText().trim());
        patient.setPostcode(postcodeField.getText().trim());
        patient.setEmergencyContactName(emergencyNameField.getText().trim());
        patient.setEmergencyContactPhone(emergencyPhoneField.getText().trim());
        patient.setGpSurgeryId(surgeryIdField.getText().trim());
        
        if (patient.getRegistrationDate() == null || patient.getRegistrationDate().isEmpty()) {
            patient.setRegistrationDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }

        confirmed = true;
        dispose();
    }

    private boolean validateInput() {
        if (firstNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "First name is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (lastNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Last name is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (nhsField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "NHS number is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Patient getPatient() {
        return patient;
    }
}
