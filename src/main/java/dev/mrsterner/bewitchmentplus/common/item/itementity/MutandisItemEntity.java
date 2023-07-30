package dev.mrsterner.bewitchmentplus.common.item.itementity;

import dev.mrsterner.bewitchmentplus.common.registry.BWPEntityTypes;
import dev.mrsterner.bewitchmentplus.common.registry.BWPObjects;
import dev.mrsterner.bewitchmentplus.common.registry.BWPTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class MutandisItemEntity extends ThrownItemEntity implements FlyingItemEntity {
    public static final BooleanProperty WATERLOGGED = BooleanProperty.of("waterlogged");

    public MutandisItemEntity(World world, LivingEntity owner) {
        super(BWPEntityTypes.MUTANDIS_ENTITY_ENTITY_TYPE, owner, world);
    }

    public MutandisItemEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Item getDefaultItem() {
        return BWPObjects.MUTANDIS_BREW;
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return itemStack.isEmpty() ? ParticleTypes.DRAGON_BREATH : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }
    @Override
    public void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.getWorld(), this.getX(), this.getY(), this.getZ());
        areaEffectCloudEntity.setRadius(2.5F);
        areaEffectCloudEntity.setDuration(10);
        areaEffectCloudEntity.setColor(2290338);
        this.getWorld().spawnEntity(areaEffectCloudEntity);
        BlockPos origo = blockHitResult.getBlockPos().add(-1,1,-1);
        List<BlockPos> listPos = new ArrayList<>();
        for(int x = 0; x<3; x++){
            for(int z = 0; z<3; z++){
                listPos.add(x + z,origo.add(x,0,z));
            }
        }
        listPos.forEach(blockPos1 -> {
            if(this.getWorld().getBlockState(blockPos1).isIn(BWPTags.MUTANDIS)){
                BlockState blockState = getWorld().getBlockState(blockPos1);
                blockState = Registries.BLOCK.getEntryList(BWPTags.MUTANDIS).flatMap((blocks) -> blocks.getRandom(getWorld().random)).map((blockEntry) -> (blockEntry.value()).getDefaultState()).orElse(blockState);
                if(blockState.getBlock() instanceof Waterloggable){
                    this.getWorld().setBlockState(blockPos1, blockState.with(WATERLOGGED, false));
                }else{
                    this.getWorld().setBlockState(blockPos1,blockState);
                }
            }
        });
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, (byte)3);
            this.kill();
        }
    }
}