package com.vlasenko.vitalii.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ContactController sut;

    @Test(threadPoolSize = 30, invocationCount = 30,  timeOut = 5000)
    public void eachRequestHasToBeProcessedInFiveSecond() throws JsonProcessingException {
        //GIVEN
        String regexp = "^.*[aei].*$";
        //WHEN
        sut.filterALL(regexp);
        //THEN SUCCESS ACCORDING TO TIMEOUT
    }
}
