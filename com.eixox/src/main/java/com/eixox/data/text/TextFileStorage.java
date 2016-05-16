package com.eixox.data.text;

import java.io.FileReader;

import com.eixox.data.Delete;
import com.eixox.data.Insert;
import com.eixox.data.Select;
import com.eixox.data.Storage;
import com.eixox.data.Update;

public class TextFileStorage implements Storage {

	public final String fileName;
	public final TextSchema<?> schema;

	public TextFileStorage(String fileName, TextSchema<?> schema) {
		this.fileName = fileName;
		this.schema = schema;
	}

	public Select select() {
		try {
			return new TextSelect(new FileReader(this.fileName), schema);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public Update update() {
		throw new RuntimeException("We haven't implemented an update just yet");
	}

	public Delete delete() {
		throw new RuntimeException("We haven't implemented a delete just yet");
	}

	public Insert insert() {
		throw new RuntimeException("We haven't implemented an insert just yet");
	}

	public long bulkUpdate(Iterable<Update> updates) {
		throw new RuntimeException("We haven't implemented a bulk update just yet");
	}

	public long bulkDelete(Iterable<Delete> deletes) {
		throw new RuntimeException("We haven't implemented a bulk delete just yet");
	}

}
