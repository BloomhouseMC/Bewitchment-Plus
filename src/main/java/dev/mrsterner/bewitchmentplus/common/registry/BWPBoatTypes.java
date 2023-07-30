package dev.mrsterner.bewitchmentplus.common.registry;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;


public class BWPBoatTypes {
    public static void init() {
        registerBoat("yew", BWPObjects.YEW_PLANKS.asItem());
    }

    private static void registerBoat(String name, Item planks) {
        Identifier boatId = BewitchmentPlus.id(name + "_boat");
        RegistryKey<TerraformBoatType> key = TerraformBoatTypeRegistry.createKey(boatId);
        Item boat = TerraformBoatItemHelper.registerBoatItem(boatId, key, false);
        Item chest_boat = TerraformBoatItemHelper.registerBoatItem(BewitchmentPlus.id(name + "_chest_boat"), key, true);
        TerraformBoatType boatType = new TerraformBoatType.Builder()
                .item(boat)
                .chestItem(chest_boat)
                .planks(planks)
                .build();
        Registry.register(TerraformBoatTypeRegistry.INSTANCE, key, boatType);
        BWPObjects.BOATS.add(boat);
        BWPObjects.BOATS.add(chest_boat);
    }
}
