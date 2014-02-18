package NMSurvivalGames.Arena.Misc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.Material;

import NMSurvivalGames.Arena.Arena;

public class Write {

	private Arena a;

	public Write(Arena a) {
		setA(a);
	}

	public void write() {
		try {
			File file = new File(getA().getWorld().getName() + "/NMSurvivalGames.settings");
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write("[ARENA SETTINGS]\r\n");
			bw.write("BREAK BLOCKS: " + getA().isBreakBlocks() + "\r\n");
			bw.write("PLACE BLOCKS: " + getA().isPlaceBlocks() + "\r\n");
			bw.write("MAX PLAYERS: " + getA().getPh().getMaxPlayers() + "\r\n");
			bw.write("MAX SPECTATORS: " + getA().getPh().getMaxSpectators() + "\r\n");
			bw.write("[END]\r\n\r\n");
			
			bw.write("[ARENA CHESTS]\r\n");
			for(Material mat : getA().getCh().getItemMap().keySet()) {
				bw.write(mat + ": " + getA().getCh().getItemMap().get(mat) + "\r\n");
			}
			bw.write("[END]\r\n\r\n");
			
			bw.write("[PREMATCH COMMANDS]\r\n");
			for(Integer i : getA().getPC().getCommands().keySet()) {
				if(getA().getPC().getCommands().get(i) != null) {
					for(String str : getA().getPC().getCommands().get(i)) {
						bw.write(i + ": " + str + "\r\n");
					}
				}
			}
			bw.write("[END]\r\n\r\n");
			
			bw.write("[MATCH COMMANDS]\r\n");
			for(Integer i : getA().getMC().getCommands().keySet()) {
				if(getA().getMC().getCommands().get(i) != null) {
					for(String str : getA().getMC().getCommands().get(i)) {
						bw.write(i + ": " + str + "\r\n");
					}
				}
			}
			bw.write("[END]\r\n\r\n");
			
			bw.write("[PREDEATHMATCH COMMANDS]\r\n");
			for(Integer i : getA().getPDC().getCommands().keySet()) {
				if(getA().getPDC().getCommands().get(i) != null) {
					for(String str : getA().getPDC().getCommands().get(i)) {
						bw.write(i + ": " + str + "\r\n");
					}
				}
			}
			bw.write("[END]\r\n\r\n");
			
			bw.write("[DEATHMATCH COMMANDS]\r\n");
			for(Integer i : getA().getDC().getCommands().keySet()) {
				if(getA().getDC().getCommands().get(i) != null) {
					for(String str : getA().getDC().getCommands().get(i)) {
						bw.write(i + ": " + str + "\r\n");
					}
				}
			}
			bw.write("[END]\r\n\r\n");
			
			bw.write("[ENDMATCH COMMANDS]\r\n");
			for(Integer i : getA().getEC().getCommands().keySet()) {
				if(getA().getEC().getCommands().get(i) != null) {
					for(String str : getA().getEC().getCommands().get(i)) {
						bw.write(i + ": " + str + "\r\n");
					}
				}
			}
			bw.write("[END]\r\n\r\n");
			
			bw.write("[REPEAT COMMANDS]\r\n");
			bw.write("[END]\r\n\r\n");
			
			bw.write("[START LOCATIONS]\r\n");
			for(Location loc : getA().getSML().getLocations()) {
				System.out.println(loc.getWorld().getName() + " : " + loc.getX() + " : " + loc.getY() + " : " + loc.getZ() + " : " + (int) loc.getYaw() + " : " + (int) loc.getPitch());
				bw.write(loc.getWorld().getName() + " : " + loc.getX() + " : " + loc.getY() + " : " + loc.getZ() + " : " + (int) loc.getYaw() + " : " + (int) loc.getPitch() +"\t\n");
			}
			bw.write("[END]\r\n\r\n");
			
			bw.write("[DEATHMATCH LOCATIONS]\r\n");
			for(Location loc : getA().getDML().getLocations()) {
				System.out.println(loc.getWorld().getName() + " : " + loc.getX() + " : " + loc.getY() + " : " + loc.getZ() + " : " + (int) loc.getYaw() + " : " + (int) loc.getPitch());
				bw.write(loc.getWorld().getName() + " : " + loc.getX() + " : " + loc.getY() + " : " + loc.getZ() + " : " + (int) loc.getYaw() + " : " + (int) loc.getPitch() +"\t\n");
			}
			bw.write("[END]\r\n\r\n");
			
			bw.close();
			fw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
	
	public void writeDefaults() {
		try {
			File file = new File(getA().getWorld().getName() + "/NMSurvivalGames.settings");
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("[ARENA SETTINGS]\r\n");
			bw.write("BREAK BLOCKS: false\r\n");
			bw.write("PLACE BLOCKS: false\r\n");
			bw.write("MAX PLAYERS: 24\r\n");
			bw.write("MAX SPECTATORS: 4\r\n");
			bw.write("[END]\r\n\r\n");
			
			bw.write("[ARENA CHESTS]\r\n");
			bw.write("[END]\r\n\r\n");
			
			bw.write("[PREMATCH COMMANDS]\r\n");
			bw.write("[END]\r\n\r\n");
			
			bw.write("[MATCH COMMANDS]\r\n");
			bw.write("[END]\r\n\r\n");
			
			bw.write("[DEATHMATCH COMMANDS]\r\n");
			bw.write("[END]\r\n\r\n");
			
			bw.write("[ENDMATCH COMMANDS]\r\n");
			bw.write("[END]\r\n\r\n");
			
			bw.write("[REPEAT COMMANDS]\r\n");
			bw.write("[END]\r\n\r\n");
			
			bw.write("[START LOCATIONS]\r\n");
			bw.write("[END]\r\n\r\n");
			
			bw.write("[DEATHMATCH LOCATIONS]\r\n");
			bw.write("[END]\r\n\r\n");

			bw.close();
			fw.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Arena getA() {
		return a;
	}

	public void setA(Arena a) {
		this.a = a;
	}

}