package br.com.beblue.domain.service;

import br.com.beblue.domain.entity.Album;
import br.com.beblue.domain.enumeration.GenreEnum;
import br.com.beblue.domain.repository.AlbumRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Album Service
 *
 * @author Andryev Lemes - 24/02/2019
 */
@Slf4j
@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveAll(List<Album> albums) {
        albumRepository.saveAll(albums);

    }

    public List<Album> findAll() {
        return albumRepository.findAll();
    }

    public Page<Album> findAll(String genre, Pageable pageable) {
        Optional<GenreEnum> anEnum = GenreEnum.getEnum(genre);
        return anEnum.isPresent() ? albumRepository.findByGenreOrderByNameAsc(anEnum.get(), pageable)
                : albumRepository.findAll(pageable);
    }

    public Optional<Album> findById(Long id) {
        return albumRepository.findById(id);
    }
}
