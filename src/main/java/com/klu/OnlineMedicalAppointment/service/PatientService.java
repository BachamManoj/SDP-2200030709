package com.klu.OnlineMedicalAppointment.service;

import java.util.List;

import com.klu.OnlineMedicalAppointment.model.Patient;

public interface PatientService {
	public String patientRegestration(Patient patient);
	public boolean checkByemailorContact(String email,String contactNumber);
	public Patient checkPatientLogin(String email,String password);
	public void updatePatientProfile(Long id, Patient updatedPatient);
	public Patient getImage(Long id);
	public Patient getPatinetData(Long id);
	public Long getTotalPatientCount();
	public List<Patient> getAllPatients();
	public void deletePatient(Long Id);
	public Patient findByEmail(String email);
}
