package br.com.beblue.domain.entity;

import br.com.beblue.domain.enumeration.GenreEnum;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.DayOfWeek;

/**
 * Cashback Entity
 *
 * @author Andryev Lemes - 26/02/2019
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = Cashback.TABLE_NAME)
public class Cashback implements Serializable {

    public static final String TABLE_NAME = "B_CASHBACK";
    public static final String SEQUENCE_NAME = "B_CASHBACK_ID_CASHBACK_SEQ";
    private static final long serialVersionUID = 9061927153133526401L;

    @Id
    @Column(name = "ID_CASHBACK")
    @GenericGenerator(
            name = Cashback.SEQUENCE_NAME,
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = Cashback.SEQUENCE_NAME),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
            }
    )
    @GeneratedValue(generator = Cashback.SEQUENCE_NAME)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "DAY_OF_WEEK", nullable = false)
    private DayOfWeek dayOfWeek;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENRE", nullable = false, length = 7)
    private GenreEnum genre;

    @Column(name = "CASHBACK_VALUE", nullable = false)
    private BigDecimal cashbackValue;
}
