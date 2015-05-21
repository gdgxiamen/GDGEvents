package com.xmgdg.gdgevents;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

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

    @InjectView(R.id.title) TextView title;
    @InjectView(R.id.date) TextView data;
    @InjectView(R.id.start_time) TextView startTime;
    @InjectView(R.id.event_description) TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventinfo);
        ButterKnife.inject(this);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        if(getIntent() != null) {
            mTopic = ((Topic) getIntent().getParcelableExtra("eventInfo"));

            title.setText(mTopic.getTitle());
            data.setText(mTopic.getStart());
            startTime.setText(mTopic.getStart());
            description.setText(mTopic.getDescription());
        }

    }
}
