package br.com.beblue.infrastructure.component.listener;

import br.com.beblue.domain.service.integration.SpotifyIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


/**
 * Beblue Listener Component - Event triggered at application start
 *
 * @author Andryev Lemes - 24/02/2019
 */
@Component
public class BeblueListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private SpotifyIntegrationService spotifyIntegrationService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        spotifyIntegrationService.synchronize();
    }

}
