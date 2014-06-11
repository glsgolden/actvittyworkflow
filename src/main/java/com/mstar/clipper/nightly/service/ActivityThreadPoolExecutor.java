package com.mstar.clipper.nightly.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.base.log.Log;
import com.mstar.clipper.nightly.executor.ActivityExecutor;

public class ActivityThreadPoolExecutor extends ThreadPoolExecutor
{

	
	
	public ActivityThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory)
	{
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void beforeExecute(Thread t, Runnable r)
	{
			super.beforeExecute(t, r);
			
			if(r instanceof ActivityExecutor)
			{
				
				ActivityExecutor a = (ActivityExecutor) r;
				Log.logInfo(ActivityThreadPoolExecutor.class, " Ganesh " + a.getName() + " started");
			}
			
			
	}
	
	@Override
	protected void afterExecute(Runnable r, Throwable t)
	{
		// TODO Auto-generated method stub
		super.afterExecute(r, t);
		
		if(r instanceof ActivityExecutor)
		{
			
			ActivityExecutor a = (ActivityExecutor) r;
			Log.logInfo(ActivityThreadPoolExecutor.class, " Ganesh " + a.getName() + " Finished");
			Log.logInfo(ActivityThreadPoolExecutor.class, " Ganesh " + a.getName() + " Finished");
		}
	}
	
	

	
	
	

	
}
