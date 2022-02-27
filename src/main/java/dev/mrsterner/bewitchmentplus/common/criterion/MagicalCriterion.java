package dev.mrsterner.bewitchmentplus.common.criterion;

import com.google.gson.JsonObject;
import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class MagicalCriterion extends AbstractCriterion<MagicalCriterion.Conditions> {
    private static final Identifier ID = new Identifier(BewitchmentPlus.MODID, "magical");

    public Identifier getId() {
        return ID;
    }

    @Override
    public Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        boolean magical = (JsonHelper.getBoolean(obj, "magical"));
        return new Conditions(playerPredicate, magical);
    }

    public void trigger(ServerPlayerEntity player) {
        this.trigger(player, (conditions) -> conditions.matches(conditions.magical));
    }

    public static class Conditions extends AbstractCriterionConditions {

        private final boolean magical;

        public Conditions(EntityPredicate.Extended player, boolean magical) {
            super(MagicalCriterion.ID, player);
            this.magical = magical;
        }

        public boolean matches(boolean magical) {
            return this.magical == magical;
        }

        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject jsonObject = super.toJson(predicateSerializer);
            jsonObject.addProperty("magical", this.magical);
            return jsonObject;
        }
    }
}
