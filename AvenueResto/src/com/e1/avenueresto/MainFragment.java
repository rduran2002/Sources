package com.e1.avenueresto;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import android.app.Fragment;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainFragment extends Fragment {
	
	private int REL_SWIPE_MIN_DISTANCE; 
    private int REL_SWIPE_MAX_OFF_PATH;
    private int REL_SWIPE_THRESHOLD_VELOCITY;
	
    private int OCCUPIED_COLOR;
    private int VACANT_COLOR;
    private int ORDER_SUBMIT_COLOR;
    private int SELECTED_COLOR;
    
	LinearLayout ly1;
	LinearLayout ly2;
	LinearLayout ly3;
	LinearLayout ly4;
	LinearLayout RemarksLayout;
	RelativeLayout totLayout;
	LinearLayout orderLayout;
	//LinearLayout ly5;

	ListView lv;
	
	List<ServingTable> tableList;
	List<Order> orders;
	//TextView tableno;
	//DataManager dm = new DataManager();
	SimpleAdapter adapter;
	//Spinner waitStaffspinner;
	ServingTable currTable;
	Menu currMenu;
	List<OrderDetail> currOrderList;
	TextView TotalItems;
	TextView TotalAmt;
	EditText OrdQtyTxt;
	Context ctx;
	int itemTot;
	double amtTot;
	User user;
	View.OnTouchListener gestureListener;
	
	//order
	EditText ordTableNoTxt;
	EditText paxTxt;
	EditText customerNameTxt;
	EditText customerAddressTxt;
	Spinner orderTypespinner;
	ListView RemarksListView;
	ListView menuItemRemarksListView;
	ArrayAdapter<String> menuItemRemarksArrayAdapter;
	
	Button sendOrder;
	
	ArrayList<HashMap<String, String>> orderlist = new ArrayList<HashMap<String, String>>();
   
	
	  private SoundPool soundPool;
	  private int soundID;
	  boolean loaded = false;
	
   
	  
   public MainFragment(User u)	
   {
	   user = u;
   }
	  
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.tables,container,false);
	
	    this.getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
	    // Load the sound
	    soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
	    soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
				// TODO Auto-generated method stub
				loaded = true;
			}
		});
	    
	
	    OCCUPIED_COLOR = android.graphics.Color.RED;
	    VACANT_COLOR=android.graphics.Color.rgb(119,196,246);
	    ORDER_SUBMIT_COLOR=android.graphics.Color.DKGRAY;
	    SELECTED_COLOR=android.graphics.Color.rgb(18,89,196);
	    
	    
		DisplayMetrics dm = getResources().getDisplayMetrics();
        REL_SWIPE_MIN_DISTANCE = (int)(120.0f * dm.densityDpi / 160.0f + 0.5); 
        REL_SWIPE_MAX_OFF_PATH = (int)(250.0f * dm.densityDpi / 160.0f + 0.5);
        REL_SWIPE_THRESHOLD_VELOCITY = (int)(200.0f * dm.densityDpi / 160.0f + 0.5);

        final GestureDetector gestureDetector = new GestureDetector(new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event); 
            }};
		
		
		return v;
	}
	
	
	Button.OnClickListener sendOrderListener = new OnClickListener() //send order
    {
		
		@Override
		public void onClick(View v) {
			
			
		
		//	String tableno = currTable.getName();
			
			if(currTable.getcOrder()!=null)
			{
				
				Order order = currTable.getcOrder();
				
				
					/*Order order = new Order();
					order.setTableNumber(tableno);
					
					Time now = new Time();
					now.setToNow();
					
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
					Calendar cal = Calendar.getInstance();
					String now = dateFormat.format(cal.getTime());
					
					
					order.setOrderDate(now);
					order.setCustomer("Cash");
					order.setOrderType("Dine In");
					order.setSalesType("Regular");
				    order.setPax("1");
					order.setCrew("Android");
					*/
					
				
					
					List<OrderDetail> ldtl = currTable.getOrderList();
					List<Menu> mnulst=new ArrayList<Menu>();
					
					if(ldtl.size() > 0)
					{
						for(OrderDetail od:ldtl)
						{
							Menu om = od.getMenu();
							om.setQuantity(od.getQty());
							om.setId(od.getIdx());
							mnulst.add(om);
						}
						order.setMenuItems(mnulst);
						order.setOrderDetails(ldtl);
						currTable.setcOrder(order);
						currTable.getBtn().setBackgroundColor(ORDER_SUBMIT_COLOR);
						currTable.getBtn().setTextColor(android.graphics.Color.WHITE);
						
						try
						{
							if(	DataManager.SendOrder(order))
							{
								PlaySound();
							}
						
							setOrders();
						
						}
						catch (JSONException e)
						{
							e.printStackTrace();
						}
					}
					else
					{
						   Toast.makeText(ctx, "Cannot submit order without items!", Toast.LENGTH_SHORT).show();
					}
				}
				
			
			
		}
	
    };
    
    void PlaySound()
    {
    
		final AudioManager audioManager = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
	      float actualVolume = (float) audioManager
	          .getStreamVolume(AudioManager.STREAM_MUSIC);
	      
	      float volume = actualVolume;
	      // Is the sound loaded already?
	      if (loaded) {
	        soundPool.play(soundID, volume, volume, 1, 0, 1f);
	       
	      }
    }
    
    Button.OnClickListener createOrderListener = new OnClickListener() 
    {
		
		@Override
		public void onClick(View v) {
			

			CreateOrder();
			ShowOrderList();
			
		}
	
    };
   
    void CreateOrder()
    {
    	Order o = new Order();
    	
    	o.setCrew(user.getFullName());
    	o.setCustomer(customerNameTxt.getText().toString());
    	o.setCustomerAddress(customerAddressTxt.getText().toString());
    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String now = dateFormat.format(cal.getTime());
    	
    	o.setOrderDate(now);
    	o.setOrderNumber(ordTableNoTxt.getText().toString());
    	o.setOrderType(orderTypespinner.getSelectedItem().toString());
    	o.setPax(paxTxt.getText().toString());
    	o.setTableNumber(ordTableNoTxt.getText().toString());
        o.setIsSubmitted(false);
    	ServingTable ts= new ServingTable(o.getTableNumber() + "-" + "*****",orders.size()+1,0);
    	orders.add(o);
		ts.setcOrder(o);
		
		
		Button b = new Button(ctx);
		b.setText(ts.getName());
		b.setTag(ts);
		b.setTextSize(25);
		b.setOnClickListener(clickListener3);
		b.setBackgroundColor(SELECTED_COLOR);
		ts.setBtn(b);
		tableList.add(ts);
		ly4.addView(b);
		
		currTable=ts;
		setTableOrders();
    }
   
    Button.OnClickListener closeEditOrderListener = new OnClickListener() 
    {
		
		@Override
		public void onClick(View v) {
			ShowOrderList();
			
			
		}
	
    };
    
    Button.OnClickListener closeRemarksListener = new OnClickListener() 
    {
		
		@Override
		public void onClick(View v) {
			ShowMenuList();
			RemarksLayout.setVisibility(View.GONE);
			
		}
	
    };
  
    Button.OnClickListener editOrderListener = new OnClickListener() 
    {
		
		@Override
		public void onClick(View v) {
			

			
		}
	
    };
   
	Button.OnClickListener newOrderListener = new OnClickListener() 
	    {
			
			@Override
			public void onClick(View v) {
				
				/* currTable.clearcOrder();
				 currTable.getOrderList().clear();
				 setTableOrders();*/
				ShowOrderEdit();
			
				ordTableNoTxt.setText("*");
				paxTxt.setText("1");
				customerNameTxt.setText("N/A");
				customerAddressTxt.setText("N/A");
			}
		
	    };
	
    Button.OnClickListener clickListener4 = new OnClickListener() 
    {
		
		@Override
		public void onClick(View v) {
			
			 AddMenuToOrder((Button)v);
		}
	
    };
    
	Button.OnClickListener clickListener3 = new OnClickListener() 
	{
		
		@Override
		public void onClick(View v) {
			
			ServingTable st = (ServingTable)((Button)v).getTag();
			currTable=st;
			//setTableNumber(st.getId());
			setTableOrders();
		}
	};
	
	Button.OnClickListener clickListener1 = new OnClickListener() 
	{
			
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				
				setGroups((ArrayList<MenuGroup>)v.getTag());
				}
				
    };
   
    Button.OnClickListener clickListener2 = new OnClickListener()
    {
					
					@Override
					public void onClick(View v) {
						
						setMenu((MenuGroup)v.getTag());
						}
						
	};

	View.OnKeyListener keyListener = new View.OnKeyListener() {
		
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			EditText et = (EditText) v;
			
			if(keyCode == KeyEvent.KEYCODE_ENTER)
			{
				et.clearFocus();
				if(et.getText().toString().equals(""))
				{
					et.setText("1");
				
				}
			}
			
			return false;
		}
	};

	View.OnTouchListener onTouch = new View.OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			EditText et = (EditText) v;
			et.setText("");
		//	et.requestFocus();
			InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
			
			return false;
		}
	};
	
    View.OnFocusChangeListener focusListener = new View.OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
            
			EditText et = (EditText) v;
			
			if(hasFocus && et.getText().toString().equals(""))
			{
		
				InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
			
			}
			else
			{
				if(et.getText().toString().equals("")||Integer.parseInt(et.getText().toString()) == 0)
				{
					et.setText("1");
					InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(et.getWindowToken(), 0); //to close
				
				}
			}
			
		}
	};
	
	
	void ShowMenuList()
	{
	
		ly3.setVisibility(View.VISIBLE);
	};
	
	void ShowOrderEdit()
	{
		LayoutParams ly1params = ly1.getLayoutParams();
		
		ly1params.height=LayoutParams.FILL_PARENT;
		totLayout.setVisibility(View.GONE);
		orderLayout.setVisibility(View.GONE);
	
	}
	
	void ShowRemarks()
	{
		LayoutParams RemarksLayoutparams = RemarksLayout.getLayoutParams();
		
		RemarksLayoutparams.height=LayoutParams.FILL_PARENT;
		ly3.setVisibility(View.GONE);
		RemarksLayout.setVisibility(View.VISIBLE);
		
		menuItemRemarksArrayAdapter.clear();
		
		if(currMenu!=null)
		{
			if(currMenu.getRemarks()!=null)
			{
				List<MenuItemRemark> remarks = currMenu.getRemarks(); 
				if(remarks.size()>0)
				{
					for(MenuItemRemark r:remarks)
					{
						menuItemRemarksArrayAdapter.add(r.getName());
					}
				}
				
			}
		}
		
	}
	
	
	
	void ShowOrderList()
	{
		
		LayoutParams ly1params = ly1.getLayoutParams();
		
		ly1params.height=0;
		totLayout.setVisibility(View.VISIBLE);
		orderLayout.setVisibility(View.VISIBLE);
		
		//default values
		ordTableNoTxt.setText("*"); 
		paxTxt.setText("1");
		customerNameTxt.setText("N/A");
		customerAddressTxt.setText("N/A");
		
	}
	
	void MinusMenuQty(int pos)
	{
		OrderDetail ord = currTable.getOrderList().get(pos);
		if(ord.getQty() > 1)
		{
			ord.setQty(ord.getQty()-1);
			setTableOrders();
			
		}
	}

	void DeleteItem(int pos)
	{
		currTable.getOrderList().remove(pos);
		setTableOrders();
	}
	
	void ResetButtons()
	{
		for(ServingTable t:tableList)
		{
			/*if(t.equals(currTable))
			{
				if(t.getOrderList().size()== 0 && t.getOrderList().size()==0) //if(t.getOrderList().size()== 0 && t.getcOrder()==null)
				{
					t.getBtn().setBackgroundColor(VACANT_COLOR);
					t.getBtn().setTextColor(android.graphics.Color.WHITE);
				}
					
				
			}
			else
			{
				*/
				if(!t.equals(currTable))
				{
					if(t.getOrderList().size()>0 && t.getOrderList().size()==0)
					{
						t.getBtn().setBackgroundColor(OCCUPIED_COLOR);
						t.getBtn().setTextColor(android.graphics.Color.WHITE);
					}
						
					if(t.getcOrder()!=null && t.getOrderList().size()>0)
					{
						t.getBtn().setBackgroundColor(ORDER_SUBMIT_COLOR);
						t.getBtn().setTextColor(android.graphics.Color.WHITE);
					}
				}
			
			//}
		}
	}
	
	void setTableOrders()
	{
		amtTot= 0;
		itemTot=0;
		
		orderlist.clear();
		for(OrderDetail o: currTable.getOrderList())
		{
			
			HashMap<String, String> map = new HashMap<String, String>();
	    	map.put("Desc", o.getDesc().toString());
			map.put("Id",Integer.toString(o.getIdx()));
			map.put("Qty", Integer.toString(o.getQty()));
			map.put("Price",String.format("%.2f",o.getPrice()));
			map.put("TotalAmt",String.format("%.2f",o.getTotalAmount()));
	        orderlist.add(map);
	        
	        itemTot+=o.getQty();
	        amtTot+=o.getTotalAmount();
	        
		}
		
		currTable.getBtn().setBackgroundColor(SELECTED_COLOR);
		currTable.getBtn().setTextColor(android.graphics.Color.WHITE);
		
		ResetButtons();
		
		
		
		TotalAmt.setText(String.format("%.2f",amtTot));
        TotalItems.setText(String.valueOf(itemTot));
        
		adapter = new SimpleAdapter(ctx,orderlist,R.layout.row,new String[]{"Desc","Qty","Price","TotalAmt"},
				new int[]{R.id.Desc,R.id.Qty,R.id.Price,R.id.TotalAmt});
    
	    lv.setAdapter(adapter);
	  
	   
	
	}
	
	void AddMenuToOrder(Button b)
	{
		
		if(currTable!=null)
		{
		    int qty = Integer.parseInt(OrdQtyTxt.getText().toString());
			Menu m = (Menu)b.getTag();
			
		
		   if(currTable.getOrderList().size()>0)
		   {
			     OrderDetail ord = IsOrderListed(m.getName());
			     
			     if(ord==null)
				 {
					OrderDetail md = new OrderDetail(m.getName(),m.getId(),qty,m.getMealPrice(),m);
					md.setIsSubmitted(false);
					currTable.getOrderList().add(md);
				
				 }
			     else
			     {
			    	ord.setQty(ord.getQty()+qty) ;
			    	
			     }
		   
		   }
		   else
		   {
			   OrderDetail md = new OrderDetail(m.getName(),m.getId(),qty,m.getMealPrice(),m);
				currTable.getOrderList().add(md);
				
		   }
		   
		   setTableOrders();
		   lv.setSelection(lv.getCount());
		   
		   OrdQtyTxt.setText("1");
		}
	}
					
    OrderDetail IsOrderListed(String desc)
   {
	   OrderDetail retord = null;
	   for(OrderDetail ord:currTable.getOrderList())
	   {
		   if(ord.getDesc() == desc)
		   {
			   retord = ord;
			   break;
		   }
	   }
	   return retord;
   }

    void setOrders()
	{
        orders = DataManager.GetAllOrder();
		int i=0;
		
		tableList.clear();
		
		for(Order t: orders)
		{
			ServingTable ts= new ServingTable(t.getTableNumber() + "-" + t.getInvoiceNumber(),i+1,0);
			ts.setcOrder(t);
			
			for(Menu m : t.getMenuItems())
			{
				OrderDetail od =new OrderDetail(m.getName(),m.getId(),m.getQuantity(),m.getMealPrice(),m);
				ts.AddOrder(od);
			}
			
			
			
			Button b = new Button(ctx);
			b.setText(ts.getName());
			//Drawable d = ctx.getResources().getDrawable(t.getResourceId());
		
			//d.setBounds(0, 0, (int)(d.getIntrinsicWidth()*0.50),(int)(d.getIntrinsicHeight()*0.50));
			b.setTag(ts);
			//b.setCompoundDrawablePadding(-10);
			//b.setCompoundDrawables(null,d, null, null);
			b.setTextSize(25);
			b.setOnClickListener(clickListener3);
			b.setBackgroundColor(VACANT_COLOR);
			
			ts.setBtn(b);
			tableList.add(ts);
			ly4.addView(b);
		
		}
		
		if(tableList.size()>0)
		{
			currTable=tableList.get(0);
		    ResetButtons();
		}
						
	}		
 
	void setMainMenu()
	{
	  
		setGroups(DataManager.MenuGroups);
	}
	
	void setGroups(List<MenuGroup> groups)
	{
		
	  	ly2.removeAllViews();	
		for(MenuGroup g: groups)
		{
			Button b = new Button(ctx);
			b.setText(g.getName());
			b.setTextSize(20);
			b.setHeight(50);
			b.setTag(g);
			b.setOnClickListener(clickListener2);
	        ly2.addView(b);
		}
		
		setMenu((MenuGroup)ly2.getChildAt(0).getTag());
	}
	
	void setMenu(MenuGroup g)
	{
		List<Menu> menus = g.getMenus();
	
	  	ly3.removeAllViews();	
		for(Menu t: menus)
		{
			Button b = new Button(ctx);
			b.setText(t.getName() + "  (Php." + t.getMealPrice() + ")");
			b.setTag(t);
			b.setTextSize(20);
			b.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			b.setOnClickListener(clickListener4);
	        ly3.addView(b);
		}
	}
	
	void setTableNumber(int number)
	{
		/*if(currTable!=null)
		{
			if(currTable.getcOrder()==null)
			{
				((Button)currTable.getBtn()).setBackgroundColor(VACANT_COLOR);
			}
		}*/
		currTable = tableList.get(number);
		//tableno.setText(currTable.getName());
		setTableOrders();
		//((Button)currTable.getBtn()).setBackgroundColor(SELECTED_COLOR);
	}
	
	 
AdapterView.OnItemClickListener remarksOnItemClickListener = new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	
		ListView v =(ListView) arg0;
		String remark =(String) v.getItemAtPosition(arg2);
		
		
		if(currMenu.getRemarks()==null)
		{
			List<MenuItemRemark> lm = new ArrayList<MenuItemRemark>();
			currMenu.setRemarks(lm);
		}
		
		if(!currMenu.getRemarks().contains(remark))
		{
			MenuItemRemark  mr = new MenuItemRemark();
			mr.setName(remark);
			mr.setId(0);
			
			currMenu.getRemarks().add(mr);
			
			menuItemRemarksArrayAdapter.add(remark);
		}
	}
};
	
	
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
			
		ctx = view.getContext();
		
		ly1 =(LinearLayout) view.findViewById(R.id.orderEditlayout);
		ly1.setBackgroundColor(android.graphics.Color.YELLOW);
	
		ly2 =(LinearLayout) view.findViewById(R.id.LinearLayout03);
		ly2.setBackgroundColor(android.graphics.Color.GREEN);
		ly3 =(LinearLayout) view.findViewById(R.id.LinearLayout04);
		ly3.setBackgroundColor(android.graphics.Color.LTGRAY);
		ly4 =(LinearLayout) view.findViewById(R.id.LinearLayout05);
		ly4.setBackgroundColor(android.graphics.Color.TRANSPARENT);
		RemarksLayout = (LinearLayout) view.findViewById(R.id.remarkslayout);
		totLayout = (RelativeLayout)view.findViewById(R.id.totlayout);
		orderLayout = (LinearLayout)view.findViewById(R.id.orderlayout);
		
		RemarksListView = (ListView)view.findViewById(R.id.remarklistview);
		
		ArrayAdapter<String> remarksArrayAdapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_dropdown_item,DataManager.Remarks);
		RemarksListView.setAdapter(remarksArrayAdapter);
		
		RemarksListView.setOnItemClickListener(remarksOnItemClickListener);
		
		
	    menuItemRemarksArrayAdapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_dropdown_item);
		
		menuItemRemarksListView =  (ListView)view.findViewById(R.id.menuItemRemarksListView);
		menuItemRemarksListView.setAdapter(menuItemRemarksArrayAdapter);
		
		lv =(ListView) view.findViewById(R.id.orderlistview);
		lv.setBackgroundColor(android.graphics.Color.TRANSPARENT);
		
		
		lv.setOnTouchListener(gestureListener);

	       
	        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
	            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
	            	
	            	currMenu = currTable.getOrderList().get(position).getMenu();
	           
	            	ShowRemarks();
	                return true;
	            }
	        });
	        
	     
	        
		
		Button newOrder = (Button)view.findViewById(R.id.newbtn) ;
		newOrder.setOnClickListener(newOrderListener);
		
		sendOrder = (Button)view.findViewById(R.id.sendbtn) ;
		sendOrder.setOnClickListener(sendOrderListener);
		
		Button editOrder = (Button)view.findViewById(R.id.editOrderbtn) ;
		editOrder.setOnClickListener(editOrderListener);
		
		Button closeEditOrder = (Button)view.findViewById(R.id.closebtn) ;
		closeEditOrder.setOnClickListener(closeEditOrderListener);
		
		Button closeRemarksButton = (Button)view.findViewById(R.id.closeremarksbtn) ;
		closeRemarksButton.setOnClickListener(closeRemarksListener);
		
		Button createOrderbtn = (Button)view.findViewById(R.id.createOrderbtn) ;
		createOrderbtn.setOnClickListener(createOrderListener);
		
		
	    orderTypespinner = (Spinner) view.findViewById(R.id.orderTypespinner);
		
	    ordTableNoTxt=(EditText)view.findViewById(R.id.orderTablenoTxt);
	    ordTableNoTxt.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				EditText et = (EditText) v; 
				et.setText("");
				InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
				
				return false;
			}
		});
	    
		paxTxt=(EditText)view.findViewById(R.id.paxTxt);
		paxTxt.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				EditText et = (EditText) v; 
				et.setText("");
				InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
				
				return false;
			}
		});
		
		customerNameTxt=(EditText)view.findViewById(R.id.customerNameTxt);
		customerNameTxt.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				EditText et = (EditText) v; 
				et.setText("");

				InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
				
				return false;
			}
		});
		
		customerAddressTxt=(EditText)view.findViewById(R.id.customerAddressTxt);
		customerAddressTxt.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				EditText et = (EditText) v; 
				et.setText("");
			
				InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
				
				return false;
			}
		});
		
	    TotalItems = (TextView)view.findViewById(R.id.itemTot);
	    TotalAmt = (TextView)view.findViewById(R.id.amtTot);
	    OrdQtyTxt =(EditText)view.findViewById(R.id.QtyTxt);
	    OrdQtyTxt.setText("1");
	    OrdQtyTxt.setOnFocusChangeListener(focusListener);
	    OrdQtyTxt.setOnKeyListener(keyListener);
	    OrdQtyTxt.setOnTouchListener(onTouch);
	    OrdQtyTxt.setCursorVisible(false);
	    
		//tableno = (TextView)view.findViewById(R.id.tablenumber);
		//tableno.setBackgroundColor(android.graphics.Color.RED);
		//tableno.setTextColor(android.graphics.Color.WHITE);
	
		tableList =new ArrayList<ServingTable>();// DataManager.GetTables();

		//setTable();
		
		//setTableNumber(0);
		
		setMainMenu();
		setOrders();
		
		super.onViewCreated(view, savedInstanceState);
	
		
		 int sound_id = ctx.getResources().getIdentifier("pingda", "raw",ctx.getPackageName());
		    soundID = soundPool.load(ctx, sound_id, 1);
		    
		
		
	}
	
	private void myOnItemClick(int position) {
	       String str = MessageFormat.format("Item clicked = {0,number}", position);
	       Toast.makeText(ctx, str, Toast.LENGTH_SHORT).show();
	    }

	

	private void onLTRFling(int pos) {
	    	if(pos!=-1){
	    		
	    		DeleteItem(pos);
	    	}
	    }

	private void onRTLFling(int pos) {
	    	if(pos!=-1){
	    		
	    		MinusMenuQty(pos);
	    	}
	    }
	
	class MyGestureDetector extends SimpleOnGestureListener{ 

  
		 
		    
		    
		    
	        // Detect a single-click and call my own handler.
	        @Override 
	        public boolean onSingleTapUp(MotionEvent e) {
	          
	            int pos = lv.pointToPosition((int)e.getX(), (int)e.getY());
	            myOnItemClick(pos);
	            return false;
	        }

	        @Override 
	        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) { 
	            if (Math.abs(e1.getY() - e2.getY()) > REL_SWIPE_MAX_OFF_PATH) 
	                return false; 
	            if(e1.getX() - e2.getX() > REL_SWIPE_MIN_DISTANCE && 
	                Math.abs(velocityX) > REL_SWIPE_THRESHOLD_VELOCITY) { 
	            	
	            	  int pos = lv.pointToPosition((int)e1.getX(), (int)e1.getY());
	                  onRTLFling(pos); 
	            }  else if (e2.getX() - e1.getX() > REL_SWIPE_MIN_DISTANCE && 
	                Math.abs(velocityX) > REL_SWIPE_THRESHOLD_VELOCITY) { 
	            	int pos = lv.pointToPosition((int)e1.getX(), (int)e1.getY());
	                onLTRFling(pos); 
	            } 
	            return false; 
	        } 

	    } 
	
}
        

