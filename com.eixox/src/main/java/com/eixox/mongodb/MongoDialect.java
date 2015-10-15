package com.eixox.mongodb;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.eixox.data.Filter;
import com.eixox.data.FilterExpression;
import com.eixox.data.FilterNode;
import com.eixox.data.FilterTerm;
import com.eixox.data.SortExpression;
import com.mongodb.BasicDBObject;

public final class MongoDialect {

	public static final BasicDBObject buildSort(SortExpression sort) {
		return null;
	}

	public static final BasicDBObject buildQuery(Filter filter) {

		switch (filter.getFilterType()) {
			case EXPRESSION:
				return buildQuery(((FilterExpression) filter).first);
			case NODE:
				FilterNode node = (FilterNode) filter;
				if (node.next != null) {
					switch (node.operation) {
						case AND:
							return new BasicDBObject("$and",
									new Object[] { buildQuery(node.filter), buildQuery(node.next) });
						case OR:
							return new BasicDBObject("$or",
									new Object[] { buildQuery(node.filter), buildQuery(node.next) });
						default:
							throw new RuntimeException("Uknown logic operator " + node.operation);
					}
				} else
					return buildQuery(node.filter);
			case TERM:
				FilterTerm term = (FilterTerm) filter;
				switch (term.comparison) {
					case EQUAL_TO:
						return new BasicDBObject(term.name, term.value);
					case GREATER_OR_EQUAL:
						return new BasicDBObject(term.name, new BasicDBObject("$gte", term.value));
					case GREATER_THAN:
						return new BasicDBObject(term.name, new BasicDBObject("$gt", term.value));
					case IN:
						return new BasicDBObject(term.name, new BasicDBObject("$in", term.value));
					case LIKE:
						return new BasicDBObject(term.name, new BasicDBObject("$regex", term.value));
					case LOWER_OR_EQUAL:
						return new BasicDBObject(term.name, new BasicDBObject("$lte", term.value));
					case LOWER_THAN:
						return new BasicDBObject(term.name, new BasicDBObject("$lt", term.value));
					case NOT_EQUAL_TO:
						return new BasicDBObject(term.name, new BasicDBObject("$ne", term.value));
					case NOT_IN:
						return new BasicDBObject(term.name, new BasicDBObject("$nin", term.value));
					default:
						throw new RuntimeException("Unkown query comparison " + term.comparison);

				}
			default:
				return null;

		}
	}

	private static final void appendMembers(BasicDBObject parent, Class<?> claz, Object instance) throws IllegalArgumentException, IllegalAccessException {
		for (Field fld : claz.getDeclaredFields()) {
			fld.setAccessible(true);
			Object obj = fld.get(instance);
			if (obj != null)
				parent.append(fld.getName(), fld.get(instance));
		}
		Class<?> superclass = claz.getSuperclass();
		if (superclass != null && !Object.class.equals(superclass))
			appendMembers(parent, superclass, instance);
	}

	public static final BasicDBObject toDbObject(Object input) {
		BasicDBObject dbo = new BasicDBObject();
		try {
			appendMembers(dbo, input.getClass(), input);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return dbo;
	}

	@SuppressWarnings("unchecked")
	private static final void appendToBson(BasicDBObject dbo, String name, Object value) {
		if (name != null && !name.isEmpty() && value != null) {
			if (dbo.containsField(name)) {
				Object itemCtnt = dbo.get(name);
				if (List.class.isAssignableFrom(itemCtnt.getClass())) {
					((List<Object>) itemCtnt).add(value);
				} else {
					List<Object> lst = new ArrayList<Object>();
					lst.add(itemCtnt);
					lst.add(value);
					dbo.put(name, lst);
				}
			} else
				dbo.append(name, value);
		}
	}

	private static final void xmlToBson(BasicDBObject dbo, Node node) {
		switch (node.getNodeType()) {
			case Node.ATTRIBUTE_NODE:
				appendToBson(dbo, node.getNodeName(), node.getNodeValue());
				break;
			case Node.ELEMENT_NODE:
			case Node.DOCUMENT_NODE:
			case Node.DOCUMENT_FRAGMENT_NODE:
			case Node.ENTITY_NODE:
			case Node.ENTITY_REFERENCE_NODE:
				NodeList children = node.getChildNodes();
				int l = children.getLength();
				short firstNodeType = l == 1 ? children.item(0).getNodeType() : -100;
				if (firstNodeType == Node.TEXT_NODE || firstNodeType == Node.CDATA_SECTION_NODE)
					appendToBson(dbo, node.getNodeName(), node.getTextContent());
				else {
					BasicDBObject childDbo = new BasicDBObject();
					for (int i = 0; i < l; i++)
						xmlToBson(childDbo, children.item(i));
					appendToBson(dbo, node.getNodeName(), childDbo);
				}
				break;
		}
	}

	public static final BasicDBObject xmlToBson(Node node) {
		BasicDBObject dbo = new BasicDBObject();
		xmlToBson(dbo, node);
		return dbo;
	}

}
