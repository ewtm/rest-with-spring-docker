package br.com.ewtm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ewtm.converter.DozerConverter;
import br.com.ewtm.data.model.Person;
import br.com.ewtm.data.vo.PersonVO;
import br.com.ewtm.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository repository;
	
	public PersonVO create(PersonVO person) {
		var entity = DozerConverter.parseObject(person, Person.class);
		var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}
	
	public Page<PersonVO> findPersonByName(String firstName,Pageable pageable){
		var page = repository.findPersonByName(firstName, pageable);	
		return page.map(this::converToPersonVO);
	}
	
	
	public Page<PersonVO> findAll(Pageable pageable){
		var page = repository.findAll(pageable);	
		return page.map(this::converToPersonVO);
	}
	
	private PersonVO converToPersonVO(Person entity) {
		return DozerConverter.parseObject(entity, PersonVO.class);
	}
	
	/*
	public List<PersonVO> findAll(){
		return DozerConverter.parseListObjects(repository.findAll(), PersonVO.class);
	}
	*/
	
	public PersonVO findById(Long id) {
		var entity  = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for thid ID"));
		
		return DozerConverter.parseObject(entity, PersonVO.class);
		
	}
	
	public PersonVO update(PersonVO person) {
		
		Person entity = repository.findById(person.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for thid ID"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}
	
	public void delete(Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for thid ID"));
		
		repository.delete(entity);
		
	}
	
	@Transactional
	public PersonVO disablePerson(Long id) {
		repository.disablePersons(id);
		var entity  = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for thid ID"));
		
		return DozerConverter.parseObject(entity, PersonVO.class);
		
	}
	
	
	
	
	
	
	
	
}
