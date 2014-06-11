package com.mstar.clipper.nightly.service;

import java.util.List;

import com.mstar.clipper.nightly.configuration.dto.Activity;
import com.mstar.clipper.nightly.configuration.dto.Task;

public interface TaskExecutorService extends Runnable
{
	void executeTasks(List<Task> tasks) throws Exception;

	void executeTask(List<Activity> activities) throws Exception;
	
	void setActivities(List<Activity> activities);
	
}
