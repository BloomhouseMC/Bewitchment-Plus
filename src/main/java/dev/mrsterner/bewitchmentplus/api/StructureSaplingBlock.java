package dev.mrsterner.bewitchmentplus.api;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.utils.WorldgenHelper;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.Random;

public class StructureSaplingBlock extends PlantBlock implements Fertilizable {
    public static final IntProperty STAGE = Properties.STAGE;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);
    private final String nbtLocation;
    private final int variants;

    public StructureSaplingBlock(int variants, String nbtLocation, AbstractBlock.Settings settings) {
        super(settings);
        this.nbtLocation = nbtLocation;
        this.variants = variants;
        this.setDefaultState(this.stateManager.getDefaultState().with(STAGE, 0));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getLightLevel(pos.up()) >= 9 && random.nextInt(7) == 0) {
            this.generate(world, pos, state, random);
        }

    }

    public void generate(ServerWorld world, BlockPos pos, BlockState state, Random random) {
        if (state.get(STAGE) == 0) {
            world.setBlockState(pos, state.cycle(STAGE), Block.NO_REDRAW);
        } else {
            generateStructureTree(world, pos, random);
        }

    }

    private void generateStructureTree(ServerWorld world, BlockPos pos, Random random) {
        StructureManager structureManager = world.toServerWorld().getStructureManager();
        //Try fetch the nbt with the structure manager
        int rand = world.getRandom().nextInt(variants);
        Identifier nbtIdentifier = new Identifier(BewitchmentPlus.MODID, variants > 1 ? nbtLocation+"_"+rand : nbtLocation);
        Optional<Structure> structureOptional = structureManager.getStructure(nbtIdentifier);
        if (structureOptional.isEmpty()) {
            BewitchmentPlus.LOGGER.info("NBT " + nbtIdentifier + " does not exist!");
        }else if (WorldgenHelper.treeNearby(world, pos, structureOptional.get().getSize().getY(), 0)) {
            //Unless structureOptional.isEmpty() not catches, get the structure from the optional
            Structure structure = structureOptional.get();
            //Change the origin from the corner of the structure to the middle of the structure
            BlockPos normalizeOrigin = pos.subtract(new Vec3i(Math.floor((double) structure.getSize().getX() / 2),0,Math.floor((double) structure.getSize().getX() / 2)));
            //Get basic placementData
            StructurePlacementData placementData = (new StructurePlacementData()).setMirror(BlockMirror.NONE).setRotation(BlockRotation.NONE).setIgnoreEntities(false);
            //Place the structure at the normalized origin
            structure.place(world, normalizeOrigin, normalizeOrigin, placementData, random, 2);
        }
    }



    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return (double)world.random.nextFloat() < 0.45;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        this.generate(world, pos, state, random);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }
}
