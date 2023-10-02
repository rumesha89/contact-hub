package com.vrealcompany.contactHub.repository;

import com.vrealcompany.contactHub.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByName(String name);

    Optional<Contact> findByEmailAndCompanyName(String email, String companyName);
}
