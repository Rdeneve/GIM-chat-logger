package com.gimchatlogger;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("GIM Chat Logger")
public interface GimChatLoggerConfig extends Config
{
	@ConfigItem(
		keyName = "webhook",
		name = "Webhook URL",
		description = "URL to the webhook where the plugin should send messages",
		position = 0
	)
	default String webhook() {return ""; }
}
