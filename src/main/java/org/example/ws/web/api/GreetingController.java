package org.example.ws.web.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

import org.example.ws.model.Greeting;
import java.util.Collection;

@RestController  // informs spring mvc that objects returned from this controller methods are either JSON or example.
public class GreetingController {

/* ResponseEntity is a wrapper class that converts JSON/XML objects to HTTP response objects
* Type is any java object, in this case a collection of Greeting objects
* RequestMapping annotatation informs spring that this method receives HTTP request objects.
* the elements value, method, produces says this method should be mapped to which context, which HTTP method, and what should be the format of response
*/
  @RequestMapping(
         value="/api/greetings",
         method=RequestMethod.Get,
         produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Collection<Greeting>> getGreetings(){

  }






}
