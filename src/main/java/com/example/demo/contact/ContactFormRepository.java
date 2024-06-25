package com.example.demo.contact;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactFormRepository extends CrudRepository<ContactForm, Long> {
}
