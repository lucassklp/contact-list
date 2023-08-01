package com.contact.list.repositories;

import com.contact.list.entities.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Page<Contact> findByName(Pageable page, String name);
    Page<Contact> findByUserId(Pageable page, long userId);
    Optional<Contact> findByIdAndUserId(long id, long userId);
    void deleteByIdAndUserId(long id, long userId);

}
