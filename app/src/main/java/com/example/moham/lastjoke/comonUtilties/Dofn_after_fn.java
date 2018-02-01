package com.example.moham.lastjoke.comonUtilties;

import android.os.AsyncTask;

public class Dofn_after_fn {
	

public Dofn_after_fn(Done befor,Done2 after) {
	// TODO Auto-generated constructor stub
		new dobeforandafter(befor, after).execute();
	
}

class dobeforandafter extends AsyncTask<Void, String, Void>
{

	Done befor ;
	Done2 after;
	public dobeforandafter(Done befor,Done2 after) {
		// TODO Auto-generated constructor stub
		this.befor=befor;
		this.after=after;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		

		super.onPreExecute();
	}
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		befor.done();
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		after.done();
		super.onPostExecute(result);
	}
}

}
