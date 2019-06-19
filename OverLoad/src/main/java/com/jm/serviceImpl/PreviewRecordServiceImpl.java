package com.jm.serviceImpl;

import java.util.Date;
import java.util.List;

import org.apache.tools.ant.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jm.bean.Preview;
import com.jm.bean.PreviewImg;
import com.jm.bean.PreviewRecord;
import com.jm.dao.PreviewDao;
import com.jm.dao.PreviewRecordDao;
import com.jm.service.PreviewRecordService;
import com.jm.service.PreviewService;

@Service
public class PreviewRecordServiceImpl implements PreviewRecordService {

	  
	@Autowired
	private PreviewRecordDao previewRecordDao;
	    /** 
	     * @discription: 
	     * @author: yinzhixi       
	     * @created: 2018年12月25日 下午4:08:47      
	     * @param previewRecord
	     * @return     
	     * @see com.jm.service.PreviewRecordService#addPreviewRecord(com.jm.bean.PreviewRecord)     
	     */  
	    
	@Override
	public int addPreviewRecord(PreviewRecord previewRecord) {
		// TODO Auto-generated method stub
		return previewRecordDao.addPreviewRecord(previewRecord);
	}

}
