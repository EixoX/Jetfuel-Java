package com.eixox.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.eixox.data.ofx.OfxElement;
import com.eixox.data.ofx.OfxFile;

public class OfxTests {


	@Test
	public void testLocalOfxFile() throws FileNotFoundException, IOException{
		OfxFile file = new OfxFile();
		
		file.parse(new FileInputStream("C:\\Users\\Rodrigo\\Downloads\\extrato.ofx"));
		
		List<OfxElement> trnList = file.getElementsByName("STMTTRN");
		
		Assert.assertTrue(trnList.size() > 0);
		
		for(OfxElement trn : trnList)
			System.out.println("|" + trn.getText("MEMO") + "|=|" + trn.getDouble("TRNAMT") + "|" + trn.getText("DTPOSTED") + "|" + trn.getDate("DTPOSTED"));
	}
	
}
