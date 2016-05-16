package com.eixox.data;

public interface Storage {

	public Select select();

	public Update update();

	public Delete delete();

	public Insert insert();

	public long bulkUpdate(Iterable<Update> updates);

	public long bulkDelete(Iterable<Delete> deletes);
}
