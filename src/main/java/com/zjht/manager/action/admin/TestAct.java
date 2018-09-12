package com.zjht.manager.action.admin;

import com.zjht.manager.common.dto.ResultDto;
import com.zjht.manager.common.dto.ResultSuccessDto;
import com.zjht.manager.entity.ErrorCode;
import com.zjht.manager.entity.Log;
import com.zjht.manager.service.ErrorCodeService;
import com.zjht.manager.service.LogService;
import com.zjht.manager.util.ExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 测试类
 * @outhor caozk
 * @create 2017-08-30 9:53
 */
@Controller
public class TestAct {
    @Autowired
    LogService logService;
    @Autowired
    ErrorCodeService errorCodeService;
    private static Logger logger = LoggerFactory.getLogger(TestAct.class);
    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @RequestMapping(value = "/test/logList.do")
    @ResponseBody
    public ResultDto<Object> logList(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        //退出登录

        Log log = new Log();

        logger.info("查询日志列表");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            log.setCreateTimeBegin("2017-08-28");
            log.setCreateTimeBegin("2017-08-29");

        List<Log> list = logService.selectByExample(log);

        ResultDto<Object> dto = new ResultSuccessDto();
        dto.setData(list);

        return dto;
    }
    @RequestMapping(value = "/test/index.html")
    @ResponseBody
    public ResultDto index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        ErrorCode record = new ErrorCode();
        record.setBusinessCode("b010101");
        record.setCreateTime(new Date());
        errorCodeService.insert(record);

        List logList = errorCodeService.selectPage(null, 1, 2);

        return null;
    }

    @RequestMapping(value="/test/kafka.html")
    public String kafka(HttpServletRequest request, HttpServletResponse response,ModelMap model){
        kafkaTemplate.sendDefault("nihao");
        return "";
    }

    @RequestMapping(value="/test/exportExcel.do")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response,ModelMap model){
        Log log = new Log();

        logger.info("查询日志列表");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            log.setCreateTimeBegin("2017-08-28");
            log.setCreateTimeBegin("2017-08-29");

        List<Log> list = logService.selectByExample(log);

        String[] head = {"haha","dfg","地方"};
        List<List<String>> list1 = new ArrayList<>();
        for (int i=0; i< list.size(); i++){
            List<String> e = new ArrayList<String>();
            e.add(list.get(i).getId());
            e.add(list.get(i).getContent());
            e.add(list.get(i).getCreateTime().toString());
            list1.add(e);
        }
        try {
            ExcelUtils.exportExcel("日志列表.xls",request,response,head,list1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
