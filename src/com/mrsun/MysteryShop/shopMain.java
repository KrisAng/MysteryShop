package com.mrsun.MysteryShop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class shopMain extends JavaPlugin{
	ObjectInputStream FileIn;
	ObjectOutputStream FileOut;
	File ListFile = new File(getDataFolder().getAbsolutePath()+"Data.dat");
	public static HashMap<String,String> Language = new HashMap<String,String>(); 
	public static MSItemList Items = new MSItemList();
	
	public void loadLanguage(){
		Map<String,Object> m  = getConfig().getValues(false);
		for(int i = 0;i<m.size();i++){
			Language.put(m.keySet().iterator().next(), m.get(i).toString());
		}
	}
	
	@Override
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(new MSItemManager(), this);
		getCommand("mshop").setExecutor(new MSItemManager());
		if(ListFile.exists()){
			readFile();
			Items.readItems();
		}
		
		//loadLanguage();
		
		getLogger().info("Enabled!");
	}
	
	public void saveFile(){
		try {
			FileOut = new ObjectOutputStream(new FileOutputStream(ListFile));
			FileOut.writeObject(Items);
		
		} catch (IOException e) {}
	}
	public void readFile(){
		try {
			FileIn = new ObjectInputStream(new FileInputStream(ListFile));
			Items = (MSItemList)FileIn.readObject();
		} catch (IOException | ClassNotFoundException e) {}
	}
	@Override
	public void onDisable(){
		Items.saveItems();
		saveFile();
		
	}
	public Plugin getThis(){
		return this;
	}
	public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args)  {
		return false;
		
	}

}
