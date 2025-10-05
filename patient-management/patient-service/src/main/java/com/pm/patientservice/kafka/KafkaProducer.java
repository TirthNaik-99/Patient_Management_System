package com.pm.patientservice.kafka;

import com.pm.patientservice.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service //Spring will manage this class for us and inject all the dependencies that it needs
//this class is responsible for sending events to a given kafka topic
public class KafkaProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    //this is essentially how we define a message types and we use kafka template to send this messages
    // By this we specifying that our messages that we send to kafka topic from this kafka producer are in Key value pairs
    //this is telling kafka we are going to send a kafka event as key of type string and value of byte[]
    //So everytime we produce and send msg we are going to convert msg to byte array and also gonna add a key this value and this will be sending to with the KafkaTemolate
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient){
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName().toString())
                .setEmail(patient.getEmail().toString())
                .setEventType("PATIENT CREATED")
                .build();
        try{
            kafkaTemplate.send("patient", event.toByteArray());
        } catch (Exception e){
            log.error("Error sending PatientCreated even: {}", event);
        }
    }
}
