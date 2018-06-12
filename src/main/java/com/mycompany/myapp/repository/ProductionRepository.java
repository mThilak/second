package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Production;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Production entity.
 */
public interface ProductionRepository extends JpaRepository<Production, Long> {

}
