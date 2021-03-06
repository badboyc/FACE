package org.jeecg.common.system.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @Author scott
 * @since 2018-12-20
 */
@Slf4j
@RestController
@RequestMapping("/sys/common")
public class CommonController {

	@Value(value = "${jeecg.path.upload}")
	private String uploadpath;

	/**
	 * @Author 政辉
	 * @return
	 */
	@GetMapping("/403")
	public Result<?> noauth()  {
		return Result.error("没有权限，请联系管理员授权");
	}
	@AutoLog(value = "上传文件")
	@PostMapping(value = "/upload")
	public Result<?> upload(HttpServletRequest request, HttpServletResponse response) {
		Result<?> result = new Result<>();
		try {
			String ctxPath = uploadpath;
			String fileName = null;
			String bizPath = "files";
			String nowday = new SimpleDateFormat("yyyyMMdd").format(new Date());
			File file = new File(ctxPath + File.separator + bizPath + File.separator + nowday);
			if (!file.exists()) {
				file.mkdirs();// 创建文件根目录
			}
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile mf = multipartRequest.getFile("file");// 获取上传文件对象
			String orgName = mf.getOriginalFilename();// 获取文件名
			fileName = orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
			String savePath = file.getPath() + File.separator + fileName;
			File savefile = new File(savePath);
			FileCopyUtils.copy(mf.getBytes(), savefile);
			String dbpath = bizPath + File.separator + nowday + File.separator + fileName;
			if (dbpath.contains("\\")) {
				dbpath = dbpath.replace("\\", "/");
			}
			result.setMessage(dbpath);
			result.setSuccess(true);
		} catch (IOException e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return result;
	}
    @PostMapping("/uploadImg")
    @ResponseBody
    public Map<String, Object> uploadImg(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> ret = new HashMap<>(2);
		try {
			String ctxPath = uploadpath;
			String fileName = null;
			String bizPath = "files";
			String nowday = new SimpleDateFormat("yyyyMMdd").format(new Date());
			File file = new File(ctxPath + File.separator + bizPath + File.separator + nowday);
			if (!file.exists()) {
				file.mkdirs();// 创建文件根目录
			}
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile mf = multipartRequest.getFile("file");// 获取上传文件对象
			String orgName = mf.getOriginalFilename();// 获取文件名
			fileName = orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
			String savePath = file.getPath() + File.separator + fileName;
			File savefile = new File(savePath);
			FileCopyUtils.copy(mf.getBytes(), savefile);
			String dbpath = bizPath + File.separator + nowday + File.separator + fileName;
			if (dbpath.contains("\\")) {
				dbpath = dbpath.replace("\\", "/");
			}
			String ip = InetAddress.getLocalHost().getHostAddress();
			int port=request.getLocalPort(); //获取服务器端口
//			String ip = InetAddress.getLocalHost().getHostAddress();
			String filePath ="http://"+ip+":3000/jeecg-boot/sys/common/download/"+ dbpath;
			ret.put("location", filePath);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return ret;
	}

	/**
	 * 预览图片
	 * 请求地址：http://localhost:8080/common/view/{user/20190119/e1fe9925bc315c60addea1b98eb1cb1349547719_1547866868179.jpg}
	 *
	 * @param request
	 * @param response
	 */
	@GetMapping(value = "/view/**")
	public void view(HttpServletRequest request, HttpServletResponse response) {
		// ISO-8859-1 ==> UTF-8 进行编码转换
		String imgPath = extractPathFromPattern(request);
		// 其余处理略
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			imgPath = imgPath.replace("..", "");
			if (imgPath.endsWith(",")) {
				imgPath = imgPath.substring(0, imgPath.length() - 1);
			}
			response.setContentType("image/jpeg;charset=utf-8");
			String localPath = uploadpath;
			String imgurl = localPath + File.separator + imgPath;
			inputStream = new BufferedInputStream(new FileInputStream(imgurl));
			outputStream = response.getOutputStream();
			byte[] buf = new byte[1024];
			int len;
			while ((len = inputStream.read(buf)) > 0) {
				outputStream.write(buf, 0, len);
			}
			response.flushBuffer();
		} catch (IOException e) {
			log.error("预览图片失败" + e.getMessage());
			// e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}

	}

	/**
	 * 下载文件
	 * 请求地址：http://localhost:8080/common/download/{user/20190119/e1fe9925bc315c60addea1b98eb1cb1349547719_1547866868179.jpg}
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@AutoLog(value = "下载文件")
	@GetMapping(value = "/download/**")
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ISO-8859-1 ==> UTF-8 进行编码转换
		String filePath = extractPathFromPattern(request);

		// 其余处理略
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			filePath = filePath.replace("..", "");
			if (filePath.endsWith(",")) {
				filePath = filePath.substring(0, filePath.length() - 1);
			}
			String localPath = uploadpath;
			String downloadFilePath = localPath + File.separator + filePath;
			File file = new File(downloadFilePath);
	         if (file.exists()) {
	        	 response.setContentType("application/force-download");// 设置强制下载不打开 force-download           
	 			response.addHeader("Content-Disposition", "attachment;fileName=" + new String(file.getName().getBytes("UTF-8"),"iso-8859-1"));
				 response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
	 			inputStream = new BufferedInputStream(new FileInputStream(file));
	 			outputStream = response.getOutputStream();
	 			byte[] buf = new byte[1024];
	 			int len;
	 			while ((len = inputStream.read(buf)) > 0) {
	 				outputStream.write(buf, 0, len);
	 			}
	 			response.flushBuffer();
	 			//下载次数加一
				 //*********************这是一条分割线**************************
				/* public void countDownload(@RequestBody doc doc){
					 System.out.println(doc);
					 docService.countDocPlus("http://127.0.0.1:8080/jeecg-boot/sys/common/download/files/20191115/测试用例_1573821041483.txt");
				 }*/
				 //*********************这是一条分割线**************************
	         }

		} catch (Exception e) {
			log.info("文件下载失败" + e.getMessage());
			// e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	/**
	 * @功能：pdf预览Iframe
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping("/pdf/pdfPreviewIframe")
	public ModelAndView pdfPreviewIframe(ModelAndView modelAndView) {
		modelAndView.setViewName("pdfPreviewIframe");
		return modelAndView;
	}

	/**
	  *  把指定URL后的字符串全部截断当成参数
	  *  这么做是为了防止URL中包含中文或者特殊字符（/等）时，匹配不了的问题
	 * @param request
	 * @return
	 */
	private static String extractPathFromPattern(final HttpServletRequest request) {
		String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
	}

}
