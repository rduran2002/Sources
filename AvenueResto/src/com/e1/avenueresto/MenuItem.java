package com.e1.avenueresto;


public class MenuItem 
{
	public int Id;
	public String IsServed;
	public String Preparable;
	public String Rejected;
	public int OrderId;
	public String ServedBy;
	public Order Order;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getIsServed() {
		return IsServed;
	}
	public void setIsServed(String isServed) {
		IsServed = isServed;
	}
	public String getPreparable() {
		return Preparable;
	}
	public void setPreparable(String preparable) {
		Preparable = preparable;
	}
	public String getRejected() {
		return Rejected;
	}
	public void setRejected(String rejected) {
		Rejected = rejected;
	}
	public int getOrderId() {
		return OrderId;
	}
	public void setOrderId(int orderId) {
		OrderId = orderId;
	}
	public String getServedBy() {
		return ServedBy;
	}
	public void setServedBy(String servedBy) {
		ServedBy = servedBy;
	}
	public Order getOrder() {
		return Order;
	}
	public void setOrder(Order order) {
		Order = order;
	}
	
	
}
