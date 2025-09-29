package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import com.pm.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController             //It tells spring that the Patient Controller is Rest Controller
@RequestMapping("/patients")   //Patient Controller is going to handle all requests that start with /patients eg. https://localhost:4000/patients, so all the requests that look like this is handled by this controller
@Tag(name = "Patient", description = "API for managing Patients")
public class PatientController {
    private final PatientService patientService;

    //Dependency injection
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping         //Tells spring that method that we are about to create is to handle any get requests
    @Operation(summary = "Get Patients")    //we need to do this to appear on Swagger UI under patients tag as we have already annotated with GetMapping, it will display on the UI
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {   //Spring object that creates a HTTP response without us having to do much of the code ourselves and lets us set properties on the response quite easily
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);    //ok() means will be returning the status code 200 and returns the whole list of patients to the body
    }

    @PostMapping
    @Operation(summary = "Create a new Patient")
    public ResponseEntity<PatientResponseDTO> createPatient(
            @Validated({Default.class, CreatePatientValidationGroup.class})
            @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(
                patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    //localhost:4000/patients/12312312-123123123-12312312
    //12312312-123123123-12312312 - takes this part and convert it to ID variable and pass variable to update patient which handles the request
    @PutMapping("/{id}")
    @Operation(summary = "Update a Patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id,
            @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}

