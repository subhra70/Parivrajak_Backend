package com.subhrashaw.ParivrajakBackend.service;

import com.subhrashaw.ParivrajakBackend.dao.OrgRepo;
import com.subhrashaw.ParivrajakBackend.model.Organizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrgService {
    @Autowired
    private OrgRepo orgRepo;

    public Organizer getOrganizer(String email) {
        return orgRepo.findByEmail(email);
    }
    public Organizer saveOrganizer(Organizer org) {
        return orgRepo.save(org);
    }

    public int getId(String email) {
        return orgRepo.findIdByEmail(email);
    }

    public Organizer getOrganizer(int orgId) {
        return orgRepo.findById(orgId).orElse(new Organizer(-1));
    }

    public void deleteAccount(String email) {
        orgRepo.deleteByEmail(email);
    }
}
