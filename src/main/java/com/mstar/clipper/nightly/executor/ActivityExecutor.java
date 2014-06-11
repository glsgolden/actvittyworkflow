package com.mstar.clipper.nightly.executor;

import java.util.Date;

import com.base.log.Log;
import com.base.util.DateFormatUtils;
import com.base.util.TimeHelper;
import com.mstar.clipper.nightly.activity.ActivityExec;
import com.mstar.clipper.nightly.activity.ActivityFactory;
import com.mstar.clipper.nightly.activity.TaskExecutor;
import com.mstar.clipper.nightly.configuration.dto.Activity;
import com.mstar.clipper.nightly.configuration.model.ActivityLog;
import com.mstar.clipper.nightly.constant.Constant;
import com.mstar.clipper.nightly.exception.ActivityFailedException;
import com.mstar.clipper.nightly.service.MailServiceImpl;

public class ActivityExecutor implements Runnable
{

	Class<ActivityExecutor> log = ActivityExecutor.class;
	private String command;
	private Activity activity;
	private ActivityHolder activityHolder;
	MailServiceImpl mailService = new MailServiceImpl();
	private StringBuffer mailContent ;
	private ActivityLog activityLog; 

	public ActivityExecutor(String s)
	{
		this.command = s;
	}

	public ActivityExecutor(Activity activity, ActivityHolder activityHolder)
	{
		this.command = activity.getName();
		this.activity = activity;
		this.activityHolder = activityHolder;
		mailContent = new StringBuffer();
	}

	public ActivityExecutor()
	{

	}

	public String getName()
	{
		return this.command;
	}

	public void run()
	{

		while (true)
		{
			
			if("unavailabel9".equals(activity.getName()))
			{
				System.out.println("unavailabel9");
			}

			if(activity.isDependentActivityFailed())
			{
				activity.setStatus(Constant.FAILED_STATE);
				break;
			}
			
			if (activity.isReadyToExecute())
			{
				try
				{
					sendTaskStartedMail();
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				activityLog = new ActivityLog();
				activityLog.setActivityName(activity.getName());
				activityLog.setType(activity.getType());
				activityLog.setStartTime(new Date());
				activityLog.setFileLocation(activity.getBatchFileLocation());
				
				// this.activityHolder.removeFromWaitingState(activity);
				this.activityHolder.addRunningTasks(activity);
				activity.setStatus(Constant.RUNNING_STATE);
				System.out.println(Thread.currentThread().getName() + " Start. Command = " + command);
				processCommand();
				System.out.println(Thread.currentThread().getName() + " End. Command = " + command);
				break;
			}
		}

	}

	private void processCommand()
	{
		try
		{
			ActivityExec activityExec = ActivityFactory.getActivityExecutor(activity.getType());
			activityExec.setActivity(activity);
			
			String activityName = activity.getName();
			
			System.out.println(this.activity.getName());
//			this.activity.setName(this.activity.getName() + "- Completed");
//			activityExec.execute();
//			Thread.sleep(5000);
			
//			this.activityHolder.removeFromRunningState(activity);
//			this.activityHolder.addCompletedTask(activity);
			
			int noOfAttempt = 0;
			boolean finished = false;
			
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
						activity.setStatus(Constant.FAILED_STATE);
						throw new ActivityFailedException(message);
					}
					
					noOfAttempt ++;
					activityExec.execute();
					
					activityLog.setEndTime(new Date());
					activityLog.setStatus(Constant.SUCCESS_MESSAGE);
					activityLog.setNoOfAttempt(noOfAttempt);
					Log.logInfo(log, activity.getName() + " activity has finished");
					activity.setStatus(Constant.COMPLETED_STATE);
					finished = true;
					sendTaskFinishedMail();
				}
				catch (Exception e)
				{
					activityLog.setEndTime(new Date());
					activityLog.setStatus(Constant.FAILED_MESSAGE);
					activityLog.setNoOfAttempt(noOfAttempt);
					activityLog.setMessage(e.getMessage());
					Log.logError(log, e.getMessage());
					activity.setStatus(Constant.FAILED_STATE);
					
					throw new ActivityFailedException(e.getMessage());
				}
				
			}
			
			
			
			
		}
		catch (Exception e)
		{
			activity.setStatus(Constant.FAILED_STATE);
			try
			{
				sendTaskFailedMail();
			}
			catch (InterruptedException e1)
			{
				activity.setStatus(Constant.FAILED_STATE);
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	
	public void sendTaskStartedMail() throws InterruptedException
	{
		String subject = "Task " + this.activity.getName()  +" execution started";
//		String subject = "Task Ganesh execution started";
		mailService.sendMail(subject, "", null);
//		Thread.sleep(2000);
	}
	
	public void sendTaskFinishedMail() throws InterruptedException
	{
		String subject = "Task " + this.activity.getName()  +" execution finished";
		prapareBody();
		mailService.sendMail(subject, mailContent.toString(), null);
//		Thread.sleep(2000);
		
	}
	public void sendTaskFailedMail() throws InterruptedException
	{
		String subject = "Task " + this.activity.getName()  +" execution Failed !!!!!!";
		prapareBody();
		mailService.sendMail(subject, mailContent.toString(), null);
//		Thread.sleep(2000);
	}
	
	
	private void prapareBody()
	{
		
		mailContent.append("Task" + activity.getName());
		mailContent.append("<br><br>");
		mailContent.append("<br><br>");
		mailContent.append("<br><br>");
		
		mailContent.append("<table border=\"1\"><thead><tr>");
		mailContent.append("<th>Activity Name</th>");
		mailContent.append("<th>Execute Count</th>");
		mailContent.append("<th>Start time</th>");
		mailContent.append("<th>End time</th>");
		mailContent.append("<th>Total Time(S)</th>");
		mailContent.append("<th>Status</th>");
		mailContent.append("<th>Message</th>");
		mailContent.append("</tr></thead>");
		mailContent.append("<tbody>");
		
		
	
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
			mailContent.append(TimeHelper.getSecondsFromMillisecond(activityLog.getEndTime().getTime() - activityLog.getStartTime().getTime()));
			mailContent.append("</td>");
			mailContent.append("<td>");
			mailContent.append(activityLog.getStatus());
			mailContent.append("</td>");
			mailContent.append("<td>");
			mailContent.append(activityLog.getMessage());
			mailContent.append("</td>");
			mailContent.append("</tr>");
		
		
		mailContent.append("</tbody>");
		mailContent.append("</table>");
		
		
	}
	
	public static void main(String[] args)
	{
		MailServiceImpl mailService = new MailServiceImpl();
		String subject = "Task Ganesh execution started";
		mailService.sendMail(subject, "", null);
	}

	@Override
	public String toString()
	{
		return this.command;
	}
}