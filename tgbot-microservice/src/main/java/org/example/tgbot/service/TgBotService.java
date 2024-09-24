package org.example.tgbot.service;


import org.example.tgbot.buttons.InlineButtons;
import org.example.tgbot.model.dto.LastMessageDto;
import org.example.tgbot.props.TgBotProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.regex.Pattern;

@Service
public class TgBotService extends TelegramLongPollingBot {

    private final TgBotProperties tgBotProperties;
    private final CrudService crudService;


    public TgBotService(TgBotProperties tgBotProperties, CrudService crudService) {
        super(tgBotProperties.getToken());
        this.tgBotProperties = tgBotProperties;
        this.crudService = crudService;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            var text = update.getMessage().getText();
            var chatId = update.getMessage().getChatId();
            if (text.equals("/start")){
                crudService.createLastMessage(chatId);
                executeMessage(InlineButtons.createInlineKeyboard(chatId));
            }

            if (text.equals("Посмотреть доски")) {
                var lastMessageId = crudService.getLastMessage(chatId);
                if (lastMessageId != null){
                    deleteMessage(chatId, lastMessageId);
                }
                var messageId = executeMessage(InlineButtons.sendBoards(update.getMessage().getChatId(), crudService.getBoards(update.getMessage().getChatId())));
                crudService.setLastMessage(new LastMessageDto(chatId,messageId));
            }
        }
        if (update.hasCallbackQuery()) {

            var callbackQuery = update.getCallbackQuery();
            var chatId = callbackQuery.getMessage().getChatId();
            var callbackData = callbackQuery.getData();

            if (Pattern.matches("^board:\\d+", callbackData)) {
                var todos = crudService.getTodos(Long.parseLong(callbackData.split(":")[1]));

                var lastMessageId = crudService.getLastMessage(chatId);
                if (lastMessageId != null){
                    deleteMessage(chatId, lastMessageId);
                }
                var messageId = executeMessage(InlineButtons.sendTodos(chatId, todos));

                crudService.setLastMessage(new LastMessageDto(chatId,messageId));

            }

            if (Pattern.matches("^deleteboard:\\d+", callbackData)) {
                //executeMessage(InlineButtons.sendDeleteAlert(chatId, "board", callbackData.split(":")[1]));
                crudService.deleteBoard(Long.parseLong(callbackData.split(":")[1]));

                var lastMessageId = crudService.getLastMessage(chatId);
                if (lastMessageId != null){
                    deleteMessage(chatId, lastMessageId);
                }

                var messageId = executeMessage(InlineButtons.sendBoards(chatId, crudService.getBoards(chatId)));

                crudService.setLastMessage(new LastMessageDto(chatId,messageId));

            }

            if (Pattern.matches("^todo:\\d+:\\d+", callbackData)) {
                var todoId = Long.parseLong(callbackData.split(":")[1]);
                var boardId = Long.parseLong(callbackData.split(":")[2]);

                crudService.switchTodoDone(todoId);
                var todos = crudService.getTodos(boardId);


                var lastMessageId = crudService.getLastMessage(chatId);
                if (lastMessageId != null){
                    deleteMessage(chatId, lastMessageId);
                }

                var messageId = executeMessage(InlineButtons.sendTodos(chatId, todos));

                crudService.setLastMessage(new LastMessageDto(chatId,messageId));


            }

            if (Pattern.matches("^infotodo:\\d+", callbackData)) {
                var todoId = Long.parseLong(callbackData.split(":")[1]);
                var todo = crudService.getTodoById(todoId);

                AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
                answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
                answerCallbackQuery.setShowAlert(true);
                answerCallbackQuery.setText("Дело: " + todo.getTitle() + "\nОписание: " + todo.getDescription());

                executeAnswer(answerCallbackQuery);
            }

            if (Pattern.matches("^deletetodo:\\d+:\\d+", callbackData)) {

                var todoId = Long.parseLong(callbackData.split(":")[1]);
                var boardId = Long.parseLong(callbackData.split(":")[2]);
                crudService.deleteTodo(todoId);

                var todos = crudService.getTodos(boardId);


                var lastMessageId = crudService.getLastMessage(chatId);
                if (lastMessageId != null){
                    deleteMessage(chatId, lastMessageId);
                }

                var messageId = executeMessage(InlineButtons.sendTodos(chatId, todos));

                crudService.setLastMessage(new LastMessageDto(chatId,messageId));

                //var todoId = callbackData.split(":")[1];
                //var boardId = callbackData.split(":")[2];

                //executeMessage(InlineButtons.sendDeleteAlert(chatId, "todo", todoId + ":" +boardId ));


            }

            if (callbackData.equals("addboard")) {

                var addMessage = new SendMessage();
                addMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
                addMessage.setText("Введите название новой доски в формате \"addНазвание доски\"");
                executeMessage(addMessage);
            }

//            if (Pattern.matches("^confirmdelete:\\D+:\\d+:", callbackData)) {
//                var element = callbackData.split(":")[1];
//                if (element.equals("board")){
//                    crudService.deleteBoard(Long.parseLong(callbackData.split(":")[2]));
//                    executeMessage(InlineButtons.sendBoards(chatId, crudService.getBoards(chatId)));
//                }
//                else {
//                    var todoId = Long.parseLong(callbackData.split(":")[2]);
//                    var boardId = Long.parseLong(callbackData.split(":")[3]);
//                    crudService.deleteTodo(todoId);
//
//                    var todos = crudService.getTodos(boardId);
//                    executeMessage(InlineButtons.sendTodos(chatId, todos));
//                }
//            }

        }
    }

    private Integer executeMessage(SendMessage message) {
        Message sentMessage = new Message();
        try {
            sentMessage = execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sentMessage.getMessageId();
    }

    private void executeAnswer(AnswerCallbackQuery answerCallbackQuery) {
        try {
            execute(answerCallbackQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteMessage(Long chatId, Integer messageId){
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);

        try {
            execute(deleteMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getBotUsername() {
        return tgBotProperties.getName();
    }


}
