package com.eixox.xml;

import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.eixox.Convert;
import com.eixox.Strings;

public final class XmlHelper {

	private XmlHelper() {
	}

	public static final String getCDATA(Element parent) {
		StringBuilder builder = new StringBuilder();
		for (Node node : getNodes(parent, Node.CDATA_SECTION_NODE)) {
			builder.append(node.getTextContent());
		}
		return builder.toString();
	}

	public static final Node getFirst(Element parent, short nodeType) {
		Iterator<Node> iter = getNodes(parent, nodeType).iterator();
		return iter.hasNext() ? iter.next() : null;
	}

	public static final String getString(Element parent, short nodeType) {
		Iterator<Node> iter = getNodes(parent, nodeType).iterator();
		return iter.hasNext() ? iter.next().getTextContent() : null;
	}

	public static final Iterable<Node> getNodes(final Element parent, final short nodeType) {
		return new Iterable<Node>() {

			public Iterator<Node> iterator() {
				return new Iterator<Node>() {
					final NodeList nodes = parent.getChildNodes();
					int ordinal = 0;

					public boolean hasNext() {
						int length = nodes.getLength();
						while (ordinal < length && nodes.item(ordinal).getNodeType() != nodeType)
							ordinal++;
						return ordinal < length;
					}

					public Node next() {
						return nodes.item(ordinal);
					}

					public void remove() {
						throw new RuntimeException("can't remove");

					}

				};
			}
		};
	}

	public static final String[] parseStringArray(NodeList nodes) {
		String[] arr = new String[nodes.getLength()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = nodes.item(i).getTextContent();
		return arr;
	}

	public static final int[] parseIntArray(NodeList nodes) {
		int[] arr = new int[nodes.getLength()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = Convert.toInteger(nodes.item(i).getTextContent());
		return arr;
	}

	public static final long[] parseLongArray(NodeList nodes) {
		long[] arr = new long[nodes.getLength()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = Convert.toLong(nodes.item(i).getTextContent());
		return arr;
	}

	public static final float[] parseFloatArray(NodeList nodes) {
		float[] arr = new float[nodes.getLength()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = Convert.toFloat(nodes.item(i).getTextContent());
		return arr;
	}

	public static final double[] parseDoubleArray(NodeList nodes) {
		double[] arr = new double[nodes.getLength()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = Convert.toDouble(nodes.item(i).getTextContent());
		return arr;
	}

	public static final Date[] parseDateArray(NodeList nodes, DateFormat format) {
		Date[] arr = new Date[nodes.getLength()];
		for (int i = 0; i < arr.length; i++) {
			String txt = nodes.item(i).getTextContent();
			if (!Strings.isNullOrEmpty(txt)) {
				try {
					arr[i] = format.parse(txt);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return arr;
	}

}
