package nl.requios.effortlessbuilding.create.foundation.utility.worldWrappers;

import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;

import javax.annotation.Nullable;

public class DummyStatusListener implements ChunkProgressListener {

	@Override
	public void updateSpawnPos(ChunkPos pCenter) {}

	@Override
	public void onStatusChange(ChunkPos pChunkPosition, @Nullable ChunkStatus pNewStatus) {}

	@Override
	public void start() {}

	@Override
	public void stop() {}

}
