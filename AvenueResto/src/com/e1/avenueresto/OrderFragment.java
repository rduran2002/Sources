package com.e1.avenueresto;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class OrderFragment extends Fragment {
	
	String menuType;
	
	public OrderFragment(String menutype ) {
		// TODO Auto-generated constructor stub
		menuType = menutype;
	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		return inflater.inflate(R.layout.order,container,false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	    TextView tv =(TextView) view.findViewById(R.id.menutitle);
	    tv.setText(menuType);
		super.onViewCreated(view, savedInstanceState);
	}

}
