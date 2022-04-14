package com.openrsc.server.constants;

import java.util.HashMap;
import java.util.Map;

import static com.openrsc.server.constants.AppearanceSlots.*;

// Normally you should just do ItemDefinition.getAppearanceId(), but not all animations have items associated with them.

class AppearanceSlots {
	static final int NPC = -2;
	static final int ANY = -1;
	static final int HEAD = 0;
	static final int SHIRT = 1;
	static final int PANTS = 2;
	static final int SHIELD = 3;
	static final int WEAPON = 4;
	static final int HAT = 5;
	static final int BODY = 6;
	static final int LEGS = 7;
	static final int GLOVES = 8;
	static final int BOOTS = 9;
	static final int AMULET = 10;
	static final int CAPE = 11;
	static final int ARROW = 12;
	static final int MORPHING_RING = 13;
}

public enum AppearanceId {
	NOTHING(0, ANY),
	SHORT_HAIR(1, HEAD),
	MALE_BODY(2, SHIRT),
	COLOURED_PANTS(3, PANTS),
	LONG_HAIR(4, HEAD),
	FEMALE_BODY(5, SHIRT),
	SHORT_HAIR_2(6, HEAD),
	LONG_BEARDED_HEAD(7, HEAD),
	BALD_HEAD(8, HEAD),
	CHEFS_HAT(9, HAT),
	WHITE_APRON(10, AMULET),
	BROWN_APRON(11, AMULET),
	LEATHER_BOOTS(12, BOOTS),
	LARGE_BRONZE_HELMET(13, HAT),
	LARGE_IRON_HELMET(14, HAT),
	LARGE_STEEL_HELMET(15, HAT),
	LARGE_MITHRIL_HELMET(16, HAT),
	LARGE_ADAMANTITE_HELMET(17, HAT),
	LARGE_RUNE_HELMET(18, HAT),
	LARGE_BLACK_HELMET(19, HAT),
	LARGE_WHITE_HELMET(20, HAT), // NO ITEM
	BRONZE_CHAIN_MAIL_BODY(21, BODY),
	IRON_CHAIN_MAIL_BODY(22, BODY),
	STEEL_CHAIN_MAIL_BODY(23, BODY),
	MITHRIL_CHAIN_MAIL_BODY(24, BODY),
	ADAMANTITE_CHAIN_MAIL_BODY(25, BODY),
	RUNE_CHAIN_MAIL_BODY(26, BODY),
	BLACK_CHAIN_MAIL_BODY(27, BODY),
	BRONZE_PLATE_MAIL_BODY(28, BODY),
	IRON_PLATE_MAIL_BODY(29, BODY),
	STEEL_PLATE_MAIL_BODY(30, BODY),
	MITHRIL_PLATE_MAIL_BODY(31, BODY),
	ADAMANTITE_PLATE_MAIL_BODY(32, BODY),
	BLACK_PLATE_MAIL_BODY(33, BODY),
	RUNE_PLATE_MAIL_BODY(34, BODY),
	WHITE_PLATE_MAIL_BODY(35, BODY), // NO ITEM
	UNUSED_PLATE_MAIL_BODY(36, BODY), // NO ITEM (colour is between mithril & rune)
	BRONZE_PLATE_MAIL_LEGS(37, LEGS),
	IRON_PLATE_MAIL_LEGS(38, LEGS),
	STEEL_PLATE_MAIL_LEGS(39, LEGS),
	MITHRIL_PLATE_MAIL_LEGS(40, LEGS),
	ADAMANTITE_PLATE_MAIL_LEGS(41, LEGS),
	RUNE_PLATE_MAIL_LEGS(42, LEGS),
	BLACK_PLATE_MAIL_LEGS(43, LEGS),
	WHITE_PLATE_MAIL_LEGS(44, LEGS), // NO ITEM
	UNUSED_PLATE_MAIL_LEGS(45, LEGS), // NO ITEM (colour is between mithril & rune)
	LEATHER_ARMOUR(46, BODY),
	LEATHER_GLOVES(47, GLOVES),
	BRONZE_SWORD(48, WEAPON),
	IRON_SWORD(49, WEAPON),
	STEEL_SWORD(50, WEAPON),
	MITHRIL_SWORD(51, WEAPON),
	ADAMANTITE_SWORD(52, WEAPON),
	RUNE_SWORD(53, WEAPON),
	BLACK_SWORD(54, WEAPON),
	FEMALE_BRONZE_PLATE_MAIL_TOP(55, BODY),
	FEMALE_IRON_PLATE_MAIL_TOP(56, BODY),
	FEMALE_STEEL_PLATE_MAIL_TOP(57, BODY),
	FEMALE_MITHRIL_PLATE_MAIL_TOP(58, BODY),
	FEMALE_ADAMANTITE_PLATE_MAIL_TOP(59, BODY),
	FEMALE_RUNE_PLATE_MAIL_TOP(60, BODY),
	FEMALE_BLACK_PLATE_MAIL_TOP(61, BODY),
	WHITE_APRON_2(62, AMULET), // TODO: why?
	RED_CAPE(63, CAPE),
	BLACK_CAPE(64, CAPE),
	BLUE_CAPE(65, CAPE),
	GREEN_CAPE(66, CAPE),
	YELLOW_CAPE(67, CAPE),
	ORANGE_CAPE(68, CAPE),
	PURPLE_CAPE(69, CAPE),
	MEDIUM_BRONZE_HELMET(70, HAT),
	MEDIUM_IRON_HELMET(71, HAT),
	MEDIUM_STEEL_HELMET(72, HAT),
	MEDIUM_MITHRIL_HELMET(73, HAT),
	MEDIUM_ADAMANTITE_HELMET(74, HAT),
	MEDIUM_RUNE_HELMET(75, HAT),
	MEDIUM_BLACK_HELMET(76, HAT),
	WIZARDS_ROBE(77, BODY),
	WIZARDSHAT(78, HAT),
	DARKWIZARDSHAT(79, HAT),
	SILVER_NECKLACE(80, AMULET),
	GOLD_NECKLACE(81, AMULET),
	BLUE_SKIRT(82, LEGS),
	DARKWIZARDS_ROBE(83, BODY),
	SARADOMIN_MONK_ROBE(84, BODY),
	ZAMORAK_MONK_ROBE(85, BODY),
	DRUID_ROBE(86, BODY),
	DRUID_SKIRT(87, LEGS),
	SARADOMIN_MONK_SKIRT(88, LEGS),
	BLACK_SKIRT(89, LEGS),
	PINK_SKIRT(90, LEGS),
	ZAMORAK_MONK_SKIRT(91, LEGS),
	BRONZE_PLATED_SKIRT(92, LEGS),
	IRON_PLATED_SKIRT(93, LEGS),
	STEEL_PLATED_SKIRT(94, LEGS),
	MITHRIL_PLATED_SKIRT(95, LEGS),
	ADAMANTITE_PLATED_SKIRT(96, LEGS),
	RUNE_PLATED_SKIRT(97, LEGS),
	BRONZE_SQUARE_SHIELD(98, SHIELD),
	IRON_SQUARE_SHIELD(99, SHIELD),
	STEEL_SQUARE_SHIELD(100, SHIELD),
	MITHRIL_SQUARE_SHIELD(101, SHIELD),
	ADAMANTITE_SQUARE_SHIELD(102, SHIELD),
	RUNE_SQUARE_SHIELD(103, SHIELD),
	BLACK_SQUARE_SHIELD(104, SHIELD),
	ANTI_DRAGON_BREATH_SHIELD(105, SHIELD),
	WOODEN_SHIELD(106, SHIELD),
	CROSSBOW(107, WEAPON),
	LONGBOW(108, WEAPON),
	BRONZE_BATTLEAXE(109, WEAPON),
	IRON_BATTLEAXE(110, WEAPON),
	STEEL_BATTLEAXE(111, WEAPON),
	MITHRIL_BATTLEAXE(112, WEAPON),
	ADAMANTITE_BATTLEAXE(113, WEAPON),
	RUNE_BATTLEAXE(114, WEAPON),
	BLACK_BATTLEAXE(115, WEAPON),
	BRONZE_MACE(116, WEAPON),
	IRON_MACE(117, WEAPON),
	STEEL_MACE(118, WEAPON),
	MITHRIL_MACE(119, WEAPON),
	ADAMANTITE_MACE(120, WEAPON),
	RUNE_MACE(121, WEAPON),
	BLACK_MACE(122, WEAPON),
	STAFF(123, WEAPON),
	RAT(124, NPC),
	DEMON(125, NPC),
	SPIDER(126, NPC),
	RED_SPIDER(127, NPC),
	CAMEL(128, NPC),
	COW(129, NPC),
	SHEEP(130, NPC),
	UNICORN(131, NPC),
	BEAR(132, NPC),
	CHICKEN(133, NPC),
	SKELETON(134, NPC),
	SKELETON_SCIMITAR_AND_SHIELD(135, WEAPON), // NO ITEM
	ZOMBIE(136, NPC),
	ZOMBIE_AXE(137, WEAPON), // NO ITEM
	GHOST(138, NPC),
	BAT(139, NPC),
	GOBLIN(140, NPC),
	GOBLIN_WITH_RED_ARMOUR(141, NPC),
	GOBLIN_WITH_GREEN_ARMOUR(142, NPC),
	GOBLIN_SPEAR(143, WEAPON), // NO ITEM
	SCORPION(144, NPC),
	ELVARG(145, NPC),
	RED_DRAGON(146, NPC),
	BLUE_DRAGON(147, NPC),
	WHITE_WOLF(148, NPC),
	GREY_WOLF(149, NPC),
	RED_PARTY_HAT(150, HAT),
	YELLOW_PARTY_HAT(151, HAT),
	BLUE_PARTY_HAT(152, HAT),
	GREEN_PARTY_HAT(153, HAT),
	PINK_PARTY_HAT(154, HAT),
	WHITE_PARTY_HAT(155, HAT),
	ICE_GLOVES(156, GLOVES), // very slightly darker than regular gloves
	FIREBIRD(157, NPC),
	UNUSED_PLATE_MAIL_TOP(158, BODY), // NO ITEM (COLOUR_BETWEEN_MITHRIL_&_RUNE)
	SHADOW_WARRIOR_SKIRT(159, LEGS), // TODO: not sure this is actually shadow warrior
	GUARD_DOG(160, NPC),
	ICE_SPIDER(161, NPC),
	DRAGON_BATTLEAXE(162, WEAPON),
	DRAGON_SWORD(163, WEAPON),
	RIGHT_EYEPATCH(164, HAT),
	BLACK_DEMON(165, NPC),
	BLACK_DRAGON(166, NPC),
	POISON_SPIDER(167, NPC),
	HELLHOUND(168, NPC),
	BLACK_UNICORN(169, NPC),
	CHRONOZON(170, NPC),
	SHADOW_SPIDER(171, NPC),
	PENDANT_OF_LUCIEN(172, AMULET),
	DUNGEON_RAT(173, NPC),
	KHAZARD_HELMET(174, HAT),
	KHAZARD_CHAINMAIL(175, BODY),
	ZAMORAK_WIZARDSHAT(176, HAT), // NO ITEM, Used on some gnomes
	MOURNER_LEGS(177, PANTS),
	GAS_MASK(178, HAT),
	DRAGON_MEDIUM_HELMET(179, HAT),
	JUNGLE_SPIDER(180, NPC),
	SPEAR(181, WEAPON),
	GREEN_HALLOWEEN_MASK(182, HAT),
	PRIEST_ROBE(183, BODY),
	PRIEST_GOWN(184, LEGS),
	RED_HALLOWEEN_MASK(185, HAT),
	BLUE_HALLOWEEN_MASK(186, HAT),
	PASTEL_PINK_GNOME_SKIRT(187, LEGS),
	PASTEL_GREEN_GNOME_SKIRT(188, LEGS),
	PASTEL_BLUE_GNOME_SKIRT(189, LEGS),
	PASTEL_YELLOW_GNOME_SKIRT(190, LEGS),
	PASTEL_CYAN_GNOME_SKIRT(191, LEGS),
	PASTEL_PINK_GNOMESHAT(192, HAT),
	PASTEL_GREEN_GNOMESHAT(193, HAT),
	PASTEL_BLUE_GNOMESHAT(194, HAT),
	PASTEL_YELLOW_GNOMESHAT(195, HAT),
	PASTEL_CYAN_GNOMESHAT(196, HAT),
	PASTEL_PINK_GNOME_TOP(197, BODY),
	PASTEL_GREEN_GNOME_TOP(198, BODY),
	PASTEL_BLUE_GNOME_TOP(199, BODY),
	PASTEL_YELLOW_GNOME_TOP(200, BODY),
	PASTEL_CYAN_GNOME_TOP(201, BODY),
	GREEN_ROBE(202, BODY), // NO ITEM (Unused, perhaps planned for Brimstail?)
	GREEN_SKIRT(203, LEGS), // NO ITEM (Unused, perhaps planned for Brimstail?)
	PASTEL_PINK_GNOME_BOOTS(204, BOOTS),
	PASTEL_GREEN_GNOME_BOOTS(205, BOOTS),
	PASTEL_BLUE_GNOME_BOOTS(206, BOOTS),
	PASTEL_YELLOW_GNOME_BOOTS(207, BOOTS),
	PASTEL_CYAN_GNOME_BOOTS(208, BOOTS),
	SANTA_HAT(209, HAT),
	STAFF_OF_IBAN(210, WEAPON),
	SOULESS(211, NPC),
	DESERT_BOOTS(212, BOOTS),
	WHITE_PANTS(213, PANTS),
	SLAVES_ROBE_TOP(214, BODY),
	SLAVES_ROBE_BOTTOM(215, LEGS),
	AL_SHABIM_CAPE(216, CAPE), // Think this is identical in appearance to a Cape of Legends
	DESERT_WOLF(217, NPC),
	BUNNY_EARS(218, HAT),
	STAFF_OF_SARADOMIN(219, WEAPON),
	GUJOU_RUNE_SPEAR(220, WEAPON), // NO ITEM (no, not even rune spear)
	GUJUO_SKIRT(221, LEGS),
	GUJUO_ROBE_TOP(222, BODY),
	KARAMJA_WOLF(223, NPC),
	OOMLIE_BIRD(224, NPC),
	DRAGON_SQUARE_SHIELD(225, SHIELD),
	CAPE_OF_LEGENDS(226, CAPE),
	SHADOW_WARRIOR_BOOTS(227, BOOTS),
	SHADOW_WARRIOR_ROBE(228, BODY),
	SCYTHE(229, WEAPON), // End of authentic animation sprites
	BUNNY(473, NPC),
	DUCK(474, NPC),
	BUNNY_MORPH(475, MORPHING_RING),
	EGG_MORPH(476, MORPHING_RING),

	DEATH_MASK(484, HAT),
	YOYO_IN_HAND(485, WEAPON),
	YOYO_UP_DOWN_ANIM1(486, WEAPON),
	YOYO_UP_DOWN_ANIM2(487, WEAPON),
	YOYO_UP_DOWN_ANIM3(488, WEAPON),
	YOYO_UP_DOWN_ANIM4(489, WEAPON),
	YOYO_CRAZY_1_OCLOCK(490, WEAPON),
	YOYO_CRAZY_2_OCLOCK(491, WEAPON),
	YOYO_CRAZY_3_OCLOCK(492, WEAPON),
	YOYO_CRAZY_4_OCLOCK(493, WEAPON),
	YOYO_CRAZY_5_OCLOCK(494, WEAPON),
	YOYO_CRAZY_7_OCLOCK(495, WEAPON),
	YOYO_CRAZY_8_OCLOCK(496, WEAPON),
	YOYO_CRAZY_9_OCLOCK(497, WEAPON),
	YOYO_CRAZY_10_OCLOCK(498, WEAPON),
	YOYO_CRAZY_11_OCLOCK(499, WEAPON),
	YOYO_CRAZY_12_OCLOCK(500, WEAPON);

	private int appearanceId;
	private int wieldPosition;

	private static final Map<Integer, AppearanceId> byId = new HashMap<>();

	static {
		for (AppearanceId appearance : AppearanceId.values()) {
			if (byId.put(appearance.id(), appearance) != null) {
				throw new IllegalArgumentException("duplicate id: " + appearance.id());
			}
		}
	}

	public static AppearanceId getById(Integer id) {
		return byId.getOrDefault(id, AppearanceId.NOTHING);
	}

	/**
	 * @param appearanceId The ID of the animation.
	 */
	AppearanceId(int appearanceId, int wieldPosition) {
		this.appearanceId = appearanceId;
		this.wieldPosition = wieldPosition;
	}

	/**
	 * @return The animations ID
	 */
	public int id() {
		return appearanceId;
	}

	public int getSuggestedWieldPosition() {
		return wieldPosition;
	}

	public static int maximumAnimationSprite(int clientVersion) {
		switch (clientVersion) {
			case 38:
				return SCORPION.id();
			case 140:
				return KHAZARD_CHAINMAIL.id();
			case 177:
			case 235:
				return SCYTHE.id();
			default:
				return Integer.MAX_VALUE;
		}
	}

	public static final int SLOT_NPC = -2;
	public static final int SLOT_ANY = -1;
	public static final int SLOT_HEAD = 0;
	public static final int SLOT_SHIRT = 1;
	public static final int SLOT_PANTS = 2;
	public static final int SLOT_SHIELD = 3;
	public static final int SLOT_WEAPON = 4;
	public static final int SLOT_HAT = 5;
	public static final int SLOT_BODY = 6;
	public static final int SLOT_LEGS = 7;
	public static final int SLOT_GLOVES = 8;
	public static final int SLOT_BOOTS = 9;
	public static final int SLOT_AMULET = 10;
	public static final int SLOT_CAPE = 11;
	public static final int SLOT_ARROW = 12;
	public static final int SLOT_MORPHING_RING = 13;
}
