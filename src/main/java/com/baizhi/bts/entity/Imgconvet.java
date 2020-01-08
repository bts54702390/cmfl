package com.baizhi.bts.entity;

import com.alibaba.excel.converters.string.StringImageConverter;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Imgconvet extends StringImageConverter {
    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws IOException {
        //将相对路径|网络路径 改为绝对路径
        // System.getProperty("user.dir");获取前部分绝对路径
        String property = System.getProperty("user.dir");
        value = value.split("/")[value.split("/").length - 1];
        String url=property+"\\src\\main\\webapp\\uploda\\img\\"+value;
        return  new CellData(FileUtils.readFileToByteArray(new File(url)));
    }
}
