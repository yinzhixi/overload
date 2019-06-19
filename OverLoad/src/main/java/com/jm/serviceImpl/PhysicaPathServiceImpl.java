package com.jm.serviceImpl;

import com.jm.bean.PhysicaPath;
import com.jm.dao.PhysicaPathDao;
import com.jm.service.PhysicaPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PhysicaPathServiceImpl implements PhysicaPathService {

    @Autowired
    private PhysicaPathDao physicaPathDao;
    @Override
    public List<PhysicaPath> findList(String pathname, String pathCode) {

        return physicaPathDao.selectList(pathname,pathCode);
    }

    @Override
    public int updatePath(String id, String pathname, String pathCode, String status, String path) {

        PhysicaPath physicaPath=new PhysicaPath();
        physicaPath.setId(Integer.parseInt(id));
        physicaPath.setPathname(pathname);
        physicaPath.setPathCode(pathCode);
        physicaPath.setPath(path);
        physicaPath.setStatus(status);

        int conut=physicaPathDao.updateByPrimaryKeySelective(physicaPath);
//        String msg=new String();
//        if(conut>0){
//           msg="编辑成功";
//        }else{
//           msg="编辑失败";
//        }

        return conut;

    }
}
