package com.hospital.www.controller;

import com.hospital.www.model.Referral;
import com.hospital.www.model.Patient;
import com.hospital.www.model.Clinician;
import com.hospital.www.service.ReferralManager;
import com.hospital.www.service.EmailService;
import com.hospital.www.service.DataManager;
import com.hospital.www.utils.CSVWriter;
import java.util.List;

public class TransferController {
    private ReferralManager refMgr;
    private EmailService emailSvc;
    private DataManager data;

    public TransferController() {
        this.refMgr = ReferralManager.getInstance();
        this.emailSvc = EmailService.getInstance();
        this.data = DataManager.getInstance();
    }

    public boolean submitReferral(Referral ref) {
        if (ref == null) {
            return false;
        }
        
        refMgr.addReferral(ref);
        return true;
    }

    public boolean updateStatus(String refId, String newStatus) {
        return refMgr.updateReferralStatus(refId, newStatus);
    }

    public boolean removeReferral(String refId) {
        if (refId == null || refId.trim().isEmpty()) {
            return false;
        }
        return refMgr.removeReferral(refId);
    }

    public Referral getById(String refId) {
        return refMgr.getReferralById(refId);
    }

    public List<Referral> getAllReferrals() {
        return refMgr.getAllReferrals();
    }

    public boolean exportToFile(String refId, String filePath) {
        Referral ref = getById(refId);
        if (ref == null) {
            return false;
        }
        
        try {
            String content = ref.generateReferralContent();
            CSVWriter.writeTextFile(content, filePath);
            return true;
        } catch (Exception e) {
            System.err.println("Export failed: " + e.getMessage());
            return false;
        }
    }

    public boolean generateEmailAndSave(String refId, String emailPath) {
        Referral ref = getById(refId);
        if (ref == null) {
            return false;
        }
        
        Patient patient = data.getPatientById(ref.getPatientId());
        Clinician fromDoc = data.getClinicianById(ref.getReferringClinicianId());
        Clinician toDoc = data.getClinicianById(ref.getReferredToClinicianId());
        
        if (patient == null || fromDoc == null || toDoc == null) {
            return false;
        }
        
        String emailContent = emailSvc.generateReferralEmail(ref, patient, fromDoc, toDoc);
        return emailSvc.sendEmail(emailContent, emailPath);
    }

    public int getTotalCount() {
        return refMgr.getReferralCount();
    }
}
