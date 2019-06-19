package org.iot.client.common.utils;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImgUtils {
    private static Logger logger = LoggerFactory.getLogger(ImgUtils.class);

    public static String getRelativePath(String clientId){
        Date date = new Date();
        return ImgUtils.getRelativePath(date, clientId);
    }

    public static String getRelativePath(Date date,String clientId){
        Calendar curr = Calendar.getInstance();
        curr.setTime(date);
        int year = curr.get(Calendar.YEAR);
        int month = curr.get(Calendar.MONTH)+1;
        int day = curr.get(Calendar.DAY_OF_MONTH);
        int hour = curr.get(Calendar.HOUR_OF_DAY);
        if(StringUtils.isNotBlank(clientId)) {
            clientId = clientId+"/";
        }else {
            clientId = "";
        }
        return "/"
                + year + "/" 
                + month + "/" 
                + day + "/" 
                + hour + "/"
                + clientId;
    }
    
    public static String getRelativePath(String time,String clientId){
        Date date = new Date();
        try {
            date = DateUtils.parseDate(time, "yyyy-MM-dd HH:mm:ss");
            return ImgUtils.getRelativePath(date, clientId);
        } catch (ParseException e) {
            logger.debug(String.format("转换时间失败：",time));
            e.printStackTrace();
        }
        return null;
    }
    
    public static BufferedImage combineImgs(ByteBuffer frontImgBuffer
            ,ByteBuffer backImgBuffer
            ,ByteBuffer screenSnapBuffer
            ,DrawStringLine string) throws IOException {
        InputStream img1In = new ByteArrayInputStream(frontImgBuffer.array());
        InputStream img2In = new ByteArrayInputStream(backImgBuffer.array());
        InputStream img3In = new ByteArrayInputStream(screenSnapBuffer.array());
        
        BufferedImage frontBufferImg = ImageIO.read(img1In);
        BufferedImage backBufferImg = ImageIO.read(img2In);
        BufferedImage screenSanpBufferImg = ImageIO.read(img3In);
        return combine(frontBufferImg,backBufferImg,screenSanpBufferImg,string);
    }
    
    private static BufferedImage combine(
            BufferedImage frontBufferImg,
            BufferedImage backBufferImg,
            BufferedImage screenSanpBufferImg ,DrawStringLine string) {
        
        int padding = 5;
        int height = backBufferImg.getHeight()+screenSanpBufferImg.getHeight()+padding;
        int width = frontBufferImg.getWidth()+backBufferImg.getWidth()+padding;
        BufferedImage combineImg = new BufferedImage(width,height,frontBufferImg.getType());
        
        Graphics2D g = combineImg.createGraphics();
//        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
//        g.setBackground(Color.white);
        
        //地址,左边距,上边距,图片宽度,图片高度,未知)
        g.drawImage(frontBufferImg, 0, 0, frontBufferImg.getWidth(), height, null);
        g.drawImage(backBufferImg, frontBufferImg.getWidth()+padding, 0, backBufferImg.getWidth(), backBufferImg.getHeight(), null);
        g.drawImage(screenSanpBufferImg, frontBufferImg.getWidth()+padding,backBufferImg.getHeight()+padding, screenSanpBufferImg.getWidth(), screenSanpBufferImg.getHeight(), null);

        //GraphicsEnvironment.getLocalGraphicsEnvironment().
        Font font=new Font("MS Shell Dlg",Font.BOLD,string.getFontSize());
        g.setFont(font);
        for (int i=0;i<string.getLines().length;i++) {
            g.drawString(string.getLines()[i], string.getFontX(), string.getFontY()+(string.getFontSize()+string.getLineSpace())*(i+1));
        }
        g.dispose();
        // 生成新的合成过的用户二维码并写入新图片
        //ImageIO.write(frontBufferImg, "jpg", outputfile);
        return combineImg;
    }
    
    public static void main(String[] args) throws IOException {
        /*RandomAccessFile frontIn = new RandomAccessFile("C:\\Users\\luke\\Pictures\\Pic\\PIC_TMP\\2018-11-13\\2018-11-13_9-34-11-89_1_1.jpg","rw");
        FileChannel frontChannel = frontIn.getChannel();
        MappedByteBuffer frontMap = frontChannel.map(MapMode.READ_WRITE, 0, frontChannel.size());
        frontIn.close();
        RandomAccessFile backIn = new RandomAccessFile("C:\\Users\\luke\\Pictures\\Pic\\PIC_TMP\\2018-11-13\\2018-11-13_9-33-11-60_1_2.jpg","rw");
        FileChannel backChannel = backIn.getChannel();
        MappedByteBuffer backMap = backChannel.map(MapMode.READ_WRITE, 0, backChannel.size());
        backIn.close();
        
        RandomAccessFile screenIn = new RandomAccessFile("C:\\Users\\luke\\Pictures\\Pic\\PIC_TMP\\2018-11-13\\2018-11-13_10-11-32-934_1_1.jpg","rw");
        FileChannel screenChannel = screenIn.getChannel();
        MappedByteBuffer screenMap = screenChannel.map(MapMode.READ_WRITE, 0, screenChannel.size());
        screenIn.close();*/
        
        BufferedImage frontBufferImg = ImageIO.read(new File("C:\\Users\\luke\\Pictures\\Pic\\PIC_TMP\\2018-11-13\\2018-11-13_9-34-11-89_1_1.jpg"));
        BufferedImage backBufferImg = ImageIO.read(new File("C:\\Users\\luke\\Pictures\\Pic\\PIC_TMP\\2018-11-13\\2018-11-13_9-33-11-60_1_2.jpg"));
        BufferedImage screenSanpBufferImg = ImageIO.read(new File("C:\\Users\\luke\\Pictures\\Pic\\PIC_TMP\\2018-11-13\\2018-11-13_10-11-32-934_1_1.jpg"));
        /*
        int height = backBufferImg.getHeight()+screenSanpBufferImg.getHeight();
        int width = frontBufferImg.getWidth()+backBufferImg.getWidth();
        
        BufferedImage combineImg = new BufferedImage(width,height,frontBufferImg.getType());
        Graphics2D g = combineImg.createGraphics();
        
        //地址,左边距,上边距,图片宽度,图片高度,未知)
        g.drawImage(frontBufferImg, 0, 0, frontBufferImg.getWidth(), frontBufferImg.getHeight()+screenSanpBufferImg.getHeight(), null);
        g.drawImage(backBufferImg, frontBufferImg.getWidth(), 0, backBufferImg.getWidth(), backBufferImg.getHeight(), null);
        g.drawImage(screenSanpBufferImg, frontBufferImg.getWidth(),backBufferImg.getHeight(), screenSanpBufferImg.getWidth(), screenSanpBufferImg.getHeight(), null);

        //GraphicsEnvironment.getLocalGraphicsEnvironment().
        int fontX = 10,fontY = 120,fontSize = 52,lineSpace=20;
        Font font=new Font("MS Shell Dlg",Font.BOLD,fontSize);
        g.setFont(font);
        g.drawString("违法地点：三门峡市陕州区G310国道885KM+500米处（民生液化气站西200米）非现场执法检测点\r\n" + 
                "", fontX, fontY+fontSize+lineSpace);
        g.drawString("违法类型：超限超载", fontX, fontY+(fontSize+lineSpace)*2 );
        g.drawString("违法代码：123", fontX, fontY+(fontSize+lineSpace)*3 );
        g.drawString("防伪标志：F", fontX, fontY+(fontSize+lineSpace)*4 );
        
        
        g.dispose();
        */
        DrawStringLine strings = new DrawStringLine(
                10,2800,52,25,new String[] {
                        "违法类型：超重",
                        "违法代码：1229"
                });
        BufferedImage combineImg = combine(frontBufferImg, backBufferImg, screenSanpBufferImg, strings);
        File outputfile = new File("C:\\Users\\luke\\Pictures\\Pic\\PIC_TMP\\combine.jpg");
        // 生成新的合成过的用户二维码并写入新图片
        ImageIO.write(combineImg, "jpg", outputfile);
        //ImgUtils.combineImgs(frontMap, backMap, screenMap);
    }
}
