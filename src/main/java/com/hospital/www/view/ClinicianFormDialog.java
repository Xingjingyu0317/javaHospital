package com.hospital.www.view;

import com.hospital.www.model.Clinician;
import com.hospital.www.controller.ClinicianController;
import javax.swing.*;
import java.awt.*;

public class ClinicianFormDialog extends JDialog {
    private JTextField idField, firstField, lastField, titleField;
    private JTextField specialtyField, gmcField, phoneField, emailField;
    private JTextField workplaceIdField, workplaceTypeField;
    private JTextField statusField, startDateField;
    private Clinician clinician;
    private boolean confirmed = false;
    private ClinicianController controller;

    public ClinicianFormDialog(Frame owner, Clinician clinician) {
        super(owner, clinician == null ? "Add Clinician" : "Edit Clinician", true);
        this.clinician = clinician;
        this.controller = new ClinicianController();
        buildUI();
        if (clinician != null) {
            fillFields();
        } else {
            idField.setText(controller.getNextId());
        }
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        setSize(450, 500);
        setLocationRelativeTo(getOwner());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        int row = 0;
        addFormField(formPanel, gbc, row++, "Clinician ID:", idField = new JTextField(20));
        idField.setEditable(false);
        addFormField(formPanel, gbc, row++, "Title:", titleField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "First Name:", firstField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "Last Name:", lastField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "Speciality:", specialtyField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "GMC Number:", gmcField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "Phone:", phoneField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "Email:", emailField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "Workplace ID:", workplaceIdField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "Workplace Type:", workplaceTypeField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "Status:", statusField = new JTextField(20));
        addFormField(formPanel, gbc, row++, "Start Date:", startDateField = new JTextField(20));

        JScrollPane scrollPane = new JScrollPane(formPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> saveClinician());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String label, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(field, gbc);
    }

    private void fillFields() {
        idField.setText(clinician.getClinicianId());
        titleField.setText(clinician.getTitle());
        firstField.setText(clinician.getFirstName());
        lastField.setText(clinician.getLastName());
        specialtyField.setText(clinician.getSpeciality());
        gmcField.setText(clinician.getGmcNumber());
        phoneField.setText(clinician.getPhoneNumber());
        emailField.setText(clinician.getEmail());
        workplaceIdField.setText(clinician.getWorkplaceId());
        workplaceTypeField.setText(clinician.getWorkplaceType());
        statusField.setText(clinician.getEmploymentStatus());
        startDateField.setText(clinician.getStartDate());
    }

    private void saveClinician() {
        if (!validateData()) {
            return;
        }

        if (clinician == null) {
            clinician = new Clinician();
        }

        clinician.setClinicianId(idField.getText().trim());
        clinician.setTitle(titleField.getText().trim());
        clinician.setFirstName(firstField.getText().trim());
        clinician.setLastName(lastField.getText().trim());
        clinician.setSpeciality(specialtyField.getText().trim());
        clinician.setGmcNumber(gmcField.getText().trim());
        clinician.setPhoneNumber(phoneField.getText().trim());
        clinician.setEmail(emailField.getText().trim());
        clinician.setWorkplaceId(workplaceIdField.getText().trim());
        clinician.setWorkplaceType(workplaceTypeField.getText().trim());
        clinician.setEmploymentStatus(statusField.getText().trim());
        clinician.setStartDate(startDateField.getText().trim());

        confirmed = true;
        dispose();
    }

    private boolean validateData() {
        if (firstField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "First name required", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (lastField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Last name required", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Clinician getClinician() {
        return clinician;
    }
}
