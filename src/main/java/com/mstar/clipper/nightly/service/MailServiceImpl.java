package com.mstar.clipper.nightly.service;

import java.util.List;

import com.base.mail.MailSender;
import com.base.util.Utility;
import com.mstar.clipper.nightly.configuration.dto.MailServer;
import com.mstar.clipper.nightly.configuration.dto.Notification;

public class MailServiceImpl
{
	private MailServer mailServer;
	private Notification notification;
	private MailSender mailSender;
	
	public MailServiceImpl()
	{
		this.mailServer = ConfigurationHolder.getConfiguration().getMailServer();
		this.notification = ConfigurationHolder.getConfiguration().getNotification();
		this.mailSender = new MailSender(this.mailServer.getHost(), this.mailServer.getUserName(), this.mailServer.getPassword(),this.mailServer.getFromEmail());
	}
	
	public void sendMail(String to, String cc, String bcc, String subject, String content, List<String> files)
	{
		
	}
	
	public void sendMail(String subject, String content, List<String> files)
	{
		this.mailSender.setToEmailAddresses(notification.getTos()); 
		this.mailSender.setCcEmailAddresses(notification.getCc()); 
		this.mailSender.setbCCEmailAddresses(notification.getBcc()); 
		this.mailSender.setSubject(subject);
		this.mailSender.setMessageContent(content);
		if(Utility.isNotBlankList(files))
		{
			this.mailSender.addAttachedFiles(files);
		}
		this.mailSender.sendEmail();
	}
	
	
	

}
