package com.brt.bref.user.svc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.brt.bref.common.util.OSSClientUtil;
import com.brt.bref.common.util.ResponseUtil;

@RestController
public class FileController {
	
	@Autowired
	private OSSClientUtil ossClient;
	
	@RequestMapping("/uploadFile")
	public JSONObject testUploadFile(@RequestParam("file")MultipartFile file) {
		JSONObject resultJson = ossClient.uploadFile("test", file);
		if(resultJson.getBoolean("key")) {
			return ResponseUtil.responseResult(true, "成功", resultJson);
		}else {
			return ResponseUtil.responseResult(false, "失败", null);
		}
	}
	
	@RequestMapping("/uploadFiles")
	public JSONObject testUploadFiles(@RequestParam("file")MultipartFile[] files) {
		JSONObject resultJson = ossClient.uploadFile("test", files);
		if(resultJson.getBoolean("key")) {
			return ResponseUtil.responseResult(true, "成功", resultJson);
		}else {
			return ResponseUtil.responseResult(false, "失败", null);
		}
	}
}
