$(function(){

	/*----------判断是否有下级列表----------*/
	$('.catalog_tree_item').each(function(index, element) {
		if( $(element).parent().siblings().hasClass('next_catalog_modular')){
			$(element).children('.catalog_tree_icon').addClass('current')
				.siblings('.catalog_grade_icon').css('background-position','-33px -63px')
		}
	});
	/*----------下级列表收缩与展开----------*/
	$('.catalog_tree_item .catalog_tree_icon').click(function(e) {
		if( $(this).hasClass('current')){
			$(this).parent().toggleClass('hide')
				.parent().next().finish().slideToggle(300)
		}
	});
		
})