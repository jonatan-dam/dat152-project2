/**
 * 
 */
package no.hvl.dat152.rest.ws.controller;

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

import no.hvl.dat152.rest.ws.exceptions.AuthorNotFoundException;
import no.hvl.dat152.rest.ws.exceptions.BookNotFoundException;
import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.model.Book;
import no.hvl.dat152.rest.ws.service.AuthorService;

/**
 * 
 */
@RestController
@RequestMapping("/elibrary/api/v1")
public class AuthorController {

	
	// TODO
		@Autowired
		private AuthorService authorService;
		
		@GetMapping("/authors")
		public ResponseEntity<Object> getAllAuthors(){
			List<Author> authors = authorService.findAll();
			
			if(authors.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(authors, HttpStatus.OK);
			}
			
		}
		
		@GetMapping("/authors/{id}")
		public ResponseEntity<Author> getAuthorById(@PathVariable int id) throws AuthorNotFoundException {
			Author author = authorService.findById(id);
			
			if(author == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(author, HttpStatus.OK);
			}
		}
		
		@PostMapping("/authors")
		public ResponseEntity<Author> saveAuthor(@RequestBody Author author){
			Author nAuthor = authorService.saveAuthor(author);
			
			return new ResponseEntity<>(nAuthor, HttpStatus.CREATED);
		}
		
		@DeleteMapping("/authors/{id}")
		public ResponseEntity<String> deleteById(@PathVariable int id) throws AuthorNotFoundException {
			Author author = authorService.findById(id);
			
			if(author == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				authorService.deleteById(id);
				String response = "Author with id = "+id+" has been deleted.";
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}
		
		@PutMapping("/authors/{id}")
		public ResponseEntity<Author> updateAuthor(@RequestBody Author author, @PathVariable int id) throws AuthorNotFoundException {
			Author uAuthor = authorService.updateAuthor(id, author);
			
			if(author == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(uAuthor, HttpStatus.OK);
			}
		}
	
	// TODO - getBooksByAuthorId (@Mappings, URI, and method)
		@GetMapping("/authors/{id}/books")
		public ResponseEntity<Object> getBooksByAuthorId(@PathVariable int id) throws AuthorNotFoundException, BookNotFoundException {
			Set<Book> books = authorService.findBooksByAuthorId(id);
			
			if(books == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(books, HttpStatus.OK);
			}
			
			
			
		}
	
	


}
