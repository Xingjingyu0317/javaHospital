package com.hospital.www.utils;

import com.hospital.www.model.*;
import com.hospital.www.singleton.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * File Output
 */
public class TestSingletonAndFileOutput {
    
    public static void main(String[] args) {
        try {
            System.out.println("=== Testing Singleton Pattern ===");
            
            ReferralManager rm1 = ReferralManager.getInstance();
            ReferralManager rm2 = ReferralManager.getInstance();
            System.out.println("ReferralManager instances are same: " + (rm1 == rm2));
            
            EmailService es1 = EmailService.getInstance();
            EmailService es2 = EmailService.getInstance();
            System.out.println("EmailService instances are same: " + (es1 == es2));
            
            System.out.println("\n=== Testing Data Manager ===");
            DataManager dataManager = DataManager.getInstance();
            dataManager.loadAllData();
            System.out.println("Loaded patients: " + dataManager.getPatients().size());
            System.out.println("Loaded clinicians: " + dataManager.getClinicians().size());
            System.out.println("Loaded referrals: " + ReferralManager.getInstance().getReferralCount());
            
            System.out.println("\n=== Testing Referral Creation ===");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String today = sdf.format(new Date());
            
            Referral newReferral = new Referral();
            newReferral.setPatientId("P001");
            newReferral.setReferringClinicianId("C001");
            newReferral.setReferredToClinicianId("C005");
            newReferral.setReferringFacilityId("S001");
            newReferral.setReferredToFacilityId("H001");
            newReferral.setReferralDate(today);
            newReferral.setUrgencyLevel("Urgent");
            newReferral.setReferralReason("Test referral");
            newReferral.setClinicalSummary("This is a test referral for system verification");
            newReferral.setRequestedInvestigations("Blood test|X-ray");
            newReferral.setStatus("New");
            newReferral.setNotes("Testing singleton pattern");
            newReferral.setCreatedDate(today);
            newReferral.setLastUpdated(today);
            
            ReferralManager.getInstance().addReferral(newReferral);
            System.out.println("Created referral: " + newReferral.getReferralId());
            
            System.out.println("\n=== Testing Referral Content Generation ===");
            String referralContent = newReferral.generateReferralContent();
            System.out.println(referralContent);
            
            System.out.println("\n=== Testing File Output ===");
            CSVWriter.writeTextFile(referralContent, "output_referral_test.txt");
            System.out.println("Referral content saved to: output_referral_test.txt");
            
            System.out.println("\n=== Testing Email Generation ===");
            Patient patient = dataManager.getPatientById("P001");
            Clinician refClinician = dataManager.getClinicianById("C001");
            Clinician toClinician = dataManager.getClinicianById("C005");
            
            if (patient != null && refClinician != null && toClinician != null) {
                String emailContent = EmailService.getInstance().generateReferralEmail(
                    newReferral, patient, refClinician, toClinician);
                System.out.println(emailContent);
                
                EmailService.getInstance().sendEmail(emailContent, "output_referral_email_test.txt");
                System.out.println("Email saved to: output_referral_email_test.txt");
            }
            
            System.out.println("\n=== Testing Prescription Output ===");
            if (dataManager.getPrescriptions().size() > 0) {
                Prescription prescription = dataManager.getPrescriptions().get(0);
                String prescriptionContent = prescription.generatePrescriptionContent();
                System.out.println(prescriptionContent);
                
                CSVWriter.writeTextFile(prescriptionContent, "output_prescription_test.txt");
                System.out.println("Prescription saved to: output_prescription_test.txt");
            }
            
            System.out.println("\n=== Testing CSV Append ===");
            CSVWriter.appendReferral(newReferral, "output_new_referrals.csv");
            System.out.println("New referral appended to: output_new_referrals.csv");
            
            System.out.println("\n=== All Tests Completed Successfully! ===");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
