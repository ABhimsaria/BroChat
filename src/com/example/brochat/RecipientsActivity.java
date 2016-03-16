package com.example.brochat;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RecipientsActivity extends ListActivity {

	public static final String TAG=RecipientsActivity.class.getSimpleName();
	
	protected List<ParseUser> mFriends;
	protected ParseRelation<ParseUser> mFriendsRelation;
	protected ParseUser mCurrentUser;
	protected MenuItem mSendMenuItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_recipients);
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	}
	@Override
	public void onResume() {
		super.onResume();
		
		mCurrentUser = ParseUser.getCurrentUser();
		mFriendsRelation = mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);
		
		setProgressBarIndeterminateVisibility(true);
		
		ParseQuery<ParseUser> query=mFriendsRelation.getQuery();
		query.addAscendingOrder(ParseConstants.KEY_USERNAME);
		query.findInBackground(new FindCallback<ParseUser>() {
			@Override
			public void done(List<ParseUser> friends, ParseException e) {

				setProgressBarIndeterminateVisibility(false);
				if(e ==  null){
					mFriends = friends;
					
					String[] usernames= new String[mFriends.size()];
					int i=0;
					for(ParseUser user1: mFriends){
						usernames[i]= user1.getUsername();
						i++;
					}
					ArrayAdapter<String> adapter= new ArrayAdapter<String>
												  (getListView().getContext(), 
												   android.R.layout.simple_list_item_checked, 
												   usernames);
					setListAdapter(adapter);
					
				}
			
			else{
				Log.e(TAG, e.getMessage());
				AlertDialog.Builder builder = new AlertDialog.Builder(RecipientsActivity.this);
				builder.setMessage(e.getMessage())
						.setTitle("We'r Sorry!")
						.setPositiveButton(android.R.string.ok, null);
				AlertDialog dialog=builder.create();
				dialog.show();
			}
		}
	});
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recipients, menu);
		mSendMenuItem = menu.getItem(0);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
		NavUtils.navigateUpFromSameTask(this);
		return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
	mSendMenuItem.setVisible(true);
	}
}
