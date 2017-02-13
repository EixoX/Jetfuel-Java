package com.eixox.helpers;

import java.util.ArrayList;
import java.util.List;

import com.eixox.data.entities.EntityStorage;

public class InsertHelper<T> {
	
	public static <T> boolean BulkInsert(List<T> items, EntityStorage<T> storage, int pageSize) {
		boolean result = false;
		
		List<T> pageToInsert = new ArrayList<T>();
		int pages = (int) Math.ceil(items.size() / (float) pageSize);

		for (int i = 0; i < pages; i++) {
			int endIndex = (i + 1) * pageSize;
			if (endIndex > items.size())
				endIndex = items.size();
			
			pageToInsert.addAll(items.subList(i * pageSize, endIndex));
			result &= storage.insert(pageToInsert) >= 0;
			
			pageToInsert.clear();
		}
		
		return result;
	}
	
	public static <T> boolean BulkInsert(List<T> items, EntityStorage<T> storage) {
		return BulkInsert(items, storage, 1000);
	}
}
