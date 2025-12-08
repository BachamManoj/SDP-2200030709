package com.klu.OnlineMedicalAppointment.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.klu.OnlineMedicalAppointment.model.Appointment;
import com.klu.OnlineMedicalAppointment.model.Doctor;
import com.klu.OnlineMedicalAppointment.model.EPrescription;
import com.klu.OnlineMedicalAppointment.model.OrderMedicines;
import com.klu.OnlineMedicalAppointment.model.Patient;
import com.klu.OnlineMedicalAppointment.model.Payment;
import com.klu.OnlineMedicalAppointment.model.Report;
import com.klu.OnlineMedicalAppointment.security.JwtUtil;
import com.klu.OnlineMedicalAppointment.service.AppointmentService;
import com.klu.OnlineMedicalAppointment.service.DoctorService;
import com.klu.OnlineMedicalAppointment.service.EPrescriptionService;
import com.klu.OnlineMedicalAppointment.service.OrderMedicinesService;
import com.klu.OnlineMedicalAppointment.service.PatientService;
import com.klu.OnlineMedicalAppointment.service.PaymentService;
import com.klu.OnlineMedicalAppointment.service.ReportService;

@RestController
public class PatientController {
	
	@Autowired
	private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;
    
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PaymentService paymentService;
    
    @Autowired 
    private ReportService reportService;
    
    @Autowired
    private EPrescriptionService ePrescriptionService;
    
    @Autowired
    private OrderMedicinesService orderMedicinesService;

    @Autowired
    private JwtUtil jwtUtil;
   
    
    @PostMapping(value = "/patientRegistration", consumes = {"multipart/form-data"})
    public ResponseEntity<String> registerPatient(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("dateOfBirth") LocalDate dateOfBirth,
            @RequestParam("gender") String gender,
            @RequestParam("contactNumber") String contactNumber,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("address") String address,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {
    	
    	if (patientService.checkByemailorContact(email,contactNumber)) {
            return ResponseEntity.badRequest().body("Email or Contact already exists.");
        }
        byte[] profileImageBytes = null;
        if (profileImage != null && !profileImage.isEmpty()) {
            try {
                profileImageBytes = profileImage.getBytes();
            } catch (IOException e) {
                return ResponseEntity.badRequest().body("Failed to process image");
            }
        }

        Patient patient = new Patient();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setDateOfBirth(dateOfBirth);
        patient.setGender(gender);
        patient.setContactNumber(contactNumber);
        patient.setEmail(email);
        patient.setPassword(password);
        patient.setAddress(address);
        patient.setProfileImage(profileImageBytes); 
        
        patientService.patientRegestration(patient);

        return ResponseEntity.ok("Patient registered successfully.");
    }

    @GetMapping("/checkPatientExists")
    public boolean checkBYemailorContact(@RequestParam String email, @RequestParam String contactNumber) {
        return patientService.checkByemailorContact(email, contactNumber);
    }

    @GetMapping("/patientLoginSession")
    public ResponseEntity<Patient> checkSession() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Patient patient = patientService.findByEmail(email);
        if (patient == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(patient);
    }

    
    @PostMapping("/patientLogin")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> login) {
        String email = login.get("email");
        String password = login.get("password");

        Patient patient = patientService.checkPatientLogin(email, password);

        if (patient == null) {
            return ResponseEntity.status(401).body(null);
        }

        String token = jwtUtil.generateToken(email);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("patient", patient);

        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/getPatientDetails")
    public ResponseEntity<Patient> getPatientDetails() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Patient patient = patientService.findByEmail(email);

        if (patient == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(patient);
    }


    @PutMapping(value = "/updatePatientProfile", consumes = "multipart/form-data")
	public ResponseEntity<String> updatePatientProfile(
	        @RequestParam("firstName") String firstName,
	        @RequestParam("lastName") String lastName,
	        @RequestParam("dateOfBirth") String dateOfBirth,
	        @RequestParam("gender") String gender,
	        @RequestParam("contactNumber") String contactNumber,
	        @RequestParam("email") String updatedEmail,
	        @RequestParam("address") String address,
	        @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {
	
	    String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
	
	    if (loggedInEmail == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
	    }
	
	    Patient patient = patientService.findByEmail(loggedInEmail);
	
	    if (patient == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
	    }
	
	    try {
	        patient.setFirstName(firstName);
	        patient.setLastName(lastName);
	        patient.setDateOfBirth(LocalDate.parse(dateOfBirth));
	        patient.setGender(gender);
	        patient.setContactNumber(contactNumber);
	        patient.setEmail(updatedEmail);
	        patient.setAddress(address);
	
	        if (profileImage != null && !profileImage.isEmpty()) {
	            patient.setProfileImage(profileImage.getBytes());
	        }
	
	        patientService.updatePatientProfile(patient.getId(), patient);
	
	        return ResponseEntity.ok("Profile updated successfully.");
	    } catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload profile image.");
	    }
	}

    
    @GetMapping("/profile/{id}/image")
    public ResponseEntity<byte[]> getPatientImage(@PathVariable Long id)
    {
    	Patient patient = patientService.getImage(id);
    	byte[] image=patient.getProfileImage();  	
		return ResponseEntity.ok().body(image);
    }
    
    @GetMapping("/getappointments/{id}")
    public ResponseEntity<List<Appointment>> getPatientAppointment(@PathVariable Long id) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (email == null || email.equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Patient loggedInPatient = patientService.findByEmail(email);
        if (loggedInPatient == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        if (!loggedInPatient.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        List<Appointment> appointments = appointmentService.getPatientAppointments(loggedInPatient);
        return ResponseEntity.ok(appointments);
    }

    
    @GetMapping("/getOrdersbyPatient")
    public ResponseEntity<List<OrderMedicines>> getAllOrders() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (email == null || email.equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Patient patient = patientService.findByEmail(email);
        if (patient == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<Appointment> appointments = appointmentService.getPatientAppointments(patient);

        List<OrderMedicines> orderMedicines = new ArrayList<>();
        for (Appointment appointment : appointments) {
            OrderMedicines order = orderMedicinesService.findOrderMedicinesByAppointment(appointment.getId());
            if (order != null) {
                orderMedicines.add(order);
            }
        }

        return ResponseEntity.ok(orderMedicines);
    }


    
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logged out successfully.");
    }

    
    @PostMapping("/getbyspecialty")
    public ResponseEntity<List<Doctor>> findBySpecialization(@RequestBody String specialization) {
        System.out.println("Received specialization: " + specialization); 
        List<Doctor> doctors = doctorService.getBySpecialization(specialization);
        System.out.println(doctors);
        if (doctors.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.ok(doctors); 
    }

    
    @PostMapping("/makeAppointment")
    public ResponseEntity<String> makeDoctorAppointment(@RequestBody Appointment appointment) {
		Appointment app = appointmentService.makeAppointment(appointment);
		if(app!=null)
		{
			return ResponseEntity.ok("appointment done!!");	
		}
		return ResponseEntity.ok("sorry no appointment done!!");	
		
	}
    
    @GetMapping("/getPatientDataById/{id}")
    public ResponseEntity<Patient> getPatientData(@PathVariable Long id)
    {
    	Patient patient=patientService.getPatinetData(id);
    	if(patient!=null)
    	{
    		return ResponseEntity.ok(patient);
    	}
		return null;
    	
    }
    
    @GetMapping("/getPatientBillings")
    public ResponseEntity<List<Payment>> getPatientBillings() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (email == null || email.equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Patient patient = patientService.findByEmail(email);
        if (patient == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<Appointment> appointments = appointmentService.getPatientAppointments(patient);

        List<Long> appointmentIds = appointments.stream()
                .map(Appointment::getId)
                .collect(Collectors.toList());

        List<Payment> payments = paymentService.getAppointmentDues(appointmentIds);

        List<Payment> filteredPayments = payments.stream()
                .filter(p -> p.getType() == 0)
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredPayments);
    }

    
    
    @GetMapping("/getPatientBillingsEprescription")
    public ResponseEntity<List<Payment>> getEprescriptionBilling() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (email == null) {
            return ResponseEntity.status(401).build();
        }

        Patient patient = patientService.findByEmail(email);
        if (patient == null) {
            return ResponseEntity.status(401).build();
        }

        List<Appointment> appointments = appointmentService.getPatientAppointments(patient);
        List<Long> appointmentIds = appointments.stream()
                .map(Appointment::getId)
                .collect(Collectors.toList());

        if (appointmentIds.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        List<Payment> ePrescriptionPayments =
                paymentService.getEprescriptionPaymentsByAppointmentIds(appointmentIds);

        return ResponseEntity.ok(ePrescriptionPayments);
    }

    
    
    
    @GetMapping("/getDoctorFreeSlot/{id}/{date}")
    public ResponseEntity<List<LocalTime>> getDoctorFreeSlot(@PathVariable Long id,@PathVariable LocalDate date) {
        try {
            
            Doctor doctor = doctorService.finbById(id);
            if (doctor == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.emptyList()); 
            }
            List<Appointment> appointments = appointmentService.getDoctorAppointmentsByDate(doctor, date);
            List<LocalTime> bookedSlots = appointments.stream().map(Appointment::getTimeSlot).collect(Collectors.toList());
            return ResponseEntity.ok(bookedSlots);
        } 
        catch (Exception e) 
        {
          
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }
    
    
	@GetMapping("/getAlreadybookedDates/{id}")
    public ResponseEntity<List<LocalDate>>getAlreadybookedDates(@PathVariable Long id)
    {
    	Patient patient=patientService.getImage(id);
    	List<Appointment> appointments=appointmentService.getPatientAppointments(patient);
    	List<LocalDate> bookedDates = new ArrayList<>();
    	for(Appointment appointment:appointments)
    	{
    		bookedDates.add(appointment.getDate());
    	}
    	
		return ResponseEntity.ok(bookedDates);
    	
    }
	
	
	@PutMapping("/ratebyPatient/{appointmentId}")
    public ResponseEntity<String> updateRating(@PathVariable Long appointmentId,@RequestBody Appointment ratingRequest) 
	{
        try 
        {
            if (ratingRequest.getRating() < 1 || ratingRequest.getRating() > 5) 
            {
                return ResponseEntity.status(400).body("Rating must be between 1 and 5");
            }

            appointmentService.updateRatingIfCompleted(appointmentId,ratingRequest.getRating(),ratingRequest.getRatingDescription());

            return ResponseEntity.ok("Rating updated successfully");
        }
        catch (IllegalArgumentException e) 
        {
            return ResponseEntity.status(400).body("Appointment not completed, unable to update rating");
        }
        catch (Exception e) 
        {
            return ResponseEntity.status(500).body("Failed to update rating");
        }
    }
	
	
	@DeleteMapping("/cancleApointment/{appointmentId}")
	public ResponseEntity<String> cancelAppointmentbyPatient(@PathVariable Long appointmentId)
	{
		Payment payment=paymentService.findByAppointmentId(appointmentId);
		if(payment != null)
			paymentService.deleteAppointment(payment.getId());
		appointmentService.deleteAppointment(appointmentId);
		return ResponseEntity.ok("Appointment cancled succesfully");	
	}
	
	 @PostMapping("/create")
	 public ResponseEntity<String> createOrder(@RequestBody OrderMedicines order) 
	 {
	  	order.setOrderDate(LocalDateTime.now());
	    orderMedicinesService.createOrder(order);
	    return ResponseEntity.ok("Order created successfully!");
	 }
	   
	 @PostMapping("/confirmOrder/{id}")
	 public ResponseEntity<String> confirmOrder(@PathVariable Long id)
	 {
	  	orderMedicinesService.confirmPayment(id);
	 	return ResponseEntity.ok("Order confirmed successfully!");	
	 }	
	
	
	@GetMapping("/viewPatientMedicalReportbyPatient/{appointmentId}/{doctorId}")
	public ResponseEntity<byte[]> viewPatientMedicalReport(@PathVariable Long appointmentId,@PathVariable Long doctorId)
	{
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (email == null) {
            return ResponseEntity.status(401).build();
        }

        Patient patient = patientService.findByEmail(email);
       
	    if (patient == null)
	    {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);  
	    }

	    try 
	    {
	        Optional<Appointment> appointmentOpt = appointmentService.findAppointment(appointmentId);
	        if (!appointmentOpt.isPresent())
	        {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  
	        }
	        Appointment appointment = appointmentOpt.get();

	        if (!appointment.getDoctor().getId().equals(doctorId)) 
	        {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);  
	        }

	        if (!appointment.getPatient().getId().equals(patient.getId())) 
	        {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);  
	        }

	        Report report = reportService.getReportByAppointmentIdAndPatientId(appointmentId, patient.getId());
	        if (report == null)
	        {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Report not found
	        }

	        List<EPrescription> prescriptions = ePrescriptionService.getEPrescriptionsByAppointment(appointment);

	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        Document document = new Document();
	        PdfWriter.getInstance(document, byteArrayOutputStream);
	        document.open();

	        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
	        Paragraph title = new Paragraph("Medical Report", titleFont);
	        title.setAlignment(Element.ALIGN_CENTER);
	        document.add(title);

	        document.add(new Paragraph("\n"));

	        Font patientInfoFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
	        Paragraph patientName = new Paragraph("Patient: " + report.getPatient().getFirstName() + " " + report.getPatient().getLastName(), patientInfoFont);
	        Paragraph doctorName = new Paragraph("Doctor: " + report.getDoctor().getName(), patientInfoFont);
	        Paragraph appointmentDate = new Paragraph("Appointment Date: " + report.getAppointment().getDate(), patientInfoFont);
	        document.add(patientName);
	        document.add(doctorName);
	        document.add(appointmentDate);

	        document.add(new Paragraph("\n"));

	        Font descriptionFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.DARK_GRAY);
	        Paragraph description = new Paragraph("Report Description:", descriptionFont);
	        description.setSpacingBefore(10);
	        description.setSpacingAfter(10);
	        document.add(description);

	        Paragraph reportDescription = new Paragraph(report.getDescription(), descriptionFont);
	        reportDescription.setIndentationLeft(20); 
	        document.add(reportDescription);

	        if (!prescriptions.isEmpty())
	        {
	            document.add(new Paragraph("\nEPrescriptions:", descriptionFont));
	            PdfPTable prescriptionTable = new PdfPTable(3);
	            prescriptionTable.setWidthPercentage(100); 

	            Font tableHeaderFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
	            PdfPCell header1 = new PdfPCell(new Phrase("Medicine Name", tableHeaderFont));
	            header1.setBackgroundColor(BaseColor.BLUE);
	            PdfPCell header2 = new PdfPCell(new Phrase("Description", tableHeaderFont));
	            header2.setBackgroundColor(BaseColor.BLUE);
	            PdfPCell header3 = new PdfPCell(new Phrase("Quantity", tableHeaderFont));
	            header3.setBackgroundColor(BaseColor.BLUE);
	            prescriptionTable.addCell(header1);
	            prescriptionTable.addCell(header2);
	            prescriptionTable.addCell(header3);

	            for (EPrescription prescription : prescriptions) {
	                prescriptionTable.addCell(prescription.getMedicineName());
	                prescriptionTable.addCell(prescription.getDescription());
	                prescriptionTable.addCell(String.valueOf(prescription.getQuantity()));
	            }

	            document.add(prescriptionTable);
	        }

	        PdfPTable table = new PdfPTable(2); // Two columns
	        table.setWidthPercentage(100); // Table width in percentage

	        PdfPCell header1 = new PdfPCell(new Phrase("Key"));
	        header1.setBackgroundColor(BaseColor.BLUE);
	        PdfPCell header2 = new PdfPCell(new Phrase("Value"));
	        header2.setBackgroundColor(BaseColor.BLUE);
	        table.addCell(header1);
	        table.addCell(header2);

	        
	        table.addCell("Patient ID");
	        table.addCell(report.getPatient().getId().toString());
	        table.addCell("Appointment ID");
	        table.addCell(report.getAppointment().getId().toString());

	        document.add(table);

	        document.close();

	        byte[] pdfContent = byteArrayOutputStream.toByteArray();

	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "application/pdf");
	        headers.add("Content-Disposition", "inline; filename=MedicalReport_" + patient.getId() + "_" + appointmentId + ".pdf");

	        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);

	    }
	    catch (Exception e) 
	    {
	        e.printStackTrace(); 
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  
	    }
	}


	
}
