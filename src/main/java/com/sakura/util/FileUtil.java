package com.sakura.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 文件操作类
 * 
 * @author Administrator
 *
 */
public class FileUtil {
	static Logger log = Logger.getLogger(FileUtil.class);
	
	/**
	 * 单例模式
	 *//*
		 * private static FileUtil instance = null; private FileUtil() { } public static
		 * FileUtil getInstance() { synchronized (FileUtil.class) { if(null == instance)
		 * { instance = new FileUtil(); } } return instance; }
		 */
	/**
	 * 创建文件/文件夹
	 * 
	 * @param path 路径
	 * @return 是否创建成功
	 * @throws Exception
	 */
	public static boolean creatFile(String path) throws Exception {
		if (null == path || path.length() == 0) {
			throw new Exception("路径不正确！");
		}
		File file = new File(path);
		// 如果路径存在，则不创建
		if (!file.exists()) {
			if (file.isDirectory()) {
				// 文件夹
				file.mkdirs();
				// file.mkdir(); 创建单层路径 file.mkdirs() 可以创建多层路径
				return true;
			} else {
				// 文件 先创建父路径，然后再创建文件
				String dirPath = path.substring(0, path.lastIndexOf(File.separator));
				File dirFile = new File(dirPath);
				if (!dirFile.exists()) {
					dirFile.mkdirs();
				}
				File fileFile = new File(path);
				try {
					fileFile.createNewFile();
					return true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			throw new Exception("文件已存在！");
		}
		return false;
	}

	/**
	 * 删除文件/文件夹及其中的所有子文件夹和文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public static void deleteFile(String filePath) throws Exception {
		if (null == filePath || filePath.length() == 0) {
			throw new Exception("filePath:" + filePath + "路径不正确！");
		}
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("filePath:" + filePath + "文件不存在！");
		}
		if (file.isFile()) {
			file.delete();
		}
		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (null != childFiles && childFiles.length != 0) {
				// 循环递归删除
				for (File childFile : childFiles) {
					deleteFile(childFile.getAbsolutePath());
				}
			}
			file.delete();
		}
		log.info("删除：【"+filePath+"】成功");
	}

	/**
	 * 删除path下所有文件，包括文件夹
	 * @param path
	 * @param isIncludeRoot 是否要删除path(如果是文件夹)
	 * @return true删除成功
	 */
	public static boolean deleteAllFile(String path, String... isIncludeRoot) {
	    File file = new File(path);
	    if (!file.exists()) {
	        return false;
	    }
	    if(file.isFile()) {
	        return file.delete();
	    }
	    File[] fileList = file.listFiles();
	    boolean res = true;
	    for (File f : fileList) {
	        if(f.isFile()) {
	            res = res && f.delete();
	        } else if(f.isDirectory()) {
	            res = res && deleteAllFile(f.getAbsolutePath(), "true");
	        }
	    }
	    if(isIncludeRoot.equals("true")) {
	        res = res && file.delete();
	    }
	    return res;
	}

	/**
	 * 查看文件夹及其中的所有子文件夹和文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<Object,Object> getFileList(String filePath) throws Exception {
		Map<Object,Object> listFile = new LinkedHashMap<>();
		if (null == filePath || filePath.length() == 0) {
			throw new Exception("filePath:" + filePath + "路径不正确！");
		}
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("filePath:" + filePath + "文件不存在！");
		}
		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (null != childFiles && childFiles.length != 0) {
				// 循环递归
				for (File childFile : childFiles) {
					listFile.put("文件路径", childFile.getAbsolutePath());
					listFile.put("文件名称", childFile.getName());
					listFile.put("文件大小", childFile.length());
				}
			}
		}
		log.info(listFile);
		return listFile;
	}
	
	/**
	 * 获取文件基本信息
	 * 
	 * @param file
	 */
	public static void getBaseInfo(File file) {
		// 文件绝对路径 文件大小 文件是否是文件夹 文件是否是文件 文件是否可读 文件是否可写 文件是否可执行 文件修改时间 文件父目录名
		// 文件所在分区总大小 未使用大小 可用大小
		System.out.println("文件基本信息如下：");
		System.out.println("文件绝对路径：" + file.getAbsolutePath());
		System.out.println("文件名称：" + file.getName());
		System.out.println("文件大小:" + file.length());
		System.out.println("文件是否是文件夹：" + file.isDirectory());
		System.out.println("文件是否是文件：" + file.isFile());
		System.out.println("文件是否可读：" + file.canExecute());
		System.out.println("文件是否可读：" + file.canRead());
		System.out.println("文件是否可写：" + file.canWrite());
		System.out.println("文件修改时间：" + file.lastModified());
		System.out.println("文件父目录名称：" + file.getParent());
		System.out.println("文件所在分区大小：" + file.getTotalSpace() / 1024 / 1024 + "Mb");
		System.out.println("文件所在分区未使用大小：" + file.getFreeSpace() / 1024 / 1024 + "Mb");
		System.out.println("文件所在分区可用大小：" + file.getUsableSpace() / 1024 / 1024 + "Mb");
		System.out.println("文件夹结构如下图：");
		try {
			printFileStructure(file, 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 打印文件路径
	 * 
	 * @param file
	 * @param deepth
	 * @throws Exception
	 */
	public static void printFileStructure(File file, int deepth) throws Exception {
		if (!file.exists()) {
			throw new Exception("文件路径不存在！");
		}
		if (!file.isHidden()) {
			if (file.isFile()) {
				// 直接打印
				printFile(file, deepth);
				return;
			}
			if (file.isDirectory()) {
				// 先打印自身，然后递归打印子文件夹和子文件
				printFile(file, deepth);
				File[] childFiles = file.listFiles();
				if (null != childFiles && childFiles.length > 0) {
					deepth++;
					for (File childFile : childFiles) {
						printFileStructure(childFile, deepth);
					}
				}
			}
		}

	}

	/**
	 * 打印文件夹树形结构
	 * 
	 * @param file
	 * @param deepth
	 */
	public static void printFile(File file, int deepth) {
		String name = file.getName();
		StringBuffer sb = new StringBuffer();
		StringBuffer tempSb = new StringBuffer();
		for (int i = 0; i < deepth; i++) {
			tempSb.append("   ");
		}
		sb.append(tempSb);
		sb.append("|" + "\n");
		sb.append(tempSb);
		sb.append("------" + name);
		System.out.println(sb.toString());
	}

	/**
	 * 删除特定的文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public static void deleteNamedFile(String filePath, String regex) throws Exception {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("文件不存在！");
		}
		// 匿名内部类实现 FilenameFilter 接口种的accept()方法，使用在正则表达式进行匹配
		// accept(File dir,String name) 使用当前文件对象 和 当前文件的名称 进行文件是否符合要求的标准判断；
		/*
		 * =============================================================================
		 * ========== File file = new File("."); String [] nameList =
		 * file.list((dir,name) -> name.endsWith(".java") || new
		 * File(name).isDirectory()); for(String name : nameList) {
		 * System.out.println(name); }
		 * =============================================================================
		 * =========== 这里使用Lamda表达式实现FilenameFilter 接口种的accept()方法
		 */
		File[] fileList = file.listFiles(new FilenameFilter() {

			/**
			 * 使用正则表达式进行匹配
			 * 
			 * @param regexStr
			 * @return
			 */
			private boolean regexMatch(String name, String regexStr) {
				Pattern pattern = Pattern.compile(regexStr);
				Matcher matcher = pattern.matcher(name);
				return matcher.find();
			}

			@Override
			public boolean accept(File dir, String name) {
				return regexMatch(name, regex);
			}
		});
		if (null != fileList && fileList.length > 0) {
			for (File filteredFile : fileList) {
				filteredFile.delete();

			}
		}
	}

	/**
	 * 复制文件/文件夹及其中的所有子文件夹和文件
	 * 
	 * @return
	 */
	public static void copyFile(String srcFilePath, String destFilePath) {
		InputStream is = null;
		OutputStream os = null;
		try {
			if (creatFile(destFilePath)) {
				File srcFile = new File(srcFilePath);
				File destFile = new File(destFilePath);
				is = new FileInputStream(srcFile);
				os = new FileOutputStream(destFile);
				byte[] buffer = new byte[2048];
				int temp = 0;
				while ((temp = is.read(buffer)) != -1) {
					os.write(buffer, 0, temp);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// java 7 以后可以不关闭，可以自动关闭
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 复制指定地文件
	 * 
	 * @return
	 */
	/*
	 * public static boolean copyNamedFile() {
	 * 
	 * }
	 *//**
		 * 剪切文件/文件夹及其中的所有子文件夹和文件
		 * 
		 * @return
		 */
	public static void cutFile(String srcFilePath, String destFilePath) {
		// 先复制，再删除
		try {
			copyFile(srcFilePath, destFilePath);
			deleteFile(srcFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 剪切文件/文件夹及其中的所有子文件夹和文件
	 * 
	 * @return
	 */
	/*
	 * public static boolean cutNamedFile() {
	 * 
	 * }
	 *//**
		 * 文件压缩
		 * 
		 * @param destPath
		 * @param fileFormats
		 * @param srcFile
		 * @return
		 */
	/*
	 * public static boolean fileCompress(String destPath,String fileFormats,String
	 * srcFile,String regex) {
	 * 
	 * }
	 *//**
		 * 文件解压缩
		 * 
		 * @param destPath
		 * @param srcFile
		 * @return
		 */
	/*
	 * public static boolean fileDecompress(String destPath,String srcPath) {
	 * 
	 * }
	 *//**
		 * 文件加密
		 * 
		 * @param srcPath
		 * @param destPath
		 * @param encryptKey
		 * @param encryptAlgorithm
		 * @return
		 */

	/*
	 * public static boolean fileEncrypt(String srcPath,String destPath,String
	 * encryptKey,String encryptAlgorithm) {
	 * 
	 * }
	 *//**
		 * 文件解密
		 * 
		 * @param srcPath
		 * @param destPath
		 * @param encryptKey
		 * @param encryptAlgorithm
		 * @return
	 * @throws Exception 
		 *//*
			 * public static boolean fileDecrypt(String srcPath,String destPath,String
			 * encryptKey,String encryptAlgorithm) {
			 * 
			 * }
			 */

	public static void main(String[] args) throws Exception {
//		deleteFile("C:\\Users\\user06\\Downloads\\");
//		deleteFile("C:\\Users\\user06\\Downloads\\配置信息.xlsx");
//		deleteAllFile("C:/Users/user06/Downloads/",false);
		String fileName = getFileList("C:/Users/user06/Downloads/").get("文件名称").toString();
		log.info(fileName);
//		File file = new File("D:\\北邮人下载\\书籍\\编译原理");
//		getBaseInfo(file);
//		try {
//			/* deleteNamedFile("D:\\北邮人下载\\书籍",".pdf"); */
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		/*
//		 * cutFile("F:\\抢票软件\\12306Bypass.exe","F:\\抢票软件\\12306Bypass\\12306Bypass.exe")
//		 * ;
//		 */
	}
}
