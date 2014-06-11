package com.mstar.clipper.nightly.executor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler
{

	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor)
	{

		System.out.println("DemoTask Rejected : " + ((ActivityExecutor) r).getName());

//		System.out.println("Waiting for a second !!");

		try
		{

			Thread.sleep(250000);
			executor.execute(r);

		}
		catch (InterruptedException e)
		{

			e.printStackTrace();

		}

//		System.out.println("Lets add another time : "+ ((WorkerThread) r).getName());


//		System.out.println(r.toString() + " is rejected");
	}
}
