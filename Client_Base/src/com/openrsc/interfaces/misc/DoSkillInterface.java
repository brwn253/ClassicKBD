package com.openrsc.interfaces.misc;

import com.openrsc.client.entityhandling.EntityHandler;
import com.openrsc.client.entityhandling.defs.ItemDef;
import orsc.graphics.gui.Panel;
import orsc.mudclient;

import java.util.ArrayList;


public final class DoSkillInterface {
	public Panel doSkillPanel;
	int itemSelected = -1, rightClickMenuX = 0, rightClickMenuY = 0;
	int width = 430;
	int height = 320;
	private ArrayList<DoSkillItem> doSkillItems;
	private String skillToDo, skillDoing;
	private String title = "";
	private boolean visible, rightClickMenu = false;
	private mudclient mc;
	private int panelColour, textColour, bordColour;
	private int x, y, autoHeight;

	public DoSkillInterface(mudclient mc) {
		this.mc = mc;
		skillToDo = mc.getSkillToDo();

		doSkillPanel = new Panel(mc.getSurface(), 15);

		x = (mc.getGameWidth() - width) / 2;
		y = (mc.getGameHeight() - height) / 2;

		doSkillItems = new ArrayList<DoSkillItem>();
	}

	public void reposition() {
		x = (mc.getGameWidth() - width) / 2;

		if (autoHeight == 0) {
			y = (mc.getGameHeight() - height) / 2;
		} else {
			y = (mc.getGameHeight() - autoHeight) / 2;
		}
	}

	public void onRender() {
		reposition();

		panelColour = 0x989898;
		textColour = 0xFFFFFF;
		bordColour = 0x000000;

		doSkillPanel.handleMouse(mc.getMouseX(), mc.getMouseY(), mc.getMouseButtonDown(), mc.getLastMouseDown());

		// Draws the background
		mc.getSurface().drawBoxAlpha(x, y, width, autoHeight - y, panelColour, 160);
		mc.getSurface().drawBoxBorder(x, width, y, autoHeight - y, bordColour);

		// Draws the title
		drawStringCentered(title.isEmpty() ? mc.getSkillToDo() : title, x, y + 28, 5, textColour);

		int itemAmount = doSkillItems.size();
		switch (itemAmount) {
			case 1:
				width = mc.getSurface().stringWidth(5, title) + 80;
				break;
			case 2:
				width = mc.getSurface().stringWidth(5, title) + 80;
				break;
			case 3:
				width = 320;
				break;
			default:
				width = 430;
				break;
		}
		reposition();

		this.drawButton(x + width - 35, y + 5, 30, 30, "X", 5, false, new ButtonHandler() {
			@Override
			void handle() {
				setVisible(false);
			}
		});

		mc.getSurface().drawLineHoriz(x + 1, y + 40, width - 2, 0);

		drawSkillItems();
	}

	public void drawSkillItems() {
		reposition();

		// Gets all items in the list for what skill activity was chosen
		populateSkillItems();

		int curX = x + 30, spriteY = y + 45, textY = spriteY + 45;
		if (doSkillItems.size() == 1) {
			curX = x + width / 2 - 30;
		}

		// Determines if the text takes two lines
		boolean lotsaText = false;

		for (int i = 0; i < doSkillItems.size(); i++) {
			if (i >= 100) {
				break;
			}

			DoSkillItem curItem = doSkillItems.get(i);
			ItemDef def = EntityHandler.getItemDef(curItem.getItemID());
			int levelReq = Integer.parseInt(curItem.getLevelReq());
			String skillDetail = curItem.getSkillDetail();
			if (skillDetail.isEmpty()) {
				skillDetail = EntityHandler.getItemDef(curItem.getItemID()).getName();
			}

			lotsaText = skillDetail.length() >= 15;

			mc.getSurface().drawSpriteClipping(mc.spriteSelect(def),
				curX, spriteY, 48, 32, def.getPictureMask(), 0,
				def.getBlueMask(),false, 0, 1);

			int stringWidth = drawStringWrapped(skillDetail, curX, textY, 2, textColour);

			// Different size highlight box based on if there is text, and the length of the text
			int boxWidth = skillDetail.isEmpty() ? 48 : (stringWidth < 48 ? 54 : stringWidth + 10);
			int boxHeight = skillDetail.isEmpty() ? 38 : (lotsaText ? 64 : 52);
			int boxColor = 16711680;

			// Grays out box if player does not have required level to do
			if (mc.getPlayerStatCurrent(skillDoing.equals("Cooking") ? 7 : skillDoing.equals("Fletching") ? 9 : skillDoing.equals("Crafting") ? 12 : skillDoing.equals("Smithing") ? 13 : 15) < levelReq) {
				boxColor = 0x000000;
			}

			if (mc.getMouseX() >= curX - (boxWidth - 48) / 2 && mc.getMouseX() <= boxWidth + curX - (boxWidth - 48) / 2 && mc.getMouseY() >= spriteY - 2 && mc.getMouseY() <= spriteY + boxHeight
				&& !rightClickMenu) {
				mc.getSurface().drawBoxAlpha(curX - (boxWidth - 48) / 2, spriteY - 2, boxWidth, boxHeight, boxColor, 92);
				if (mc.mouseButtonClick == 1 && boxColor != 0x000000) {
					setVisible(false);
					itemSelected = curItem.getItemID();
					// send make all
					mc.setMouseClick(0);
				}
				if (mc.mouseButtonClick == 2 && boxColor != 0x000000) {
					rightClickMenuX = mc.getMouseX() - 10;
					rightClickMenuY = mc.getMouseY() - 10;
					itemSelected = curItem.getItemID();
					rightClickMenu = true;
					mc.setMouseClick(0);
				}
			}

			autoHeight = lotsaText ? spriteY + 70 : spriteY + 55;

			if (((i + 1) % 4) == 0) {
				curX = x + 30;
				spriteY += lotsaText ? 65 : 55;
				textY += lotsaText ? 65 : 55;
			} else {
				curX += 105;
			}
		}

		if (itemSelected >= 0) {
			String itemName = EntityHandler.getItemDef(itemSelected).getName();
			int menuWidth = mc.getSurface().stringWidth(2, itemName) > mc.getSurface().stringWidth(2, "Make-All") ? mc.getSurface().stringWidth(2, itemName) + 5 : mc.getSurface().stringWidth(2, "Make-All") + 5;
			int menuHeight = 105;

			if (rightClickMenuX + menuWidth > mc.getGameWidth()) {
				rightClickMenuX = mc.getGameWidth() - menuWidth - 5;
			}
			if (rightClickMenuY + menuHeight > mc.getGameHeight()) {
				rightClickMenuY = mc.getGameHeight() - menuHeight - 5;
			}

			if (mc.getMouseX() >= rightClickMenuX && mc.getMouseX() <= rightClickMenuX + menuWidth && mc.getMouseY() >= rightClickMenuY && mc.getMouseY() <= rightClickMenuY + menuHeight) {
				if (rightClickMenu) {
					mc.getSurface().drawBoxAlpha(rightClickMenuX, rightClickMenuY, menuWidth, 15, 0x000000, 255);
					mc.getSurface().drawBoxAlpha(rightClickMenuX, rightClickMenuY + 15, menuWidth, menuHeight - 15, 0x5C5548, 255);
					mc.getSurface().drawBoxBorder(rightClickMenuX, menuWidth, rightClickMenuY, menuHeight, 0x000000);


					drawString(itemName, rightClickMenuX + 1, rightClickMenuY + 11, 2, 0xFFFFFF);

					int hovering = 0;
					for (int f = 1; f <= 6; f++) {
						if (mc.getMouseX() >= rightClickMenuX + 1 && mc.getMouseX() < rightClickMenuX + menuWidth && mc.getMouseY() > rightClickMenuY + 11 + (f - 1) * 15 && mc.getMouseY() <= rightClickMenuY + 11 + f * 15) {
							hovering = f;
							if (mc.mouseButtonClick == 1) {
								System.out.println("Send make: " + f);
								switch (hovering) {
									case 1:
										;// send make 1
									case 2:
										;// send make 5
									case 3:
										;// send make 10
									case 4:
										;// send make x
									case 5:
										;// send make all
									case 6:
										rightClickMenu = false;
										break;
									default:
										break;
								}
							}
						}

						switch (f) {
							case 1:
								drawString("Make-1", rightClickMenuX + 1, rightClickMenuY + 26, 2, hovering == 1 ? 0xFF0000 : 0xFFFFFF);
							case 2:
								drawString("Make-5", rightClickMenuX + 1, rightClickMenuY + 41, 2, hovering == 2 ? 0xFF0000 : 0xFFFFFF);
							case 3:
								drawString("Make-10", rightClickMenuX + 1, rightClickMenuY + 56, 2, hovering == 3 ? 0xFF0000 : 0xFFFFFF);
							case 4:
								drawString("Make-X", rightClickMenuX + 1, rightClickMenuY + 71, 2, hovering == 4 ? 0xFF0000 : 0xFFFFFF);
							case 5:
								drawString("Make-All", rightClickMenuX + 1, rightClickMenuY + 86, 2, hovering == 5 ? 0xFF0000 : 0xFFFFFF);
							case 6:
								drawString("Cancel", rightClickMenuX + 1, rightClickMenuY + 101, 2, hovering == 6 ? 0xFF0000 : 0xFFFFFF);
								break;
							default:
								break;
						}
					}
				}
			} else {
				rightClickMenu = false;
			}
		}

		doSkillPanel.drawPanel();
	}

	public void drawString(String str, int x, int y, int font, int color) {
		mc.getSurface().drawString(str, x, y, color, font);
	}

	public void drawStringCentered(String str, int x, int y, int font, int color) {
		int stringWid = mc.getSurface().stringWidth(font, str);
		mc.getSurface().drawString(str, x + (width / 2) - (stringWid / 2) - 2, y, color, font);
	}

	public int drawStringWrapped(String text, int x, int y, int font, int color) {
		int strWidth = mc.getSurface().stringWidth(font, text);
		if (text.length() >= 15) {
			String text1 = text.substring(0, 15);
			text1 = text.substring(0, text1.lastIndexOf(" "));
			int strWidth1 = mc.getSurface().stringWidth(font, text1);
			drawString(text1, x + 24 - (strWidth1 / 2), y, font, color);

			String text2 = text.substring(text1.lastIndexOf(" ") + 1);
			text2 = text2.substring(text2.indexOf(" ") + 1);
			int strWidth2 = mc.getSurface().stringWidth(font, text2);
			drawString(text2, x + 24 - (strWidth2 / 2), y + 15, font, color);
			return text1.length() >= text2.length() ? strWidth1 : strWidth2;
		} else {
			drawString(text, x + 24 - (strWidth / 2), y, font, color);
			return strWidth;
		}
	}

	private void drawButton(int x, int y, int width, int height, String text, int font, boolean checked, ButtonHandler handler) {
		int bgBtnColour = 0x333333; // grey
		if (checked) {
			bgBtnColour = 16711680; // red
		}
		if (mc.getMouseX() >= x && mc.getMouseY() >= y && mc.getMouseX() <= x + width && mc.getMouseY() <= y + height) {
			if (!checked)
				bgBtnColour = 16711680; // blue
			if (mc.getMouseClick() == 1) {
				handler.handle();
				mc.setMouseClick(0);
			}
		}
		mc.getSurface().drawBoxAlpha(x, y, width, height, bgBtnColour, 192);
		mc.getSurface().drawBoxBorder(x, width, y, height, 0x242424);
		mc.getSurface().drawString(text, x + (width / 2) - (mc.getSurface().stringWidth(font, text) / 2) - 1, y + height / 2 + 5, textColour, font);
	}

	public void populateSkillItems() {
		doSkillItems.clear();
		skillToDo = mc.getSkillToDo();
		if (skillToDo.equals("Leather")) {
			title = "Choose a leather item to craft";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(16, "1", ""));
			doSkillItems.add(new DoSkillItem(17, "7", ""));
			doSkillItems.add(new DoSkillItem(15, "14", ""));
			doSkillItems.add(new DoSkillItem(1713, "28", ""));
			doSkillItems.add(new DoSkillItem(1714, "26", ""));
			doSkillItems.add(new DoSkillItem(1715, "24", ""));
			doSkillItems.add(new DoSkillItem(1716, "22", ""));
			doSkillItems.add(new DoSkillItem(1717, "20", ""));
			doSkillItems.add(new DoSkillItem(1719, "108", ""));
			doSkillItems.add(new DoSkillItem(1720, "106", ""));
			doSkillItems.add(new DoSkillItem(1721, "104", ""));
			doSkillItems.add(new DoSkillItem(1722, "102", ""));
			doSkillItems.add(new DoSkillItem(1723, "100", ""));
			doSkillItems.add(new DoSkillItem(1725, "128", ""));
			doSkillItems.add(new DoSkillItem(1726, "126", ""));
			doSkillItems.add(new DoSkillItem(1727, "124", ""));
			doSkillItems.add(new DoSkillItem(1728, "122", ""));
			doSkillItems.add(new DoSkillItem(1729, "120", ""));
			doSkillItems.add(new DoSkillItem(1731, "138", ""));
			doSkillItems.add(new DoSkillItem(1732, "136", ""));
			doSkillItems.add(new DoSkillItem(1733, "134", ""));
			doSkillItems.add(new DoSkillItem(1734, "132", ""));
			doSkillItems.add(new DoSkillItem(1735, "130", ""));
			doSkillItems.add(new DoSkillItem(1737, "148", ""));
			doSkillItems.add(new DoSkillItem(1738, "146", ""));
			doSkillItems.add(new DoSkillItem(1739, "144", ""));
			doSkillItems.add(new DoSkillItem(1740, "142", ""));
			doSkillItems.add(new DoSkillItem(1741, "140", ""));
		} else if (skillToDo.equals("Clay")) {
			title = "Choose a clay item to craft";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(135, "1", ""));
			doSkillItems.add(new DoSkillItem(251, "4", ""));
			doSkillItems.add(new DoSkillItem(341, "7", ""));
		} else if (skillToDo.equals("Spin")) {
			title = "Choose an item to spin";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(207, "1", "Wool"));
			doSkillItems.add(new DoSkillItem(676, "10", "Flax"));
			doSkillItems.add(new DoSkillItem(1743, "20", "Snakeweed"));
			doSkillItems.add(new DoSkillItem(1744, "60", "Ardrigal"));
			doSkillItems.add(new DoSkillItem(1745, "100", "Sito Foil"));
			doSkillItems.add(new DoSkillItem(1746, "120", "Volencia Moss"));
			doSkillItems.add(new DoSkillItem(1747, "140", "Rogues Purse"));
		} else if (skillToDo.equals("Glass")) {
			title = "Choose a glass item to blow";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(623, "1", ""));
			doSkillItems.add(new DoSkillItem(620, "3", ""));
			doSkillItems.add(new DoSkillItem(1018, "10", ""));
			doSkillItems.add(new DoSkillItem(465, "33", ""));
			doSkillItems.add(new DoSkillItem(611, "46", ""));
		} else if (skillToDo.equals("Silver")) {
			title = "Choose a jewellry piece to craft";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(44, "16", ""));
			doSkillItems.add(new DoSkillItem(1027, "17", ""));
		} else if (skillToDo.equals("Gold")) {
			title = "Choose a jewellry piece to craft";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(283, "5", ""));
			doSkillItems.add(new DoSkillItem(288, "6", ""));
			doSkillItems.add(new DoSkillItem(296, "8", ""));
			doSkillItems.add(new DoSkillItem(284, "8", ""));
			doSkillItems.add(new DoSkillItem(289, "10", ""));
			doSkillItems.add(new DoSkillItem(297, "13", ""));
			doSkillItems.add(new DoSkillItem(285, "18", ""));
			doSkillItems.add(new DoSkillItem(290, "24", ""));
			doSkillItems.add(new DoSkillItem(298, "30", ""));
			doSkillItems.add(new DoSkillItem(286, "30", ""));
			doSkillItems.add(new DoSkillItem(291, "40", ""));
			doSkillItems.add(new DoSkillItem(287, "42", ""));
			doSkillItems.add(new DoSkillItem(299, "50", ""));
			doSkillItems.add(new DoSkillItem(543, "55", ""));
			doSkillItems.add(new DoSkillItem(292, "56", ""));
			doSkillItems.add(new DoSkillItem(300, "70", ""));
			doSkillItems.add(new DoSkillItem(544, "72", ""));
			doSkillItems.add(new DoSkillItem(524, "80", ""));
			doSkillItems.add(new DoSkillItem(1321, "85", ""));  // Opal Ring 
			doSkillItems.add(new DoSkillItem(1692, "95", "")); // Opal Necklace 
			doSkillItems.add(new DoSkillItem(1699, "95", "")); // Opal Amulet 
			doSkillItems.add(new DoSkillItem(1696, "100", "")); // Topaz Ring 
			doSkillItems.add(new DoSkillItem(1693, "115", "")); // Topaz Necklace 
			doSkillItems.add(new DoSkillItem(1700, "120", "")); // Topaz Amulet >
			doSkillItems.add(new DoSkillItem(1697, "125", "")); // Jade Ring 
			doSkillItems.add(new DoSkillItem(1694, "130", "")); // Jade Necklace 
			doSkillItems.add(new DoSkillItem(1701, "135", "")); // Jade Amulet 
			doSkillItems.add(new DoSkillItem(1698, "140", "")); // Onyx Ring 
			doSkillItems.add(new DoSkillItem(1695, "145", "")); // Onyx Necklace 
			doSkillItems.add(new DoSkillItem(1702, "149", "")); // Onyx Amulet 
		} else if (skillToDo.equals("Battlestaff water")) {
			title = "Choose an amount to craft";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(616, "54", ""));
		} else if (skillToDo.equals("Battlestaff earth")) {
			title = "Choose an amount to craft";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(618, "58", ""));
		} else if (skillToDo.equals("Battlestaff fire")) {
			title = "Choose an amount to craft";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(615, "62", ""));
		} else if (skillToDo.equals("Battlestaff air")) {
			title = "Choose an amount to craft";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(617, "66", ""));
		} else if (skillToDo.equals("Snakeweed Staff")) {
			title = "Choose an amount to craft";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(1718, "30", ""));
		} else if (skillToDo.equals("Ardrigal Staff")) {
			title = "Choose an amount to craft";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(1724, "110", ""));
		} else if (skillToDo.equals("Sito Foil Staff")) {
			title = "Choose an amount to craft";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(1730, "130", ""));
		} else if (skillToDo.equals("Volencia Moss Staff")) {
			title = "Choose an amount to craft";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(1736, "140", ""));
		} else if (skillToDo.equals("Rogues Purse Staff")) {
			title = "Choose an amount to craft";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(1742, "149", ""));
		} else if (skillToDo.equals("Cut opal")) {
			title = "Choose an amount to cut";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(891, "1", ""));
		} else if (skillToDo.equals("Cut jade")) {
			title = "Choose an amount to cut";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(890, "13", ""));
		} else if (skillToDo.equals("Cut topaz")) {
			title = "Choose an amount to cut";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(889, "16", ""));
		} else if (skillToDo.equals("Cut sapphire")) {
			title = "Choose an amount to cut";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(160, "20", ""));
		} else if (skillToDo.equals("Cut emerald")) {
			title = "Choose an amount to cut";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(159, "27", ""));
		} else if (skillToDo.equals("Cut ruby")) {
			title = "Choose an amount to cut";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(158, "34", ""));
		} else if (skillToDo.equals("Cut diamond")) {
			title = "Choose an amount to cut";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(157, "43", ""));
		} else if (skillToDo.equals("Cut dragonstone")) {
			title = "Choose an amount to cut";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(542, "55", ""));
		} else if (skillToDo.equals("Cut onyx")) {
			title = "Choose an amount to cut";
			skillDoing = "Crafting";
			doSkillItems.add(new DoSkillItem(1711, "135", ""));
		} else if (skillToDo.equals("Smelt")) {
			title = "Choose a bar type to smelt";
			skillDoing = "Smithing";
			doSkillItems.add(new DoSkillItem(169, "1", "1 Tin ore & 1 copper ore"));
			doSkillItems.add(new DoSkillItem(170, "15", "1 Iron ore"));
			doSkillItems.add(new DoSkillItem(384, "20", "1 Silver ore"));
			doSkillItems.add(new DoSkillItem(171, "30", "2 Coal & 1 iron ore"));
			doSkillItems.add(new DoSkillItem(172, "40", "1 Gold ore"));
			doSkillItems.add(new DoSkillItem(173, "50", "4 Coal & 1 mithril ore"));
			doSkillItems.add(new DoSkillItem(174, "70", "6 Coal & 1 adamantite ore"));
			doSkillItems.add(new DoSkillItem(408, "85", "8 Coal & 1 runite ore"));
			doSkillItems.add(new DoSkillItem(1365, "100", "10 Coal & 1 dragon ore"));
			doSkillItems.add(new DoSkillItem(1567, "120", "12 Coal & 1 khorium ore"));
			doSkillItems.add(new DoSkillItem(1569, "130", "14 Coal & 1 platinum ore"));
			doSkillItems.add(new DoSkillItem(1571, "140", "16 Coal & 1 titanium ore"));
		} else if (skillToDo.equals("Bronze")) {
			title = "Choose a bronze item to smith";
			skillDoing = "Smithing";
			doSkillItems.add(new DoSkillItem(62, "1", "1 Bar"));
			doSkillItems.add(new DoSkillItem(87, "1", "1 Bar"));
			doSkillItems.add(new DoSkillItem(94, "1", "1 Bar"));
			doSkillItems.add(new DoSkillItem(104, "3", "1 Bar"));
			doSkillItems.add(new DoSkillItem(66, "4", "1 Bar"));
			doSkillItems.add(new DoSkillItem(1062, "4", "1 Bar makes 7"));
			doSkillItems.add(new DoSkillItem(979, "4", "1 Bar"));
			doSkillItems.add(new DoSkillItem(82, "5", "2 Bars"));
			doSkillItems.add(new DoSkillItem(669, "5", "1 Bar makes 10"));
			doSkillItems.add(new DoSkillItem(70, "6", "2 Bars"));
			doSkillItems.add(new DoSkillItem(108, "7", "2 Bars"));
			doSkillItems.add(new DoSkillItem(124, "8", "2 Bars"));
			doSkillItems.add(new DoSkillItem(205, "10", "3 Bars"));
			doSkillItems.add(new DoSkillItem(113, "11", "3 Bars"));
			doSkillItems.add(new DoSkillItem(128, "12", "3 Bars"));
			doSkillItems.add(new DoSkillItem(76, "14", "3 Bars"));
			doSkillItems.add(new DoSkillItem(206, "16", "3 Bars"));
			doSkillItems.add(new DoSkillItem(117, "18", "5 Bars"));
		} else if (skillToDo.equals("Iron")) {
			title = "Choose an iron item to smith";
			skillDoing = "Smithing";
			doSkillItems.add(new DoSkillItem(28, "15", "1 Bar"));
			doSkillItems.add(new DoSkillItem(12, "16", "1 Bar"));
			doSkillItems.add(new DoSkillItem(0, "17", "1 Bar"));
			doSkillItems.add(new DoSkillItem(5, "18", "1 Bar"));
			doSkillItems.add(new DoSkillItem(1, "19", "1 Bar"));
			doSkillItems.add(new DoSkillItem(1063, "19", "1 Bar makes 7"));
			doSkillItems.add(new DoSkillItem(83, "20", "2 Bars"));
			doSkillItems.add(new DoSkillItem(670, "20", "1 Bar makes 10"));
			doSkillItems.add(new DoSkillItem(71, "21", "2 Bars"));
			doSkillItems.add(new DoSkillItem(6, "22", "2 Bars"));
			doSkillItems.add(new DoSkillItem(3, "23", "2 Bars"));
			doSkillItems.add(new DoSkillItem(89, "25", "3 Bars"));
			doSkillItems.add(new DoSkillItem(7, "26", "3 Bars"));
			doSkillItems.add(new DoSkillItem(2, "27", "3 Bars"));
			doSkillItems.add(new DoSkillItem(77, "29", "3 Bars"));
			doSkillItems.add(new DoSkillItem(9, "31", "3 Bars"));
			doSkillItems.add(new DoSkillItem(8, "33", "5 Bars"));
		} else if (skillToDo.equals("Steel")) {
			title = "Choose a steel item to smith";
			skillDoing = "Smithing";
			doSkillItems.add(new DoSkillItem(63, "30", "1 Bar"));
			doSkillItems.add(new DoSkillItem(88, "31", "1 Bar"));
			doSkillItems.add(new DoSkillItem(95, "32", "1 Bar"));
			doSkillItems.add(new DoSkillItem(105, "33", "1 Bar"));
			doSkillItems.add(new DoSkillItem(67, "34", "1 Bar"));
			doSkillItems.add(new DoSkillItem(1064, "34", "1 Bar makes 7"));
			doSkillItems.add(new DoSkillItem(84, "35", "2 Bars"));
			doSkillItems.add(new DoSkillItem(671, "35", "1 Bar makes 10"));
			doSkillItems.add(new DoSkillItem(1041, "35", "1 Bar makes 2"));
			doSkillItems.add(new DoSkillItem(72, "36", "2 Bars"));
			doSkillItems.add(new DoSkillItem(109, "37", "2 Bars"));
			doSkillItems.add(new DoSkillItem(125, "38", "2 Bars"));
			doSkillItems.add(new DoSkillItem(90, "40", "3 Bars"));
			doSkillItems.add(new DoSkillItem(114, "41", "3 Bars"));
			doSkillItems.add(new DoSkillItem(129, "42", "3 Bars"));
			doSkillItems.add(new DoSkillItem(78, "44", "3 Bars"));
			doSkillItems.add(new DoSkillItem(121, "46", "3 Bars"));
			doSkillItems.add(new DoSkillItem(118, "48", "5 Bars"));
		} else if (skillToDo.equals("Mithril")) {
			title = "Choose a mithril item to smith";
			skillDoing = "Smithing";
			doSkillItems.add(new DoSkillItem(64, "50", "1 Bar"));
			doSkillItems.add(new DoSkillItem(203, "51", "1 Bar"));
			doSkillItems.add(new DoSkillItem(96, "52", "1 Bar"));
			doSkillItems.add(new DoSkillItem(106, "53", "1 Bar"));
			doSkillItems.add(new DoSkillItem(68, "54", "1 Bar"));
			doSkillItems.add(new DoSkillItem(1065, "54", "1 Bar makes 7"));
			doSkillItems.add(new DoSkillItem(85, "55", "2 Bars"));
			doSkillItems.add(new DoSkillItem(672, "55", "1 Bar makes 10"));
			doSkillItems.add(new DoSkillItem(73, "56", "2 Bars"));
			doSkillItems.add(new DoSkillItem(110, "57", "2 Bars"));
			doSkillItems.add(new DoSkillItem(126, "58", "2 Bars"));
			doSkillItems.add(new DoSkillItem(91, "60", "3 Bars"));
			doSkillItems.add(new DoSkillItem(115, "61", "3 Bars"));
			doSkillItems.add(new DoSkillItem(130, "62", "3 Bars"));
			doSkillItems.add(new DoSkillItem(79, "64", "3 Bars"));
			doSkillItems.add(new DoSkillItem(122, "66", "3 Bars"));
			doSkillItems.add(new DoSkillItem(119, "68", "5 Bars"));
		} else if (skillToDo.equals("Adamant")) {
			title = "Choose an adamant item to smith";
			skillDoing = "Smithing";
			doSkillItems.add(new DoSkillItem(65, "70", "1 Bar"));
			doSkillItems.add(new DoSkillItem(204, "71", "1 Bar"));
			doSkillItems.add(new DoSkillItem(97, "72", "1 Bar"));
			doSkillItems.add(new DoSkillItem(107, "73", "1 Bar"));
			doSkillItems.add(new DoSkillItem(69, "74", "1 Bar"));
			doSkillItems.add(new DoSkillItem(1066, "74", "1 Bar makes 7"));
			doSkillItems.add(new DoSkillItem(86, "75", "2 Bars"));
			doSkillItems.add(new DoSkillItem(673, "75", "1 Bar makes 10"));
			doSkillItems.add(new DoSkillItem(71, "76", "2 Bars"));
			doSkillItems.add(new DoSkillItem(111, "77", "2 Bars"));
			doSkillItems.add(new DoSkillItem(127, "78", "2 Bars"));
			doSkillItems.add(new DoSkillItem(92, "80", "3 Bars"));
			doSkillItems.add(new DoSkillItem(116, "81", "3 Bars"));
			doSkillItems.add(new DoSkillItem(131, "82", "3 Bars"));
			doSkillItems.add(new DoSkillItem(80, "84", "3 Bars"));
			doSkillItems.add(new DoSkillItem(123, "86", "3 Bars"));
			doSkillItems.add(new DoSkillItem(120, "88", "5 Bars"));
		} else if (skillToDo.equals("Rune")) {
			title = "Choose a rune item to smith";
			skillDoing = "Smithing";
			doSkillItems.add(new DoSkillItem(396, "85", "1 Bar"));
			doSkillItems.add(new DoSkillItem(405, "86", "1 Bar"));
			doSkillItems.add(new DoSkillItem(98, "87", "1 Bar"));
			doSkillItems.add(new DoSkillItem(399, "88", "1 Bar"));
			doSkillItems.add(new DoSkillItem(397, "89", "1 Bar"));
			doSkillItems.add(new DoSkillItem(1067, "89", "1 Bar makes 7"));
			doSkillItems.add(new DoSkillItem(398, "90", "2 Bars"));
			doSkillItems.add(new DoSkillItem(674, "90", "1 Bar makes 10"));
			doSkillItems.add(new DoSkillItem(75, "91", "2 Bars"));
			doSkillItems.add(new DoSkillItem(112, "92", "2 Bars"));
			doSkillItems.add(new DoSkillItem(403, "93", "2 Bars"));
			doSkillItems.add(new DoSkillItem(93, "95", "3 Bars"));
			doSkillItems.add(new DoSkillItem(400, "96", "3 Bars"));
			doSkillItems.add(new DoSkillItem(404, "97", "3 Bars"));
			doSkillItems.add(new DoSkillItem(81, "99", "3 Bars"));
			doSkillItems.add(new DoSkillItem(402, "99", "3 Bars"));
			doSkillItems.add(new DoSkillItem(401, "99", "5 Bars"));
		} else if (skillToDo.equals("Dragon")) {
			title = "Choose a dragon item to smith";
			skillDoing = "Smithing";
			doSkillItems.add(new DoSkillItem(1447, "102", "1 Bar")); // Dagger 
			doSkillItems.add(new DoSkillItem(1480, "103", "1 Bar")); // Axe 
			doSkillItems.add(new DoSkillItem(1561, "104", "1 Bar")); // Mace 
			doSkillItems.add(new DoSkillItem(795, "105", "1 Bar")); // Med 
			doSkillItems.add(new DoSkillItem(1562, "106", "1 Bar")); // Short 
			doSkillItems.add(new DoSkillItem(1585, "107", "1 Bar makes 7")); // Dart Tips 
			doSkillItems.add(new DoSkillItem(1563, "109", "2 Bars")); // Scimitar 
			doSkillItems.add(new DoSkillItem(1581, "108", "1 Bar makes 10")); // Arrowheads 
			doSkillItems.add(new DoSkillItem(593, "110", "2 Bars")); // Long 
			doSkillItems.add(new DoSkillItem(1425, "111", "2 Bars")); // Large 
			doSkillItems.add(new DoSkillItem(1278, "113", "2 Bars")); // SQ 
			doSkillItems.add(new DoSkillItem(594, "114", "3 Bars")); // Battle Axe 
			doSkillItems.add(new DoSkillItem(1367, "115", "3 Bars")); // Chain 
			doSkillItems.add(new DoSkillItem(1426, "116", "3 Bars")); // Kite 
			doSkillItems.add(new DoSkillItem(1346, "117", "3 Bars")); // 2 Hand 
			doSkillItems.add(new DoSkillItem(1429, "119", "3 Bars")); // Legs 
			doSkillItems.add(new DoSkillItem(1427, "120", "5 Bars")); // Plate 
			} else if (skillToDo.equals("Khorium")) {
			title = "Choose a khorium item to smith";
			skillDoing = "Smithing";
			doSkillItems.add(new DoSkillItem(1497, "120", "1 Bar")); // Dagger 
			doSkillItems.add(new DoSkillItem(1512, "120", "1 Bar")); // Axe 
			doSkillItems.add(new DoSkillItem(1518, "120", "1 Bar")); // Mace 
			doSkillItems.add(new DoSkillItem(1521, "120", "1 Bar")); // Med 
			doSkillItems.add(new DoSkillItem(1500, "121", "1 Bar")); // Short 
			doSkillItems.add(new DoSkillItem(1586, "120", "1 Bar makes 7")); // Dart Tips 
			doSkillItems.add(new DoSkillItem(1509, "122", "2 Bars")); // Scimitar 
			doSkillItems.add(new DoSkillItem(1582, "121", "1 Bar makes 10")); // Arrowheads 
			doSkillItems.add(new DoSkillItem(1503, "123", "2 Bars")); // Long 
			doSkillItems.add(new DoSkillItem(1524, "124", "2 Bars")); // Large 
			doSkillItems.add(new DoSkillItem(1536, "126", "2 Bars")); // SQ 
			doSkillItems.add(new DoSkillItem(1515, "127", "3 Bars")); // Battle Axe 
			doSkillItems.add(new DoSkillItem(1527, "128", "3 Bars")); // Chain 
			doSkillItems.add(new DoSkillItem(1539, "129", "3 Bars")); // Kite 
			doSkillItems.add(new DoSkillItem(1506, "129", "3 Bars")); // 2 Hand 
			doSkillItems.add(new DoSkillItem(1533, "130", "3 Bars")); // Legs 
			doSkillItems.add(new DoSkillItem(1530, "130", "5 Bars")); // Plate 
			} else if (skillToDo.equals("Platinum")) {
			title = "Choose a platinum item to smith";
			skillDoing = "Smithing";
			doSkillItems.add(new DoSkillItem(1498, "130", "1 Bar")); // Dagger 
			doSkillItems.add(new DoSkillItem(1513, "130", "1 Bar")); // Axe 
			doSkillItems.add(new DoSkillItem(1519, "130", "1 Bar")); // Mace 
			doSkillItems.add(new DoSkillItem(1522, "130", "1 Bar")); // Med 
			doSkillItems.add(new DoSkillItem(1501, "131", "1 Bar")); // Short 
			doSkillItems.add(new DoSkillItem(1587, "131", "1 Bar makes 7")); // Dart Tips 
			doSkillItems.add(new DoSkillItem(1510, "132", "2 Bars")); // Scimitar 
			doSkillItems.add(new DoSkillItem(1583, "131", "1 Bar makes 10")); // Arrowheads 
			doSkillItems.add(new DoSkillItem(1504, "133", "2 Bars")); // Long 
			doSkillItems.add(new DoSkillItem(1525, "134", "2 Bars")); // Large 
			doSkillItems.add(new DoSkillItem(1537, "136", "2 Bars")); // SQ 
			doSkillItems.add(new DoSkillItem(1516, "137", "3 Bars")); // Battle Axe 
			doSkillItems.add(new DoSkillItem(1528, "138", "3 Bars")); // Chain 
			doSkillItems.add(new DoSkillItem(1540, "139", "3 Bars")); // Kite 
			doSkillItems.add(new DoSkillItem(1507, "139", "3 Bars")); // 2 Hand 
			doSkillItems.add(new DoSkillItem(1534, "140", "3 Bars")); // Legs 
			doSkillItems.add(new DoSkillItem(1531, "140", "5 Bars")); // Plate 
			} else if (skillToDo.equals("Titanium")) {
			title = "Choose a titanium item to smith";
			skillDoing = "Smithing";
			doSkillItems.add(new DoSkillItem(1499, "140", "1 Bar")); // Dagger 
			doSkillItems.add(new DoSkillItem(1514, "140", "1 Bar")); // Axe 
			doSkillItems.add(new DoSkillItem(1520, "140", "1 Bar")); // Mace 
			doSkillItems.add(new DoSkillItem(1523, "140", "1 Bar")); // Med 
			doSkillItems.add(new DoSkillItem(1502, "141", "1 Bar")); // Short 
			doSkillItems.add(new DoSkillItem(1588, "142", "1 Bar makes 7")); // Dart Tips 
			doSkillItems.add(new DoSkillItem(1511, "144", "2 Bars")); // Scimitar 
			doSkillItems.add(new DoSkillItem(1584, "143", "1 Bar makes 10")); // Arrowheads 
			doSkillItems.add(new DoSkillItem(1505, "145", "2 Bars")); // Long 
			doSkillItems.add(new DoSkillItem(1526, "146", "2 Bars")); // Large 
			doSkillItems.add(new DoSkillItem(1538, "148", "2 Bars")); // SQ 
			doSkillItems.add(new DoSkillItem(1517, "148", "3 Bars")); // Battle Axe 
			doSkillItems.add(new DoSkillItem(1529, "148", "3 Bars")); // Chain 
			doSkillItems.add(new DoSkillItem(1541, "149", "3 Bars")); // Kite 
			doSkillItems.add(new DoSkillItem(1508, "149", "3 Bars")); // 2 Hand 
			doSkillItems.add(new DoSkillItem(1535, "150", "3 Bars")); // Legs 
			doSkillItems.add(new DoSkillItem(1532, "150", "5 Bars")); // Plate 
			} else if (skillToDo.equals("Fletch normal")) {
			title = "Choose an item to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(280, "1", ""));
			doSkillItems.add(new DoSkillItem(277, "5", ""));
			doSkillItems.add(new DoSkillItem(188, "10", ""));
		} else if (skillToDo.equals("Fletch oak")) {
			title = "Choose a bow to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(659, "20", ""));
			doSkillItems.add(new DoSkillItem(658, "25", ""));
		} else if (skillToDo.equals("Fletch willow")) {
			title = "Choose a bow to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(661, "35", ""));
			doSkillItems.add(new DoSkillItem(660, "40", ""));
		} else if (skillToDo.equals("Fletch maple")) {
			title = "Choose a bow to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(663, "50", ""));
			doSkillItems.add(new DoSkillItem(662, "55", ""));
		} else if (skillToDo.equals("Fletch yew")) {
			title = "Choose a bow to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(665, "65", ""));
			doSkillItems.add(new DoSkillItem(664, "70", ""));
		} else if (skillToDo.equals("Fletch magic")) {
			title = "Choose a bow to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(667, "80", ""));
			doSkillItems.add(new DoSkillItem(666, "85", ""));
		} else if (skillToDo.equals("Fletch mystic")) {
			title = "Choose a bow to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1622, "90", ""));
			doSkillItems.add(new DoSkillItem(1623, "97", ""));
		} else if (skillToDo.equals("Fletch cork")) {
			title = "Choose a bow to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1625, "122", ""));
			doSkillItems.add(new DoSkillItem(1626, "115", ""));
		} else if (skillToDo.equals("Fletch boojum")) {
			title = "Choose a bow to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1629, "137", ""));
			doSkillItems.add(new DoSkillItem(1630, "130", ""));
		} else if (skillToDo.equals("Fletch gum")) {
			title = "Choose a bow to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1633, "147", ""));
			doSkillItems.add(new DoSkillItem(1634, "145", ""));
		} else if (skillToDo.equals("Fletch twisted")) {
			title = "Choose a bow to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1637, "150", ""));
			doSkillItems.add(new DoSkillItem(1638, "149", ""));
		} else if (skillToDo.equals("String normal short")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(189, "5", ""));
		} else if (skillToDo.equals("String normal long")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(188, "10", ""));
		} else if (skillToDo.equals("String oak short")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(649, "20", ""));
		} else if (skillToDo.equals("String oak long")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(648, "25", ""));
		} else if (skillToDo.equals("String willow short")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(651, "35", ""));
		} else if (skillToDo.equals("String willow long")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(650, "40", ""));
		} else if (skillToDo.equals("String maple short")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(653, "50", ""));
		} else if (skillToDo.equals("String maple long")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(652, "55", ""));
		} else if (skillToDo.equals("String yew short")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(655, "65", ""));
		} else if (skillToDo.equals("String yew long")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(654, "70", ""));
		} else if (skillToDo.equals("String magic short")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(657, "80", ""));
		} else if (skillToDo.equals("String magic long")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(656, "85", ""));
		} 		else if (skillToDo.equals("String mystic short")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1624, "90", ""));
		} else if (skillToDo.equals("String mystic long")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1623, "97", ""));
		} else if (skillToDo.equals("String cork short")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1628, "115", ""));
		} else if (skillToDo.equals("String cork long")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1627, "122", ""));
		} else if (skillToDo.equals("String boojum short")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1632, "130", ""));
		} else if (skillToDo.equals("String boojum long")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1631, "137", ""));
		} else if (skillToDo.equals("String gum short")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1636, "145", ""));
		} else if (skillToDo.equals("String gum long")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1635, "147", ""));
		} else if (skillToDo.equals("String twisted short")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1640, "149", ""));
		} else if (skillToDo.equals("String twisted long")) {
			title = "Choose an amount to string";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1639, "150", ""));
		} else if (skillToDo.equals("Fletch headless arrows")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(637, "1", ""));
		} else if (skillToDo.equals("Fletch bronze arrows")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(11, "1", ""));
		} else if (skillToDo.equals("Fletch iron arrows")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(638, "15", ""));
		} else if (skillToDo.equals("Fletch steel arrows")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(640, "30", ""));
		} else if (skillToDo.equals("Fletch mithril arrows")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(642, "45", ""));
		} else if (skillToDo.equals("Fletch adamant arrows")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(644, "60", ""));
		} else if (skillToDo.equals("Fletch rune arrows")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(646, "75", ""));
		} else if (skillToDo.equals("Fletch dragon arrows")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1449, "105", ""));
		} else if (skillToDo.equals("Fletch khorium arrows")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1609, "120", ""));
		} else if (skillToDo.equals("Fletch platinum arrows")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1610, "135", ""));
		} else if (skillToDo.equals("Fletch titanium arrows")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1611, "145", ""));
		} else if (skillToDo.equals("Fletch bronze darts")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1013, "1", ""));
		} else if (skillToDo.equals("Fletch iron darts")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1015, "22", ""));
		} else if (skillToDo.equals("Fletch steel darts")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1024, "37", ""));
		} else if (skillToDo.equals("Fletch mithril darts")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1068, "52", ""));
		} else if (skillToDo.equals("Fletch adamant darts")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1069, "67", ""));
		} else if (skillToDo.equals("Fletch rune darts")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1070, "82", ""));
		} 		else if (skillToDo.equals("Fletch dragon darts")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1589, "105", ""));
		} else if (skillToDo.equals("Fletch khorium darts")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1590, "120", ""));
		} else if (skillToDo.equals("Fletch platinum darts")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1591, "135", ""));
		} else if (skillToDo.equals("Fletch titanium darts")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(1592, "145", ""));
		} else if (skillToDo.equals("Fletch pearl bolts")) {
			title = "Choose an amount to fletch";
			skillDoing = "Fletching";
			doSkillItems.add(new DoSkillItem(786, "34", ""));
		} else if (skillToDo.equals("Mix guam")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(454, "1", ""));
		} else if (skillToDo.equals("Mix marrentill")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(455, "1", ""));
		} else if (skillToDo.equals("Mix tarromin")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(456, "1", ""));
		} else if (skillToDo.equals("Mix harralander")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(457, "1", ""));
		} else if (skillToDo.equals("Mix ranarr")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(458, "1", ""));
		} else if (skillToDo.equals("Mix irit")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(459, "1", ""));
		} else if (skillToDo.equals("Mix avantoe")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(460, "1", ""));
		} else if (skillToDo.equals("Mix kwuarm")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(461, "1", ""));
		} else if (skillToDo.equals("Mix cadantine")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(462, "1", ""));
		} else if (skillToDo.equals("Mix dwarfweed")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(463, "1", ""));
		} else if (skillToDo.equals("Mix attack")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(474, "3", ""));
		} else if (skillToDo.equals("Mix cure poison")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(566, "5", ""));
		} else if (skillToDo.equals("Mix strength")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(222, "12", ""));
		} else if (skillToDo.equals("Mix stat restore")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(477, "22", ""));
		} else if (skillToDo.equals("Mix defense")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(480, "30", ""));
		} else if (skillToDo.equals("Mix prayer")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(483, "38", ""));
		} else if (skillToDo.equals("Mix super attack")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(486, "45", ""));
		} else if (skillToDo.equals("Mix poison antidote")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(569, "48", ""));
		} else if (skillToDo.equals("Mix fishing")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(489, "50", ""));
		} else if (skillToDo.equals("Mix super strength")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(492, "55", ""));
		} else if (skillToDo.equals("Mix weapon poison")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(572, "60", ""));
		} else if (skillToDo.equals("Mix super defense")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(495, "66", ""));
		} else if (skillToDo.equals("Mix ranging")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(498, "72", ""));
		} else if (skillToDo.equals("Mix zamorak")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(963, "78", ""));
		} else if (skillToDo.equals("Mix woodworking")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(1674, "90", ""));
		} else if (skillToDo.equals("Mix super prayer")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(1677, "122", ""));
		} else if (skillToDo.equals("Mix ultimate attack")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(1680, "130", ""));
		} else if (skillToDo.equals("Mix ultimate strength")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(1683, "138", ""));
		} else if (skillToDo.equals("Mix ultimate defense")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(1686, "140", ""));
		} else if (skillToDo.equals("Mix ultimate zamorak")) {
			title = "Choose an amount to mix";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(1689, "149", ""));
		} else if (skillToDo.equals("Grind horn")) {
			title = "Choose an amount to grind";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(473, "1", ""));
		} else if (skillToDo.equals("Grind scale")) {
			title = "Choose an amount to grind";
			skillDoing = "Herblaw";
			doSkillItems.add(new DoSkillItem(472, "1", ""));
		} else if (skillToDo.equals("Wine")) {
			title = "Choose an amount to create";
			skillDoing = "Cooking";
			doSkillItems.add(new DoSkillItem(142, "35", ""));
		} else {
			System.out.println("No doSkill matches");
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}

class DoSkillItem {

	private int itemID;
	private String levelReq, skillDetail;

	public DoSkillItem(int itemID, String levelReq, String skillDetail) {
		this.itemID = itemID;
		this.levelReq = levelReq;
		this.skillDetail = skillDetail;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getLevelReq() {
		return levelReq;
	}

	public void setLevelReq(String levelReq) {
		this.levelReq = levelReq;
	}

	public String getSkillDetail() {
		return skillDetail;
	}

	public void setSkillDetail(String skillDetail) {
		this.skillDetail = skillDetail;
	}
}
