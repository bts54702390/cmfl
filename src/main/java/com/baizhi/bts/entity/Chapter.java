package com.baizhi.bts.entity;


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
public class Chapter implements Serializable {
  @Id
  private String id;
  private String title;
  private String url;
  private String size;
  private String time;
  @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
  @JSONField(format = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private java.util.Date createTime;
  private String albumId;



}
