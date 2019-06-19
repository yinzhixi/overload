package com.jm.websocket;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONObject;
import com.jm.bean.StaticWeight;
import com.jm.service.StaticWeightService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket")
public class WebSocket implements OnDataCallback{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
	//静态变量，用来记录当前在线连接数
	private static volatile int onlineCount = 0;

	//concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
	private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();

	//与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	
	private HashMap<String, Object> setting = new HashMap<>();

    private Gson gson = new Gson();
    
	/**
	 * 连接建立成功调用的方法
	 * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(Session session){
	    session.setMaxIdleTimeout(10*60*1000);
		this.session = session;
		webSocketSet.add(this);     //加入set中
		addOnlineCount();           //在线数加1
        ClientService.addDataListener(this);
		logger.debug(String.format("有新连接加入 session:%s！当前在线人数为 %d",session.getId(), getOnlineCount()));
		
		Map<String, List<String>> paramMap = session.getRequestParameterMap();
		List<String> list = paramMap.get("direction");
		if(list != null && list.size() > 0) {
		    this.setting.put("direction", list.get(0));
		}
		list = paramMap.get("station");
		if(list != null && list.size() > 0) {
            this.setting.put("station", list.get(0));
        }
		
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(){
		webSocketSet.remove(this);  //从set中删除
		subOnlineCount();           //在线数减1
		logger.debug("有一连接关闭！当前在线人数为" + getOnlineCount());
		ClientService.removeDataListener(this);
	}

	/**
	 * 收到客户端消息后调用的方法
	 * @param message 客户端发送过来的消息
	 * @param session 可选的参数
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		/*Msg msg = gson.fromJson(message, Msg.class);
		if("setting".equals(msg.getHeader())) {
		    String content = msg.getContent();
		    HashMap<String,Object> newSettings = gson.fromJson(content, HashMap.class);
		    this.setting.putAll(newSettings);
		}*/
	}

	/**
	 * 发生错误时调用
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error){
		System.out.println("发生错误");
		error.printStackTrace();
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException{
		this.session.getBasicRemote().sendText(message);
		//this.session.getAsyncRemote().sendText(message);
	}
	
    private void sendImg(String filePath) {
        try {
            FileInputStream fs = new FileInputStream(filePath);
            // 获取指定文件的长度并用它来创建一个可以存放内容的字节数组
            byte[] content = new byte[fs.available()];
            // 将图片内容读入到字节数组
            fs.read(content);
            // 使用刚才的字节数组创建ByteBuffer对象
            ByteBuffer byteBuffer = ByteBuffer.wrap(content);
            Basic basic = session.getBasicRemote();
            // 发送byteBuffer对象到客户端
            basic.sendBinary(byteBuffer);
            // 关闭文件流对象
            fs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		WebSocket.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		WebSocket.onlineCount--;
	}
	
    //private final Queue<Pack> readQueue = new LinkedList<Pack>();
    
    @Override
    public void onVehicleData(String json) {/*
        try {
            Vehicle v = gson.fromJson(json, Vehicle.class);
            boolean send = true;
            if(this.setting.containsKey("direction")) {
                String direction = Objects.toString(this.setting.get("direction"),"");
                if(StringUtils.isNotBlank(direction) && v.getDirection() != Integer.parseInt(direction)) {
                    send = false;
                }
            }
            
            if(this.setting.containsKey("station")) {
                String station = Objects.toString(this.setting.get("station"),"");
                if(StringUtils.isNotBlank(station) && v.getStationId() != Integer.parseInt(station)) {
                    send = false;
                }
            }
            
            if(send) {
                this.sendMessage(this.formatMessage(json));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    */}
    
    private String formatMessage(String origMessage) throws IOException {
        Map<String,String> body = new HashMap<String,String>();
        body.put("type", "vehicle");
        body.put("data", origMessage);
        return gson.toJson(body);
    }
    
	@Override
	public void onImgData(String imgFileName) {/*
		try {
        	Map<String,String> body = new HashMap<String,String>();
        	body.put("type", "image");
        	body.put("data", imgFileName);
            this.sendMessage(gson.toJson(body));
            //this.sendImg("E:/JM/Pic/"+imgFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
	*/}

    @Override
    public void onSnapData(String json) {
        try {
            Map<String,String> body = new HashMap<String,String>();
            body.put("type", "snap");
            body.put("data", json);
            
            this.sendMessage(gson.toJson(body));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFrontImgComplete(String json, String jsonImg) {
        try {
            Vehicle v = gson.fromJson(json, Vehicle.class);
            boolean send = true;
            if(this.setting.containsKey("direction")) {
                String direction = Objects.toString(this.setting.get("direction"),"");
                if(StringUtils.isNotBlank(direction) && v.getDirection() != Integer.parseInt(direction)) {
                    send = false;
                }
            }
            /*
            if(this.setting.containsKey("station")) {
                String station = Objects.toString(this.setting.get("station"),"");
                if(StringUtils.isNotBlank(station) && v.getStationId() != Integer.parseInt(station)) {
                    send = false;
                }
            }*/
            
            if(send) {
                this.sendMessage(this.formatMessage(json));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSnapImgComplete(String jsonVehicle, String jsonSnapImg) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onAllImgComplete(String jsonVehicle) {
        // TODO Auto-generated method stub
        
    }
	@Override
	public void onStcData(String data, StaticWeightService staticWeightService) {
		//保存设备发送的数据
		JSONObject jsonObject = JSONObject.parseObject(data);
		StaticWeight staticWeight=new StaticWeight();
		String staticId=UUID.randomUUID().toString().replace("-", "").toUpperCase();
		staticWeight.setStaticid(staticId);
		staticWeight.setWeight(jsonObject.getDouble("weight"));
		jsonObject.put("staticId",staticId);
		staticWeightService.insertStaticWeight(staticWeight);
		try {
			this.sendMessage(this.formatMessage(jsonObject.toJSONString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
