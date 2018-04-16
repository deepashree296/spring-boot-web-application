package org.example.ws.service;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.example.ws.service.GreetingService;
import org.example.ws.repository.GreetingRepository;
import org.example.ws.model.Greeting;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.transaction.annotation.Transactional;
import  org.springframework.transaction.annotation.Propagation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;

@Service
@Transactional(
      propagation=Propagation.SUPPORTS,
      readOnly=true)
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
@Cacheable(
       value="greetings",
       key="#id")
public Greeting findOne(Long id) {
        Greeting greeting = greetingRepository.findOne(id);
        return greeting;
}





// create
@Override
@CachePut(
        value="greetings",
        key="#result.id")
@Transactional(
        propagation=Propagation.REQUIRED,
        readOnly=false)
public Greeting create(Greeting greeting) {
        if (greeting.getId() != null) {
          // cannot be created
          return null;
        }
        Greeting savedGreeting = greetingRepository.save(greeting);
        if (savedGreeting.getId() == 3L) {
          throw new RuntimeException("transaction rollbacked");
        }
        return savedGreeting;
}





// update
@Override
@CachePut(
        value="greetings",
        key="#greeting.id")
@Transactional(
        propagation=Propagation.REQUIRED,
        readOnly=false)
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
@CacheEvict(
        value="greetings",
        key="#id")
public void delete(Long id) {
        greetingRepository.delete(id);
}


// evict all entries in cache`
@Override
@CacheEvict(
        value="greetings",
        allEntries=true)
public void evictCache() {

}






}
