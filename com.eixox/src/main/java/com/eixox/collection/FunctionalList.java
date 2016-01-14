package com.eixox.collection;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<T> toList() {
		ArrayList<T> aux = new ArrayList<T>();
		for (T obj : this)
			aux.add(obj);
		return aux;
	}
}
