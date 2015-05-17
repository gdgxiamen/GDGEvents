package com.xmgdg.gdgevents.Tools;

/**
 * Created by 雨蓝 on 2015/4/4.
 * 抽屉的 RecyclerView 适配器
 */

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xmgdg.gdgevents.R;

public class MetarialDrawerAdapter extends RecyclerView.Adapter<MetarialDrawerAdapter.ViewHolder> {

	private static final int TYPE_HEADER = 0;
	private static final int TYPE_ITEM = 1;
	private final String logtag = "抽屉适配器";

	private String mNavTitles[];
	private int mIcons[];

	private String name;
	private Uri profile;
	private String email;

	//activity生命量
	private Context context;
	private Activity activity;
	DrawerLayout drawerLayout;

	public MetarialDrawerAdapter(String Titles[], int Icons[], String Name,
	                             String Email, Uri Profile,
	                             Activity passedActivity, DrawerLayout drawerLayout) {
		mNavTitles = Titles;
		mIcons = Icons;
		name = Name;
		email = Email;
		profile = Profile;

		this.activity = passedActivity;
		this.drawerLayout = drawerLayout;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		int Holderid;

		TextView textView;
		ImageView imageView;
		ImageView profile;
		TextView Name;
		TextView email;
		Activity activityinviewhold;
		Context contextinviewhold;
		MaterialDrawerOnClickLis materialDrawerOnClickLis;
		DrawerLayout drawerLayout;

		public ViewHolder(View itemView, int ViewType, Activity ac,
		                  DrawerLayout mdrawerLayout) {

			super(itemView);
			activityinviewhold = ac;
			contextinviewhold = ac.getApplicationContext();
			itemView.setClickable(true);
			itemView.setOnClickListener(this);
			drawerLayout = mdrawerLayout;
			materialDrawerOnClickLis = new MaterialDrawerOnClickLis();


			if (ViewType == TYPE_ITEM) {
				textView = (TextView) itemView.findViewById(R.id.rowText);
				imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
				Holderid = 1;
			} else {


				Name = (TextView) itemView.findViewById(R.id.name);
				email = (TextView) itemView.findViewById(R.id.email);
				profile = (ImageView) itemView.findViewById(R.id.circleView);
				Holderid = 0;
			}
		}

		@Override
		public void onClick(View v) {

			materialDrawerOnClickLis.setDrawerIntent(activityinviewhold, getPosition(), drawerLayout);

		}

	}

	@Override
	public MetarialDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		if (viewType == TYPE_ITEM) {
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_drawer_item_row, parent, false);

			ViewHolder vhItem = new ViewHolder(v, viewType, activity, drawerLayout);
			return vhItem;

		} else if (viewType == TYPE_HEADER) {

			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_toolbar_header, parent,
					false);

			ViewHolder vhHeader = new ViewHolder(v, viewType, activity, drawerLayout);

			return vhHeader;


		}
		return null;

	}

	@Override
	public void onBindViewHolder(MetarialDrawerAdapter.ViewHolder holder, int position) {
		if (holder.Holderid == 1) {
			holder.textView.setText(mNavTitles[position - 1]);
			holder.imageView.setImageResource(mIcons[position - 1]);
		} else {
			try {
				holder.profile.setImageURI(profile);
			} catch (Exception e) {
				Log.e(logtag, "用户图片不存在");
			}

			holder.Name.setText(name);
			holder.email.setText(email);
		}
	}


	@Override
	public int getItemCount() {
		return mNavTitles.length + 1;
	}

	@Override
	public int getItemViewType(int position) {
		if (isPositionHeader(position))
			return TYPE_HEADER;

		return TYPE_ITEM;
	}

	private boolean isPositionHeader(int position) {
		return position == 0;
	}

}
