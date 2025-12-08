package com.klu.OnlineMedicalAppointment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.klu.OnlineMedicalAppointment.model.Doctor;
import com.klu.OnlineMedicalAppointment.model.Patient;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>{
	@Query("SELECT D FROM Doctor D WHERE D.email=?1 AND D.password=?2")
	Doctor checkDoctorLogin(String email, String password);
	List<Doctor> findBySpecialization(String specialization);
	Optional<Doctor> findById(Long id);
	@Query("SELECT COUNT(p) FROM Doctor p")
	long getTotalDoctorCount();
	Optional<Doctor> findByEmail(String email);
}
