package com.vlasenko.vitalii.service;

import com.vlasenko.vitalii.domain.Contact;
import com.vlasenko.vitalii.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {
    private final static Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class.getSimpleName());

    //in case of config issue default QUERY_LIMIT value have been installed
    @Value("${custom.dataSelectLimit}")
    private int QUERY_LIMIT = 100000;
    //in case of config issue default MAX_REGEXP_LENGTH value have been installed
    @Value("${custom.maxRegexpLength}")
    private int MAX_REGEXP_LENGTH = 40;

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ContactCache contactCache;

    @Override
    public List<Contact> findAllByRegexp(String regexp) {
        long start = System.nanoTime();
        Objects.requireNonNull(regexp, "Regexp can't be null!");
        if(regexp.isEmpty() || regexp.length() > MAX_REGEXP_LENGTH) {
            String message = "Regexp mustn't be empty and max allowable regexp length is " + MAX_REGEXP_LENGTH;
            logger.info(message);
            throw new IllegalArgumentException(message);
        }
        int offset = 0;
        List<Contact> contacts;
        List<Contact> filteredContacts = new ArrayList<>();
        do {
            contacts = contactCache.findRange(offset, QUERY_LIMIT);
            findByRegexp(contacts, regexp);
            filteredContacts.addAll(findByRegexp(contacts, regexp));
            offset += QUERY_LIMIT;
        } while (contacts.size() == QUERY_LIMIT);
        long finish = System.nanoTime();
        logger.info(String.format("Filtering completed. Thread=%s. Execution time=%d ms. Regexp=%s.",
                Thread.currentThread().getName(), (finish - start) / 1000000, regexp));
        return filteredContacts;
    }

    List<Contact> findByRegexp(List<Contact> contacts, String regexp) {
        long start = System.nanoTime();
        Pattern pattern = Pattern.compile(regexp);
        List<Contact> filteredContacts = contacts
                .stream()
                .filter(person -> !pattern.matcher(person.getName()).matches())
                .collect(Collectors.toList());
        long finish = System.nanoTime();
        logger.info(String.format("Filtering portion. Thread=%s. Execution time=%d ms. Filtered items=%s.",
                Thread.currentThread().getName(), (finish - start) / 1000000, filteredContacts.size()));
        return filteredContacts;
    }

    @EventListener(ApplicationReadyEvent.class)
    private void createCacheOnStartUp() {
        int offset = 0;
        while (contactCache.findRange(offset, QUERY_LIMIT).size() == QUERY_LIMIT) {
            offset += QUERY_LIMIT;
        }
    }

    void setMaxRegexpLength(int maxRegexpLength) {
        this.MAX_REGEXP_LENGTH = maxRegexpLength;
    }
}
