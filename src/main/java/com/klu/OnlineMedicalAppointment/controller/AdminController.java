package com.klu.OnlineMedicalAppointment.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.DeleteExchange;

import com.klu.OnlineMedicalAppointment.model.Admin;
import com.klu.OnlineMedicalAppointment.model.Doctor;
import com.klu.OnlineMedicalAppointment.model.Patient;
import com.klu.OnlineMedicalAppointment.model.Pharmacist;
import com.klu.OnlineMedicalAppointment.service.AdminService;
import com.klu.OnlineMedicalAppointment.service.AppointmentService;
import com.klu.OnlineMedicalAppointment.service.DoctorService;
import com.klu.OnlineMedicalAppointment.service.PatientService;
import com.klu.OnlineMedicalAppointment.service.PharmacistService;

import jakarta.servlet.http.HttpSession;

@RestController
public class AdminController {
	
	@Autowired 
	private AdminService adminService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private PharmacistService pharmacistService;
	
	@PostMapping("/adminLogin")
    public ResponseEntity<?> login(@RequestBody Map<String, String> admin, HttpSession session) {
    	
    	
		String email = admin.get("email");
        String password = admin.get("password");

        Admin admin2 = adminService.checkLogin(email, password);

        if (admin2 == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        if (!admin2.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        session.setAttribute("admin", admin2);
        session.setAttribute("adminEmail", admin2.getEmail());

        return ResponseEntity.ok(admin2);
    }
	
	
	@GetMapping("/getTotalPatients")
	public Long noofPatients()
	{
		return patientService.getTotalPatientCount();
	}
	
	@GetMapping("/getTotalDoctor")
	public Long noofDoctor()
	{
		return doctorService.getTotalDoctorCount();
	}
	
	@GetMapping("/getTotalAppointment")
	public Long noofAppointment()
	{
		return appointmentService.getTotalAppointmentCount();
	}
	
	
	@GetMapping("/managePatient")
	public ResponseEntity<List<Patient>> getAllPatients()
	{
		return ResponseEntity.ok(patientService.getAllPatients());
	}
	
	@DeleteMapping("/managePatient/{id}")
	public void getPatientById(@PathVariable Long id)
	{
		patientService.deletePatient(id);
	}
	
	
	@GetMapping("/manageDoctors")
	public ResponseEntity<List<Doctor>> getAllDoctors()
	{
		return ResponseEntity.ok(doctorService.fetchAllDoctors());
	}
	
	@PostMapping("/addDoctor")
	public ResponseEntity<String> addDoctor(@RequestPart("doctor") Doctor doctor,
	                                        @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
	    try {
	        if (profileImage != null && !profileImage.isEmpty()) {
	            doctor.setProfileImage(profileImage.getBytes());
	        }
	        doctorService.saveDoctor(doctor);
	        return ResponseEntity.ok("Doctor added successfully.");
	    } catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save doctor: " + e.getMessage());
	    }
	}

	
	@DeleteExchange("/manageDoctor/{id}")
	public void deleteDoctorById(@PathVariable Long id)
	{
		doctorService.deleteDoctor(id);
	}
	
	@GetMapping("/managePharmacist")
	public ResponseEntity<List<Pharmacist>> getAllPharmacist()
	{
		return ResponseEntity.ok(pharmacistService.getAllPharmacist());
	}
	
	@PostMapping("/addPharmacist")
	public void addPharmacist(@RequestBody Pharmacist pharmacist)
	{
		pharmacistService.savePharmacist(pharmacist);
	}
	
	@DeleteMapping("/managePharmacist/{id}")
	public void deletePharmacist(@PathVariable Long id)
	{
		pharmacistService.deletPharmasist(id);
	}
}
