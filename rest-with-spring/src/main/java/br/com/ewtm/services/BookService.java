package br.com.ewtm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import br.com.ewtm.converter.DozerConverter;
import br.com.ewtm.data.model.Book;
import br.com.ewtm.data.vo.BookVO;
import br.com.ewtm.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	BookRepository repository;
	
	public BookVO create(BookVO book) {
		var entity = DozerConverter.parseObject(book, Book.class);
		var vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);
		return vo;
	}
	
	public List<BookVO> findAll(){
		return DozerConverter.parseListObjects(repository.findAll(), BookVO.class);
	}
	
	
	public BookVO findById(Long id) {
		var entity  = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for thid ID"));
		
		return DozerConverter.parseObject(entity, BookVO.class);
		
	}
	
	public BookVO update(BookVO book) {
		
		Book entity = repository.findById(book.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for thid ID"));
		
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		
		
		var vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);
		return vo;
	}
	
	public void delete(Long id) {
		Book entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for thid ID"));
		
		repository.delete(entity);
		
	}
	
	
	
	
	
	
	
}
