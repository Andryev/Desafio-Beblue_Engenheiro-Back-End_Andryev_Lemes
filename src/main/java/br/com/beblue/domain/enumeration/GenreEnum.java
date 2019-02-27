package br.com.beblue.domain.enumeration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.stream.Stream;


/**
 * Genre Enum
 *
 * @author Andryev Lemes - 23/02/2019
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum GenreEnum {

    POP,
    MPB,
    CLASSIC,
    ROCK;

    public static Stream<GenreEnum> stream() {
        return Stream.of(GenreEnum.values());
    }

    public static Optional<GenreEnum> getEnum(String genre) {
        return stream()
                .filter(g -> genre != null && g.name().equals(genre.toUpperCase()))
                .findFirst();
    }

}
