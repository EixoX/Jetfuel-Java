package com.eixox.mongodb;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.eixox.data.Filter;
import com.eixox.data.FilterExpression;
import com.eixox.data.FilterNode;
import com.eixox.data.FilterTerm;
import com.eixox.data.SortExpression;

public final class MongoDialect {

	public static final Document buildSort(SortExpression sort) {
		return null;
	}

	public static final Document buildQuery(Filter filter) {

		switch (filter.getFilterType()) {
		case EXPRESSION:
			return buildQuery(((FilterExpression) filter).first);
		case NODE:
			FilterNode node = (FilterNode) filter;
			if (node.next != null) {
				switch (node.operation) {
				case AND:
					return new Document("$and", new Object[] { buildQuery(node.filter), buildQuery(node.next) });
				case OR:
					return new Document("$or", new Object[] { buildQuery(node.filter), buildQuery(node.next) });
				default:
					throw new RuntimeException("Uknown logic operator " + node.operation);
				}
			} else
				return buildQuery(node.filter);
		case TERM:
			FilterTerm term = (FilterTerm) filter;
			switch (term.comparison) {
			case EQUAL_TO:
				return new Document(term.name, term.value);
			case GREATER_OR_EQUAL:
				return new Document(term.name, new Document("$gte", term.value));
			case GREATER_THAN:
				return new Document(term.name, new Document("$gt", term.value));
			case IN:
				return new Document(term.name, new Document("$in", term.value));
			case LIKE:
				return new Document(term.name, new Document("$regex", term.value));
			case LOWER_OR_EQUAL:
				return new Document(term.name, new Document("$lte", term.value));
			case LOWER_THAN:
				return new Document(term.name, new Document("$lt", term.value));
			case NOT_EQUAL_TO:
				return new Document(term.name, new Document("$ne", term.value));
			case NOT_IN:
				return new Document(term.name, new Document("$nin", term.value));
			default:
				throw new RuntimeException("Unkown query comparison " + term.comparison);

			}
		default:
			return null;

		}
	}

	private static final void appendMembers(Document parent, Class<?> claz, Object instance)
			throws IllegalArgumentException, IllegalAccessException {
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

	public static final Document toDbObject(Object input) {
		Document dbo = new Document();
		try {
			appendMembers(dbo, input.getClass(), input);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return dbo;
	}

	@SuppressWarnings("unchecked")
	private static final void appendToBson(Document dbo, String name, Object value) {
		if (name != null && !name.isEmpty() && value != null) {
			if (dbo.containsKey(name)) {
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

	private static final void xmlToBson(Document dbo, Node node) {
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
				Document childDbo = new Document();
				for (int i = 0; i < l; i++)
					xmlToBson(childDbo, children.item(i));
				appendToBson(dbo, node.getNodeName(), childDbo);
			}
			break;
		}
	}

	public static final Document xmlToBson(Node node) {
		Document dbo = new Document();
		xmlToBson(dbo, node);
		return dbo;
	}

}
