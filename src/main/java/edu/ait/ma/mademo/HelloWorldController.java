package edu.ait.ma.mademo;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1") // Root URI for all mapping
public class HelloWorldController {
   //Use getMapping
    @GetMapping("sayhello")
    public String sayHelloWorld(){
        return "Hello world from mademo";
    }

    //Use Request Mapping
    /*@RequestMapping(value = "/sayhello", method = RequestMethod.GET)
    public String sayHelloWorld(){
        return "Hello world from mademo";
    }*/

    // pass parameters into REST API method
    /*@GetMapping("/helloWorldPathParam/{message}")
    public String helloWorldPathParam(@PathVariable String message){
        return "Hello world with message: "+message;
    }*/

    // pass parameters into REST API method , placeholder name in URI
    @GetMapping("/helloWorldPathParam/{msg}")
    public String helloWorldPathParam(@PathVariable("msg") String message){
        return "Hello world with message: "+message;
    }

    // multiple path parameters
    @GetMapping("/helloWorldPathParam/{firstName}/{lastName}")
    public String helloWorldPathParam(@PathVariable String firstName, @PathVariable String lastName){
        return "Hello world with name: "+firstName+" "+lastName;
    }

    // REST optional path parameters - 1. Approach
    // Set the required flag
    /*@GetMapping( value = {"/helloWorldPathParamOptionalId", "/helloWorldPathParamOptionalId/{id}"})
    public String helloWorldPathParamOptionalId(@PathVariable(required = false) Integer id){
        if(id!=null){
            return "Hello World with id: "+id;
        } else {
            return "Hello world with empty id";
        }
    }*/

    // REST optional path parameters - 2. Approach
    // Use the Optional<T>
    @GetMapping( value = {"/helloWorldPathParamOptionalId", "/helloWorldPathParamOptionalId/{id}"})
    public String helloWorldPathParamOptionalId(@PathVariable Optional<Integer> id){
        if(id.isPresent()){
            return "Hello World with id: "+id;
        } else {
            return "Hello world with empty id";
        }
    }

    // REST Query Request Parameters
    // params expected as param1 and param2
    // http://localhost:8080/api/v1/helloRequestParam?param1=dary&param2=41
    @GetMapping("/helloRequestParam")
    public String helloRequestParam(@RequestParam String param1, @RequestParam String param2){
        return "Param 1: "+param1+" , Param 2: "+param2;
    }

    // REST Query Request Parameters
    // params expected as param1 and param2
    // http://localhost:8080/api/v1/helloRequestParam2?param1=dary&param2=41
    @GetMapping("/helloRequestParam2")
    public String helloRequestParam2(@RequestParam(value = "name") String param1, @RequestParam("age") Integer param2){
        return "name: "+param1+" , age: "+param2;
    }

    // Rest Optional Request Params - 1. Approach
    // http://localhost:8080/api/v1/helloRequestParam3?opt=daryl
    // and
    // http://localhost:8080/api/v1/helloRequestParam3 are both valid
    // Set the required flag
    @GetMapping("/helloRequestParam3")
    public String helloRequestParam3(@RequestParam(value = "opt", required = false) String param){
        if (param!=null){
            return "Option value: "+param;
        } else {
            return "Option opt not provided ";
        }
    }

    // Rest Optional Request Params - 1. Approach
    // http://localhost:8080/api/v1/helloRequestParam4?opt=daryl
    // and
    // http://localhost:8080/api/v1/helloRequestParam4 are both valid
    // Use Optional<T>
    @GetMapping("/helloRequestParam4")
    public String helloRequestParam4(@RequestParam(value = "opt") Optional<String> param){
        if (param.isPresent()){
            return "Option value: "+param;
        } else {
            return "Option opt not provided ";
        }
    }

    // Rest Optional Default values - 1. Approach
    // http://localhost:8080/api/v1/helloRequestParam5?opt=daryl
    // and
    // http://localhost:8080/api/v1/helloRequestParam5 are both valid
    // Set defaultValue
    @GetMapping("/helloRequestParam5")
    public String helloRequestParam5(@RequestParam(defaultValue = "test") String param){
        return "Option value: "+param;
    }

    // Rest Optional Default values - 2. Approach
    // http://localhost:8080/api/v1/helloRequestParam6?opt=daryl
    // and
    // http://localhost:8080/api/v1/helloRequestParam6 are both valid
    // Use orElseGet method of Optional<T>
    @GetMapping("/helloRequestParam6")
    public String helloRequestParam6(@RequestParam Optional<String> param){
        return "Option value: "+param.orElseGet(() -> "test");
    }

    // Return JSON data
    // change return type of the REST method
    @GetMapping("/dummyPerson")
    public Person getSamplePerson(){
        return new Person("John", "Doe", 20);
    }

    // Return JSON Array
    // change return type of the REST method
    @GetMapping("/dummyPersonList")
    public List<Person> getSamplePersonList(){
        return Arrays.asList(new Person("John", "Doe", 20), new Person("John", "Doe", 25));
    }

    // Return JSON Map
    // change return type of the REST method
    @GetMapping("/dummyPersonMap")
    public Map<String,Person> getSamplePersonMap(){
        Map<String, Person> peopleMap = new HashMap<>();
        peopleMap.put("Joe",new Person("Joe", "Doe", 20));
        peopleMap.put("Jane",new Person("Jane", "Bloggs", 20));
        return peopleMap;
    }

    // Send Data in RequestBody
    @PostMapping("/sendPerson")
    public String sendPerson(@RequestBody Person person){
        return person.getFirstName() + " " + person.getLastName();
    }

    // Specify Request / Response format
    // consumes = RequestBody
    // produces = ResponseBody
    @GetMapping(value = "/dummyPersonWithProduces", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person getSamplePersonWithProduces(){
        return new Person("John", "Doe", 20);
    }
    @PostMapping(value = "/sendPersonWithConsumes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String sendPersonWithConsumes(@RequestBody Person person){
        return person.getFirstName() + " " + person.getLastName();
    }

    // Consumes and Produce XML
    // consumes = RequestBody
    // produces = ResponseBody
    @GetMapping(value = "/dummyPersonWithProducesXml", produces = MediaType.APPLICATION_XML_VALUE)
    public Person getSamplePersonWithProducesXml(){
        return new Person("John", "Doe", 20);
    }
    @PostMapping(value = "/sendPersonWithConsumesXml", consumes = MediaType.APPLICATION_XML_VALUE)
    public String sendPersonWithConsumesXml(@RequestBody Person person){
        return person.getFirstName() + " " + person.getLastName();
    }

    // Status Code and Exception Handling
    // Return new ResponseEntity with return code
    @GetMapping(value = "dummyPersonWithResponseEntity", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getSamplePersonWithResponseEntity(){
        return new ResponseEntity<>(new Person("John","Doe",20), HttpStatus.ACCEPTED);
    }

    // Return none-types
    @GetMapping("/doNothing")
    public ResponseEntity doNothing(){
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    // Create Custom exception class with annotation
    // Return none-types
    @GetMapping("/notAllowed")
    public String notAllowed(){
        throw new ForbiddenException();
    }
}
