package com.eixox.web.controls;

public class Heading extends ComponentList {

	private static final long serialVersionUID = 3441561597466136789L;
	public HeadingType headingType;

	public Heading(HeadingType type) {
		this.headingType = type;
	}

	public Heading(HeadingType type, String text) {
		this.headingType = type;
		super.add(new Text(text));
	}

	public static final Heading h1() {
		return new Heading(HeadingType.H1);
	}

	public static final Heading h1(String content) {
		return new Heading(HeadingType.H1, content);
	}
	
	public static final Heading h2() {
		return new Heading(HeadingType.H2);
	}

	public static final Heading h2(String content) {
		return new Heading(HeadingType.H2, content);
	}
	
	public static final Heading h3() {
		return new Heading(HeadingType.H3);
	}

	public static final Heading h3(String content) {
		return new Heading(HeadingType.H3, content);
	}
	
	public static final Heading h4() {
		return new Heading(HeadingType.H4);
	}

	public static final Heading h4(String content) {
		return new Heading(HeadingType.H4, content);
	}
	
	public static final Heading h5() {
		return new Heading(HeadingType.H5);
	}

	public static final Heading h5(String content) {
		return new Heading(HeadingType.H5, content);
	}
	
	public static final Heading h6() {
		return new Heading(HeadingType.H6);
	}

	public static final Heading h6(String content) {
		return new Heading(HeadingType.H6, content);
	}
	

}
