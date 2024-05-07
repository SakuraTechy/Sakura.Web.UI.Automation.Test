package com.sakura.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

/** 
* @author 刘智King
* @date 2020年9月22日 下午3:26:39
*/
public class JsonUtil {

    static Logger log = Logger.getLogger(JsonUtil.class);
    
    /**
     * 对节点进行解析
     * 
     * @author mengfeiyang
     * @param obj
     * @param node
     * @return
     */
    private static JSONObject getObj(JSONObject obj, String node) {
        try {
            if (node.contains("[")) {
                JSONArray arr = obj.getJSONArray(node.substring(0,node.indexOf("[")));
                for (int i = 0; i < arr.size(); i++) {
                    if ((i + "").equals(node.substring(node.indexOf("["),node.indexOf("]")).replace("[", ""))) {
                        return arr.getJSONObject(i);
                    }
                }
            } else {
                return obj.getJSONObject(node);
            }
        } catch (Exception e) {
            return obj;
        }
        return null;
    }
    
    /**
     * 获取节点
     * @author mengfeiyang
     * @param jsonContent
     * @param jsonPath
     * @return
     * @throws Exception
     */
    public static JSONObject getNode(String jsonContent, String jsonPath) throws Exception {
        String[] nodes = jsonPath.split("\\.");
        JSONObject obj = JSONObject.parseObject(jsonContent, Feature.OrderedField);
        for (int i = 0; i < nodes.length; i++) {
            if (obj != null) {
                obj = getObj(obj, nodes[i]);
            }
        }
        return obj;
    }
    
    /**
     * 获取节点值
     * @author mengfeiyang
     * @param jsonContent
     * @param jsonPath
     * @return
     * @throws Exception
     */
    public static String getNodeValue(String jsonContent, String jsonPath) throws Exception {
        String[] nodes = jsonPath.split("\\.");
        JSONObject obj = JSONObject.parseObject(jsonContent, Feature.OrderedField);
        for (int i = 0; i < nodes.length; i++) {
            if (obj != null) {
                obj = getObj(obj, nodes[i]);
            }
            if ((i + 1) == nodes.length) {
                try{
                    return obj.getString(nodes[i]);
                }catch(Exception e){
                    //e.printStackTrace();
//                    log.error("获取json节点失败！",e);
                    return "JSONException:"+e.getMessage()+",NodeString:"+obj.toString();
                }
            }
        }
        return null;
    }
    
    /**
     * 按指定key排序JSONObject对象数组
     * 
     * @param array JSONObject对象数组
     * @param key 指定排序key
     */
    public static void sort(JSONObject[] array, String key) {
        Arrays.sort(array, new Comparator<JSONObject>() {

            @Override
            public int compare(JSONObject a, JSONObject b) {
                return JsonUtil.compare(a, b, key);
            }
        });
    }

    /**
     * 按指定key排序JSONObject对象集合
     * 
     * @param list JSONObject对象集合
     * @param key 指定排序key
     */
    public static void sort(List<JSONObject> list, String key) {
        Collections.sort(list, new Comparator<JSONObject>() {

            @Override
            public int compare(JSONObject a, JSONObject b) {
                return JsonUtil.compare(a, b, key);
            }
        });
    }

    /**
     * 按指定key降序排序JSONObject对象数组
     * 
     * @param array JSONObject对象数组
     * @param key 指定排序key
     */
    public static void sortDesc(JSONObject[] array, String key) {
        Arrays.sort(array, new Comparator<JSONObject>() {

            @Override
            public int compare(JSONObject a, JSONObject b) {
                return -1 * JsonUtil.compare(a, b, key);
            }
        });
    }

    /**
     * 按指定key降序排序JSONObject对象集合
     * 
     * @param list JSONObject对象集合
     * @param key 指定排序key
     */
    public static void sortDesc(List<JSONObject> list, String key) {
        Collections.sort(list, new Comparator<JSONObject>() {

            @Override
            public int compare(JSONObject a, JSONObject b) {
                return -1 * JsonUtil.compare(a, b, key);
            }
        });
    }

    /**
     * 按指定key比较两个JSONObject对象大小
     * 
     * @param a 第一个JSONObject对象
     * @param b 第二个JSONObject对象
     * @param key 指定进行比较的key
     * @return
     *         <ul>
     *             <li>如果a==b,返回0</li>
     *             <li>如果a>b,返回1</li>
     *             <li>如果a<b,返回-1</li>
     *         </ul>
     */
    public static int compare(JSONObject a, JSONObject b, String key) {
        Object va = a.get(key);
        Object vb = b.get(key);

        if (null == va && null == vb) {
            return 0;
        }

        if (null == va) {
            return -1;
        }

        if (null == vb) {
            return 1;
        }

        if (va.equals(vb)) {
            return 0;
        }

        if (va instanceof Number && vb instanceof Number) {

            /* 取double值相减，兼容整数 */
            if (a.getDoubleValue(key) - b.getDoubleValue(key) > 0) {
                return 1;
            }

            else {
                return -1;
            }
        }

        return a.getString(key).compareToIgnoreCase(b.getString(key));
    }
    
    public static void main(String[] args) throws Exception {
        JSONObject Step = new JSONObject(true);
        Step.put("desc", "1");
        Step.put("picture", "2");

        JSONObject Step1 = new JSONObject(true);
        Step1.put("desc", "3");
        Step1.put("picture", "4");
        
        JSONArray Steps = new JSONArray();
        Steps.add(0, Step);
        Steps.add(1, Step1);
        
        JSONObject Step2 = new JSONObject(true);
        Step2.put("desc", "5");
        Step2.put("picture", "6");

        JSONObject Step3 = new JSONObject(true);
        Step3.put("desc", "7");
        Step3.put("picture", "8");
        
        JSONArray Steps1 = new JSONArray();
        Steps1.add(0, Step2);
        Steps1.add(1, Step3);
        
        JSONObject Case = new JSONObject(true);
        Case.put("id", "case1");
        Case.put("name", "case1name");
        Case.put("steps", Steps);

        JSONObject Case1 = new JSONObject(true);
        Case1.put("id", "case2");
        Case1.put("name", "case2name");
        Case1.put("steps", Steps1);
        
        JSONArray Cases = new JSONArray();
        Cases.add(0, Case);
        Cases.add(1, Case1);
        
        JSONObject Unit = new JSONObject(true);
        Unit.put("id", "YHT_A1_Login_Process");
        Unit.put("name", "银户通-手机号登录流程");
        Unit.put("cases", Cases);
        
        JSONObject Unit1 = new JSONObject(true);
        Unit1.put("id", "YHT_A2_Login_Process");
        Unit1.put("name", "银户通-实名认证流程");
        Unit1.put("cases", Cases);
        
        JSONArray Units = new JSONArray();
        Units.add(0, Unit);
        Units.add(1, Unit1);
        
        JSONObject json = new JSONObject(true);
        json.put("name", "name");
        json.put("units", Units);

        String jsonContent = JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);;
        log.info(jsonContent);

        String units_id = getNodeValue(jsonContent, "units[0].id");
        String units_name = getNodeValue(jsonContent, "units[0].name");
        log.info("units_id: "+units_id);
        log.info("units_name: "+units_name);
        
        String cases_id = getNodeValue(jsonContent, "units[0].cases[0].id");
        String cases_name = getNodeValue(jsonContent, "units[0].cases[0].name");
        log.info("cases_id: "+cases_id);
        log.info("cases_name: "+cases_name);
        
        String steps_id = getNodeValue(jsonContent, "units[0].cases[0].steps[0].desc");
        String steps_name = getNodeValue(jsonContent, "units[0].cases[0].steps[0].picture");
        log.info("steps_desc: "+steps_id);
        log.info("steps_picture: "+steps_name);
        
//        JSONObject val4 = getNode(jsonContent, "units[0].cases[0].steps");
//        JSONObject val5 = getNode(jsonContent, "units[0].cases[0].steps[0]");
//        log.info(val4);
//        log.info(val5);

//        JSONArray casesArray = getNode(jsonContent, "units[0]").getJSONArray("cases");
//        for (int i = 0; i < casesArray.size(); i++) {
//            JSONArray stepsArray = casesArray.getJSONObject(i).getJSONArray("steps");
//            for (int a = 0; a < stepsArray.size(); a++) {
//                log.info("desc ：" + stepsArray.getJSONObject(a).get("desc"));
//                log.info("picture ：" + stepsArray.getJSONObject(a).get("picture"));
//                log.info("abc ：" + stepsArray.getJSONObject(a).get("abc"));
//            }
//        }
        
        JSONArray jsonArray = getNode(jsonContent, "units[0].cases[0]").getJSONArray("steps");
        for(int i=0; i<jsonArray.size(); i++){
            log.info("desc ：" + jsonArray.getJSONObject(i).get("desc"));
            log.info("picture ：" + jsonArray.getJSONObject(i).get("picture"));
        }
    }

}
