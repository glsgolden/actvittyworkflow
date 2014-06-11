package com.mstar.clipper.nightly;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.mstar.clipper.nightly.configuration.dto.Configuration;
import com.mstar.clipper.nightly.configuration.dto.Task;
import com.mstar.clipper.nightly.configuration.dto.TaskHolder;
import com.mstar.clipper.nightly.constant.Constant;
import com.mstar.clipper.nightly.service.ConfigurationHolder;
import com.mstar.clipper.nightly.service.TaskExecutorService;
import com.mstar.clipper.nightly.service.TaskExecutorServiceImpl;

public class NightlyProcessAutomate
{
	private Object log = NightlyProcessAutomate.class;
	private Configuration configuration;
	private TaskExecutorService taskExecutorService = new TaskExecutorServiceImpl();


	public Configuration getConfiguration()
	{
		if (configuration == null)
		{
			configuration = ConfigurationHolder.getConfiguration();
		}
		return configuration;
	}

	public void runNightlyProcess()
	{
		getConfiguration();
		TaskHolder tasks = configuration.getTasks();
		try
		{
//			taskExecutorService.executeTasks(tasks.getTasks());
			taskExecutorService.setActivities(configuration.getActivities());
			Thread t = new Thread(taskExecutorService);
			t.start();
			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void runNightlyProcess(String[] parameters)
	{
		getConfiguration();
		TaskHolder taskHolder = configuration.getTasks();
		try
		{
			List<Task> tasks = getExecutableTasks(parameters, taskHolder);
//			taskExecutorService.executeTasks(tasks);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public List<Task> getExecutableTasks(String[] parameter, TaskHolder taskHolder)
	{

		List<Task> tasks = new ArrayList<Task>();
		if (Constant.SINGLESTAGE.equalsIgnoreCase(parameter[0]))
		{
			tasks.add(taskHolder.getTask(parameter[1]));
		}
		if (Constant.MULTISTAGE.equalsIgnoreCase(parameter[0]))
		{
			String stageName = parameter[1];

			StringTokenizer tokenizer = new StringTokenizer(stageName, "|");

			List<String> stageNames = new ArrayList<String>();
			while (tokenizer.hasMoreTokens())
			{
				stageName = tokenizer.nextToken();
				stageNames.add(stageName);
			}

			tasks.addAll(taskHolder.getTask(stageNames));

		}
		if (Constant.FROMTOSTAGE.equalsIgnoreCase(parameter[0]))
		{
			List<Task> task = taskHolder.getTaskFromToTO(parameter[1], parameter[2]);
			tasks.addAll(task);
		}
		return tasks;
	}

}
