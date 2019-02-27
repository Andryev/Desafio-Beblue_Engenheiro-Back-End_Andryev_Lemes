package br.com.beblue.domain.repository;

import br.com.beblue.domain.entity.Album;
import br.com.beblue.domain.enumeration.GenreEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Album Repository
 *
 * @author Andryev Lemes - 24/02/2019
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    Page<Album> findByGenreOrderByNameAsc(GenreEnum genreEnum, Pageable pageable);
}
