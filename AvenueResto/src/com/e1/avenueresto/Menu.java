package com.e1.avenueresto;

import java.util.List;

public class Menu 
{
	private int Id;
	private String Code;
	private String Name;
	private double Price;
	private boolean Status;
	private String GCode;
	private boolean Vatable;
	private String Station;
	private double Cost;
	private double MealPrice;
	private int Quantity;

	private List<MenuItemRemark> Remarks;
	
	
	
	public List<MenuItemRemark> getRemarks() {
		return Remarks;
	}
	
	public void setRemarks(List<MenuItemRemark> remarks) {
		Remarks = remarks;
	}
	
	
	public int getQuantity() {
		return Quantity;
	}
	
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public double getPrice() {
		return Price;
	}
	public void setPrice(double price) {
		Price = price;
	}
	public boolean isStatus() {
		return Status;
	}
	public void setStatus(boolean status) {
		Status = status;
	}
	public String getGCode() {
		return GCode;
	}
	public void setGCode(String gCode) {
		GCode = gCode;
	}
	public boolean isVatable() {
		return Vatable;
	}
	public void setVatable(boolean vatable) {
		Vatable = vatable;
	}
	public String getStation() {
		return Station;
	}
	public void setStation(String station) {
		Station = station;
	}
	public double getCost() {
		return Cost;
	}
	public void setCost(double cost) {
		Cost = cost;
	}
	public double getMealPrice() {
		return MealPrice;
	}
	public void setMealPrice(double mealPrice) {
		MealPrice = mealPrice;
	}

	
}
