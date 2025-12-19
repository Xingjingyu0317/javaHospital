package com.hospital.www.view;

import com.hospital.www.utils.DataManager;
import javax.swing.*;
import java.awt.*;

public class HospitalMainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private DataManager dataManager;

    public HospitalMainFrame() {
        this.dataManager = DataManager.getInstance();
        loadData();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Hospital Management System");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createMainContent();
    }

    private void createMainContent() {
        tabbedPane = new JTabbedPane();
        
        PatientManagePanel patientPanel = new PatientManagePanel();
        ClinicianManagePanel clinicianPanel = new ClinicianManagePanel();
        ReservationManagePanel reservationPanel = new ReservationManagePanel();
        PrescriptionViewPanel prescriptionPanel = new PrescriptionViewPanel();
        
        tabbedPane.addTab("Patients", patientPanel);
        tabbedPane.addTab("Clinicians", clinicianPanel);
        tabbedPane.addTab("Reservation", reservationPanel);
        tabbedPane.addTab("Prescriptions", prescriptionPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
    }

    private void loadData() {
        try {
            dataManager.loadAllData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading data: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HospitalMainFrame frame = new HospitalMainFrame();
            frame.setVisible(true);
        });
    }
}
