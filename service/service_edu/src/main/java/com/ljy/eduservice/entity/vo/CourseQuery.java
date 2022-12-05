package com.ljy.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class CourseQuery {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程名称,模糊查询")
    private String title;
    @ApiModelProperty(value = "状态")
    private String status;

}
