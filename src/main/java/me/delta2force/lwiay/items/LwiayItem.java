package me.delta2force.lwiay.items;

import me.delta2force.lwiay.LwiayMod;
import net.minecraft.block.Blocks;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class LwiayItem extends Item{

	public LwiayItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if(!context.getWorld().isClient) {
			context.getStack().decrement(1);
			context.getPlayer().giveItemStack(new ItemStack(LwiayItems.LWIAY_REMOTE));
			LwiayMod.usingReddit = true;
			LwiayMod.postIndex = -1;
			LwiayMod.thePlayer=context.getPlayer();
			LwiayMod.theWorld=context.getWorld();
			buildWall(context.getBlockPos(), context.getWorld(), context.getPlayer());
			return ActionResult.SUCCESS;
		}
		return super.useOnBlock(context);
	}
	
	public void buildWall(BlockPos bottomLeft, World w, PlayerEntity player) {
		LwiayMod.frames = new ItemFrameEntity[5][5];
		for(int x = bottomLeft.getX();x<bottomLeft.getX()+3;x++) {
			for(int y = bottomLeft.getY()+1;y<bottomLeft.getY()+4;y++) {
				BlockPos position = new BlockPos(x, y, bottomLeft.getZ());
				w.setBlockState(position, Blocks.QUARTZ_BLOCK.getDefaultState());
				
				ItemFrameEntity ife = new ItemFrameEntity(w, position.add(0, 0, 1), Direction.SOUTH);
				w.spawnEntity(ife);
				LwiayMod.frames[x-bottomLeft.getX()][y-bottomLeft.getY()-1] = ife;
			}
		}
	}
	
}
