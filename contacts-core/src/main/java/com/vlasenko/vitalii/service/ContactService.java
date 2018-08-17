package com.vlasenko.vitalii.service;

import com.vlasenko.vitalii.domain.Contact;

import java.util.List;

public interface ContactService {

    List<Contact> findAllByRegexp(String regexp);

}
