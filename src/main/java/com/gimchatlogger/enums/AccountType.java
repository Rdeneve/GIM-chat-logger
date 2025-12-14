package com.gimchatlogger.enums;

public enum AccountType {
    NORMAL(""),
    IRON("<:Ironman_chat_badge:1082980848200065034>"),
    HARDCORE_IRON("<:Hardcore_ironman_chat_badge:1082980846887243826>"),
    ULTIMATE_IRON("<:Ultimate_ironman_chat_badge:1082980849571602532>"),
    UNRANKED_IRON("<:Unranked_group_ironman_chat_badg:1082981035068895302>"),
    GROUP_IRON("<:Group_ironman_chat_badge:1082980845024985128>"),
    HARDCORE_GROUP_IRON("<:Hardcore_group_ironman_chat_badg:1082981031315001344>"),
    PLAYER_MODERATOR("<:Player_moderator_emblem:1082981033340833804>"),
    JAGEX_MODERATOR("<:Player_moderator_emblem:1082981033340833804>");

    public final String discordEmoji;

    private AccountType(String discordEmoji) {
        this.discordEmoji = discordEmoji;
    }
}