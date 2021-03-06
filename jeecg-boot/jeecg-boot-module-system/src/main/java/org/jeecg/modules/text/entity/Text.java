package org.jeecg.modules.text.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 文本编辑
 * @Author: jeecg-boot
 * @Date:   2019-12-18
 * @Version: V1.0
 */
@Data
@TableName("text")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="text对象", description="文本编辑")
public class Text {

	/**主键id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "主键id")
	private java.lang.String id;
	/**标题*/
	@Excel(name = "标题", width = 15)
    @ApiModelProperty(value = "标题")
	private java.lang.String title;
	/**html页面内容*/
	@Excel(name = "html页面内容", width = 15)
    @ApiModelProperty(value = "html页面内容")
	private java.lang.Object content;
	/**文本类型*/
	@Excel(name = "文本类型", width = 15)
    @ApiModelProperty(value = "文本类型")
	private java.lang.String type;
	/**创建时间*/
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private java.util.Date createTime;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
	private java.lang.String createBy;
}
