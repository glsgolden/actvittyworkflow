package com.mstar.clipper.nightly.executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import com.mstar.clipper.nightly.configuration.dto.Activity;

public class ActivityHolder
{
	public List<Activity> waitingTasks;
	public List<Activity> runningTasks;
	public List<Activity> completedTasks;

	public List<Activity> getWaitingTasks()
	{
		return waitingTasks;
	}
	
	public Activity getWaitingTasks(int index)
	{
		return this.waitingTasks.get(index);
	}

	public void setWaitingTasks(List<Activity> waitingTasks)
	{
		this.waitingTasks = waitingTasks;
		this.waitingTasks = Collections.synchronizedList(this.waitingTasks);
	}
	
	public void addWaitingTasks(Activity waitingTask)
	{
		if(this.waitingTasks == null)
		{
			this.waitingTasks = Collections.synchronizedList(new ArrayList<Activity>());
//			this.waitingTasks = new Vector<Activity>();
		}
		this.waitingTasks.add(waitingTask);
	}
	
	public synchronized void removeFromWaitingState(Activity waitingTask)
	{
		this.waitingTasks.remove(waitingTask);
	}
	

	public List<Activity> getRunningTasks()
	{
		return runningTasks;
	}
	
	public Activity getRunningTasks(int index)
	{
		return runningTasks.get(index);
	}
	

	public void setRunningTasks(List<Activity> runningTasks)
	{
		this.runningTasks = runningTasks;
	}

	public void addRunningTasks(Activity waitingTask)
	{
		if(this.runningTasks == null)
		{
//			this.runningTasks = new ArrayList<Activity>();
			this.runningTasks = Collections.synchronizedList(new ArrayList<Activity>());
//			this.runningTasks = new Vector<Activity>();
		}
		this.runningTasks.add(waitingTask);
	}
	
	public void removeFromRunningState(Activity waitingTask)
	{
		this.runningTasks.remove(waitingTask);
	}
	
	
	public List<Activity> getCompletedTasks()
	{
		return completedTasks;
	}
	
	public Activity getCompletedTasks(int index)
	{
		return completedTasks.get(index);
	}

	public void setCompletedTasks(List<Activity> completedTasks)
	{
		this.completedTasks = completedTasks;
	}

	public void addCompletedTask(Activity completedTask)
	{
		if(this.completedTasks == null)
		{
//			this.completedTasks = new ArrayList<Activity>();
			this.completedTasks = new Vector<Activity>();
		}
		this.completedTasks.add(completedTask);
	}
}
