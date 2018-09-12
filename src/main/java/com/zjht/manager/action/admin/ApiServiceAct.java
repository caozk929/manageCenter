package com.zjht.manager.action.admin;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.common.dto.LayuiResultDto;
import com.zjht.manager.common.dto.LayuiResultFailDto;
import com.zjht.manager.common.dto.LayuiResultSuccessDto;
import com.zjht.manager.common.web.WebSite;
import com.zjht.manager.common.web.session.SessionProvider;
import com.zjht.manager.entity.ApiService;
import com.zjht.manager.service.ApiServiceService;
import com.zjht.manager.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 接口服务ACTION
 * @outhor caozk
 * @create 2017-09-05 19:44
 */
@Controller
public class ApiServiceAct {
    @Autowired
    private ApiServiceService apiServiceService;
    @Autowired
    private SessionProvider sessionProvider;

    /**
     * 进入列表
     * @param request
     * @param modelMap
     * @param service
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/service/v_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_list(HttpServletRequest request,ModelMap modelMap,
                         ApiService service, Integer pageNum, Integer pageSize) {
        WebSite.setParameters(request,modelMap);
        if(service == null){
            service = new ApiService();
        }
        modelMap.put("service", service);
        return "service/list";
    }

    /**
     * 新增页面
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/service/v_add.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_add(HttpServletRequest request,ModelMap modelMap) {
        WebSite.setParameters(request,modelMap);
        return "service/add";
    }

    /**
     * 新增请求
     * @param request
     * @param modelMap
     * @param service
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/service/o_add.do", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto v_add(HttpServletRequest request, ModelMap modelMap, ApiService service) {
        WebSite.setParameters(request,modelMap);
        if(service == null){
            service = new ApiService();
        }
        if(StringUtils.isBlank(service.getServiceName())){
            return new LayuiResultFailDto("服务名称不能为空");
        }
        if(StringUtils.isBlank(service.getServiceCode())){
            return new LayuiResultFailDto("服务编码不能为空");
        }
        service.setCreateTime(new Date());
        service.setUserId(sessionProvider.getUser().getId());
        this.apiServiceService.addChannel(service);
        LayuiResultDto resultDto = new LayuiResultSuccessDto();
        return resultDto;
    }

    /**
     * 列表JSON数据
     * @param request
     * @param modelMap
     * @param service
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/service/o_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_list(HttpServletRequest request, ModelMap modelMap,
                                 ApiService service, Integer pageNum, Integer pageSize) {
        if(service == null){
            service = new ApiService();
        }
        //1.根据分页参数查询数据
        service.setOrderByClause(" id desc");
        PageInfo<ApiService> pageInfo = this.apiServiceService.getPage(service, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null,pageInfo.getList(),pageInfo.getTotal());
        return resultDto;
    }
    /**
     * 修改页面
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/service/v_edit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_edit(HttpServletRequest request,ModelMap modelMap,String id) {
        WebSite.setParameters(request,modelMap);
        if(id == null){
            throw new IllegalArgumentException("id不能为空");
        }
        ApiService ApiService = apiServiceService.getById(id);
        modelMap.put("service", ApiService);
        return "service/edit";
    }

    /**
     * 修改操作
     * @param request
     * @param modelMap
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/service/o_edit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_edit(HttpServletRequest request,ModelMap modelMap,ApiService service) {
        WebSite.setParameters(request,modelMap);
        if(service == null){
            throw new IllegalArgumentException("参数不能为空");
        }
        if(service.getId() == null){
            throw new IllegalArgumentException("编辑id不能为空");
        }
        this.apiServiceService.updateSelective(service);
        LayuiResultDto resultDto = new LayuiResultSuccessDto();
        return resultDto;
    }

    /**
     * 删除渠道，不做物理删除，只修改状态
     * @param request
     * @param modelMap
     * @param ids id串
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/service/o_delete.do", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_delete(HttpServletRequest request, ModelMap modelMap, String ids) {
        WebSite.setParameters(request,modelMap);
        if(ids == null){
            return new LayuiResultFailDto("渠道id不能为空");
        }
        String[] idArr = ids.split(",");
        apiServiceService.updateStatus(idArr,ApiService.Status.DELETED.getStatus());
        LayuiResultDto resultDto = new LayuiResultSuccessDto();
        return resultDto;
    }
}
