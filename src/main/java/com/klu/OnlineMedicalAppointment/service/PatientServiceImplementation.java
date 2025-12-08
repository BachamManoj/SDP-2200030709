package com.klu.OnlineMedicalAppointment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.OnlineMedicalAppointment.model.Patient;
import com.klu.OnlineMedicalAppointment.repository.PatientRepository;

@Service
public class PatientServiceImplementation implements PatientService {

	@Autowired
	private PatientRepository patientRepository;
	
	@Override
	public String patientRegestration(Patient patient) {
		patientRepository.save(patient);
		return "Patient Regesterd sucessfully login to book appointment!!!...";
	}

	@Override
	public boolean checkByemailorContact(String email, String contactNumber) {
		
		return patientRepository.findByEmail(email).isPresent() || patientRepository.findByContactNumber(contactNumber).isPresent();
	}

	@Override
	public Patient checkPatientLogin(String email, String password) {
		
		return patientRepository.checkPatientLogin(email, password);
	}
	
	public void updatePatientProfile(Long id, Patient updatedPatient) {
        Patient existingPatient = patientRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Patient not found"));       
        existingPatient.updateDetails(updatedPatient);
        patientRepository.save(existingPatient);
    }

	@SuppressWarnings("deprecation")
	@Override
	public Patient getImage(Long id) {
		return patientRepository.getById(id);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Patient getPatinetData(Long id) {
		return patientRepository.getById(id);
	}

	@Override
	public Long getTotalPatientCount() {
		return patientRepository.getTotalPatientCount();
	}

	@Override
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}

	@Override
	public void deletePatient(Long Id) {
		patientRepository.deleteById(Id);
		
	}

	@Override
	public Patient findByEmail(String email) {
		// TODO Auto-generated method stub
		
		Optional<Patient> patientObj = patientRepository.findByEmail(email);

	    return patientObj.orElse(null);
	}

	
}
