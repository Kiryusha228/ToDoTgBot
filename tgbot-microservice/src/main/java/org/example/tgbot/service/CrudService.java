package org.example.tgbot.service;

import lombok.AllArgsConstructor;
import org.example.tgbot.model.dto.BoardDto;
import org.example.tgbot.model.dto.TodoDto;
import org.example.tgbot.props.LinkProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
        var uri = UriComponentsBuilder.fromUriString(linkProperties.getCrudMicroserviceUrl() + "/board")
                .queryParam("userChatId", userChatId).toUriString();
        return Arrays.stream(restTemplate.getForObject(uri, BoardDto[].class)).toList();
    }

    public void deleteBoard(Long boardId) {
        var uri = UriComponentsBuilder.fromUriString(linkProperties.getCrudMicroserviceUrl() + "/board/delete")
                .queryParam("boardId", boardId).toUriString();
        restTemplate.delete(uri);
    }

    public List<TodoDto> getTodos(Long boardId) {
        var uri = UriComponentsBuilder.fromUriString(linkProperties.getCrudMicroserviceUrl() + "/todo")
                .queryParam("boardId", boardId).toUriString();
        return Arrays.stream(restTemplate.getForObject(uri, TodoDto[].class)).toList();
    }

    public TodoDto getTodoById(Long todoId) {
        var uri = UriComponentsBuilder.fromUriString(linkProperties.getCrudMicroserviceUrl() + "/todo/get")
                .queryParam("todoId", todoId).toUriString();
        return restTemplate.getForObject(uri, TodoDto.class);
    }

    public void deleteTodo(Long todoId) {
        var uri = UriComponentsBuilder.fromUriString(linkProperties.getCrudMicroserviceUrl() + "/todo/delete")
                .queryParam("todoId", todoId).toUriString();
        restTemplate.delete(uri);
    }

    public void switchTodoDone(Long todoId) {
        var uri = UriComponentsBuilder.fromUriString(linkProperties.getCrudMicroserviceUrl() + "/todo/switch")
                .queryParam("todoId", todoId).toUriString();
        restTemplate.put(uri, void.class);
        //restTemplate.patchForObject(uri, null, Void.class);
    }



}
