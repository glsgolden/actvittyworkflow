package com.mstar.clipper.nightly.constant;

public interface Constant
{

	String BATCH_TYPE = "Batch";
	String SINGLESTAGE = "SINGLE";
	String MULTISTAGE = "MULTI";
	String FROMTOSTAGE = "FROM";
	
	String FAILED_MESSAGE = "Failed";
	String SUCCESS_MESSAGE = "Success";
	
	int WAITING_STATE = 0;
	int RUNNING_STATE = 1;
	int COMPLETED_STATE = 2;
	int FAILED_STATE = 3;
	
	
}
