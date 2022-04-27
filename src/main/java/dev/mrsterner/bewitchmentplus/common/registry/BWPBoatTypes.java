package dev.mrsterner.bewitchmentplus.common.registry;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BWPBoatTypes {
    public static TerraformBoatType yew;

    public static void init(){
        Item juniper_boat = TerraformBoatItemHelper.registerBoatItem(new Identifier(BewitchmentPlus.MODID, "yew_boat"), () -> yew, BewitchmentPlus.BEWITCHMENT_PLUS_GROUP);
        yew = new TerraformBoatType.Builder().item(juniper_boat).build();
        Registry.register(TerraformBoatTypeRegistry.INSTANCE, new Identifier(BewitchmentPlus.MODID, "yew"), yew);
    }
}
