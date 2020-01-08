package com.baizhi.bts.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Banner implements Serializable {
  @Id
  @ExcelProperty(value = {"轮播图","ID"})
  private String id;
  @ExcelProperty(value = {"轮播图","标题"})
  private String title;
  @ExcelProperty(value ={"轮播图","图片" },converter = Imgconvet.class)
  private String url;
  @ExcelProperty(value = {"轮播图","超链接"})
  private String href;
  @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
  @JSONField(format = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @ExcelProperty(value = {"轮播图","创建时间"})
  private java.util.Date createDate;
  @ExcelProperty(value = {"轮播图","简介"})
  private String descvs;
  @ExcelProperty(value = {"轮播图","状态"})
  private String status;

}
