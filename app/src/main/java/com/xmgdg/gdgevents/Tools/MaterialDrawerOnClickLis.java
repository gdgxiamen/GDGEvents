
package com.xmgdg.gdgevents.Tools;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;


/**
 * Created by 雨蓝 on 2015/4/4.
 * 材料设计抽屉按键监听
 */
public class MaterialDrawerOnClickLis {

	//设置抽屉的点击监听器
	public void setDrawerIntent(Activity activity, int Position,
	                            DrawerLayout mDrawerLayout) {

		switch (Position) {

			default:
				Toast.makeText(activity.getApplicationContext(), "异常按动", Toast.LENGTH_LONG).show();

		}

	}
}
