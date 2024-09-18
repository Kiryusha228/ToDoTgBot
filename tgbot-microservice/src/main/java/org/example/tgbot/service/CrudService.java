package org.example.tgbot.service;

import jakarta.ws.rs.HttpMethod;
import lombok.AllArgsConstructor;
import org.apache.http.HttpEntity;
import org.example.tgbot.model.dto.BoardDto;
import org.example.tgbot.model.dto.TodoDto;
import org.example.tgbot.props.LinkProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.http.HttpHeaders;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CrudService {
    @Autowired
    private final RestTemplate restTemplate;
    @Autowired
    private final LinkProperties linkProperties;

    public List<BoardDto> getBoards(Long userChatId) {
        var uri = UriComponentsBuilder.fromUriString(linkProperties.getCrudMicroserviceUrl() +  "/board")
                .queryParam("userChatId", userChatId).toUriString();
        return Arrays.stream(restTemplate.getForObject(uri, BoardDto[].class)).toList();
    }

    public void addBoard(BoardDto boardDto) {
        var uri = UriComponentsBuilder.fromUriString(linkProperties.getCrudMicroserviceUrl() +  "/board/add")
                .queryParam("board", boardDto).toUriString();
        restTemplate.postForObject(uri, boardDto, BoardDto.class);
    }

    public List<TodoDto> getTodos(Long boardId) {
        var uri = UriComponentsBuilder.fromUriString(linkProperties.getCrudMicroserviceUrl() +  "/todo")
                .queryParam("boardId", boardId).toUriString();
        return Arrays.stream(restTemplate.getForObject(uri, TodoDto[].class)).toList();
    }

    public void switchTodoDone(Long todoId){
        var uri = UriComponentsBuilder.fromUriString(linkProperties.getCrudMicroserviceUrl() +  "/todo/switch")
                .queryParam("todoId", todoId).toUriString();
        restTemplate.put(uri, void.class);
        //restTemplate.patchForObject(uri, null, Void.class);
    }

}
