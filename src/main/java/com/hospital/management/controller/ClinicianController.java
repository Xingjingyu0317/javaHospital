package com.hospital.management.controller;

import com.hospital.management.model.Clinician;
import com.hospital.management.utils.DataManager;
import java.util.ArrayList;
import java.util.List;

public class ClinicianController {
    private DataManager dm;

    public ClinicianController() {
        this.dm = DataManager.getInstance();
    }

    public boolean create(Clinician c) {
        if (c == null || c.getClinicianId() == null) {
            return false;
        }
        
        Clinician exist = dm.getClinicianById(c.getClinicianId());
        if (exist != null) {
            return false;
        }
        
        dm.addClinician(c);
        return true;
    }

    public boolean update(Clinician c) {
        if (c == null || c.getClinicianId() == null) {
            return false;
        }
        return dm.updateClinician(c);
    }

    public boolean delete(String clinicianId) {
        if (clinicianId == null || clinicianId.trim().isEmpty()) {
            return false;
        }
        return dm.deleteClinician(clinicianId);
    }

    public Clinician getById(String clinicianId) {
        return dm.getClinicianById(clinicianId);
    }

    public List<Clinician> getAll() {
        return dm.getClinicians();
    }

    public List<Clinician> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAll();
        }
        
        List<Clinician> found = new ArrayList<>();
        String q = query.toLowerCase();
        
        for (Clinician c : dm.getClinicians()) {
            if (c.getClinicianId().toLowerCase().contains(q) ||
                c.getFirstName().toLowerCase().contains(q) ||
                c.getLastName().toLowerCase().contains(q) ||
                (c.getSpeciality() != null && c.getSpeciality().toLowerCase().contains(q)) ||
                (c.getTitle() != null && c.getTitle().toLowerCase().contains(q))) {
                found.add(c);
            }
        }
        return found;
    }

    public List<Clinician> findBySpeciality(String speciality) {
        List<Clinician> result = new ArrayList<>();
        if (speciality == null) {
            return result;
        }
        
        for (Clinician c : dm.getClinicians()) {
            if (speciality.equalsIgnoreCase(c.getSpeciality())) {
                result.add(c);
            }
        }
        return result;
    }

    public String getNextId() {
        List<Clinician> list = getAll();
        int max = 0;
        
        for (Clinician c : list) {
            String id = c.getClinicianId();
            if (id != null && id.startsWith("C")) {
                try {
                    int n = Integer.parseInt(id.substring(1));
                    if (n > max) max = n;
                } catch (NumberFormatException e) {
                }
            }
        }
        
        return String.format("C%03d", max + 1);
    }
}
