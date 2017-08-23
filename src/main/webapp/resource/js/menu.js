/**
 * 菜单收起和展开
 * @param e
 */
function slideMenu(e) {
	//图标旋转
	$(e).children().children('i').toggleClass('open');
	//二级菜单收起或展开
	$(e).siblings().slideToggle(200);
}