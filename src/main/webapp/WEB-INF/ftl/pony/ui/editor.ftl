<#--
<textarea name="textarea"></textarea>
-->
<#macro editor
	name value="" height="230"
	fullPage="false" toolbarSet="My"
	label="" noHeight="false" required="false" colspan="" width="100" help="" helpPosition="2" colon=":" hasColon="true"
	maxlength="65535"
	onclick="" ondblclick="" onmousedown="" onmouseup="" onmouseover="" onmousemove="" onmouseout="" onfocus="" onblur="" onkeypress="" onkeydown="" onkeyup="" onselect="" onchange=""
	>
<#include "control.ftl"/><#rt/>
<script type="text/javascript">
<#local editorBasePath="${base}/thirdparty/fckeditor/" filemanager="${editorBasePath}editor/filemanager/browser/default/browser.html"/>
var ${name} = new FCKeditor("${name}");
${name}.BasePath = "${editorBasePath}";

<#--
${name}.Config["CustomConfigurationsPath"]="${base}/thirdparty/fckeditor/myconfig.js?d="+new Date()*1;
-->
${name}.Config["CustomConfigurationsPath"]="${base}/thirdparty/fckeditor/myconfig.js";

${name}.Config["LinkBrowser"] = false ;
${name}.Config["ImageBrowser"] = false ;
${name}.Config["FlashBrowser"] = false ;

${name}.Config["LinkBrowserURL"] = "${filemanager}?Connector=${base}/fck_connectors/connector.svl" ;
${name}.Config["ImageBrowserURL"] = "${filemanager}?Type=Image&Connector=${base}/fck_connectors/connector.svl" ;
${name}.Config["FlashBrowserURL"] = "${filemanager}?Type=Flash&Connector=${base}/fck_connectors/connector.svl" ;

${name}.Config["LinkUpload"] = true ;
${name}.Config["ImageUpload"] = true ;
${name}.Config["FlashUpload"] = true ;

${name}.Config["LinkUploadURL"] = "${base}/fck_connectors/upload.svl" ;
${name}.Config["ImageUploadURL"] = "${base}/fck_connectors/upload.svl?Type=Image" ;
${name}.Config["FlashUploadURL"] = "$${base}/fck_connectors/upload.svl?Type=Flash" ;

${name}.Config["MaxLength"] = ${maxlength};

${name}.ToolbarSet="${toolbarSet}";
${name}.Height=${height};
${name}.Value="${value!?js_string}";
${name}.Create();
</script>
<#include "control-close.ftl"/><#rt/>
</#macro>