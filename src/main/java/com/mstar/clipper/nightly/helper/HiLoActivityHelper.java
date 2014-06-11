/**
 * 
 */
package com.mstar.clipper.nightly.helper;

import java.io.IOException;
import java.util.Calendar;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import com.base.mail.MailReader;

/**
 * @author gshirsa
 * 
 */
public class HiLoActivityHelper
{
	String downloadPath = "";
	String mail = "";

	/*
	 * 1. Read mail from Nightly mail box for currrent date 2. download the
	 * attachment from email 3. FTP the attchement to specific location.
	 */
	public void downloadAttachment()
	{
		MailReader mailReader = new MailReader();
		SearchTerm term1 = new FromStringTerm("oracle@euffhsoracle4.morningstar.com");
		SearchTerm term2 = new SubjectTerm("Hi Lo Close prices");

		Calendar c = Calendar.getInstance();

		c.add(Calendar.DATE, -2);

		SearchTerm term3 = new ReceivedDateTerm(ComparisonTerm.EQ, c.getTime());
		SearchTerm andTerm = new AndTerm(new SearchTerm[] { term1, term2, term3 });

		try
		{
			Message msg[] = mailReader.getMessages("Inbox", andTerm);
			System.out.println(msg.length);
			for (Message ms : msg)
			{
				mailReader.handleMessage(ms);
			}
		}
		catch (MessagingException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public String getDownloadPath()
	{
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath)
	{
		this.downloadPath = downloadPath;
	}

	public String getMail()
	{
		return mail;
	}

	public void setMail(String mail)
	{
		this.mail = mail;
	}

	public static void main(String[] args)
	{
		HiLoActivityHelper activityImpl = new HiLoActivityHelper();
		activityImpl.setDownloadPath(args[1]);
		activityImpl.setMail(args[0]);
		
		activityImpl.downloadAttachment();
	}

}
