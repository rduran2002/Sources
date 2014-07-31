package com.e1.avenueresto;

import java.util.List;

public class MenuGroup  {
	
	private int Id;
	private String GCode;
	private String Name;
	private String Type;
	private int Priority;
	private boolean Status;
	private List<Menu> Menus;
	
	public List<Menu> getMenus() {
		return Menus;
	}
	public void setMenus(List<Menu> menus) {
		Menus = menus;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getGCode() {
		return GCode;
	}
	public void setGCode(String gcode) {
		GCode = gcode;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public int getPriority() {
		return Priority;
	}
	public void setPriority(int priority) {
		Priority = priority;
	}
	public boolean isStatus() {
		return Status;
	}
	public void setStatus(boolean status) {
		Status = status;
	}
	
	
	
	 

		       


}
