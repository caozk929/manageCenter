package com.zjht.manager.common.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 网站模板工具类
 *
 * @outhor caozk
 * @create 2017-09-05 19:44
 */
public class WebSite {

    public static final String PATH_SP = "/";// /
    public static final String WEBINF_BASE = "WEB-INF";// WEB-INF
    public static final String PAGES_BASE = "pages";//
    public static final String RES_BASE = "res";// res
    public static final String UPLOAD_PATH = "upload";// upload
    public static final String TPL_SUFFIX = ".html";// .html

    /**
     * 指定/WEB-INF/pages/webapp/文件夹路径，
     * 示例：path=team/index 方法返回/WEB-INF/pages/webapp/team/index
     *
     * @param path 文件夹路径
     * @return 模板全路径
     */
    public static String getWebAppTemplateRel(String path) {
        StringBuilder sbuider = new StringBuilder("/");
        sbuider.append(WEBINF_BASE).append("/").append(PAGES_BASE).append("/").append("webapp");
        if (!StringUtils.isBlank(path)) {
            if (!path.startsWith("/")) {
                sbuider.append("/");
            }
            sbuider.append(path);
        }
        return sbuider.toString();
    }

    /**
     * 上传文件根目录
     *
     * @return
     */
    public static String getUploadBasePath() {
        StringBuilder sbuider = new StringBuilder("/");
        sbuider.append(RES_BASE).append("/").append(UPLOAD_PATH);
        return sbuider.toString();
    }

    /**
     * 上传文件根目录
     *
     * @param path 上传分文件夹
     * @return
     */
    public static String getUploadPath(String path) {
        StringBuilder sbuider = new StringBuilder("/");
        sbuider.append(RES_BASE).append("/").append(UPLOAD_PATH);
        if (!StringUtils.isBlank(path)) {
            if (!path.startsWith("/")) {
                sbuider.append("/");
            }
            sbuider.append(path);
        }
        return sbuider.toString();
    }

    /**
     * 根据相对路径全路径
     *
     * @param relPath
     * @return
     */
    public static String getUrlPath(String relPath, HttpServletRequest request) {
        StringBuilder sbuilder = new StringBuilder();
        sbuilder.append(PATH_SP).append(RES_BASE).append(PATH_SP).append(UPLOAD_PATH);
        if (relPath.startsWith("/")) {
            sbuilder.append(relPath);
        } else {
            sbuilder.append(PATH_SP).append(relPath);
        }
        return sbuilder.toString();
    }

    /**
     * 把参数转存到model里
     *
     * @return
     */
    @SuppressWarnings({"unchecked"})
    public static void setParameters(HttpServletRequest request, ModelMap model) {
        Enumeration<String> parameterNames = request.getParameterNames();
        if (parameterNames != null) {
            for (; parameterNames.hasMoreElements(); ) {
                String name = parameterNames.nextElement();
                model.addAttribute(name, request.getParameter(name));
            }
        }
    }

    public static String getDomainUrl(boolean flag, HttpServletRequest request) {
        StringBuilder sbuilder = new StringBuilder("/");
        if (flag) {
            sbuilder.append(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getServerPort())
                    .append(request.getContextPath()).append("/");
        }
        return sbuilder.toString();
    }

    /**
     * 指定/WEB-INF/pages/webapp/文件夹路径，页面使用.html后缀 参数path不能带页面名后缀
     * 示例：path=team/index 方法返回/WEB-INF/pages/webapp/team/index.html
     *
     * @param path 页面路径
     * @return 模板全路径
     */
    public static String getWebAppTemplate(String path) {
        if (StringUtils.isBlank(path)) {
            return null;
        }
        if (path.contains("redirect:")) {
            return path;
        }
        StringBuilder sbuider = new StringBuilder();
        if (path.startsWith("/")) {
            sbuider.append(path.substring(1)).append(TPL_SUFFIX);
        } else {
            sbuider.append(path).append(TPL_SUFFIX);
        }
        return sbuider.toString();
    }
}
