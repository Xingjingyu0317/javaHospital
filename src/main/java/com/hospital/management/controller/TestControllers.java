package com.hospital.management.controller;

import com.hospital.management.model.*;
import com.hospital.management.utils.DataManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * test controllers
 */
public class TestControllers {
    
    public static void main(String[] args) {
        try {
            System.out.println("=== data loading ===");
            DataManager.getInstance().loadAllData();

            PatientController pc = new PatientController();
            System.out.println("Total number of patients: " + pc.getAllPatients().size());
            
            String newPid = pc.generateNextId();
            Patient newPatient = new Patient();
            newPatient.setPatientId(newPid);
            newPatient.setFirstName("test");
            newPatient.setLastName("patient");
            newPatient.setDateOfBirth("1990-01-01");
            newPatient.setNhsNumber("9999999999");
            newPatient.setGender("M");
            newPatient.setPhoneNumber("07999999999");
            newPatient.setEmail("test@test.com");
            newPatient.setAddress("test address");
            newPatient.setPostcode("TE1 1ST");
            newPatient.setEmergencyContactName("emergency contact");
            newPatient.setEmergencyContactPhone("07888888888");
            newPatient.setRegistrationDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            newPatient.setGpSurgeryId("S001");
            
            boolean added = pc.addPatient(newPatient);
            System.out.println("New patients: " + added + " (ID: " + newPid + ")");
            System.out.println("The total number of new patients: " + pc.getAllPatients().size());

            ClinicianController cc = new ClinicianController();
            System.out.println("The total number of clinicians: " + cc.getAll().size());
            List<Clinician> gps = cc.findBySpeciality("General Practice");
            System.out.println("The number of general practitioners: " + gps.size());

            ReservationController ac = new ReservationController();
            System.out.println("Total number of reservations: " + ac.getAll().size());
            List<Appointment> scheduled = ac.getByStatus("Scheduled");
            System.out.println("An appointment has been arranged.: " + scheduled.size());

            PrescriptionController pxc = new PrescriptionController();
            System.out.println("Total number of prescriptions: " + pxc.getAllPrescriptions().size());
            
            if (pxc.getAllPrescriptions().size() > 0) {
                Prescription first = pxc.getAllPrescriptions().get(0);
                boolean exported = pxc.exportToFile(first.getPrescriptionId(), 
                    "test_prescription_output.txt");
                System.out.println("Prescription export: " + exported);
            }

            TransferController rc = new TransferController();
            System.out.println("Total number of referrals: " + rc.getTotalCount());
            
            Referral testRef = new Referral();
            testRef.setPatientId("P001");
            testRef.setReferringClinicianId("C001");
            testRef.setReferredToClinicianId("C005");
            testRef.setReferringFacilityId("S001");
            testRef.setReferredToFacilityId("H001");
            testRef.setReferralDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            testRef.setUrgencyLevel("convention");
            testRef.setReferralReason("Test referral");
            testRef.setClinicalSummary("This is the clinical summary for testing");
            testRef.setRequestedInvestigations("blood detection");
            testRef.setStatus("new");
            testRef.setNotes("Controller testing");
            testRef.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            testRef.setLastUpdated(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            
            boolean submitted = rc.submitReferral(testRef);
            System.out.println("Submit a referral: " + submitted + " (ID: " + testRef.getReferralId() + ")");
            System.out.println("Total number of new referrals: " + rc.getTotalCount());
            
            boolean emailSaved = rc.generateEmailAndSave(testRef.getReferralId(), 
                "test_referral_email.txt");
            System.out.println("Generate a referral email: " + emailSaved);
            
        } catch (Exception e) {
            System.err.println("error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
