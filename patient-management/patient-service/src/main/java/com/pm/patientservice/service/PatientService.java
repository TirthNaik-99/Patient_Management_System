package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients (){
        List<Patient> patients = patientRepository.findAll();

        //like for each loop that takes every patient data from current Patient list to the function to the method and then
        //return the result to patientResponseDTOs where toList() is used
        //Basically used when Patient object is transformed to ResponseDTOs where DTOs are string and will be seen by the client
        //(patient -> PatientMapper.toDTO(patient)) Lambda function for this is PatientMapper::toDTO

        // List<PatientResponseDTO> patientResponseDTOs =
        //                patients.stream().map(PatientMapper::toDTO).toList();
        return patients.stream().map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        Patient newPatient = patientRepository.save(
                PatientMapper.toModel(patientRequestDTO));

        return PatientMapper.toDTO(newPatient);
    }
}
