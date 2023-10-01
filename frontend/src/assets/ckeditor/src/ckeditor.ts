/**
 * @license Copyright (c) 2014-2023, CKSource Holding sp. z o.o. All rights reserved.
 * For licensing, see LICENSE.md or https://ckeditor.com/legal/ckeditor-oss-license
 */

import { ClassicEditor } from '@ckeditor/ckeditor5-editor-classic';

import { Bold, Italic, Strikethrough, Underline } from '@ckeditor/ckeditor5-basic-styles';
import { Essentials } from '@ckeditor/ckeditor5-essentials';
import { FontBackgroundColor, FontColor, FontSize } from '@ckeditor/ckeditor5-font';
import { Heading, Title } from '@ckeditor/ckeditor5-heading';
import { Highlight } from '@ckeditor/ckeditor5-highlight';
import { AutoImage, Image, ImageResize, ImageUpload } from '@ckeditor/ckeditor5-image';
import { List, ListProperties } from '@ckeditor/ckeditor5-list';
import { Paragraph } from '@ckeditor/ckeditor5-paragraph';
import { SelectAll } from '@ckeditor/ckeditor5-select-all';

// You can read more about extending the build with additional plugins in the "Installing plugins" guide.
// See https://ckeditor.com/docs/ckeditor5/latest/installation/plugins/installing-plugins.html for details.

class Editor extends ClassicEditor {
	public static override builtinPlugins = [
		AutoImage,
		Bold,
		Essentials,
		FontBackgroundColor,
		FontColor,
		FontSize,
		Heading,
		Highlight,
		Image,
		ImageResize,
		ImageUpload,
		Italic,
		List,
		ListProperties,
		Paragraph,
		SelectAll,
		Strikethrough,
		Title,
		Underline
	];

	public static override defaultConfig = {
		toolbar: {
			items: [
				'bold',
				'italic',
				'underline',
				'strikethrough',
				'|',
				'fontSize',
				'fontColor',
				'fontBackgroundColor',
				'|',
				'bulletedList',
				'numberedList',
				'imageUpload',
				'|',
				'selectAll',
				'undo',
				'redo'
			]
		},
		language: 'ko'
	};
}

export default Editor;
