package com.gimchatlogger;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class GimChatLoggerTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(GimChatLoggerPlugin.class);
		RuneLite.main(args);
	}
}