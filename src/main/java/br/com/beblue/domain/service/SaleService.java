package br.com.beblue.domain.service;

import br.com.beblue.domain.entity.Sale;
import br.com.beblue.domain.repository.SaleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Sale Service
 *
 * @author Andryev Lemes - 24/02/2019
 */
@Slf4j
@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private CashbackService cashbackService;


    public Optional<Sale> findById(Long id) {
        return saleRepository.findById(id);
    }

    public Page<Sale> findAll(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return startDate != null && endDate != null ?
                saleRepository.findBySaleDateBetween(startDate, endDate, pageable) :
                saleRepository.findAll(pageable);
    }

    public Sale sell(Sale sale) {

        sale.setSaleDate(LocalDate.now());
        sale.setSaleValue(BigDecimal.ZERO);
        sale.setCashbackValue(BigDecimal.ZERO);

        sale.getItemsSale().forEach(itemSale -> {
            itemSale.setAlbum(albumService.findById(itemSale.getAlbum().getId()).get());
            itemSale.setAlbumValue(itemSale.getAlbum().getValue().multiply(new BigDecimal(itemSale.getAmount())));

            BigDecimal percentualCashback = cashbackService.getCashback(sale.getSaleDate().getDayOfWeek(), itemSale.getAlbum().getGenre());

            itemSale.setCashbackValue((itemSale.getAlbumValue().multiply(new BigDecimal(itemSale.getAmount()))).multiply(percentualCashback).divide(new BigDecimal(100.00)).setScale(2, RoundingMode.CEILING));

            sale.setCashbackValue(sale.getCashbackValue().add(itemSale.getCashbackValue()).setScale(2, RoundingMode.CEILING));
            sale.setSaleValue(sale.getSaleValue().add(itemSale.getAlbumValue().multiply(new BigDecimal(itemSale.getAmount()))));

            itemSale.setSale(sale);
        });
        return saleRepository.save(sale);
    }
}