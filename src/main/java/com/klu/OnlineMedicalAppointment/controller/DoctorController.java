package com.klu.OnlineMedicalAppointment.controller;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.klu.OnlineMedicalAppointment.model.*;
import com.klu.OnlineMedicalAppointment.security.JwtUtil;
import com.klu.OnlineMedicalAppointment.service.*;

@RestController
public class DoctorController {

    @Autowired private DoctorService doctorService;
    @Autowired private AppointmentService appointmentService;
    @Autowired private ReportService reportService;
    @Autowired private EPrescriptionService ePrescriptionService;
    @Autowired private PaymentService paymentService;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/doctorlogin")
    public ResponseEntity<?> doctorLogin(@RequestBody Doctor credentials) {
        String email = credentials.getEmail();
        String password = credentials.getPassword();

        Doctor doctor = doctorService.checkDoctorLogin(email, password);
        if (doctor == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");

        String token = jwtUtil.generateToken(email);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("doctor", doctor);

        return ResponseEntity.ok(map);
    }

    @GetMapping("/getDoctorDetails")
    public ResponseEntity<Doctor> getDoctorDetails() {
        String email = getEmail();
        if (email == null) return unauthorized();

        Doctor doctor = doctorService.findByEmail(email);
        if (doctor == null) return unauthorized();

        return ResponseEntity.ok(doctor);
    }

    @GetMapping("/getDoctorAppointments")
    public ResponseEntity<List<Appointment>> getDoctorAppointments() {
        String email = getEmail();
        if (email == null) return unauthorized();

        Doctor doctor = doctorService.findByEmail(email);
        if (doctor == null) return unauthorized();

        List<Appointment> appointments = appointmentService.getPatientAppointmentsByDoctor(doctor);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/getMyPayments")
    public ResponseEntity<List<Payment>> viewMyPayments() {
        String email = getEmail();
        if (email == null) return unauthorized();

        Doctor doctor = doctorService.findByEmail(email);
        if (doctor == null) return unauthorized();

        List<Appointment> appointments = appointmentService.getPatientAppointmentsByDoctor(doctor);
        return ResponseEntity.ok(paymentService.findByAppointmentIds(appointments));
    }

    @GetMapping("/viewMyFeedback")
    public ResponseEntity<List<Appointment>> viewMyFeedback() {
        String email = getEmail();
        if (email == null) return unauthorized();

        Doctor doctor = doctorService.findByEmail(email);
        if (doctor == null) return unauthorized();

        List<Appointment> appointments = appointmentService.getPatientAppointmentsByDoctor(doctor);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/Doctorprofile/{id}/image")
    public ResponseEntity<byte[]> getDoctorImage(@PathVariable Long id) {
        Doctor doctor = doctorService.getImage(id);
        return ResponseEntity.ok(doctor.getProfileImage());
    }

    @PutMapping(value = "/updateDoctorProfile", consumes = "multipart/form-data")
    public ResponseEntity<?> updateDoctorProfile(
            @RequestParam("name") String name,
            @RequestParam("specialization") String specialization,
            @RequestParam("contactNumber") String contactNumber,
            @RequestParam("email") String updatedEmail,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage
    ) {
        String email = getEmail();
        if (email == null) return unauthorized();

        Doctor doctor = doctorService.findByEmail(email);
        if (doctor == null) return unauthorized();

        try {
            doctor.setName(name);
            doctor.setSpecialization(specialization);
            doctor.setContactNumber(contactNumber);
            doctor.setEmail(updatedEmail);

            if (profileImage != null && !profileImage.isEmpty()) {
                doctor.setProfileImage(profileImage.getBytes());
            }

            doctorService.saveDoctor(doctor);

            return ResponseEntity.ok("Doctor profile updated successfully.");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update doctor profile.");
        }
    }

    @GetMapping("/getAllDoctorsList")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.fetchAllDoctors());
    }
    
    @PutMapping("/updateReport/{appointmentId}")
    public ResponseEntity<Appointment> updateReportCompleted(@PathVariable Long appointmentId) {
        try 
        {
            Appointment updatedAppointment = appointmentService.updateReportCompleted(appointmentId);
            return ResponseEntity.ok(updatedAppointment);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/doctorlogout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logged out successfully.");
    }


    @PutMapping("/OnlineConferance")
    public ResponseEntity<String> updateConsultationUrl(
            @RequestParam String url,
            @RequestParam Long appointmentId
    ) {
        Optional<Appointment> opt = appointmentService.findAppointment(appointmentId);
        if (opt.isEmpty()) return ResponseEntity.status(404).body("Appointment not found.");

        Appointment appt = opt.get();
        appt.setAppointmentUrl(url);
        appt.setStatus("VIRTUAL APP ✔");
        appt.setIsCompleted(true);

        appointmentService.makeAppointment(appt);

        return ResponseEntity.ok("URL updated successfully.");
    }

    
    @GetMapping("/viewPatientMedicalReport/{patientId}/{appointmentId}")
    public ResponseEntity<byte[]> viewPatientMedicalReport(@PathVariable Long patientId, @PathVariable Long appointmentId) 
	{
    	
    	String email = getEmail();
        if (email == null) return unauthorized();

        Doctor doctor = doctorService.findByEmail(email);
        if (doctor == null) return unauthorized();
        
        try 
        {
            Report report = reportService.getReportByAppointmentIdAndPatientId(appointmentId, patientId);

            if (report == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Optional<Appointment> appointmentOpt = appointmentService.findAppointment(appointmentId);
            Appointment appointment = appointmentOpt.orElse(null);
            if (appointment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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
            Paragraph doctorName = new Paragraph("Doctor: " + report.getDoctor().getName() , patientInfoFont);
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
            if (!prescriptions.isEmpty()) {
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

            PdfPTable table = new PdfPTable(2); 
            table.setWidthPercentage(100); 

            Font tableHeaderFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
            PdfPCell header1 = new PdfPCell(new Phrase("Key", tableHeaderFont));
            header1.setBackgroundColor(BaseColor.BLUE);
            PdfPCell header2 = new PdfPCell(new Phrase("Value", tableHeaderFont));
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
            headers.add("Content-Disposition", "inline; filename=MedicalReport_" + patientId + "_" + appointmentId + ".pdf");

            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    
    
    private String getEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    
	private ResponseEntity unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
