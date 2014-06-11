package com.mstar.clipper.nightly.configuration.dto;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Configuration")
public class Configuration
{
	// @XStreamImplicit(itemFieldName="MailServer")
	@XStreamAlias("MailServer")
	private MailServer mailServer;

	@XStreamAlias("Notification")
	private Notification notification;

	@XStreamAlias("Tasks")
	private TaskHolder tasks;

	public MailServer getMailServer()
	{
		return mailServer;
	}

	public void setMailServer(MailServer mailServer)
	{
		this.mailServer = mailServer;
	}

	public TaskHolder getTasks()
	{
		return tasks;
	}

	public void setTasks(TaskHolder tasks)
	{
		this.tasks = tasks;
	}

	public Notification getNotification()
	{
		return notification;
	}

	public void setNotification(Notification notification)
	{
		this.notification = notification;
	}
	
	public List<Activity> getActivities()
	{
		return this.tasks.getAllActiveActivities();
	}

}
