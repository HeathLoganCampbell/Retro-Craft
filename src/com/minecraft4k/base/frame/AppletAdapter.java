package com.minecraft4k.base.frame;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.applet.AudioClip;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class AppletAdapter extends Panel implements AppletStub, AppletContext 
{
	private static final long serialVersionUID = 1L;
	private Label status = null;
	private 	Map<String, InputStream> streamMap = new HashMap<>();

	
	AppletAdapter()
	{
	  add(this.status = new Label());
	  
	
	  status.setSize(this.getWidth(), status.getSize().height);
	  
	  showStatus("AppletAdapter constructed");
	}
	
	public void appletResize(int w, int h) {}
	
	public AppletContext getAppletContext()
	{
	  return this;
	}
	
	public URL getCodeBase()
	{
	  return getClass().getResource(".");
	}
	
	public URL getDocumentBase()
	{
	  return getClass().getResource(".");
	}
	
	public String getParameter(String name)
	{
	  String value = null;
	  return value;
	}
	
	public boolean isActive() {
	  return true;
	}
	
	
	
	public Applet getApplet(String an)
	{
	  return null;
	}
	
	
	
	public Enumeration<Applet> getApplets()
	{
	  return new Enumeration<Applet>()
	  {
	    public boolean hasMoreElements()
	    {
	      return false;
	    }
	    
	    public Applet nextElement()
	    {
	    	return null; 
	    }
	  };
	}
	
	
	
	public AudioClip getAudioClip(URL u)
	{
	  return null;
	}
	
	public Image getImage(URL u)
	{
	  return null;
	}
	
	
	
	public void showDocument(URL u) {}
	
	
	public void showDocument(URL u, String frame) {}
	
	
	public void showStatus(String msg)
	{
	  if (msg == null)
	    msg = "";
	  status.setText(msg);
	}
	
	public void setStream(String key, InputStream stream) throws IOException
	{
	  streamMap.put(key, stream);
	}
	
	public InputStream getStream(String key) {
	  return (InputStream) streamMap.get(key);
	}
	
	public Iterator<String> getStreamKeys() {
	  return streamMap.keySet().iterator();
	}
}