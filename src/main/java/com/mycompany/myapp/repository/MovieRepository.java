package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Movie entity.
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
