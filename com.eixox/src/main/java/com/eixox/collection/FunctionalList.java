package com.eixox.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eixox.reflection.ClassAspect;

public class FunctionalList<T> extends ArrayList<T> {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public FunctionalList(List<?> oldList) {
		for (Object o : oldList) {
			this.add((T)o);
		}
	}
	
	public FunctionalList() {
		
	}
	
	@SuppressWarnings("unchecked")
	public <E, F> FunctionalList<F> map(MapProvider<E, F> provider) {
		FunctionalList<F> list = new FunctionalList<F>();
		
		for (int i = 0; i < this.size(); i++)
			list.add((F) provider.provide((E)this.get(i)));
		
		return list;
	}
	
	public FunctionalList<T> filter(FilterProvider<T> filterProvider) {
		FunctionalList<T> list = new FunctionalList<T>();
		
		for (int i = 0; i < this.size(); i++)
			if (filterProvider.filter(this.get(i)))
				list.add(this.get(i));
		
		return list;
	}
	
	public <E, F> Map<Object, F> toMappedDictionary(String keyName, Class<T> type, MapProvider<E, F> mapProvider) {
		ClassAspect<T> aspect = ClassAspect.getInstance(type);
		Map<Object, F> map = new HashMap<Object, F>();
		
		for (int i = 0; i < this.size(); i++) {
			@SuppressWarnings("unchecked")
			F obj = (F) mapProvider.provide((E)this.get(i));
			Object key = aspect.get(keyName).getValue(get(i));
			
			map.put(key, obj);
		}
		
		return map;
	}
	
	public Map<Object, T> toDictionary(String key, Class<T> type) {
		ClassAspect<T> aspect = ClassAspect.getInstance(type);
		Map<Object, T> map = new HashMap<Object, T>();
		
		for (int i = 0; i < this.size(); i++) {
			T obj = get(i);
			Object objKey = aspect.get(key).getValue(obj);
			
			map.put(objKey, obj);
		}
		
		return map;
	}
	
	public List<T> toList() {
		ArrayList<T> aux = new ArrayList<T>();
		for (T obj : this)
			aux.add(obj);
		return aux;
	}
}
