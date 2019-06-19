package com.jm.serviceImpl;

import com.jm.bean.Fileidx;
import com.jm.bean.Preview;
import com.jm.dao.FileidxDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jm.bean.PreviewPre;
import com.jm.dao.PreviewPreDao;
import com.jm.service.PreviewPreService;

@Service
public class PreviewPreServiceImpl implements PreviewPreService {
	@Autowired
	private PreviewPreDao dao;
	@Autowired
	private FileidxDao fileidxDao;

	@Override
	public PreviewPre quaryByPreviewId(Integer previewId) {
		PreviewPre previewPre=dao.quaryByPreviewId(previewId);

		if(previewPre.getRelCode()!=null || previewPre.getRelCode().equals("")){
			previewPre.setFrontPic(getPicture(previewPre.getRelCode(),"3"));
			previewPre.setSidePic(getPicture(previewPre.getRelCode(),"4"));
			previewPre.setUpPic(getPicture(previewPre.getRelCode(),"5"));
			previewPre.setBackPic(getPicture(previewPre.getRelCode(),"6"));
		}
		return previewPre;
	}

	/**
	 * 功能描述: 根据条件查询出图片信息
	 * @return:
	 * @Author: yinzhixi
	 * @Date: 2019/4/9 16:13
	 */
	public String getPicture(String relCode,String fileType){

		Fileidx fileidx=fileidxDao.getFileidx(relCode,fileType);
		String path=fileidx.getFilepath();
		return path;
	}

}
