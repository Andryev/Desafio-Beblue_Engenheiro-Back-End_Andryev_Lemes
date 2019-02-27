package br.com.beblue.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ItemSale Entity
 * @author Andryev Lemes - 26/02/2019
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = ItemSale.TABLE_NAME)
public class ItemSale implements Serializable {

    public static final String TABLE_NAME = "B_ITEM_SALE";
    public static final String SEQUENCE_NAME = "B_ITEM_SALE_ID_ITEM_SALE_SEQ";
    private static final long serialVersionUID = -924534729538744046L;

    @Id
    @Column(name = "ID_ITEM_SALE")
    @GenericGenerator(
            name = ItemSale.SEQUENCE_NAME,
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = ItemSale.SEQUENCE_NAME),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
            }
    )
    @GeneratedValue(generator = ItemSale.SEQUENCE_NAME)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_ALBUM", nullable = false)
    private Album album;

    @Column(name = "ALBUM_VALUE", nullable = false)
    private BigDecimal albumValue;

    @Column(name = "CASHBACK_VALUE", nullable = false)
    private BigDecimal cashbackValue;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ID_SALE", referencedColumnName = "ID_SALE", nullable = false)
    private Sale sale;

    @Column(name = "AMOUNT", nullable = false)
    private Integer amount;

}