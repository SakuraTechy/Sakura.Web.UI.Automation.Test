package com.sakura.handler;

import org.apache.log4j.Logger;

import com.sakura.base.TestStep;
import com.sakura.service.RunUnitService;
import com.sakura.util.ConfigUtil;
import com.sakura.util.Constants;
import com.sakura.util.FreeSFTPUtil;
import com.sakura.util.SeleniumUtil;
import com.sakura.util.StringUtil;

/**
 * 上传本地文件到服务器
 */
public class FreeSSH_FTP_Handler {
	Logger log = Logger.getLogger(getClass());

	public void freeSftp(TestStep step) throws Exception{
		String host = ConfigUtil.getProperty(""+step.getDevice()+"_LinuxShell_IP", Constants.CONFIG_APP);
		int port = Integer.parseInt(ConfigUtil.getProperty(""+step.getDevice()+"_LinuxShell_Port", Constants.CONFIG_APP));
		String username = ConfigUtil.getProperty(""+step.getDevice()+"_LinuxShell_UserName", Constants.CONFIG_APP);
		String password = ConfigUtil.getProperty(""+step.getDevice()+"_LinuxShell_PassWord", Constants.CONFIG_APP);
        
		FreeSFTPUtil ftp = new FreeSFTPUtil(host,port,username,password);
        String localPath = SeleniumUtil.parseStringHasEls(step.getLocalpath());	
		String remotePath = step.getRemotepath();
        ftp.connect();
        boolean result = false;
        if(StringUtil.isEqual(step.getFiletype(),"单个文件")){
        	result = ftp.uploadFile(localPath,SeleniumUtil.parseStringHasEls(step.getValue()),remotePath,SeleniumUtil.parseStringHasEls(step.getValue()),Boolean.parseBoolean(step.getDelete()));
        } else {
        	result = ftp.bacthUploadFile(localPath,remotePath,Boolean.parseBoolean(step.getDelete()));
        }
        ftp.listFiles(remotePath);
        ftp.disconnect();
        log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + "<" + localPath + ">");
		RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + "<" + localPath + ">");
		log.info("『正常测试』文件上传成功: " + "<" + result + ">");
	}
	
	public void freeSftps(TestStep step) throws Exception{
		String host = ConfigUtil.getProperty(""+step.getDevice()+"_LinuxShell_IP", Constants.CONFIG_APP);
		int port = Integer.parseInt(ConfigUtil.getProperty(""+step.getDevice()+"_LinuxShell_Port", Constants.CONFIG_APP));
		String username = ConfigUtil.getProperty(""+step.getDevice()+"_LinuxShell_UserName", Constants.CONFIG_APP);
		String password = ConfigUtil.getProperty(""+step.getDevice()+"_LinuxShell_PassWord", Constants.CONFIG_APP);
		FreeSFTPUtil ftp = new FreeSFTPUtil(host,port,username,password);
		String localPath="";
		if(step.getCatalogue()!=null) {
			localPath = SeleniumUtil.parseStringHasEls(System.getProperty(step.getCatalogue()) + step.getLocalpath());
		}else {
			localPath = SeleniumUtil.parseStringHasEls(System.getProperty("user.dir") + step.getLocalpath());
		}
		String remotePath = step.getRemotepath();
        ftp.connect();
        boolean result = false;
        if(StringUtil.isEqual(step.getFiletype(),"单个文件")){
        	result = ftp.uploadFile(localPath,SeleniumUtil.parseStringHasEls(step.getValue()),remotePath,SeleniumUtil.parseStringHasEls(step.getValue()),Boolean.parseBoolean(step.getDelete()));
        } else {
        	result = ftp.bacthUploadFile(localPath,remotePath,Boolean.parseBoolean(step.getDelete()));
        }
        ftp.listFiles(remotePath);
        ftp.disconnect();
        log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + "<" + localPath + ">");
		RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + "<" + localPath + ">");
		log.info("『正常测试』文件上传成功: " + "<" + result + ">");
	}
}
