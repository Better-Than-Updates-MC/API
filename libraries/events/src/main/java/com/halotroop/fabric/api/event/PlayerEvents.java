package com.halotroop.fabric.api.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.ServerPlayerEntity;

/**
 * <b>Contains the following player-related events:</b>
 * <p/>
 * <li/>{@link #PLAYER_DEATH Death<br/>}
 * <li/>{@link #PLAYER_FIRST_DEATH First death}<br/>
 * <li/>{@link #PLAYER_JOIN Join<br/>}
 * <li/>{@link #PLAYER_FIRST_JOIN First join<br/>}
 * <li/>{@link #PLAYER_KILL_ENTITY Kill entity<br/>}
 * <li/>{@link #PLAYER_KILL_PLAYER Kill player<br/>}
 * <li/>{@link #PLAYER_LEAVE Leave<br/>}
 */
@SuppressWarnings("unused")
public final class PlayerEvents {
	public static final Event<PlayerDeath> PLAYER_DEATH = EventFactory.createArrayBacked(PlayerDeath.class, (listeners) -> (player) -> {
		for (PlayerDeath listener : listeners) {
			listener.kill(player);
		}
	});

	public static final Event<PlayerFirstDeath> PLAYER_FIRST_DEATH = EventFactory.createArrayBacked(PlayerFirstDeath.class, (listeners) -> (player) -> {
		for (PlayerFirstDeath listener : listeners) {
			listener.firstDeath(player);
		}
	});

	public static final Event<PlayerFirstJoin> PLAYER_FIRST_JOIN = EventFactory.createArrayBacked(PlayerFirstJoin.class, (listeners) -> (player, server) -> {
		for (PlayerFirstJoin listener : listeners) {
			listener.joinServerForFirstTime(player, server);
		}
	});

	public static final Event<PlayerJoin> PLAYER_JOIN = EventFactory.createArrayBacked(PlayerJoin.class, (listeners) -> (player, server) -> {
		for (PlayerJoin listener : listeners) {
			listener.joinServer(player, server);
		}
	});

	public static final Event<PlayerKillEntity> PLAYER_KILL_ENTITY = EventFactory.createArrayBacked(PlayerKillEntity.class, (listeners) -> (player, killedEntity) -> {
		for (PlayerKillEntity listener : listeners) {
			listener.killEntity(player, killedEntity);
		}
	});

	public static final Event<PlayerKillPlayer> PLAYER_KILL_PLAYER = EventFactory.createArrayBacked(PlayerKillPlayer.class, (listeners) -> (player, killedPlayer) -> {
		for (PlayerKillPlayer listener : listeners) {
			listener.killPlayer(player, killedPlayer);
		}
	});

	public static final Event<PlayerLeave> PLAYER_LEAVE = EventFactory.createArrayBacked(PlayerLeave.class, (listeners) -> (player, server) -> {
		for (PlayerLeave listener : listeners) {
			listener.leaveServer(player, server);
		}
	});

	@Environment(EnvType.SERVER)
	public interface PlayerDeath {
		void kill(ServerPlayerEntity player);
	}

	@Environment(EnvType.SERVER)
	public interface PlayerFirstDeath {
		void firstDeath(ServerPlayerEntity player);
	}

	@Environment(EnvType.SERVER)
	public interface PlayerFirstJoin {
		void joinServerForFirstTime(ServerPlayerEntity player, MinecraftServer server);
	}

	@Environment(EnvType.SERVER)
	public interface PlayerJoin {
		void joinServer(ServerPlayerEntity player, MinecraftServer server);
	}

	@Environment(EnvType.SERVER)
	public interface PlayerKillEntity {
		void killEntity(ServerPlayerEntity player, Entity killedEntity);
	}

	@Environment(EnvType.SERVER)
	public interface PlayerKillPlayer {
		void killPlayer(ServerPlayerEntity player, ServerPlayerEntity killedPlayer);
	}

	@Environment(EnvType.SERVER)
	public interface PlayerLeave {
		void leaveServer(ServerPlayerEntity player, MinecraftServer server);
	}

	/**
	 * Utility Class
	 */
	private PlayerEvents() {
	}
}
