package com.mstar.clipper.nightly.service;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.base.log.Log;
import com.mstar.clipper.nightly.NightlyProcessAutomate;
import com.mstar.clipper.nightly.activity.TaskExecutor;
import com.mstar.clipper.nightly.configuration.dto.Activity;
import com.mstar.clipper.nightly.configuration.dto.Configuration;
import com.mstar.clipper.nightly.configuration.dto.Task;
import com.mstar.clipper.nightly.executor.ActivityHolder;
import com.mstar.clipper.nightly.executor.MyMonitorThread;
import com.mstar.clipper.nightly.executor.RejectedExecutionHandlerImpl;
import com.mstar.clipper.nightly.executor.ActivityExecutor;

public class TaskExecutorServiceImpl implements TaskExecutorService
{
	private Class<TaskExecutorServiceImpl> log = TaskExecutorServiceImpl.class;
	private TaskExecutor taskExecutor = new TaskExecutor();
	private List<Task> tasks;
	private List<Activity> activities;

	public void executeTasks(List<Task> tasks) throws Exception
	{

		this.tasks = tasks;
		for (Task task : tasks)
		{
			if (task.isActive())
			{
				taskExecutor.executeTask(task);
			}
		}
	}

	public void executeTask(List<Activity> activities) throws Exception
	{
		this.activities = activities;
	}

	public void setActivities(List<Activity> activities)
	{
		this.activities = activities;
	}

	public void run()
	{
		try
		{

			// RejectedExecutionHandler implementation
			RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
			// Get the ThreadFactory implementation to use
			ThreadFactory threadFactory = Executors.defaultThreadFactory();
			// creating the ThreadPoolExecutor

			ActivityHolder activityHolder = new ActivityHolder();
			activityHolder.setWaitingTasks(activities);

			int size = activities.size();
			int maxPoolSize = size / 2;
			ThreadPoolExecutor executorPool = new ThreadPoolExecutor(10, maxPoolSize, 90, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(5), threadFactory, rejectionHandler);
			// start the monitoring thread
			MyMonitorThread monitor = new MyMonitorThread(executorPool, 3, size);
			Thread monitorThread = new Thread(monitor);
			monitorThread.start();

			List<Activity> activities2 = activityHolder.getWaitingTasks();
			for (Activity activity : activities2)
			{

				Log.logInfo(log, "Activity " + activity.getName() + " put in Executor queue");

				ActivityExecutor activityExec = new ActivityExecutor(activity, activityHolder);
				executorPool.execute(activityExec);
				Log.logInfo(log, "Activity " + activity.getName() + " put in Executor queue");

				// Thread.sleep(5000);
			}

			// Thread.sleep(30000);
			// shut down the pool
			// executorPool.shutdown();
			// shut down the monitor thread
			// Thread.sleep(5000);
			// monitor.shutdown();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
