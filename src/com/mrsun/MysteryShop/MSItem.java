package com.mrsun.MysteryShop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;


//在MSItem类构造时启动异步计时器
public class MSItem implements Serializable{
	private ArrayList<MSSingleItem> ItemList = new ArrayList<MSSingleItem>(); //参与随机的物品
	private MSSingleItem ItemNow; //当前物品
	private double Price; //所有物品价格
	private int Delay; //随机延时
	private int rTime; //距离下一次随机剩余时间
	private int Id; //随机项目ID
	private Random r = new Random();
	
	public void RandomIt(){
		if(!ItemList.isEmpty()){
			ItemNow = ItemList.get(r.nextInt(ItemList.size())-1);
		}
		
	}
	
	public MSItem(ItemStack[] itemlist,double price,int delay,int id){
		if(!(itemlist==null)){
			for(int i =0;i<itemlist.length;i++){
			ItemList.add(new MSSingleItem(itemlist[i]));
		}
		}
		
		Price = price;
		Delay = delay;
		rTime = delay;
		Id = id;
		
		BukkitTask randomTask = new BukkitRunnable() { //随机进程
			@Override
			public void run() {
				RandomIt();
				if(rTime == 0){
					rTime = Delay;
				}
				rTime--;
				
			}
		}.runTaskTimerAsynchronously(Bukkit.getPluginManager().getPlugin("MysteryShop"), Delay*20, Delay*20);
		
		
		
	}
	//编辑随机物品s
	public void editItems(ItemStack[] itemlist){
		ItemList.clear();
		for(int i =0;i<itemlist.length;i++){
			ItemList.add(new MSSingleItem(itemlist[i]));
		}
		
		
	}
	public void setPrice(double price){
		Price = price;
	}
	public void setDelay(int delay){
		Delay = delay;
	}
	public ArrayList<MSSingleItem> getItems(){
		return ItemList;
	}
	public int getId(){
		return Id;
	}
	public double getPrice(){
		return Price;
	}
	public int getrTime(){
		return rTime;
	}
	public int getDelay(){
		return Delay;
	}
	public ItemStack getNowItem(){
		return ItemNow.getItem();
	}
	

}
