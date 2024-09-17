package org.example.tgbot.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.tgbot.props.LinkProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@AllArgsConstructor
public class CrudService {
    @Autowired
    private final RestTemplate restTemplate;
    @Autowired
    private final LinkProperties linkProperties;

    public String getBoards() {

        var uri = UriComponentsBuilder.fromUriString(linkProperties.getCrudMicroserviceUrl() +  "/board")
                .queryParam("userChatId", 1).toUriString();
        return restTemplate.getForObject(uri, String.class);
    }
}
