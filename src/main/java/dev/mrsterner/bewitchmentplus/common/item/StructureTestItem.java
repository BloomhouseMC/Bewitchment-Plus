package dev.mrsterner.bewitchmentplus.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

public class StructureTestItem extends Item {
    public StructureTestItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {




        return super.useOnBlock(context);
    }

    public boolean place(ServerWorld world, boolean bl, Structure structure) {


        return false;
    }
}
