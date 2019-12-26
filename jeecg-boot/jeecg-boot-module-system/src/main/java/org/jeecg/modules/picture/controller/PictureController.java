package org.jeecg.modules.picture.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.picture.entity.Picture;
import org.jeecg.modules.picture.service.IPictureService;
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
 * @Description: 图片编辑
 * @Author: jeecg-boot
 * @Date:   2019-12-26
 * @Version: V1.0
 */
@Slf4j
@Api(tags="图片编辑")
@RestController
@RequestMapping("/picture/picture")
public class PictureController {
	@Autowired
	private IPictureService pictureService;
	
	/**
	  * 分页列表查询
	 * @param picture
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "图片编辑-分页列表查询")
	@ApiOperation(value="图片编辑-分页列表查询", notes="图片编辑-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Picture>> queryPageList(Picture picture,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<Picture>> result = new Result<IPage<Picture>>();
		QueryWrapper<Picture> queryWrapper = QueryGenerator.initQueryWrapper(picture, req.getParameterMap());
		Page<Picture> page = new Page<Picture>(pageNo, pageSize);
		IPage<Picture> pageList = pictureService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param picture
	 * @return
	 */
	@AutoLog(value = "图片编辑-添加")
	@ApiOperation(value="图片编辑-添加", notes="图片编辑-添加")
	@PostMapping(value = "/add")
	public Result<Picture> add(@RequestBody Picture picture) {
		Result<Picture> result = new Result<Picture>();
		try {
			pictureService.save(picture);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param picture
	 * @return
	 */
	@AutoLog(value = "图片编辑-编辑")
	@ApiOperation(value="图片编辑-编辑", notes="图片编辑-编辑")
	@PutMapping(value = "/edit")
	public Result<Picture> edit(@RequestBody Picture picture) {
		Result<Picture> result = new Result<Picture>();
		Picture pictureEntity = pictureService.getById(picture.getId());
		if(pictureEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = pictureService.updateById(picture);
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
	@AutoLog(value = "图片编辑-通过id删除")
	@ApiOperation(value="图片编辑-通过id删除", notes="图片编辑-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			pictureService.removeById(id);
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
	@AutoLog(value = "图片编辑-批量删除")
	@ApiOperation(value="图片编辑-批量删除", notes="图片编辑-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<Picture> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Picture> result = new Result<Picture>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.pictureService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "图片编辑-通过id查询")
	@ApiOperation(value="图片编辑-通过id查询", notes="图片编辑-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Picture> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Picture> result = new Result<Picture>();
		Picture picture = pictureService.getById(id);
		if(picture==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(picture);
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
      QueryWrapper<Picture> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              Picture picture = JSON.parseObject(deString, Picture.class);
              queryWrapper = QueryGenerator.initQueryWrapper(picture, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<Picture> pageList = pictureService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "图片编辑列表");
      mv.addObject(NormalExcelConstants.CLASS, Picture.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("图片编辑列表数据", "导出人:Jeecg", "导出信息"));
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
              List<Picture> listPictures = ExcelImportUtil.importExcel(file.getInputStream(), Picture.class, params);
              pictureService.saveBatch(listPictures);
              return Result.ok("文件导入成功！数据行数:" + listPictures.size());
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
