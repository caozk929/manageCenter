/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
    config.toolbar = [
        { name: 'document', items: ['Source'] },
        { name: 'links',items:['Link','Unlink','Anchor']},
        { name: 'insert',items:['Image','Table','HorizontalRule','SpecialChar']},
        { name: 'tools',items:['Maximize']},
        { name: 'basicstyles',items:['Bold','Italic','Strike','-','RemoveFormat']},
        { name: 'paragraph',items:['NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote']},
        { name: 'styles',items:['Styles','Format']},
        { name: 'clipboard', items: [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord'] }
    ];
    // 上传图片路径
    config.filebrowserImageUploadUrl = "/admin/file/ckeditorUploadImg.do";

};
