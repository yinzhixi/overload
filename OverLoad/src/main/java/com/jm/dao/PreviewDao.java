package com.jm.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.jm.bean.Preview;
import com.jm.bean.PreviewImg;
public interface PreviewDao {
	
	
	//获取总条数
	int getCount();

	/**
	 * 预检实时数据首页展示
	 * @return
	 */
	
	public List<Preview> getAll(@Param("station")String station,@Param("time")Integer time,@Param("userStationCodes") String[] userStationCodes);
	
	
	

	/**
	 * 预检明细首页审核
	 * 
	 */
	List<PreviewImg> getAllImgs(@Param("carNum")String carNum,@Param("dateTime")String dateTime);
	
	/**
	 * 预检明细首页
	 */

	public List<Preview> showAllByPre(@Param("startDate")String startDate,
			@Param("endDate")String endDate, 
			@Param("station")String station,
			@Param("lane")Integer lane,
			@Param("carNum")String carNum,
			@Param("axleCnt")Integer axleCnt,
            @Param("axleCnt_op")Integer axleCnt_op,
			@Param("startWeight")Double startWeight,
			@Param("endWeight")Double endWeight,
			@Param("overRate")Integer overRate,
			@Param("recognition")Integer recognition,
			@Param("direction")Integer direction,
			@Param("previewId")Integer previewId,
            @Param("overRate_op")Integer overRate_op,
            @Param("overRate_num")Integer overRate_num,
			@Param("userStationCodes") String[] userStationCodes
			
			);

	
	  /**
	   * 车牌识别率首页展示
	   */
	public List<Preview> getAllByCarNum(@Param("startDate")String startDate, @Param("endDate")String endDate, @Param("station")String station,@Param("userStationCodes") String[] userStationCodes);
		
	/**
	 * 删除明细
	 */
	
	public int delPreview(Integer id);	
	/**
	 * 修改前查询
	 */
	public Preview getPreviewById(Integer id);
	/**
	 * 修改
	 */
	public int updatePreview(Preview preview);

	List<Preview> getFinallyData(@Param("pageSize")Integer pageSize,@Param("station") String station, @Param("overLoadRate")Integer overLoadRate);
	
    //app明细分页查询
	List<Preview> getPreviewData(Map map);

	int insertSelective(Preview record);
	
}
