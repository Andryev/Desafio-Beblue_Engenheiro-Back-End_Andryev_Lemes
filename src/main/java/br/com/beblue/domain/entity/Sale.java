package br.com.beblue.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Sale Entity
 *
 * @author Andryev Lemes - 26/02/2019
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = Sale.TABLE_NAME)
public class Sale implements Serializable {

    public static final String TABLE_NAME = "B_SALE";
    public static final String SEQUENCE_NAME = "B_SALE_ID_SALE_SEQ";
    private static final long serialVersionUID = -915449412879423766L;

    @Id
    @Column(name = "ID_SALE")
    @GenericGenerator(
            name = Sale.SEQUENCE_NAME,
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = Sale.SEQUENCE_NAME),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
            }
    )
    @GeneratedValue(generator = Sale.SEQUENCE_NAME)
    private Long id;

    @Column(name = "CASHBACK_VALUE", nullable = false)
    private BigDecimal cashbackValue;


    @Column(name = "SALE_DATE", nullable = false)
    private LocalDate saleDate;

    @Column(name = "SALE_VALUE", nullable = false)
    private BigDecimal saleValue;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sale")
    private Collection<ItemSale> itemsSale;

}
