package com.verizon.smartfeedback;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ThanksFragment extends Fragment{
	   View thanksFragmentView;
	   
	   
	   @Override
	   public View onCreateView(LayoutInflater inflater,
	      ViewGroup container, Bundle savedInstanceState) {
		  thanksFragmentView = inflater.inflate(R.layout.thanks_fragment, null);
		  new Timer().schedule( new TimerTask() {
				@Override
				public void run() {
					getActivity().finish();
				}
			},1500);
		  return thanksFragmentView;
	   }	   	   	  
	   
}