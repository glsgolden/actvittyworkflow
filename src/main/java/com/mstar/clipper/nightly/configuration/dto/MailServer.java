package com.mstar.clipper.nightly.configuration.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("MailServer")
public class MailServer
{

	@XStreamAlias("host")
	public String host;
	@XStreamAlias("username")
	public String userName;
	@XStreamAlias("password")
	public String password;

	@XStreamAlias("fromemail")
	private String fromEmail;

	public String getHost()
	{
		return host;
	}

	public void setHost(String host)
	{
		this.host = host;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getFromEmail()
	{
		return fromEmail;
	}

	public void setFromEmail(String fromEmail)
	{
		this.fromEmail = fromEmail;
	}

}
