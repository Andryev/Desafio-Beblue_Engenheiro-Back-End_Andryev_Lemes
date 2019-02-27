package br.com.beblue.application.resource;

import br.com.beblue.domain.entity.Album;
import br.com.beblue.domain.service.AlbumService;
import br.com.beblue.infrastructure.constants.URIConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Album Resource
 *
 * @author Andryev Lemes - 24/02/2019
 */
@RestController
@RequestMapping(URIConstants.ALBUM)
public class AlbumResource {

    @Autowired
    private AlbumService albumService;

    @GetMapping
    public ResponseEntity<Page<Album>> findAll(
            @RequestParam(value = "genre", required = false) String genre, Pageable pageable) {
        return new ResponseEntity<>(albumService.findAll(genre, pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<Album>> findById(@PathVariable("id") Long id) {
        return Optional
                .ofNullable(albumService.findById(id))
                .map(album -> ResponseEntity.ok().body(album))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
