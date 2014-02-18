package NMSurvivalGames.Arena.Chests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import NMSurvivalGames.Arena.Arena;


public class ChestHandler {
	
	private Arena a;
	
	private HashMap<Material, Integer> itemMap = new HashMap<Material, Integer>();
	private ArrayList<ItemStack> itemStackList = new ArrayList<ItemStack>();
	private ArrayList<Chest> chestList = new ArrayList<Chest>();
	
	private Random randGen;
	
	public ChestHandler(Arena a) {
		setA(a);
		randGen = new Random();
	}
	
	public void getChests() {
		populateChestList();
		populateItemStackList();
	}
	
	public void populateChestList() {
		ArrayList<Chest> ret = new ArrayList<Chest>();
		for(Chunk chunk : getA().getWorld().getLoadedChunks()) {
			for(BlockState bs : chunk.getTileEntities()) {
				if(bs instanceof Chest) {
					ret.add((Chest) bs);
				}
			}
		}
		synchronized(chestList) {
			chestList = ret;
		}
	}
	
	public void populateItemStackList() {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		for(Material mat : itemMap.keySet()) {
			for(int i = 0; i < itemMap.get(mat); i++) {
				ret.add(new ItemStack(mat));
			}
		}
		for(int i = 0; i < chestList.size()*27; i++) {
			ret.add(new ItemStack(Material.AIR));
		}
		synchronized(itemStackList) {
			itemStackList = ret;
		}
	}

	public void emptyChests() {
		getChests();
		synchronized(chestList) {
			for(Chest c : chestList) {
				c.getInventory().clear();
			}
		}
	}
	
	public void replaceChestInventories() {
		getChests();
		synchronized(chestList) {
			for(Chest c : chestList) {
				c.getInventory().clear();
				for(int i = 0; i < c.getInventory().getSize(); i++) {
					synchronized(itemStackList) {
						int rand = randGen.nextInt(itemStackList.size());					
						c.getInventory().setItem(i, itemStackList.get(rand));
					}
				}
			}
		}
	}
	
	public void addToChestInventories() {
		getChests();
		synchronized(chestList) {
			for(Chest c : chestList) {
				for(int i = 0; i < c.getInventory().getSize(); i++) {
					if(c.getInventory().getItem(i) == null) {
						synchronized(itemStackList) {
							int rand = randGen.nextInt(itemStackList.size());
							c.getInventory().setItem(i, itemStackList.get(rand));
						}
					}
				}
			}
		}
	}
	
	public void addItem(ItemStack is, int ammount) {
		synchronized (itemMap) {
			itemMap.put(is.getType(), ammount);
		}
	}
	
	public void removeItem(ItemStack is) {
		synchronized (itemMap) {
			itemMap.remove(is.getType());
		}
	}
	
	public HashMap<Material, Integer> getItemMap() {
		return itemMap;
	}

	public void setItemMap(HashMap<Material, Integer> itemMap) {
		this.itemMap = itemMap;
	}

	public Arena getA() {
		return a;
	}

	public void setA(Arena a) {
		this.a = a;
	}

	public ArrayList<Chest> getChestList() {
		return chestList;
	}

	public void setChestList(ArrayList<Chest> chestList) {
		this.chestList = chestList;
	}

	public ArrayList<ItemStack> getItemStackList() {
		return itemStackList;
	}

	public void setItemStackList(ArrayList<ItemStack> itemStackList) {
		this.itemStackList = itemStackList;
	}

}
