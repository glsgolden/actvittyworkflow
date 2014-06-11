package com.mstar.clipper.nightly.activity;

import java.io.IOException;

import com.base.log.Log;
import com.base.util.BatchFileHelper;
import com.mstar.clipper.nightly.configuration.dto.Activity;
import com.mstar.clipper.nightly.constant.Constant;

public class BatchFileExecutorImpl implements ActivityExec
{
	private Class<BatchFileExecutorImpl> log = BatchFileExecutorImpl.class;
	private Activity activity;

	public void execute() throws Exception
	{
		Log.logInfo(log, "Batch File " + activity.getBatchFileLocation() + " execution has started");
		String batchFileocation = activity.getBatchFileLocation();
		String outputFileName = batchFileocation.substring(0, batchFileocation.indexOf("."));
		outputFileName =  String.format(outputFileName +"_" + activity.getName() +  "_%tY%<tm%<td_%<tH%<tM%<tS.txt", System.currentTimeMillis());
		Log.logInfo(log, "Batch File exection log maintained in " + outputFileName);
		String argument = "";
		try
		{
			
			BatchFileHelper.executeCommands(activity.getBatchFileLocation(), outputFileName, argument, activity.getCompletionTimeInSecond(), activity.isParallelExecution());
			
//			if(activity.isParallelExecution())
//			{
//				BatchFileHelper.executeBatchFile(activity.getBatchFileLocation(), outputFileName, argument);
//			}
//			else
//			{
//				BatchFileHelper.executeBatchFileWithStatus(activity.getBatchFileLocation(), outputFileName, argument);
//				
//			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			Log.logError(log, e.getMessage());
			
			throw e;
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
			Log.logError(log, e.getMessage());
			activity.setStatus(Constant.FAILED_STATE);
			throw e;
		}
	}

	public void setActivity(Activity activity)
	{
		this.activity = activity;
	}

}
