package org.example.tgbot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineButtons {

    public static SendMessage sendBoards(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Ваши доски:");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            rowsInline.add(getInlineKeyboardButtons(i));
        }

        List<InlineKeyboardButton> addRow = new ArrayList<>();

        var addButton = new InlineKeyboardButton("➕ Добавить доску");
        addButton.setCallbackData("add");

        addRow.add(addButton);

        rowsInline.add(addRow);

        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        return message;
    }

    private static List<InlineKeyboardButton> getInlineKeyboardButtons(int i) {
        List<InlineKeyboardButton> boardRow = new ArrayList<>();

        var button = new InlineKeyboardButton("Доска " + i);
        button.setCallbackData("board" + i);

        var buttonEdit = new InlineKeyboardButton("\uD83D\uDCDD");
        buttonEdit.setCallbackData("editBoard" + i);

        var buttonDelete = new InlineKeyboardButton("❌");
        buttonDelete.setCallbackData("deleteBoard" + i);

        boardRow.add(button);
        boardRow.add(buttonEdit);
        boardRow.add(buttonDelete);
        return boardRow;
    }
}
