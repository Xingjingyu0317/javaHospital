package com.hospital.management.utils;

import com.hospital.management.model.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Write to the file
 */
public class CSVWriter {

    public static void appendPatient(Patient patient, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,\"%s\",%s,%s,%s,%s,%s",
                patient.getPatientId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getDateOfBirth(),
                patient.getNhsNumber(),
                patient.getGender(),
                patient.getPhoneNumber(),
                patient.getEmail(),
                patient.getAddress(),
                patient.getPostcode(),
                patient.getEmergencyContactName(),
                patient.getEmergencyContactPhone(),
                patient.getRegistrationDate(),
                patient.getGpSurgeryId());
        writer.write(line);
        writer.newLine();
        writer.close();
    }

    public static void appendPrescription(Prescription prescription, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%d,%s,%s,%s,%s,%s,%s",
                prescription.getPrescriptionId(),
                prescription.getPatientId(),
                prescription.getClinicianId(),
                prescription.getAppointmentId() != null ? prescription.getAppointmentId() : "",
                prescription.getPrescriptionDate(),
                prescription.getMedicationName(),
                prescription.getDosage(),
                prescription.getFrequency(),
                prescription.getDurationDays(),
                prescription.getQuantity(),
                prescription.getInstructions(),
                prescription.getPharmacyName(),
                prescription.getStatus(),
                prescription.getIssueDate(),
                prescription.getCollectionDate() != null ? prescription.getCollectionDate() : "");
        writer.write(line);
        writer.newLine();
        writer.close();
    }

    public static void appendReferral(Referral referral, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,\"%s\",\"%s\",%s,%s,\"%s\",%s,%s",
                referral.getReferralId(),
                referral.getPatientId(),
                referral.getReferringClinicianId(),
                referral.getReferredToClinicianId(),
                referral.getReferringFacilityId(),
                referral.getReferredToFacilityId(),
                referral.getReferralDate(),
                referral.getUrgencyLevel(),
                referral.getReferralReason(),
                referral.getClinicalSummary(),
                referral.getRequestedInvestigations(),
                referral.getStatus(),
                referral.getAppointmentId() != null ? referral.getAppointmentId() : "",
                referral.getNotes() != null ? referral.getNotes() : "",
                referral.getCreatedDate(),
                referral.getLastUpdated());
        writer.write(line);
        writer.newLine();
        writer.close();
    }

    public static void writeTextFile(String content, String filePath) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(content);
        writer.close();
    }

    public static void appendAppointment(Appointment appointment, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        String line = String.format("%s,%s,%s,%s,%s,%s,%d,%s,%s,%s,\"%s\",%s,%s",
                appointment.getAppointmentId(),
                appointment.getPatientId(),
                appointment.getClinicianId(),
                appointment.getFacilityId(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime(),
                appointment.getDurationMinutes(),
                appointment.getAppointmentType(),
                appointment.getStatus(),
                appointment.getReasonForVisit(),
                appointment.getNotes(),
                appointment.getCreatedDate(),
                appointment.getLastModified());
        writer.write(line);
        writer.newLine();
        writer.close();
    }
}
