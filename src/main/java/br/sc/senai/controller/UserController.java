package br.sc.senai.controller;

import br.sc.senai.model.User;
import br.sc.senai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/users")
    public @ResponseBody ResponseEntity<User> addNewUser(@RequestBody User user) {
        try{
            User newUser = userRepository.save(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(path = "/users")
    public @ResponseBody ResponseEntity<Iterable<User>> getAllUsers(){

        try{
            Iterable<User> users = userRepository.findAll();
            if(((Collection<?>) users).size() == 0){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/{id}")
    public @ResponseBody ResponseEntity<HttpStatus> removeUser(@PathVariable("id") Integer id){

        try{
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/users/{id}")
    public @ResponseBody ResponseEntity<User> updateUser(@PathVariable("id") Integer id, @RequestBody User user){

        Optional<User> userData = userRepository.findById(id);

        if(userData.isPresent()){
            User updatedUser = userData.get();
            updatedUser.setName(user.getName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(user.getPassword());
            return new ResponseEntity<>(userRepository.save(updatedUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/users/{name}")
    public @ResponseBody ResponseEntity<User> allByName(@PathVariable String name, @RequestBody User user){

        try{
            if (((Collection<?>) user).size() == 0){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
