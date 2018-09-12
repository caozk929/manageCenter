package com.zjht.manager.action.admin.content;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.common.dto.LayuiResultDto;
import com.zjht.manager.common.dto.LayuiResultSuccessDto;
import com.zjht.manager.common.dto.ResultFailDto;
import com.zjht.manager.common.dto.ResultSuccessDto;
import com.zjht.manager.common.web.WebSite;
import com.zjht.manager.entity.ApiChannel;
import com.zjht.manager.entity.Article;
import com.zjht.manager.entity.Column;
import com.zjht.manager.service.ArticleService;
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
 * Created by vip on 2017/9/12.
 */
@Controller
public class ArticleAct {

    @Autowired
    private ArticleService articleService;//文章
    @Autowired
    private LogService logService;//日志
    @Autowired
    private ColumnService columnService;//栏目
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

    //进入页面方法
    @RequestMapping(value = "/content/article/v_list.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_list(ModelMap modelMap, HttpServletRequest request, Article article) {
        //1.保存请求参数
        WebSite.setParameters(request, modelMap);
        //2.保存查询参数
        modelMap.put("article", article);
        getChannelMap(modelMap,null);
        return "content/article/list";
    }

    //异步获取数据方法
    @ResponseBody
    @RequestMapping(value = "/content/article/o_list.json", method = {RequestMethod.POST})
    public LayuiResultDto o_list(Article article, Integer pageNum, Integer pageSize,ModelMap modelMap) {
        //分页查询数据
        PageInfo<Article> pageInfo = articleService.findPage(article, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        List<Article> list = pageInfo.getList();
        for (Article art : list) {
            if (StringUtils.isNotBlank(art.getColumnId())) {//栏目
                Column column = columnService.findById(art.getColumnId());
                if (column != null) {
                    art.setColumnTitle(column.getTitle());
                }
            }

        }
        getChannelMap(modelMap,list);
        return new LayuiResultSuccessDto(null, list, pageInfo.getTotal());
    }

    @RequestMapping(value = "/content/article/v_saveOrUpdate.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_saveOrUpdate(ModelMap modelMap, HttpServletRequest request, String id) {
        //1.保存请求参数
        WebSite.setParameters(request, modelMap);
        //2.判断新增还是修改
        if (StringUtils.isNotBlank(id)) {
            Article article = articleService.findById(id);
            modelMap.put("article", article);
        }
        //3.查询栏目信息
        Column column = new Column();
        column.setStatus(1);
        modelMap.put("columns", columnService.findAll(column));
        //4.查询渠道信息
        ApiChannel ac = new ApiChannel();
        ac.setStatus(1);
        modelMap.put("channels", channelService.getListIsNotDelete(ApiChannel.Status.DELETED.getStatus()));
        return "content/article/saveOrUpdate";
    }

    @ResponseBody
    @RequestMapping(value = "/content/article/saveOrUpdate.do", method = {RequestMethod.GET, RequestMethod.POST})
    public Object saveOrUpdate(Article article) {
        //1.校验参数
        if (checkParamData(article)) {
            return new ResultFailDto("参数错误...");
        }
        if (article.getPublishTime() == null) {//不填写发布时间默认当前时间
            article.setPublishTime(new Date());
        }
        if (StringUtils.isNotBlank(article.getId())) {//修改
            articleService.update(article);
        } else {//新增
            article.setCreateTime(new Date());
            articleService.add(article);
        }
        logService.add("新增/修改文章信息, 文章名称：" + article.getTitle());
        return new ResultSuccessDto("操作成功...");
    }

    @ResponseBody
    @RequestMapping(value = "/content/article/deleteById.do", method = {RequestMethod.POST})
    public Object deleteById(String id) {
        if (StringUtils.isBlank(id)) {
            return new ResultFailDto("参数错误...");
        }
        articleService.deleteById(id);
        logService.add("删除文章信息, 文章ID：" + id);
        return new ResultSuccessDto("操作成功...");
    }

    @ResponseBody
    @RequestMapping(value = "/content/article/deleteByIds.do", method = {RequestMethod.POST})
    public Object deleteByIds(@RequestParam(value = "ids[]") String[] ids) {
        if (ids == null || ids.length == 0) {
            return new ResultFailDto("参数错误...");
        }
        articleService.deleteByIds(ids);
        logService.add("批量删除文章信息, 文章IDs：" + ids);
        return new ResultSuccessDto("操作成功...");
    }

    /**
     * 校验参数
     * @param article
     * @return
     */
    private boolean checkParamData(Article article) {
        if (article == null) {
            return true;
        }
        if (StringUtils.isBlank(article.getTitle())) {
            return true;
        }
        return false;
    }

    /**
     * 生成渠道Map
     * @param modelMap
     * @param articles
     */
    private void getChannelMap(ModelMap modelMap,List<Article> articles){
        List<ApiChannel> ApiChannels = channelService.getListIsNotDelete(ApiChannel.Status.DELETED.getStatus());
        if (ApiChannels.size() > 0) {
            Map<String, String> channelMap = new HashMap<>();
            for (ApiChannel apiChannel : ApiChannels) {
                channelMap.put(apiChannel.getId(), apiChannel.getChannelName());
            }
            if(articles!=null && articles.size()>0){
                for(Article article : articles){
                    article.setChannelName(channelMap.get(article.getChannelId()));
                }
            }

            modelMap.put("channelMap", channelMap);
        }
    }

}
