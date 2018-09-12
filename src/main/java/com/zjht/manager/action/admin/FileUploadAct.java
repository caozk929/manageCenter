package com.zjht.manager.action.admin;

import com.alibaba.fastjson.JSONObject;
import com.zjht.manager.common.dto.ResultDto;
import com.zjht.manager.common.dto.ResultFailDto;
import com.zjht.manager.common.dto.ResultSuccessDto;
import com.zjht.manager.common.web.Constants;
import com.zjht.manager.common.web.annotation.NoNeedAuth;
import com.zjht.manager.util.*;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


@Controller
public class FileUploadAct {

    private static final Logger log = LoggerFactory.getLogger(FileUploadAct.class);
    
    @Value(value = "${file.savePath}")
	private String savePath;
    @Value(value = "${file.compress_threshold}")
    private int compress_threshold;
    @Value(value = "${file.compress_ratio}")
    private String compress_ratio;
    @Value(value = "${file.compress_quality}")
    private String compress_quality;
    @Value(value = "${file.serverPath}")
    private String serverPath;

    @NoNeedAuth
    @RequestMapping(value = "/file/uploadFile.do")
    public void fileUpload(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        //返回的JSON对象
        JSONObject respObj = new JSONObject();

        //获取系统名
        String bizCode = request.getParameter("bizCode");
        log.info("收到系统" + bizCode + "文件上传请求");

        //bizCode参数处理
        if (checkBizCode(response, respObj, bizCode)) return;

        List<JSONObject> fileList = new ArrayList<JSONObject>();
        //上传方法
        this.uploadFile(request, bizCode, fileList);

        respObj.put("files", fileList);
        log.info("返回文件上传信息：" + respObj.toJSONString());
        ResponseUtils.renderJson(response, respObj.toJSONString());
    }

    /**
     * 删除上传文件
     *
     * @param request
     * @param fileUrl  文件路径
     * @return
     */
    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/file/deleteFile.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultDto o_delImage(HttpServletRequest request, String fileUrl) {
        try {
            request.setCharacterEncoding("utf-8");
            if (FileUtils.delFile(getSystemUploadDir(fileUrl))) {
                return new ResultSuccessDto();
            } else {
                return new ResultFailDto("删除失败");
            }
        } catch (Exception e) {
            return new ResultFailDto("系统异常");
        }

    }

    /**
     * ckeditor文件上传功能，回调，传回图片路径，实现预览效果。
     *
     * @param request
     * @param response
     */
    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/file/ckeditorUploadImg.do")
    public void ckeditorUploadFile(HttpServletRequest request, HttpServletResponse response) {
        //返回的JSON对象
        JSONObject respObj = new JSONObject();

        //获取系统名
        String bizCode = request.getParameter("bizCode");
        log.info("收到系统" + bizCode + "文件上传请求");
        try {
            //bizCode参数处理
            if (checkBizCode(response, respObj, bizCode)) return;

            List<JSONObject> fileList = new ArrayList<JSONObject>();
            //上传方法
            this.uploadFile(request, bizCode, fileList);

            // imageContextPath为图片在服务器地址
            String imageContextPath = serverPath+fileList.get(0).getString("filePath");
            String callback = request.getParameter("CKEditorFuncNum");

            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + imageContextPath + "',''" + ")");
            out.println("</script>");
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new IllegalFormatFlagsException("上传文件异常");
        }
    }

    /////////私有方法/////////

    /**
     * 上传文件
     *
     * @param request
     * @param bizCode
     * @param fileList
     */
    private void uploadFile(HttpServletRequest request, String bizCode, List<JSONObject> fileList) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMaps = multipartRequest.getFileMap();
        Iterator fileItor = fileMaps.entrySet().iterator();

        while (fileItor.hasNext()) {
            Map.Entry entity = (Map.Entry) fileItor.next();
            String key = (String) entity.getKey();
            MultipartFile file = (MultipartFile) entity.getValue();
            log.info("收到系统" + bizCode + "文件上传文件：" + file.getOriginalFilename());
            if (file.isEmpty()) {
                continue;
            }
            //文件扩展名
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            String filePath = FileNameUtils.genPathName();
            //返回给请求系统的文件路径
            String retFilePath = filePath + Constants.SPT;
            filePath = getSystemUploadDir(savePath + bizCode + Constants.SPT + filePath + Constants.SPT);
            System.out.println(filePath);
            JSONObject fileObj = new JSONObject();
            fileObj.put("fileName", key);
            try {
                //目录不存在时创建
                File newFile = new File(filePath);
                if (!newFile.exists()) {
                    newFile.mkdirs();
                }
                String fileName = FileNameUtils.genFileNameWithOutPath(ext);
                filePath = filePath + fileName;
                retFilePath = retFilePath + fileName;
                newFile = new File(filePath);
                if (!newFile.exists()) {
                    newFile.createNewFile();
                }
                //如果文件需要压缩，则压缩保存
                boolean dealImg = (file.getBytes().length / 1024) > compress_threshold;//如果用户上传图片大于阈值，采用等比例压缩图片
                if (dealImg) {
                    float ratio = Float.valueOf(compress_ratio);
                    float quality = Float.valueOf(compress_quality);
                    ImageUtils.compress(file.getInputStream(), filePath, ratio, ext, quality);
                } else {
                    file.transferTo(newFile);
                }
                fileObj.put("filePath", savePath + bizCode + Constants.SPT + retFilePath);
                fileObj.put(ResponseState.STATENAME, ResponseState.SUCCESS_SINGLE);
            } catch (IOException e) {
                fileObj.put(ResponseState.STATENAME, ResponseState.FAIL_SINGLE);
                e.printStackTrace();
            }
            fileList.add(fileObj);
        }
    }

    /**
     * 处理bizCode参数
     *
     * @param response
     * @param respObj
     * @param bizCode
     * @return
     */
    private boolean checkBizCode(HttpServletResponse response, JSONObject respObj, String bizCode) {
        if (StringUtils.isBlank(bizCode)) {
            respObj.put(ResponseState.RESPCODE, ResponseState.FAIL);//返回成功
            respObj.put(ResponseState.RESPMSG, "系统名不能为空");
            ResponseUtils.renderJson(response, respObj.toJSONString());
            return true;
        }
        respObj.put(ResponseState.RESPCODE, ResponseState.SUCCESS);//返回成功
        respObj.put(ResponseState.RESPMSG, "请求接收成功");
        return false;
    }

    /**
     * 设置文件上传目录
     *
     * @param dir
     * @return
     */
    private String getSystemUploadDir(String dir) {
        return getClass().getResource("/").getPath().replace("/WEB-INF/classes/", dir);
    }

}
