package com.gimchatlogger.enums;

public enum SystemMessageType {
	DROP("<:Guideprices:1147702301298016349>"),
	RAID_DROP("<:Guideprices:1147702301298016349>"),
	PET_DROP("<:Petshopicon:1147703359227297872>"),
	PERSONAL_BEST("<:Speedrunningshopicon:1147703649917751357>"),
	COLLECTION_LOG("<:Collectionlog:1147701373455048814>"),
	QUESTS("<:Quest:1147703095711764550>"),
	PVP("<:BountyHuntertradericon:1147703810110791802>"),
	ATTENDANCE("<:AccountManagementCommunityicon:1147704337599041606>"),
	LEVEL_UP("<:Statsicon:1147702829029543996>"),
	CLUE_DROP("<:DistractionDiversionmapicon:1147704823500779521>"),
	COMBAT_ACHIEVEMENTS("<:CombatAchievementsicon:1147704502368075786>"),
	DIARY("<:TaskMastericon:1147705076677345322>"),
	NORMAL(""),
	UNKNOWN(""),
	LOGIN("");

	public final String discordEmoji;

	private SystemMessageType(String discordEmoji) {
		this.discordEmoji = discordEmoji;
	}
}
