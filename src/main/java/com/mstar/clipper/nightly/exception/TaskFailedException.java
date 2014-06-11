package com.mstar.clipper.nightly.exception;

public class TaskFailedException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public TaskFailedException()
	{
		super();
	}
	
	public TaskFailedException(String message)
	{
		super(message);
	}

}
