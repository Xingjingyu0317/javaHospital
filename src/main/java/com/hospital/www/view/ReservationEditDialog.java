package com.hospital.www.view;

import com.hospital.www.model.Appointment;
import com.hospital.www.controller.ReservationController;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservationEditDialog extends JDialog {
    private JTextField idField, patientIdField, clinicianIdField, facilityIdField;
    private JTextField dateField, timeField, durationField, typeField;
    private JTextField statusField, reasonField, notesField;
    private Appointment appointment;
    private boolean confirmed = false;
    private ReservationController controller;

    public ReservationEditDialog(Frame owner, Appointment appointment) {
        super(owner, appointment == null ? "Add Appointment" : "Edit Appointment", true);
        this.appointment = appointment;
        this.controller = new ReservationController();
        buildUI();
        if (appointment != null) {
            fillFields();
        } else {
            idField.setText(controller.generateId());
            dateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        setSize(500, 550);
        setLocationRelativeTo(getOwner());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        int row = 0;
        addField(formPanel, gbc, row++, "Appointment ID:", idField = new JTextField(20));
        idField.setEditable(false);
        addField(formPanel, gbc, row++, "Patient ID:", patientIdField = new JTextField(20));
        addField(formPanel, gbc, row++, "Clinician ID:", clinicianIdField = new JTextField(20));
        addField(formPanel, gbc, row++, "Facility ID:", facilityIdField = new JTextField(20));
        addField(formPanel, gbc, row++, "Date (yyyy-MM-dd):", dateField = new JTextField(20));
        addField(formPanel, gbc, row++, "Time (HH:mm):", timeField = new JTextField(20));
        addField(formPanel, gbc, row++, "Duration (minutes):", durationField = new JTextField(20));
        addField(formPanel, gbc, row++, "Type:", typeField = new JTextField(20));
        addField(formPanel, gbc, row++, "Status:", statusField = new JTextField(20));
        addField(formPanel, gbc, row++, "Reason:", reasonField = new JTextField(20));
        addField(formPanel, gbc, row++, "Notes:", notesField = new JTextField(20));

        JScrollPane scrollPane = new JScrollPane(formPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");

        saveBtn.addActionListener(e -> saveAppointment());
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
        idField.setText(appointment.getAppointmentId());
        patientIdField.setText(appointment.getPatientId());
        clinicianIdField.setText(appointment.getClinicianId());
        facilityIdField.setText(appointment.getFacilityId());
        dateField.setText(appointment.getAppointmentDate());
        timeField.setText(appointment.getAppointmentTime());
        durationField.setText(String.valueOf(appointment.getDurationMinutes()));
        typeField.setText(appointment.getAppointmentType());
        statusField.setText(appointment.getStatus());
        reasonField.setText(appointment.getReasonForVisit());
        notesField.setText(appointment.getNotes());
    }

    private void saveAppointment() {
        if (!validateInput()) {
            return;
        }

        if (appointment == null) {
            appointment = new Appointment();
        }

        appointment.setAppointmentId(idField.getText().trim());
        appointment.setPatientId(patientIdField.getText().trim());
        appointment.setClinicianId(clinicianIdField.getText().trim());
        appointment.setFacilityId(facilityIdField.getText().trim());
        appointment.setAppointmentDate(dateField.getText().trim());
        appointment.setAppointmentTime(timeField.getText().trim());
        appointment.setDurationMinutes(Integer.parseInt(durationField.getText().trim()));
        appointment.setAppointmentType(typeField.getText().trim());
        appointment.setStatus(statusField.getText().trim());
        appointment.setReasonForVisit(reasonField.getText().trim());
        appointment.setNotes(notesField.getText().trim());
        
        if (appointment.getCreatedDate() == null || appointment.getCreatedDate().isEmpty()) {
            String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            appointment.setCreatedDate(today);
            appointment.setLastModified(today);
        }

        confirmed = true;
        dispose();
    }

    private boolean validateInput() {
        if (patientIdField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Patient ID required", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (clinicianIdField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Clinician ID required", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Appointment getAppointment() {
        return appointment;
    }
}
