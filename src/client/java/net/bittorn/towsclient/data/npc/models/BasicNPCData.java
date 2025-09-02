package net.bittorn.towsclient.data.npc.models;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("unused")
public class BasicNPCData {
    private String dialogId;
    private String entityId;
    private final int[] position = new int[2];

    public String getDialogId() {
        return dialogId;
    }

    public String getEntityId() {
        return entityId;
    }

    public BlockPos getPosition() {
        return BlockPos.ofFloored(position[0], position[1], position[2]);
    }

    public int[] getPositionRaw() { return position; }
}
