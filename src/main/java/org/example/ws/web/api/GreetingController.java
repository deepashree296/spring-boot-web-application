package org.example.ws.web.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

import org.example.ws.model.Greeting;
import java.util.Collection;
import java.math.BigInteger;
import java.util.Map;
import java.util.HashMap;

@RestController  // informs spring mvc that objects returned from this controller methods are either JSON or example.
public class GreetingController {

  // temporary helper methods to manage model object - a hard coded collection of greeting object. In actual, it will serve data from spring data repository
  private static BigInteger nextId;

  // temporary data store
  private static Map<BigInteger,Greeting> greetingMap;

// helper method to create or update greeting objects in the temp data repo- map
  private static Greeting save(Greeting greeting) {
    if (greetingMap == null) {
      greetingMap = new HashMap<BigInteger,Greeting>();
      nextId = BigInteger.ONE;
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
    nextId = nextId.add(BigInteger.ONE);
    greetingMap.put(greeting.getId(), greeting);
    return greeting;
  }

  // helper method to remove data from temp data repo - greetingMap
  private static Boolean delete(BigInteger id ) {
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


/* ResponseEntity is a wrapper class that converts JSON/XML objects to HTTP response objects
* Type is any java object, in this case a collection of Greeting objects
* RequestMapping annotatation informs spring that this method receives HTTP request objects.
* the elements value, method, produces says this method should be mapped to which context, which HTTP method, and what should be the format of response
*/
  @RequestMapping(
         value="/api/greetings",
         method=RequestMethod.GET,
         produces=MediaType.APPLICATION_JSON_VALUE
         )
  public ResponseEntity<Collection<Greeting>> getGreetings() {

      Collection<Greeting> greetings = greetingMap.values();
      return new ResponseEntity<Collection<Greeting>>(
             greetings,
             HttpStatus.OK);
  }

  @RequestMapping(
          value="/api/greetings/{id}",
          method=RequestMethod.GET,
          produces=MediaType.APPLICATION_JSON_VALUE
          )
  public ResponseEntity<Greeting> getGreeting(@PathVariable("id") BigInteger id) {

    Greeting greeting = greetingMap.get(id);
    if (greeting == null) {
      return new ResponseEntity<Greeting>(
             HttpStatus.NOT_FOUND);
    }
      return new ResponseEntity<Greeting>(
             greeting,
             HttpStatus.OK);
  }


  @RequestMapping(
          value="/api/greetings",
          method=RequestMethod.POST,
          consumes=MediaType.APPLICATION_JSON_VALUE,
          produces=MediaType.APPLICATION_JSON_VALUE
          )
  public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting) {
    Greeting savedGreeting = save(greeting);
    return new ResponseEntity<Greeting>(
               savedGreeting,
               HttpStatus.CREATED);
  }

  @RequestMapping(
          value="/api/greetings/{id}",
          method=RequestMethod.PUT,
          consumes=MediaType.APPLICATION_JSON_VALUE,
          produces=MediaType.APPLICATION_JSON_VALUE
          )
  public ResponseEntity<Greeting> updateGreeting(@RequestBody Greeting greeting) {

    Greeting updatedGreeting = save(greeting);
    if (updatedGreeting == null) {
      return new ResponseEntity<Greeting>(
            HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Greeting>(
               updatedGreeting,
               HttpStatus.OK);
  }

  @RequestMapping(
         value="/api/greetings/{id}",
         method=RequestMethod.DELETE,
         consumes=MediaType.APPLICATION_JSON_VALUE,
         produces=MediaType.APPLICATION_JSON_VALUE
         )
  public ResponseEntity<Greeting> deleteGreeting(@PathVariable BigInteger id, @RequestBody Greeting greeting) {
    Boolean isDeleted = delete(id);
    if (!isDeleted) {
      return new ResponseEntity<Greeting>(
            HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Greeting>(
            HttpStatus.NO_CONTENT);

  }


}
