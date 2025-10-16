/**
 * 
 */
package no.hvl.dat152.rest.ws.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.hvl.dat152.rest.ws.exceptions.OrderNotFoundException;
import no.hvl.dat152.rest.ws.exceptions.UserNotFoundException;
import no.hvl.dat152.rest.ws.model.Order;
import no.hvl.dat152.rest.ws.model.User;
import no.hvl.dat152.rest.ws.repository.UserRepository;

/**
 * @author tdoy
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> findAllUsers(){
		
		List<User> allUsers = (List<User>) userRepository.findAll();
		
		return allUsers;
	}
	
	public User findUser(Long userid) throws UserNotFoundException {
		
		User user = userRepository.findById(userid)
				.orElseThrow(()-> new UserNotFoundException("User with id: "+userid+" not found"));
		
		return user;
	}
	
	
	// TODO public User saveUser(User user)
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	// TODO public void deleteUser(Long id) throws UserNotFoundException 
	public void deleteUser(Long id) throws UserNotFoundException {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User with id = "+id+" not found!"));
		
		userRepository.delete(user);
	}
	
	// TODO public User updateUser(User user, Long id)
	public User updateUser(User user, Long id) throws UserNotFoundException {
		User existing = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User with id= "+id+" not found!"));
		
		existing.setFirstname(user.getFirstname());
		existing.setLastname(user.getLastname());
		userRepository.save(existing);
		return existing;
	}
	
	// TODO public Set<Order> getUserOrders(Long userid) 
	public Set<Order> getUserOrders(Long userid) throws UserNotFoundException {
		User user = userRepository.findById(userid)
				.orElseThrow(() -> new UserNotFoundException("User with id = "+userid+" not found!"));
		
		return user.getOrders();
	}
	
	// TODO public Order getUserOrder(Long userid, Long oid)
	public Order getUserOrder(Long userid, Long oid) throws UserNotFoundException, OrderNotFoundException {
		User user = userRepository.findById(userid)
				.orElseThrow(() -> new UserNotFoundException("User with id = "+userid+" not found!"));
		
		Set<Order> orders = user.getOrders();
		
		Order order = orders.stream()
			.filter(o -> o.getId() == oid)
			.findAny()
			.orElseThrow(() -> new OrderNotFoundException("Order with id = "+oid+ " not found for user with id = "+userid));
		
		return order;
	}
	
	// TODO public void deleteOrderForUser(Long userid, Long oid)
	public void deleteOrderForUser(Long userid, Long oid) throws UserNotFoundException, OrderNotFoundException {
		User user = userRepository.findById(userid)
				.orElseThrow(() -> new UserNotFoundException("User with id = "+userid+" not found!"));
		
		Set<Order> orders = user.getOrders();
		
		Order order = orders.stream()
				.filter(o -> o.getId() == oid)
				.findAny()
				.orElseThrow(() -> new OrderNotFoundException("Order with id = "+oid+ " not found for user with id = "+userid));
		
		orders.remove(order);
		userRepository.save(user);
		
	}
	
	// TODO public User createOrdersForUser(Long userid, Order order)
	public User createOrdersForUSer(Long userid, Order order) throws UserNotFoundException {
		User user = userRepository.findById(userid)
				.orElseThrow(() -> new UserNotFoundException("User with id = "+userid+" not found!"));
		
		Set<Order> orders = user.getOrders();
		orders.add(order);
		userRepository.save(user);
		
		return user;
	}
}
