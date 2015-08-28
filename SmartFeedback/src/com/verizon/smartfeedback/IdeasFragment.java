package com.verizon.smartfeedback;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import com.google.gson.Gson;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class IdeasFragment extends Fragment{
	   View ideasFragmentView;
	   EditText ideasField,mobNumber;
	   CustomerTO customer;
	   Button btnFinish;
	   
	   IdeasFragment(CustomerTO customer){
		   this.customer = customer;
	   }
	   @Override
	   public View onCreateView(LayoutInflater inflater,
	      ViewGroup container, Bundle savedInstanceState) {
		  ideasFragmentView = inflater.inflate(R.layout.ideas_fragment, null);
		  ideasField = (EditText) ideasFragmentView.findViewById(R.id.ideasField);
		  mobNumber = (EditText) ideasFragmentView.findViewById(R.id.mobNumber);
		  btnFinish = (Button) ideasFragmentView.findViewById(R.id.btn_finish);
		  
		  
		  
		  
		  
		  Button btnFinish = (Button) ideasFragmentView.findViewById(R.id.btn_finish);
		  btnFinish.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				try {
					customer.setIdeas(ideasField.getText().toString());
					customer.setMobileNumber(mobNumber.getText().toString());
					
					/*String json = new Gson().toJson(customer);
					HttpPost httpPost = new HttpPost("http://localhost:8080");
				    StringEntity entity = new StringEntity(json, HTTP.UTF_8);
				    entity.setContentType("application/json");
				    httpPost.setEntity(entity);
				    HttpClient client = new DefaultHttpClient();
					HttpResponse response = client.execute(httpPost);*/
					
					getActivity().getSupportFragmentManager().beginTransaction().replace(android.R.id.content,new ThanksFragment()).commit();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		  });
		  
		  return ideasFragmentView;
	   }	   	   	  
	   
}