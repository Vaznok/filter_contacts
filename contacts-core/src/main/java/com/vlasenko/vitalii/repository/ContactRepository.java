package com.vlasenko.vitalii.repository;

import com.vlasenko.vitalii.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

@Repository
@Table(name = "contact")
public interface ContactRepository extends JpaRepository<Contact, Long> {

}