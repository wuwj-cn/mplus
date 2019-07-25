package com.mplus.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JschUtil {

    private String charset = "UTF-8"; // 设置编码格式,可以根据服务器的编码设置相应的编码格式
    private JSch jsch;
    private Session session;
    Channel channel = null;
    ChannelSftp chSftp = null;
 
 
    //初始化配置参数，暂定硬编码，后续有需要可修改为可配置
    private String jschHost = "***";
    private int jschPort = -1;
    private String jschUserName = "***";
    private String jschPassWord = "***";
    private int jschTimeOut = 1000 * 5 * 60;
 
    private static final JschUtil INSTANCE = new JschUtil();
    private JschUtil() {}
    public static final JschUtil getInstance() {
        return INSTANCE;
    }
 
    /**
     * 连接到指定的服务器
     * @return
     * @throws JSchException
     */
    public boolean connect() throws JSchException {
        jsch = new JSch();// 创建JSch对象
        boolean result = false;
        try{
            long begin = System.currentTimeMillis();//连接前时间
            session = jsch.getSession(jschUserName, jschHost, jschPort);// // 根据用户名，主机ip，端口获取一个Session对象
            session.setPassword(jschPassWord); // 设置密码
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);// 为Session对象设置properties
            session.setTimeout(jschTimeOut);//设置连接超时时间
            session.connect();
            long end = System.currentTimeMillis();//连接后时间
            log.debug("Connected To SA Successful in {} ms", (end-begin));
            result = session.isConnected();
        }catch(Exception e){
            log.error(e.getMessage(), e);
        }finally{
            if(result){
                log.debug("connect success");
            }else{
                log.debug("connect failure");
            }
        }
 
        if(!session.isConnected()) {
            log.error("获取连接失败");
        }
        return  session.isConnected();
    }
 
    /**
     * 关闭连接
     */
    public void close() {
        if(channel != null && channel.isConnected()){
            channel.disconnect();
            channel=null;
        }
 
        if(session!=null && session.isConnected()){
            session.disconnect();
            session=null;
        }
    }
 
    /**
     * 脚本是同步执行的方式
     * 执行脚本命令
     * @param command
     * @return
     */
    public Map<String,Object> execCmmmand(String command) throws Exception{
        Map<String,Object> mapResult = new HashMap<String,Object>();
 
        log.debug(command);
 
        StringBuffer result = new StringBuffer();//脚本返回结果
 
        BufferedReader reader = null;
        int returnCode = -2;//脚本执行退出状态码
        try {
            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
 
            channel.connect();//执行命令 等待执行结束
 
            InputStream in = channel.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in, Charset.forName(charset)));
 
            String res="";
            while((res=reader.readLine()) != null){
                result.append(res+"\n");
            }
            returnCode = channel.getExitStatus();
            mapResult.put("returnCode",returnCode);
            mapResult.put("result",result.toString());
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        } catch (JSchException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
 
        return mapResult;
    }
 
    public String getCharset() {
        return charset;
    }
 
    public void setCharset(String charset) {
        this.charset = charset;
    }
}
