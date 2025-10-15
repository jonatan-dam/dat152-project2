/**
 * 
 */
package no.hvl.dat152.rest.ws.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import no.hvl.dat152.rest.ws.exceptions.BookNotFoundException;
import no.hvl.dat152.rest.ws.exceptions.UpdateBookFailedException;
import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.model.Book;
import no.hvl.dat152.rest.ws.repository.BookRepository;

/**
 * @author tdoy
 */
@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	
	public Book saveBook(Book book) {
		
		return bookRepository.save(book);
		
	}
	
	public List<Book> findAll(){
		
		return (List<Book>) bookRepository.findAll();
		
	}
	
	
	public Book findByISBN(String isbn) throws BookNotFoundException {
		
		Book book = bookRepository.findByIsbn(isbn)
				.orElseThrow(() -> new BookNotFoundException("Book with isbn = "+isbn+" not found!"));
		
		return book;
	}
	
	// TODO public Book updateBook(Book book, String isbn)
	public Book updateBook(Book book) throws BookNotFoundException {
		Book existingBook = bookRepository.findByIsbn(book.getIsbn())
				.orElseThrow(() -> new BookNotFoundException("Book with isbn ="+book.getIsbn()+" not found!"));
		
		existingBook.setTitle(book.getTitle());
		existingBook.setAuthors(book.getAuthors());
		return existingBook;
		
	}
	
	// TODO public List<Book> findAllPaginate(Pageable page)
	public List<Book> findAllPaginate(Pageable page){
		int limit = page.getPageSize();
		int offset = page.getPageNumber() * page.getPageSize();
		return bookRepository.findAllPaginate(limit, offset);
	}
	
	
	// TODO public Set<Author> findAuthorsOfBookByISBN(String isbn)
	public Set<Author> findAuthorsOfBookByISBN(String isbn){
		Set<Author> authors = new HashSet<>();
		Book book = null;
		try {
			book = findByISBN(isbn);
		} catch (BookNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		authors = book.getAuthors();
		
		return authors;
	}
	
	// TODO public void deleteById(long id)
	public void deleteById(long id) throws BookNotFoundException {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException("Book with id = "+id+" not found!"));
		
		bookRepository.delete(book);
	}
	
	// TODO public void deleteByISBN(String isbn) 
	public void deleteByISBN(String isbn) throws BookNotFoundException {
		Book book = bookRepository.findByIsbn(isbn)
				.orElseThrow(() -> new BookNotFoundException("Book wtih isbn = "+isbn+" not found!"));
		
		bookRepository.delete(book);
		
	}
	
}
