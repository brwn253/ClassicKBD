package com.openrsc.server.model.entity.player;

import com.openrsc.server.net.rsc.ActionSender;

public class Prayers {

	public static int THICK_SKIN = 0, BURST_OF_STRENGTH = 1, CLARITY_OF_THOUGHT = 2, ROCK_SKIN = 3,
		SUPERHUMAN_STRENGTH = 4, IMPROVED_REFLEXES = 5, RAPID_RESTORE = 6, RAPID_HEAL = 7, PROTECT_ITEMS = 8,
		STEEL_SKIN = 9, ULTIMATE_STRENGTH = 10, INCREDIBLE_REFLEXES = 11, PARALYZE_MONSTER = 12,
		PROTECT_FROM_MISSILES = 13, SUPER_RANGE = 14, SUPER_MAGE = 15, INVULNERABLE = 16, ULTIMATE_RESTORE = 17,
        ULTIMATE_HEAL = 18, ULTIMATE_RANGE = 19, ULTIMATE_MAGE = 20, WEAKEN_BOSS = 20, PROTECT_MORE_ITEMS = 21;

	private Player player;
	private boolean[] activatedPrayers = new boolean[14];

	public Prayers(Player player) {
		this.player = player;
	}

	public boolean isPrayerActivated(int pID) {
		return activatedPrayers[pID];
	}

	public boolean[] getActivePrayers() {
	    return activatedPrayers;
    }

	public void setPrayer(int pID, boolean b) {
		activatedPrayers[pID] = b;
		ActionSender.sendPrayers(player, activatedPrayers);
	}

	public void resetPrayers() {
		for (int x = 0; x < activatedPrayers.length; x++) {
			if (activatedPrayers[x]) {
				activatedPrayers[x] = false;
			}
		}
		ActionSender.sendPrayers(player, activatedPrayers);
	}
}
