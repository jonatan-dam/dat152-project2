/**
 * 
 */
package no.hvl.dat152.rest.ws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.hvl.dat152.rest.ws.exceptions.OrderNotFoundException;
import no.hvl.dat152.rest.ws.model.Order;
import no.hvl.dat152.rest.ws.service.OrderService;

/**
 * @author tdoy
 */
@RestController
@RequestMapping("/elibrary/api/v1")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	// TODO - getAllBorrowOrders (@Mappings, URI=/orders, and method) + filter by expiry and paginate 
	@GetMapping(value = "/orders")
	public ResponseEntity<Object> getAllBorrowOrders(){
		List<Order> orders = orderService.findAllOrders();
		
		if(orders == null || orders.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(orders, HttpStatus.OK);
		}
	}
	
	
	// TODO - getBorrowOrder (@Mappings, URI=/orders/{id}, and method)
	@GetMapping(value = "/orders/{id}")
	public ResponseEntity<Order> getBorrowOrder(@PathVariable Long id) throws OrderNotFoundException {
		Order order = orderService.findOrder(id);
		
		if(order == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(order, HttpStatus.OK);
		}
	}
	
	
	// TODO - updateOrder (@Mappings, URI=/orders/{id}, and method)
	@PutMapping(value = "/orders/{id}")
	public ResponseEntity<Order> updateOrder(@RequestBody Order order, @PathVariable Long id) throws OrderNotFoundException {

		if(order == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			Order uOrder = orderService.updateOrder(order, id);
			return new ResponseEntity<>(uOrder, HttpStatus.OK);
		}
	}
	
	// TODO - deleteBookOrder (@Mappings, URI=/orders/{id}, and method)
	@DeleteMapping(value = "/orders/{id}")
	public ResponseEntity<String> deleteBookOrder(@PathVariable Long id) throws OrderNotFoundException {
		Order order = orderService.findOrder(id);
		
		if(order == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			orderService.deleteOrder(id);
			String response = "Order with id = "+id+" has been deleted.";
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		}
		
	}
	
}
