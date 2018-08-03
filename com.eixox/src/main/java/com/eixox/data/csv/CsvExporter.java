package com.eixox.data.csv;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.eixox.Strings;
import com.eixox.reflection.AspectMember;

public class CsvExporter<T> {
	
	private String separator;
	private CsvAspect<T> aspect;
	private List<String> header;
	private List<String> rows;
	
	public CsvExporter(CsvAspect<T> aspect, String separator) {
		this.aspect = aspect;
		this.separator = separator;
	}
	
	public CsvExporter(CsvAspect<T> aspect) {
		this.aspect = aspect;
		this.separator = ",";
	}
	
	private void builHeader() {
		this.header = new ArrayList<String>();
		for (int i = 0; i < aspect.getCount(); i++) {
			AspectMember member = aspect.get(i);
			header.add(member.getName());
		}
	}
	
	public void saveTo(List<T> items, String fileName) throws Exception {
		this.builHeader();
		this.buildRows(items);
		
		Path file = Paths.get(fileName);
		if (file == null)
			throw new RuntimeException();
		
		((LinkedList<String>) this.rows).addFirst(Strings.join(",", this.header));
		Files.write(file, rows, Charset.forName("UTF-8"));
	}

	private void buildRows(List<T> items) {
		this.rows = new LinkedList<String>();
		for (T item : items) {
			StringBuilder csvRowStr = new StringBuilder(); 
			for (int i = 0; i < aspect.getCount(); i++) {
				AspectMember member = aspect.get(i);
				Object value = member.getValue(item);
				if (value != null)
					csvRowStr.append(value.toString());
				
				if (i < aspect.getCount() - 1)
					csvRowStr.append(this.separator);
			}
			this.rows.add(csvRowStr.toString());
		}
	}

}
