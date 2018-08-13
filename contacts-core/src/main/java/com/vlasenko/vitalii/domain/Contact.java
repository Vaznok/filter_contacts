package com.vlasenko.vitalii.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    public Contact() {
    }

    public Contact(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public Contact(long id, String name) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return id == contact.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
