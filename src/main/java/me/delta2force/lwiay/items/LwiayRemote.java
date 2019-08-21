package me.delta2force.lwiay.items;

import me.delta2force.lwiay.LwiayMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class LwiayRemote extends Item{
	public LwiayRemote(Settings item$Settings_1) {
		super(item$Settings_1);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World w, PlayerEntity pl, Hand hand) {
		if(!w.isClient) {
			if(LwiayMod.usingReddit) {
				LwiayMod.postIndex++;
				LwiayMod.update();
				pl.swingHand(hand);
				return new TypedActionResult<ItemStack>(ActionResult.SUCCESS, pl.getStackInHand(hand));
			}
		}
		return super.use(w, pl, hand);
	}
}
