package com.mstar.clipper.nightly.activity;

import com.mstar.clipper.nightly.constant.Constant;

public class ActivityFactory
{

	private static ActivityExec activityExec;

	public static ActivityExec getActivityExecutor(String type)
	{
		if (Constant.BATCH_TYPE.equals(type))
		{
			activityExec = new BatchFileExecutorImpl();
		}
		return activityExec;
	}

}
