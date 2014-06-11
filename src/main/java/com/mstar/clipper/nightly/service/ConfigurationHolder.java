package com.mstar.clipper.nightly.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.base.log.Log;
import com.base.util.SerializerHelper;
import com.mstar.clipper.nightly.configuration.dto.Configuration;

public class ConfigurationHolder
{
	
	private static Properties prop = new Properties();
	private static Class<ConfigurationHolder> log = ConfigurationHolder.class;
	private static Configuration configuration;
	private static SerializerHelper serializerHelper = new SerializerHelper();
	
	public static Configuration getConfiguration()
	{
		if (configuration == null)
		{
			
			try
			{
				System.out.println(new File(".").getCanonicalPath());
				prop.load(new FileInputStream(new File("configuration.properties")));
				parseConfiguration(prop.getProperty("nightlyLocation"));
			}
			catch (FileNotFoundException e)
			{
				Log.logError(log, "Property file not exist");
			}
			catch (IOException e)
			{
				Log.logError(log, "Check Property file not exist");
				e.printStackTrace();
			}
		}
		return configuration;
	}

	private static void parseConfiguration(String path)
	{

		serializerHelper.processAnnotation(Configuration.class);
		// serializerHelper.getFromFile(NightlyProcessAutomate.class.getClassLoader().getResource("Nightly.xml"));
		try
		{
//			String path = NightlyProcessAutomate.class.getClassLoader().getResource("Nightly.xml").getPath();
//			String path = "C:\\D\\MAVEN_WORKSPACE\\Nightly\\src\\main\\resources\\com\\mstar\\clipper\\nightly\\configuration\\nightly.xml";
//			String path = "C:\\D\\MAVEN_WORKSPACE\\Nightly\\src\\main\\resources\\com\\clipper\\nightly\\configuration\\nightly.xml";
			configuration = serializerHelper.getFromFile(path);
			
			
			
		}
		catch (FileNotFoundException e)
		{
			Log.logError(log, e.getMessage());
		}
	}

}
