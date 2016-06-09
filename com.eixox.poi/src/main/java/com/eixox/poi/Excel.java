package com.eixox.poi;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Excel {

	public static final <T> T process(InputStream xls, ExcelProcessor<T> processor) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(xls);
			try {
				HSSFSheet sheet = workbook.getSheetAt(0);
				ExcelProcessorState state = new ExcelProcessorState();
				state.firstRowNumber = sheet.getFirstRowNum();
				state.lastRowNumber = sheet.getLastRowNum();
				state.rowCount = state.lastRowNumber - state.firstColNumber;

				HSSFRow headers = sheet.getRow(state.firstRowNumber);

				state.firstColNumber = headers.getFirstCellNum();
				state.lastColNumber = headers.getLastCellNum();
				state.colCount = state.lastColNumber - state.firstColNumber;
				state.cols = new ArrayList<String>(state.colCount);

				// get cell headers
				for (int i = state.firstColNumber; i < state.lastColNumber; i++) {
					HSSFCell cell = headers.getCell(i);
					if (cell != null)
						state.cols.add(cell.getStringCellValue());
					else
						state.cols.add("[UNDEFINED]");
				}
				processor.init(state);

				// get table body
				for (int i = state.currentRowNumber + 1; i <= state.lastRowNumber && !state.cancel; i++) {
					HSSFRow cells = sheet.getRow(i);

					state.currentRowNumber = i;
					state.row = new Object[state.colCount];

					int cellindex = 0;
					for (int j = state.firstColNumber; j < state.lastColNumber; j++) {
						HSSFCell cell = cells.getCell(j);
						if (cell != null)
							switch (cell.getCellType()) {
							case HSSFCell.CELL_TYPE_NUMERIC:
								state.row[cellindex++] = cell.getNumericCellValue();
								break;
							case HSSFCell.CELL_TYPE_BOOLEAN:
								state.row[cellindex++] = cell.getBooleanCellValue();
								break;
							default:
								state.row[cellindex++] = cell.getStringCellValue();
								break;
							}
						else
							state.row[cellindex++] = null;

					}
					processor.process(state);
				}
			} finally {
				workbook.close();
			}
			return processor.getOutput();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
