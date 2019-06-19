package com.jm.controller;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jm.service.SystemSetService;
import com.jm.serviceImpl.SystemProperties;

@Controller
@RequestMapping("/static")
@Scope("prototype")
public class ImgController {
    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SystemSetService systemSetService;
    
    @RequestMapping("/viewflow")
    public void viewflow(ModelMap model, String fileName,HttpServletRequest request, HttpServletResponse response){  
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        ServletOutputStream out = null;
        RandomAccessFile fs = null;
        String picPath = systemSetService.getProperty(SystemProperties.PIC_PATH);
        
        try {
            File file = new File(picPath+fileName);
            int times = 0;
            int totalTimes =0;
            while(!file.exists() || !file.canWrite()){
                Thread.sleep(100*times);
                totalTimes = totalTimes + 100*times;
                file = new File(picPath+fileName);
                if(times == 10) {
                    break;
                }
                times++;
            }
            fs = new RandomAccessFile(file,"r");
            /*
            FileChannel channel = fs.getChannel();
            FileLock fileLock = null;
            while(true) {
                fileLock = channel.tryLock(0,Long.MAX_VALUE,false);
                if(fileLock != null) {
                    break;
                }else {
                    Thread.sleep(100);
                }
            }*/

            out = response.getOutputStream();
            byte[] buffer = new byte[1024 * 100];
            int len = 0;
            while ((len = fs.read(buffer)) != -1){
                out.write(buffer,0,len);
                out.flush();
            }
            out.flush();
        }catch (Exception e) {
            logger.debug(fileName);
            e.printStackTrace();
        } finally {  
            try {
                if(fs != null) {
                    fs.close();
                }
                if(out != null) {
                    out.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }  
        }  
    }
    
    
}
