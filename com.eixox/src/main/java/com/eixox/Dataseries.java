package com.eixox;

import java.util.ArrayList;

public class Dataseries {

	public final ArrayList<Object> x = new ArrayList<Object>();
	public final PairList<String, ArrayList<Object>> series = new PairList<String, ArrayList<Object>>();

	public final int getXordinal(Object key) {
		int s = this.x.size();
		for (int i = 0; i < s; i++)
			if (key.equals(this.x.get(i)))
				return i;
		return -1;
	}

	public final int getSeriesOrdinal(String name) {
		int s = this.series.size();
		for (int i = 0; i < s; i++)
			if (name.equalsIgnoreCase(this.series.getKey(i)))
				return i;
		return -1;
	}

	public final ArrayList<Object> getSeriesData(int ordinal) {
		return this.series.getValue(ordinal);
	}

	public final String getSeriesName(int ordinal) {
		return this.series.getKey(ordinal);
	}

	public final ArrayList<Object> getSeriesData(String name) {
		int ordinal = getSeriesOrdinal(name);
		return ordinal < 0 ? null : this.series.getValue(ordinal);
	}

	public synchronized final void add(Object x, String seriesName, Object y) {

		int ordinal = getXordinal(x);
		if (ordinal < 0) {
			ordinal = this.x.size();
			this.x.add(x);
		}

		ArrayList<Object> seriesData = null;
		int sord = getSeriesOrdinal(seriesName);
		if (sord < 0) {
			seriesData = new ArrayList<Object>();
			this.series.add(seriesName, seriesData);
		} else {
			seriesData = this.series.getValue(sord);
		}
		while (seriesData.size() <= ordinal)
			seriesData.add(0);

		seriesData.set(ordinal, y);
	}
	
}
