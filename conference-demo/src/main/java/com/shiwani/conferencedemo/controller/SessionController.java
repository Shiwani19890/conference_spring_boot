package com.shiwani.conferencedemo.controller;

import com.shiwani.conferencedemo.models.Session;
import com.shiwani.conferencedemo.models.Speaker;
import com.shiwani.conferencedemo.repesetories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping
    public List<Session> list()
    {
       return  sessionRepository.findAll();
    }
    @GetMapping
    @RequestMapping(value = "{id}")
    public Session get(@PathVariable Long id){
        return sessionRepository.getOne(id);

    }
    @GetMapping("/name/{name}")
    public List<Session> getSpeakersByFirstName(@PathVariable final  String name) {

        List<Session> sessions = entityManager.createQuery("select t from sessions t where t.session_name = :sessionName", Session.class)
                .setParameter("sessionName", name)
                .getResultList();
        return sessions;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Session create(@RequestBody final Session session){
        return sessionRepository.saveAndFlush(session);
    }


    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        //Also need to check for children records
        sessionRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Session update(@PathVariable Long id, @RequestBody Session session) {
        //because this a PUT, we expect all attributes to be passed in. A PATCH would only need the attributes to be updated
        //TODO: Add validation that all attributes are passed in, otherwise return a 400 bad payload
        Session existingSession = sessionRepository.getOne(id);
        BeanUtils.copyProperties(session, existingSession, "session_id");
        return sessionRepository.saveAndFlush(existingSession);
    }




}
