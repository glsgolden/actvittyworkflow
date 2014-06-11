package com.mstar.clipper.nightly.configuration.dto;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.converters.basic.BooleanConverter;

@XStreamAlias("Task")
public class Task implements Comparable<Task>
{
	@XStreamAlias("id")
	@XStreamAsAttribute
	private String id;

	@XStreamAlias("name")
	@XStreamAsAttribute
	private String name;

	@XStreamAlias("sequence")
	@XStreamAsAttribute
	private int sequence;

	@XStreamAlias("Description")
	@XStreamAsAttribute
	private String desc;

	@XStreamAlias("successNotification")
	@XStreamAsAttribute
	private boolean successNotification = true;

	@XStreamAlias("failureNotification")
	@XStreamAsAttribute
	private boolean failureNotification = true;

	@XStreamAlias("startNotification")
	@XStreamAsAttribute
	private boolean startNotification = true;

	@XStreamAlias("active")
	@XStreamAsAttribute
	@XStreamConverter(value = BooleanConverter.class)
	private boolean active;

	@XStreamImplicit(itemFieldName = "Activity")
	private List<Activity> activities;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{

		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getSequence()
	{
		return sequence;
	}

	public void setSequence(int sequence)
	{
		this.sequence = sequence;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public boolean isSuccessNotification()
	{
		return successNotification;
	}

	public void setSuccessNotification(boolean successNotification)
	{
		this.successNotification = successNotification;
	}

	public boolean isFailureNotification()
	{
		return failureNotification;
	}

	public void setFailureNotification(boolean failureNotification)
	{
		this.failureNotification = failureNotification;
	}

	public boolean isStartNotification()
	{
		return startNotification;
	}

	public void setStartNotification(boolean startNotification)
	{
		this.startNotification = startNotification;
	}

	public List<Activity> getActivities()
	{
		return activities;
	}

	public void setActivities(List<Activity> activities)
	{
		this.activities = activities;
	}

	public int compareTo(Task object)
	{
		int sequence = object.getSequence();
		return this.sequence - sequence;
	}

}
