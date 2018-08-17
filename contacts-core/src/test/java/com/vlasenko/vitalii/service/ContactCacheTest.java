package com.vlasenko.vitalii.service;

import com.vlasenko.vitalii.domain.Contact;
import com.vlasenko.vitalii.repository.ContactRepository;
import com.vlasenko.vitalii.service.config.JPAGeneralTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = JPAGeneralTestConfig.class)
@ActiveProfiles("test")
@EnableAutoConfiguration
public class ContactCacheTest {
    @Autowired
    private ContactRepository contactRepository;
    private ContactCache sut;

    @Before
    public void initialization() {
        this.sut = new ContactCache(contactRepository);
        contactRepository.deleteAll();
    }

    @Test
    public void methodFindRangeReturnCorrectListByAppropriateArguments() {
        //GIVEN
        int limit = 3;
        List<Contact> contacts = Arrays.asList(
                new Contact(1, "Kayley Schowalter"),
                new Contact(2, "Arno Bechtelar"),
                new Contact(3, "Winnifred Mosciski"),
                new Contact(4, "Bartholome Leannon"),
                new Contact(5, "Allen Bruen"),
                new Contact(6, "Winnifred Mosciski"),
                new Contact(7, "Flo Runolfsson"),
                new Contact(8, "Joy McCullough")
        );
        contactRepository.saveAll(contacts);
        //WHEN
        List<Contact> firstWithdrawal = sut.findRange(0, limit);
        List<Contact> secondWithdrawal = sut.findRange(3, limit);
        List<Contact> thirdWithdrawal = sut.findRange(6, limit);
        //THEN
        //firstWithdrawal
        assertEquals(firstWithdrawal.get(0).getName(), contacts.get(0).getName());
        assertEquals(firstWithdrawal.get(1).getName(), contacts.get(1).getName());
        assertEquals(firstWithdrawal.get(2).getName(), contacts.get(2).getName());
        assertEquals(firstWithdrawal.size(), 3);
        //secondWithdrawal
        assertEquals(secondWithdrawal.get(0).getName(), contacts.get(3).getName());
        assertEquals(secondWithdrawal.get(1).getName(), contacts.get(4).getName());
        assertEquals(secondWithdrawal.get(2).getName(), contacts.get(5).getName());
        assertEquals(secondWithdrawal.size(), 3);
        //thirdWithdrawal
        assertEquals(thirdWithdrawal.get(0).getName(), contacts.get(6).getName());
        assertEquals(thirdWithdrawal.get(1).getName(), contacts.get(7).getName());
        assertEquals(thirdWithdrawal.size(), 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void methodFindRangeThrowsExceptionWhenArgumentOffsetLowerThenZero() {
        //GIVEN
        int offset = -1;
        //WHEN
        sut.findRange(offset, 1000);
        //THEN IllegalArgumentException THROWN
    }

    @Test(expected = IllegalArgumentException.class)
    public void methodFindRangeThrowsExceptionWhenArgumentLimitLowerThenOne() {
        //GIVEN
        int limit = 0;
        //WHEN
        sut.findRange(0, limit);
        //THEN IllegalArgumentException THROWN
    }
}
