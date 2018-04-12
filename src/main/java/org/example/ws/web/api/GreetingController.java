package org.example.ws.web.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
  private static Map<BigInteger,Greeting> greetingMap;

  private static Greeting save(Greeting greeting) {
    if (greetingMap == null) {
      greetingMap = new HashMap<BigInteger,Greeting>();
      nextId = BigInteger.ONE;
    }
    greeting.setId(nextId);
    nextId = nextId.add(BigInteger.ONE);
    greetingMap.put(greeting.getId(), greeting);
    return greeting;
  }

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
         produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Collection<Greeting>> getGreetings() {

      Collection<Greeting> greetings = greetingMap.values();
      return new ResponseEntity<Collection<Greeting>>(greetings,
             HttpStatus.OK);
  }






}
