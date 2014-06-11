package com.mstar.clipper.nightly;

public class Main
{
	public static void main(String[] args)
	{
		NightlyProcessAutomate nightlyProcessAutomate = new NightlyProcessAutomate();
		if(args.length > 0)
		{
			nightlyProcessAutomate.runNightlyProcess(args);
		}
		else
		{
			nightlyProcessAutomate.runNightlyProcess();
		}
	}

}
