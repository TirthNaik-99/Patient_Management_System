package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController             //It tells spring that the Patient Controller is Rest Controller
@RequestMapping("/patients")   //Patient Controller is going to handle all requests that start with /patients eg. https://localhost:4000/patients, so all the requests that look like this is handled by this controller
public class PatientController {
    private final PatientService patientService;

    //Dependency injection
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping         //Tells spring that method that we are about to create is to handle any get requests
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {   //Spring object that creates a HTTP response without us having to do much of the code ourselves and lets us set properties on the response quite easily
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);    //ok() means will be returning the status code 200 and returns the whole list of patients to the body
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(
            @Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(
                patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }
}
