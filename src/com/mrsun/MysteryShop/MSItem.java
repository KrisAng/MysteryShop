package com.mrsun.MysteryShop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;


//��MSItem�๹��ʱ�����첽��ʱ��
public class MSItem implements Serializable{
	private ArrayList<MSSingleItem> ItemList = new ArrayList<MSSingleItem>(); //�����������Ʒ
	private MSSingleItem ItemNow; //��ǰ��Ʒ
	private double Price; //������Ʒ�۸�
	private int Delay; //�����ʱ
	private int rTime; //������һ�����ʣ��ʱ��
	private int Id; //�����ĿID
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
		
		BukkitTask randomTask = new BukkitRunnable() { //�������
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
	//�༭�����Ʒs
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
