package com.mstar.clipper.nightly.activity;

import com.mstar.clipper.nightly.configuration.dto.Activity;

public interface ActivityExec
{

	public void execute() throws Exception;

	public void setActivity(Activity activity);
}
