package br.com.ewtm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ewtm.data.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
