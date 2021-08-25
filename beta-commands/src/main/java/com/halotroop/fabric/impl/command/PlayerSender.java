package com.halotroop.fabric.impl.command;

import com.halotroop.fabric.accessor.command.MinecraftAccessor;
import com.halotroop.fabric.api.command.Sender;
import net.minecraft.entity.player.PlayerEntity;

public class PlayerSender implements Sender {
	private final PlayerEntity player;

	public PlayerSender(PlayerEntity player) {
		this.player = player;
	}

	public PlayerEntity getPlayer() {
		return this.player;
	}

	public String getName() {
		return this.player.name;
	}

	public void sendCommandFeedback(String message) {
		MinecraftAccessor.getInstance().overlay.addChatMessage(message);
	}
}
