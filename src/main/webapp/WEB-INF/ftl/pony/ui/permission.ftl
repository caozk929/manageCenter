<#--
<input type="hidden"/>
-->
<#macro permission bizCode=''>
	<#if permissions?? && permissions?seq_contains(bizCode)>
		<#nested/>
	</#if>
</#macro>
