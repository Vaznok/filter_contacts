package com.vlasenko.vitalii.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vlasenko.vitalii.domain.Contact;

import java.util.List;

public class ContactsDto {
    @JsonProperty("contacts")
    private List<Contact> list;

    public ContactsDto(List<Contact> list) {
        this.list = list;
    }

    public List<Contact> getList() {
        return list;
    }
}
