package com.mstar.clipper.nightly.configuration.dto;

import java.util.ArrayList;
import java.util.List;

import com.base.util.StringUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Notification
{
	@XStreamAlias("To")
	private String tos;
	
	@XStreamAlias("Cc")
	private String cc;
	
	@XStreamAlias("Bcc")
	private String bcc;

	
	public List<String> getTos()
	{
		if(this.tos == null)
			return new ArrayList<String>();
		
		return StringUtil.getListByString(tos, "\\s*,\\s*");
	}

	public void setTos(String tos)
	{
		this.tos = tos;
	}

	public List<String> getCc()
	{
		if(this.cc == null)
			return new ArrayList<String>();
		
		return StringUtil.getListByString(cc, "\\s*,\\s*");
	}

	public void setCc(String cc)
	{
		this.cc = cc;
	}

	public List<String> getBcc()
	{
		if(this.bcc == null)
			return new ArrayList<String>();
		
		
		return StringUtil.getListByString(bcc, "\\s*,\\s*");
	}

	public void setBcc(String bcc)
	{
		this.bcc = bcc;
	}
}
