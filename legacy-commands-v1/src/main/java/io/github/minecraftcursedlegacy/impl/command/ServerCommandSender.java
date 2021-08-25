package io.github.minecraftcursedlegacy.impl.command;

import io.github.minecraftcursedlegacy.accessor.command.ServerPlayPacketHandlerAccessor;
import io.github.minecraftcursedlegacy.api.command.Sender;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandSource;

@Environment(EnvType.SERVER)
public class ServerCommandSender implements Sender {
	private final CommandSource source;

	public ServerCommandSender(CommandSource source) {
		this.source = source;
	}

	public PlayerEntity getPlayer() {
		if (source instanceof ServerPlayPacketHandlerAccessor) {
			return ((ServerPlayPacketHandlerAccessor) source).getPlayer();
		}
		return null;
	}

	public String getName() {
		return this.source.getName();
	}

	public void sendCommandFeedback(String message) {
		this.source.sendFeedback(message);
	}
}
