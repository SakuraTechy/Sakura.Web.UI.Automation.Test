package com.sakura.handler;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;  
import org.testng.Assert;

import com.sakura.base.TestStep;
import com.sakura.service.AndroidXmlParseService;
import com.sakura.service.RunUnitService;
import com.sakura.service.WebXmlParseService;
import com.sakura.util.AppiumUtil;
import com.sakura.util.Constants;
import com.sakura.util.SeleniumUtil;
import com.sakura.util.StringUtil;

/**
 * <br>检查操作<br/>
 *
 * @author  刘智
 * @date    2017年7月26日 上午10:27:26
 * @version 1.0
 * @since   1.0
 */
/**
 * <br>检查操作<br/>
 *
 * @author  刘智
 * @date    2017年7月26日 上午10:27:26
 * @version 1.0
 * @since   1.0
 */
@SuppressWarnings("unchecked")
public class CheckActionHandler {
    Logger log = Logger.getLogger(getClass());
    
	/**
	 * <br>检查界面上的元素是否与预期值相等</br>
	 *
	 * @author    刘智
	 * @date      2017年8月2日 下午6:03:33
	 * @param step
	 * @throws Exception 
	 */
	//获取元素的文本值
	public void webCheck(TestStep step) throws Exception{
	    log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">");
	    Thread.sleep(1000);
//	    log.info(step.getWebDriver().getPageSource());
	    
		String Actual = SeleniumUtil.getElement(step).getText();
		if(step.getRegex()!=null) {
			if(step.getValue()!=null) {
				Actual = StringUtil.replaceAllBlank(Actual, step.getRegex(), step.getValue());
			} else {
				Actual = StringUtil.getRegex(Actual,step.getRegex());
			}
		}
		if(step.getKey()!=null&&step.getKeys()!=null) {
			Actual = Actual.replace(step.getKey(), step.getKeys());
		}
		String Expected = SeleniumUtil.parseStringHasEls(step.getExpect());	
		String FailHint = "Step"+step.getId()+"."+step.getMessage();
		String name = "" +step.getId() + "." +step.getName() + "";
		checkEqualsWeb(step,Actual,Expected,FailHint,name);
	}
	
	public void webNotcheck(TestStep step) throws Exception{
	    log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">");
	    Thread.sleep(1000);
		String Actual = SeleniumUtil.getElement(step).getText();
		String Expected = SeleniumUtil.parseStringHasEls(step.getExpect());	
		String FailHint = "Step"+step.getId()+"."+step.getMessage();
		String name = "" +step.getId() + "." +step.getName() + "";
		checkNotEqualsWeb(step,Actual,Expected,FailHint,name);
	}
	
	//获取元素的属性值
	public void webCheckvalue(TestStep step) throws Exception{
	    log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">");
		String Actual = SeleniumUtil.getElement(step).getAttribute(step.getValue());
		String Expected = SeleniumUtil.parseStringHasEls(step.getExpect());	
		String FailHint = "Step"+step.getId()+"."+step.getMessage();
		String name = "" +step.getId() + "." +step.getName() + "";
		checkEqualsWeb(step,Actual,Expected,FailHint,name);
	}
	
	//获取调用JS脚本返回的值
	public void webCheckjs(TestStep step) throws Exception{
	    log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">");
//		String js = "var user_input = document.getElementById(\"passport_51_user\").title;return user_input;";
//		String js1 = "var specVol =$(\"#specVol\").val();return specVol;";
//		String js2 = "return computeVol();";
//	    String js3 = "document.querySelector(\"div[class='modal-body scroll-wrap'] div:nth-child(2) div:nth-child(1) button:nth-child(1)\").className";
		String Actual = (String) ((JavascriptExecutor)step.getWebDriver()).executeScript(step.getValue());  
		String Expected = SeleniumUtil.parseStringHasEls(step.getExpect());	
		String FailHint = "Step"+step.getId()+"."+step.getMessage();
		String name = "" +step.getId() + "." +step.getName() + "";
		checkEqualsWeb(step,Actual,Expected,FailHint,name);
	}

	// 检查从数据库中查询出的结果中的值是否符合预期
	public void webChecklist(TestStep step) throws NumberFormatException, Exception{
		log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">");
		RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + "");
		String conditon = step.getDetails().get("condition");
		String name = "" +step.getId() + "." +step.getName() + "";
		if(Constants.SIZE_NOT_ZERO.equals(conditon))
			weblistSizeNotZero(step);
		else if(Constants.FIELD.equals(conditon)) 
			webfieldEquals(step,name);
	}
	
	// 检查本地list变量
	public void webCheckset(TestStep step) throws Exception{
	    log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">");
		String Actual = SeleniumUtil.parseStringHasEls(step.getValue());	
		String Expected = SeleniumUtil.parseStringHasEls(step.getExpect());	
		String FailHint = "Step"+step.getId()+"."+step.getMessage();
		String name = "" +step.getId() + "." +step.getName() + "";
		checkEqualsWeb(step,Actual,Expected,FailHint,name);
	}
	
	// 不检查本地list变量
	public void webNotchecklists(TestStep step) throws Exception{
	    log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">");
		String Actual = SeleniumUtil.parseStringHasEls(step.getValue());
		String Expected = SeleniumUtil.parseStringHasEls(step.getExpect());	
		String FailHint = "Step"+step.getId()+"."+step.getMessage();
		String name = "" +step.getId() + "." +step.getName() + "";
		checkNotEqualsWeb(step,Actual,Expected,FailHint,name);
	}
		
	public void androidCheck(TestStep step) throws Exception{
	    try{
	        log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">");
//	        String Actual = AppiumUtil.getElement(step).getText();
	        String Actual = AppiumUtil.getElement(step).getAttribute("name");
	        String Expected = AppiumUtil.parseStringHasEls(step.getExpect());
	        String FailHint = "Step"+step.getId()+"."+step.getMessage();
//	        String CaseID = step.getCaseid();
	        String name = "" +step.getId() + "." +step.getName() + "";
	        checkEqualsAndroid(Actual,Expected,FailHint,name);
	    }catch(Exception e){
	        //e.printStackTrace();
	        log.error("",e);
	        Thread.sleep(500);
	    }
	}
	
	public void androidChecklist(TestStep step) throws NumberFormatException, Exception{
		log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">");
		RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + "");
		String conditon = step.getDetails().get("condition");
		if(Constants.SIZE_NOT_ZERO.equals(conditon))
			androidlistSizeNotZero(step);
		else if(Constants.FIELD.equals(conditon)) 
			androidfieldEquals(step);
	}
	
	public void weblistSizeNotZero(TestStep step) throws Exception{
		List<Map<String,Object>>  list = (List<Map<String,Object>>)
		SeleniumUtil.parseEL(step.getDetails().get("subject"));
		
		if(list.size() == 0)
			throw new Exception(step.getMessage());
	}
	
	public void androidlistSizeNotZero(TestStep step) throws Exception{
		List<Map<String,Object>>  list = (List<Map<String,Object>>)
		AppiumUtil.parseEL(step.getDetails().get("subject"));
		
		if(list.size() == 0)
			throw new Exception(step.getMessage());
	}
	/**
	 * <br>检查列表中的字段值</br>
	 * 此时localmap中的值是一个List<Map<>>，所以需要提供检查的索引
	 *
	 * @author    刘智
	 * @date      2017年8月3日 上午9:35:19
	 * @param step
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public void webfieldEquals(TestStep step,String name) throws NumberFormatException, Exception{
//		log.info("CHECK_FILED---"+step.getDetails().get("subject"));	
		String Actual = (String)SeleniumUtil.parseStringHasEls(step.getDetails().get("subject"));
		String Expected = SeleniumUtil.parseStringHasEls(step.getExpect());
		String FailHint = "Step"+step.getId()+"."+step.getMessage();
		try {
			Assert.assertEquals(Actual,Expected,FailHint);
			Thread.sleep(500);
        }catch (Error | InterruptedException e)  {
        	if (StringUtil.isEqual("expect", step.getSkip())) {
				log.error("『发现问题』执行异常: " + "<" + step.getId() + "." + step.getName() + "==> 【目前有报错，已忽略跳过，请仔细检查测试脚本..】");
				RunUnitService.Step.put("name", "" + step.getId() + "." + step.getName() + " ==> 【目前有报错，已忽略跳过，请仔细检查测试脚本..】");
				RunUnitService.Step.put("picture", "skip");
			}else {
//				Assert.fail(FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
				RunUnitService.softAssert.fail("『发现问题』执行异常: "+FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
	 	        RunUnitService.Step.put("name",name);
			}
 	    }
	}	 

	public void androidfieldEquals(TestStep step) throws NumberFormatException, Exception{
//		log.info("CHECK_FILED---"+step.getDetails().get("subject"));
		String Actual = (String)AppiumUtil.parseStringHasEls(step.getDetails().get("subject"));
		String Expected = AppiumUtil.parseStringHasEls(step.getExpect());
		String FailHint = "Step"+step.getId()+"."+step.getMessage();
		try {
			Assert.assertEquals(Actual,Expected,FailHint);
			Thread.sleep(500);
        }catch (Error | InterruptedException e)  {
 	    	Assert.fail(FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
 	    }
	}
	
	/**
	 * <br>检查预期与实际是否相等，不等则提示错误信息，并截图</br>
	 *
	 * @author    刘智
	 * @date      2017年8月2日 下午6:01:04
	 * @param Actual 
	 * @param Expected
	 * @param FailHint
	 * @param FailedScreenshotCaseName
	 */
	public void checkEqualsWeb1(String Actual,String Expected,String FailHint,String CaseID){
		try {
			Assert.assertEquals(Actual,Expected,FailHint);
			Thread.sleep(500);
        }
 	    catch (Error | InterruptedException e)  {
 	    	WebXmlParseService.screenShot(CaseID);
 	    	Assert.fail(FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
 	    }
	}
	
	public void checkEqualsWeb(TestStep step, String Actual,String Expected,String FailHint,String name) throws Exception{
		try {
			Assert.assertEquals(Expected,Actual,FailHint);
			RunUnitService.Step.put("name",name);
			Thread.sleep(500);
        } catch (Error | InterruptedException e)  {
        	if (StringUtil.isEqual("expect", step.getSkip())) {
				log.error("『发现问题』执行异常: " + "<" + step.getId() + "." + step.getName() + "==> 【目前有报错，已忽略跳过，请仔细检查测试脚本..】");
				RunUnitService.Step.put("name", "" + step.getId() + "." + step.getName() + " ==> 【目前有报错，已忽略跳过，请仔细检查测试脚本..】");
				RunUnitService.Step.put("picture", "skip");
				RunUnitService.stepSkip++;
			} else {
				log.info("『发现问题』执行异常: "+FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
//	 	        Assert.fail(FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
	 	        RunUnitService.softAssert.fail("『发现问题』执行异常: "+FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
	 	        RunUnitService.Step.put("name",name);
				WebXmlParseService.screenShot(RunUnitService.testUnit.getId(), RunUnitService.testCase.getId(),FailHint);
				RunUnitService.stepFail++;
			}
        	//e.printStackTrace();
 	        log.error("",e);
 	        Thread.sleep(500);
 	    }
	}

	public void checkNotEqualsWeb(TestStep step, String Actual,String Expected,String FailHint,String name) throws Exception{
		try {
			Assert.assertNotEquals(Actual,Expected,FailHint);
			RunUnitService.Step.put("name",name);
			Thread.sleep(500);
        } catch (Error | InterruptedException e)  {
        	if (StringUtil.isEqual("expect", step.getSkip())) {
				log.error("『发现问题』执行异常: " + "<" + step.getId() + "." + step.getName() + "==> 【目前有报错，已忽略跳过，请仔细检查测试脚本..】");
				RunUnitService.Step.put("name", "" + step.getId() + "." + step.getName() + " ==> 【目前有报错，已忽略跳过，请仔细检查测试脚本..】");
				RunUnitService.Step.put("picture", "skip");
				RunUnitService.stepSkip++;
			} else {
				log.info("『发现问题』执行异常: "+FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
//	 	        Assert.fail(FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
	 	        RunUnitService.softAssert.fail("『发现问题』执行异常: "+FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
	 	        RunUnitService.Step.put("name",name);
	 	        WebXmlParseService.screenShot(RunUnitService.testUnit.getId(), RunUnitService.testCase.getId(),FailHint);
	 	        RunUnitService.stepFail++;
			}
        	//e.printStackTrace();
 	        log.error("",e);
 	        Thread.sleep(500);
 	    }
	}
	
	public void checkBooleanWeb(TestStep step, String Actual,String Expected,String FailHint,String name) throws Exception{
		try {
			Assert.assertEquals(Expected,Actual,FailHint);
			RunUnitService.Step.put("name",name);
			Thread.sleep(500);
        } catch (Error | InterruptedException e)  {
        	if (StringUtil.isEqual("expect", step.getSkip())) {
				log.error("『发现问题』执行异常: " + "<" + step.getId() + "." + step.getName() + "==> 【目前有报错，已忽略跳过，请仔细检查测试脚本..】");
				RunUnitService.Step.put("name", "" + step.getId() + "." + step.getName() + " ==> 【目前有报错，已忽略跳过，请仔细检查测试脚本..】");
				RunUnitService.Step.put("picture", "skip");
			} else {
				log.info("『发现问题』执行异常: "+FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
//	 	        Assert.fail(FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
	 	        RunUnitService.softAssert.fail("『发现问题』执行异常: "+FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
	 	        RunUnitService.Step.put("name",name);
				WebXmlParseService.screenShot(RunUnitService.testUnit.getId(), RunUnitService.testCase.getId(),FailHint);
			}
        	//e.printStackTrace();
 	        log.error("",e);
 	        Thread.sleep(500);
 	    }
		
		if(StringUtil.StringMatchRule(FailHint, name)) {
			
		}
	}
	
	public void webFuzzycheck(TestStep step) throws Exception{
	    log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">");
		String Actual = SeleniumUtil.getElement(step).getText();
		String Regex = step.getRegex();	
		String FailHint = "Step"+step.getId()+"."+step.getMessage();
		String name = "" +step.getId() + "." +step.getName() + "";
		if(!StringUtil.StringMatchRule(Actual, Regex)) {
			if (StringUtil.isEqual("regex", step.getSkip())) {
				log.error("『发现问题』执行异常: " + "<" + step.getId() + "." + step.getName() + "==> 【目前有报错，已忽略跳过，请仔细检查测试脚本..】");
				RunUnitService.Step.put("name", "" + step.getId() + "." + step.getName() + " ==> 【目前有报错，已忽略跳过，请仔细检查测试脚本..】");
				RunUnitService.Step.put("picture", "skip");
			} else {
				log.info("『发现问题』执行异常: "+FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Regex +"】");
//	 	        Assert.fail(FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
	 	        RunUnitService.softAssert.fail("『发现问题』执行异常: "+FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Regex +"】");
	 	        RunUnitService.Step.put("name",name);
				WebXmlParseService.screenShot(RunUnitService.testUnit.getId(), RunUnitService.testCase.getId(),FailHint);
			}
		}
	}
	
	public void checkEqualsAndroid(String Actual,String Expected,String FailHint,String name) throws Exception{
		try { 
			Assert.assertEquals(Actual,Expected,FailHint);
			RunUnitService.Step.put("name",name);
			Thread.sleep(500);
        } catch (Error | InterruptedException e)  {
 	        log.info("『发现问题』执行异常: "+FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
// 	        Assert.fail(FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
 	        RunUnitService.softAssert.fail("『发现问题』执行异常: "+FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
 	        RunUnitService.Step.put("name",name);
 	       AndroidXmlParseService.screenShot(RunUnitService.testUnit.getId(), RunUnitService.testCase.getId(),FailHint);
 	        //e.printStackTrace();
 	        log.error("",e);
 	        Thread.sleep(500);
 	    }
	}
}