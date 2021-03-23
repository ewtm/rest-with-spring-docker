package br.com.ewtm.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.ewtm.converter.DozerConverter;
import br.com.ewtm.data.model.Person;
import br.com.ewtm.data.vo.PersonVO;
import br.com.ewtm.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	UserRepository repository;
	
	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = repository.findByUserName(username);
		if(user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("Usernama " + username + "not found");
		}
	}
	
	
	
		

	
	
	
	
}
