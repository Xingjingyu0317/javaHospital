package com.hospital.management.utils;

import com.hospital.management.model.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV file reading tool class
 */
public class CSVReader {

    public static List<Patient> loadPatients(String filePath) throws IOException {
        List<Patient> patients = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = br.readLine();
        
        while ((line = br.readLine()) != null) {
            String[] fields = parseLine(line);
            if (fields.length >= 14) {
                Patient patient = new Patient(
                    fields[0], fields[1], fields[2], fields[3], fields[4], fields[5],
                    fields[6], fields[7], fields[8], fields[9], fields[10], fields[11],
                    fields[12], fields[13]
                );
                patients.add(patient);
            }
        }
        br.close();
        return patients;
    }

    public static List<Clinician> loadClinicians(String filePath) throws IOException {
        List<Clinician> clinicians = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = br.readLine();
        
        while ((line = br.readLine()) != null) {
            String[] fields = parseLine(line);
            if (fields.length >= 12) {
                Clinician clinician = new Clinician(
                    fields[0], fields[1], fields[2], fields[3], fields[4], fields[5],
                    fields[6], fields[7], fields[8], fields[9], fields[10], fields[11]
                );
                clinicians.add(clinician);
            }
        }
        br.close();
        return clinicians;
    }

    public static List<Facility> loadFacilities(String filePath) throws IOException {
        List<Facility> facilities = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = br.readLine();
        
        while ((line = br.readLine()) != null) {
            String[] fields = parseLine(line);
            if (fields.length >= 11) {
                Facility facility = new Facility(
                    fields[0], fields[1], fields[2], fields[3], fields[4], fields[5],
                    fields[6], fields[7], fields[8], Integer.parseInt(fields[9]), fields[10]
                );
                facilities.add(facility);
            }
        }
        br.close();
        return facilities;
    }

    public static List<Appointment> loadAppointments(String filePath) throws IOException {
        List<Appointment> appointments = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = br.readLine();
        
        while ((line = br.readLine()) != null) {
            String[] fields = parseLine(line);
            if (fields.length >= 13) {
                Appointment appointment = new Appointment(
                    fields[0], fields[1], fields[2], fields[3], fields[4], fields[5],
                    Integer.parseInt(fields[6]), fields[7], fields[8], fields[9], 
                    fields[10], fields[11], fields[12]
                );
                appointments.add(appointment);
            }
        }
        br.close();
        return appointments;
    }

    public static List<Prescription> loadPrescriptions(String filePath) throws IOException {
        List<Prescription> prescriptions = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = br.readLine();
        
        while ((line = br.readLine()) != null) {
            String[] fields = parseLine(line);
            if (fields.length >= 15) {
                Prescription prescription = new Prescription(
                    fields[0], fields[1], fields[2], fields[3], fields[4], fields[5],
                    fields[6], fields[7], Integer.parseInt(fields[8]), fields[9], 
                    fields[10], fields[11], fields[12], fields[13], fields[14]
                );
                prescriptions.add(prescription);
            }
        }
        br.close();
        return prescriptions;
    }

    public static List<Referral> loadReferrals(String filePath) throws IOException {
        List<Referral> referrals = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = br.readLine();
        
        while ((line = br.readLine()) != null) {
            String[] fields = parseLine(line);
            if (fields.length >= 16) {
                Referral referral = new Referral(
                    fields[0], fields[1], fields[2], fields[3], fields[4], fields[5],
                    fields[6], fields[7], fields[8], fields[9], fields[10], fields[11],
                    fields[12], fields[13], fields[14], fields[15]
                );
                referrals.add(referral);
            }
        }
        br.close();
        return referrals;
    }

    public static List<Staff> loadStaff(String filePath) throws IOException {
        List<Staff> staffList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = br.readLine();
        
        while ((line = br.readLine()) != null) {
            String[] fields = parseLine(line);
            if (fields.length >= 12) {
                Staff staff = new Staff(
                    fields[0], fields[1], fields[2], fields[3], fields[4], fields[5],
                    fields[6], fields[7], fields[8], fields[9], fields[10], fields[11]
                );
                staffList.add(staff);
            }
        }
        br.close();
        return staffList;
    }

    public static String[] parseLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder field = new StringBuilder();
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(field.toString());
                field = new StringBuilder();
            } else {
                field.append(c);
            }
        }
        result.add(field.toString());
        
        return result.toArray(new String[0]);
    }
}
