package com.baizhi.bts.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImgData {
    /*
     * 绝对路径 继承StringImageConverter 重写 写入方法
     */
    @ExcelProperty(value = "图片",converter = Imgconvet.class)
    private String string;
}
