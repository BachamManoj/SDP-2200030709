package com.klu.OnlineMedicalAppointment.service;

import java.util.List;

import com.klu.OnlineMedicalAppointment.model.Doctor;

public interface DoctorService {
	public Doctor checkDoctorLogin(String email,String password);
	public List<Doctor> getBySpecialization(String specialization);
	public Doctor finbById(Long id);
	public void updateDoctorProfile(Long id, Doctor updatedDoctor);
	public Doctor findDoctorById(Long id);
	public void saveDoctor(Doctor doctor);
	public Doctor getImage(Long id);
	public List<Doctor> fetchAllDoctors();
	public Long getTotalDoctorCount();
	public void deleteDoctor(Long id);
	public Doctor findByEmail(String email);
}
