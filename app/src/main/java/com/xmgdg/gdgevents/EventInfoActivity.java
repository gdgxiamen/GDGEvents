package com.xmgdg.gdgevents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.xmgdg.gdgevents.app.App;
import com.xmgdg.gdgevents.model.Topic;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ye on 15/5/21.
 */
public class EventInfoActivity extends AppCompatActivity {

	private Topic mTopic;
	//toolbar
	private Toolbar toolbar;
	@InjectView(R.id.title)
	TextView title;
	@InjectView(R.id.date)
	TextView data;
//	@InjectView(R.id.start_time)
//	TextView startTime;
	@InjectView(R.id.event_description)
	TextView description;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eventinfo);
		ButterKnife.inject(this);
		toolbar = (Toolbar) findViewById(R.id.tool_bar);
		setSupportActionBar(toolbar);
		//toolbar左返回键
		toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				NavUtils.navigateUpFromSameTask(EventInfoActivity.this);
			}
		});

		if (getIntent() != null) {
			//获取传递对象
			mTopic = ((Topic) getIntent().getParcelableExtra("eventInfo"));
			title.setText(mTopic.getTitle());
			data.setText(mTopic.getStart().replace("+0800", ""));
//			startTime.setText(mTopic.getStart());
			description.setText(mTopic.getDescription());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_eventinfo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_share:
				shareEvents(mTopic);
			default:
				return false;
		}
	}

	//分享活动到其他应用
	private void shareEvents(Topic topic) {
		String shareString = topic.getSummary();

		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_TEXT, shareString);
		shareIntent.setType("text/plain");
		this.startActivity(shareIntent);
	}

}
