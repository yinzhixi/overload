package com.jm.dao;

import com.jm.bean.PreviewPre;

public interface PreviewPreDao {
	//站点数据回显
	PreviewPre quaryByPreviewId(Integer previewId);

	PreviewPre getByPreviewPreId(Integer previewId);

}
