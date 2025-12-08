package com.klu.OnlineMedicalAppointment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.klu.OnlineMedicalAppointment.model.Doctor;
import com.klu.OnlineMedicalAppointment.repository.DoctorRepository;

@Service
public class DoctorServiceImplementation implements DoctorService{

	@Autowired
	DoctorRepository doctorRepository;
	
	@Override
	public Doctor checkDoctorLogin(String email, String password) {
		return doctorRepository.checkDoctorLogin(email, password);
	}

	@Override
	public List<Doctor> getBySpecialization(String specialization) {
		
		return doctorRepository.findBySpecialization(specialization);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Doctor finbById(Long id) {
		return doctorRepository.getById(id);
	}

	@Override
	public void updateDoctorProfile(Long id, Doctor updatedDoctor) {
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        existingDoctor.setName(updatedDoctor.getName());
        existingDoctor.setSpecialization(updatedDoctor.getSpecialization());
        existingDoctor.setContactNumber(updatedDoctor.getContactNumber());
        existingDoctor.setFee(updatedDoctor.getFee());

        if (updatedDoctor.getProfileImage() != null) {
            existingDoctor.setProfileImage(updatedDoctor.getProfileImage());
        }

        doctorRepository.save(existingDoctor);
    }
	
	 public Doctor findDoctorById(Long id) 
	 {
		 return doctorRepository.findById(id).orElse(null);
	 }

	 public void saveDoctor(Doctor doctor) 
	 {
	     doctorRepository.save(doctor);
	 }

	@SuppressWarnings("deprecation")
	@Override
	public Doctor getImage(Long id) {
		return doctorRepository.getById(id);
	}

	@Override
	public List<Doctor> fetchAllDoctors() {
		
		return doctorRepository.findAll();
	}

	@Override
	public Long getTotalDoctorCount() {
		
		return doctorRepository.getTotalDoctorCount();
	}

	@Override
	public void deleteDoctor(Long id) {
		doctorRepository.deleteById(id);
		
	}

	@Override
	public Doctor findByEmail(String email) {
		Optional<Doctor> doctor = doctorRepository.findByEmail(email);
		
		return doctor.orElse(null);
	}	

}
