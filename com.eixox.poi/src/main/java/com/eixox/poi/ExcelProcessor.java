package com.eixox.poi;

public interface ExcelProcessor<T> {

	public void init(ExcelProcessorState state);

	public void process(ExcelProcessorState state);

	public T getOutput();
}
