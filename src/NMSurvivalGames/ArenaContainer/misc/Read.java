package NMSurvivalGames.ArenaContainer.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import NMSurvivalGames.ArenaContainer.ArenaContainer;

public class Read {

	private ArenaContainer ac;

	public Read(ArenaContainer ac) {
		setAc(ac);
	}
	
	public void read() {
		try {
			File file = new File("plugins/NMSurvivalGames/container.settings");
			if(file.exists()) {
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);

				String currentLine;
				while((currentLine = br.readLine()) != null) {
					if(currentLine.equalsIgnoreCase("[ENABLED ARENAS]")) {
						while((currentLine = br.readLine()) != null && currentLine != "" && currentLine != "\r\n") {
							if(currentLine.contains(": ")) {
								String[] splits = currentLine.split(": ");
								getAc().getEnabled().put(splits[0], Boolean.parseBoolean(splits[1]));
							}
						}
					} else {
						
					}
				}
				
				fr.close();
				br.close();
			} else {
				System.out.println("Loading and Saving defaults container values. All worlds set to no Arenas");
				getAc().getW().writeDefaults();
				read();
			}
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
