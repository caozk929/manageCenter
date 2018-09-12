package com.zjht.manager.action.apiDemo;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zjht.manager.common.dto.ApiConstants;
import com.zjht.manager.entity.ApiService;
import com.zjht.manager.service.ApiServiceService;
import com.zjht.manager.util.MessageDigestHelper;

@Controller
public class ApiAct {
	
	@Value(value = "${api.serverPath}")
	private String serverPath;
	
	@Autowired
	private ApiServiceService apiServiceService; 

	/**
	 * 进入查询页面
	 * 1.需要查询状态是可用的接口
	 * @param modelMap
	 * @return
	 */
	@GetMapping("/index.do")
	public String index(ModelMap modelMap) {
		ApiService as = new ApiService();
		as.setStatus(1);
		modelMap.put("service", apiServiceService.getList(as));
		return "index";
	}
	
	/**
	 * 进入说明文档页面
	 * @return
	 */
	@GetMapping("/doc.do")
	public String doc() {
		return "doc";
	}
	
	@ResponseBody
	@RequestMapping(value = "/service.do")
	public JSONObject service(String bizCode, String channelCode, String flowNo, String random, String request,
			String channelAccount, String channelKey) {
		JSONObject object = new JSONObject();
		String requestTime = String.valueOf(System.currentTimeMillis());
        object.put("requestTime", requestTime.trim());
        object.put("bizCode", bizCode.trim());
        object.put("channelCode", channelCode.trim());
        object.put("flowNo", flowNo.trim());
        object.put("random", random);
        if (StringUtils.isNotBlank(request)) {
            object.put("request", JSON.parse(request));
		} else {
			JSONObject json = new JSONObject();
	        object.put("request", json);
		}
		
        StringBuilder original = new StringBuilder();
        original.append("bizCode").append(ApiConstants.PARA_ASSIGN).append(bizCode).append(ApiConstants.PARA_SPT);
        original.append("channelCode").append(ApiConstants.PARA_ASSIGN).append(channelCode).append(ApiConstants.PARA_SPT);
        original.append("flowNo").append(ApiConstants.PARA_ASSIGN).append(flowNo).append(ApiConstants.PARA_SPT);
        original.append("random").append(ApiConstants.PARA_ASSIGN).append(random).append(ApiConstants.PARA_SPT);
        original.append("requestTime").append(ApiConstants.PARA_ASSIGN).append(requestTime).append(ApiConstants.PARA_SPT);
        original.append("request").append(ApiConstants.PARA_ASSIGN)
                .append(JSONObject.toJSONString(object.get("request"), SerializerFeature.MapSortField)).append(ApiConstants.PARA_SPT);
        //拼接渠道在本系统的账号和秘钥
        original.append("channelAccount").append(ApiConstants.PARA_ASSIGN).append(channelAccount).append(ApiConstants.PARA_SPT);
        original.append("channelKey").append(ApiConstants.PARA_ASSIGN).append(channelKey).append("");
        System.out.println("original===" + original.toString());
        String sign = MessageDigestHelper.encodeBySHA1(original.toString(),null);
        System.out.println("sign===" + sign);
        object.put("sign",sign);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(serverPath);
        HttpEntity entity = new StringEntity(object.toJSONString(),"UTF-8");
        httpPost.setEntity(entity);
        try {
            CloseableHttpResponse response2 = httpclient.execute(httpPost);
            JSONObject response = JSONObject.parseObject(response2.getEntity().getContent(),JSONObject.class);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}
	
	
	
}
