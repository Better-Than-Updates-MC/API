/*
 * Copyright (c) 2020 The Cursed Legacy Team.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.halotroop.fabric.mixin.event.lifecycle;

import com.halotroop.fabric.api.event.lifecycle.CommonLifecycleEvents;
import org.jetbrains.annotations.Nullable;

import net.minecraft.server.ServerPlayerConnectionManager;
import net.minecraft.server.entity.player.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.halotroop.fabric.api.event.lifecycle.ServerLifecycleEvents;
import net.minecraft.server.network.ServerLoginPacketHandler;

@Mixin(ServerPlayerConnectionManager.class)
public class ServerPlayerConnectionManagerMixin {
	@Inject(at = @At("RETURN"), method = "connectPlayer", cancellable = true)
	private void onConnectPlayer(ServerLoginPacketHandler handler, String name, CallbackInfoReturnable<ServerPlayerEntity> info) {
		@Nullable
		ServerPlayerEntity player = info.getReturnValue();

		if (player != null) {
			ServerLifecycleEvents.PLAYER_LOGIN.invoker().onPlayerLogin(player, handler);

			// If disconnected, the player has not joined due to a mod cancelling the join.
			if (handler.closed) {
				info.setReturnValue(null);
			}
		}
	}

	@Inject(at = @At("RETURN"), method = "respawn")
	private void onRespawn(ServerPlayerEntity player, int dimensionId, CallbackInfoReturnable<ServerPlayerEntity> info) {
		CommonLifecycleEvents.PLAYER_RESPAWN.invoker().onRespawn(info.getReturnValue());
	}

	@Inject(at = @At("RETURN"), method = "updateDimension") // this method is named incorrectly in the current mappings. Should be renamed by the next mapping update.
	private void onDisconnect(ServerPlayerEntity player, CallbackInfo info) {
		ServerLifecycleEvents.PLAYER_DISCONNECT.invoker().onPlayerDisconnect(player);
	}
}
