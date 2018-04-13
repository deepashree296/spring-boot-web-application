package org.example.ws.service;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.example.ws.service.GreetingService;
import org.example.ws.repository.GreetingRepository;
import org.example.ws.model.Greeting;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class GreetingServiceBean implements GreetingService {

@Autowired
private GreetingRepository greetingRepository;


// find all
@Override
public Collection<Greeting> findAll() {
        Collection<Greeting> greetings = greetingRepository.findAll();
        return greetings;


}

// find one
@Override
public Greeting findOne(Long id) {
        Greeting greeting = greetingRepository.findOne(id);
        return greeting;
}

// create
@Override
public Greeting create(Greeting greeting) {
        if (greeting.getId() != null) {
          // cannot be created
          return null;
        }
        Greeting savedGreeting = greetingRepository.save(greeting);
        return savedGreeting;
}

// update
@Override
public Greeting update(Greeting greeting) {
        Greeting persistedObj = greetingRepository.findOne(greeting.getId());
        if (persistedObj == null) {
          // object is not persisted previously
          return null;
        }

        Greeting updatedGreeting = greetingRepository.save(greeting);
        return updatedGreeting;
}

// delete
@Override
public void delete(Long id) {
        greetingRepository.delete(id);
}

}
