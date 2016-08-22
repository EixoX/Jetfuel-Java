package com.eixox.helpers;

import java.util.List;

import com.eixox.data.entities.EntityStorage;

public class InsertHelper<T> {
	
	public static <T> boolean BulkInsert(List<T> items, EntityStorage<T> storage, int pageSize) {
		boolean result = false;
		
		int pages = (int) Math.ceil(items.size() / (float) pageSize);

		for (int i = 0; i < pages; i++) {
			for (int j = i * pageSize; j < items.size() && j < (i + 1) * pageSize; j++) {
				result &= storage.insert(items.get(j));
			}
		}
		
		return result;
	}
	
	public static <T> boolean BulkInsert(List<T> items, EntityStorage<T> storage) {
		return BulkInsert(items, storage, 1000);
	}
}
