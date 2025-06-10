package com.subhrashaw.ParivrajakBackend.service;

import com.subhrashaw.ParivrajakBackend.dao.OrgRepo;
import com.subhrashaw.ParivrajakBackend.model.Organizer;
import com.subhrashaw.ParivrajakBackend.model.OrganizerPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("MyOrganizationDetailedService")
public class MyOrganizationDetailedService implements UserDetailsService {
    @Autowired
    private OrgRepo orgRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Organizer organizer=orgRepo.findByEmail(email);
        if(organizer==null)
        {
            System.out.println("Organizer not found");
            throw new UsernameNotFoundException("404 not found");
        }
        return new OrganizerPrincipal(organizer);
    }
}
