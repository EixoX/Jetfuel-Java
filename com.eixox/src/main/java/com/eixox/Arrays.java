package com.eixox;

import java.util.List;

public class Arrays {

	public static final int[] toInt(List<Integer> list) {
		int[] arr = new int[list.size()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = list.get(i);
		return arr;
	}
}
