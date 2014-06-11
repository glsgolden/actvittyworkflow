package com.mstar.clipper.nightly.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.base.constant.Constant;
import com.mstar.clipper.nightly.NightlyProcessAutomate;
import com.mstar.clipper.nightly.configuration.dto.Activity;
import com.mstar.clipper.nightly.configuration.dto.Configuration;

public class ActivitiesExecutorService implements Runnable
{

	public static List<String> cmds = new ArrayList<String>();

	public static void main(String[] args) throws InterruptedException
	{
		ActivitiesExecutorService a = new ActivitiesExecutorService();
		Thread t = new Thread(a);
		t.start();
	}

	public void run()
	{

		try
		{
			NightlyProcessAutomate process = new NightlyProcessAutomate();

			Configuration conf = process.getConfiguration();

			List<Activity> activities = conf.getActivities();

			// RejectedExecutionHandler implementation
			RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
			// Get the ThreadFactory implementation to use
			ThreadFactory threadFactory = Executors.defaultThreadFactory();
			// creating the ThreadPoolExecutor

			ActivityHolder activityHolder = new ActivityHolder();
			activityHolder.setWaitingTasks(activities);

			ThreadPoolExecutor executorPool = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5), threadFactory, rejectionHandler);
			// start the monitoring thread
			MyMonitorThread monitor = new MyMonitorThread(executorPool, 3);
			Thread monitorThread = new Thread(monitor);

			monitorThread.start();


			List<Activity> activities2 = activityHolder.getWaitingTasks();
			for (Activity activity : activities2)
			{
				if (activity.isActive())
				{
					ActivityExecutor workerThread = new ActivityExecutor(activity, activityHolder);
					executorPool.execute(workerThread);
				}
				Thread.sleep(5000);
			}
			monitor.shutdown();
			executorPool.shutdown();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
