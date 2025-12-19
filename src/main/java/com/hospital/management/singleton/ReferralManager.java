package com.hospital.management.singleton;

import com.hospital.management.model.Referral;
import java.util.*;

/**
 * Referral Manager
 */
public class ReferralManager {
    private static ReferralManager instance;
    private List<Referral> referralQueue;
    private int referralCounter;

    private ReferralManager() {
        this.referralQueue = new ArrayList<>();
        this.referralCounter = 1000;
    }

    public static synchronized ReferralManager getInstance() {
        if (instance == null) {
            instance = new ReferralManager();
        }
        return instance;
    }

    public void addReferral(Referral referral) {
        if (referral.getReferralId() == null || referral.getReferralId().isEmpty()) {
            referral.setReferralId("R" + String.format("%04d", ++referralCounter));
        }
        referralQueue.add(referral);
    }

    public boolean removeReferral(String referralId) {
        return referralQueue.removeIf(r -> r.getReferralId().equals(referralId));
    }

    public Referral getReferralById(String referralId) {
        return referralQueue.stream()
                .filter(r -> r.getReferralId().equals(referralId))
                .findFirst()
                .orElse(null);
    }

    public List<Referral> getAllReferrals() {
        return new ArrayList<>(referralQueue);
    }

    public boolean updateReferralStatus(String referralId, String newStatus) {
        Referral referral = getReferralById(referralId);
        if (referral != null) {
            referral.setStatus(newStatus);
            referral.setLastUpdated(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            return true;
        }
        return false;
    }

    public void loadExistingReferrals(List<Referral> referrals) {
        this.referralQueue.addAll(referrals);
        for (Referral r : referrals) {
            if (r.getReferralId() != null && r.getReferralId().startsWith("R")) {
                try {
                    int num = Integer.parseInt(r.getReferralId().substring(1));
                    if (num > referralCounter) {
                        referralCounter = num;
                    }
                } catch (NumberFormatException e) {
                }
            }
        }
    }

    public int getReferralCount() {
        return referralQueue.size();
    }
}
