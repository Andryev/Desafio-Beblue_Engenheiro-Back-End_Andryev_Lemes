package br.com.beblue.domain.service;

import br.com.beblue.domain.entity.Cashback;
import br.com.beblue.domain.enumeration.GenreEnum;
import br.com.beblue.domain.repository.CashbackRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Optional;

/**
 * Cashback Service
 *
 * @author Andryev Lemes - 24/02/2019
 */
@Slf4j
@Service
public class CashbackService {

    @Autowired
    private CashbackRepository cashbackRepository;


    public BigDecimal getCashback(DayOfWeek dayOfWeek, GenreEnum genre) {
        Optional<Cashback> cashback = cashbackRepository.findByDayOfWeekAndGenre(dayOfWeek, genre);

        return cashback.isPresent() ? cashback.get().getCashbackValue() : BigDecimal.ZERO;
    }

}
