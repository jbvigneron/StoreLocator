package com.epsi.VignPerzMal.activities;

import java.util.AbstractList;

import com.epsi.VignPerzMal.adapters.StoreAdapterProvider;
import com.epsi.VignPerzMal.controllers.StoresProviderController;
import com.epsi.VignPerzMal.models.Store;
import com.epsi.VignPerzMal.parser.StoreTags;
import com.epsi.VignPerzMal.storelocator.R;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SearchActivity extends ListActivity {

	private ProgressDialog pDialog;
	private AbstractList<Store> stores = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_search);

		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				// Get values from selected ListItem
				String libelle = ((TextView) view.findViewById(R.id.libelle)).getText().toString();
				String adresse = ((TextView) view.findViewById(R.id.adresse)).getText().toString();
				String phone = ((TextView) view.findViewById(R.id.phone)).getText().toString();

				// Starting single contact activity
				Intent in = new Intent(getApplicationContext(), SingleContactActivity.class);
				in.putExtra(StoreTags.LABEL, libelle);
				in.putExtra(StoreTags.ADDRESS, adresse);
				in.putExtra(StoreTags.PHONE, phone);

				startActivity(in);
			}
		});

		// Calling a-sync task to get stores
		new GetStoresAsyncTask().execute();
	}

	/**
	 * A-sync Task to get stores from JSON feed or from database
	 */
	private class GetStoresAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			// Showing progress dialog
			pDialog = new ProgressDialog(SearchActivity.this);
			pDialog.setMessage("Chargement des magasins en cours...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			StoresProviderController controller = new StoresProviderController();
			stores = controller.retrieve(getApplicationContext());

			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();

			// Show stores in ListView
			StoreAdapterProvider adapterProvider = new StoreAdapterProvider();
			ListAdapter adapter = adapterProvider.adapt(SearchActivity.this, stores);
			setListAdapter(adapter);
		}
	}
}