package com.hospital.management.controller;

import com.hospital.management.model.Appointment;
import com.hospital.management.utils.DataManager;
import java.util.ArrayList;
import java.util.List;

public class ReservationController {
    private DataManager dataMgr;

    public ReservationController() {
        this.dataMgr = DataManager.getInstance();
    }

    public boolean add(Appointment apt) {
        if (apt == null || apt.getAppointmentId() == null) {
            return false;
        }
        dataMgr.addAppointment(apt);
        return true;
    }

    public boolean modify(Appointment apt) {
        if (apt == null || apt.getAppointmentId() == null) {
            return false;
        }
        return dataMgr.updateAppointment(apt);
    }

    public boolean remove(String aptId) {
        if (aptId == null || aptId.trim().isEmpty()) {
            return false;
        }
        return dataMgr.deleteAppointment(aptId);
    }

    public List<Appointment> getAll() {
        return dataMgr.getAppointments();
    }

    public List<Appointment> getByPatient(String patientId) {
        List<Appointment> result = new ArrayList<>();
        if (patientId == null) return result;
        
        for (Appointment a : dataMgr.getAppointments()) {
            if (patientId.equals(a.getPatientId())) {
                result.add(a);
            }
        }
        return result;
    }

    public List<Appointment> getByClinician(String clinicianId) {
        List<Appointment> result = new ArrayList<>();
        if (clinicianId == null) return result;
        
        for (Appointment a : dataMgr.getAppointments()) {
            if (clinicianId.equals(a.getClinicianId())) {
                result.add(a);
            }
        }
        return result;
    }

    public List<Appointment> getByStatus(String status) {
        List<Appointment> result = new ArrayList<>();
        if (status == null) return result;
        
        for (Appointment a : dataMgr.getAppointments()) {
            if (status.equalsIgnoreCase(a.getStatus())) {
                result.add(a);
            }
        }
        return result;
    }

    public String generateId() {
        List<Appointment> apts = getAll();
        int maxId = 0;
        
        for (Appointment a : apts) {
            String id = a.getAppointmentId();
            if (id != null && id.startsWith("A")) {
                try {
                    int num = Integer.parseInt(id.substring(1));
                    if (num > maxId) maxId = num;
                } catch (NumberFormatException e) {
                }
            }
        }
        
        return String.format("A%03d", maxId + 1);
    }
}
