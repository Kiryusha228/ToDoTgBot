package org.example.tgbot.service;

import org.example.tgbot.model.dto.BoardDto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineButtons {

    public static SendMessage sendBoards(Long chatId, List<BoardDto> boards) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Ваши доски:");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (BoardDto board : boards) {
            rowsInline.add(getInlineKeyboardButtonsTitle(board));
            rowsInline.add(getInlineKeyboardButtonsActions(board));
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

    private static List<InlineKeyboardButton> getInlineKeyboardButtonsTitle(BoardDto board) {
        List<InlineKeyboardButton> boardRow = new ArrayList<>();

        var button = new InlineKeyboardButton(board.getTitle());
        button.setCallbackData(board.getId().toString());

        boardRow.add(button);
        return boardRow;
    }

    private static List<InlineKeyboardButton> getInlineKeyboardButtonsActions(BoardDto board) {
        List<InlineKeyboardButton> boardRow = new ArrayList<>();

        var buttonEdit = new InlineKeyboardButton("\uD83D\uDCDD");
        buttonEdit.setCallbackData("edit." + board.getId());

        var buttonDelete = new InlineKeyboardButton("❌");
        buttonDelete.setCallbackData("delete." + board.getId());

        boardRow.add(buttonEdit);
        boardRow.add(buttonDelete);
        return boardRow;
    }
}
