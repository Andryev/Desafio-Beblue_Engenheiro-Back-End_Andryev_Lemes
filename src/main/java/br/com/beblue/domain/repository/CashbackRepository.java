package br.com.beblue.domain.repository;

import br.com.beblue.domain.entity.Cashback;
import br.com.beblue.domain.enumeration.GenreEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.Optional;

/**
 * Cashback Repository
 *
 * @author Andryev Lemes - 27/02/2019
 */
@Repository
public interface CashbackRepository extends JpaRepository<Cashback, Long> {

    Optional<Cashback> findByDayOfWeekAndGenre(DayOfWeek dayOfWeek, GenreEnum genreEnum);

}
