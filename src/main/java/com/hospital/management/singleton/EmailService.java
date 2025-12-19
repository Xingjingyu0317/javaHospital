package com.hospital.management.singleton;

import com.hospital.management.model.*;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Email Service
 */
public class EmailService {
    private static EmailService instance;
    private SimpleDateFormat dateFormat;

    private EmailService() {
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static synchronized EmailService getInstance() {
        if (instance == null) {
            instance = new EmailService();
        }
        return instance;
    }

    public String generateReferralEmail(Referral referral, Patient patient, 
                                       Clinician referringClinician, 
                                       Clinician referredClinician) {
        StringBuilder email = new StringBuilder();
        email.append("================================\n");
        email.append("NHS REFERRAL EMAIL\n");
        email.append("================================\n\n");
        email.append("Date: ").append(dateFormat.format(new Date())).append("\n\n");
        
        email.append("To: ").append(referredClinician.getEmail()).append("\n");
        email.append("From: ").append(referringClinician.getEmail()).append("\n");
        email.append("Subject: New Patient Referral - ").append(referral.getUrgencyLevel()).append("\n\n");
        
        email.append("Dear ").append(referredClinician.getTitle()).append(" ")
             .append(referredClinician.getLastName()).append(",\n\n");
        
        email.append("I am writing to refer the following patient for your specialist assessment:\n\n");
        
        email.append("PATIENT DETAILS:\n");
        email.append("Name: ").append(patient.getFirstName()).append(" ")
             .append(patient.getLastName()).append("\n");
        email.append("NHS Number: ").append(patient.getNhsNumber()).append("\n");
        email.append("Date of Birth: ").append(patient.getDateOfBirth()).append("\n");
        email.append("Contact: ").append(patient.getPhoneNumber()).append("\n\n");
        
        email.append("REFERRAL INFORMATION:\n");
        email.append("Referral ID: ").append(referral.getReferralId()).append("\n");
        email.append("Urgency: ").append(referral.getUrgencyLevel()).append("\n");
        email.append("Reason: ").append(referral.getReferralReason()).append("\n\n");
        
        email.append("CLINICAL SUMMARY:\n");
        email.append(referral.getClinicalSummary()).append("\n\n");
        
        email.append("INVESTIGATIONS REQUESTED:\n");
        email.append(referral.getRequestedInvestigations()).append("\n\n");
        
        if (referral.getNotes() != null && !referral.getNotes().isEmpty()) {
            email.append("ADDITIONAL NOTES:\n");
            email.append(referral.getNotes()).append("\n\n");
        }
        
        email.append("Thank you for your assistance.\n\n");
        email.append("Best regards,\n");
        email.append(referringClinician.getTitle()).append(" ")
             .append(referringClinician.getFirstName()).append(" ")
             .append(referringClinician.getLastName()).append("\n");
        email.append(referringClinician.getSpeciality()).append("\n");
        email.append("================================\n");
        
        return email.toString();
    }

    public boolean sendEmail(String emailContent, String outputPath) {
        try {
            FileWriter writer = new FileWriter(outputPath);
            writer.write(emailContent);
            writer.close();
            return true;
        } catch (IOException e) {
            System.err.println("Error writing email to file: " + e.getMessage());
            return false;
        }
    }

    public String generatePrescriptionNotification(Prescription prescription, 
                                                  Patient patient, 
                                                  Clinician clinician) {
        StringBuilder email = new StringBuilder();
        email.append("================================\n");
        email.append("PRESCRIPTION NOTIFICATION\n");
        email.append("================================\n\n");
        email.append("Date: ").append(dateFormat.format(new Date())).append("\n\n");
        
        email.append("Dear ").append(patient.getFirstName()).append(" ")
             .append(patient.getLastName()).append(",\n\n");
        
        email.append("Your prescription has been issued:\n\n");
        email.append("Prescription ID: ").append(prescription.getPrescriptionId()).append("\n");
        email.append("Medication: ").append(prescription.getMedicationName()).append("\n");
        email.append("Dosage: ").append(prescription.getDosage()).append("\n");
        email.append("Instructions: ").append(prescription.getInstructions()).append("\n\n");
        
        email.append("Please collect from: ").append(prescription.getPharmacyName()).append("\n\n");
        
        email.append("Prescribed by:\n");
        email.append(clinician.getTitle()).append(" ")
             .append(clinician.getFirstName()).append(" ")
             .append(clinician.getLastName()).append("\n");
        email.append("================================\n");
        
        return email.toString();
    }
}
