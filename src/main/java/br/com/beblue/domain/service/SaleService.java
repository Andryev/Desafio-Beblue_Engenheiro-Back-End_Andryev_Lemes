package br.com.beblue.domain.service;

import br.com.beblue.domain.constant.LabelConstants;
import br.com.beblue.domain.entity.ItemSale;
import br.com.beblue.domain.entity.Sale;
import br.com.beblue.domain.repository.SaleRepository;
import br.com.beblue.infrastructure.constants.MenssageKeyConstants;
import br.com.beblue.infrastructure.enumeration.MessageTypeEnum;
import br.com.beblue.infrastructure.exception.BeblueSystemException;
import br.com.beblue.infrastructure.exception.Message;
import br.com.beblue.infrastructure.service.AbstractBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Sale Service
 *
 * @author Andryev Lemes - 24/02/2019
 */
@Slf4j
@Service
public class SaleService extends AbstractBaseService<Sale, Long> {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private CashbackService cashbackService;

    public Page<Sale> findAll(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return startDate != null && endDate != null ?
                saleRepository.findBySaleDateBetween(startDate, endDate, pageable) :
                findAll(pageable);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Sale sell(Sale sale) throws BeblueSystemException {
        List<Message> messages = new ArrayList<Message>();
        validate(sale, messages);
        sale.setSaleDate(LocalDate.now());
        sale.setSaleValue(BigDecimal.ZERO);
        sale.setCashbackValue(BigDecimal.ZERO);
        for (ItemSale itemSale : sale.getItemsSale()) {
            validate(itemSale, messages);
            itemSale.setAlbum(albumService.findAlbum(itemSale.getAlbum()));

            itemSale.setAlbumValue(itemSale.getAlbum().getValue());
            itemSale.setCashbackValue(cashbackService.getCashbackValueItemSale(sale.getSaleDate().getDayOfWeek(), itemSale));

            sale.setCashbackValue(sale.getCashbackValue().add(itemSale.getCashbackValue()).setScale(2, RoundingMode.FLOOR));
            sale.setSaleValue(getSaleValue(sale, itemSale));

            itemSale.setSale(sale);
        }
        return saleRepository.save(sale);
    }

    private BigDecimal getSaleValue(Sale sale, ItemSale itemSale) {
        return sale.getSaleValue().add(itemSale.getAlbumValue()).multiply(new BigDecimal(itemSale.getAmount())).setScale(2, RoundingMode.FLOOR);
    }

    private void validate(Sale sale, List<Message> messages) throws BeblueSystemException {
        if (sale == null) {
            throwBeblueException(MenssageKeyConstants.ERROR_DATA_NOT_FOUND, MessageTypeEnum.ERROR);
        }
        validateRequiredField(sale.getItemsSale(), LabelConstants.ITEMS_SALE, messages, MenssageKeyConstants.ERROR_ITEMS_SALE_REQUIRED);
        throwBeblueException(messages);
    }

    private void validate(ItemSale itemSale, List<Message> messages) throws BeblueSystemException {
        validateRequiredField(itemSale.getAmount(), LabelConstants.AMOUNT, messages, MenssageKeyConstants.ERROR_ITEM_SALE_AMOUNT_REQUIRED);
        throwBeblueException(messages);
    }

    @Override
    protected JpaRepository<Sale, Long> getRepository() {
        return saleRepository;
    }

}