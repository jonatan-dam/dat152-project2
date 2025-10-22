/**
 * 
 */
package no.hvl.dat152.rest.ws.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import no.hvl.dat152.rest.ws.service.BookService;

/**
 * @author tdoy
 */
@RestController
@RequestMapping("/elibrary/api/v1")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@GetMapping("/books")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> getAllBooks(){
		
		List<Book> books = bookService.findAll();
		
		if(books.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<>(books, HttpStatus.OK);		
	}
	
	@GetMapping("/books/{isbn}")
	public ResponseEntity<Object> getBook(@PathVariable String isbn) throws BookNotFoundException{
		
		Book book = bookService.findByISBN(isbn);
		
		return new ResponseEntity<>(book, HttpStatus.OK);
				
	}
	
	@PostMapping("/books")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Book> createBook(@RequestBody Book book){
		
		Book nbook = bookService.saveBook(book);
		
		return new ResponseEntity<>(nbook, HttpStatus.CREATED);
	}
	
	// TODO - getAuthorsOfBookByISBN (@Mappings, URI, and method)
	@GetMapping("/books/{isbn}/authors")
	public ResponseEntity<Object> getAuthorsOfBookByISBN(@PathVariable String isbn) throws BookNotFoundException, AuthorNotFoundException {
		
		Book book = bookService.findByISBN(isbn);
		
		Set<Author> authors = book.getAuthors();
		if(authors.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(authors, HttpStatus.OK);
		}
	}
	
	// TODO - updateBookByISBN (@Mappings, URI, and method)
	@PutMapping("/books/{isbn}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Book> updateBook(@RequestBody Book book) throws BookNotFoundException {
		Book ubook = bookService.updateBook(book);
		return new ResponseEntity<>(ubook, HttpStatus.OK);
		
		
	}
	
	// TODO - deleteBookByISBN (@Mappings, URI, and method)
	@DeleteMapping("/books/{isbn}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> deleteByISBN(@PathVariable String isbn) throws BookNotFoundException {
		
		bookService.deleteByISBN(isbn);
		String response = "Book deleted.";
		return new ResponseEntity<>(response, HttpStatus.OK);
		
		
	}

}
