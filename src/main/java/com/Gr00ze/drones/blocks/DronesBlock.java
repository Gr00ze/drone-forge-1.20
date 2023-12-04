package com.Gr00ze.drones.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class DronesBlock extends Block {
    public DronesBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    }
}
