package org.example.tgbot.service;


import org.example.tgbot.model.dto.BoardDto;
import org.example.tgbot.props.TgBotProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
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

            if (Pattern.matches("^add.+", text)) {
                crudService.addBoard(new BoardDto(0L, update.getMessage().getChatId(), text.substring(3)));
                executeMessage(InlineButtons.sendBoards(update.getMessage().getChatId(), crudService.getBoards(update.getMessage().getChatId())));
            }

            if (text.equals("Посмотреть доски")) {
                executeMessage(InlineButtons.sendBoards(update.getMessage().getChatId(), crudService.getBoards(update.getMessage().getChatId())));
            }
        }
        if (update.hasCallbackQuery()) {

            var callbackData = update.getCallbackQuery().getData();

            if (Pattern.matches("^board:\\d+", callbackData)){
                var todos = crudService.getTodos(Long.parseLong(callbackData.split(":")[1]));
                executeMessage(InlineButtons.sendTodos(update.getCallbackQuery().getMessage().getChatId(), todos));
            }

            if (Pattern.matches("^todo:\\d+:\\d+", callbackData)){
                var todoId = Long.parseLong(callbackData.split(":")[1]);
                var boardId = Long.parseLong(callbackData.split(":")[2]);

                crudService.switchTodoDone(todoId);
                var todos = crudService.getTodos(boardId);

                executeMessage(InlineButtons.sendTodos(update.getCallbackQuery().getMessage().getChatId(), todos));
            }

            if (callbackData.equals("addboard")){

                var addMessage = new SendMessage();
                addMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
                addMessage.setText("Введите название новой доски в формате \"addНазвание доски\"");
                executeMessage(addMessage);

                //crudService.addBoard(new BoardDto(0L, update.getMessage().getChatId(), ));

                //executeMessage(InlineButtons.sendBoards(update.getMessage().getChatId(), crudService.getBoards(update.getMessage().getChatId())));
            }

            //var message = new SendMessage();
            //message.setChatId(update.getCallbackQuery().getMessage().getChatId());
            //message.setText(update.getCallbackQuery().getData());


        }
    }

    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        }
        catch (Exception e) {
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
