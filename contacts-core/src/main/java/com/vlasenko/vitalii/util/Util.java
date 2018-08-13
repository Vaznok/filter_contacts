package com.vlasenko.vitalii.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vlasenko.vitalii.domain.Contact;
import com.vlasenko.vitalii.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
/*
 * todo: class was created in order to load big data to database. On the real prod should be deleted
 * */
@Service
public class Util {
    private ContactRepository contactRepository;

    @Autowired
    public Util(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void saveJsonNames () {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Contact> contacts = mapper.readValue(new File("contacts-core\\src\\main\\resources\\names.json"), new TypeReference<List<Contact>>(){});
            contactRepository.saveAll(contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
