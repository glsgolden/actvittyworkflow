package com.mstar.clipper.nightly.configuration.model;

import java.util.Date;

public class ActivityLog
{
	private Long id;
	private String activityName;
	private String type;
	private String fileLocation;
	private int noOfAttempt;
	private Date startTime;
	private Date endTime;
	private String status;
	private String message;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getActivityName()
	{
		return activityName;
	}

	public void setActivityName(String activityName)
	{
		this.activityName = activityName;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getFileLocation()
	{
		return fileLocation;
	}

	public void setFileLocation(String fileLocation)
	{
		this.fileLocation = fileLocation;
	}

	public int getNoOfAttempt()
	{
		return noOfAttempt;
	}

	public void setNoOfAttempt(int noOfAttempt)
	{
		this.noOfAttempt = noOfAttempt;
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

}
