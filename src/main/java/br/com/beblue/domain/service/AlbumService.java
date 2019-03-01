package br.com.beblue.domain.service;

import br.com.beblue.domain.constant.LabelConstants;
import br.com.beblue.domain.entity.Album;
import br.com.beblue.domain.enumeration.GenreEnum;
import br.com.beblue.domain.repository.AlbumRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Album Service
 *
 * @author Andryev Lemes - 24/02/2019
 */
@Slf4j
@Service
public class AlbumService extends AbstractBaseService<Album, Long> {

    @Autowired
    private AlbumRepository albumRepository;

    public Page<Album> findAll(String genre, Pageable pageable) {
        Optional<GenreEnum> anEnum = GenreEnum.getEnum(genre);
        return anEnum.isPresent() ? albumRepository.findByGenreOrderByNameAsc(anEnum.get(), pageable)
                : findAll(pageable);
    }

    public Album findAlbum(Album album) throws BeblueSystemException {
        validate(album);
        Optional<Album> entityAlbum = findById(album.getId());
        if (!entityAlbum.isPresent()) {
            throw new BeblueSystemException(getMessageSource(MenssageKeyConstants.ERROR_ALBUM_NOT_FOUND, album.getId().toString()), MessageTypeEnum.ERROR);
        }
        return entityAlbum.get();
    }

    private void validate(Album album) throws BeblueSystemException {
        List<Message> messages = new ArrayList<Message>();

        if (album == null) {
            throwBeblueException(MenssageKeyConstants.ERROR_ALBUM_DATA_NOT_FOUND, MessageTypeEnum.ERROR);
        }
        validateRequiredField(album.getId(), LabelConstants.ID_ALBUM, messages);

        throwBeblueException(messages);
    }

    @Override
    protected JpaRepository<Album, Long> getRepository() {
        return albumRepository;
    }

}
