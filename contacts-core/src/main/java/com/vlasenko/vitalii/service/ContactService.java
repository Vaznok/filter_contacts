package com.vlasenko.vitalii.service;

import com.vlasenko.vitalii.domain.Contact;

import java.util.List;

public interface ContactService {

    List<Contact> findAllByRegexp(String regexp);

    /*
     * todo: method was created in order to load big data to database. On the real prod should be deleted
     * */
    List<Contact> saveAll(List<Contact> contacts);
}
