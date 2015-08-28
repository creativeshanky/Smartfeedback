package com.verizon.smartfeedback;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class DisLikeFragment extends Fragment{
	   View disLikeFragmentView;
	   EditText otherReason;
	   CustomerTO customer;
	   Spinner spinner;
	   
	   DisLikeFragment(CustomerTO customer){
		   this.customer = customer;
	   }
	   @Override
	   public View onCreateView(LayoutInflater inflater,
	      ViewGroup container, Bundle savedInstanceState) {
		  disLikeFragmentView = inflater.inflate(R.layout.dislike_fragment, null);
		  otherReason = (EditText) disLikeFragmentView.findViewById(R.id.other_reason);
		  
		  spinner = (Spinner) disLikeFragmentView.findViewById(R.id.dislike_dropdown);
		  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
		        R.array.dislike_array, android.R.layout.simple_spinner_item);
		  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  spinner.setAdapter(adapter);
		  spinner.setOnItemSelectedListener(new SpinnerActivity());
		  
		  
		  
		  Button btnNext = (Button) disLikeFragmentView.findViewById(R.id.btn_next_red);
		  btnNext.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				customer.setBadFeedback(spinner.getSelectedItem().toString());
				customer.setBadComments(otherReason.getText().toString());
				getActivity().getSupportFragmentManager().beginTransaction().replace(android.R.id.content,new IdeasFragment(customer)).commit();
			}
		  });
		  
		  return disLikeFragmentView;
	   }	   	   
	   public class SpinnerActivity extends Activity implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			String option = parent.getItemAtPosition(position).toString();
			if(option.equalsIgnoreCase("Choose one..")){
				otherReason.setVisibility(View.GONE);
			}else{
				otherReason.setVisibility(View.VISIBLE);
				if(option.equalsIgnoreCase("other"))
					otherReason.setHint("Please enter what you don't like");
				else if(option.equalsIgnoreCase("Poor Customer Support"))
					otherReason.setHint("Details of CustomerCare Representative");
				else if(option.equalsIgnoreCase("Signal Loss/Dropped calls"))
					otherReason.setHint("Region where you are facing this issue");
				else if(option.equalsIgnoreCase("Expensive Tariff"))
					otherReason.setHint("Details of the tariff plan");
				else if(option.equalsIgnoreCase("Issues with Delivery"))
					otherReason.setHint("Additional comments");
				else if(option.equalsIgnoreCase("Warranty Issues"))
					otherReason.setHint("Contract details (eg: Contract #)");
				else if(option.equalsIgnoreCase("Device Issues"))
					otherReason.setHint("Please be specific on the issue");				
			}
				
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		    
		}
	   
}