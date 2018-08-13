package com.vlasenko.vitalii.service;

import com.vlasenko.vitalii.domain.Contact;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ContactServiceImplTest {
    private ContactServiceImpl sut = new ContactServiceImpl();

    @Test
    public void filterListByRegexpWhichBeginsOnLetter_A() {
        //GIVEN
        String regexp = "^A.*$";
        List<Contact> contacts = Arrays.asList(
                new Contact(1, "Kayley Schowalter"),
                new Contact(2, "Arno Bechtelar"),
                new Contact(3, "Winnifred Mosciski"),
                new Contact(4, "Bartholome Leannon"),
                new Contact(5, "Allen Bruen")
        );
        List<Contact> expectedContacts = Arrays.asList(
                new Contact(1, "Kayley Schowalter"),
                new Contact(3, "Winnifred Mosciski"),
                new Contact(4, "Bartholome Leannon")
        );
        //WHEN
        List<Contact> filteredContacts = sut.findByRegexp(contacts, regexp);
        //THEN
        assertEquals(expectedContacts.get(0).getName(), filteredContacts.get(0).getName());
        assertEquals(expectedContacts.get(1).getName(), filteredContacts.get(1).getName());
        assertEquals(expectedContacts.get(2).getName(), filteredContacts.get(2).getName());
        assertEquals(expectedContacts.size(), filteredContacts.size());
    }

    @Test
    public void filterListByRegexpWhichDoesNotContainLetters_a_e_i() {
        //GIVEN
        String regexp = "^.*[aei].*$";
        List<Contact> contacts = Arrays.asList(
                new Contact(1, "Tom Wunsch"),
                new Contact(2, "Arno Bechtelar"),
                new Contact(3, "Winnifred Mosciski"),
                new Contact(4, "Flo Runolfsson"),
                new Contact(5, "Joy McCullough")
        );
        List<Contact> expectedContacts = Arrays.asList(
                new Contact(1, "Tom Wunsch"),
                new Contact(4, "Flo Runolfsson"),
                new Contact(5, "Joy McCullough")
        );
        //WHEN
        List<Contact> filteredContacts = sut.findByRegexp(contacts, regexp);
        //THEN
        assertEquals(expectedContacts.get(0).getName(), filteredContacts.get(0).getName());
        assertEquals(expectedContacts.get(1).getName(), filteredContacts.get(1).getName());
        assertEquals(expectedContacts.get(2).getName(), filteredContacts.get(2).getName());
        assertEquals(expectedContacts.size(), filteredContacts.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void methodFindAllByRegexpThrowsIllegalArgumentExceptionForEmptyArgument() {
        //GIVEN
        String regexp = "";
        //WHEN
        sut.findAllByRegexp(regexp);
        //THEN IllegalArgumentException THROWN
    }

    @Test(expected = IllegalArgumentException.class)
    public void methodFindAllByRegexpThrowsIllegalArgumentExceptionForMaxRegexpLength() {
        //GIVEN
        sut.setMaxRegexpLength(20);
        String regexp = "^(https?://)?([da-z.-]+).([a-z.]{2,6})([/w .-]*)*/?$"; //regexp length = 52
        //WHEN
        sut.findAllByRegexp(regexp);
        //THEN IllegalArgumentException THROWN
    }

    @Test(expected = NullPointerException.class)
    public void methodFindAllByRegexpThrowsNullPointerExceptionForNullArgument() {
        //GIVEN
        String regexp = null;
        //WHEN
        sut.findAllByRegexp(regexp);
        //THEN NullPointerException THROWN
    }
}
