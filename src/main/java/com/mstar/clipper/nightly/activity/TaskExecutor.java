package com.mstar.clipper.nightly.activity;

import java.util.Date;
import java.util.List;

import org.apache.commons.io.output.ThresholdingOutputStream;

import com.base.log.Log;
import com.base.util.DateFormatUtils;
import com.base.util.TimeHelper;
import com.mstar.clipper.nightly.configuration.dto.Activity;
import com.mstar.clipper.nightly.configuration.dto.Task;
import com.mstar.clipper.nightly.configuration.model.ActivityLog;
import com.mstar.clipper.nightly.configuration.model.TaskLog;
import com.mstar.clipper.nightly.constant.Constant;
import com.mstar.clipper.nightly.exception.ActivityFailedException;
import com.mstar.clipper.nightly.exception.TaskFailedException;
import com.mstar.clipper.nightly.service.MailServiceImpl;

public class TaskExecutor extends Thread
{
	Class<TaskExecutor> log = TaskExecutor.class;
	private StringBuffer mailContent ; 

	TaskLog taskLog ;
	MailServiceImpl mailService = new MailServiceImpl();

	public void executeTask(Task task) throws TaskFailedException
	{
//		this.run();
		String taskName = task.getName();
		mailContent = new StringBuffer();
		taskLog = new TaskLog();
		
		Log.logInfo(log, taskName + " task has started");
		taskLog.setTaskName(taskName);
		taskLog.setDescription(task.getDesc());
		taskLog.setStartTime(new Date());
		
		Log.logInfo(log, taskName + " task start time " + DateFormatUtils.format(taskLog.getStartTime()));
		
		if(task.isStartNotification())
		{
			sendTaskStartedMail();
		}
		
		try
		{
			executeActivities(task.getActivities());
			
			taskLog.setEndTime(new Date());
			taskLog.setStatus(Constant.SUCCESS_MESSAGE);
			if(task.isSuccessNotification())
			{	
				sendTaskFinishedMail();
			}
			Log.logInfo(log, taskName + " task end time " + DateFormatUtils.format(taskLog.getEndTime()));
			Log.logInfo(log, taskName + " task has Finished");
		}
		catch (TaskFailedException e)
		{
			taskLog.setEndTime(new Date());
			taskLog.setStatus(Constant.FAILED_MESSAGE);
			taskLog.setMessage(e.getMessage());
			if(task.isFailureNotification())
			{
				sendTaskFailedMail();
			}
			
			Log.logError(log, e.getMessage());
			throw new TaskFailedException(e.getMessage());
		}
		
	}
	
	private void executeActivities(List<Activity> activities) throws TaskFailedException 
	{
		try
		{
			for (Activity activity : activities)
			{
				if(activity.isActive())
				{
					executeActivity(activity);
				}
			}
		}
		catch (ActivityFailedException e)
		{
			throw new TaskFailedException(e.getMessage());
		}
		
	}

	private void executeActivity(Activity activity) throws ActivityFailedException
	{
		ActivityLog activityLog = new ActivityLog();
		taskLog.addActivityLog(activityLog);
		

		String activityName = activity.getName();
		activityLog.setActivityName(activityName);
		activityLog.setType(activity.getType());
		activityLog.setStartTime(new Date());
		activityLog.setFileLocation(activity.getBatchFileLocation());

		
		Log.logInfo(log, activityName + " activity has started");
		Log.logInfo(log, "Activity type is " + activity.getType());
		Log.logInfo(log, "Batch File location" + activity.getBatchFileLocation());
		Log.logInfo(log, activityName + " activity start time " + DateFormatUtils.format(activityLog.getStartTime()));
		
		boolean finished = false;
		int noOfAttempt = 0;
		
		ActivityExec activityExec = ActivityFactory.getActivityExecutor(activity.getType());
		activityExec.setActivity(activity);
		
		
		while(!finished)
		{
			try
			{
				if(noOfAttempt == activity.getFailedAttempt())
				{
					String message = activityName + " activity not able to execute after " + activity.getFailedAttempt() + " attempt";
					
					activityLog.setEndTime(new Date());
					activityLog.setStatus(Constant.FAILED_MESSAGE);
					activityLog.setNoOfAttempt(noOfAttempt);
					activityLog.setMessage(message);
					throw new ActivityFailedException(message);
				}
				
				noOfAttempt ++;
				activityExec.execute();
				
				activityLog.setEndTime(new Date());
				activityLog.setStatus(Constant.SUCCESS_MESSAGE);
				activityLog.setNoOfAttempt(noOfAttempt);
				Log.logInfo(log, activity.getName() + " activity has finished");
				
				finished = true;
			}
			catch (Exception e)
			{
				activityLog.setEndTime(new Date());
				activityLog.setStatus(Constant.FAILED_MESSAGE);
				activityLog.setNoOfAttempt(noOfAttempt);
				activityLog.setMessage(e.getMessage());
				Log.logError(log, e.getMessage());
				
				throw new ActivityFailedException(e.getMessage());
			}
			
		}
	}

	private void prapareBody()
	{
		
		mailContent.append("Task" + taskLog.getTaskName());
		mailContent.append("<br><br>");
		mailContent.append("<br><br>");
		mailContent.append("<br><br>");
		
		mailContent.append("<table border=\"1\"><thead><tr>");
		mailContent.append("<th>Activity Name</th>");
		mailContent.append("<th>Execute Count</th>");
		mailContent.append("<th>Start time</th>");
		mailContent.append("<th>End time</th>");
		mailContent.append("<th>Total Time</th>");
		mailContent.append("<th>Status</th>");
		mailContent.append("<th>Message</th>");
		mailContent.append("</tr></thead>");
		mailContent.append("<tbody>");
		
		
		for(ActivityLog activityLog : taskLog.getActivityLogs())
		{
			mailContent.append("<tr>");
			mailContent.append("<td>");
			mailContent.append(activityLog.getActivityName());
			mailContent.append("</td>");
			mailContent.append("<td>");
			mailContent.append(activityLog.getNoOfAttempt());
			mailContent.append("</td>");
			mailContent.append("<td>");
			mailContent.append(DateFormatUtils.format(activityLog.getStartTime()));
			mailContent.append("</td>");
			mailContent.append("<td>");
			mailContent.append(DateFormatUtils.format(activityLog.getEndTime()));
			mailContent.append("</td>");
			mailContent.append("<td>");
			mailContent.append(TimeHelper.getMinutesFromMillisecond(activityLog.getEndTime().getTime() - activityLog.getStartTime().getTime()));
			mailContent.append("</td>");
			mailContent.append("<td>");
			mailContent.append(activityLog.getStatus());
			mailContent.append("</td>");
			mailContent.append("<td>");
			mailContent.append(activityLog.getMessage());
			mailContent.append("</td>");
			mailContent.append("</tr>");
		}
		
		mailContent.append("</tbody>");
		mailContent.append("</table>");
		
		
	}
	
	public void sendTaskStartedMail()
	{
		String subject = "Task " + this.taskLog.getTaskName()  +" execution started";
		mailService.sendMail(subject, "", null);
	}
	
	public void sendTaskFinishedMail()
	{
		String subject = "Task " + this.taskLog.getTaskName()  +" execution finished";
		prapareBody();
		mailService.sendMail(subject, mailContent.toString(), null);
		
	}
	public void sendTaskFailedMail()
	{
		String subject = "Task " + this.taskLog.getTaskName()  +" execution Failed !!!!!!";
		prapareBody();
		mailService.sendMail(subject, mailContent.toString(), null);
	}
	
}
	

