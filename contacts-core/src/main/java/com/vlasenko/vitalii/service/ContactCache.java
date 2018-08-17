package com.vlasenko.vitalii.service;

import com.vlasenko.vitalii.domain.Contact;
import com.vlasenko.vitalii.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@CacheConfig(cacheNames = "contacts")
public class ContactCache {
    private final static Logger logger = LoggerFactory.getLogger(ContactCache.class.getSimpleName());

    private ContactRepository contactRepository;

    @Autowired
    public ContactCache(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /*
        notes: method should be public, in other case Spring Boot won't create proxy
        for invocation of this method, hence cache won't work. Also arguments must be Integer,
        because if it is 'int' KeyGenerator won't be able to generate key to withdraw cache.
    */
    @Cacheable("contacts")
    public List<Contact> findRange(Integer offset, Integer limit) {
        if (offset >= 0 && limit > 0) {
            long start = System.nanoTime();
            List<Contact> contacts = contactRepository.findAll(PageRequest.of(offset/limit, limit)).getContent();
            long finish = System.nanoTime();
            logger.info(String.format("Select query limit=%d. Offset=%d. Thread=%s. Execution time=%d ms. Withdraw items=%s.",
                    limit, offset, Thread.currentThread().getName(), (finish - start) / 1000000, contacts.size()));
            return contacts;
        } else {
            String message = String.format("Argument 'offset' must be >= '0' but was %d." +
                    " Argument 'limit' must be > 0 but was %d", offset, limit);
            logger.info(message);
            throw new IllegalArgumentException(message);
        }
    }
}
