package com.core.corelib.ui.animator.activity;


import android.app.Activity;

import com.core.corelib.R;


public class ActivityAnimator
{
	public static void flipHorizontalAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.anim_flip_horizontal_in, R.anim.anim_flip_horizontal_out);
	}
	
	public static void flipVerticalAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.anim_flip_vertical_in, R.anim.anim_flip_vertical_out);
	}
	
	public static void fadeAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
	}
	
	public static void disappearTopLeftAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.anim_disappear_top_left_in, R.anim.anim_disappear_top_left_out);
	}
	
	public static void appearTopLeftAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.anim_appear_top_left_in, R.anim.anim_appear_top_left_out);
	}
	
	public static void disappearBottomRightAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.anim_disappear_bottom_right_in, R.anim.anim_disappear_bottom_right_out);
	}
	
	public static void appearBottomRightAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.anim_appear_bottom_right_in, R.anim.anim_appear_bottom_right_out);
	}
	
	public static void unzoomAnimation(Activity a)
	{
		a.overridePendingTransition(R.anim.anim_unzoom_in, R.anim.anim_unzoom_out);
	}
    
    public static void pullRightPushLeft(Activity a)
    {
        a.overridePendingTransition(R.anim.anim_pull_in_right, R.anim.anim_push_out_left);
    }

    public static void pullLeftPushRight(Activity a)
    {
        a.overridePendingTransition(R.anim.anim_pull_in_left, R.anim.anim_push_out_right);
    }

	public static void pullBottomPushTop(Activity a)
	{
		a.overridePendingTransition(R.anim.anim_pull_in_bottom, R.anim.anim_push_out_top);
	}

	public static void pullTopPushBottom(Activity a)
	{
		a.overridePendingTransition(R.anim.anim_pull_in_top, R.anim.anim_push_out_bottom);
	}

	public static void fromBottomToTop(Activity a)
	{
		a.overridePendingTransition(R.anim.anim_pull_in_bottom, R.anim.anim_fade_out_fast);
	}

	public static void fromTopToBottom(Activity a)
	{
		a.overridePendingTransition(R.anim.anim_pull_in_top, R.anim.anim_fade_out_fast);
	}
}
