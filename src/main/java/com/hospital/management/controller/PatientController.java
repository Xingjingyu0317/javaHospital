package com.hospital.management.controller;

import com.hospital.management.model.Patient;
import com.hospital.management.utils.DataManager;
import java.util.ArrayList;
import java.util.List;

public class PatientController {
    private DataManager dataManager;

    public PatientController() {
        this.dataManager = DataManager.getInstance();
    }

    public boolean addPatient(Patient p) {
        if (p == null || p.getPatientId() == null) {
            return false;
        }
        
        Patient existing = dataManager.getPatientById(p.getPatientId());
        if (existing != null) {
            return false;
        }
        
        dataManager.addPatient(p);
        return true;
    }

    public boolean modifyPatient(Patient p) {
        if (p == null || p.getPatientId() == null) {
            return false;
        }
        return dataManager.updatePatient(p);
    }

    public boolean removePatient(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        return dataManager.deletePatient(id);
    }

    public Patient findPatientById(String id) {
        if (id == null) {
            return null;
        }
        return dataManager.getPatientById(id);
    }

    public List<Patient> getAllPatients() {
        return dataManager.getPatients();
    }

    public List<Patient> searchPatients(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllPatients();
        }
        
        List<Patient> results = new ArrayList<>();
        String kw = keyword.toLowerCase();
        
        for (Patient p : dataManager.getPatients()) {
            if (p.getPatientId().toLowerCase().contains(kw) ||
                p.getFirstName().toLowerCase().contains(kw) ||
                p.getLastName().toLowerCase().contains(kw) ||
                p.getNhsNumber().contains(kw) ||
                (p.getEmail() != null && p.getEmail().toLowerCase().contains(kw))) {
                results.add(p);
            }
        }
        return results;
    }

    public String generateNextId() {
        List<Patient> patients = getAllPatients();
        int maxNum = 0;
        
        for (Patient p : patients) {
            String id = p.getPatientId();
            if (id != null && id.startsWith("P")) {
                try {
                    int num = Integer.parseInt(id.substring(1));
                    if (num > maxNum) {
                        maxNum = num;
                    }
                } catch (NumberFormatException e) {
                }
            }
        }
        
        return String.format("P%03d", maxNum + 1);
    }
}
