package org.example.ws.service;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.example.ws.service.GreetingService;
import org.example.ws.model.Greeting;
import org.springframework.stereotype.Service;

@Service
public class GreetingServiceBean implements GreetingService {

  // temporary helper methods to manage model object - a hard coded collection of greeting object. In actual, it will serve data from spring data repository
  private static Long nextId;

  // temporary data store
  private static Map<Long, Greeting> greetingMap;



  // find all
  @Override
  public Collection<Greeting> findAll() {
    Collection<Greeting> greetings = greetingMap.values();
    return greetings;


  }

  // find one
  @Override
  public Greeting findOne(Long id) {
    Greeting greeting = greetingMap.get(id);
    return greeting;
  }

  // create
  @Override
  public Greeting create(Greeting greeting) {
    Greeting savedGreeting = save(greeting);
    return savedGreeting;
  }

  // update
  @Override
  public Greeting update(Greeting greeting) {
      Greeting updatedGreeting = save(greeting);
      return updatedGreeting;
  }

  // delete
  @Override
  public void delete(Long id) {
    remove(id);
  }

  // helper method to create or update greeting objects in the temp data repo- map
    private static Greeting save(Greeting greeting) {
      if (greetingMap == null) {
        greetingMap = new HashMap<Long,Greeting>();
        nextId = new Long(1);
      }

      // If save is called to update existing greeting objects
      if (greeting.getId() != null) {
        Greeting oldGreetingObj = greetingMap.get(greeting.getId());
        if (oldGreetingObj == null) {
          return null;
        }
        greetingMap.remove(greeting.getId());
        greetingMap.put(greeting.getId(), greeting);
        return  greeting;

      }
      // If save is called to create new greeting object
      greeting.setId(nextId);
      nextId += 1;
      greetingMap.put(greeting.getId(), greeting);
      return greeting;
    }

    // helper method to remove data from temp data repo - greetingMap
    private static boolean remove(Long id ) {
      Greeting deletedGreetingObj = greetingMap.remove(id);
      if (deletedGreetingObj == null) {
        return false;
      }
      return true;
    }

    // this code block will get executed for the first time JVM loads this class
    static {
            Greeting g1 = new Greeting();
            g1.setText("Hello World!");
            save(g1);

            Greeting g2 = new Greeting();
            g2.setText("Hola!");
            save(g2);
    }


}
