package com.example.travelweb.controller;

import com.example.travelweb.dto.request.ContactRequest;
import com.example.travelweb.dto.response.ContactResponse;
import com.example.travelweb.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/create")
    public ResponseEntity<ContactResponse> createContact(
            @RequestBody ContactRequest request){
        ContactResponse response = contactService.createContact(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
