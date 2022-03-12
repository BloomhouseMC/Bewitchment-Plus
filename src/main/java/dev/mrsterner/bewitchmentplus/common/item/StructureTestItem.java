package dev.mrsterner.bewitchmentplus.common.item;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.Random;

public class StructureTestItem extends Item {
    public StructureTestItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if((context.getWorld() instanceof ServerWorld serverWorld)){
            Optional<Structure> structureOptional =  serverWorld.getStructureManager().getStructure(new Identifier(BewitchmentPlus.MODID, "features/trees/yew_tree1"));
            if(structureOptional.isPresent()) {
                Structure structure = structureOptional.get();
                StructurePlacementData structurePlacementData = new StructurePlacementData().setMirror(BlockMirror.FRONT_BACK).setRotation(BlockRotation.NONE).setIgnoreEntities(false);
                structure.place(serverWorld, context.getPlayer().getBlockPos(), context.getPlayer().getBlockPos(), structurePlacementData, new Random(), 2);
            }
        }
        return super.useOnBlock(context);
    }
}
