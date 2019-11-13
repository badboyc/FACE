package org.jeecg.modules.meta.entity;

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
 * @Description: 元数据
 * @Author: jeecg-boot
 * @Date:   2019-10-20
 * @Version: V1.0
 */
@Data
@TableName("metadata")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="metadata对象", description="元数据")
public class MetaData {
    
	/**主键ID*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "主键ID")
	private String id;
	/**产品名称*/
	@Excel(name = "产品名称", width = 15)
    @ApiModelProperty(value = "产品名称")
	private String productName;
	/**创建人ID*/
	@Excel(name = "创建人ID", width = 15)
    @ApiModelProperty(value = "创建人ID")
	private String createBy;
	/**创建时间*/
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**供应商名称*/
	@Excel(name = "供应商名称", width = 15)
    @ApiModelProperty(value = "供应商名称")
	private String supplierName;
	/**供应商地址*/
	@Excel(name = "供应商地址", width = 15)
    @ApiModelProperty(value = "供应商地址")
	private String supplierAddress;
	/**联系方式*/
	@Excel(name = "联系方式", width = 15)
    @ApiModelProperty(value = "联系方式")
	private String contact;
	/**产品版本号*/
	@Excel(name = "产品版本号", width = 15)
    @ApiModelProperty(value = "产品版本号")
	private String version;
	/**上一版本号*/
	@Excel(name = "上一版本号", width = 15)
    @ApiModelProperty(value = "上一版本号")
	private String previousVersion;
	/**关键词*/
	@Excel(name = "关键词", width = 15)
    @ApiModelProperty(value = "关键词")
	private String keyWord;
	/**产品研发及应用背景*/
	@Excel(name = "产品研发及应用背景", width = 15)
    @ApiModelProperty(value = "产品研发及应用背景")
	private String background;
	/**产品主要功能描述*/
	@Excel(name = "产品主要功能描述", width = 15)
    @ApiModelProperty(value = "产品主要功能描述")
	private String functionDescription;
	/**硬件环境基础要求*/
	@Excel(name = "硬件环境基础要求", width = 15)
    @ApiModelProperty(value = "硬件环境基础要求")
	private String hardwareRequirements;
	/**环境依赖包/工具*/
	@Excel(name = "环境依赖包/工具", width = 15)
    @ApiModelProperty(value = "环境依赖包/工具")
	private String envDependency;
	/**一致性认证结果*/
	@Excel(name = "一致性认证结果", width = 15)
    @ApiModelProperty(value = "一致性认证结果")
	private String certificateResult;
	/**相关库链接*/
	@Excel(name = "相关库链接", width = 15)
    @ApiModelProperty(value = "相关库链接")
	private String libraryLink;
	/**UoC包下载链接*/
	@Excel(name = "UoC包下载链接", width = 15)
    @ApiModelProperty(value = "UoC包下载链接")
	private String uocPackageUrl;
	/**许可类别*/
	@Excel(name = "许可类别", width = 15)
    @ApiModelProperty(value = "许可类别")
	private String licenseCategory;
	/**版本状态(0-最新版,1-旧版,2-已移除)*/
	@Excel(name = "版本状态(0-最新版,1-旧版,2-已移除)", width = 15)
    @ApiModelProperty(value = "版本状态(0-最新版,1-旧版,2-已移除)")
	private String versionStatus;
	/**审核状态(0-待审核,1-审核已通过,2-审核未通过)*/
	@Excel(name = "审核状态(0-待审核,1-审核已通过,2-审核未通过)", width = 15)
    @ApiModelProperty(value = "审核状态(0-待审核,1-审核已通过,2-审核未通过)")
	private String reviewStatus;
	/**审核结论*/
	@Excel(name = "审核结论", width = 15)
    @ApiModelProperty(value = "审核结论")
	private String reviewResult;
	/**审核人ID*/
	@Excel(name = "审核人ID", width = 15)
    @ApiModelProperty(value = "审核人ID")
	private String updateBy;
	/**审核时间*/
	@Excel(name = "审核时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "审核时间")
	private Date updateTime;
	/**下载量统计*/
	@Excel(name = "下载量统计", width = 15)
	@ApiModelProperty(value = "下载量统计")
	private String downloadCount;
}
