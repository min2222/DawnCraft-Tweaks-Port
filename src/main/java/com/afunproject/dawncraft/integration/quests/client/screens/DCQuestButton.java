package com.afunproject.dawncraft.integration.quests.client.screens;

import java.util.List;

import com.feywild.quest_giver.screen.button.QuestButton;
import com.google.common.collect.Lists;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class DCQuestButton extends QuestButton {

	protected final QuestScreen parent;
	protected final List<Page> pages = Lists.newArrayList();

	public DCQuestButton(int x, int y, QuestScreen parent, String text, Button back_button) {
		super(x, y, true, parent.entity.blockPosition(), Component.translatable(text + ".title"), (button)->{});
		this.parent = parent;
		pages.addAll(QuestScreen.generatePages(parent, Component.translatable(text + ".text")));
		if (pages.size() > 0) ((TextPage)pages.get(pages.size()-1)).addWidget(back_button);
	}

	@Override
	public void onPress() {
		super.onPress();
		parent.pages.addAll(pages);
		parent.pageIndex++;
	}
}
