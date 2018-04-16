package org.example.ws.service;

import java.util.Collection;
import org.example.ws.model.Greeting;

public interface GreetingService {

  // by default all methods in Interface are public
  // find all
   Collection<Greeting> findAll();

  // find one
  Greeting findOne(Long id);

  // create
  Greeting create(Greeting greeting);

  // update
  Greeting update(Greeting greeting);

  // delete
  void delete(Long id);

  // CacheEvict
  void evictCache();


}
