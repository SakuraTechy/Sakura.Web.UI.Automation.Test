package com.sakura.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sakura.base.StepAction;
import com.sakura.base.TestCase;
import com.sakura.base.TestStep;
import com.sakura.base.TestUnit;
import com.sakura.util.ConfigUtil;
import com.sakura.util.Constants;
import com.sakura.util.DateUtil;
import com.sakura.util.StringUtil;

import io.appium.java_client.android.AndroidDriver;

/**
 * <br>配置Appium</br>
 * <br>解析xml到TestUnit</br>
 *
 * @version 1.0
 * @since   1.0
 */
@SuppressWarnings({"static-access"})
public class AndroidXmlParseService {

	static Logger log = LoggerFactory.getLogger(AndroidXmlParseService.class);

	static public AndroidDriver<WebElement> driver;
	static AppiumService appiumServer; 

	/**
	 * <br>Appium服务配置</br>
	 *
	 * @throws Exception
	 */
	public static void AppiumConfigure(String APP,String Device) throws Exception {
		//安装APP
//		Runtime.getRuntime().exec("adb -s "+ConfigUtil.getProperty(""+Device+"_DeviceID", Constants.CONFIG_APP)+" install "+ConfigUtil.getProperty("apk.path", Constants.CONFIG_APP)+""+ConfigUtil.getProperty(""+APP+"_ApkName", Constants.CONFIG_APP)+"");
		
		//指定APK安装路径:
//		File apk = new File(ConfigUtil.getProperty("apk.path", Constants.CONFIG_APP), ConfigUtil.getProperty(""+APP+"_ApkName", Constants.CONFIG_APP));

		//设置自动化相关参数:
		DesiredCapabilities capabilities = new DesiredCapabilities();

		//设置Appium测试引擎:
		capabilities.setCapability("device", "uiautomator2");

		//指定测试设备系统及系统版本:
		capabilities.setCapability("platformName", ConfigUtil.getProperty(""+Device+"_PlatformName", Constants.CONFIG_APP));
		capabilities.setCapability("platformVersion", ConfigUtil.getProperty(""+Device+"_PlatformVersion",Constants.CONFIG_APP));
		
		//当前连接的手机,默认识别一台
//		capabilities.setCapability("deviceName", "Android Emulator");
	
		//指定测试设备名称及设备ID:
        capabilities.setCapability("deviceName", ConfigUtil.getProperty(""+Device+"_DeviceID", Constants.CONFIG_APP));
        capabilities.setCapability("udid", ConfigUtil.getProperty(""+Device+"_DeviceID", Constants.CONFIG_APP));
        
		//小米5S(黑色-USB有线连接)
//        capabilities.setCapability("deviceName", "29739ff4");
//        capabilities.setCapability("udid", "29739ff4");
        
        //小米5S(金色-WIFI无线连接)
//        capabilities.setCapability("deviceName", ConfigUtil.getProperty("MI_5S_golden.WIFI", Constants.CONFIG_COMMON));
//        capabilities.setCapability("udid", ConfigUtil.getProperty("MI_5S_golden.WIFI", Constants.CONFIG_COMMON));
        
		//初始化APP缓存，false(初始化)/true(不初始化)
		capabilities.setCapability("noReset", false);

		//重新安装APP，true(重新安装)/false(不重新安装)
		capabilities.setCapability("fullReset", false);
		
		//启动时是否覆盖session，true(覆盖)/false(不覆盖)
		capabilities.setCapability("sessionOverride", false);

		//开启中文输入，安装Unicode输入法，true(安装)/false(不安装)
		capabilities.setCapability("unicodeKeyboard", true);

		//还原系统默认输入法，true(还原)/false(不还原)
		capabilities.setCapability("resetKeyboard", true);

		//设置Appium超时时间:
		capabilities.setCapability("newCommandTimeout", 60000);

		//APK重新签名，false(重签)/true(不重签)
		capabilities.setCapability("noSign", true);

		//已安装后启动APP
//		capabilities.setCapability("app", apk.getAbsolutePath());
		capabilities.setCapability("app", ConfigUtil.getProperty("apk.url", Constants.CONFIG_APP));
		
//		//要启动的应用包名
		capabilities.setCapability("appPackage", ConfigUtil.getProperty(""+APP+"_appPackage", Constants.CONFIG_APP));
//		
//		//要启动的应用的起始activity
		capabilities.setCapability("appActivity", ConfigUtil.getProperty(""+APP+"_appActivity", Constants.CONFIG_APP));
		
		//进入Webview
//		capabilities.setCapability("autoWebview", true);

		//初始化AndroidDriver
		driver = new AndroidDriver<WebElement>(new URL("http://"+ConfigUtil.getProperty(""+Device+"_Appium_IP", Constants.CONFIG_APP)+":"+ConfigUtil.getProperty(""+Device+"_Appium_Port", Constants.CONFIG_APP)+"/wd/hub"), capabilities);
	    
		//命令启动Appium Service
		//node C:\Users\King-liu\AppData\Local\Programs\Appium\resources\app\node_modules\appium\build\lib\main.js --address 127.0.0.1 --port 4723
		
		//设置全局隐性等待时间
		driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
	}

    public static void AppiumTest(String APP,String Device) throws Exception {  
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("device", "uiautomator2");
        capabilities.setCapability("platformName", ConfigUtil.getProperty(""+Device+"_PlatformName", Constants.CONFIG_APP));
        capabilities.setCapability("platformVersion", ConfigUtil.getProperty(""+Device+"_PlatformVersion",Constants.CONFIG_APP));
        capabilities.setCapability("deviceName", ConfigUtil.getProperty(""+Device+"_DeviceID", Constants.CONFIG_APP));
        capabilities.setCapability("udid", ConfigUtil.getProperty(""+Device+"_DeviceID", Constants.CONFIG_APP));
        capabilities.setCapability("noReset", true);
        capabilities.setCapability("fullReset", false);
        capabilities.setCapability("app", ConfigUtil.getProperty("apk.url", Constants.CONFIG_APP));
        capabilities.setCapability("appPackage", ConfigUtil.getProperty(""+APP+"_appPackage", Constants.CONFIG_APP));
        capabilities.setCapability("appActivity", ConfigUtil.getProperty(""+APP+"_appActivity", Constants.CONFIG_APP));
        capabilities.setCapability("sessionOverride", false);
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);
        capabilities.setCapability("noSign", true);
//      capabilities.setCapability("autoWebview", true);
        capabilities.setCapability("newCommandTimeout", 60000);
        driver = new AndroidDriver<WebElement>(new URL("http://"+ConfigUtil.getProperty(""+Device+"_Appium_IP", Constants.CONFIG_APP)+":"+ConfigUtil.getProperty(""+Device+"_Appium_Port", Constants.CONFIG_APP)+"/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
	}
	
	public static void AppiumProduction(String ApkName,String PlatformName,String PlatformVersion,String DeviceID) throws Exception { 
		File apk = new File(ConfigUtil.getProperty("apk.path", Constants.CONFIG_COMMON), ApkName);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("device", "uiautomator2");
		capabilities.setCapability("platformName", PlatformName);
		capabilities.setCapability("platformVersion", PlatformVersion);
		capabilities.setCapability("deviceName", DeviceID);
        capabilities.setCapability("udid", DeviceID);
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("fullReset", false);
		capabilities.setCapability("app", apk.getAbsolutePath());
		capabilities.setCapability("sessionOverride", false);
		capabilities.setCapability("unicodeKeyboard", true);
		capabilities.setCapability("resetKeyboard", true);
		capabilities.setCapability("noSign", true);
//		capabilities.setCapability("autoWebview", "true");
		capabilities.setCapability("newCommandTimeout", 60000);
		driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
	}
	
	public static void AppiumCustom(String APP,String Device) throws Exception {  
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("device", "uiautomator2");
        capabilities.setCapability("platformName", ConfigUtil.getProperty(""+Device+"_PlatformName", Constants.CONFIG_APP));
        capabilities.setCapability("platformVersion", ConfigUtil.getProperty(""+Device+"_PlatformVersion",Constants.CONFIG_APP));
        capabilities.setCapability("deviceName", ConfigUtil.getProperty(""+Device+"_DeviceID", Constants.CONFIG_APP));
        capabilities.setCapability("udid", ConfigUtil.getProperty(""+Device+"_DeviceID", Constants.CONFIG_APP));
        capabilities.setCapability("noReset", false);
        capabilities.setCapability("fullReset", false);
        capabilities.setCapability("app", ConfigUtil.getProperty("apk.url", Constants.CONFIG_APP));
        capabilities.setCapability("appPackage", ConfigUtil.getProperty(""+APP+"_appPackage", Constants.CONFIG_APP));
        capabilities.setCapability("appActivity", ConfigUtil.getProperty(""+APP+"_appActivity", Constants.CONFIG_APP));
        capabilities.setCapability("sessionOverride", false);
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);
        capabilities.setCapability("noSign", true);
//      capabilities.setCapability("autoWebview", true);
        capabilities.setCapability("newCommandTimeout", 60000);
        driver = new AndroidDriver<WebElement>(new URL("http://"+ConfigUtil.getProperty(""+Device+"_Appium_IP", Constants.CONFIG_APP)+":"+ConfigUtil.getProperty(""+Device+"_Appium_Port", Constants.CONFIG_APP)+"/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
    }
	
	/**
	 * <br>解析xml测试场景配置文件之前，对appium进行配置</br>
	 *
	 * @param xmlPath
	 * @return
	 * @throws IOException 
	 */
	public static TestUnit parse(String APP,String Device,String XmlPath){
		try {
			log.info("当前为重新安装APK，初始化Android设备,耐心等待App启动ing..."); 
			AppiumConfigure(APP,Device);
		} catch (Exception e) {
//			e.printStackTrace();
			log.error("",e);
		}
		return parse(new File("src/test/java/"+XmlPath));
	}
	
	public static TestUnit ParseTest(String APP,String Device,Boolean xmState,String XmlPath) throws Exception {
	    if(xmState){
	        log.info("当前为测试环境，开始初始化Android设备,耐心等待App启动ing..."); 
	        AppiumTest(APP,Device);
	    }
		return parse(new File("src/test/java/"+XmlPath));
	}
	
	public static TestUnit ParseProduction(String ApkName,String PlatformName,String PlatformVersion,String DeviceID,String XmlPath) {
		try {
			log.info("当前为生产环境，开始初始化Android设备,耐心等待App启动ing..."); 
			AppiumProduction(ApkName,PlatformName,PlatformVersion,DeviceID);
		} catch (Exception e) {
//			e.printStackTrace();
			log.error("",e);
		}
		return parse(new File("src/test/java/"+XmlPath));
	}
	
    public static TestUnit ParseCustom(int index,String APP,String Device,String XmlPath) throws InterruptedException {
		try {
			if(index==0){
				//0表示启动前会杀掉所有Appium服务，做为第一个Appium服务启动
				appiumServer =new AppiumService();
				appiumServer.StartAppium(ConfigUtil.getProperty(""+Device+"_Appium_IP", Constants.CONFIG_APP),ConfigUtil.getProperty(""+Device+"_Appium_Port", Constants.CONFIG_APP),ConfigUtil.getProperty(""+Device+"_FreeSSHd_UserName", Constants.CONFIG_APP),ConfigUtil.getProperty(""+Device+"_FreeSSHd_PassWord", Constants.CONFIG_APP),Device);
			}else if(index==1){
				//1表示不会杀掉Appium服务，接着启动第二个Appium服务，适用多台设备并发测试
				appiumServer =new AppiumService();
				appiumServer.appiumStart(ConfigUtil.getProperty(""+Device+"_Appium_IP", Constants.CONFIG_APP),ConfigUtil.getProperty(""+Device+"_Appium_Port", Constants.CONFIG_APP),ConfigUtil.getProperty(""+Device+"_FreeSSHd_UserName", Constants.CONFIG_APP),ConfigUtil.getProperty(""+Device+"_FreeSSHd_PassWord", Constants.CONFIG_APP),Device);
			}
			log.info("当前为测试环境，开始初始化Android设备,耐心等待App启动ing..."); 
			AppiumCustom(APP,Device);
		} catch (Exception e) {
//			e.printStackTrace();
			log.error("",e);
			Thread.sleep(1000);
		}
		return parse(new File("src/test/java/"+XmlPath));
	}
	
	public static TestUnit Parse(String XmlPath){
        return parse(new File("src/test/java/"+XmlPath));
    }
	
	/**
	 * <br>根据用例的名称，截取图片，进行保存</br>
	 *
	 * @param UnitName,CaseName,StepDesc
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public static void screenShot(String UnitName,String CaseName,String StepDesc) throws Exception{

	    String data = DateUtil.getDateFormat("yyyy-MM-dd");
	    String Product_Name = ConfigUtil.getProperty("Product_Name", Constants.CONFIG_APP);
	    String Product_Version =  ConfigUtil.getProperty("Product_Version", Constants.CONFIG_APP);
	    String AppointDir = System.getProperty("user.dir")+"/TestOutput/ExtentReport/"+data+"/"+Product_Name+"/"+Product_Version+"/BugScreenshot/"+UnitName+"/" + CaseName+"/";
		String picture = AppointDir +"step"+ StepDesc + "异常.png";
		try {
		    File file = new File(AppointDir);
		    File[] fs = file.listFiles();
		    //图片大于300时，删除清空
	        if (fs!=null&&fs.length >= 300) { 
	            for (File f : fs) {
	                if (f.getName().contains("png"))
	                    f.delete();
	            }
	        }
			File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	        FileUtils.copyFile(screenShot, new File(picture));
//			FileUtils.copyFile(new File("D:\\King\\Eclipse\\Sunline.finline.Android.Test\\TestOutput\\ExtentReport\\456.jpg"), new File(picture));
	        log.info("『发现问题』开始执行: " + "<截图操作,保存目录为[" + picture + "]>");
		} catch (Exception e) {
//			e.printStackTrace();
			log.error("",e);
			Thread.sleep(500);
		} finally {
		    if(StringUtil.isEqual(ConfigUtil.getProperty("Environment_Type", Constants.CONFIG_APP), "Linux")&&StringUtil.isNoEmpty(ConfigUtil.getProperty("ExtentReport_Type", Constants.CONFIG_APP))){
	            AppointDir = ""+ConfigUtil.getProperty("ExtentReport_URL", Constants.CONFIG_APP)+""+ConfigUtil.getProperty("ExtentReport_Type", Constants.CONFIG_APP)+"/ExtentReport/"+data+"/"+Product_Name+"/"+Product_Version+"/BugScreenshot/"+UnitName+"/" + CaseName+"/";
	            picture = AppointDir +"step"+ StepDesc + "异常.png";
	            RunUnitService.Step.put("picture",picture);
		    }else{
	            RunUnitService.Step.put("picture",picture);
	        }
		}
	}

	/**
	 * <br>根据用例的名称，录制视频，上传至指定目录</br>
	 * 
	 * @param CaseName
	 * @param CaseName
	 */
	public static void StartRecord(String CaseName) throws Exception {
	    Runtime rt = Runtime.getRuntime();
	    try {
	  		 rt.exec("cmd.exe /C adb shell screenrecord /sdcard/"+ CaseName +".mp4");
	  	} 
	    catch (IOException e) {
	    	 e.printStackTrace();
	    	 log.error("",e);
	  	}
	}
	  
	public void EndRecord(String CaseName,String SpecifiedDirectory) throws Exception {
	    Runtime rt = Runtime.getRuntime();
	    try {
	  		 rt.exec("cmd.exe /C adb kill-server");
	  		 rt.exec("cmd.exe /C adb start-server");
	  		 Thread.sleep(5000);
	  		 rt.exec("cmd.exe /C adb pull /sdcard/"+ CaseName +".mp4");
	  		 Thread.sleep(5000);
	  		 rt.exec("cmd.exe /C move "+ CaseName +".mp4 " + SpecifiedDirectory);
	  	} 
	    catch (IOException e) {
	    	 e.printStackTrace();
	    	 log.error("",e);
	    }
	}
	
	/**
	 * <br>解析xml测试场景配置文件，转换为一个TestUnit实例，也就是一个TestCase的集合</br>
	 *
	 * @param xmlFile
	 * @return
	 */
	public static TestUnit parse(File xmlFile) {

		TestUnit testUnit = null;

		if ( xmlFile == null || !xmlFile.exists()  )
			return testUnit;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		
		try {
			builder = factory.newDocumentBuilder();
			
			Document doc = builder.parse(xmlFile);
			Element root = doc.getDocumentElement();
			NodeList cases = root.getElementsByTagName("case");
			
			//存放case的map
			LinkedHashMap<String, TestCase> caseMap = new LinkedHashMap<String, TestCase>();
			Element child;
			TestCase testCase;
			//逐个解析xml中的case元素
			for (int i = 0; i < cases.getLength(); i++) {
				child = (Element) cases.item(i);
				testCase = parseTestCase(child);
				if (testCase == null)
					continue;
				if (caseMap.containsKey(testCase.getId()))
					throw new RuntimeException("存在多个" + testCase.getId() + "，请检查id配置");
				else
					caseMap.put(testCase.getId(), testCase);
			}
			testUnit = new TestUnit();
			testUnit.setId(doc.getDocumentElement().getAttribute("id"));
			testUnit.setName(doc.getDocumentElement().getAttribute("name"));
//			log.info("<-----------------------------------------------------------【"+doc.getDocumentElement().getAttribute("name")+"】----------------------------------------------------------->");
			testUnit.setCaseMap(caseMap);
			deleteDirectory(testUnit.getName());
		} catch (Exception e) {
//			e.printStackTrace();
			log.error("",e);
		}
		return testUnit;
	}

	/**
	 * <br>将case元素解析为TestCase实例，也就是一个TestStep的集合</br>
	 *
	 * @param element 一个case元素
	 * @return
	 */
	private static TestCase parseTestCase(Element element) {
		if (element == null)
			return null;
		
		NamedNodeMap attrs = element.getAttributes();
		//根据case的属性实例化TestCase，并注入相应字段值
		TestCase testCase = initByAttributes(attrs, new TestCase());
		
		NodeList stepNodes = element.getElementsByTagName("step");
		int len = stepNodes.getLength();
		List<TestStep> stepList = new ArrayList<TestStep>(len);
		
		Element stepNode;
		//逐个解析case元素的step子元素
		for (int i = 0; i < len; i++) {
			stepNode = (Element) stepNodes.item(i);
			stepList.add(parseTestStep(stepNode));
		}
		testCase.setSteps(stepList);
		
		return testCase;
	}

	/**
	 * <br>解析step元素为TestStep实例</br>
	 *
	 * @param element
	 * @return
	 */
	private static TestStep parseTestStep(Element element) {
		if (element == null)
			return null;
		
		TestStep testStep = initByAttributes(element.getAttributes(), new TestStep());
		testStep.setAndroidDriver(driver);
		
		return testStep;
	}

	/**
	 * <br>根据xml文件中的元素属性为对象的对应字段注入值</br>
	 *
	 * @param attrs
	 * @param t 需要实例化并注入字段值的对象
	 * @return
	 */
	private static <T> T initByAttributes(NamedNodeMap attrs, T t) {
		if (attrs == null || attrs.getLength() == 0)
			return t;
		
		int len = attrs.getLength();
		Node attr;
		String name, value;
		
		//通过反射逐个注入字段值
		for (int i = 0; i < len; i++) {
			attr = attrs.item(i);
			if (attr == null) continue;
			
			name = attr.getNodeName();
			value = attr.getNodeValue();
			//通过反射为对象的对应字段注入值
			initByReflect(name, value, t);
		}
		return t;
	}

	/**
	 * <br>通过反射为对象的对应字段注入值</br>
	 *
	 * @param name
	 * @param value
	 * @param t
	 * @return
	 */
	private static <T> T initByReflect(String name, String value, T t) {
		if (t == null)
			throw new RuntimeException("null object");
		if (name == null || "".equals(name))
			throw new RuntimeException("empty name");
		
		Class<?> clazz = t.getClass();
		Method setter, getter;

		try {
			String methodStr = name.substring(0, 1).toUpperCase() + name.substring(1);
			
			//如果名称是cancel，则调用isCancel()方法，主要是为了语义上的直观
			getter = clazz.getMethod(("cancel".equals(name) ? "is" : "get") + methodStr, new Class<?>[] {});
			setter = clazz.getMethod("set" + methodStr, getter.getReturnType());
			
			if ("action".equals(name))
				//根据StepAction类中的map来获取名称对应的StepAction（枚举）实例
				setter.invoke(t, StepAction.action(value));
			else if ("cancel".equals(name))
				setter.invoke(t, "true".equals(value) ? true : false);
			else if("details".equals(name))
				setter.invoke(t,parseDetail(value));
			else
				setter.invoke(t, value);
		} catch (Exception e) {
			log.info("对象反射初始化失败");
//			e.printStackTrace();
			log.error("",e);
		}
		return t;
	}
	
	/**
	 * <br>解析行为的细节描述为map</br>
	 *
	 * @author    102051
	 * @date      2017年7月28日 上午11:01:14
	 * @param detail
	 * @return
	 */
	public static Map<String,String> parseDetail(String detail){
		HashMap<String,String> map = new HashMap<>();
		String[] strarr = detail.split(";");
		
		for(String str : strarr){
			map.put(str.split(":")[0], str.split(":")[1]);
		}
		return map;
	}

    /**
     * <br>删除当天的BUG截图目录</br>
     *
     * @param command
     */
    public static void deleteDirectory(String UnitName) throws IOException{
        String Data = DateUtil.getDateFormat("yyyy年MM月dd日");
        String AppointDir = System.getProperty("user.dir")+"/TestOutput/ExtentReport/BugScreenshot/"+Data+"/"+UnitName+"/";
        FileUtils.deleteDirectory(new File(AppointDir));
    }
    
	/**
	 * <br>执行完毕,退出App程序</br>
	 */
	public static void QuitApp() {
		try {
			driver.quit();
			log.info("『测试结束』开始执行: <结束进程,退出App>");
		} catch (Exception e) {
//			e.printStackTrace();
			log.error("",e);
		}
	}
	
	public static void main(String[] arg) throws Exception{
	    TestUnit testunit= parse(new File("src/test/java/TestCaseXml/YHT_A4_Account_Recharge_Process.xml"));
	    RunUnitService runService = new RunUnitService(testunit);
//	    runService.runCase("case1");
	    runService.runCase("case2");
//        screenShot("银户通手机号登录流程","case1","点击同意按钮");
	}
}