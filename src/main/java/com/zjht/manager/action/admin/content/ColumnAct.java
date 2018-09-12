package com.zjht.manager.action.admin.content;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.common.dto.LayuiResultDto;
import com.zjht.manager.common.dto.LayuiResultSuccessDto;
import com.zjht.manager.common.dto.ResultFailDto;
import com.zjht.manager.common.dto.ResultSuccessDto;
import com.zjht.manager.common.web.WebSite;
import com.zjht.manager.entity.ApiChannel;
import com.zjht.manager.entity.Column;
import com.zjht.manager.service.ChannelService;
import com.zjht.manager.service.ColumnService;
import com.zjht.manager.service.LogService;
import com.zjht.manager.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 栏目Controller
 * Created by vip on 2017/9/15.
 */
@Controller
public class ColumnAct {

    @Autowired
    private ColumnService columnService;//栏目
    @Autowired
    private LogService logService;//日志
    @Autowired
    private ChannelService channelService;//渠道

    /**
     * TODO:初始化接收的日期类型不然Date接收不到String类型
     * @param binder
     */
    @InitBinder
    public void dateBinder(WebDataBinder binder) {
        //The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        //Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(value = "/content/column/v_list.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_list(ModelMap modelMap, HttpServletRequest request, Column column) {
        //1.保存请求参数
        WebSite.setParameters(request, modelMap);
        //2.保存查询条件
        modelMap.put("column", column);
        getChannelMap(modelMap,null);
        return "content/column/list";
    }

    //异步获取数据方法
    @ResponseBody
    @RequestMapping(value = "/content/column/o_list.json", method = {RequestMethod.POST})
    public LayuiResultDto o_list(Column column, Integer pageNum, Integer pageSize,ModelMap modelMap) {
        //分页查询数据
        PageInfo<Column> pageInfo = columnService.findPage(column, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        //设置栏目所属渠道名称
        getChannelMap(modelMap,pageInfo.getList());
        return new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
    }

    @RequestMapping(value = "/content/column/v_saveOrUpdate.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_saveOrUpdate(ModelMap modelMap, HttpServletRequest request, String id) {
        //1.保存请求参数
        WebSite.setParameters(request, modelMap);
        //2.判断新增还是修改
        if (StringUtils.isNotBlank(id)) {
            Column column = columnService.findById(id);
            modelMap.put("column", column);
        }
        //3.查询渠道信息
        //3.1构建查询条件对象和设置参数
        ApiChannel ac = new ApiChannel();
        ac.setStatus(1);
        modelMap.put("channels", channelService.getListIsNotDelete(ApiChannel.Status.DELETED.getStatus()));
        return "content/column/saveOrUpdate";
    }

    @ResponseBody
    @RequestMapping(value = "/content/column/saveOrUpdate.do", method = {RequestMethod.GET, RequestMethod.POST})
    public Object saveOrUpdate(Column column) {
        //1.校验参数
        if (checkParamData(column)) {
            return new ResultFailDto("参数错误...");
        }
        if (StringUtils.isNotBlank(column.getId())) {//修改
            columnService.update(column);
        } else {//新增
            column.setCreateTime(new Date());
            columnService.add(column);
        }
        logService.add("新增/修改栏目信息, 文章名称：" + column.getTitle());
        return new ResultSuccessDto("操作成功...");
    }

    @ResponseBody
    @RequestMapping(value = "/content/column/deleteById.do", method = {RequestMethod.POST})
    public Object deleteById(String id) {
        if (StringUtils.isBlank(id)) {
            return new ResultFailDto("参数错误...");
        }
        columnService.deleteById(id);
        logService.add("删除栏目信息, 文章ID：" + id);
        return new ResultSuccessDto("操作成功...");
    }

    @ResponseBody
    @RequestMapping(value = "/content/column/deleteByIds.do", method = {RequestMethod.POST})
    public Object deleteByIds(@RequestParam(value = "ids[]") String[] ids) {
        if (ids == null || ids.length == 0) {
            return new ResultFailDto("参数错误...");
        }
        columnService.deleteByIds(ids);
        logService.add("批量删除栏目信息, 文章IDs：" + ids);
        return new ResultSuccessDto("操作成功...");
    }

    /**
     * 校验参数
     * @param column
     * @return
     */
    private boolean checkParamData(Column column) {
        if (column == null) {
            return true;
        }
        if (StringUtils.isBlank(column.getCode())) {//编码
            return true;
        }
        return false;
    }

    /**
     * 生成渠道Map
     * @param modelMap
     * @param columns
     */
    private void getChannelMap(ModelMap modelMap,List<Column> columns){
        List<ApiChannel> ApiChannels = channelService.getListIsNotDelete(ApiChannel.Status.DELETED.getStatus());
        if (ApiChannels.size() > 0) {
            Map<String, String> channelMap = new HashMap<>();
            for (ApiChannel apiChannel : ApiChannels) {
                channelMap.put(apiChannel.getId(), apiChannel.getChannelName());
            }
            if(columns!=null && columns.size()>0){
                for(Column column : columns){
                    column.setChannelName(channelMap.get(column.getChannelId()));
                }
            }

            modelMap.put("channelMap", channelMap);
        }
    }
}
