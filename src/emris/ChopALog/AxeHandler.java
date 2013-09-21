/**
 *  Copyright (C) 2013  emris
 *  https://github.com/emris/ChopALog
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package emris.ChopALog;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import TFC.TFCBlocks;
import TFC.TFCItems;

public class AxeHandler {
	@ForgeSubscribe
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.action == Action.LEFT_CLICK_BLOCK) {
			// You have a random chance of failing
			Random rand = new Random();
			if(rand.nextInt(5) > 0)
				return;

			int[] axes = new int[] {
				TFCItems.IgInAxe.itemID,
				TFCItems.SedAxe.itemID,
				TFCItems.IgExAxe.itemID,
				TFCItems.MMAxe.itemID,
				TFCItems.BismuthBronzeAxe.itemID,
				TFCItems.BlackBronzeAxe.itemID,
				TFCItems.BlackSteelAxe.itemID,
				TFCItems.BlueSteelAxe.itemID,
				TFCItems.BronzeAxe.itemID,
				TFCItems.CopperAxe.itemID,
				TFCItems.WroughtIronAxe.itemID,
				TFCItems.RedSteelAxe.itemID,
				TFCItems.SteelAxe.itemID,
			};

			EntityPlayer p = e.entityPlayer;
			ItemStack s = p.inventory.getCurrentItem();

			Boolean isAxe = false;
			if (s != null) {
				for (int i = 0; i < axes.length; i++)
					if (s.itemID == axes[i]) isAxe = true;
			}
			
			if ( isAxe ) {
				MovingObjectPosition mop = PlayerUtils.getTargetBlock(p);
				if (mop != null && mop.sideHit == 1) {
					World world = p.worldObj;
					int X = mop.blockX;
					int Y = mop.blockY;
					int Z = mop.blockZ;

					int target = world.getBlockId(X, Y, Z);
					int meta = world.getBlockMetadata(X, Y, Z);
					if (target == TFCBlocks.WoodVert.blockID){
						if (world.getBlockId(X, Y+1, Z) == 0 &&
							world.getBlockId(X, Y-1, Z) == TFCBlocks.WoodVert.blockID &&
							world.getBlockId(X+1, Y-1, Z) == TFCBlocks.WoodVert.blockID &&
							world.getBlockId(X-1, Y-1, Z) == TFCBlocks.WoodVert.blockID &&
							world.getBlockId(X, Y-1, Z+1) == TFCBlocks.WoodVert.blockID &&
							world.getBlockId(X, Y-1, Z-1) == TFCBlocks.WoodVert.blockID)
						{
							Random rn = new Random();
							world.setBlockToAir(X, Y, Z);
							
							ItemStack stack1 = new ItemStack(TFCItems.SinglePlank, 1, meta);
							ItemStack stack2 = new ItemStack(TFCItems.SinglePlank, 1, meta);
							
							EntityItem en1 = new EntityItem(world, X+1, Y, Z, stack1);
							EntityItem en2 = new EntityItem(world, X-1, Y, Z, stack2);
							
							en1.delayBeforeCanPickup = 6;
							en2.delayBeforeCanPickup = 6;
							
							world.spawnEntityInWorld(en1);
							world.spawnEntityInWorld(en2);
							
							s.damageItem(10, p);
							if(s.getItemDamage() == s.getMaxDamage() || s.getItemDamage() == 0) {
								p.renderBrokenItemStack(s);
								p.worldObj.playSoundAtEntity(p, "random.break", 0.8F, 0.8F + p.worldObj.rand.nextFloat() * 0.4F);
								p.destroyCurrentEquippedItem();
							}
						}
					}
				}
			}
		}
	}
}
