package org.example.ws.web.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

import org.example.ws.model.Greeting;
import org.example.ws.service.GreetingService;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

@RestController  // informs spring mvc that objects returned from this controller methods are either JSON or example.
public class GreetingController {

@Autowired // to tell spring to inject an instance of GreetingService into the controller class. use of Interface type for dependency injection  follows programming by
// contract model, ie to make sure only public methods are exposed by the interface or available to the service client.
private GreetingService greetingService;


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
public ResponseEntity<Collection<Greeting> > getGreetings() {

        Collection<Greeting> greetings = greetingService.findAll();
        return new ResponseEntity<Collection<Greeting> >(
                       greetings,
                       HttpStatus.OK);
}

@RequestMapping(
        value="/api/greetings/{id}",
        method=RequestMethod.GET,
        produces=MediaType.APPLICATION_JSON_VALUE
        )
public ResponseEntity<Greeting> getGreeting(@PathVariable("id") Long id) {

        Greeting greeting = greetingService.findOne(id);
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
        Greeting savedGreeting = greetingService.create(greeting);
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

        Greeting updatedGreeting = greetingService.update(greeting);
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
public ResponseEntity<Greeting> deleteGreeting(@PathVariable Long id, @RequestBody Greeting greeting) {
        greetingService.delete(id);
        return new ResponseEntity<Greeting>(
                       HttpStatus.NO_CONTENT);

}

}
