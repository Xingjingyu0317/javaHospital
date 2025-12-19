package com.hospital.www.controller;

import com.hospital.www.model.Prescription;
import com.hospital.www.service.DataManager;
import com.hospital.www.utils.CSVWriter;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionController {
    private DataManager manager;

    public PrescriptionController() {
        this.manager = DataManager.getInstance();
    }

    public boolean createPrescription(Prescription rx) {
        if (rx == null || rx.getPrescriptionId() == null) {
            return false;
        }
        manager.addPrescription(rx);
        return true;
    }

    public boolean updatePrescription(Prescription rx) {
        if (rx == null || rx.getPrescriptionId() == null) {
            return false;
        }
        return manager.updatePrescription(rx);
    }

    public boolean deletePrescription(String rxId) {
        if (rxId == null || rxId.trim().isEmpty()) {
            return false;
        }
        return manager.deletePrescription(rxId);
    }

    public List<Prescription> getAllPrescriptions() {
        return manager.getPrescriptions();
    }

    public Prescription findById(String rxId) {
        return manager.getPrescriptionById(rxId);
    }

    public List<Prescription> findByPatient(String patientId) {
        List<Prescription> rxList = new ArrayList<>();
        if (patientId == null) return rxList;
        
        for (Prescription rx : manager.getPrescriptions()) {
            if (patientId.equals(rx.getPatientId())) {
                rxList.add(rx);
            }
        }
        return rxList;
    }

    public boolean exportToFile(String rxId, String outputPath) {
        Prescription rx = findById(rxId);
        if (rx == null) {
            return false;
        }
        
        try {
            String content = rx.generatePrescriptionContent();
            CSVWriter.writeTextFile(content, outputPath);
            return true;
        } catch (Exception e) {
            System.err.println("Error exporting prescription: " + e.getMessage());
            return false;
        }
    }

    public String getNewId() {
        List<Prescription> all = getAllPrescriptions();
        int highest = 0;
        
        for (Prescription rx : all) {
            String id = rx.getPrescriptionId();
            if (id != null && id.startsWith("RX")) {
                try {
                    int val = Integer.parseInt(id.substring(2));
                    if (val > highest) highest = val;
                } catch (NumberFormatException e) {
                }
            }
        }
        
        return String.format("RX%03d", highest + 1);
    }
}
