package com.mrsun.MysteryShop;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MSSingleItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient ItemStack Item;
	private String Id;
	private List<String> Lore;
	String DisplayName;
	transient Map<Enchantment,Integer> enchant = new HashMap<Enchantment,Integer>();
	Map<Integer,Integer> Enchant = new HashMap<Integer,Integer>();
	private short Durability;
	boolean isEnchanted;
	public MSSingleItem(ItemStack item){
		Item = item;
		
	}
	
	//��ȡ���Ƿ�ħ
	public String getIsEnchant(){
		return isEnchanted?"yes":"no";
	}
	
	//����֧�ָ�ħ��
	@SuppressWarnings("deprecation")
	public void seri(){
		
		Durability = Item.getDurability();
		Id = Item.getType().toString();
		if(Item.getItemMeta().hasDisplayName()){
			DisplayName=Item.getItemMeta().getDisplayName();
		}
		if(Item.getItemMeta().hasLore()){
			Lore = Item.getItemMeta().getLore();
		}
		isEnchanted = Item.getItemMeta().hasEnchants();
		if(Item.getItemMeta().hasEnchants()){
			enchant = Item.getItemMeta().getEnchants();       //����Ʒ����ԭ��ħ����
			for(int i =0 ;i<enchant.size();i++){
				//ת��Ϊ���ִ洢
				Enchant.put(enchant.keySet().iterator().next().getId(), enchant.values().iterator().next());
			}
		}
		
		
		
	}
	
	@SuppressWarnings("deprecation")
	public void deseri(){
		
		Item = new ItemStack(Material.getMaterial(Id));
		Item.setDurability(Durability);
		ItemMeta im = Item.getItemMeta();
		if(!(DisplayName == null)){
			im.setDisplayName(DisplayName);
		}
		if(!(Lore == null)){
			im.setLore(Lore);
			
		}
		if(!(Enchant == null)){
			for(int i = 0;i<Enchant.size();i++){
				im.addEnchant(Enchantment.getById(Enchant.keySet().iterator().next()), Enchant.values().iterator().next(),true);
			}
		}
		
		Item.setItemMeta(im);
		
	}
	
	//��ȡItemStack��Ʒ
	public ItemStack getItem(){
		return Item;
	}
	
	

}
