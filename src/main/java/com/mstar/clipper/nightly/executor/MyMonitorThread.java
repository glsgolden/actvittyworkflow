package com.mstar.clipper.nightly.executor;

import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.lang.time.DurationFormatUtils;

public class MyMonitorThread implements Runnable
{
	private ThreadPoolExecutor executor;

	private int seconds;
	private int noOfActivity;

	private boolean run = true;

	public MyMonitorThread(ThreadPoolExecutor executor, int delay)
	{
		this.executor = executor;
		this.seconds = delay;
	}
	
	public MyMonitorThread(ThreadPoolExecutor executor, int delay, int noOfActivity)
	{
		this.executor = executor;
		this.seconds = delay;
		this.noOfActivity = noOfActivity;
	}

	public void shutdown()
	{
		this.run = false;
	}

	public void run()
	{
		while (run)
		{
			
			System.out.println(String.format("[monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s", this.executor.getPoolSize(), this.executor.getCorePoolSize(),
					this.executor.getActiveCount(), this.executor.getCompletedTaskCount(), this.executor.getTaskCount(), this.executor.isShutdown(), this.executor.isTerminated()));
			
			if(executor.getCompletedTaskCount() == noOfActivity)
			{
				this.shutdown();
				executor.shutdown();
			}
			
			try
			{
				Thread.sleep(seconds * 1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

	}
}