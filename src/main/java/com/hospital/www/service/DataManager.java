package com.hospital.www.service;

import com.hospital.www.model.*;
import com.hospital.www.utils.CSVReader;
import com.hospital.www.utils.CSVWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Manager
 */
public class DataManager {
    private static DataManager instance;
    private List<Patient> patients;
    private List<Clinician> clinicians;
    private List<Facility> facilities;
    private List<Appointment> appointments;
    private List<Prescription> prescriptions;
    private List<Staff> staffList;
    private String basePath;

    private DataManager() {
        this.patients = new ArrayList<>();
        this.clinicians = new ArrayList<>();
        this.facilities = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
        this.staffList = new ArrayList<>();
        this.basePath = "csv/";
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public void loadAllData() throws IOException {
        patients = CSVReader.loadPatients(basePath + "patients.csv");
        clinicians = CSVReader.loadClinicians(basePath + "clinicians.csv");
        facilities = CSVReader.loadFacilities(basePath + "facilities.csv");
        appointments = CSVReader.loadAppointments(basePath + "appointments.csv");
        prescriptions = CSVReader.loadPrescriptions(basePath + "prescriptions.csv");
        staffList = CSVReader.loadStaff(basePath + "staff.csv");
        
        List<Referral> referrals = CSVReader.loadReferrals(basePath + "referrals.csv");
        ReferralManager.getInstance().loadExistingReferrals(referrals);
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public List<Clinician> getClinicians() {
        return clinicians;
    }

    public List<Facility> getFacilities() {
        return facilities;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
        savePatients();
    }

    public boolean updatePatient(Patient updatedPatient) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getPatientId().equals(updatedPatient.getPatientId())) {
                patients.set(i, updatedPatient);
                savePatients();
                return true;
            }
        }
        return false;
    }

    public boolean deletePatient(String patientId) {
        boolean removed = patients.removeIf(p -> p.getPatientId().equals(patientId));
        if (removed) savePatients();
        return removed;
    }

    public Patient getPatientById(String patientId) {
        return patients.stream()
                .filter(p -> p.getPatientId().equals(patientId))
                .findFirst()
                .orElse(null);
    }

    public void addClinician(Clinician clinician) {
        clinicians.add(clinician);
        saveClinicians();
    }

    public boolean updateClinician(Clinician updatedClinician) {
        for (int i = 0; i < clinicians.size(); i++) {
            if (clinicians.get(i).getClinicianId().equals(updatedClinician.getClinicianId())) {
                clinicians.set(i, updatedClinician);
                saveClinicians();
                return true;
            }
        }
        return false;
    }

    public boolean deleteClinician(String clinicianId) {
        boolean removed = clinicians.removeIf(c -> c.getClinicianId().equals(clinicianId));
        if (removed) saveClinicians();
        return removed;
    }

    public Clinician getClinicianById(String clinicianId) {
        return clinicians.stream()
                .filter(c -> c.getClinicianId().equals(clinicianId))
                .findFirst()
                .orElse(null);
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        saveAppointments();
    }

    public boolean updateAppointment(Appointment updatedAppointment) {
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getAppointmentId().equals(updatedAppointment.getAppointmentId())) {
                appointments.set(i, updatedAppointment);
                saveAppointments();
                return true;
            }
        }
        return false;
    }

    public boolean deleteAppointment(String appointmentId) {
        boolean removed = appointments.removeIf(a -> a.getAppointmentId().equals(appointmentId));
        if (removed) saveAppointments();
        return removed;
    }

    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
        savePrescriptions();
    }

    public boolean updatePrescription(Prescription updatedPrescription) {
        for (int i = 0; i < prescriptions.size(); i++) {
            if (prescriptions.get(i).getPrescriptionId().equals(updatedPrescription.getPrescriptionId())) {
                prescriptions.set(i, updatedPrescription);
                savePrescriptions();
                return true;
            }
        }
        return false;
    }

    public boolean deletePrescription(String prescriptionId) {
        boolean removed = prescriptions.removeIf(p -> p.getPrescriptionId().equals(prescriptionId));
        if (removed) savePrescriptions();
        return removed;
    }

    public Prescription getPrescriptionById(String prescriptionId) {
        return prescriptions.stream()
                .filter(p -> p.getPrescriptionId().equals(prescriptionId))
                .findFirst()
                .orElse(null);
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    private void savePatients() {
        try {
            CSVWriter.writeAllPatients(patients, basePath + "patients.csv");
        } catch (IOException e) {
            System.err.println("Error saving patients: " + e.getMessage());
        }
    }

    private void saveClinicians() {
        try {
            CSVWriter.writeAllClinicians(clinicians, basePath + "clinicians.csv");
        } catch (IOException e) {
            System.err.println("Error saving clinicians: " + e.getMessage());
        }
    }

    private void saveAppointments() {
        try {
            CSVWriter.writeAllAppointments(appointments, basePath + "appointments.csv");
        } catch (IOException e) {
            System.err.println("Error saving appointments: " + e.getMessage());
        }
    }

    private void savePrescriptions() {
        try {
            CSVWriter.writeAllPrescriptions(prescriptions, basePath + "prescriptions.csv");
        } catch (IOException e) {
            System.err.println("Error saving prescriptions: " + e.getMessage());
        }
    }
}
