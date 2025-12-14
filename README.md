# GIM chat logger
A simple plugin to log GIM chat to a discord server using a webhook.

**This plugin will only log messages from the currently logged in player.**

I used these plugins to aid in the development of this plugin, using some of their code to make it work while extending it with my own code.
 - [Clan chat webhook](https://runelite.net/plugin-hub/show/clan-chat-webhook)
 - [Discord chat logger](https://runelite.net/plugin-hub/show/discord-chat-logger)

## Creating a webhook
You can create a webhook by going to the integrations tab in your server settings and clicking the 'Create Webhook' button.

![Create webhook](/img/create-webhook.png)

After clicking this button, you can select your webhook and edit the name and profile image. On this same page you can copy the webhook URL that you need to configure the plugin.

![Edit webhook and copy URL](/img/edit-webhook-and-copy-url.png)

## Configuring the plugin
Paste the webhook URL into the plugin configuration and that's it! The plugin will now send all your messages to your discord server.

![Paste webhook url in plugin config](/img/plugin-configuration.png)


