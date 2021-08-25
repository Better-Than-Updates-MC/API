package io.github.minecraftcursedlegacy.impl.command;

import io.github.minecraftcursedlegacy.accessor.command.MinecraftAccessor;
import io.github.minecraftcursedlegacy.api.command.Sender;
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
