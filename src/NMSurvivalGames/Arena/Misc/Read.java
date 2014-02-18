package NMSurvivalGames.Arena.Misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Material;

import NMSurvivalGames.Arena.Arena;

public class Read {

	private Arena a;

	public Read(Arena a) {
		setA(a);
	}
	
	public void read() {
		try {
			File file = new File(getA().getWorld().getName() + "/NMSurvivalGames.settings");

			if(file.exists()) {
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);	
	
				String currentLine;
				while((currentLine = br.readLine()) != null) {
					if(currentLine.equalsIgnoreCase("[ARENA SETTINGS]")) {
						while((currentLine = br.readLine()) != null && !currentLine.contains("[END]")) {
							if(currentLine.contains(": ")) {
								String[] line = currentLine.split(": ");
								if(line.length == 2) {
									if(line[0].equalsIgnoreCase("BREAK BLOCKS")) {
										getA().setBreakBlocks(Boolean.parseBoolean(line[1]));
									} else if(line[0].equalsIgnoreCase("PLACE BLOCKS")) {
										getA().setPlaceBlocks(Boolean.parseBoolean(line[1]));
									} else if(line[0].equalsIgnoreCase("MAX PLAYERS")) {
										getA().getPh().setMaxPlayers(Integer.parseInt(line[1]));
									} else if(line[0].equalsIgnoreCase("MAX SPECTATORS")) {
										getA().getPh().setMaxSpectators(Integer.parseInt(line[1]));
									} else {
										
									}
								}
							}
						}
					} else if(currentLine.equalsIgnoreCase("[ARENA CHESTS]")) {
						HashMap<Material, Integer> map = getA().getCh().getItemMap();
						while((currentLine = br.readLine()) != null && !currentLine.contains("[END]")) {
							System.out.println(currentLine);
							String[] line = currentLine.split(": ");
							if(line.length == 2) {
								Material mat = Material.getMaterial(line[0].toUpperCase());
								int numOf = Integer.parseInt(line[1]);
								if(mat != null) {
									map.put(mat, numOf);
								}
							}
						}
						getA().getCh().setItemMap(map);
					} else if(currentLine.equalsIgnoreCase("[PREMATCH COMMANDS]")) {
						getA().getPC().reset();
						while((currentLine = br.readLine()) != null && !currentLine.contains("[END]")) {
							String[] line = currentLine.split(": ");
							if(line.length == 2) {
								getA().getPC().addCommand(Integer.parseInt(line[0]), line[1]);
							}
						}
					} else if(currentLine.equalsIgnoreCase("[MATCH COMMANDS]")) {
						getA().getMC().reset();
						while((currentLine = br.readLine()) != null && !currentLine.contains("[END]")) {
							String[] line = currentLine.split(": ");
							if(line.length == 2) {
								getA().getMC().addCommand(Integer.parseInt(line[0]), line[1]);
							}
						}
					} else if(currentLine.equalsIgnoreCase("[PREDEATHMATCH COMMANDS]")) {
						getA().getPDC().reset();
						while((currentLine = br.readLine()) != null && !currentLine.contains("[END]")) {
							String[] line = currentLine.split(": ");
							if(line.length == 2) {
								getA().getPDC().addCommand(Integer.parseInt(line[0]), line[1]);
							}
						}
					} else if(currentLine.equalsIgnoreCase("[DEATHMATCH COMMANDS]")) {
						getA().getDC().reset();
						while((currentLine = br.readLine()) != null && !currentLine.contains("[END]")) {
							String[] line = currentLine.split(": ");
							if(line.length == 2) {
								getA().getDC().addCommand(Integer.parseInt(line[0]), line[1]);
							}
						}
					} else if(currentLine.equalsIgnoreCase("[ENDMATCH COMMANDS]")) {
						getA().getEC().reset();
						while((currentLine = br.readLine()) != null && !currentLine.contains("[END]")) {
							String[] line = currentLine.split(": ");
							if(line.length == 2) {
								getA().getEC().addCommand(Integer.parseInt(line[0]), line[1]);
							}
						}
					} 
				}

				br.close();
				fr.close();
			} else {
				getA().getW().write();
				read();
			}
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
