package com.sakura.handler;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sakura.base.TestStep;
import com.sakura.service.RunUnitService;
import com.sakura.util.AppiumUtil;
import com.sakura.util.DBHelper;
import com.sakura.util.SeleniumUtil;
import com.sakura.util.StringUtil;

public class DBActionHandler {
    Logger log = Logger.getLogger(DBActionHandler.class);
    
	public void dbInserta(TestStep step) throws Exception{
		String sql = AppiumUtil.parseStringHasEls(step.getSql());
		log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">【"+ sql +"】");
		RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + "【"+ sql + "】");
		int n = DBHelper.insert(step.getDatatype(),step.getDataenviron(), step.getDevice(),step.getPort(), step.getDatabase(),sql);
		if(n > 0){
		    log.info("『执行成功』已插入"+n+"条数据");
		}
	}
	
	public void dbInsertw(TestStep step) throws Exception{
		String sql = SeleniumUtil.parseStringHasEls(step.getSql());
		log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">【"+ sql +"】");
	    RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + "【"+ sql + "】");
        int n = DBHelper.insert(step.getDatatype(),step.getDataenviron(), step.getDevice(),step.getPort(), step.getDatabase(),sql);
        if(n > 0){
            log.info("『执行成功』已插入"+n+"条数据");
        }
	}
	
	public void dbDeletea(TestStep step) throws Exception{
		String sql = AppiumUtil.parseStringHasEls(step.getSql());
		log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">【"+ sql +"】");
	    RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + "【"+ sql + "】");
        int n = DBHelper.delete(step.getDatatype(),step.getDataenviron(), step.getDevice(),step.getPort(), step.getDatabase(),sql);
        if(n > 0){
            log.info("『执行成功』已删除"+n+"条数据");
        }
	}
	
	public void dbDeletew(TestStep step) throws Exception{
		String sql = SeleniumUtil.parseStringHasEls(step.getSql());
		log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">【"+ sql +"】");
	    RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + "【"+ sql + "】");
        int n = DBHelper.delete(step.getDatatype(),step.getDataenviron(), step.getDevice(),step.getPort(), step.getDatabase(),sql);
        if(n > 0){
            log.info("『执行成功』已删除"+n+"条数据");
        }
	}

	public void dbUpdatea(TestStep step) throws Exception{
		String sql = AppiumUtil.parseStringHasEls(step.getSql());
		log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">【"+ sql +"】");
		RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + "【"+ sql + "】");
		int n = DBHelper.update(step.getDatatype(),step.getDataenviron(), step.getDevice(),step.getPort(), step.getDatabase(),sql);
		if(n > 0){
		    log.info("『执行成功』已修改"+n+"条数据");
//			Reporter.log(base.getDesc());
		}	
	}
	
	public void dbUpdatew(TestStep step) throws Exception{
		String sql = SeleniumUtil.parseStringHasEls(step.getSql());
		log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">【"+ sql +"】");
		RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + "【"+ sql + "】");
		int n = DBHelper.update(step.getDatatype(),step.getDataenviron(), step.getDevice(),step.getPort(), step.getDatabase(),sql);
		if(n > 0){
		    log.info("『执行成功』已修改"+n+"条数据");
//			Reporter.log(base.getDesc());
		}	
	}
	
	public void dbQuerya(TestStep step) throws Exception{
		if(StringUtils.isBlank(step.getDetails().get("key")))
			throw new Exception("数据库查询务必设置保存结果的键值，供后续操作使用，例子为details='key:credit'！");
		String sql = AppiumUtil.parseStringHasEls(step.getSql());
		log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">【"+ sql +"】");
		RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + "【"+ sql + "】");
//      System.err.println("Query-sql "+sql);
		List<Map<String, Object>> st = DBHelper.query(step.getDatatype(),step.getDataenviron(), step.getDevice(),step.getPort(), step.getDatabase(),sql);
		AppiumUtil.localmap.put(step.getDetails().get("key"), st);
		log.info("『正常测试』执行成功: <成功记录到本地缓存List列表，" +AppiumUtil.localmap.toString() + ">");
	}
	
	public void dbQueryw(TestStep step) throws Exception{
		String sql = SeleniumUtil.parseStringHasEls(step.getSql());
		log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">【"+ sql +"】");
        RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + "【"+ sql + "】");
        DBHelper.query(step.getDatatype(),step.getDataenviron(), step.getDevice(),step.getPort(), step.getDatabase(),sql);
	}
	
	public void dbQueryws(TestStep step) throws Exception{
		String sql = SeleniumUtil.parseStringHasEls(step.getSql());
		log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">【"+ sql +"】");
        List<Map<String, Object>> st = DBHelper.query(step.getDatatype(),step.getDataenviron(), step.getDevice(),step.getPort(), step.getDatabase(),sql);
        if(StringUtil.isNoEmpty(step.getDetails().get("key"))) {
        	// 数据库查询务必设置保存结果的键值，供后续操作使用，例子为details='key:credit'！
        	SeleniumUtil.localmap.put(step.getDetails().get("key"), st);
            log.info("『正常测试』执行成功: <成功记录到本地缓存List列表，" +SeleniumUtil.localmap.toString() + ">");
            RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + "【"+ step.getSql() + "】"+ "获取本地变量：【"+SeleniumUtil.localmap.toString()+"】");
        }
	}
	
	public void dbProcedurea(TestStep step) throws Exception{
	    String params = AppiumUtil.parseStringHasEls(step.getValue());
        log.info("『正常测试』开始执行: "+ step.getDetails().get("prc_name") + params + " <" +step.getId() + "." +step.getName() + ">");	    
        RunUnitService.Step.put("name","" +step.getId() + "." +step.getName() + ""+step.getDetails().get("prc_name")+"");
        int n =DBHelper.procedure(step.getDatatype(),step.getDataenviron(), step.getDevice(),step.getPort(),step.getDatabase(),step.getDetails().get("prc_name"),params,null);
		if(n > 0){
		    log.info("『执行成功』已运行"+n+"条数据");
		}
	}
	
	public void dbProcedurew(TestStep step) throws Exception{
		String params = SeleniumUtil.parseStringHasEls(step.getValue());
		log.info("『正常测试』开始执行: "+ step.getDetails().get("prc_name") + params + " <" +step.getId() + "." +step.getName() + ">");
		RunUnitService.Step.put("name","" +step.getId() + "." +step.getName() + ""+step.getDetails().get("prc_name")+"");
		int n =DBHelper.procedure(step.getDatatype(),step.getDataenviron(), step.getDevice(),step.getPort(), step.getDatabase(),step.getDetails().get("prc_name"),params,null);
		if(n > 0){
		    log.info("『执行成功』已运行"+n+"条数据");
		}
	}
}
