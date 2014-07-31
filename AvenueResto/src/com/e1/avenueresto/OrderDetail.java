package com.e1.avenueresto;



public class OrderDetail {
	
	private String desc;
	private int idx;
	private int qty;
	private double price;
	private Menu ordMenu;
	private boolean IsSubmitted;

	public boolean isIsSubmitted() {
		return IsSubmitted;
	}

	public void setIsSubmitted(boolean isSubmitted) {
		IsSubmitted = isSubmitted;
	}

	public OrderDetail(String odesc,int oidx,int oqty,double oprice,Menu ordm)
	{
		 ordMenu=ordm;
		 desc=odesc;
		 idx=oidx;
		 qty=oqty;
		 price=oprice;
		
	}

	public Menu getMenu(){
		return ordMenu;
		}
		
		public void setMenu(Menu menu){
			this.ordMenu=menu;
			}

	
	public String getDesc(){
	return desc;
	}
	
	public void setDesc(String desc){
		this.desc=desc;
		}

	public int getIdx(){
		return idx;
	}

	public void setIdx(int idx){
		this.idx = idx;
    }

	public void setQty(int qty){
		this.qty = qty;
		
    }
	
	public int getQty(){
		return qty;
	}

	public void setPrice(double price){
		this.price = price;
    }
	
	public double getPrice(){
		return price;
	}
	
	public double getTotalAmount(){
		return price * qty;
	}


}
