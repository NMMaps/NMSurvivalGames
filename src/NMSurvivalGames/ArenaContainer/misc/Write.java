package NMSurvivalGames.ArenaContainer.misc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.World;

import NMSurvivalGames.ArenaContainer.ArenaContainer;

public class Write {
	
	private ArenaContainer ac;
	
	public Write(ArenaContainer ac) {
		this.setAc(ac);
	}
	
	public void write() {
		try {
			File file = new File("plugins/NMSurvivalGames/container.settings");
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write("[ENABLED ARENAS]\r\n");
			for(World w : Bukkit.getWorlds()) {
				bw.write(w.getName() + ": " + ((getAc().getEnabled().get(w.getName()) != null) ? getAc().getEnabled().get(w.getName()) : false) + "\r\n");
			}
			
			bw.close();
			fw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void writeDefaults() {
		try {
			new File("plugins/NMSurvivalGames/").mkdirs();
			File file = new File("plugins/NMSurvivalGames/container.settings");
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write("[ENABLED ARENAS]\r\n");
			for(World w : Bukkit.getWorlds()) {
				bw.write(w.getName() + ": false\r\n");
			}
			
			bw.close();
			fw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public ArenaContainer getAc() {

		return ac;
	}

	public void setAc(ArenaContainer ac) {

		this.ac = ac;
	}

}
