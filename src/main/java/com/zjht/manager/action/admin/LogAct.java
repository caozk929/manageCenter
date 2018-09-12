package com.zjht.manager.action.admin;

import com.zjht.manager.common.dto.LayuiResultDto;
import com.zjht.manager.common.dto.LayuiResultSuccessDto;
import com.zjht.manager.common.web.WebSite;
import com.zjht.manager.entity.Log;
import com.zjht.manager.service.LogService;
import com.zjht.manager.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 〈操作日志管理〉
 *
 * @author wangpeng
 * @create 2017/9/29
 * @since 1.0.0
 */
@Controller
public class LogAct {
    @Autowired
    private  LogService logService;

    /**
     * 进入列表页面
     */
    @RequestMapping(value = "/log/v_list.do",method = {RequestMethod.POST, RequestMethod.GET})
    public String list(HttpServletRequest request, Log log, ModelMap modelMap) {
        WebSite.setParameters(request, modelMap);
        if (log == null) {
            log = new Log();
        }
        modelMap.put("log", log);

        return "system/log/list";
    }

    /**
     * 列表JSON数据
     */
    @ResponseBody
    @RequestMapping(value = "/log/o_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_list( Log log, Integer pageNum, Integer pageSize) {
        if (log == null) {
            log = new Log();
        }

        List<Log> list = logService.selectList(log, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        int total=logService.selectListCount(log);

        return  new LayuiResultSuccessDto(null, list, total);
    }

}