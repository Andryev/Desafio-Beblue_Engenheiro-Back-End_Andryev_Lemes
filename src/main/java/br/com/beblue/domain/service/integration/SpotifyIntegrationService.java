package br.com.beblue.domain.service.integration;

import br.com.beblue.domain.entity.Album;
import br.com.beblue.domain.enumeration.GenreEnum;
import br.com.beblue.domain.service.AlbumService;
import br.com.beblue.infrastructure.constants.MenssageKeyConstants;
import br.com.beblue.infrastructure.constants.SpotifyApiConstants;
import br.com.beblue.infrastructure.enumeration.MessageTypeEnum;
import br.com.beblue.infrastructure.exception.BeblueSystemException;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service responsible for integrating with Spotify
 *
 * @author Andryev Lemes - 24/02/2019
 */
@Slf4j
@Service
public class SpotifyIntegrationService {

    @Autowired
    private SpotifyApi spotifyApi;

    @Autowired
    private AlbumService albumService;

    public void synchronize() {
        if (CollectionUtils.isEmpty(albumService.findAll())) {
            saveAlbuns();
        }
    }

    private JSONArray getAlbums(GenreEnum genre) throws BeblueSystemException {
        JSONArray albums = null;
        try {
            albums = new JSONArray("[" + spotifyApi.searchAlbums(genre.name())
                    .setQueryParameter(SpotifyApiConstants.GENRES, genre.name())
                    .limit(50)
                    .build()
                    .getJson() + "]");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BeblueSystemException(MenssageKeyConstants.SPOTIFY_INTEGRATION_ERROR, MessageTypeEnum.ERROR);
        } catch (SpotifyWebApiException e) {
            log.error(e.getMessage(), e);
            throw new BeblueSystemException(MenssageKeyConstants.SPOTIFY_INTEGRATION_ERROR, MessageTypeEnum.ERROR);
        }
        return albums;
    }

    private List<Album> parseAlbums(JSONArray object, GenreEnum genre) {
        JSONObject json = object.getJSONObject(0).getJSONObject(SpotifyApiConstants.ALBUMS);

        return json.getJSONArray(SpotifyApiConstants.ITEMS).toList().stream()
                .map(item -> {
                    Map obj = (Map) item;
                    return Album.builder()
                            .idSpotify((String) obj.get(SpotifyApiConstants.ID))
                            .name((String) obj.get(SpotifyApiConstants.NAME))
                            .genre(genre)
                            .artistName((String) ((Map) ((List) obj.get(SpotifyApiConstants.ARTISTS)).get(0)).get(SpotifyApiConstants.NAME))
                            .value(new BigDecimal((Math.random() * ((100.00 - 12.00) + 1)) + 12.00)
                                    .setScale(2, RoundingMode.CEILING))
                            .build();
                })
                .collect(Collectors.toList());

    }

    private void saveAlbuns() {

        GenreEnum.stream().forEach(item -> {
            List<Album> albums = null;
            try {
                albums = parseAlbums(getAlbums(item), item);
            } catch (BeblueSystemException e) {
                log.error(e.getMessage(), e);
            }
            albumService.saveAll(albums);
        });

    }


}
