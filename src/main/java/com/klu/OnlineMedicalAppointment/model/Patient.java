package com.klu.OnlineMedicalAppointment.model;

import jakarta.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Lob
    private byte[] profileImage;

    public byte[] getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}

	
    @Size(max = 50, message = "First name should not exceed 50 characters")
    private String firstName;

    @Size(max = 50, message = "Last name should not exceed 50 characters")
    private String lastName;

    @Past(message = "Date of birth must be a past date")
    private LocalDate dateOfBirth;

    private String gender;

    @Pattern(regexp = "^\\d{10}$", message = "Contact number must be a valid 10-digit number")
    private String contactNumber;

    @Email(message = "Email should be valid")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Size(max = 100, message = "Address should not exceed 100 characters")
    private String address;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Appointment> appointments;



    public Patient() {}

    public Patient(String firstName, String lastName, LocalDate dateOfBirth, String gender, String contactNumber, String email, String password, String address,byte[] profileImage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.email = email;
        this.password = password;
        this.address = address;
        this.profileImage = profileImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void updateDetails(Patient updatedPatient) {
        if (updatedPatient.getFirstName() != null) {
            this.firstName = updatedPatient.getFirstName();
        }
        if (updatedPatient.getLastName() != null) {
            this.lastName = updatedPatient.getLastName();
        }
        if (updatedPatient.getDateOfBirth() != null) {
            this.dateOfBirth = updatedPatient.getDateOfBirth();
        }
        if (updatedPatient.getGender() != null) {
            this.gender = updatedPatient.getGender();
        }
        if (updatedPatient.getContactNumber() != null) {
            this.contactNumber = updatedPatient.getContactNumber();
        }
        if (updatedPatient.getAddress() != null) {
            this.address = updatedPatient.getAddress();
        }
        if (updatedPatient.getProfileImage() != null) {
            this.profileImage = updatedPatient.getProfileImage();
        }
    }
    
}
