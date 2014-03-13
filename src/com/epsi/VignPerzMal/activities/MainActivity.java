package com.epsi.VignPerzMal.activities;

import java.util.AbstractList;
import java.util.ArrayList;

import com.epsi.VignPerzMal.adapters.StoreAdapterProvider;
import com.epsi.VignPerzMal.database.DAL;
import com.epsi.VignPerzMal.database.StoreDAL;
import com.epsi.VignPerzMal.model.Store;
import com.epsi.VignPerzMal.storelocator.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private TextView etZipCode;
	private ImageButton btnSearch;
	private ImageButton btnAroundMe;
	private Button btnMap;
	private ListView lvStores;
	
	private DAL<Store> dal;
	private AbstractList<Store> stores;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		dal = new StoreDAL(getApplicationContext());
		stores = new ArrayList<Store>();

		// Get controls
		etZipCode = (TextView)findViewById(R.id.etZipCode);
		btnSearch = (ImageButton)findViewById(R.id.btnSearch);
		btnAroundMe = (ImageButton)findViewById(R.id.btnAroundMe);
		btnMap = (Button)findViewById(R.id.btnMap);
		lvStores = (ListView)findViewById(R.id.lvStores);

		// Create events
		btnSearch.setOnClickListener(this);
		btnAroundMe.setOnClickListener(this);
		btnMap.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();

		stores = dal.get();
		
		displayStores();
	}

	@Override
	public void onClick(View v) {
		if(v == btnSearch)
			searchbyZipCode();
		else if(v == btnAroundMe)
			searchAroundMe();
		else if(v == btnMap)
			showMap();
	}
	
	private void displayStores() {
		StoreAdapterProvider adapterProvider = new StoreAdapterProvider();
		ListAdapter adapter = adapterProvider.adapt(getApplicationContext(), stores);
		lvStores.setAdapter(adapter);
	}
	
	private void searchbyZipCode() {
		
		String zipCode = etZipCode.getText().toString();
		stores = dal.search(zipCode);
		
		displayStores();
	}
	
	private void searchAroundMe() {
		
		// TODO: Launch search around the user
	}
	
	private void showMap() {
		
		// TODO: Show stores on Google Maps
	}
}