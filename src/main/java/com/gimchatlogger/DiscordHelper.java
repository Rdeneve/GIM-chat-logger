package com.gimchatlogger;

import com.gimchatlogger.enums.AccountType;
import com.gimchatlogger.enums.SystemMessageType;
import lombok.Data;
import net.runelite.api.events.ChatMessage;
import okhttp3.*;

import static net.runelite.http.api.RuneLiteAPI.GSON;

public class DiscordHelper {
    // Receive completed message to send to discord
    public Request BuildRequest(String message, String webhookUrl) {
        if (message.isEmpty())
            return null;
        var webhookBody = new WebhookRequestBody();
        webhookBody.setContent(message);

        HttpUrl httpUrl = HttpUrl.parse(webhookUrl);
        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("payload_json", GSON.toJson(webhookBody));
        return new Request.Builder()
                .url(httpUrl)
                .post(requestBodyBuilder.build())
                .build();
    }

    public String BuildDiscordMessage(SystemMessageType systemMessageType, AccountType accountType, ChatMessage chatMessage) {
        var stringBuilder = new StringBuilder();
        // If type = normal, get account name from message and include in message
        if (systemMessageType == SystemMessageType.NORMAL) {
            var messsageAuthor = getMessageAuthor(chatMessage.getName());
            stringBuilder.append(accountType.discordEmoji);
            stringBuilder.append(" **").append(messsageAuthor).append("**").append(": ");
            stringBuilder.append(sanitizeMessageForDiscord(chatMessage.getMessage()));
            return stringBuilder.toString();
        } else {
            // Do not send login message to discord
            if (systemMessageType == SystemMessageType.LOGIN)
                return "";
            stringBuilder.append(systemMessageType.discordEmoji);
            stringBuilder.append(accountType.discordEmoji);
            stringBuilder.append(" ").append(sanitizeMessageForDiscord(chatMessage.getMessage()));
            return stringBuilder.toString();
        }
    }

    @Data
    private static class WebhookRequestBody {
       private String content;
    }

    public static String getMessageAuthor(String messageName) {
        return messageName.replaceAll("\\<.*?>", "").replaceAll("[^0-9a-zA-Z ]+", " ");
    }
    private static String sanitizeMessageForDiscord(String message) {
        message = message.replaceAll("@everyone", "@ everyone");
        message = message.replaceAll("@here", "@ here");
        message = message.replaceAll("<(?::\\w+:|@!*&*|#)[0-9]+>", "");
        message = message.replaceAll("@", "\\@");
        message = message.replaceAll("~", "\\~");
        message = message.replaceAll("`", "\\`");
        return message;
    }
}



