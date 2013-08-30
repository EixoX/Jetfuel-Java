package com.eixox;

import java.util.List;

public class ArrayHelper {

	// _____________________________________________________________________________________________________________
	public static int[] toIntArray(List<Integer> list) {
		int[] arr = new int[list.size()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = list.get(i);
		return arr;
	}

	// _____________________________________________________________________________________________________________
	public static String[] toStringArray(List<String> list) {
		String[] arr = new String[list.size()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = list.get(i);
		return arr;
	}


}
