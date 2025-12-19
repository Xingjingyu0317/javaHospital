package com.hospital.www.utils;

import com.hospital.www.model.*;
import java.util.List;

/**
 * Data loader
 */
public class TestDataLoader {
    
    public static void main(String[] args) {
        try {
            String basePath = "csv/";
            
            System.out.println("=== Loading Patients ===");
            List<Patient> patients = CSVReader.loadPatients(basePath + "patients.csv");
            System.out.println("Loaded " + patients.size() + " patients");
            if (patients.size() > 0) {
                System.out.println("Sample: " + patients.get(0));
            }
            
            System.out.println("\n=== Loading Clinicians ===");
            List<Clinician> clinicians = CSVReader.loadClinicians(basePath + "clinicians.csv");
            System.out.println("Loaded " + clinicians.size() + " clinicians");
            if (clinicians.size() > 0) {
                System.out.println("Sample: " + clinicians.get(0));
            }
            
            System.out.println("\n=== Loading Facilities ===");
            List<Facility> facilities = CSVReader.loadFacilities(basePath + "facilities.csv");
            System.out.println("Loaded " + facilities.size() + " facilities");
            if (facilities.size() > 0) {
                System.out.println("Sample: " + facilities.get(0));
            }
            
            System.out.println("\n=== Loading Appointments ===");
            List<Appointment> appointments = CSVReader.loadAppointments(basePath + "appointments.csv");
            System.out.println("Loaded " + appointments.size() + " appointments");
            if (appointments.size() > 0) {
                System.out.println("Sample: " + appointments.get(0));
            }
            
            System.out.println("\n=== Loading Prescriptions ===");
            List<Prescription> prescriptions = CSVReader.loadPrescriptions(basePath + "prescriptions.csv");
            System.out.println("Loaded " + prescriptions.size() + " prescriptions");
            if (prescriptions.size() > 0) {
                System.out.println("Sample: " + prescriptions.get(0));
            }
            
            System.out.println("\n=== Loading Referrals ===");
            List<Referral> referrals = CSVReader.loadReferrals(basePath + "referrals.csv");
            System.out.println("Loaded " + referrals.size() + " referrals");
            if (referrals.size() > 0) {
                System.out.println("Sample: " + referrals.get(0));
            }
            
            System.out.println("\n=== Loading Staff ===");
            List<Staff> staffList = CSVReader.loadStaff(basePath + "staff.csv");
            System.out.println("Loaded " + staffList.size() + " staff members");
            if (staffList.size() > 0) {
                System.out.println("Sample: " + staffList.get(0));
            }
            
            System.out.println("\n=== All Data Loaded Successfully! ===");
            
        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
