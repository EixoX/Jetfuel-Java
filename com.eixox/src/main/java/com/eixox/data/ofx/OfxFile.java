package com.eixox.data.ofx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class OfxFile {

	public final HashMap<String, String> headers = new HashMap<String, String>();
	public OfxElement ofx;

	public final boolean isEmpty() {
		return this.ofx == null || !"OFX".equalsIgnoreCase(this.ofx.name) || this.ofx.children.size() == 0;
	}

	public final LinkedList<OfxElement> getElementsByName(String name) {
		LinkedList<OfxElement> list = new LinkedList<OfxElement>();
		if (this.ofx != null)
			this.ofx.appendElementsByName(list, name);
		return list;
	}

	private final int parseHeaders(BufferedReader reader) throws IOException {
		String line = reader.readLine();
		int counter = 0;
		while (line != null && line.isEmpty()) {
			line = reader.readLine();
		}
		while (line != null && line.length() > 3 && line.charAt(0) != '<') {

			int ddpos = line.indexOf(':');
			if (ddpos > 0) {
				counter++;
				String key = line.substring(0, ddpos);
				String value = line.substring(ddpos + 1, line.length());
				this.headers.put(key, value);
			}

			line = reader.readLine();
		}
		return counter;
	}

	private synchronized final int parseOfx(BufferedReader reader) throws IOException {
		String line = reader.readLine();
		int counter = 0;
		String tag;
		String content;
		LinkedList<OfxElement> elements = new LinkedList<OfxElement>();
		while (line != null) {
			if (line.length() > 2 && line.charAt(0) == '<') {
				int lpos = line.indexOf('>');
				if (lpos > 0) {
					counter++;
					int spos = line.charAt(1);
					if (spos == '/') {
						// closing tag
						tag = line.substring(2, lpos);
						LinkedList<OfxElement> children = new LinkedList<OfxElement>();
						Iterator<OfxElement> descendingIterator = elements.descendingIterator();
						boolean process = true;
						while (descendingIterator.hasNext() && process) {
							OfxElement current = descendingIterator.next();
							if (current.name.equalsIgnoreCase(tag)) {
								current.children = children;
								process = false;
							} else {
								children.add(current);
								descendingIterator.remove();
							}
						}

					} else {
						// one tag
						tag = line.substring(1, lpos);
						content = (line.length() - lpos) > 0 ? line.substring(lpos + 1, line.length()) : null;
						OfxElement element = new OfxElement();
						element.name = tag;
						element.text = content;
						elements.add(element);
					}
				}
			}
			line = reader.readLine();
		}
		this.ofx = elements.getFirst();
		return counter;
	}

	public final void parse(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		try {
			parseHeaders(reader);
			parseOfx(reader);
		} finally {
			reader.close();
		}
	}
}
