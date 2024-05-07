package com.sakura.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

/**
 * 数据库操作类
 */
@SuppressWarnings("unused")
public class DBHelper {

    static Logger log = Logger.getLogger(DBHelper.class);

    private static final String Oracle_FDRIVER = ConfigUtil.getProperty("Oracle.jdbc.Fdriver", ConstantsUtil.CONFIG_JDBC);

    private static final String Oracle_FURL = ConfigUtil.getProperty("Oracle.jdbc.Furl", ConstantsUtil.CONFIG_JDBC);

    private static final String Oracle_FUSERNAME = ConfigUtil.getProperty("Oracle.jdbc.Fusername", ConstantsUtil.CONFIG_JDBC);

    private static final String Oracle_FPASSWORD = ConfigUtil.getProperty("Oracle.jdbc.Fpassword", ConstantsUtil.CONFIG_JDBC);

    private static final String Oracle_DDRIVER = ConfigUtil.getProperty("Oracle.jdbc.Ddriver", ConstantsUtil.CONFIG_JDBC);

    private static final String Oracle_DURL = ConfigUtil.getProperty("Oracle.jdbc.Durl", ConstantsUtil.CONFIG_JDBC);

    private static final String Oracle_DUSERNAME = ConfigUtil.getProperty("Oracle.jdbc.Dusername", ConstantsUtil.CONFIG_JDBC);

    private static final String Oracle_DPASSWORD = ConfigUtil.getProperty("Oracle.jdbc.Dpassword", ConstantsUtil.CONFIG_JDBC);

    private static final String Oracle_TDRIVER = ConfigUtil.getProperty("Oracle.jdbc.Tdriver", ConstantsUtil.CONFIG_JDBC);

    private static final String Oracle_TURL = ConfigUtil.getProperty("Oracle.jdbc.Turl", ConstantsUtil.CONFIG_JDBC);
    
    private static final int Oracle_TPort = Integer.valueOf(ConfigUtil.getProperty("Oracle.jdbc.Tport", ConstantsUtil.CONFIG_JDBC));

    private static final String Oracle_TUSERNAME = ConfigUtil.getProperty("Oracle.jdbc.Tusername", ConstantsUtil.CONFIG_JDBC);

    private static final String Oracle_TPASSWORD = ConfigUtil.getProperty("Oracle.jdbc.Tpassword", ConstantsUtil.CONFIG_JDBC);

    private static final String MySql_FDRIVER = ConfigUtil.getProperty("MySql.jdbc.Fdriver", ConstantsUtil.CONFIG_JDBC);

    private static final String MySql_FURL = ConfigUtil.getProperty("MySql.jdbc.Furl", ConstantsUtil.CONFIG_JDBC);

    private static final String MySql_FUSERNAME = ConfigUtil.getProperty("MySql.jdbc.Fusername", ConstantsUtil.CONFIG_JDBC);

    private static final String MySql_FPASSWORD = ConfigUtil.getProperty("MySql.jdbc.Fpassword", ConstantsUtil.CONFIG_JDBC);

    private static final String MySql_DDRIVER = ConfigUtil.getProperty("MySql.jdbc.Ddriver", ConstantsUtil.CONFIG_JDBC);

    private static final String MySql_DURL = ConfigUtil.getProperty("MySql.jdbc.Durl", ConstantsUtil.CONFIG_JDBC);

    private static final String MySql_DUSERNAME = ConfigUtil.getProperty("MySql.jdbc.Dusername", ConstantsUtil.CONFIG_JDBC);

    private static final String MySql_DPASSWORD = ConfigUtil.getProperty("MySql.jdbc.Dpassword", ConstantsUtil.CONFIG_JDBC);

    private static final String MySql_TDRIVER = ConfigUtil.getProperty("MySql.jdbc.Tdriver", ConstantsUtil.CONFIG_JDBC);

    private static final String MySql_TURL = ConfigUtil.getProperty("MySql.jdbc.Turl", ConstantsUtil.CONFIG_JDBC);
    
    private static final int MySql_TPort = Integer.valueOf(ConfigUtil.getProperty("MySql.jdbc.Tport", ConstantsUtil.CONFIG_JDBC));

    private static final String MySql_TUSERNAME = ConfigUtil.getProperty("MySql.jdbc.Tusername", ConstantsUtil.CONFIG_JDBC);

    private static final String MySql_TPASSWORD = ConfigUtil.getProperty("MySql.jdbc.Tpassword", ConstantsUtil.CONFIG_JDBC);

    private static final String MySqlSSH_FHost = ConfigUtil.getProperty("MySql.ssh.Fhost", ConstantsUtil.CONFIG_JDBC);

    private static final int MySqlSSH_FPort = Integer.valueOf(ConfigUtil.getProperty("MySql.ssh.Fport", ConstantsUtil.CONFIG_JDBC));

    private static final String MySqlSSH_FUser = ConfigUtil.getProperty("MySql.ssh.Fuser", ConstantsUtil.CONFIG_JDBC);

    private static final String MySqlSSH_FPassword = ConfigUtil.getProperty("MySql.ssh.Fpassword", ConstantsUtil.CONFIG_JDBC);

    private static final String MySqlSSH_FKeyFile = ConfigUtil.getProperty("MySql.ssh.FkeyFile", ConstantsUtil.CONFIG_JDBC);

    private static final int MySqlSSH_FLport = Integer.valueOf(ConfigUtil.getProperty("MySql.ssh.Flport", ConstantsUtil.CONFIG_JDBC));

    private static final String MySqlSSH_FRhost = ConfigUtil.getProperty("MySql.ssh.Frhost", ConstantsUtil.CONFIG_JDBC);

    private static final int MySqlSSH_FRport = Integer.valueOf(ConfigUtil.getProperty("MySql.ssh.Frport", ConstantsUtil.CONFIG_JDBC));

    private static final String MySqlSSH_FDRIVER = ConfigUtil.getProperty("MySql.jdbc.Fdriver", ConstantsUtil.CONFIG_JDBC);

    private static final String MySqlSSH_FURL = ConfigUtil.getProperty("MySql.jdbc.Furl", ConstantsUtil.CONFIG_JDBC);

    private static final String MySqlSSH_FUSERNAME = ConfigUtil.getProperty("MySql.jdbc.Fusername", ConstantsUtil.CONFIG_JDBC);

    private static final String MySqlSSH_FPASSWORD = ConfigUtil.getProperty("MySql.jdbc.Fpassword", ConstantsUtil.CONFIG_JDBC);

    private static final String MySqlSSH_DHost = ConfigUtil.getProperty("MySql.ssh.Dhost", ConstantsUtil.CONFIG_JDBC);

    private static final int MySqlSSH_DPort = Integer.valueOf(ConfigUtil.getProperty("MySql.ssh.Dport", ConstantsUtil.CONFIG_JDBC));

    private static final String MySqlSSH_DUser = ConfigUtil.getProperty("MySql.ssh.Duser", ConstantsUtil.CONFIG_JDBC);

    private static final String MySqlSSH_DPassword = ConfigUtil.getProperty("MySql.ssh.Dpassword", ConstantsUtil.CONFIG_JDBC);

    private static final String MySqlSSH_DKeyFile = ConfigUtil.getProperty("MySql.ssh.DkeyFile", ConstantsUtil.CONFIG_JDBC);

    private static final int MySqlSSH_DLport = Integer.valueOf(ConfigUtil.getProperty("MySql.ssh.Dlport", ConstantsUtil.CONFIG_JDBC));

    private static final String MySqlSSH_DRhost = ConfigUtil.getProperty("MySql.ssh.Drhost", ConstantsUtil.CONFIG_JDBC);

    private static final int MySqlSSH_DRport = Integer.valueOf(ConfigUtil.getProperty("MySql.ssh.Drport", ConstantsUtil.CONFIG_JDBC));

    private static final String MySqlSSH_DDRIVER = ConfigUtil.getProperty("MySql.jdbc.Ddriver", ConstantsUtil.CONFIG_JDBC);

    private static final String MySqlSSH_DURL = ConfigUtil.getProperty("MySql.jdbc.Durl", ConstantsUtil.CONFIG_JDBC);

    private static final String MySqlSSH_DUSERNAME = ConfigUtil.getProperty("MySql.jdbc.Dusername", ConstantsUtil.CONFIG_JDBC);

    private static final String MySqlSSH_DPASSWORD = ConfigUtil.getProperty("MySql.jdbc.Dpassword", ConstantsUtil.CONFIG_JDBC);

    private static final String MySqlSSH_THost = ConfigUtil.getProperty("MySql.ssh.Thost", ConstantsUtil.CONFIG_JDBC);

    private static final int MySqlSSH_TPort = Integer.valueOf(ConfigUtil.getProperty("MySql.ssh.Tport", ConstantsUtil.CONFIG_JDBC));

    private static final String MySqlSSH_TUser = ConfigUtil.getProperty("MySql.ssh.Tuser", ConstantsUtil.CONFIG_JDBC);

    private static final String MySqlSSH_TPassword = ConfigUtil.getProperty("MySql.ssh.Tpassword", ConstantsUtil.CONFIG_JDBC);

    private static final String MySqlSSH_TKeyFile = ConfigUtil.getProperty("MySql.ssh.TkeyFile", ConstantsUtil.CONFIG_JDBC);

    private static final int MySqlSSH_TLport = Integer.valueOf(ConfigUtil.getProperty("MySql.ssh.Tlport", ConstantsUtil.CONFIG_JDBC));

    private static final String MySqlSSH_TRhost = ConfigUtil.getProperty("MySql.ssh.Trhost", ConstantsUtil.CONFIG_JDBC);

    private static final int MySqlSSH_TRport = Integer.valueOf(ConfigUtil.getProperty("MySql.ssh.Trport", ConstantsUtil.CONFIG_JDBC));

    private static final String MySqlSSH_TDRIVER = ConfigUtil.getProperty("MySql.jdbc.ssh.Tdriver", ConstantsUtil.CONFIG_JDBC);

    private static final String MySqlSSH_TURL = ConfigUtil.getProperty("MySql.jdbc.ssh.Turl", ConstantsUtil.CONFIG_JDBC);

    private static final String MySqlSSH_TUSERNAME = ConfigUtil.getProperty("MySql.jdbc.ssh.Tusername", ConstantsUtil.CONFIG_JDBC);

    private static final String MySqlSSH_TPASSWORD = ConfigUtil.getProperty("MySql.jdbc.ssh.Tpassword", ConstantsUtil.CONFIG_JDBC);

    private static ResultSet rs;
    public static Statement sm;
    private static Connection con;
    private static CallableStatement cs;

    /**
     * 数据库插入
     * 
     * @param sql
     * @return
     */
    public static int insert(String DataType, String DataEnviron,String DataName,String Port, String DataBase, String Sql) {
        return executeUpdate(DataType, DataEnviron, DataName,Port, DataBase, Sql, OpType.INSERT);
    }

    /**
     * 数据库删除
     * 
     * @param sql
     * @return
     */
    public static int delete(String DataType, String DataEnviron, String DataName,String Port,String DataBase, String Sql) {
        return executeUpdate(DataType, DataEnviron, DataName,Port, DataBase, Sql, OpType.DELETE);
    }

    /**
     * 数据库修改
     * 
     * @param sql
     * @return
     */
    public static int update(String DataType, String DataEnviron, String DataName,String Port,String DataBase, String Sql) {
        return executeUpdate(DataType, DataEnviron, DataName,Port, DataBase, Sql, OpType.UPDATE);
    }

    /**
     * 执行数据库操作
     * 
     * @param sql
     * @param type
     * @return
     */
    private static int executeUpdate(String DataType, String DataEnviron, String DataName,String Port,String DataBase, String Sql, OpType Type) {
        checkConnection(DataType, DataEnviron, DataName,Port, DataBase);
        PreparedStatement ps = null;
        int rs;
        log.info("Sql: " + Sql);
        try {
            // ps = con.prepareStatement(Sql);
            // int result = ps.executeUpdate();
            rs = sm.executeUpdate(Sql);
            log.info("Result: " + rs);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Type.desc() + "失败", e.fillInStackTrace());
//            RunUnitService.Step.put("picture", "数据库查询失败");
        } finally {
            close();
        }
        return -1;
    }

    /**
     * 数据库查询
     * 
     * @param sql
     * @return
     */
    public static List<Map<String, Object>> query(String DataType, String DataEnviron, String DataName,String Port,String DataBase, String Sql) {
        checkConnection(DataType,DataEnviron, DataName,Port, DataBase);
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        log.info("Sql: " + Sql);
        try {
            // ps = connection.prepareStatement(sql);
            // rs = ps.executeQuery();
            rs = sm.executeQuery(Sql);
            ResultSetMetaData metaData = rs.getMetaData();
            while (rs.next()) {
                int count = metaData.getColumnCount();
                Map<String, Object> rowMap = new HashMap<String, Object>();
                for (int i = 1; i <= count; i++) {
                    rowMap.put(metaData.getColumnLabel(i), rs.getObject(i));
                    // System.out.print(rs.getString(i) + "\t");
                    if ((i == 2) && (rs.getString(i).length() < 8)) {
                        // System.out.print("\t");
                    }
                }
                // log.info("");
                results.add(rowMap);
            }
            log.info("Count: " + results.size());
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("查询失败", e.fillInStackTrace());
//            RunUnitService.Step.put("picture", "数据库查询失败");
        } finally {
            close(rs);
        }
        return results;
    }

    /**
     * 指定SQL语句,执行查询操作,并打印结果
     * 
     * @param sql
     * @return
     */
    public static void Query(String DataType, String DataEnviron,String DataName,String Port,String DataBase, String Sql) {
        checkConnection(DataType,DataEnviron, DataName,Port, DataBase);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // ps = con.prepareStatement(Sql);
            // rs = ps.executeQuery();
            rs = sm.executeQuery(Sql);
            int col = rs.getMetaData().getColumnCount();
            log.info("============================");
            while (rs.next()) {
                for (int i = 1; i <= col; i++) {
                    System.out.print(rs.getString(i) + "\t");
                    if ((i == 2) && (rs.getString(i).length() < 8)) {
                        System.out.print("\t");
                    }
                }
                log.info("");
            }
            log.info("============================");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("", e);
        } finally {
            close(rs);
        }
    }

    /** 执行一批SQL查询语句 */
    public static void getBatchQuery(Connection con, String[] sqls) throws Exception {
        boolean supportBatch = supportBatch(con); // 判断是否支持批处理
        if (supportBatch && sqls != null) {
            Statement sm = null;
            try {
                sm = con.createStatement();
                for (int a = 0; a < sqls.length; a++) {
                    ResultSet rs = sm.executeQuery(sqls[a]);
                    int col = rs.getMetaData().getColumnCount();
                    log.info("============================");
                    while (rs.next()) {
                        for (int i = 1; i <= col; i++) {
                            System.out.print(rs.getString(i) + "\t");
                            if ((i == 2) && (rs.getString(i).length() < 8)) {
                                System.out.print("\t");
                            }
                        }
                        log.info("");
                    }
                    log.info("============================");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                sm.close();
            }
        }
    }

    /** 执行一批SQL更新语句 */
    public static void goBatchUpdate(Connection con, String[] sqls) throws Exception {
        boolean supportBatch = supportBatch(con); // 判断是否支持批处理
        if (supportBatch && sqls != null) {
            Statement sm = null;
            try {
                sm = con.createStatement();
                for (int i = 0; i < sqls.length; i++) {
                    sm.addBatch(sqls[i]);// 将所有的SQL语句添加到Statement中
                }
                // 一次执行多条SQL语句
                int[] results = sm.executeBatch();// 执行一批SQL语句
                // 分析执行的结果
                for (int i = 0; i < sqls.length; i++) {
                    if (results[i] >= 0) {
                        log.info("语句: " + sqls[i] + " 执行成功，影响了" + results[i] + "行数据");
                    } else if (results[i] == Statement.SUCCESS_NO_INFO) {
                        log.info("语句: " + sqls[i] + " 执行成功，影响的行数未知");
                    } else if (results[i] == Statement.EXECUTE_FAILED) {
                        log.info("语句: " + sqls[i] + " 执行失败");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                sm.close();
            }
        }
    }

    /**
     * 执行存储过程,带参数
     * 
     * @param procedure
     * @param params
     * @return
     */
    public static int procedure(String DataType, String DataEnviron, String DataName,String Port,String DataBase, String prc_name, Object... params) {
        checkConnection(DataType,DataEnviron, DataName,Port, DataBase);
        CallableStatement cs = null;
        try {
            cs = con.prepareCall(prc_name);
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    // TODO set类型对应数据库,包含输入和输出
                    cs.setString(i + 1, String.valueOf(params[i]));
                }
            }
            log.info("开始执行存储过程: " + prc_name);
            cs.execute();
            log.info("存储过程执行成功 ");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("存储过程[" + prc_name + "]执行失败", e.fillInStackTrace());
        } finally {
            close(cs);
        }
        return 0;
    }

    // public static int procedure2(String prc_name, String params) {
    // checkConnection();
    // CallableStatement cs = null;
    // try {
    // cs = connection.prepareCall(prc_name);
    // //给存储过程的第一个参数设置值
    // cs.setBigDecimal(1, new BigDecimal(params));
    // //注册存储过程的第二个参数
    // cs.registerOutParameter(2,java.sql.Types.VARCHAR);
    // cs.execute();
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // return 0;
    // }
    //
    // public static void procedure2(String Process,String Value,String Result) {
    // checkConnection();
    // CallableStatement cs;
    // PreparedStatement ps;
    // try {
    // cs = conn.prepareCall(Process);
    // cs.setString(1, Value);
    // log.info("开始执行存储过程: "+ Process);
    // cs.execute();
    // log.info("存储过程执行成功, 生成数据如下: ");
    // ps = conn.prepareStatement(Result);
    // rs = ps.executeQuery();
    // int col = rs.getMetaData().getColumnCount();
    // log.info("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    // while (rs.next()) {
    // for (int i = 1; i <= col; i++) {
    // System.out.print(rs.getString(i) + "\t");
    // if ((i == 2) && (rs.getString(i).length() < 8)) {
    // System.out.print("\t");
    // }
    // }
    // log.info("");
    // }
    // log.info("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    // CloseDatabase();
    // log.info("数据库连接已关闭！");
    // }
    // catch (Exception e) {
    // e.printStackTrace();
    // log.info("存储过程执行失败！");
    // }
    // }
//    try {
//		long start = System.currentTimeMillis();
//		// 设置不允许自动提交数据
//		con.setAutoCommit(false);
//		String sql = "INSERT INTO \"TEST\".\"JDBC\" VALUES (?,?)";
//		ps = con.prepareStatement(sql);
//		for (int i = 0; i < 200; i++) {
//			ps.setInt(1,  i);
//			ps.setObject(2, "name" + i);
//			// 1、暂时缓存sql，缓存一定数量之后再与数据库交互，进行插入
//			ps.addBatch();
//			if (i % 50 == 0) { // 缓存50个sql,执行一次数据库插入的交互
//				// 2、执行batch
//				ps.executeBatch();
//				// 3、清空batch
//				ps.clearBatch();
//			}
//		}
//		// 统一提交数据
//		con.commit();
//		long end = System.currentTimeMillis();
//		System.out.println("花费的时间为：" + (end - start));
//	} catch (Exception e) {
//		e.printStackTrace();
//	} finally {
//		con.close();
//		ps.close();
//	}

//	String sql = "INSERT INTO \"TEST\".\"JDBC\" VALUES (?,?)";
//	ps = con.prepareStatement(sql);
//	ps.setInt(1, 3);
//	ps.setString(2, "小赵");
//	ps.execute();

    public static void checkConnection(String DataType, String DataEnviron, String DataName,String Port, String DataBase) {
        switch (DataType) {
            case "Oracle":
                checkConnection_Oracle(DataEnviron, DataName,Port, DataBase);
                break;
            case "MySql":
                checkConnection_MySql(DataEnviron, DataName,Port, DataBase);
                break;
            case "SSHKeyMySql":
                checkConnect_SSHKeyMySql(DataEnviron, DataBase);
                break;
            case "SSHPassWordMySql(":
                checkConnect_SSHPassWordMySql(DataEnviron, DataBase);
                break;
        }
    }

    public static void checkConnection_Oracle(String DataEnviron, String DataName,String Port,String DataBase) {
        try {
            if (con == null || con.isClosed())
                Connect_Oracle(DataEnviron, DataName,Port,DataBase);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        }
    }

    public static void checkConnection_MySql(String DataEnviron,String DataName,String Port, String DataBase) {
        try {
            if (con == null || con.isClosed())
                Connect_MySql(DataEnviron, DataName,Port, DataBase);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e.fillInStackTrace());
        }
    }

    private static void checkConnect_SSHKeyMySql(String DataEnviron, String DataBase) {
        try {
            if (con == null || con.isClosed())
                Connect_SSHKeyMySql(DataEnviron, DataBase);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e.fillInStackTrace());
        }
    }

    private static void checkConnect_SSHPassWordMySql(String DataEnviron, String DataBase) {
        try {
            if (con == null || con.isClosed())
                Connect_SSHPassWordMySql(DataEnviron, DataBase);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e.fillInStackTrace());
        }
    }

    /**
     * 连接Oracle数据库
     * 
     * @throws Exception
     */
    public static void Connect_Oracle(String DataEnviron, String DataName,String Port, String DataBase) throws Exception {
        try {
            if ("正式环境".equals(DataEnviron)) {
                Class.forName(Oracle_FDRIVER);
                con = DriverManager.getConnection(Oracle_FURL + "" + DataBase + "", Oracle_FUSERNAME, Oracle_FPASSWORD);
                sm = con.createStatement();
            }
            if ("测试环境".equals(DataEnviron)) {
                Class.forName(Oracle_TDRIVER);
                String Oracle_TURL= ConfigUtil.getProperty(""+DataName+"_Oracle.jdbc.Turl", ConstantsUtil.CONFIG_JDBC);
                String Oracle_TUSERNAME = ConfigUtil.getProperty(""+DataName+"_Oracle.jdbc.Tusername", ConstantsUtil.CONFIG_JDBC);
                String Oracle_TPASSWORD = ConfigUtil.getProperty(""+DataName+"_Oracle.jdbc.Tpassword", ConstantsUtil.CONFIG_JDBC);
                con = DriverManager.getConnection("jdbc:oracle:thin:@"+Oracle_TURL+":"+Port+":"+DataBase+"" , Oracle_TUSERNAME, Oracle_TPASSWORD);
                sm = con.createStatement();
            }
            if ("开发环境".equals(DataEnviron)) {
                Class.forName(Oracle_DDRIVER);
                con = DriverManager.getConnection(Oracle_DURL, Oracle_DUSERNAME, Oracle_DPASSWORD);
                sm = con.createStatement();
            }
            log.info("数据库连接成功");
        } catch (Exception e) {
            String message = "数据库连接失败";
            if (e instanceof ClassNotFoundException)
                message = "数据库驱动类未找到";
            throw new Exception(message, e.fillInStackTrace());
        }
    }

    /**
     * 连接MySql数据库
     * 
     * @throws Exception
     */
    public static void Connect_MySql(String DataEnviron, String DataName,String Port, String DataBase) throws Exception {
        String sql = "SELECT str1 from m_getmsg_water where phonno ='17378403911' ORDER BY create_date DESC LIMIT 1;";
        try {
            if ("正式环境".equals(DataEnviron)) {
                Class.forName(MySql_FDRIVER);
                con = DriverManager.getConnection(MySql_FURL + "" + DataBase + "?useUnicode=true&characterEncoding=utf-8&useSSL=false", MySql_FUSERNAME, MySql_FPASSWORD);
                sm = con.createStatement();
            } else if ("测试环境".equals(DataEnviron)) {
                Class.forName(MySql_TDRIVER);
                String MySql_TURL = ConfigUtil.getProperty(""+DataName+"_MySql.jdbc.Turl", ConstantsUtil.CONFIG_JDBC);                
                String MySql_TUSERNAME = ConfigUtil.getProperty(""+DataName+"_MySql.jdbc.Tusername", ConstantsUtil.CONFIG_JDBC);
                String MySql_TPASSWORD = ConfigUtil.getProperty(""+DataName+"_MySql.jdbc.Tpassword", ConstantsUtil.CONFIG_JDBC);
                con = DriverManager.getConnection("jdbc:mysql://"+ MySql_TURL + ":"+Port+"/" + DataBase + "?useUnicode=true&characterEncoding=utf-8&useSSL=false", MySql_TUSERNAME, MySql_TPASSWORD);
                sm = con.createStatement();
            } else if ("开发环境".equals(DataEnviron)) {
                Class.forName(MySql_DDRIVER);
                con = DriverManager.getConnection(MySql_DURL + "" + DataBase + "?useUnicode=true&characterEncoding=utf-8&useSSL=false", MySql_DUSERNAME, MySql_DPASSWORD);
                sm = con.createStatement();
            }
            log.info("数据库连接成功");
        } catch (Exception e) {
            String message = "数据库连接失败";
            if (e instanceof ClassNotFoundException)
                message = "数据库驱动类未找到";
            throw new Exception(message, e.fillInStackTrace());
        }
    }

    /**
     * 连接SHHMySql数据库
     * 
     * @throws Exception
     */
    public static void Connect_SSHKeyMySql(String DataEnviron, String DataBase) throws Exception {
        try {
            if ("正式环境".equals(DataEnviron)) {
                SSHKey(DataEnviron);
                Class.forName(MySqlSSH_TDRIVER);
                con = DriverManager.getConnection(MySqlSSH_TURL + "" + DataBase + "?useUnicode=true&characterEncoding=utf-8&useSSL=false", MySqlSSH_TUSERNAME, MySqlSSH_TPASSWORD);
                sm = con.createStatement();
            } else if ("测试环境".equals(DataEnviron)) {
                SSHKey(DataEnviron);
                Class.forName(MySqlSSH_TDRIVER);
                con = DriverManager.getConnection(MySqlSSH_TURL + "" + DataBase + "?useUnicode=true&characterEncoding=utf-8&useSSL=false", MySqlSSH_TUSERNAME, MySqlSSH_TPASSWORD);
                sm = con.createStatement();
            } else if ("开发环境".equals(DataEnviron)) {
                SSHKey(DataEnviron);
                Class.forName(MySqlSSH_TDRIVER);
                con = DriverManager.getConnection(MySqlSSH_TURL + "" + DataBase + "?useUnicode=true&characterEncoding=utf-8&useSSL=false", MySqlSSH_TUSERNAME, MySqlSSH_TPASSWORD);
                sm = con.createStatement();
            }
            log.info("数据库连接成功");
        } catch (Exception e) {
            String message = "数据库连接失败";
            if (e instanceof ClassNotFoundException)
                message = "数据库驱动类未找到";
            throw new Exception(message, e.fillInStackTrace());
        }
    }

    /**
     * 连接SSHPassWordMySql数据库
     * 
     * @throws Exception
     */
    public static void Connect_SSHPassWordMySql(String DataEnviron, String DataBase) throws Exception {
        try {
            if ("正式环境".equals(DataEnviron)) {
                SSHPassWord(DataEnviron);
                Class.forName(MySqlSSH_TDRIVER);
                con = DriverManager.getConnection(MySqlSSH_TURL + "" + DataBase + "?useUnicode=true&characterEncoding=utf-8&useSSL=false", MySqlSSH_TUSERNAME, MySqlSSH_TPASSWORD);
                sm = con.createStatement();
            } else if ("测试环境".equals(DataEnviron)) {
                SSHPassWord(DataEnviron);
                Class.forName(MySqlSSH_TDRIVER);
                con = DriverManager.getConnection(MySqlSSH_TURL + "" + DataBase + "?useUnicode=true&characterEncoding=utf-8&useSSL=false", MySqlSSH_TUSERNAME, MySqlSSH_TPASSWORD);
                sm = con.createStatement();
            } else if ("开发环境".equals(DataEnviron)) {
                SSHPassWord(DataEnviron);
                Class.forName(MySqlSSH_TDRIVER);
                con = DriverManager.getConnection(MySqlSSH_TURL + "" + DataBase + "?useUnicode=true&characterEncoding=utf-8&useSSL=false", MySqlSSH_TUSERNAME, MySqlSSH_TPASSWORD);
                sm = con.createStatement();
            }
            log.info("数据库连接成功");
        } catch (Exception e) {
            String message = "数据库连接失败";
            if (e instanceof ClassNotFoundException)
                message = "数据库驱动类未找到";
            throw new Exception(message, e.fillInStackTrace());
        }
    }

    public static void SSHKey(String DataEnviron) throws Exception {
        String passphrase = "11";
        try {
            if ("正式环境".equals(DataEnviron)) {
                JSch jsch = new JSch();
                jsch.addIdentity(MySqlSSH_FKeyFile);
                Session session = jsch.getSession(MySqlSSH_FUser, MySqlSSH_FHost, MySqlSSH_FPort);
                UserInfo ui = new MyUserInfo(passphrase);
                session.setUserInfo(ui);
                session.connect();
                // log.info("SSH服务器连接成功，版本信息为："+session.getServerVersion());//这里打印SSH服务器版本信息
                int assinged_port = session.setPortForwardingL(MySqlSSH_FLport, MySqlSSH_FRhost, MySqlSSH_FRport);
                // log.info("端口映射成功：localhost:" + assinged_port + " -> " + MySqlSSH_FRhost + ":" + MySqlSSH_FRport);
            } else if ("开发环境".equals(DataEnviron)) {
                JSch jsch = new JSch();
                jsch.addIdentity(MySqlSSH_DKeyFile);
                Session session = jsch.getSession(MySqlSSH_DUser, MySqlSSH_DHost, MySqlSSH_DPort);
                UserInfo ui = new MyUserInfo(passphrase);
                session.setUserInfo(ui);
                session.connect();
                // log.info("SSH服务器连接成功，版本信息为："+session.getServerVersion());//这里打印SSH服务器版本信息
                int assinged_port = session.setPortForwardingL(MySqlSSH_DLport, MySqlSSH_DRhost, MySqlSSH_DRport);
                // log.info("端口映射成功：localhost:" + assinged_port + " -> " + MySqlSSH_DRhost + ":" + MySqlSSH_DRport);
            } else if ("测试环境".equals(DataEnviron)) {
                JSch jsch = new JSch();
                jsch.addIdentity(MySqlSSH_TKeyFile);
                Session session = jsch.getSession(MySqlSSH_TUser, MySqlSSH_THost, MySqlSSH_TPort);
                UserInfo ui = new MyUserInfo(passphrase);
                session.setUserInfo(ui);
                session.connect();
                // log.info("SSH服务器连接成功，版本信息为："+session.getServerVersion());//这里打印SSH服务器版本信息
                int assinged_port = session.setPortForwardingL(MySqlSSH_TLport, MySqlSSH_TRhost, MySqlSSH_TRport);
                // log.info("端口映射成功：localhost:" + assinged_port + " -> " + MySqlSSH_TRhost + ":" + MySqlSSH_TRport);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            Thread.sleep(500);
        }
    }

    public static void SSHPassWord(String DataEnviron) throws Exception {
        String passphrase = "11";
        try {
            if ("正式环境".equals(DataEnviron)) {
                JSch jsch = new JSch();
                Session session = jsch.getSession(MySqlSSH_FUser, MySqlSSH_FHost, MySqlSSH_FPort);
                session.setPassword(MySqlSSH_FPassword);
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect();
                // log.info("SSH服务器连接成功，版本信息为："+session.getServerVersion());//这里打印SSH服务器版本信息
                int assinged_port = session.setPortForwardingL(MySqlSSH_FLport, MySqlSSH_FRhost, MySqlSSH_FRport);
                // log.info("端口映射成功：localhost:" + assinged_port + " -> " + MySqlSSH_FRhost + ":" + MySqlSSH_FRport);
            } else if ("开发环境".equals(DataEnviron)) {
                JSch jsch = new JSch();
                Session session = jsch.getSession(MySqlSSH_DUser, MySqlSSH_DHost, MySqlSSH_DPort);
                session.setPassword(MySqlSSH_DPassword);
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect();
                // log.info("SSH服务器连接成功，版本信息为："+session.getServerVersion());//这里打印SSH服务器版本信息
                int assinged_port = session.setPortForwardingL(MySqlSSH_DLport, MySqlSSH_DRhost, MySqlSSH_DRport);
                // log.info("端口映射成功：localhost:" + assinged_port + " -> " + MySqlSSH_DRhost + ":" + MySqlSSH_DRport);
            } else if ("测试环境".equals(DataEnviron)) {
                JSch jsch = new JSch();
                Session session = jsch.getSession(MySqlSSH_TUser, MySqlSSH_THost, MySqlSSH_TPort);
                session.setPassword(MySqlSSH_TPassword);
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect();
                // log.info("SSH服务器连接成功，版本信息为："+session.getServerVersion());//这里打印SSH服务器版本信息
                int assinged_port = session.setPortForwardingL(MySqlSSH_TLport, MySqlSSH_TRhost, MySqlSSH_TRport);
                // log.info("端口映射成功：localhost:" + assinged_port + " -> " + MySqlSSH_TRhost + ":" + MySqlSSH_TRport);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        }
    }

    /** 判断数据库是否支持批处理 */
    public static boolean supportBatch(Connection con) {
        try {
            // 得到数据库的元数据
            DatabaseMetaData md = con.getMetaData();
            return md.supportsBatchUpdates();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 释放资源并关闭数据库
     */
    public static void close(ResultSet rs) {
        try {
            if (sm != null) {
                sm.close();
                sm = null;
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            log.info("数据库资源释放成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("数据库资源释放失败！", e);
        }
        close();
    }

    /**
     * 释放资源并关闭数据库
     */
    public static void close(CallableStatement cs) {
        try {
            if (cs != null) {
                cs.close();
                cs = null;
            }
            log.info("数据库资源释放成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("数据库资源释放失败！", e);
        }
        close();
    }

    /**
     * 关闭数据库
     */
    public static void close() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                con = null;
            }
            if (sm != null) {
                sm.close();
                sm = null;
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            log.info("数据库关闭成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("数据库关闭失败！", e);
        }
    }

    public static class MyUserInfo implements UserInfo {
        private String passphrase = null;

        public MyUserInfo(String passphrase) {
            this.passphrase = passphrase;
        }

        public String getPassphrase() {
            return passphrase;
        }

        public String getPassword() {
            return null;
        }

        public boolean promptPassphrase(String s) {
            return true;
        }

        public boolean promptPassword(String s) {
            return true;
        }

        public boolean promptYesNo(String s) {
            return true;
        }

        public void showMessage(String s) {
            log.info(s);
        }
    }

    /**
     * 数据库操作枚举
     */
    enum OpType {

        INSERT("插入"), UPDATE("更新"), DELETE("删除"), QUERY("查询"), PROCEDURE("执行存储过程");

        String desc;

        OpType(String desc) {
            this.desc = desc;
        }

        String desc() {
            return desc;
        }
    }

    public static void main(String[] args) {
        String[] sqls = new String[1];
//        sqls[0] = "SELECT * FROM `555` ORDER BY df DESC;";
        sqls[0] = "SELECT * FROM \"TEST\".\"JDBC\"";
        
        String[] sqls1 = new String[7];
        sqls1[0] = "DROP TABLE \"TEST\".\"JDBC\"";
        sqls1[1] = "CREATE TABLE \"TEST\".\"JDBC\" (\r\n"
        		+ "  \"id\" NUMBER ,\r\n"
        		+ "  \"name\" VARCHAR2(255 BYTE) \r\n"
        		+ ")";
        sqls1[2] = "ALTER TABLE \"TEST\".\"JDBC\" ADD CONSTRAINT \"tableName_PK\" PRIMARY KEY (\"id\")";
        sqls1[3] = "INSERT INTO \"TEST\".\"JDBC\" VALUES (1, '小王')";
        sqls1[4] = "INSERT INTO \"TEST\".\"JDBC\" VALUES (2, '小李')";		
        sqls1[5] = "DELETE FROM \"TEST\".\"JDBC\" WHERE \"id\"=2";
        sqls1[6] = "UPDATE \"TEST\".\"JDBC\" SET \"name\"='小李' WHERE \"id\"=1";
        try {
        	checkConnection_Oracle("测试环境","AAS_DBSG","1521", "4567");
//            checkConnection_MySql("测试环境", "4567");
            goBatchUpdate(con, sqls1);
            getBatchQuery(con, sqls);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
}
