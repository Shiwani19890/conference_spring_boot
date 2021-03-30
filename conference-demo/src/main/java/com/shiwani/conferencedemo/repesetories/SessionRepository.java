package com.shiwani.conferencedemo.repesetories;

import com.shiwani.conferencedemo.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session,Long> {
}
