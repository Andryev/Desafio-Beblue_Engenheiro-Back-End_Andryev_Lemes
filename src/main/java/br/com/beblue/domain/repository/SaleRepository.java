package br.com.beblue.domain.repository;

import br.com.beblue.domain.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * Sale Repository
 *
 * @author Andryev Lemes - 24/02/2019
 */
@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    Page<Sale> findBySaleDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

}
