package com.klu.OnlineMedicalAppointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.klu.OnlineMedicalAppointment.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>{
	@Query("Select a from Admin a where a.email=?1 AND a.password=?2")
	Admin checkAdminLogin(String email, String password);
}
