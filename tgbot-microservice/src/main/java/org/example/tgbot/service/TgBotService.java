package org.example.tgbot.service;


import org.example.tgbot.buttons.InlineButtons;
import org.example.tgbot.model.dto.BoardDto;
import org.example.tgbot.props.TgBotProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
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

            if (text.equals("Посмотреть доски")) {
                executeMessage(InlineButtons.sendBoards(update.getMessage().getChatId(), crudService.getBoards(update.getMessage().getChatId())));
            }
        }
        if (update.hasCallbackQuery()) {

            var callbackQuery = update.getCallbackQuery();
            var chatId = callbackQuery.getMessage().getChatId();
            var callbackData = callbackQuery.getData();

            if (Pattern.matches("^board:\\d+", callbackData)) {
                var todos = crudService.getTodos(Long.parseLong(callbackData.split(":")[1]));
                executeMessage(InlineButtons.sendTodos(chatId, todos));
            }

            if (Pattern.matches("^deleteboard:\\d+", callbackData)) {
                //executeMessage(InlineButtons.sendDeleteAlert(chatId, "board", callbackData.split(":")[1]));
                crudService.deleteBoard(Long.parseLong(callbackData.split(":")[1]));
                executeMessage(InlineButtons.sendBoards(chatId, crudService.getBoards(chatId)));
            }

            if (Pattern.matches("^todo:\\d+:\\d+", callbackData)) {
                var todoId = Long.parseLong(callbackData.split(":")[1]);
                var boardId = Long.parseLong(callbackData.split(":")[2]);

                crudService.switchTodoDone(todoId);
                var todos = crudService.getTodos(boardId);

                executeMessage(InlineButtons.sendTodos(chatId, todos));
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
                executeMessage(InlineButtons.sendTodos(chatId, todos));
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

    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeAnswer(AnswerCallbackQuery answerCallbackQuery) {
        try {
            execute(answerCallbackQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendMiniAppMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Запустить мини-приложение");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

        var button = new InlineKeyboardButton("Доска 1");
        button.setCallbackData("checkedButton");
        var data = button.getCallbackData();

        rowInline1.add(button);
        rowsInline.add(rowInline1);
        markupInline.setKeyboard(rowsInline);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Посмотреть доски"));
        keyboard.add(row1);

        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true); // Уменьшить клавиатуру до размера кнопок
        keyboardMarkup.setOneTimeKeyboard(false); // Показывать клавиатуру при каждом сообщении

        try {

            message.setReplyMarkup(keyboardMarkup);
            message.setReplyMarkup(markupInline);
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return tgBotProperties.getName();
    }


}
