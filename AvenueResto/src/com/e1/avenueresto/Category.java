package com.e1.avenueresto;

import java.util.List;

public class Category 
{
	private int Id;
	private String Name;
	private int MenuTypeId;
	private List<Menu> Menus;
	
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getMenuTypeId() {
		return MenuTypeId;
	}
	public void setMenuTypeId(int menuTypeId) {
		MenuTypeId = menuTypeId;
	}
	public List<Menu> getMenus() {
		return Menus;
	}
	public void setMenus(List<Menu> menus) {
		Menus = menus;
	}
	
	
	

}
