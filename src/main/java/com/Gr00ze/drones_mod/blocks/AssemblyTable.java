package com.Gr00ze.drones_mod.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class AssemblyTable extends Block {
    public AssemblyTable() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    }
}
