package com.e1.avenueresto;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.widget.Toast;


public final class DataManager {
	
	
	public static List<MenuType> MainMenus; 
	public static List<MenuGroup> MenuGroups; 
	public static List<Menu> Menus; 
	public static List<MenuItemRemark> MenuItemRemarks;
	public static List<String> Remarks;
	public static Context ctx;
	
	private static void GetRemarks()
	{
		Remarks = new ArrayList<String>();
		for(MenuItemRemark m : MenuItemRemarks)
		{
			Remarks.add(m.getName());
		}
		
	
	}
	
	public static void GetMainMenus() 
	{
		     MainMenus = new ArrayList<MenuType>();
		     MenuGroups = new ArrayList<MenuGroup>();
		     Menus = new ArrayList<Menu>();
		     MenuItemRemarks = new ArrayList<MenuItemRemark>();
	  
		    try
		    {
		    	///need to get Remarks table
	            //menus
	            JSONArray menus = WebRequest("GetMenus");
		       
		        
	            for (int i = 0; i < menus.length(); ++i)
	            {
	            	JSONObject j = new JSONObject(new String(menus.getString(i)));
                 	Menu m = new Menu();
	            	m.setName(j.getString("Name"));
	            	m.setId(j.getInt("Id"));
	            	m.setGCode(j.getString("GCode"));
	            	m.setCode(j.getString("Code"));
	            	m.setPrice(j.getDouble("Price"));
	            	m.setMealPrice(j.getDouble("MealPrice"));
	            	m.setCost(j.getDouble("Cost"));
	            	m.setStation(j.getString("Station"));
	            	m.setStatus(j.getBoolean("Status"));
	            	m.setVatable(j.getBoolean("Vatable"));
	            
	            	Menus.add(m);
	              
	            }
	            
	            //categories
	            JSONArray menuGroups = WebRequest("GetAllMenuGroup");
	            
	          
	            
	            for (int i = 0; i < menuGroups.length(); ++i)
	            {
	            	JSONObject j = new JSONObject(new String(menuGroups.getString(i)));
           		            	
	            	MenuGroup mg = new MenuGroup();
	            	mg.setName(j.getString("Name"));
	            	mg.setId(j.getInt("Id"));
	            	mg.setGCode(j.getString("GCode"));
	            	mg.setMenus(new ArrayList<Menu>());
	            	for(Menu m: Menus)
	            	{
	            		if(m.getGCode().toString().equals(mg.getGCode().toString()))
	            		{
	            			mg.getMenus().add(m);
	            		}
	            	}
	            	MenuGroups.add(mg);
	              
	            }
	            //categories
	            JSONArray menuRemarks = WebRequest("GetAllServingRemark");
	            
	          
	            
	            for (int i = 0; i < menuRemarks.length(); ++i)
	            {
	            	JSONObject j = new JSONObject(new String(menuRemarks.getString(i)));
           		            	
	            	MenuItemRemark r = new MenuItemRemark();
	            	r.setName(j.getString("Name"));
	            	r.setId(j.getInt("Id"));
	           
	                MenuItemRemarks.add(r);
	            }
	            
	            GetRemarks();
	      
	        } 
		    catch (Exception e)
	        {
		    	e.printStackTrace();
	           
	        }
	
	}

	//Send
	public static boolean SendOrder(Order order) throws JSONException
	{
		
		
		JSONArray ml = new JSONArray();
		
		for(OrderDetail m:order.getOrderDetails())
		{
			JSONObject mlobj = new JSONObject(); 
			
		
			mlobj.put("MenuId", m.getMenu().getId());
			
			mlobj.put("Quantity", m.getQty());
			
			mlobj.put("Name", m.getMenu().getName());
			
			mlobj.put("Price", m.getMenu().getPrice());
			
			mlobj.put("MealPrice", m.getMenu().getMealPrice());
			
			mlobj.put("TotalAmount", m.getTotalAmount());
			
			mlobj.put("IsSubmitted", m.isIsSubmitted());
			
			
			
			JSONArray mrl = new JSONArray();
			
			for(MenuItemRemark mr:m.getMenu().getRemarks())
			{
				JSONObject mrobj = new JSONObject();
				mrobj.put("Id", 0);
				mrobj.put("Name", mr.getName());
				mrl.put(mrobj);
			}
			
			
			mlobj.put("Remarks", mrl);
			ml.put(mlobj);
		
		
		}
		
	
		JSONObject obj =new JSONObject();
		
		obj.put("TableNumber", order.getTableNumber());
	    obj.put("Customer", order.getCustomer());
	    obj.put("CustomerAddress", order.getCustomerAddress());
	    obj.put("OrderDate", order.getOrderDate());
	    obj.put("OrderNumber", order.getOrderNumber());
	    obj.put("OrderType", order.getOrderType());
	    obj.put("SalesType", "Regular");
		obj.put("MenuItems", ml);
		obj.put("Pax", order.getPax());
		obj.put("Crew", order.getCrew());
		
		JSONObject ord =new JSONObject();
		
		ord.put("order", obj);
		return SendRequest(ord.toString());
		
	
		
	}
	
	private static JSONArray WebRequest(String req) 
	{
		String SERVICE_URI =  ctx.getResources().getString(R.string.service_url);
		
	   
        try {
     
            // Send GET request to <service>/GetPlates
            HttpGet request = new HttpGet(SERVICE_URI + "/" + req);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
     
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(request);
     
            HttpEntity responseEntity = response.getEntity();
          
            String jsonText = EntityUtils.toString(responseEntity, HTTP.UTF_8);
            return new JSONArray(jsonText);
            
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            return null;
        }
	}
	
	
	private static JSONObject WebRequest2(String req) 
	{
		String SERVICE_URI =  ctx.getResources().getString(R.string.service_url);
		
	   
        try {
     
            // Send GET request to <service>/GetPlates
            HttpGet request = new HttpGet(SERVICE_URI + "/" + req);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
     
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(request);
     
            HttpEntity responseEntity = response.getEntity();
          
            String jsonText = EntityUtils.toString(responseEntity, HTTP.UTF_8);
            return new JSONObject(jsonText);
            
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            return null;
        }
	}
	
	
	public static List<ServingTable> GetTables()
	{
		List<ServingTable> tables = new ArrayList<ServingTable>();
		
		int count = Integer.parseInt(ctx.getResources().getString(R.string.table_count));	
		for(int i=0;i<count;i++)
		{
			ServingTable st = new ServingTable(Integer.toString(i+1), i, R.drawable.table);
			tables.add(st);
		}
		
		return  tables;
	}
	
	public static User GetUser(String Username,String Password)
	{
		String param = "GetUser/"+Username+"/"+Password;
		 JSONObject j = WebRequest2(param);
		 if(j!=null)
		 {
		    try {
				//JSONObject j = new JSONObject(new String(user.getString(0)));
				User u = new User();
				u.setId(j.getString("Id"));
				u.setFullName(j.getString("FullName"));
				
				return u;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				return null;
			
			}
		 }
		 else
		 {
			 return null;
		 }
	}
	
	
	public static List<Order> GetAllOrder()
	{
		 List<Order> Orders = new ArrayList<Order>();
		
		 JSONArray order = WebRequest("GetAllOrder");
	       
	     try
	     {
	   
	         for (int i = 0; i < order.length(); ++i)
	         {
	         	JSONObject j = new JSONObject(new String(order.getString(i)));
	          	
	         	Order o = new Order();
	         	o.setCrew(j.getString("Crew"));
	         	o.setCustomer(j.getString("Customer"));
	         	o.setCustomerAddress(j.getString("CustomerAddress"));
	            o.setOrderDate(j.getString("OrderDate"));
	            o.setOrderNumber(j.getString("OrderNumber"));
	            o.setOrderType(j.getString("OrderType"));
	            o.setPax(j.getString("Pax"));
	            o.setSalesType(j.getString("SalesType"));
	            o.setTableNumber(j.getString("TableNumber"));
	            o.setInvoiceNumber(j.getString("InvoiceNumber"));
	            
	            o.setMenuItems(new ArrayList<Menu>());
	            
	            JSONArray ms = new JSONArray(new String(j.getString("MenuItems")));
	            
	            for (int a = 0; a < ms.length(); ++a)
	            {
	            	JSONObject jo = new JSONObject(new String(ms.getString(a)));
	            	
	            	 Menu m = new Menu();
	            	 m.setId(jo.getInt("MenuId"));
	            	 m.setQuantity(jo.getInt("Quantity"));
	            	 m.setName(jo.getString("Name"));
	                 m.setPrice(jo.getDouble("Price"));
	                 m.setMealPrice(jo.getDouble("MealPrice"));
	            	 
	            	 m.setRemarks(new ArrayList<MenuItemRemark>());
	            	 
	            	 JSONArray rm = new JSONArray(new String(jo.getString("Remarks")));
	            	 
	            	 for(int b=0;b<rm.length();++b)
	            	 {
	            		 JSONObject ro=new JSONObject(new String(rm.getString(b)));
	            		 MenuItemRemark r = new MenuItemRemark();
	            		 r.setId(ro.getInt("Id"));
	            		 r.setName(ro.getString("Name"));
	            		 
	            		 m.getRemarks().add(r);
	            	 }
	            	 
	            	 o.getMenuItems().add(m);
	            }
	                 
	         	Orders.add(o);
	           
	         }
	         
	         
	         
	        
	     }
	     catch (Exception e)
	     {
	        	e.printStackTrace();
	         
	     }
	     
	     return Orders;
	}
	
	
	public static boolean SendRequest(String ord) throws JSONException
	{
		String SERVICE_URI =  ctx.getResources().getString(R.string.service_url);
		
		HttpPost request = new HttpPost(SERVICE_URI + "/" + "SubmitOrder" );
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");

			
		StringEntity entity = null;
		try {
			entity = new StringEntity(ord.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}                                                
		entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		entity.setContentType("application/json");

		request.setEntity(entity);

		// Send request to WCF service
		DefaultHttpClient httpClient = new DefaultHttpClient();             
		try {
			HttpResponse response = httpClient.execute(request);
		
		    int responseCode = response.getStatusLine().getStatusCode();
		  
		    if(responseCode == 200)
		    {
		    	 Toast.makeText(ctx,"Order sent succesfully!",Toast.LENGTH_LONG).show();
		    	 return true;
     	    }
		    else
		    {
		    	 Toast.makeText(ctx,"Order send failed.",Toast.LENGTH_LONG).show();
		    	 return false;
		    }
		    
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	

	public  List<Category> GetCategory(MenuType main)
	{
		return main.getCategories();
	}
	
	public  List<Menu> GetMenus(Category category)
	{
		return category.getMenus();
	}

	public class StringInt{

		private String s;
		private int i;

		public StringInt(String o1, int o2){
		s = o1;
		i = o2;
		}

		public String getString(){
		return s;
		}

		public int getInt(){
		return i;
		}

		public void setString(String s){
		this.s = s;
		}

		public void setInt(int i){
		this.i = i;
		}

  }
	
 
	
}
