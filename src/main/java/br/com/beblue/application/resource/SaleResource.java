package br.com.beblue.application.resource;

import br.com.beblue.domain.entity.Sale;
import br.com.beblue.domain.service.SaleService;
import br.com.beblue.infrastructure.constants.URIConstants;
import br.com.beblue.infrastructure.exception.BeblueSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Sale Resource
 *
 * @author Andryev Lemes - 26/02/2019
 */
@RestController
@RequestMapping(URIConstants.SALE)
public class SaleResource {

    @Autowired
    private SaleService saleService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<Sale>> findById(@PathVariable("id") Long id) {
        return Optional
                .ofNullable(saleService.findById(id))
                .map(sale -> ResponseEntity.ok().body(sale))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping
    public ResponseEntity<Page<Sale>> findAll(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate,
            Pageable pageable) {
        return new ResponseEntity<>(saleService.findAll(startDate, endDate, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Sale> sell(@RequestBody Sale sale) throws BeblueSystemException {
        return new ResponseEntity<>(saleService.sell(sale), HttpStatus.CREATED);
    }

}
