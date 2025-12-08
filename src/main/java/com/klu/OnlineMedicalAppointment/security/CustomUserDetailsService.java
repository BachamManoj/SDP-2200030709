package com.klu.OnlineMedicalAppointment.security;

import com.klu.OnlineMedicalAppointment.model.Patient;
import com.klu.OnlineMedicalAppointment.service.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PatientService patientService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Patient patient = patientService.findByEmail(email);

        if (patient == null) {
            throw new UsernameNotFoundException("Patient not found");
        }

        return new User(
                patient.getEmail(),
                patient.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_PATIENT"))
        );
    }
}
