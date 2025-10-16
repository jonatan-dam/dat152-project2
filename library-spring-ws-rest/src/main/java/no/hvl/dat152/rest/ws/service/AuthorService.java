/**
 * 
 */
package no.hvl.dat152.rest.ws.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.hvl.dat152.rest.ws.exceptions.AuthorNotFoundException;
import no.hvl.dat152.rest.ws.exceptions.BookNotFoundException;
import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.model.Book;
import no.hvl.dat152.rest.ws.repository.AuthorRepository;

/**
 * @author tdoy
 */
@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;
		
	
	public Author findById(int id) throws AuthorNotFoundException {
		
		Author author = authorRepository.findById(id)
				.orElseThrow(()-> new AuthorNotFoundException("Author with the id: "+id+ "not found!"));
		
		return author;
	}
	
	// TODO public saveAuthor(Author author)
	public Author saveAuthor(Author author) {
		return authorRepository.save(author);
	}	
	
	// TODO public Author updateAuthor(Author author, int id)
	public Author updateAuthor(int authorId, Author author) throws AuthorNotFoundException {
		Author existing = authorRepository.findById(authorId)
				.orElseThrow(() -> new AuthorNotFoundException("Author with id = "+authorId+" not found!"));
		
		existing.setFirstname(author.getFirstname());
		existing.setLastname(author.getLastname());
		authorRepository.save(existing);
		return existing;
	}	
	
	// TODO public List<Author> findAll()
	public List<Author> findAll(){
		return (List<Author>) authorRepository.findAll();
	}
	
	// TODO public void deleteById(Long id) throws AuthorNotFoundException 
	public Author deleteById(int id) throws AuthorNotFoundException {
		Author author = authorRepository.findById(id)
				.orElseThrow(() -> new AuthorNotFoundException("Author with id = "+id+" not found!"));
		
		authorRepository.delete(author);
		return author;
		
		
	}
	
	// TODO public Set<Book> findBooksByAuthorId(int id)
	public Set<Book> findBooksByAuthorId(int id) throws AuthorNotFoundException, BookNotFoundException {
		Author author = authorRepository.findById(id)
				.orElseThrow(() -> new AuthorNotFoundException("Author with id = "+id+" not found!"));
		
		Set<Book> books = author.getBooks();
		if(books == null || books.isEmpty()) {
			throw new BookNotFoundException("Books for author = " +id+" not found!");
		} else {
			return books;
		}
	}
}
