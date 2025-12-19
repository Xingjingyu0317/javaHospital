package com.hospital.www.view;

import com.hospital.www.model.Appointment;
import com.hospital.www.controller.ReservationController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReservationManagePanel extends JPanel {
    private JTable appointTable;
    private DefaultTableModel tableModel;
    private ReservationController controller;
    private JButton addBtn, editBtn, deleteBtn, refreshBtn;

    public ReservationManagePanel() {
        this.controller = new ReservationController();
        buildUI();
        loadAppointments();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(createTopPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Appointment Records");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.WEST);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addBtn = new JButton("Add");
        editBtn = new JButton("Edit");
        deleteBtn = new JButton("Delete");
        refreshBtn = new JButton("Refresh");
        
        addBtn.addActionListener(e -> addAppointment());
        editBtn.addActionListener(e -> editAppointment());
        deleteBtn.addActionListener(e -> deleteAppointment());
        refreshBtn.addActionListener(e -> loadAppointments());
        
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(refreshBtn);
        
        panel.add(btnPanel, BorderLayout.EAST);
        
        return panel;
    }

    private JScrollPane createTablePanel() {
        String[] columns = {
            "Appointment ID", "Patient ID", "Clinician ID", "Facility ID",
            "Date", "Time", "Type", "Status", "Reason"
        };
        
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        appointTable = new JTable(tableModel);
        appointTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        appointTable.setRowHeight(25);
        
        return new JScrollPane(appointTable);
    }

    private void loadAppointments() {
        tableModel.setRowCount(0);
        List<Appointment> appointments = controller.getAll();
        
        for (Appointment apt : appointments) {
            Object[] row = {
                apt.getAppointmentId(),
                apt.getPatientId(),
                apt.getClinicianId(),
                apt.getFacilityId(),
                apt.getAppointmentDate(),
                apt.getAppointmentTime(),
                apt.getAppointmentType(),
                apt.getStatus(),
                apt.getReasonForVisit()
            };
            tableModel.addRow(row);
        }
    }

    private void addAppointment() {
        ReservationEditDialog dialog = new ReservationEditDialog(
            (Frame) SwingUtilities.getWindowAncestor(this), null);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            Appointment newAppointment = dialog.getAppointment();
            if (controller.add(newAppointment)) {
                loadAppointments();
                JOptionPane.showMessageDialog(this,
                    "Appointment added successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to add appointment",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editAppointment() {
        int selectedRow = appointTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select an appointment to edit",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String appointmentId = (String) tableModel.getValueAt(selectedRow, 0);
        Appointment appointment = controller.getAll().stream()
            .filter(a -> a.getAppointmentId().equals(appointmentId))
            .findFirst().orElse(null);
        
        if (appointment != null) {
            ReservationEditDialog dialog = new ReservationEditDialog(
                (Frame) SwingUtilities.getWindowAncestor(this), appointment);
            dialog.setVisible(true);
            
            if (dialog.isConfirmed()) {
                if (controller.modify(appointment)) {
                    loadAppointments();
                    JOptionPane.showMessageDialog(this,
                        "Appointment updated successfully",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Failed to update appointment",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void deleteAppointment() {
        int selectedRow = appointTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select an appointment to delete",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String appointmentId = (String) tableModel.getValueAt(selectedRow, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete appointment: " + appointmentId + "?",
            "Confirm",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.remove(appointmentId)) {
                loadAppointments();
                JOptionPane.showMessageDialog(this,
                    "Appointment deleted successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to delete appointment",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void refresh() {
        loadAppointments();
    }
}
