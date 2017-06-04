package com.mrsun.MysteryShop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class MSItemList implements Serializable{
	public ArrayList<MSItem> Items = new ArrayList<MSItem>();
	//构造方法
	public MSItemList(){
		
	}
	
	
	//获取指定ID的项
	public MSItem getItem(int id){
		for(int i =0;i<Items.size();i++){
			if(Items.get(i).getId()== id){
				
					return (MSItem) Items.get(i);
			}
			
		}
		return null;
	
		
	}
	//添加随机项
	public void addItem(MSItem item){
		Items.add(item);
	}
	
	//移除随机项
	public void removeItem(MSItem item){
		Items.remove(item);
	}
	public void readItems(){
		for(int i = 0;i<Items.size();i++){
			Items.get(i).readStacks();
		}
		
		
	}
	public void saveItems(){
		for(int i = 0;i<Items.size();i++){
			Items.get(i).saveStacks();
		}
	}
	
	

}
