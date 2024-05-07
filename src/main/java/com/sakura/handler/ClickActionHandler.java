package com.sakura.handler;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.sakura.base.TestStep;
import com.sakura.service.RunUnitService;
import com.sakura.util.AppiumUtil;
import com.sakura.util.SeleniumUtil;

/**
 * <br>
 * 处理界面上的点击操作，即如果当前操作为点击，<br/>
 * 则选中脚本中配置的节点，并执行点击操作</br>
 *
 * @author 刘智
 * @date 2017年7月26日 上午10:27:26
 * @version 1.0
 * @since 1.0
 */
public class ClickActionHandler {
    Logger log = Logger.getLogger(ClickActionHandler.class);
    
    /**
     * <br>Web端点击操作</br>
     *
     * @param step
     * @throws Exception
     */
    public void webClick(TestStep step) throws Exception {
        try {
            WebElement e = SeleniumUtil.getElement(step);
            if (e != null) {
            	log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">["+ e.getText() +"]");
            	RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + ">["+ e.getText() +"]");
                e.click();
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            //e.printStackTrace();
            log.error("",e);
        }
    }

    /**
	 * <br>Web端选项框点击操作</br>
	 *
	 * @param step
	 * @throws Exception 
	 */
	public void selectClick(TestStep step) throws Exception{ 
		WebDriver driver = step.getWebDriver();
		WebElement selectElem = SeleniumUtil.getElement(step);
        Actions actions = new Actions(driver);
        actions.moveToElement(selectElem).click().perform();
//        Thread.sleep(Long.valueOf("3000"));
        Thread.sleep(1000);
        WebElement e = driver.findElement(By.xpath(SeleniumUtil.parseStringHasEls(step.getValue())));
        log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">["+ e.getText() +"]");
        RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + ">["+ e.getText() +"]");
        e.click();
    }
	
	/**
	 * <br>Web端选项框点击操作</br>
	 *
	 * @param step
	 * @throws Exception 
	 */
	public void inputClick(TestStep step) throws Exception{ 
		WebDriver driver = step.getWebDriver();
		WebElement selectElem = SeleniumUtil.getElement(step);
		selectElem.sendKeys(SeleniumUtil.parseStringHasEls(step.getValue()));
//		Thread.sleep(Long.valueOf("1000"));
		Thread.sleep(1000);
		
        WebElement e = driver.findElement(By.xpath(step.getElement()));
        log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">["+ e.getText() +"]");
        RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + ">["+ e.getText() +"]");
        e.click();
    }
	
	/**
	 * <br>Web端滚动到元素操作</br>
	 *
	 * @param step
	 * @throws Exception 
	 */
	public void scrollElement(TestStep step) throws Exception {
		WebDriver driver = step.getWebDriver();
		WebElement selectElem = SeleniumUtil.getElement(step);
		selectElem.click();
        WebElement e = driver.findElement(By.xpath(step.getElement()));
        JavascriptExecutor js= (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true)",e);
        log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">["+ e.getText() +"]");
        RunUnitService.Step.put("name","" + step.getId() + "." + step.getName() + ">["+ e.getText() +"]");
        e.click();
    }

    /**
     * <br>Web端点击浏览器弹出框的确定键</br>
     *
     * @param step
     * @throws Exception
     */
    public void clickOk(TestStep step) throws Exception {
        log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">");
        step.getWebDriver().switchTo().alert().accept();
    }

    /**
     * <br>Web端点击浏览器弹出框的取消键</br>
     *
     * @param step
     * @throws Exception
     */
    public void clickCancel(TestStep step) throws Exception {
        log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">");
        step.getWebDriver().switchTo().alert().dismiss();
    }

    /**
     * <br>Web端执行浏览器文本弹出框的文本输入</br>
     *
     * @param step
     * @throws Exception
     */
    public void clickText(TestStep step) throws Exception {
        log.info("『正常测试』开始执行: " + "<" +step.getId() + "." +step.getName() + ">");
        step.getWebDriver().switchTo().alert().sendKeys(step.getValue());
    }

    /**
     * <br>
     * Android点击操作</br>
     *
     * @param step
     * @throws Exception
     */
    public void androidClick(TestStep step) throws Exception {
        // log.info(TestUnit.getid());
        // log.info(TestUnit.getname());
        // log.info(TestCase.getid());
        // log.info(TestCase.getname());
        try {
            log.info("『正常测试』开始执行: " + "<" + step.getId() + "." + step.getName() + ">");
            AppiumUtil.getElement(step).click();
        } catch (Exception e) {
            // log.info("『发现问题』执行异常: " +"<" +step.getId() + "." +step.getName() + "> ==> 【未找到相关元素信息】");
            // RunUnitService.softAssert.fail("『发现问题』执行异常: " +"<" +step.getId() + "." +step.getName() + "> ==>
            // 【未找到相关元素信息】");
            // AndroidXmlParseService.screenShot(TestUnit.getname(),TestCase.getid(),"" +step.getId() + "."
            // +step.getName() + "");
             //e.printStackTrace();
             log.error("",e);
             Thread.sleep(500);
        }
    }
}
