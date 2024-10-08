package nl.requios.effortlessbuilding.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import nl.requios.effortlessbuilding.EffortlessBuilding;

import java.util.function.Supplier;

public class PerformRedoPacket {

	public PerformRedoPacket() {}

	public static void encode(PerformRedoPacket message, FriendlyByteBuf buf) {}

	public static PerformRedoPacket decode(FriendlyByteBuf buf) {
		return new PerformRedoPacket();
	}

	public static class Handler {
		public static void handle(PerformRedoPacket message, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				EffortlessBuilding.UNDO_REDO.redo(ctx.get().getSender());
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
