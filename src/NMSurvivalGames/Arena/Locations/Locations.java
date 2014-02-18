package NMSurvivalGames.Arena.Locations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import NMSurvivalGames.Arena.Arena;

public class Locations {
	
	private Arena a;
	
	private ArrayList<Location> locations = new ArrayList<Location>();
	
	public Locations(Arena a) {
		setA(a);
	}
	
	public boolean addLocation(Location loc) {
		loc.setX(((int) loc.getX()) + .5);
		loc.setY(((int) loc.getY()));
		loc.setZ(((int) loc.getZ()) + .5);
		if(!locations.contains(loc)) {
			synchronized (locations) {
				locations.add(loc);
				return true;
			}
		} else {
			return false;
		}
	}
	
	public boolean removeLocation(Location loc) {
		loc.setX(((int) loc.getX()) + .5);
		loc.setY(((int) loc.getY()));
		loc.setZ(((int) loc.getZ()) + .5);
		if(locations.contains(loc)) {
			synchronized (locations) {
				locations.remove(loc);
				return true;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Teleports each player in the list to a random poing in the locations list
	 * does not teleport if there are more players than locations, somehow fix this?
	 * 
	 * @param hashSet
	 * @return
	 */
	public boolean teleport(HashSet<String> hashSet) {
		synchronized (locations) {
			synchronized (hashSet) {
				if(hashSet.size() < locations.size()) {
					boolean tempfreeze = getA().isFrozen();
					getA().setFrozen(false);
					ArrayList<Location> templist = new ArrayList<Location>(locations);
					Iterator<String> i = hashSet.iterator();
					int index = 0;
					while(i.hasNext()) {
						Bukkit.getPlayerExact(i.next()).teleport(templist.get(index));
						index++;
					}
					Bukkit.getLogger().info("Players in alive were all teleported");
					getA().setFrozen(tempfreeze);
					return true;
				} else {
					Bukkit.getLogger().info("There are more players than locations, cannot teleport them all!");
					return false;
				}
			}
		}
	}
	
	public ArrayList<Location> getLocations() {
		return locations;
	}

	public Arena getA() {
		return a;
	}

	public void setA(Arena a) {
		this.a = a;
	}
}
