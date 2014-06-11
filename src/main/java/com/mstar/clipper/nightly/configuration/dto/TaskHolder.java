package com.mstar.clipper.nightly.configuration.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @author gshirsa
 * 
 */
public class TaskHolder
{
	@XStreamAlias("Task")
	@XStreamImplicit
	private List<Task> tasks;
	
	private List<Activity> activities;
	
	private Map<String, Activity> activityMap;
	

	public List<Task> getTasks()
	{
		sortTasks();
		return tasks;
	}

	public void setTasks(List<Task> tasks)
	{
		this.tasks = tasks;
//		sortTasks();
	}
	
	public  void sortTasks()
	{
		Collections.sort(tasks);
	}
	
	public Task getTask(String name)
	{
		Task t = null;
		for(Task task : getTasks())
		{
			if(name.equals(task.getName()) || name.equals(task.getId()))
			{
				t = task;
			}
		}
		return t;
	}
	
	public List<Task> getTask(List<String> name)
	{
		
		List<Task> tasks = new ArrayList<Task>();
		
		for(String taskName : name)
		{
			Task t = getTask(taskName);
			tasks.add(t);
		}
		return tasks;
	}
	
	public List<Task> getTaskFromToTO(String from, String to)
	{
		
		List<Task> tasks = new ArrayList<Task>();
		boolean fromFound = false;
		
		for(Task task : getTasks())
		{
			if(fromFound)
			{
				tasks.add(task);
				if(to.equals(task.getName()) || to.equals(task.getId()))
				{
					break;
				}
			}
			
			if(from.equals(task.getName()) || from.equals(task.getId()))
			{
				tasks.add(task);
				fromFound = true;
			}
		}
		
		return tasks;
	}
	
	
	public List<Activity> getAllActivities()
	{
		if(activities == null)
		{
			this.activityMap = new HashMap<String, Activity>();
			this.activities = new ArrayList<Activity>();
			for(Task task : getTasks())
			{
				if(task.isActive())
				{
					for(Activity activity : task.getActivities())
					{
						activityMap.put(activity.getName(), activity);
						activities.add(activity);
					}
				}
				
			}
			prepareActivities();
		}
		return activities;
		
	}
	
	public List<Activity> getAllActiveActivities()
	{
		getAllActivities();
		
		List<Activity> activities = new ArrayList<Activity>();
		for(Activity activity : this.activities)
		{
			if(activity.isActive())
			{
				activities.add(activity) ;
			}
		}
		
		return activities;
		
	}
	
	
	public void prepareActivities()
	{
		for(Activity activity : this.activities)
		{
			if(activity.getDependOnActivity()!=null)
			{
				if(!"".equals(activity.getDependOnActivity()))
				{
					Activity dependActivity = this.activityMap.get(activity.getDependOnActivity());
					activity.setDependActivity(dependActivity);
				}
			}
		}
	}
	
}
