package com.jm.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jm.bean.Preview;
import com.jm.bean.PreviewRecord;
public interface PreviewRecordDao {
	
	int addPreviewRecord(PreviewRecord previewRecord);
	
	//List<PreviewRecord> getFinallyData(@Param("pageSize")Integer pageSize,@Param("station") String station, @Param("overLoadRate")Integer overLoadRate);
	
	List<PreviewRecord> getFinallyData(Map map);

}
