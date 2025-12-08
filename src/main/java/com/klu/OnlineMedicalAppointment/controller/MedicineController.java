package com.klu.OnlineMedicalAppointment.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.klu.OnlineMedicalAppointment.model.Medicine;
import com.klu.OnlineMedicalAppointment.service.MedicineService;

@RestController
public class MedicineController {
	@Autowired
    private MedicineService medicineService;
	
	
    @PostMapping("/addMedicine")
    public ResponseEntity<Medicine> createOrUpdateMedicine(@RequestBody Medicine medicine) {
        Medicine savedMedicine = medicineService.saveMedicine(medicine);
        return new ResponseEntity<>(savedMedicine, HttpStatus.CREATED);
    }


    @GetMapping("getMedicine/{id}")
    public ResponseEntity<Medicine> getMedicineById(@PathVariable Long id) {
        Optional<Medicine> medicine = medicineService.getMedicineById(id);
        return medicine.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("deleteMedicine/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        Optional<Medicine> medicine = medicineService.getMedicineById(id);
        if (medicine.isPresent()) {
            medicineService.deleteMedicine(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/getAllMedicine")
    public ResponseEntity<List<Medicine>> getAllmedicinesData()
    {
    	return ResponseEntity.ok(medicineService.getAllMedicines());
    }
    
    @PutMapping("/updateMedicine/{id}")
    public ResponseEntity<Medicine> updateMedicine(@PathVariable Long id, @RequestParam("name") String name,
                                                   @RequestParam("quantity") int quantity,
                                                   @RequestParam("price") double price,
                                                   @RequestParam("description") String description,
                                                   @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        Optional<Medicine> existingMedicine = medicineService.getMedicineById(id);
        if (existingMedicine.isPresent()) {
            Medicine medicine = existingMedicine.get();
            medicine.setName(name);
            medicine.setQuantity(quantity);
            medicine.setPrice(price);
            medicine.setDescription(description);
            if (image != null && !image.isEmpty()) {
               medicine.setImage(image.getBytes());
            }
            medicineService.saveMedicine(medicine);
            return ResponseEntity.ok(medicine);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
}
