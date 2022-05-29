package com.task3008.client;

import com.task3008.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BotClient extends Client {
    public class BotSocketThread extends Client.SocketThread {
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
            String[] split = message.split(":");
            if (split.length != 2) return;

            String name = split[0].trim();
            String text = split[1].trim();
            String format = null;

            switch (text) {
                case "дата":
                    format = "d.MM.YYYY";
                    break;
                case "день":
                    format = "d";
                    break;
                case "месяц":
                    format = "MMMM";
                    break;
                case "год":
                    format = "YYYY";
                    break;
                case "время":
                    format = "H:mm:ss";
                    break;
                case "час":
                    format = "H";
                    break;
                case "минуты":
                    format = "m";
                    break;
                case "секунды":
                    format = "s";
                    break;
            }

            if (format != null) {
                Calendar calendar = Calendar.getInstance();
                String textResponse = String.format("Информация для %s: ", name);
                String answer = new SimpleDateFormat(format).format(calendar.getTime());
                BotClient.this.sendTextMessage(textResponse + answer);
            }
        }
    }

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        int X = (int) (Math.random() * 100);
        return "date_bot_" + X;
    }

    public static void main(String[] args) {
        BotClient bot = new BotClient();
        bot.run();
    }
}
