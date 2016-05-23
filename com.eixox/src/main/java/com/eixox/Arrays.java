package com.eixox;

import java.util.List;

public class Arrays {

	public static final int[] toInt(List<? extends Number> list) {
		final int[] arr = new int[list.size()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = list.get(i).intValue();
		return arr;
	}

	public static final long[] toLong(List<? extends Number> list) {
		final long[] arr = new long[list.size()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = list.get(i).longValue();
		return arr;
	}

	public static final double[] toDouble(List<? extends Number> list) {
		final double[] arr = new double[list.size()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = list.get(i).doubleValue();
		return arr;
	}

	public static final float[] toFloat(List<? extends Number> list) {
		final float[] arr = new float[list.size()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = list.get(i).floatValue();
		return arr;
	}

	public static final byte[] toByte(List<? extends Number> list) {
		final byte[] arr = new byte[list.size()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = list.get(i).byteValue();
		return arr;
	}

	public static final short[] toShort(List<? extends Number> list) {
		final short[] arr = new short[list.size()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = list.get(i).shortValue();
		return arr;
	}
}
