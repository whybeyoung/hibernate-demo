package com.iflytek.iaas.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.iflytek.iaas.service.ImageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 远程执行shell工具类
 * test.sh
 * #!/bin/bash
 * echo $1 $2
 */
public class ShellHelper {

	private Logger log = LoggerFactory.getLogger(ImageService.class);
	
	private String ip;

	private int port;
	
	private String username;

	private String password;

	private Connection conn;
	private Session sess;

	/**
	 * 是否加密
	 */
	private boolean isEncrypt = true;

	public ShellHelper(String ip, int port, String username, String password, boolean isEncrypt) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.isEncrypt = isEncrypt;
		if(this.isEncrypt && StringUtils.isNoneBlank(password)){
//			this.password = DESUtil.decryptFromDES(password, Constants.SERVER_PSW_DES_KEY);
		}else {
			this.password = password;
		}
	}

	/**
	 * 构造函数（默认需要加密）
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	public ShellHelper(String ip, int port, String username, String password) throws Exception{
		this(ip, port, username, password, true);
	}

	/**
	 * 创建连接
	 * @return
	 * @throws Exception
	 */
	private Connection getConnection() throws Exception{
		Connection conn = new Connection(ip, port);
		try {
			conn.connect();
			if(!conn.authenticateWithPassword(username, password)){
				throw new Exception("账号密码鉴权失败");
			}
		}catch(Exception e) {
			throw e;
		}
		return conn;
	}

	public void openSession() throws Exception {
		conn = getConnection();
		sess = conn.openSession();
	}
	/**
	 * 关闭连接
	 * @param conn
	 * @param sess
	 */
	private void closeCollection(Connection conn, Session sess) {
		if (sess != null) {
			sess.close();
		}
		if (conn != null) {
			conn.close();
		}
	}

	/**
	 * 关闭连接
	 */
	public void closeSession() {
		if (sess != null) {
			sess.close();
		}
		if (conn != null) {
			conn.close();
		}
	}

	/**
	 * 执行远程服务端命令，无返回
	 * @param command
	 * @throws Exception
	 */
	public void exec(String command) throws Exception{
		Connection conn = null;
		Session sess = null;
		try {
			conn = getConnection();
			sess = conn.openSession();
			sess.execCommand(command);
		} catch (Exception e) {
			log.error("远程执行IP【{}】命令【{}】异常", ip, command, e);
		}finally {
			closeCollection(conn, sess);
		}
	}

	/**
	 * 执行远程服务端命令，不关闭连接会话，有返回返回
	 * @param command
	 * @throws Exception
	 */
	public String execWithoutClose(String command) throws Exception{

		BufferedReader br = null;
		InputStreamReader is = null;
		try {
			sess.execCommand(command);
			is = new InputStreamReader(sess.getStdout());
			br = new BufferedReader(is);
			String line = null;
			StringBuilder result = new StringBuilder();
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (Exception e) {
			log.error("远程执行IP【{}】命令【{}】异常", ip, command, e);
		}finally {
			if(br != null){
				br.close();
			}
			if(is != null){
				is.close();
			}
		}
		return null;
	}

	/**
	 * 执行远程服务端命令，有返回
	 * @param command
	 * @throws Exception
	 */
	public String execWithResult(String command) throws Exception{
		Connection conn = null;
		Session sess = null;
		BufferedReader br = null;
		InputStreamReader is = null;
		try {
			conn = getConnection();
			sess = conn.openSession();
			sess.execCommand(command);
			is = new InputStreamReader(sess.getStdout());
			br = new BufferedReader(is);
			String line = null;
			StringBuilder result = new StringBuilder();
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (Exception e) {
			log.error("远程执行IP【{}】命令【{}】异常", ip, command, e);
		}finally {
			if(br != null){
				br.close();
			}
			if(is != null){
				is.close();
			}
			closeCollection(conn, sess);
		}
		return null;
	}
	
	public static void main(final String [] args) {

		String image = "harbour.iflytek.com/library/pause-amd64:0";
		System.out.println(image.substring(image.indexOf(":")));
		try {
			ShellHelper helper = new ShellHelper("172.31.1.157",22, "root", "iflytek_docker", false);
//			helper.openSession();
			String code="";
			code = helper.execWithResult("cd /tmp");
			System.out.println(code);
			code = helper.execWithResult("cd /tmp&&pwd");
			System.out.println(code);
			code = helper.execWithResult("docker login harbour.iflytek.com -u public -p Harbour123");
			System.out.println(code);

			if("1".equals(code)){
				System.out.println("success");
			}else{
				System.out.println("fail");
			}
//			helper.closeSession();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }  
	

}

