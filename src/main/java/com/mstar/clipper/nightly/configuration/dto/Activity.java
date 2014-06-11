package com.mstar.clipper.nightly.configuration.dto;

import com.mstar.clipper.nightly.constant.Constant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Activity")
public class Activity
{
	@XStreamAlias("name")
	@XStreamAsAttribute
	private String name;
	
	@XStreamAlias("type")
	@XStreamAsAttribute
	private String type;
	
	@XStreamAlias("failedAttempt")
	@XStreamAsAttribute
	private int failedAttempt;
	
	@XStreamAlias("completionTimeInSecond")
	@XStreamAsAttribute
	private int completionTimeInSecond;
	
	@XStreamAlias("failedNotification")
	@XStreamAsAttribute
	private boolean failedNotification;
	
	@XStreamAlias("parallelExecution")
	@XStreamAsAttribute
	private boolean parallelExecution;
	
	@XStreamAlias("active")
	@XStreamAsAttribute
	private boolean active;
	
	@XStreamAlias("BatchFileLocation")
	private String batchFileLocation;
	
	@XStreamAlias("dependOn")
	@XStreamAsAttribute
	private String dependOnActivity;
	
	private Activity dependActivity;
	
	private int status = Constant.WAITING_STATE;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public int getFailedAttempt()
	{
		return failedAttempt;
	}

	public void setFailedAttempt(int failedAttempt)
	{
		this.failedAttempt = failedAttempt;
	}

	public int getCompletionTimeInSecond()
	{
		return completionTimeInSecond;
	}

	public void setCompletionTimeInSecond(int completionTimeInSecond)
	{
		this.completionTimeInSecond = completionTimeInSecond;
	}

	public boolean isFailedNotification()
	{
		return failedNotification;
	}

	public void setFailedNotification(boolean failedNotification)
	{
		this.failedNotification = failedNotification;
	}

	public boolean isParallelExecution()
	{
		return parallelExecution;
	}

	public void setParallelExecution(boolean parallelExecution)
	{
		this.parallelExecution = parallelExecution;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public String getBatchFileLocation()
	{
		return batchFileLocation;
	}

	public void setBatchFileLocation(String batchFileLocation)
	{
		this.batchFileLocation = batchFileLocation;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}
	
	
	public String getDependOnActivity()
	{
		return dependOnActivity;
	}

	public void setDependOnActivity(String dependOnActivity)
	{
		this.dependOnActivity = dependOnActivity;
	}

	public Activity getDependActivity()
	{
		return dependActivity;
	}

	public void setDependActivity(Activity dependActivity)
	{
		this.dependActivity = dependActivity;
	}

	public boolean isReadyToExecute()
	{
		if(this.dependActivity == null)
		{
			return true;
		}
		
		if(this.dependActivity.getStatus() == Constant.COMPLETED_STATE)
		{
			return true;
		}
		return false;
	}
	
	public boolean isDependentActivityFailed()
	{
		if(this.dependActivity == null)
		{
			return false;
		}
		
		if(this.dependActivity.getStatus() == Constant.FAILED_STATE)
		{
			return true;
		}
		return false;
	}
	

}
