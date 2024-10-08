package nl.requios.effortlessbuilding.compatibility;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import nl.requios.effortlessbuilding.create.foundation.item.ItemHelper;
import nl.requios.effortlessbuilding.item.AbstractRandomizerBagItem;

public class CompatHelper {

	public static void setup() {

	}

	public static boolean isItemBlockProxy(ItemStack stack) {
		return isItemBlockProxy(stack, true);
	}

	// Check if the item given is a proxy for blocks. For now, we check for the randomizer bag,
	// /dank/null, or plain old blocks.
	public static boolean isItemBlockProxy(ItemStack stack, boolean seeBlockItemsAsProxies) {
		Item item = stack.getItem();
		if (item instanceof BlockItem)
			return seeBlockItemsAsProxies;
		return item instanceof AbstractRandomizerBagItem;
	}

	// Get the block to be placed by this proxy. For the /dank/null, it's the slot stack
	// pointed to by nbt integer selectedIndex.
	public static ItemStack getItemBlockFromStack(ItemStack proxy) {
		Item proxyItem = proxy.getItem();

		if (proxyItem instanceof BlockItem)
			return proxy;

		//Randomizer Bag
		if (proxyItem instanceof AbstractRandomizerBagItem) {
			ItemStack itemStack = proxy;
			while (!(itemStack.getItem() instanceof BlockItem || itemStack.isEmpty())) {
				if (itemStack.getItem() instanceof AbstractRandomizerBagItem) {
					AbstractRandomizerBagItem randomizerBagItem = (AbstractRandomizerBagItem) itemStack.getItem();
					itemStack = randomizerBagItem.pickRandomStack(randomizerBagItem.getBagInventory(itemStack));
				}
			}
			return itemStack;
		}

		return ItemStack.EMPTY;
	}

	public static ItemStack getItemBlockByState(ItemStack stack, BlockState state) {
		if (state == null) return ItemStack.EMPTY;

		Item blockItem = Item.byBlock(state.getBlock());
		if (stack.getItem() instanceof BlockItem)
			return stack;
		else if (stack.getItem() instanceof AbstractRandomizerBagItem) {
			AbstractRandomizerBagItem randomizerBagItem = (AbstractRandomizerBagItem) stack.getItem();
			IItemHandler bagInventory = randomizerBagItem.getBagInventory(stack);
			return randomizerBagItem.findStack(bagInventory, blockItem);
		}

		return ItemStack.EMPTY;
	}

	public static boolean containsBlock(ItemStack stack, Block block) {
		if (stack == null || stack.isEmpty() || !isItemBlockProxy(stack)) {
			return block == null || block == Blocks.AIR;
		}

		if (stack.getItem() instanceof BlockItem) {
			return ((BlockItem) stack.getItem()).getBlock() == block;
		}

		if (stack.getItem() instanceof AbstractRandomizerBagItem randomizerBagItem) {
			IItemHandler bagInventory = randomizerBagItem.getBagInventory(stack);
			ItemStack firstMatch = ItemHelper.findFirstMatch(bagInventory, s -> s.getItem() instanceof BlockItem);
			return firstMatch != null && !firstMatch.isEmpty();
		}
		return false;
	}

}
