package com.mrsun.MysteryShop;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_7_R1.Material;

//实现GUI编辑
public class MSItemManager implements Listener, CommandExecutor{
	Inventory Inv;
	StringBuilder InvName;
	Player P;
	MSItem List;
    public void editItem(Player p,MSItem list){
    	P = p;
    	List = list;
		InvName.append(Color.YELLOW).append(list.getId());
		Inv = Bukkit.createInventory(null, 54,InvName.toString());
		for(int i =0;i<list.getItems().size();i++){
			Inv.addItem(list.getItems().get(i).getItem());
		}
		p.openInventory(Inv);
	}
    @EventHandler
    public void onInvClose(InventoryCloseEvent e){
    	if(((Player)e.getPlayer()).equals(P)){
    		if(e.getInventory().getName().equals(InvName.toString())){
    			List.editItems(e.getInventory().getContents());
    		}
    	}
    }
    //创建
    //记得先检测ID是否重复！√ 
    public static boolean createItem(Player p,int id,double price,int delay){
    	if(shopMain.Items.getItem(id) == null){
    		ItemStack stone = new ItemStack(org.bukkit.Material.STONE);
    	ArrayList<ItemStack> items = new ArrayList<ItemStack>();
    	items.add(stone);
    	ItemStack[] itemsS = new ItemStack[items.size()];
    	for(int i =0;i<items.size();i++){
    		itemsS[i] = items.get(i);
    	}
    	MSItem item = new MSItem(itemsS,price,delay,id);
    	shopMain.Items.addItem(item);  //添加到列表
    	
    	return true;
    	}
    	return false;
    	
    	
    }
    
    //文件处理
    
    
    
    //命令侦听
    public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args){
    	if(label.equalsIgnoreCase("mshop")){
    		if(sender.hasPermission("MysteryShop.edit") | sender.isOp()){
    			if(args[0].equalsIgnoreCase("create")){
    				Player p = (Player)sender;
    			    //mshop create [id] [price] [delay]
    				if(args[1].isEmpty() | args[2].isEmpty() | args[3].isEmpty()){
    					p.sendMessage(new StringBuilder().append(Color.RED).append("创建失败：缺少参数").toString());
    					return true;
    				}
    			    createItem((Player)sender,Integer.parseInt(args[1]),Double.parseDouble(args[2]),Integer.parseInt(args[3]));
    			    
    			    p.sendMessage(new StringBuilder().append(Color.GREEN).append("创建成功!").append("该随机项目编号为 ").append(Color.RED).append(args[1]).append(Color.GREEN).append(" 请使用/mshop edit指令编辑").toString());
    			    return true;
    		    }
    			if(args[0].equalsIgnoreCase("edit")){
    				//mshop edit [id] %price% %delay%
    				if(!args[1].isEmpty()){
    					shopMain.Items.getItem(Integer.parseInt(args[0])).setPrice(Double.parseDouble(args[1]));
    				}
    				if(!args[2].isEmpty()){
    					shopMain.Items.getItem(Integer.parseInt(args[0])).setDelay(Integer.parseInt(args[2]));
    				}
    				editItem((Player)sender,shopMain.Items.getItem(Integer.parseInt(args[1])));
    				return true;
    			}
    			if(args[0].equalsIgnoreCase("delete")){
    				//mshop delete [id]
    				Player p = (Player)sender;
    				if(args[1].isEmpty()){
    					p.sendMessage(new StringBuilder().append(Color.RED).append("删除失败：缺少参数").toString());
    					return true;
    				}
    				shopMain.Items.removeItem(shopMain.Items.getItem(Integer.parseInt(args[1])));
    				return true;
    			}
    		}
    		
    	}
    	return true;
    }
   

}
