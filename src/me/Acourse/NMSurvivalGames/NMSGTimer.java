package me.Acourse.NMSurvivalGames;

import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class NMSGTimer {

	Main main;
	Thread timer;
	BukkitScheduler sch;

	oneCommandThread oct;
	repeatCommandThread rct;
	deathmatchCommandThread dct;

	Vector<Command> onecommands = new Vector<Command>();
	Vector<Command> repeatcommands = new Vector<Command>();
	Vector<Command> deathmatchcommands = new Vector<Command>();

	Vector<Integer> onecommandsids = new Vector<Integer>();
	Vector<Integer> repeatcommandsids = new Vector<Integer>();
	Vector<Integer> deathmatchcommandsids = new Vector<Integer>();

	NMSGTimer(Main main) {

		this.main = main;
		oct = new oneCommandThread(this);
		rct = new repeatCommandThread(this);
		dct = new deathmatchCommandThread(this);
	}

	//

	public void addOneCommand(int timesec, String command) {

		Command temp = new Command(timesec, command);
		onecommands.add(temp);

		main.getConfig().set("onecommands." + onecommands.indexOf(temp) + ".time", timesec);
		main.getConfig().set("onecommands." + onecommands.indexOf(temp) + ".command", command);
		main.getConfig().set("onecommands.size", onecommands.size());
	}

	public void listOneCommands() {

		for (int i = 0; i < onecommands.size(); i++) {
			main.getLogger().info(i + ": @" + onecommands.get(i).time + " " + onecommands.get(i).command);
		}
	}

	public void removeOneCommand(int index) {

		onecommands.remove(index);
		main.getConfig().set("onecommands." + index, null);
		main.getConfig().set("onecommands." + index, null);
		main.getConfig().set("onecommands.size", onecommands.size());

	}

	public void removeOneCommand(String all) {

		if (all.equalsIgnoreCase("all")) {
			onecommands.clear();
			main.getConfig().set("onecommands", null);
			main.getConfig().set("onecommands.size", onecommands.size());
		}
	}

	public Vector<Command> getOneCommand() {

		return onecommands;
	}

	public void runOneCommands() {

		stopOneCommands();
		oct.start();
	}

	public void stopOneCommands() {

		for (int i = 0; i < onecommandsids.size(); i++) {
			main.getServer().getScheduler().cancelTask(onecommandsids.get(i));
		}
	}

	//

	public void addRepeatCommand(int startdelay, int repeatdelay, String command) {

		Command temp = new Command(startdelay, repeatdelay, command);
		repeatcommands.add(temp);

		main.getConfig().set("repeatcommands." + repeatcommands.indexOf(temp) + ".startdelay", startdelay);
		main.getConfig().set("repeatcommands." + repeatcommands.indexOf(temp) + ".repeatdelay", repeatdelay);
		main.getConfig().set("repeatcommands." + repeatcommands.indexOf(temp) + ".command", command);
		main.getConfig().set("repeatcommands.size", repeatcommands.size());
	}

	public void listRepeatCommands() {

		for (int i = 0; i < repeatcommands.size(); i++) {
			main.getLogger().info(i + ": @" + repeatcommands.get(i).startdelay + " " + repeatcommands.get(i).repeatdelay + " " + repeatcommands.get(i).command);
		}
	}

	public void removeRepeatCommand(int index) {

		repeatcommands.remove(index);
		main.getConfig().set("repeatcommands." + index, null);
		main.getConfig().set("repeatcommands.size", repeatcommands.size());

	}

	public void removeRepeatCommand(String all) {

		if (all.equalsIgnoreCase("all")) {
			repeatcommands.clear();
			main.getConfig().set("repeatcommands", null);
			main.getConfig().set("repeatcommands.size", repeatcommands.size());
		}
	}

	public Vector<Command> getRepeatCommand() {

		stopOneCommands();
		return repeatcommands;
	}

	public void runRepeatCommands() {

		stopRepeatCommands();
		rct.start();
	}

	public void stopRepeatCommands() {

		for (int i = 0; i < repeatcommandsids.size(); i++) {
			main.getServer().getScheduler().cancelTask(repeatcommandsids.get(i));
		}
	}

	//

	public void addDeathmatchCommand(int timesec, String command) {

		Command temp = new Command(timesec, command);
		deathmatchcommands.add(temp);

		main.getConfig().set("deathmatchcommands." + deathmatchcommands.indexOf(temp) + ".time", timesec);
		main.getConfig().set("deathmatchcommands." + deathmatchcommands.indexOf(temp) + ".command", command);
		main.getConfig().set("deathmatchcommands.size", deathmatchcommands.size());
	}

	public void listDeathmatchCommands() {

		for (int i = 0; i < deathmatchcommands.size(); i++) {
			main.getLogger().info(i + ": @" + deathmatchcommands.get(i).time + " " + deathmatchcommands.get(i).command);
		}
	}

	public void removeDeathmatchCommand(int index) {

		deathmatchcommands.remove(index);
		main.getConfig().set("deathmatchcommands." + index, null);
		main.getConfig().set("deathmatchcommands." + index, null);
		main.getConfig().set("deathmatchcommands.size", deathmatchcommands.size());

	}

	public void removeDeathmatchCommand(String all) {

		if (all.equalsIgnoreCase("all")) {
			deathmatchcommands.clear();
			main.getConfig().set("deathmatchcommands", null);
			main.getConfig().set("deathmatchcommands.size", deathmatchcommands.size());
		}
	}

	public Vector<Command> getDeathmatchCommand() {

		return deathmatchcommands;
	}

	public void runDeathmatchCommands() {

		stopOneCommands();
		stopDeathmatchCommands();
		dct.start();
	}

	public void stopDeathmatchCommands() {

		for (int i = 0; i < deathmatchcommandsids.size(); i++) {
			main.getServer().getScheduler().cancelTask(deathmatchcommandsids.get(i));
		}
	}

}

class Command {

	int time;
	String command;

	int startdelay;
	int repeatdelay;

	public Command(int time, String command) {

		this.time = time;
		this.command = command;
	}

	public Command(int startdelay, int repeatdelay, String command) {

		this.command = command;
		this.startdelay = startdelay;
		this.repeatdelay = repeatdelay;
	}

	public int getTime() {

		return time;
	}

	public String getCommand() {

		return command;
	}

	public int getStartdelay() {

		return startdelay;
	}

	public int getRepeatdelay() {

		return repeatdelay;
	}
}

class oneCommandThread extends BukkitRunnable {

	NMSGTimer ti;
	BukkitScheduler sch;

	oneCommandThread(NMSGTimer ti) {

		this.ti = ti;
		sch = getTi().main.getServer().getScheduler();
	}

	public void start() {

		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {

		sch = getTi().main.getServer().getScheduler();
		for (final Command c : getTi().onecommands) {
			int ID = sch.scheduleSyncDelayedTask(getTi().main, new Runnable() {

				public void run() {

					Bukkit.dispatchCommand(getTi().main.getServer().getConsoleSender(), c.command);
				}
			}, 20 * c.time);

			getTi().onecommandsids.add(ID);
		}
	}

	private NMSGTimer getTi() {

		return ti;
	}

}

class deathmatchCommandThread extends BukkitRunnable {

	NMSGTimer ti;
	BukkitScheduler sch;

	deathmatchCommandThread(NMSGTimer ti) {

		this.ti = ti;
		sch = getTi().main.getServer().getScheduler();
	}

	public void start() {

		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {

		for (final Command c : getTi().deathmatchcommands) {
			int ID = sch.scheduleSyncDelayedTask(getTi().main, new Runnable() {

				@Override
				public void run() {

					Bukkit.dispatchCommand(getTi().main.getServer().getConsoleSender(), c.command);
				}

			}, 20 * c.time);
			getTi().deathmatchcommandsids.add(ID);
		}
	}

	public NMSGTimer getTi() {

		return ti;
	}
}

class repeatCommandThread extends BukkitRunnable {

	NMSGTimer ti;
	BukkitScheduler sch;

	repeatCommandThread(NMSGTimer ti) {

		this.ti = ti;
		sch = getTi().main.getServer().getScheduler();
	}

	public void start() {

		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {

		ti.main.getLogger().info("Running repeated commands");
		for (final Command c : getTi().repeatcommands) {
			int ID = sch.scheduleSyncRepeatingTask(getTi().main, new Runnable() {

				public void run() {

					Bukkit.dispatchCommand(getTi().main.getServer().getConsoleSender(), c.command);
				}
			}, 20 * c.startdelay, 20 * c.repeatdelay);
			ti.repeatcommandsids.add(ID);
		}
	}

	private NMSGTimer getTi() {

		return ti;
	}
}