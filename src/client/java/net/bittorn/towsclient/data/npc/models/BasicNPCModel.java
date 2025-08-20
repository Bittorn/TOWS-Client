package net.bittorn.towsclient.data.npc.models;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector3d;

public class BasicNPCModel {
    private String identifier;
    private String dialogId;
    private int[] position = new int[2];

    private BasicNPCModel() {
        // no-op constructor
    }

    private String getIdentifier() {
        return identifier;
    }

    public String getDialogId() {
        return dialogId;
    }

    public BlockPos getPosition() {
        return BlockPos.ofFloored(position[0], position[1], position[2]);
    }

    public int[] getPositionRaw() { return position; }
}
