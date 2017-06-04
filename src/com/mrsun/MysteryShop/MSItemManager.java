package com.mrsun.MysteryShop;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


//实现GUI编辑
public class MSItemManager implements Listener, CommandExecutor{
	Inventory Inv;
	public static String InvName;
	public static String Pname = new String();
	public static MSItem List;
    public void editItem(Player p,MSItem list){
    	Pname = p.getName();
    	
		List = list;
		InvName = list.getId() + "";
		
		Inv = Bukkit.createInventory(null, 54,InvName.toString());
		Material m;
		for(int i =0;i<List.getItems().size();i++){
			m = List.getItems().get(i).getItem().getType();
			if(m == Material.AIR ){
				continue;
			}
			Inv.addItem(list.getItems().get(i).getItem());
		}
		p.openInventory(Inv);
	}
    @EventHandler
    public void onInvClose(InventoryCloseEvent e){
    	Player p = (Player)e.getPlayer();
    	//p.sendMessage(Pname);
    	//p.sendMessage(InvName);
    	//Bukkit.broadcastMessage(p.getName());
    	//if(p.getName().equals(Pname)) p.sendMessage("T");else p.sendMessage("F");
    	//if(e.getInventory().getName().equals(InvName)) p.sendMessage("T");else p.sendMessage("F");
    	if(p.getName().equals(Pname)){
    		//p.sendMessage("Y");
    		if(e.getInventory().getName().equals(InvName.toString())){
    			
    			//p.sendMessage("Y");
    			ArrayList<MSSingleItem> l = new ArrayList<MSSingleItem>();
    			ItemStack item;
    			for(int i = 0;i<53;i++){
    				item =  e.getInventory().getItem(i);
    				
    				if(!(e.getInventory().getItem(i) == null)){
    					p.sendMessage(item.toString());
    					l.add(new MSSingleItem(e.getInventory().getItem(i)));
    				}
    			}
    			p.sendMessage(l.size()+"");
    			List.editItems(l);
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
    				//p.sendMessage(args);
    				if(args.length == 4){
    					if(shopMain.Items.getItem(Integer.parseInt(args[1])) == null){
    						p.sendMessage(new StringBuilder().append("§c").append("编辑失败：随机项不存在").toString());
    						return true;
    					}
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
    		if(args[0].equalsIgnoreCase("get")){
    			Player p = (Player)sender;
    			int id = Integer.parseInt(args[1]);
    			ItemStack i = shopMain.Items.getItem(id).getNowItem();
    			if(i == null) p.sendMessage("F");
    			p.sendMessage(i.toString());
    		}
    		
    	}
    	return true;
    }
   

}
