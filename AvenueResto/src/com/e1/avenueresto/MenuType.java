package com.e1.avenueresto;

import java.util.List;

public class MenuType
{
	private int Id;
	private String Name;
	private String Description;
	private List<Category> Categories;
	private int drawableId;
	
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
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public List<Category> getCategories() {
		return Categories;
	}
	public void setCategories(List<Category> categories) {
		Categories = categories;
	}
	
	public int getdrawableId() {
		return drawableId;
	}
	public void setdrawableId(int drawableId) {
		this.drawableId = drawableId;
	}
	
	
	
}
