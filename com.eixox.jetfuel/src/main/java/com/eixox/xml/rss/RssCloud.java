package com.eixox.xml.rss;

//From http://www.rssboard.org/rss-specification
//_________________________________________________________________
public class RssCloud {

	private String domain;
	private String port;
	private String path;
	private String registerProcedure;
	private String protocol;
	
	public final String getDomain() {
		return domain;
	}
	public final void setDomain(String domain) {
		this.domain = domain;
	}
	public final String getPort() {
		return port;
	}
	public final void setPort(String port) {
		this.port = port;
	}
	public final String getPath() {
		return path;
	}
	public final void setPath(String path) {
		this.path = path;
	}
	public final String getRegisterProcedure() {
		return registerProcedure;
	}
	public final void setRegisterProcedure(String registerProcedure) {
		this.registerProcedure = registerProcedure;
	}
	public final String getProtocol() {
		return protocol;
	}
	public final void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	
}
