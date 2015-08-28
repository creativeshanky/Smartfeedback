package com.verizon.smartfeedback;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class QuickPayFragment extends Fragment{
	   View quickPayFragmentView;
	   
	   
	   @Override
	   public View onCreateView(LayoutInflater inflater,
	      ViewGroup container, Bundle savedInstanceState) {
		  quickPayFragmentView = inflater.inflate(R.layout.quickpay_fragment, null);
		  Button btnQuickPay = (Button) quickPayFragmentView.findViewById(R.id.btn_quick_pay);
		  btnQuickPay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager().beginTransaction().replace(android.R.id.content,new SuccessPayFragment()).commit();
			}
		  });
		  return quickPayFragmentView;
	   }	   	   	  
	   
}