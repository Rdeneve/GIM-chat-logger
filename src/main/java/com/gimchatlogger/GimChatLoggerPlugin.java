package com.gimchatlogger;

import javax.inject.Inject;

import com.gimchatlogger.enums.AccountType;
import com.gimchatlogger.enums.SystemMessageType;
import com.google.inject.Provides;

import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import okhttp3.*;

import java.io.IOException;


@PluginDescriptor(
	name = "GIM Chat Logger"
)
public class GimChatLoggerPlugin extends Plugin
{
	@Provides
	GimChatLoggerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(GimChatLoggerConfig.class);
	}

	@Inject
	private Client client;
	@Inject
	private GimChatLoggerConfig config;
	@Inject
	private OkHttpClient okHttpClient;


	@Subscribe
	public void onChatMessage(ChatMessage chatMessage) {
		if (config.webhook().isEmpty()) {
			return;
		}

		var chatMessageType = chatMessage.getType();

		if (chatMessageType == ChatMessageType.CLAN_GIM_MESSAGE || chatMessageType == ChatMessageType.CLAN_GIM_CHAT) {
			var messageAuthor = DiscordHelper.getMessageAuthor(chatMessage.getName());
			// Only process messages for current user, not for every message in the chat
			if (!messageAuthor.equals(getPlayerName()) && !chatMessage.getMessage().contains(messageAuthor)) {
				return;
			}

			SystemMessageType systemMessageType = getSystemMessageType(chatMessage.getMessage(), chatMessageType);
			AccountType accountType = getAccountType(chatMessage.getName());

			var discordHelper = new DiscordHelper();
			var discordMessage = discordHelper.BuildDiscordMessage(systemMessageType, accountType, chatMessage);
			var request = discordHelper.BuildRequest(discordMessage, config.webhook());
			sendHttpRequest(request);

		}
	}

	private String getPlayerName() {
		return client.getLocalPlayer().getName();
	}

	private void sendHttpRequest(Request request) {
		okHttpClient.newCall(request).enqueue(new Callback()
		{
			@Override
			public void onFailure(Call call, IOException e)
			{
				System.out.println("Error submitting webhook");
				System.out.println(e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException
			{
				response.close();
			}
		});
	}

	private SystemMessageType getSystemMessageType(String message, ChatMessageType messageType)
	{
		if(messageType == ChatMessageType.CLAN_GIM_MESSAGE) {
			if (message.contains("received a drop:")) {
				return SystemMessageType.DROP;
			} else if (message.contains("received special loot from a raid:")) {
				return SystemMessageType.RAID_DROP;
			} else if (message.contains("has completed a quest:")) {
				return SystemMessageType.QUESTS;
			} else if (message.contains("received a new collection log item:")) {
				return SystemMessageType.COLLECTION_LOG;
			} else if (message.contains("personal best:")) {
				return SystemMessageType.PERSONAL_BEST;
			} else if (message.contains("To talk in your Ironman Group's channel, start each line of chat with")) {
				return SystemMessageType.LOGIN;
			} else if (message.contains("has defeated") || message.contains("has been defeated by")) {
				return SystemMessageType.PVP;
			} else if (message.contains("has a funny feeling like") || message.contains("backpack:") || message.contains("something special:")) {
				return SystemMessageType.PET_DROP;
			} else if ((message.contains("has reached") && (message.contains("level") || message.contains("XP"))) || message.contains("has reached a total level of")) {
				return SystemMessageType.LEVEL_UP;
			} else if (message.contains("tier of rewards from Combat Achievements!") || (message.contains("has completed") && message.contains("combat task"))) {
				return SystemMessageType.COMBAT_ACHIEVEMENTS;
			} else if (message.contains("received a clue item:")) {
				return SystemMessageType.CLUE_DROP;
			} else if (message.contains("has left.") || message.contains("has been invited into the clan by") || message.contains("has joined.")) {
				return SystemMessageType.ATTENDANCE;
			} else if(message.contains("has completed the") && message.contains("diary.")) {
				return SystemMessageType.DIARY;
			}

			return SystemMessageType.UNKNOWN;
		}

		return SystemMessageType.NORMAL;
	}

	private AccountType getAccountType(String message)
	{
		if (message.contains("<img=0>") || message.contains("<img=1>")) {
			return AccountType.PLAYER_MODERATOR;
		} else if (message.contains("<img=2>")) {
			return AccountType.IRON;
		} else if (message.contains("<img=10>")) {
			return AccountType.HARDCORE_IRON;
		} else if (message.contains("<img=3>")) {
			return AccountType.ULTIMATE_IRON;
		} else if (message.contains("<img=41>")) {
			return AccountType.GROUP_IRON;
		} else if (message.contains("<img=42>")) {
			return AccountType.HARDCORE_GROUP_IRON;
		} else if (message.contains("<img=43>")) {
			return AccountType.UNRANKED_IRON;
		} else {
			return AccountType.NORMAL;
		}
	}
}


