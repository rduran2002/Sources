package com.e1.avenueresto;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuFragment extends Fragment {
	
	Fragment locFragment;
	Button btnAppetizer,btnMainCourse,btnBeverages,btnShortOrder,btnDesserts,btnListOrders;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.menutypes,container, false);
	}
	
	
	
	Button.OnClickListener btnClickListener = new Button.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment newFragment;
			
			String menutype="";
			
			if(v==btnAppetizer)
			{
				menutype = "Appetizer";
			}
			else if(v==btnMainCourse)
			{
				menutype = "MainCourse";
			}
			else if(v==btnBeverages)
			{
				menutype = "Beverages";
			}
			else if(v==btnShortOrder)
			{
				menutype = "ShortOrders";
			}
			else if(v==btnDesserts)
			{
				menutype = "Desserts";
			}
			else if(v==btnListOrders)
			{
				menutype = "ListOrders";
			}
		
			newFragment = new OrderFragment(menutype);
			
			FragmentTransaction ft =getFragmentManager().beginTransaction();
			ft.replace(R.id.mainfragment, newFragment);
			ft.addToBackStack(null);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		}
	};
	
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		 btnAppetizer = (Button)view.findViewById(R.id.BtnAppetizer);
		 btnMainCourse = (Button)view.findViewById(R.id.BtnMainCourse);
		 btnBeverages = (Button)view.findViewById(R.id.BtnBeverages);
		 btnShortOrder = (Button)view.findViewById(R.id.BtnShortOrders);
		 btnDesserts = (Button)view.findViewById(R.id.BtnDesserts);
		 btnListOrders = (Button)view.findViewById(R.id.BtnListOrders);
		 
		 btnAppetizer.setOnClickListener(btnClickListener);
		 btnMainCourse.setOnClickListener(btnClickListener);
		 btnBeverages.setOnClickListener(btnClickListener);
		 btnShortOrder.setOnClickListener(btnClickListener);
		 btnDesserts.setOnClickListener(btnClickListener);
		 btnListOrders.setOnClickListener(btnClickListener);
		 
	
		 super.onViewCreated(view, savedInstanceState);
	}
	

}
