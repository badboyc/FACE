package org.jeecg.modules.meta.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.meta.entity.MetaData;
import org.jeecg.modules.meta.service.IMetaDataService;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 元数据
 * @Author: jeecg-boot
 * @Date:   2019-10-20
 * @Version: V1.0
 */
@Slf4j
@Api(tags="元数据")
@RestController
@RequestMapping("/meta/metaData")
public class MetaDataController {
	@Autowired
	private IMetaDataService metaDataService;
	//*******************************************************
	//测试下载
	@PostMapping(value="/downloadUrl")
	public void countPlus(@RequestBody MetaData metaData){
		System.out.println(metaData);
		metaDataService.countPlus("http://127.0.0.1:8080/jeecg-boot/sys/common/download/files/20191115/测试用例_1573821807150.txt");
	}




	//*****************************************************
	/**
	  * 分页列表查询
	 * @param metaData
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "元数据-分页列表查询")
	@ApiOperation(value="元数据-分页列表查询", notes="元数据-分页列表查询")
	@GetMapping(value = "/list")
	@PermissionData(pageComponent="jeecg/MetaDataList")
	public Result<IPage<MetaData>> queryPageList(MetaData metaData,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<MetaData>> result = new Result<IPage<MetaData>>();
		QueryWrapper<MetaData> queryWrapper = QueryGenerator.initQueryWrapper(metaData, req.getParameterMap());
		Page<MetaData> page = new Page<MetaData>(pageNo, pageSize);
		IPage<MetaData> pageList = metaDataService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	 @AutoLog(value = "元数据-分页列表查询")
	 @ApiOperation(value="元数据-分页列表查询", notes="元数据-分页列表查询")
	 @GetMapping(value = "/listclient")
	 @PermissionData(pageComponent="jeecg/MetaDataListclient")
	 public Result<IPage<MetaData>> queryPageList2(MetaData metaData,
												  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												  HttpServletRequest req) {
		 Result<IPage<MetaData>> result = new Result<IPage<MetaData>>();
		 QueryWrapper<MetaData> queryWrapper = QueryGenerator.initQueryWrapper(metaData, req.getParameterMap());
		 Page<MetaData> page = new Page<MetaData>(pageNo, pageSize);
		 IPage<MetaData> pageList = metaDataService.page(page, queryWrapper);
		 result.setSuccess(true);
		 result.setResult(pageList);
		 return result;
	 }
	/**
	  *   添加
	 * @param metaData
	 * @return
	 */
	@AutoLog(value = "元数据-添加")
	@ApiOperation(value="元数据-添加", notes="元数据-添加")
	@PostMapping(value = "/add")
	public Result<MetaData> add(@RequestBody MetaData metaData) {
		Result<MetaData> result = new Result<MetaData>();
		try {
			metaDataService.save(metaData);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param metaData
	 * @return
	 */
	@AutoLog(value = "元数据-编辑")
	@ApiOperation(value="元数据-编辑", notes="元数据-编辑")
	@PutMapping(value = "/edit")
	public Result<MetaData> edit(@RequestBody MetaData metaData) {
		Result<MetaData> result = new Result<MetaData>();
		MetaData metaDataEntity = metaDataService.getById(metaData.getId());
		if(metaDataEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = metaDataService.updateById(metaData);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "元数据-通过id删除")
	@ApiOperation(value="元数据-通过id删除", notes="元数据-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			metaDataService.removeById(id);
		} catch (Exception e) {
			log.error("删除失败",e.getMessage());
			return Result.error("删除失败!");
		}
		return Result.ok("删除成功!");
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "元数据-批量删除")
	@ApiOperation(value="元数据-批量删除", notes="元数据-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<MetaData> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<MetaData> result = new Result<MetaData>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.metaDataService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "元数据-通过id查询")
	@ApiOperation(value="元数据-通过id查询", notes="元数据-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<MetaData> queryById(@RequestParam(name="id",required=true) String id) {
		Result<MetaData> result = new Result<MetaData>();
		MetaData metaData = metaDataService.getById(id);
		if(metaData==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(metaData);
			result.setSuccess(true);
		}
		return result;
	}


	 /**
         * 导出excel
      *
      * @param request
      * @param response
      */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
      // Step.1 组装查询条件
      QueryWrapper<MetaData> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              MetaData metaData = JSON.parseObject(deString, MetaData.class);
              queryWrapper = QueryGenerator.initQueryWrapper(metaData, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<MetaData> pageList = metaDataService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "元数据列表");
      mv.addObject(NormalExcelConstants.CLASS, MetaData.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("元数据列表数据", "导出人:Jeecg", "导出信息"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
  }

  /**
      * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<MetaData> listMetaDatas = ExcelImportUtil.importExcel(file.getInputStream(), MetaData.class, params);
              metaDataService.saveBatch(listMetaDatas);
              return Result.ok("文件导入成功！数据行数:" + listMetaDatas.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
  }



}
