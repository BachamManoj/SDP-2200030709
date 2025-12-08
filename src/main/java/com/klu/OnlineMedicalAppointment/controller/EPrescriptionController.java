package com.klu.OnlineMedicalAppointment.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.klu.OnlineMedicalAppointment.model.Appointment;
import com.klu.OnlineMedicalAppointment.model.EPrescription;
import com.klu.OnlineMedicalAppointment.model.Patient;
import com.klu.OnlineMedicalAppointment.model.Payment;
import com.klu.OnlineMedicalAppointment.service.AppointmentService;
import com.klu.OnlineMedicalAppointment.service.EPrescriptionService;
import com.klu.OnlineMedicalAppointment.service.PatientService;
import com.klu.OnlineMedicalAppointment.service.PaymentService;

@RestController
public class EPrescriptionController {

    @Autowired
    private EPrescriptionService ePrescriptionService;

    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
    private PatientService patientService;
    
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/provideEPrescription")
    public ResponseEntity<EPrescription> addEPrescription(@RequestBody EPrescription ePrescription) {
        EPrescription savedEPrescription = ePrescriptionService.saveEPrescription(ePrescription);
        return ResponseEntity.ok(savedEPrescription);
    }

    @GetMapping("/getEPrescriptionsByAppointment/{appointmentId}")
    public ResponseEntity<List<EPrescription>> getEPrescriptionsByAppointment(@PathVariable Long appointmentId) {
        Optional<Appointment> appointment = appointmentService.findAppointment(appointmentId);
        Appointment appointment1 = appointment.get();
        List<EPrescription> prescriptions = ePrescriptionService.getEPrescriptionsByAppointment(appointment1);
        if (prescriptions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(prescriptions);
    }

    @PutMapping("changeEPrescriptions/{id}")
    public ResponseEntity<EPrescription> updateEPrescription(
            @PathVariable Long id,
            @RequestBody EPrescription updatedEPrescription) {
        try {
            EPrescription updated = ePrescriptionService.updateEPrescription(id, updatedEPrescription);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @DeleteMapping("/deleteEPrescription/{id}")
    public ResponseEntity<Void> deleteEPrescription(@PathVariable Long id) {
        try {
            ePrescriptionService.deleteEPrescription(id);
            return ResponseEntity.noContent().build(); 
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); 
        }
    }
    
    @GetMapping("/viewMyEPrescriptionByPatient/{id}")
    public ResponseEntity<List<EPrescription>> viewMyEPrescriptionByPatient(@PathVariable Long id)
    {
    	Patient patient=patientService.getPatinetData(id);
    	List<EPrescription> ePrescriptions=ePrescriptionService.getEPrescriptionsByPatient(patient);
    	
		return ResponseEntity.ok(ePrescriptions);
    	
    }
    
    @GetMapping("/acceptandgetEPrescriptionPrice/{appointmentId}")
    public Double getEPrescriptionsAmmount(@PathVariable Long appointmentId) {
        Optional<Appointment> appointment = appointmentService.findAppointment(appointmentId);
        Appointment appointment1 = appointment.get();
        List<EPrescription> prescriptions = ePrescriptionService.getEPrescriptionsByAppointment(appointment1);
        Double ammount=0.0;
        for(EPrescription prescription:prescriptions)
        {
        	prescription.setAccept(true);
        	ammount+=prescription.getQuantity()*prescription.getMedicine().getPrice();
        	ePrescriptionService.saveEPrescription(prescription);
        }
        Payment payment=new Payment();
        payment.setAmount(ammount);
        payment.setAppointment(appointment1);
        payment.setType(1);
        paymentService.createPayment(payment);
        return ammount;
    }
    
    
    @GetMapping("/viewAcceptedEPrescription")
    public ResponseEntity<List<Payment>> viewAcceptedEPrescription()
    {
    	List<Long> appointment=ePrescriptionService.getAcceptedEPrescription();	
    	List<Payment> payments=paymentService.getEprescriptionPaymentsByAppointmentIds(appointment);
    	
		return ResponseEntity.ok(payments);
    }
    
    
}
