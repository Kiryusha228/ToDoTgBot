package org.example.tgbot.config;

import org.example.tgbot.service.TgBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TgBotConfig {

    @Autowired
    TgBotService bot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        try {
            var tgApi = new TelegramBotsApi(DefaultBotSession.class);
            tgApi.registerBot(bot);
        }
        catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }
}
