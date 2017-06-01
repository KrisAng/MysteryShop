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
	StringBuilder InvName = new StringBuilder();
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
    		stone.setAmount(1);
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
    				//mshop create [id] [price] [delay]
    				Player p = (Player)sender;
    				if(args.length == 4){
    					boolean isSucc = createItem((Player)sender,Integer.parseInt(args[1]),Double.parseDouble(args[2]),Integer.parseInt(args[3]));
    			        if(isSucc == true){
        					p.sendMessage(new StringBuilder().append("§a").append("创建成功!").append("该随机项目编号为 ").append("§c").append(args[1]).append("§a").append(" 请使用/mshop edit指令编辑").toString());
    			        }else{
    			        	p.sendMessage(new StringBuilder().append("§c").append("创建失败：物品编号与现有物品重复").toString());
    			        }
    			        return true;
    				}else{
    					p.sendMessage(new StringBuilder().append("§c").append("创建失败：缺少参数").toString());
    				}    			    
    		    }
    			if(args[0].equalsIgnoreCase("edit")){
    				//mshop edit [id] %price% %delay%
    				Player p = (Player)sender;
    				p.sendMessage(args);
    				if(args.length == 4){
    					shopMain.Items.getItem(Integer.parseInt(args[1])).setPrice(Double.parseDouble(args[2]));
    					shopMain.Items.getItem(Integer.parseInt(args[1])).setDelay(Integer.parseInt(args[3]));
    					editItem((Player)sender,shopMain.Items.getItem(Integer.parseInt(args[1])));
    					return true;
    				}else{
    					p.sendMessage(new StringBuilder().append("§c").append("编辑失败：缺少参数").toString());
    					return true;
    				}
    				
    			}
    			if(args[0].equalsIgnoreCase("delete")){
    				//mshop delete [id]
    				Player p = (Player)sender;
    				if(args.length == 2){
    					shopMain.Items.removeItem(shopMain.Items.getItem(Integer.parseInt(args[1])));
    					p.sendMessage(new StringBuilder().append("§a").append("删除成功").toString());
    				    return true;
    				}else{
    					p.sendMessage(new StringBuilder().append("§c").append("删除失败：缺少参数").toString());
    					return true;
    				}
    				
    			}
    			if(args[0].equalsIgnoreCase("list")){
    				//mshop list
    				Player p = (Player)sender;
    				p.sendMessage(shopMain.Items.toString());
    			}
    		}
    		
    	}
    	return true;
    }
   

}
