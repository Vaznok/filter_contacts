package com.vlasenko.vitalii.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vlasenko.vitalii.dto.ContactsDto;
import com.vlasenko.vitalii.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    private ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public @ResponseBody ResponseEntity<Resource> filterALL(@RequestParam("nameFilter") String filter) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ContactsDto contacts = new ContactsDto(contactService.findAllByRegexp(filter));
        byte[] buf = mapper.writeValueAsBytes(contacts);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .header("Content-Disposition", "attachment; filename=contacts.json")
                .body(new InputStreamResource(new ByteArrayInputStream(buf)));
    }
}
