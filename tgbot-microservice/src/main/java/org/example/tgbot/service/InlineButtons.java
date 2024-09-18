package org.example.tgbot.service;

import org.example.tgbot.model.dto.BoardDto;
import org.example.tgbot.model.dto.TodoDto;
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
            rowsInline.add(getInlineKeyboardButtonsTitleForBoard(board));
            rowsInline.add(getInlineKeyboardButtonsActionsForBoard(board));
        }

        List<InlineKeyboardButton> addRow = new ArrayList<>();

        var addButton = new InlineKeyboardButton("➕ Добавить доску");
        addButton.setCallbackData("addboard");

        addRow.add(addButton);

        rowsInline.add(addRow);

        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        return message;
    }

    private static List<InlineKeyboardButton> getInlineKeyboardButtonsTitleForBoard(BoardDto board) {
        List<InlineKeyboardButton> boardRow = new ArrayList<>();

        var button = new InlineKeyboardButton(board.getTitle());
        button.setCallbackData("board:" + board.getId().toString());

        boardRow.add(button);
        return boardRow;
    }

    private static List<InlineKeyboardButton> getInlineKeyboardButtonsActionsForBoard(BoardDto board) {
        List<InlineKeyboardButton> boardRow = new ArrayList<>();

        var buttonEdit = new InlineKeyboardButton("\uD83D\uDCDD");
        buttonEdit.setCallbackData("editboard:" + board.getId());

        var buttonDelete = new InlineKeyboardButton("❌");
        buttonDelete.setCallbackData("deleteboard:" + board.getId());

        boardRow.add(buttonEdit);
        boardRow.add(buttonDelete);
        return boardRow;
    }

    public static SendMessage sendTodos(Long chatId, List<TodoDto> todos) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Ваши дела:");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (TodoDto todo : todos) {
            rowsInline.add(getInlineKeyboardButtonsTitleForTodo(todo));
            rowsInline.add(getInlineKeyboardButtonsActionsForTodo(todo));
        }

        List<InlineKeyboardButton> addRow = new ArrayList<>();

        var addButton = new InlineKeyboardButton("➕ Добавить дело");
        addButton.setCallbackData("addtodo");

        addRow.add(addButton);

        rowsInline.add(addRow);

        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        return message;
    }

    private static List<InlineKeyboardButton> getInlineKeyboardButtonsTitleForTodo(TodoDto todo) {
        List<InlineKeyboardButton> todoRow = new ArrayList<>();

        var button = new InlineKeyboardButton();
        if (todo.getDone()){
            button.setText("✅" + todo.getTitle());
        }
        else {
            button.setText(todo.getTitle());
        }
        button.setCallbackData("todo:" + todo.getId().toString() + ":" + todo.getBoardId());

        todoRow.add(button);
        return todoRow;
    }

    private static List<InlineKeyboardButton> getInlineKeyboardButtonsActionsForTodo(TodoDto todo) {
        List<InlineKeyboardButton> todoRow = new ArrayList<>();

        var buttonInfo = new InlineKeyboardButton("❔");
        buttonInfo.setCallbackData("infotodo:" + todo.getId());

        var buttonEdit = new InlineKeyboardButton("\uD83D\uDCDD");
        buttonEdit.setCallbackData("edittodo:" + todo.getId());

        var buttonDelete = new InlineKeyboardButton("❌");
        buttonDelete.setCallbackData("deletetodo:" + todo.getId());

        todoRow.add(buttonInfo);
        todoRow.add(buttonEdit);
        todoRow.add(buttonDelete);
        return todoRow;
    }
}
