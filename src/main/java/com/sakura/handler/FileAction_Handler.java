package com.sakura.handler;

import org.apache.log4j.Logger;

import com.sakura.base.TestStep;
import com.sakura.service.RunUnitService;
import com.sakura.util.FileUtil;
import com.sakura.util.SeleniumUtil;

/**
 * 上传本地文件到服务器
 */
public class FileAction_Handler {
	Logger log = Logger.getLogger(getClass());

	public void getFile(TestStep step) throws Exception{
		String value = SeleniumUtil.parseStringHasEls(step.getLocalpath());
		log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">["+ value +"]");
		RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + ">["+ value +"]");
		value = FileUtil.getFileList(value).get(step.getDetails().get("key")).toString();
		SeleniumUtil.localmap.put(step.getDetails().get("keys"), value);
		log.info("『正常测试』开始执行: <成功记录到本地List列表，" + SeleniumUtil.localmap.toString() + ">");
	}
	
	public void getFiles(TestStep step) throws Exception{
		String value = "";
		if(step.getCatalogue()!=null) {
    		value = SeleniumUtil.parseStringHasEls(System.getProperty(step.getCatalogue())+step.getLocalpath());
    	}else {
    		value = SeleniumUtil.parseStringHasEls(System.getProperty("user.dir")+step.getLocalpath());
    	}
		log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">["+ value +"]");
		RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + ">["+ value +"]");
		value = FileUtil.getFileList(value).get(step.getDetails().get("key")).toString();
		SeleniumUtil.localmap.put(step.getDetails().get("keys"), value);
		log.info("『正常测试』开始执行: <成功记录到本地List列表，" + SeleniumUtil.localmap.toString() + ">");
	}
	
	public void deleteFile(TestStep step) throws Exception{
		String value = SeleniumUtil.parseStringHasEls(step.getLocalpath());
		log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">["+ value +"]");
		RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + ">["+ value +"]");
		if(step.getDelete()!=null) {
			FileUtil.deleteAllFile(value,step.getDelete());
    	}else {
    		FileUtil.deleteAllFile(value);
    	}
	}
	
	public void deleteFiles(TestStep step) throws Exception{
		String value = "";
		if(step.getCatalogue()!=null) {
    		value = SeleniumUtil.parseStringHasEls(System.getProperty(step.getCatalogue())+step.getLocalpath());
    	}else {
    		value = SeleniumUtil.parseStringHasEls(System.getProperty("user.dir")+step.getLocalpath());
    	}
		log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">["+ value +"]");
		RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + ">["+ value +"]");
		if(step.getDelete()!=null) {
			FileUtil.deleteAllFile(value,step.getDelete());
    	}else {
    		FileUtil.deleteAllFile(value);
    	}
	}
}
