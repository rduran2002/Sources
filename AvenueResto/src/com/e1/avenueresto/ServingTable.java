package com.e1.avenueresto;

import java.util.ArrayList;
import java.util.List;

import android.widget.Button;

public class ServingTable {
	
	private String name;
	private List<OrderDetail> orderlist;
	private int id;
    private int resourceid;
    private Button btn;
    private Order cOrder;
    public static boolean isSelected;
    
	public ServingTable(String oname, int oid,int oresourceid){
		name = oname;
		id = oid;
		resourceid = oresourceid;
		orderlist = new ArrayList<OrderDetail>();
	}

	public Button getBtn(){
		return btn;
		}
		
    public void setBtn(Button btn){
		this.btn=btn;
		}
	
	public void setName(String name){
		this.name = name;
		}
	
	public String getName(){
	return name;
	}

	public int getId(){
	return id;
	}

	public void setId(int id){
	this.id = id;
	}
	
	
	    public int getResourceId(){
		return resourceid;
		}

		public void setResourceId(int resourceid){
		this.resourceid = resourceid;
		}
	
	    public List<OrderDetail> getOrderList(){
		return orderlist;
		}

		public void AddOrder(OrderDetail order){
		    orderlist.add(order);
		}

		public void removeOrder(OrderDetail order){
		    orderlist.remove(order);
		}
	
		public void setcOrder(Order corder){
		      cOrder=corder;
		}

		public Order getcOrder(){
		        return cOrder;
		}
		 
		public void clearcOrder(){
	        cOrder = null;
	}
}
