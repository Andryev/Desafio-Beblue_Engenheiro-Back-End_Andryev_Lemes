package br.com.beblue.domain.entity;

import br.com.beblue.domain.enumeration.GenreEnum;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Album Entity
 * @author Andryev Lemes - 24/02/2019
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = Album.TABLE_NAME)
public class Album implements Serializable {

    public static final String TABLE_NAME = "B_ALBUM";
    public static final String SEQUENCE_NAME = "B_ALBUM_ID_ALBUM_SEQ";
    private static final long serialVersionUID = -8462132361706418681L;

    @Id
    @Column(name = "ID_ALBUM")
    @GenericGenerator(
            name = Album.SEQUENCE_NAME,
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = Album.SEQUENCE_NAME),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
            }
    )
    @GeneratedValue(generator = Album.SEQUENCE_NAME)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 150)
    private String name;

    @Column(name = "ID_SPOTIFY" , nullable = false, length = 100)
    private String idSpotify;

    @Column(name = "ARTIST_NAME", nullable = false, length = 150)
    private String artistName;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENRE", nullable = false, length = 7)
    private GenreEnum genre;

    @Column(name = "VALUE", nullable = false)
    private BigDecimal value;

}
