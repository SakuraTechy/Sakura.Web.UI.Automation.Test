package com.sakura.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.log4j.Logger;

@SuppressWarnings({ "unused","unchecked", "rawtypes" })
public class StringUtil {

	static Logger log = Logger.getLogger(StringUtil.class);

	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str)) {
			return true;
		}
		return false;
	}

	public static boolean isNoEmpty(String str) {
		if (null == str || "".equals(str)) {
			return false;
		}
		return true;
	}

	public static boolean isNoEmpty1(String str) {
		if ("".equals(str)) {
			return false;
		} else if (null != str) {
			return true;
		}
		return false;
	}

	public static boolean isEqual(String str1, String str2) {
		if (str1 == str2 || str1.equals(str2)) {
			return true;
		}
		return false;
	}

	public static boolean isNotEqual(String str1, String str2) {
		if (str1 == str2 || str1.equals(str2)) {
			return false;
		}
		return true;
	}

	public static boolean isBlank(String string) {
		if (string == null || string.length() == 0)
			return true;

		int l = string.length();
		for (int i = 0; i < l; i++) {
			if (!StringUtil.isWhitespace(string.codePointAt(i)))
				return false;
		}
		return true;
	}

	public static boolean isWhitespace(int c) {
		return c == ' ' || c == '\t' || c == '\n' || c == '\f' || c == '\r';
	}

	/**
	 * String结果对比，得到OK，NG
	 * 
	 * @param expectedResult
	 * @param actualResult
	 * @return
	 */
	public static String assertResult(String expectedResult, String actualResult) {
		String result;
		if (expectedResult.equals(actualResult))
			result = "OK";
		else
			result = "NG";
		return result;
	}

	/**
	 * 获取.后的内容
	 * 
	 * @param s
	 * @return
	 */
	public static String getSubString(String str) {
		int one = str.lastIndexOf(".");
		String Suffix = str.substring((one + 1), str.length());
//        log.info(Suffix);
		return Suffix;
	}

	/**
	 * 使用正则表达式去掉多余的.与0 100.00 => 100
	 * 
	 * @param s
	 * @return
	 */
	public static String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			// 去掉多余的 0
			s = s.replaceAll("0+?$", "");
			// 如果最后一位是.则去掉
			s = s.replaceAll("[.]$", "");
		}
		return s;
	}

	/**
	 * 获取str最后一位 case1 => 1
	 * 
	 * @param s
	 * @return
	 */
	public static String subLastOne(String str) {
//        String str = "case1";
//        int key = Integer.parseInt(str.substring(str.length()-1,str.length()));
//        log.info(key);
		return str.substring(str.length() - 1, str.length());
	}

	public static String getRegex(String smsBody,String reg) {
		StringBuffer code = new StringBuffer();
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(smsBody);
		while(matcher.find()){
            String group = matcher.group();
            code.append(group);
        }
//		if (matcher.find()) {
//			return matcher.group();
////			return matcher.replaceAll("").trim();
//		}
		return code.toString();
	}
	
	public static List getRegexs(String smsBody,String reg) {
		List list = new ArrayList();
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(smsBody);
		while (matcher.find()) {
			list.add(matcher.group(1));
        }
		return list;
	}
	
	//字符串截取
    public static String regCompFront(String str,String value) {
    	// 替换中文
        String re = "[\\u4e00-\\u9fa5]+";
        //截取波|第前面数字
        String reg = "(.*?["+value+"|测试])";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        if (m.find()) {
        	return m.group().replaceAll(value, "");
        }
        return null;
    }
    
    //字符串截取
    public static String regCompBack(String str,String value) {
    	// 替换中文
        String re = "[\\u4e00-\\u9fa5]+";
        //截取波|第后面内容
        String reg = ".*?(["+value+"|测试]).*?";
        return  str.replaceFirst(reg, "$1").replaceAll(value, "");
    }
    
    public static String regComp(String str) {
        String num = "";
        // 替换中文
        String reg = "[\\u4e00-\\u9fa5]+";
        //截取λ|入后面数字
        String comp1 = "[\\s\\S]*([λ|入]\\d*)[\\s\\S]*";
        //截取波|第前面数字
        String comp2 = "(\\d+[波|第])";
        if (str.matches(comp1)) {
            num = str.replaceFirst(comp1, "$1").replaceAll(reg, "").replace("λ", "");
        } else {
            Pattern p = Pattern.compile(comp2);
            Matcher m = p.matcher(str);
            if (m.find()) {
                num = m.group(1).replaceAll(reg, "");
            }
        }
        String str1 = "石家庄(至郑州)架1-2-23-OTU3S-1(OTU3S 1波).OCH)";
        String str3 = " 北京东四1-1-4D-OTU3S-1(OTU3S 100第三个).OCH";
        String str2 = " 北京东四1-1-4D-OTU3S-1(OTU3S 入12).OCH";
        String str4 = " 北京东四1-1-4D-OTU3S-1(OTU3S λ12334).OCH";
        return num;
    }

	private void getPhoneNum(String smsBody) {
		Pattern pattern = Pattern.compile("(13|14|15|18)\\d{9}");
		Matcher matcher = pattern.matcher(smsBody);
		while (matcher.find()) {
			log.info(matcher.group());
		}
	}

	private static String getNumber(String smsBody,String reg) {
		Pattern pattern = Pattern.compile("\\d{1}");
		Matcher matcher = pattern.matcher(smsBody);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}
	
	//去除所有空格
    public static String replaceAllBlank(String str) {
        String s = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            /*\n 回车(\u000a)
            \t 水平制表符(\u0009)
            \s 空格(\u0008)
            \r 换行(\u000d)*/
            Matcher m = p.matcher(str);
            s = m.replaceAll("");
        }
        return s;
    }
    
    public static String replaceAllBlank(String str,String reg,String rep) {
        String s = "";
        if (str!=null) {
            Pattern p = Pattern.compile(reg);
            /*\n 回车(\u000a)
            \t 水平制表符(\u0009)
            \s 空格(\u0008)
            \r 换行(\u000d)*/
            Matcher m = p.matcher(str);
            s = m.replaceAll(rep);
        }
        return s;
    }
    
    //去除所有空格，留下一个
    public static String replaceBlankLeaveOne(String str) {
        String s = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s{2,}|\t|\r|\n");
            Matcher m = p.matcher(str);
            s = m.replaceAll(" ");
        }
        return s;
    }

  //去除所有空格，留下一个
    public static String ScriptEngine(String script) {
    	String str = "";
    	try {
//          StringBuilder str = new StringBuilder();
//          str.append("(");
//          str.append("1.53");
//          str.append("+2.53");
//          str.append(")");
//          str.append("*3.3");
//          System.out.println(str.toString());
//          ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
//          System.out.println(jse.eval(str.toString()));
			
          ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
          str = jse.eval(SeleniumUtil.parseStringHasEls(script)).toString();
      }
      catch (Exception e){
          e.printStackTrace();
      }
    	return str;
    }

    //根据小数点后第一位=0，则取整，大于0，则保留一位小数，例如11.0333=11，11.2333=11.2
    public static String CustomRules(String str) {
//        String str = "11.0333";
    	String result = "";
    	
        // 创建正则表达式
        Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)?$");
        // 使用正则表达式匹配字符串
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            // 匹配成功，获取小数点后的位数
            int decimals = 0;
            if (str.contains(".")) {
                decimals = str.length() - str.indexOf(".") - 1;
            }
            // 判断小数位的第一位
            if (decimals > 0) {
                String firstDecimal = str.substring(str.indexOf(".") + 1, str.indexOf(".") + 2);
                if (Integer.parseInt(firstDecimal) > 0) {
                    // 保留一位小数
                	str = str.substring(0, str.indexOf(".") + 2);
//                    System.out.println(str);
                } else {
                    // 取整
                	str = str.substring(0, str.indexOf("."));
//                    System.out.println(str);
                }
            } else {
                // 字符串没有小数部分，直接取整
                System.out.println(str);
            }
        } else {
            // 字符串不是合法的数字格式
            System.out.println("不是合法的数字格式");
        }
		return str;
    }
    
    /**String 四舍五入 */
    public static String formatDouble(double d,String format) {
//        DecimalFormat df = new DecimalFormat("#.00");
    	DecimalFormat df = new DecimalFormat(format);
        return df.format(d);
    }
    
    public static String formatDouble1(double d,String format) {
    	double pi=3.1415927;//圆周率

    	//取一位整数

    	System.out.println(new DecimalFormat("0").format(pi));//3

    	//取一位整数和两位小数  

    	System.out.println(new DecimalFormat("0.00").format(pi));//3.14

    	//取两位整数和三位小数，整数不足部分以0填补。  

    	System.out.println(new DecimalFormat("00.000").format(pi));//03.142  

    	//取所有整数部分  

    	System.out.println(new DecimalFormat("#").format(pi));//3  

    	//以百分比方式计数，并取两位小数  

    	System.out.println(new DecimalFormat("#.##%").format(pi));//314.16%  

    	long c=299792458;//光速  

    	//显示为科学计数法，并取五位小数  

    	System.out.println(new DecimalFormat("#.#####E0").format(c));//2.99792E8  

    	//显示为两位整数的科学计数法，并取四位小数  

    	System.out.println(new DecimalFormat("00.####E0").format(c));//29.9792E7  

    	//每三位以逗号进行分隔。  

    	System.out.println(new DecimalFormat(",###").format(c));//299,792,458  

    	//将格式嵌入文本  

    	System.out.println(new DecimalFormat("光速大小为每秒,###米").format(c)); //光速大小为每秒299,792,458米
      return new DecimalFormat(format).format(d);
  }
    
    /*

    * 字符不包含特定字符串的表达式

    */

    public static boolean StringMatchRule(String souce, String regex) {

    boolean result = false;

    if (regex != null && souce != null) {

    result = Pattern.matches(regex, souce);

    }

    return result;

    }
    
	public static void main1(String[] arg) throws Exception {
//        log.info(isEqual("f",""));
		log.info(getRegex("共 402706 条","(?<=共)(.*?)(?=条)"));
		log.info(getRegex("用户名：test；初始密码：An3{wurz","(?<=初始密码：)[^；]*"));
		log.info(getRegex("[402706]","(?<=\\[)(.*?)(?=])"));
		log.info(getRegex("[402706]","\\[(.*?)]"));
		if(getRegex("共 402706 条","(?<=共\\s)(.*?)(?=\\s条)").equals("402706")) {
			log.info("11");
		}
		log.info(getRegex("2022-08-24 18:40:49","(\\d{4})-(\\d{1,2})-(\\d{1,2})").replace("-0", "-"));
		log.info(getRegex("2022-08-24 18:40:49","([0-9]{4})-(\\d{1,2})-(\\d{1,2})").replace("-0", "-"));
		log.info(getRegex("测试用户对试用户对试用户","(^.*?[用])").replace("用", ""));
		log.info(getRegex("系统正在升级,请勿断电或强制退出，300s后重新登录","(^.*?[，])").replace("，", ""));
		log.info(getRegex("产测试用户对试用户对试用户","([试].*?)$"));
		log.info(getRegex("截至今日: 2022-09-13 16:12:59。产生风险:高风险0个 中风险0个 低风险0个","([。].*?)$").replace("。", ""));
		
		log.info(regCompFront("石家庄(至郑州)架1-2-23-OTU3S-1(OTU3S 1波2测试).OCH)","波"));
		log.info(regCompFront("北京东四1-1-4D-OTU3S-1(OTU3S λ12334).OC","λ"));
		log.info(regCompFront("保护对象名称${Local_IP}s","1"));
		log.info(regCompBack("保护对象名称${Local_IP}s","保护对象名称"));
		log.info(replaceAllBlank("保护对象名称${Local_IP}s","(.*?[称])",""));
		
		log.info(getRegex("用户对orcl数据库[测试]表所有字段进行了查询操作；操作发生在：2022-08-31 17:15:55，使用的电脑IP为：172.18.1.118，电脑名称为：ANKKI014","\\[(.*?)]"));
		log.info(getRegex("本地IP用户对orcl数据库[测试]表所有字段进行了查询操作；操作发生在：2022-08-31 17:15:55，使用的电脑IP为：172.18.1.118，电脑名称为：ANKKI014","(^.*?[用])").replace("用", ""));
		log.info(regCompFront("本地IP用户对orcl数据库[测试]表所有字段进行了查询操作；操作发生在：2022-09-01 09:41:37，使用的电脑IP为：172.18.1.118，电脑名称为：ANKKI014","用"));
		
		log.info(getRegex("172.18.1.118 : 26 (>99%)","(?<= : )(.*?)(?= \\(>)"));
		log.info(getRegex("系统平台监控墙432","[\\d]"));
		log.info(getRegex("系统平台监控墙432","[0-9]"));
		log.info(getRegex("系统平台监控墙432","[\u4e00-\u9fa5]"));
		log.info(formatDouble(31/2,"0"));
		
		log.info(getRegex("用户对orcl数据库[ALL_OBJECTS]表所有字段进行了查询操作；操作的条件为：[OBJECT_TYPE]等于[PACKAGE]；且[所有者字段翻译]等于[var_1]；操作发生在：2022-11-10 09:34:51，使用的电脑IP为：172.18.1.26，电脑物理地址（MAC地址）为：C4:FF:1F:F3:9D:D0，电脑名称为：DESKTOP-3TR91IC","(?<=且\\[)(.*?)(?=\\]等)"));
//		String a = "402,706";
//		Map<String,String> replace = new LinkedHashMap<String,String>();
//		replace.put("key", ",");
//		replace.put("value", "");
//		log.info(a.replace(replace.get("key"), replace.get("value")));
		
		String regex_containStr = "^(.*(SaveDefinition)).*$";
		String regex_containStr1 = "^(.*(20117-04-17 00:00:00|20117-04-18 23:59:59)).*$";
		String regex_containStr2 = "^(.*(1</font></b>)).*$";
		String regex_notcontainStr = "^(?!.*(转发)).*$";// 不包含特定字符串的表达式
	    System.out.println(StringMatchRule("%Studio.ClassMgr SaveDefinition 翭26:RegInterface.WebRegService1:K1:126:RegInterface.WebRegService2:230:2:461:12:6017:%RegisteredObject2:6318:64764,38805.16", regex_containStr));
	    System.out.println(StringMatchRule("发药时间 Order By 发药时间,<b><font color='red'>科室</font></b>;(2017-04-17 00:00:00|2017-04-18 23:59:59)", regex_containStr1));
	    System.out.println(StringMatchRule("<b><font color='red'>select</font></b> <b><font color='red'>11111111111</font></b>", regex_containStr2));
	    System.out.println(StringMatchRule("这个邮件 是转发的！", regex_notcontainStr));
	    
	    log.info(replaceAllBlank("大小1：64              块：8          IO 块：4096","(?i)(?<=大小：)[^块]*",""));
	    log.info(getRegex("大小：64              块：8          IO 块：4096","(\\s)|(?i)(?<=大小：)[^块]*"));
	    log.info("["+replaceAllBlank(getRegex("大小：64              块：8          IO 块：4096","(?i)(?<=大小：)[^块]*"),"\\s|\n","")+"]");
//	    final String regex = "(?i)(?<=isemployee:')[^']*";
//        final String string = "id:'1234',Salary:'200000',Year:'2018',IsEmployee:'Yes'";
        final String regex = "(?i)(?<=大小：)[^']*";
        final String string = "大小：64              块：8          IO 块：4096";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));
            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println("Group " + i + ": " + matcher.group(i));
            }
        }
	}
	
	public static void main(String[] arg) throws Exception {
//		log.info(getRegex("172.19.4.56","\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\."));
//		log.info(getRegexs("172.19.4.123","(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})\\.\\d{1,3}"));
		log.info(replaceAllBlank("\n52399108+104806400+","(\n|.$)",""));
		log.info(CustomRules("11.0333"));
	}
}
