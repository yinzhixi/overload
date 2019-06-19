package com.jm.service;
import java.util.List;

import com.jm.bean.Preview;
import com.jm.bean.PreviewImg;

public interface PreviewService {
	
	/**
	 * 审核之照片显示
	 */
	List<PreviewImg> getAllImgs(String carNum, String dateTime);
	
	/**
	 * 车牌识别率首页展示
	 */
	
	public List<Preview> getByCarNum(String startDate,String endDate,String station,String[] userStationCodes);
	
	
	
	/**
	 * 预检实时数据首页展示
	 */
	
	public List<Preview> getAll(String station,Integer time,String[] userStationCodes);
	
	
	/**
	 * 预检明细首页展示
	 * @param overRate_num 
	 * @param overRate_op 
	 */
	public List<Preview> showAllPre(String startDate,String endDate,
			String station,Integer lane,String carNum,Integer axleCnt,Integer axleCnt_op,
			Double statrWeight,
			Double endWeight,Integer overRate,Integer recognition,
			Integer direction,Integer previewId, Integer overRate_op, Integer overRate_num,String[] userStationIds);
	
	/**
	 * 删除明细
	 * @param id
	 * @return
	 */
	int delPreview(Integer id);
	
	/**
	 * 修改前查询
	 */
	Preview getPreviewById(Integer previewId);
	
	/**
	 * 修改
	 */
	
	int updatePreview(Preview preview);

    int updatePreview(Integer previewId, Integer lane, String carNum, Integer axleCnt, Integer speed, Double sumWeight,
            Double limitWeight,String frontPic);


	int insertSelective(Preview record);
}
