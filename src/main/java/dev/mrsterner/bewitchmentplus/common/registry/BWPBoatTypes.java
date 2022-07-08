package dev.mrsterner.bewitchmentplus.common.registry;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import com.terraformersmc.terraform.boat.impl.TerraformBoatTypeImpl;
import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.concurrent.atomic.AtomicReference;

public class BWPBoatTypes {
    public static void init() {
        registerBoat("yew");
    }

    private static void registerBoat(String name) {
        AtomicReference<TerraformBoatType> type = new AtomicReference<>();
        Item boat = TerraformBoatItemHelper.registerBoatItem(new Identifier(BewitchmentPlus.MODID, name + "_boat"), type::get, false, BewitchmentPlus.BEWITCHMENT_PLUS_GROUP);
        Item chest_boat = TerraformBoatItemHelper.registerBoatItem(new Identifier(BewitchmentPlus.MODID, name + "_chest_boat"), type::get, true, BewitchmentPlus.BEWITCHMENT_PLUS_GROUP);
        type.set(new TerraformBoatTypeImpl(boat, chest_boat));
        Registry.register(TerraformBoatTypeRegistry.INSTANCE, new Identifier(BewitchmentPlus.MODID, name), type.get());
    }
}
