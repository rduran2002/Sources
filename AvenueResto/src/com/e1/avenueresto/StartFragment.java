package com.e1.avenueresto;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class StartFragment extends Fragment{
	
	  Button mainbtn;
	
	 @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	
		return inflater.inflate(R.layout.startfragment,container, false);
	}
	 

	 
	  Button.OnClickListener btnClickListener = new Button.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				User u = DataManager.GetUser("Cashier", "1");
				
				if(u!=null)
				{
					Fragment newFragment;
					
					newFragment = new MainFragment(u);
					FragmentTransaction ft =getFragmentManager().beginTransaction();
					ft.replace(R.id.mainfragment, newFragment);
					ft.addToBackStack(null);
					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					ft.commit();
				}
				else
				{
					Toast.makeText(v.getContext(), "Invalid user!", Toast.LENGTH_SHORT).show();
				}
			}
		};
		
		
		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			
			mainbtn = (Button) view.findViewById(R.id.mainbtn);
			
			mainbtn.setOnClickListener(btnClickListener);
			super.onViewCreated(view, savedInstanceState);
		}

}
