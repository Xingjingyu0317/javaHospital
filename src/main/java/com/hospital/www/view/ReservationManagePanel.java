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
    private JButton refreshBtn;

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
        refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadAppointments());
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

    public void refresh() {
        loadAppointments();
    }
}
