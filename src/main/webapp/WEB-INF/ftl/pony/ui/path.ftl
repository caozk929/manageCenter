<#--
返回图片上传的根目录
-->
<#macro path type="" src="">
<#if type=="img">
<#t/>/res/upload${src}<#t/>
<#elseif type=="css">
<#t/>/res/front/ver2013/css/${src}<#t/>
<#elseif type=="js_common">
<#t/>/res/front/ver2013/commonjs/${src}<#t/>
<#elseif type=="images">
<#t/>/res/front/ver2013/images/${src}<#t/>
<#elseif type=="js">
<#t/>/res/front/ver2013/js/${src}<#t/>
</#if>
</#macro>
