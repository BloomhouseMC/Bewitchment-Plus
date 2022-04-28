package dev.mrsterner.bewitchmentplus.client.particle;

import dev.mrsterner.bewitchmentplus.common.network.packet.C2SBloodParticlePacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

//Derivative from Illuminations https://github.com/Ladysnake/Illuminations/blob/1.18/src/main/java/ladysnake/illuminations/client/particle/WillOWispParticle.java
public class LifeDrainParticle extends Particle {
    public final Identifier texture;
    protected final float redEvolution;
    protected final float greenEvolution;
    protected final float blueEvolution;
    protected double xTarget;
    protected double yTarget;
    protected double zTarget;
    public float speedModifier;
    protected PlayerEntity player;
    protected Optional<LivingEntity> source;

    protected LifeDrainParticle(ClientWorld world, double x, double y, double z, Identifier texture, float red, float green, float blue, float redEvolution, float greenEvolution, float blueEvolution) {
        super(world, x, y, z);
        this.texture = texture;
        setBoundingBoxSpacing(0.02f, 0.02f);
        this.gravityStrength = 0.0F;
        this.maxAge = 20 * 4;
        speedModifier = 0.1f + Math.max(0, random.nextFloat() - 0.1f);
        this.velocityX *= 0.1;
        this.velocityY *= 0.1;
        this.velocityZ *= 0.1;
        this.red = red;
        this.green = green;
        this.blue = blue;

        this.redEvolution = redEvolution;
        this.blueEvolution = blueEvolution;
        this.greenEvolution = greenEvolution;
        this.player = world.getClosestPlayer((TargetPredicate.createNonAttackable()).setBaseMaxDistance(8D), this.x, this.y, this.z);
        source = world.getEntitiesByClass(LivingEntity.class, new Box(new BlockPos(this.x, this.y, this.z)).expand(2, 2, 2), LivingEntity::isAlive).stream().findFirst();
        if (this.player == null) {
            this.markDead();
        }
    }

    @Override
    public void tick() {
        if (this.prevPosX == this.x && this.prevPosY == this.y && this.prevPosZ == this.z) {
            this.selectBlockTarget();
        }

        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;

        if (this.age++ >= this.maxAge) {
            for (int i = 0; i < 25; i++) {
                this.world.addParticle(new LifeDrainParticleEffect(this.red, this.green, this.blue, this.redEvolution, this.greenEvolution, this.blueEvolution), this.x + random.nextGaussian() / 15, this.y + random.nextGaussian() / 15, this.z + random.nextGaussian() / 15, 0, 0, 0);
            }
            this.world.playSound(new BlockPos(this.x, this.y, this.z), SoundEvents.PARTICLE_SOUL_ESCAPE, SoundCategory.AMBIENT, 1.0f, 1.5f, true);
            this.world.playSound(new BlockPos(this.x, this.y, this.z), SoundEvents.BLOCK_SOUL_SAND_BREAK, SoundCategory.AMBIENT, 1.0f, 1.0f, true);
            this.markDead();
        }

        selectBlockTarget();

        Vec3d targetVector = new Vec3d(this.xTarget - this.x, this.yTarget - this.y, this.zTarget - this.z);
        double length = targetVector.length();
        targetVector = targetVector.multiply(speedModifier / length);

        velocityX = (0.9) * velocityX + (0.1) * targetVector.x;
        velocityY = (0.9) * velocityY + (0.1) * targetVector.y;
        velocityZ = (0.9) * velocityZ + (0.1) * targetVector.z;

        for (int i = 0; i < 10 * this.speedModifier; i++) {
            this.world.addParticle(new LifeDrainParticleEffect(this.red, this.green, this.blue, this.redEvolution, this.greenEvolution, this.blueEvolution), this.x + random.nextGaussian() / 15, this.y + random.nextGaussian() / 15, this.z + random.nextGaussian() / 15, 0, 0, 0);
        }

        if (!new BlockPos(x, y, z).equals(this.getTargetPosition())) {
            this.move(velocityX, velocityY, velocityZ);
        }
        double distance = getTargetPosition().getSquaredDistance(new Vec3i(this.x, this.y, this.z));
        if (distance < 2D) {
            if(source.isPresent()){
                C2SBloodParticlePacket.send(player.getUuid(), source.get().getId());
            }
            this.markDead();
        }
    }


    @Override
    public void move(double dx, double dy, double dz) {
        double d = dx;
        if (this.collidesWithWorld && (dx != 0.0D || dy != 0.0D || dz != 0.0D)) {
            Vec3d vec3d = Entity.adjustMovementForCollisions(null, new Vec3d(dx, dy, dz), this.getBoundingBox(), this.world, List.of());

            dx = vec3d.x;
            dy = vec3d.y;
            dz = vec3d.z;
        }

        if (dx != 0.0D || dy != 0.0D || dz != 0.0D) {
            this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
            this.repositionFromBoundingBox();
        }

        this.onGround = false;
        if (d != dx) {
            this.velocityX = 0.0D;
        }

        if (dz != dz) {
            this.velocityZ = 0.0D;
        }
    }


    public BlockPos getTargetPosition() {
        return new BlockPos(this.xTarget, this.yTarget + 0.5, this.zTarget);
    }

    private void selectBlockTarget() {
        if(player != null){
            this.xTarget = player.getX();
            this.yTarget = player.getY() + 0.5D;
            this.zTarget = player.getZ();

            BlockPos targetPos = new BlockPos(this.xTarget, this.yTarget, this.zTarget);
            if (this.world.getBlockState(targetPos).isFullCube(world, targetPos) && !this.world.getBlockState(targetPos).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS)) {
                return;
            }
            speedModifier = 0.1f + Math.max(0, random.nextFloat() - 0.1f);
        }
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.CUSTOM;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final Identifier texture;
        private final float red;
        private final float green;
        private final float blue;
        private final float redEvolution;
        private final float greenEvolution;
        private final float blueEvolution;

        public Factory(SpriteProvider spriteProvider, Identifier texture, float red, float green, float blue, float redEvolution, float greenEvolution, float blueEvolution) {
            this.texture = texture;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.redEvolution = redEvolution;
            this.greenEvolution = greenEvolution;
            this.blueEvolution = blueEvolution;
        }

        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new LifeDrainParticle(world, x, y, z, this.texture, this.red, this.green, this.blue, this.redEvolution, this.greenEvolution, this.blueEvolution);
        }
    }
}
