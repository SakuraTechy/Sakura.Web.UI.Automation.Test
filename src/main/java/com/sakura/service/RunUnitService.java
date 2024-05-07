package com.sakura.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.sakura.base.StepAction;
import com.sakura.base.TestCase;
import com.sakura.base.TestStep;
import com.sakura.base.TestUnit;

/**
 * <br>TODO(描述该类的作用)</br>
 *
 * @date    2017年7月27日 下午5:50:09
 * @version 1.0
 * @since   1.0
 */
public class RunUnitService {

    Logger log = Logger.getLogger(RunUnitService.class);
    
    public static TestUnit testUnit;
    public static TestCase testCase; 
    public static SoftAssert softAssert = new SoftAssert();

    public static JSONObject Json = new JSONObject(true);

    public static JSONArray Units = new JSONArray();
    public static JSONObject Unit = new JSONObject(true);

    public static JSONArray Cases = new JSONArray();
    public static JSONObject Case = new JSONObject(true);

    public static JSONArray Steps = new JSONArray();
    public static JSONObject Step = new JSONObject(true);

    static int UnitsIndex = 0;
    static int CaseIndex = 0;

	public RunUnitService(TestUnit testUnit) {
        Unit = new JSONObject(true);
        Cases = new JSONArray();
        CaseIndex = 0;
        RunUnitService.testUnit = testUnit;
    }

    /**
     * <br>
     * 根据索引从TestUnit中获取测试用例</br>
     *
     * @param index
     * @return
     */
    public TestCase getCase(int index) {
        int n = 0;
        if (testUnit.getCaseMap() == null)
            return null;

        int size = testUnit.getCaseMap().size();
        if (index < 0 || (index > 0 && index >= size))
            throw new IndexOutOfBoundsException("index=" + index + ", size=" + size);

        // 遍历map内部Entry集合
        for (Map.Entry<String, TestCase> e : testUnit.getCaseMap().entrySet()) {
            if (n++ == index)
                return e.getValue();
        }

        return null;
    }

    /**
     * <br>
     * 根据用例名，即case元素中的id元素值，从TestUnit中获取测试用例</br>
     *
     * @param id
     * @return
     */
    public TestCase getCase(String id) {
        return testUnit.getCaseMap() == null ? null : testUnit.getCaseMap().get(id);
    }

    /**
     * <br>
     * 执行测试用例</br>
     *
     * @param id
     * @throws Exception
     */
    public void runCase(String id) throws Exception {
    	log.info("<----------------------------------------【"+ testUnit.getId()+"，"+testUnit.getName() +"】--------------------------------------->");
        softAssert = new SoftAssert();
        Case = new JSONObject(true);
        Steps = new JSONArray();
        testCase = getCase(id);
        testCase.setId(testCase.getId());
        testCase.setName(testCase.getName());
        log.info("<---"+testCase.getId()+"，"+testCase.getName()+"--->");
        List<TestStep> steps = testCase.getSteps();
        int StepIndex = 1;
        for (TestStep step : steps) {
            Step = new JSONObject(true);
            step.setId(String.valueOf(StepIndex));
            if (step.isCancel())
                continue;
            try {
                // log.info("开始执行: "+step.toString());
                StepAction action = step.getAction();
                // 如果StepAction实例有handler字段，则调用handler中的方法，否则直接调用run方法
                Class<?> clazz = action.handler();
                if (clazz != null) {
                    String key = step.getAction().key();
                    Method m = clazz.getDeclaredMethod(getMethodName(key), new Class<?>[] {TestStep.class});
                    m.invoke(clazz.newInstance(), step);
                } else {
                    action.run(step);
                }
            }  catch (Exception e) {
                log.info("『发现问题』执行异常: " + "<" + step.getId() + "." + step.getName() + ">");
                //e.printStackTrace();
                log.error("",e);
//                Steps.add(StepIndex - 1, Step);
                Thread.sleep(500);
                continue;
            } finally {
            	Steps.add(StepIndex - 1, Step);
                StepIndex++;
            }
        }
        Case.put("id", id);
        Case.put("name", testCase.getName());
        Case.put("steps", Steps);
        Cases.add(CaseIndex, Case);
        CaseIndex = CaseIndex + 1;
        TestReportRemarks(testCase.getName());
        softAssert.assertAll();
    }

    /**
     * <br>
     * 根据step元素的值解析出对应的方法名</br>
     * 作用是将"-"后面的第一个字母转为大写，并且去掉“-”
     *
     * @param actionKey
     * @return
     */
    private String getMethodName(String actionKey) {
        if (actionKey == null || "".equals(actionKey))
            throw new RuntimeException("empty action key");

        char[] arr = actionKey.toCharArray();
        char prevChar = '\0';
        StringBuilder sb = new StringBuilder();
        char splitChar = '-';

        for (char c : arr) {
            if (c == splitChar) {
                prevChar = c;
                continue;
            }
            if (prevChar == splitChar) {
                sb.append(Character.toUpperCase(c));
            } else {
                sb.append(c);
            }
            prevChar = c;
        }

        return sb.toString();
    }

    /**
     * <br>
     * 执行完毕，退出App程序</br>
     */
    public void closeApp() {
        AndroidXmlParseService.QuitApp();
    }

    /**
     * 
     * 执行完毕，添加Unit
     * 
     * @throws JSONException
     */
    public void setUnit(Boolean ...State) {
        if(State.length>0){
            UnitsIndex = 0;
        }
        try {
            Unit.put("id", testUnit.getId());
            Unit.put("name", testCase.getName());
            Unit.put("cases", Cases);
            Units.add(UnitsIndex, Unit);
            UnitsIndex = UnitsIndex+1;
        } catch (JSONException e) {
            //e.printStackTrace();
            log.error("",e);
        }
    }

    /**
     * <br>
     * 执行完毕，关闭浏览器</br>
     */
    public void closeBrowser() {
        WebXmlParseService.QuitBrowser();
    }

    /**
     * <br>
     * 标记备注，一般展示在测试报告中</br>
     *
     * @param RemarksName
     */
    public void TestReportRemarks(String RemarksName) {
        Reporter.log(RemarksName);
    }

    /**
     * <br>
     * 标记备注，一般展示在测试报告中</br>
     *
     * @param command
     */
    public void AndroidAdb(String command) throws IOException {
        Runtime.getRuntime().exec(command);
    }
    
    public static void main(String[] args) throws Exception {

    }
}