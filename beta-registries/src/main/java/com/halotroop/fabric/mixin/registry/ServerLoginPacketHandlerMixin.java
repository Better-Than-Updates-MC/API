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

package com.halotroop.fabric.mixin.registry;

import java.io.ByteArrayOutputStream;

import com.halotroop.fabric.impl.registry.RegistryImpl;
import net.minecraft.server.entity.player.ServerPlayerEntity;
import net.minecraft.util.io.TagInputOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.halotroop.fabric.impl.registry.sync.RegistryRemapper;
import net.minecraft.packet.login.LoginRequestPacket;
import net.minecraft.server.network.ServerLoginPacketHandler;

@Mixin(ServerLoginPacketHandler.class)
public class ServerLoginPacketHandlerMixin {
	@Inject(at = @At("RETURN"), method = "complete", locals = LocalCapture.CAPTURE_FAILHARD)
	private void onConnectPlayer(LoginRequestPacket arg, CallbackInfo info, ServerPlayerEntity player) {
		RegistryRemapper.LOGGER.info("Sending registry remap packet to connecting client.");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		TagInputOutput.writeNBT(RegistryImpl.registryData, bos);
		RegistryImpl.syncChannel.send(bos.toByteArray(), player);
	}
}
