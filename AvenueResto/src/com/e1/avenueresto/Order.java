package com.e1.avenueresto;
import java.util.*;



public class Order
{
	
	private String TableNumber;
	private String OrderType;
	private List<Menu> MenuItems;
	private List<OrderDetail> OrderDetails;
    private String Customer;
    private String CustomerAddress;
	private String OrderDate;
	private String OrderNumber;
	private String SalesType;
	private String Pax;
	private String Crew;
	private String InvoiceNumber;
	private boolean IsSubmitted;
	
	public boolean getIsSubmitted() {
		return IsSubmitted;
	}

	public List<OrderDetail> getOrderDetails() {
		return OrderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		OrderDetails = orderDetails;
	}

	public void setIsSubmitted(boolean isSubmitted) {
		IsSubmitted = isSubmitted;
	}

	public String getInvoiceNumber() {
		return InvoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		InvoiceNumber = invoiceNumber;
	}

	public String getCrew() {
		return Crew;
	}

	public void setCrew(String crew) {
		Crew = crew;
	}

	public String getPax() {
		return Pax;
	}

	public void setPax(String pax) {
		Pax = pax;
	}

	public String getSalesType() {
		return SalesType;
	}

	public void setSalesType(String salesType) {
		SalesType = salesType;
	}

	public String getCustomerAddress() {
		return CustomerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		CustomerAddress = customerAddress;
	}

	public String getOrderType() {
		return OrderType;
	}

	public void setOrderType(String orderType) {
		OrderType = orderType;
	}
	
	public String getOrderDate() {
		return OrderDate;
	}

	public void setOrderDate(String orderDate) {
		OrderDate = orderDate;
	}

	public String getOrderNumber() {
		return OrderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		OrderNumber = orderNumber;
	}

	public String getCustomer() {
		return Customer;
	}

	public void setCustomer(String customer) {
		Customer = customer;
	}

	public String getTableNumber() {
		return TableNumber;
	}
	
	public void setTableNumber(String tableNumber) {
		TableNumber = tableNumber;
	}	
	
	/*public String getIsCancelled() {
		return IsCancelled;
	}
	public void setIsCancelled(String isCancelled) {
		IsCancelled = isCancelled;
	}	
	*/
	public List<Menu> getMenuItems() {
		return MenuItems;
	}
	
	public void setMenuItems(List<Menu> menuItems) {
		MenuItems = menuItems;
	}
	
	/*@Override
	public String toString() {
	   return "order [TableNumber=" + TableNumber + ", IsCancelled=" + IsCancelled + ", IsTakeOut="
		+ IsTakeOut + ", MenuItems="+ MenuItems + "]";
	}*/
	
}
