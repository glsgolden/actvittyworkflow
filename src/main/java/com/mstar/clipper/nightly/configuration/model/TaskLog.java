package com.mstar.clipper.nightly.configuration.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskLog
{
	private Long id;
	private String taskName;
	private Date startTime;
	private Date endTime;
	private String description;
	private String status;
	private String message;
	private List<ActivityLog> activityLogs;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getTaskName()
	{
		return taskName;
	}

	public void setTaskName(String taskName)
	{
		this.taskName = taskName;
	}

	public Date getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	public Date getEndTime()
	{
		return endTime;
	}

	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public List<ActivityLog> getActivityLogs()
	{
		return activityLogs;
	}

	public void setActivityLogs(List<ActivityLog> activityLogs)
	{
		this.activityLogs = activityLogs;
	}

	public void addActivityLog(ActivityLog activityLog)
	{
		if (this.activityLogs == null)
		{
			this.activityLogs = new ArrayList<ActivityLog>();
		}
		this.activityLogs.add(activityLog);
	}

}
