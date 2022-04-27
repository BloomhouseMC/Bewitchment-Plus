package dev.mrsterner.bewitchmentplus.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
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

//Derivative from Illuminations https://github.com/Ladysnake/Illuminations/blob/1.18/src/main/java/ladysnake/illuminations/client/particle/WillOWispParticle.java
public class LifeDrainParticle extends Particle {
    private final TargetPredicate targetPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(12.0);
    public final Identifier texture;
    protected final float redEvolution;
    protected final float greenEvolution;
    protected final float blueEvolution;
    protected double xTarget;
    protected double yTarget;
    protected double zTarget;
    public float prevYaw;
    public float prevPitch;
    public float yaw;
    public float pitch;
    public float speedModifier;
    protected int timeInSolid = -1;
    protected int targetChangeCooldown = 0;
    protected PlayerEntity player;

    protected LifeDrainParticle(ClientWorld world, double x, double y, double z, Identifier texture, float red, float green, float blue, float redEvolution, float greenEvolution, float blueEvolution) {
        super(world, x, y, z);
        this.texture = texture;
        setBoundingBoxSpacing(0.02f, 0.02f);
        this.gravityStrength = 0.0F;
        this.maxAge = 16;
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

        this.targetChangeCooldown -= (new Vec3d(x, y, z).squaredDistanceTo(prevPosX, prevPosY, prevPosZ) < 0.0125) ? 10 : 1;

        if ((this.world.getTime() % 20 == 0) && ((xTarget == 0 && yTarget == 0 && zTarget == 0) || new Vec3d(x, y, z).squaredDistanceTo(xTarget, yTarget, zTarget) < 9 || targetChangeCooldown <= 0)) {
            selectBlockTarget();
        }

        Vec3d targetVector = new Vec3d(this.xTarget - this.x, this.yTarget - this.y, this.zTarget - this.z);
        double length = targetVector.length();
        targetVector = targetVector.multiply(speedModifier / length);

        velocityX = (0.9) * velocityX + (0.1) * targetVector.x;
        velocityY = (0.9) * velocityY + (0.1) * targetVector.y;
        velocityZ = (0.9) * velocityZ + (0.1) * targetVector.z;

        this.prevYaw = this.yaw;
        this.prevPitch = this.pitch;
        Vec3d vec3d = new Vec3d(velocityX, velocityY, velocityZ);
        float f = (float) Math.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z);
        this.yaw = (float) (MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875D);
        this.pitch = (float) (MathHelper.atan2(vec3d.y, f) * 57.2957763671875D);

        for (int i = 0; i < 10 * this.speedModifier; i++) {
            this.world.addParticle(new LifeDrainParticleEffect(this.red, this.green, this.blue, this.redEvolution, this.greenEvolution, this.blueEvolution), this.x + random.nextGaussian() / 15, this.y + random.nextGaussian() / 15, this.z + random.nextGaussian() / 15, 0, 0, 0);
        }

        if (!new BlockPos(x, y, z).equals(this.getTargetPosition())) {
            this.move(velocityX, velocityY, velocityZ);
        }

        if (random.nextInt(20) == 0) {
            this.world.playSound(new BlockPos(this.x, this.y, this.z), SoundEvents.PARTICLE_SOUL_ESCAPE, SoundCategory.AMBIENT, 1.0f, 1.5f, true);
        }

        BlockPos pos = new BlockPos(this.x, this.y, this.z);
        if (!this.world.getBlockState(pos).isAir()) {
            if (timeInSolid > -1) {
                timeInSolid += 1;
            }
        } else {
            timeInSolid = 0;
        }

        if (timeInSolid > 25) {
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
        Vec3d vec3d = camera.getPos();
        float f = (float) (MathHelper.lerp(tickDelta, this.prevPosX, this.x) - vec3d.getX());
        float g = (float) (MathHelper.lerp(tickDelta, this.prevPosY, this.y) - vec3d.getY());
        float h = (float) (MathHelper.lerp(tickDelta, this.prevPosZ, this.z) - vec3d.getZ());

        MatrixStack matrixStack = new MatrixStack();
        matrixStack.translate(f, g, h);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(g, this.prevYaw, this.yaw) - 180));
        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(MathHelper.lerp(g, this.prevPitch, this.pitch)));
        matrixStack.scale(0.5F, -0.5F, 0.5F);
        matrixStack.translate(0, -1, 0);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        immediate.draw();
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
