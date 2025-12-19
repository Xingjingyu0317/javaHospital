package com.hospital.www.utils;

import com.hospital.www.model.*;
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
    }

    public static void writeAllPatients(java.util.List<Patient> patients, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write("patient_id,first_name,last_name,date_of_birth,nhs_number,gender,phone_number,email,address,postcode,emergency_contact_name,emergency_contact_phone,registration_date,gp_surgery_id");
        writer.newLine();
        for (Patient p : patients) {
            String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,\"%s\",%s,%s,%s,%s,%s",
                p.getPatientId(), p.getFirstName(), p.getLastName(), p.getDateOfBirth(),
                p.getNhsNumber(), p.getGender(), p.getPhoneNumber(), p.getEmail(),
                p.getAddress(), p.getPostcode(), p.getEmergencyContactName(),
                p.getEmergencyContactPhone(), p.getRegistrationDate(), p.getGpSurgeryId());
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

    public static void writeAllClinicians(java.util.List<Clinician> clinicians, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write("clinician_id,first_name,last_name,title,speciality,gmc_number,phone_number,email,workplace_id,workplace_type,employment_status,start_date");
        writer.newLine();
        for (Clinician c : clinicians) {
            String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                c.getClinicianId(), c.getFirstName(), c.getLastName(), c.getTitle(),
                c.getSpeciality(), c.getGmcNumber(), c.getPhoneNumber(), c.getEmail(),
                c.getWorkplaceId(), c.getWorkplaceType(), c.getEmploymentStatus(), c.getStartDate());
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

    public static void writeAllAppointments(java.util.List<Appointment> appointments, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write("appointment_id,patient_id,clinician_id,facility_id,appointment_date,appointment_time,duration_minutes,appointment_type,status,reason_for_visit,notes,created_date,last_modified");
        writer.newLine();
        for (Appointment a : appointments) {
            String line = String.format("%s,%s,%s,%s,%s,%s,%d,%s,%s,%s,\"%s\",%s,%s",
                a.getAppointmentId(), a.getPatientId(), a.getClinicianId(), a.getFacilityId(),
                a.getAppointmentDate(), a.getAppointmentTime(), a.getDurationMinutes(),
                a.getAppointmentType(), a.getStatus(), a.getReasonForVisit(),
                a.getNotes(), a.getCreatedDate(), a.getLastModified());
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

    public static void writeAllPrescriptions(java.util.List<Prescription> prescriptions, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write("prescription_id,patient_id,clinician_id,appointment_id,prescription_date,medication_name,dosage,frequency,duration_days,quantity,instructions,pharmacy_name,status,issue_date,collection_date");
        writer.newLine();
        for (Prescription p : prescriptions) {
            String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%d,%s,%s,%s,%s,%s,%s",
                p.getPrescriptionId(), p.getPatientId(), p.getClinicianId(),
                p.getAppointmentId() != null ? p.getAppointmentId() : "",
                p.getPrescriptionDate(), p.getMedicationName(), p.getDosage(),
                p.getFrequency(), p.getDurationDays(), p.getQuantity(),
                p.getInstructions(), p.getPharmacyName(), p.getStatus(),
                p.getIssueDate(), p.getCollectionDate() != null ? p.getCollectionDate() : "");
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

    public static void writeAllReferrals(java.util.List<Referral> referrals, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write("referral_id,patient_id,referring_clinician_id,referred_to_clinician_id,referring_facility_id,referred_to_facility_id,referral_date,urgency_level,referral_reason,clinical_summary,requested_investigations,status,appointment_id,notes,created_date,last_updated");
        writer.newLine();
        for (Referral r : referrals) {
            String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,\"%s\",\"%s\",%s,%s,\"%s\",%s,%s",
                r.getReferralId(), r.getPatientId(), r.getReferringClinicianId(),
                r.getReferredToClinicianId(), r.getReferringFacilityId(),
                r.getReferredToFacilityId(), r.getReferralDate(), r.getUrgencyLevel(),
                r.getReferralReason(), r.getClinicalSummary(),
                r.getRequestedInvestigations(), r.getStatus(),
                r.getAppointmentId() != null ? r.getAppointmentId() : "",
                r.getNotes() != null ? r.getNotes() : "",
                r.getCreatedDate(), r.getLastUpdated());
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }
}
