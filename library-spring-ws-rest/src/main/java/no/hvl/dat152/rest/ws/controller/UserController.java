/**
 * 
 */
package no.hvl.dat152.rest.ws.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.hvl.dat152.rest.ws.exceptions.OrderNotFoundException;
import no.hvl.dat152.rest.ws.exceptions.UserNotFoundException;
import no.hvl.dat152.rest.ws.model.Order;
import no.hvl.dat152.rest.ws.model.User;
import no.hvl.dat152.rest.ws.service.UserService;

/**
 * @author tdoy
 */
@RestController
@RequestMapping("/elibrary/api/v1")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public ResponseEntity<Object> getUsers(){
		
		List<User> users = userService.findAllUsers();
		
		if(users.isEmpty())
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@GetMapping(value = "/users/{id}")
	public ResponseEntity<Object> getUser(@PathVariable Long id) throws UserNotFoundException, OrderNotFoundException{
		
		User user = userService.findUser(id);
		
		return new ResponseEntity<>(user, HttpStatus.OK);	
		
	}
	
	// TODO - createUser (@Mappings, URI=/users, and method)
	@PostMapping(value = "/users")
	public ResponseEntity<User> createUser(@RequestBody User user){
		User nUser = userService.saveUser(user);
		
		return new ResponseEntity<>(nUser, HttpStatus.CREATED);
	}

	// TODO - updateUser (@Mappings, URI, and method)
	@PutMapping(value = "/users/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id) throws UserNotFoundException {

		User updatedUser = userService.updateUser(user, id);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		
	}
	
	// TODO - deleteUser (@Mappings, URI, and method)
	@DeleteMapping(value = "/users/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) throws UserNotFoundException {

		userService.deleteUser(id);
		String response = "User with id = "+id+" has been deleted.";
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}

	// TODO - getUserOrders (@Mappings, URI=/users/{id}/orders, and method)
	@GetMapping(value = "/users/{id}/orders")
	public ResponseEntity<Object> getUserOrders(@PathVariable Long id) throws UserNotFoundException{
		Set<Order> orders = userService.getUserOrders(id);
		
		if(orders.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(orders, HttpStatus.OK);
		}
	}
	
	// TODO - getUserOrder (@Mappings, URI=/users/{uid}/orders/{oid}, and method)
	@GetMapping(value = "/users/{uid}/orders/{oid}")
	public ResponseEntity<Order> getUserOrder(@PathVariable Long uid, @PathVariable Long oid) throws UserNotFoundException, OrderNotFoundException {
		Order order = userService.getUserOrder(uid, oid);
		return new ResponseEntity<>(order, HttpStatus.OK);
		
	}
	
	// TODO - deleteUserOrder (@Mappings, URI, and method)
	@DeleteMapping(value = "/users/{uid}/orders/{oid}")
	public ResponseEntity<String> deleteUserOrder(@PathVariable Long uid, @PathVariable Long oid) throws UserNotFoundException, OrderNotFoundException{
		String response = "Order with id = "+oid+" belonging to user with id = "+uid+" has been deleted.";
		userService.deleteOrderForUser(uid, oid);
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	// TODO - createUserOrder (@Mappings, URI, and method) + HATEOAS links
	@PostMapping("/users/{id}/orders")
	public ResponseEntity<List<Order>> createOrdersForUser(@PathVariable Long id, @RequestBody Order order)
	        throws UserNotFoundException {

	    User updatedUser = userService.createOrdersForUser(id, order);
	    Set<Order> orders = updatedUser.getOrders();

	    // Add HATEOAS links for each order
	    orders.forEach(o -> {
	        try {
				o.add(linkTo(methodOn(OrderController.class).getBorrowOrder(o.getId())).withSelfRel());
				o.add(linkTo(methodOn(OrderController.class).deleteBookOrder(o.getId())).withRel("Return: DELETE"));
			} catch (OrderNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	    });

	    return new ResponseEntity<>(List.copyOf(orders), HttpStatus.CREATED);
	}


	
}
