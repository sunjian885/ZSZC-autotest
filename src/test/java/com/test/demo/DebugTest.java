package com.test.demo;


import com.common.utils.ExcelDataUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DebugTest {
    String pathFile = "target/test-classes/data/testData.xlsx";

    @DataProvider(name = "testData")
    public Object[][] data() throws Exception{
        ExcelDataUtil testcase = new ExcelDataUtil();
        return testcase.testData(pathFile);
    }

    @Test(dataProvider = "testData")
    public void testCase(HashMap<String, String> data) {

        String name = data.get("name");
        String status = data.get("status");
        System.out.println(">>>= name:"+name+">>>>>>= status:"+status);
    }
}
