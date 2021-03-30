package com.shiwani.conferencedemo.controller;


import com.shiwani.conferencedemo.models.Session;
import com.shiwani.conferencedemo.models.Speaker;
import com.shiwani.conferencedemo.repesetories.SessionRepository;
import com.shiwani.conferencedemo.repesetories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakerController {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> list()
    {
        return  speakerRepository.findAll();
    }
    @GetMapping
    @RequestMapping(value = "{id}")
    public Speaker get(@PathVariable Long id){
        return speakerRepository.getOne(id);

    }

    @GetMapping("/name/{name}")
    public List<Speaker> getSpeakersByFirstName(@PathVariable final  String name) {

        List<Speaker> speakers = entityManager.createQuery("select t from speakers t where t.first_name = :firstName", Speaker.class)
                .setParameter("firstName", name)
                .getResultList();
        return speakers;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Speaker create(@RequestBody final Speaker speaker){
        return speakerRepository.saveAndFlush(speaker);
    }


    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        //Also need to check for children records
        speakerRepository.deleteById(id);
    }
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker) {
        //because this a PUT, we expect all attributes to be passed in. A PATCH would only need the attributes to be updated
        //TODO: Add validation that all attributes are passed in, otherwise return a 400 bad payload
        Speaker existingSpeaker = speakerRepository.getOne(id);
        BeanUtils.copyProperties(speaker, existingSpeaker, "speaker_id");
        return speakerRepository.saveAndFlush(existingSpeaker);
    }



}
