package com.mrsun.MysteryShop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class MSItemList implements Serializable{
	public ArrayList<MSItem> Items = new ArrayList<MSItem>();
	//���췽��
	public MSItemList(){
		
	}
	
	
	//��ȡָ��ID����Ʒ
	public MSItem getItem(int id){
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for(int i =0;i<Items.size();i++){
			ids.add(Items.get(i).getId());
		}
		for(int i =0;i<ids.size();i++){
			if(ids.get(i) == id){
				return Items.get(i);
			}
			
		}
		return null;
	
		
	}
	//��������
	public void addItem(MSItem item){
		Items.add(item);
	}
	
	//�Ƴ������
	public void removeItem(MSItem item){
		Items.remove(item);
	}
	
	

}
