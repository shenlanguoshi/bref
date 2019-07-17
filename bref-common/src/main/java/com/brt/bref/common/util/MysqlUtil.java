package com.brt.bref.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Calendar;

public class MysqlUtil {
	static String newLine = System.getProperty("line.separator");
	static String url = "jdbc:mysql://rm-uf6z7a1b34xrse2z57o.mysql.rds.aliyuncs.com:3306?user=root&password=fang_brt.bref_0416&useInformationSchema=true";
	static String entityPath = "com.brt.bref.user.feign.entity";
	static String constantPath = "com.brt.bref.user.feign.constant";
	static String daoPath = "com.brt.bref.user.svc.dao";
	static String servicePath = "com.brt.bref.user.svc.service";
	static String controllerPath = "com.brt.bref.user.svc.controller";
	static String author = "方杰";
	// 对应表名,使用%表示所有表
	static String table = "sys_user";
	// 对应数据库
	static String database = "brt.bref_user";

	public static void main(String[] args) {
		tableConstant();
		daoUseMap();
		daoXmlUseMap();
		serviceUseMap();
		serviceImplUseMap();
		controllerUseMap();
	}

	public static void tableConstantSimple() {
		try {
			// 加载驱动
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// 连接数据库
			Connection conn = DriverManager.getConnection(url);
			DatabaseMetaData metaData = conn.getMetaData();
			// sys_user对应表名,使用%表示所有表
			ResultSet tables = metaData.getTables(database, "%", table, new String[]{"TABLE"});
			while(tables.next()) {
				FileOutputStream fop = null;
				File file;
				String tableName = tables.getString("TABLE_NAME");
				String tableRemarks = tables.getString("REMARKS");

				file = new File("D:/" + UnderlineToHump2(tableName.substring(tableName.indexOf("_") + 1)) + "Constant.java");
				fop = new FileOutputStream(file);
				if (!file.exists()) {
					file.createNewFile();
				}
				fop.write(("package " + constantPath + ";").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("/**").getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @description " + tableRemarks).getBytes());
				fop.write(newLine.getBytes());
				fop.write((" */").getBytes());
				fop.write(newLine.getBytes());

				fop.write(("public interface " + UnderlineToHump2(tableName.substring(tableName.indexOf("_") + 1)) + "Constant {").getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    String TABLE_NAME = \"" + tableName + "\";").getBytes());
				fop.write(newLine.getBytes());
				ResultSet columns = metaData.getColumns(database, "%", tableName, "%");
				while(columns.next()) {
					String columnRemarks = columns.getString("REMARKS");
					String columnName = columns.getString("COLUMN_NAME");
					fop.write(("    /**").getBytes());
					fop.write(newLine.getBytes());
					fop.write(("     * @description " + columnRemarks).getBytes());
					fop.write(newLine.getBytes());
					fop.write(("     */").getBytes());
					fop.write(newLine.getBytes());
					fop.write(("    String " + columnName.toUpperCase() + " = \"" + tableName + "." + columnName + "\";").getBytes());
					fop.write(newLine.getBytes());
				}

				fop.write(("}").getBytes());
				fop.write(newLine.getBytes());

				fop.flush();
				fop.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void tableConstant() {
		try {
			// 加载驱动
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// 连接数据库
			Connection conn = DriverManager.getConnection(url);
			DatabaseMetaData metaData = conn.getMetaData();
			// sys_user对应表名,使用%表示所有表
			ResultSet tables = metaData.getTables(database, "%", table, new String[]{"TABLE"});
			while(tables.next()) {
				FileOutputStream fop = null;
				File file;
				String tableName = tables.getString("TABLE_NAME");
				String tableRemarks = tables.getString("REMARKS");

				file = new File("D:/" + UnderlineToHump2(tableName.substring(tableName.indexOf("_") + 1)) + "Constant.java");
				fop = new FileOutputStream(file);
				if (!file.exists()) {
					file.createNewFile();
				}
				fop.write(("package " + constantPath + ";").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(("import com.brt.bref.common.constant.TableColumn;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("/**").getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @description " + tableRemarks).getBytes());
				fop.write(newLine.getBytes());
				fop.write((" */").getBytes());
				fop.write(newLine.getBytes());

				fop.write(("public interface " + UnderlineToHump2(tableName.substring(tableName.indexOf("_") + 1)) + "Constant {").getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    String TABLE_NAME = \"" + tableName + "\";").getBytes());
				fop.write(newLine.getBytes());
				ResultSet columns = metaData.getColumns(database, "%", tableName, "%");
				while(columns.next()) {
					String columnRemarks = columns.getString("REMARKS");
					String columnName = columns.getString("COLUMN_NAME");
					fop.write(("    /**").getBytes());
					fop.write(newLine.getBytes());
					fop.write(("     * @description " + columnRemarks).getBytes());
					fop.write(newLine.getBytes());
					fop.write(("     */").getBytes());
					fop.write(newLine.getBytes());
					fop.write(("    TableColumn " + columnName.toUpperCase() + " =  new TableColumn(\"" + UnderlineToHump(columnName) + "\", \"" + tableName + "." + columnName + "\", " + columns.getInt("DATA_TYPE") + ");").getBytes());
					fop.write(newLine.getBytes());
				}

				fop.write(("}").getBytes());
				fop.write(newLine.getBytes());

				fop.flush();
				fop.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public static void entity() {
		try {
			// 加载驱动
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// 连接数据库
			Connection conn = DriverManager.getConnection(url);
			DatabaseMetaData metaData = conn.getMetaData();
			// sys_user对应表名,使用%表示所有表
			ResultSet tables = metaData.getTables(database, "%", table, new String[]{"TABLE"});
			while(tables.next()) {
				FileOutputStream fop = null;
				File file;
				// sys_user
				String tableName = tables.getString("TABLE_NAME");
				String tableRemarks = tables.getString("REMARKS");
				// User
				String name = UnderlineToHump2(tableName.substring(tableName.indexOf("_") + 1));

				file = new File("D:/" + name + "Entity.java");
				fop = new FileOutputStream(file);
				if (!file.exists()) {
					file.createNewFile();
				}

				fop.write(("package " + entityPath + ";").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("import java.io.Serializable;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import java.util.Date;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("import lombok.Getter;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import lombok.Setter;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("/**").getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @author " + author).getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @date " + getYmd()).getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @description " + tableRemarks + "实体").getBytes());
				fop.write(newLine.getBytes());
				fop.write((" */").getBytes());
				fop.write(newLine.getBytes());

				fop.write(("@SuppressWarnings(\"serial\")").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("@Getter").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("@Setter").getBytes());
				fop.write(newLine.getBytes());

				fop.write(("public class " + name + "Entity implements Serializable {").getBytes());
				fop.write(newLine.getBytes());

				ResultSet columns = metaData.getColumns(database, "%", tableName,"%");
				while(columns.next()) {
					String columnRemarks = columns.getString("REMARKS");
					String columnName = UnderlineToHump(columns.getString("COLUMN_NAME"));
					System.out.println(columnName + ":" + columns.getInt("DATA_TYPE"));
					if(columnName.equals("del")) {
						continue;
					}
					int type = columns.getInt("DATA_TYPE");
					fop.write(("    /**").getBytes());
					fop.write(newLine.getBytes());
					fop.write(("     * @description " + columnRemarks).getBytes());
					fop.write(newLine.getBytes());
					fop.write(("     */").getBytes());
					fop.write(newLine.getBytes());
					if(type == 1 || type == 12 || type == -1) {
						fop.write(("    private String " + columnName + ";").getBytes());
					}else if (type == 93) {
						fop.write(("    private Date " + columnName + ";").getBytes());
					}else if (type == 4) {
						fop.write(("    private Integer " + columnName + ";").getBytes());
					}else if (type == 3 || type == 8) {
						fop.write(("    private Double " + columnName + ";").getBytes());
					}else if (type == -7) {
						fop.write(("    private Boolean " + columnName + ";").getBytes());
					}else {
						fop.write(("    private String " + columnName + ";").getBytes());
					}
					fop.write(newLine.getBytes());
				}

				fop.write(("}").getBytes());
				fop.write(newLine.getBytes());

				fop.flush();
				fop.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public static void daoUseEntity() {
		try {
			// 加载驱动
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// 连接数据库
			Connection conn = DriverManager.getConnection(url);
			DatabaseMetaData metaData = conn.getMetaData();
			// sys_user对应表名,使用%表示所有表
			ResultSet tables = metaData.getTables(database, "%", table, new String[]{"TABLE"});
			while(tables.next()) {
				FileOutputStream fop = null;
				File file;
				// sys_user
				String tableName = tables.getString("TABLE_NAME");
				String tableRemarks = tables.getString("REMARKS");
				// User
				String name = UnderlineToHump2(tableName.substring(tableName.indexOf("_") + 1));

				file = new File("D:/" + name + "Dao.java");
				fop = new FileOutputStream(file);
				if (!file.exists()) {
					file.createNewFile();
				}

				fop.write(("package " + daoPath + ";").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("import java.util.Map;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import java.util.List;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("import org.apache.ibatis.annotations.Param;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("import com.github.pagehelper.Page;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(("import " + entityPath + "." + name + "Entity;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("/**").getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @author " + author).getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @date " + getYmd()).getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @description " + tableRemarks + "Dao").getBytes());
				fop.write(newLine.getBytes());
				fop.write((" */").getBytes());
				fop.write(newLine.getBytes());

				fop.write(("public interface " + name + "Dao {").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("    int insert(@Param(\"mapInsert\") Map<String, Object> mapInsert);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    int deleteById(String id);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    int update(Map<String, Object> mapUpdate, String id);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    " + name + "Entity getById(String id);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    Page<" + name + "Entity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike, String dataSchema, Map<String, List<Map<String, Object>>> dataScope);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("}").getBytes());
				fop.write(newLine.getBytes());

				fop.flush();
				fop.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void daoUseMap() {
		try {
			// 加载驱动
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// 连接数据库
			Connection conn = DriverManager.getConnection(url);
			DatabaseMetaData metaData = conn.getMetaData();
			// sys_user对应表名,使用%表示所有表
			ResultSet tables = metaData.getTables(database, "%", table, new String[]{"TABLE"});
			while(tables.next()) {
				FileOutputStream fop = null;
				File file;
				// sys_user
				String tableName = tables.getString("TABLE_NAME");
				String tableRemarks = tables.getString("REMARKS");
				// User
				String name = UnderlineToHump2(tableName.substring(tableName.indexOf("_") + 1));

				file = new File("D:/" + name + "Dao.java");
				fop = new FileOutputStream(file);
				if (!file.exists()) {
					file.createNewFile();
				}

				fop.write(("package " + daoPath + ";").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("import java.util.Map;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import java.util.List;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("import org.apache.ibatis.annotations.Param;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("import com.github.pagehelper.Page;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("/**").getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @author " + author).getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @date " + getYmd()).getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @description " + tableRemarks + "Dao").getBytes());
				fop.write(newLine.getBytes());
				fop.write((" */").getBytes());
				fop.write(newLine.getBytes());

				fop.write(("public interface " + name + "Dao {").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("    int insert(@Param(\"mapInsert\") Map<String, Object> mapInsert);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    int deleteById(String id);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    int update(Map<String, Object> mapUpdate, String id);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    Map<String, Object> getById(String id);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    Page<Map<String, Object>> list(Map<String, Object> mapEqual, Map<String, Object> mapLike, String dataSchema, Map<String, List<Map<String, Object>>> dataScope);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("}").getBytes());
				fop.write(newLine.getBytes());

				fop.flush();
				fop.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public static void daoXmlUseEntity() {
		try {
			// 加载驱动
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// 连接数据库
			Connection conn = DriverManager.getConnection(url);
			DatabaseMetaData metaData = conn.getMetaData();
			// sys_user对应表名,使用%表示所有表
			ResultSet tables = metaData.getTables(database, "%", table, new String[]{"TABLE"});
			while(tables.next()) {
				FileOutputStream fop = null;
				File file;
				// sys_user
				String tableName = tables.getString("TABLE_NAME");
				// User
				String name = UnderlineToHump2(tableName.substring(tableName.indexOf("_") + 1));

				file = new File("D:/" + name + "Dao.xml");
				fop = new FileOutputStream(file);
				if (!file.exists()) {
					file.createNewFile();
				}

				fop.write(("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">").getBytes());
				fop.write(newLine.getBytes());

				fop.write(("<mapper namespace=\"" + daoPath + "." + name + "Dao\">").getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    <resultMap id=\"resultMap\" type=\"" + entityPath + "." + name + "Entity\">").getBytes());
				fop.write(newLine.getBytes());
				ResultSet columns = metaData.getColumns(database, "%", tableName, "%");
				while(columns.next()) {
					String columnName = columns.getString("COLUMN_NAME");
					if(columnName.equals("id")) {
						fop.write(("        <id property=\"id\" column=\"id\" />").getBytes());
					}else if(columnName.equals("del")) {
						continue;
					}else {
						fop.write(("        <result property=\"" + UnderlineToHump(columnName) + "\" column=\"" + columnName + "\" />").getBytes());
					}
					fop.write(newLine.getBytes());
				}
				fop.write(("    </resultMap>").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("    <sql id=\"defaultColumn\">").getBytes());
				columns = metaData.getColumns(database, "%", tableName, "%");
				boolean tag = false;
				while(columns.next()) {
					String columnName = columns.getString("COLUMN_NAME");
					if(columnName.equals("del")) {
						continue;
					}
					if(tag) {
						fop.write((",").getBytes());
						fop.write(newLine.getBytes());
					}else {
						fop.write(newLine.getBytes());
						tag = true;
					}
					fop.write(("        " + tableName + "." + columnName + "").getBytes());
				}
				fop.write(newLine.getBytes());
				fop.write(("    </sql>").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("    <insert id=\"insert\">").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        INSERT INTO " + tableName + " <include refid=\"com.brt.bref.common.dao.CommonDao.mapInsert\"/>").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    </insert>").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("    <update id=\"deleteById\">").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        UPDATE " + tableName + " SET del = 1 WHERE id = #{id}").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    </update>").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("    <update id=\"update\">").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        UPDATE " + tableName + " SET <include refid=\"com.brt.bref.common.dao.CommonDao.mapUpdate\"/> WHERE id = #{id}").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    </update>").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("    <select id=\"getById\" resultMap=\"resultMap\">").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        SELECT <include refid=\"defaultColumn\"/>").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        FROM " + tableName).getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        WHERE " + tableName + ".id = #{id}").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    </select>").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("    <select id=\"list\" resultMap=\"resultMap\">").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        SELECT <include refid=\"com.brt.bref.common.dao.CommonDao.dataSchema\"/>").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        FROM " + tableName).getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        <where>").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            <include refid=\"com.brt.bref.common.dao.CommonDao.mapEqual\"/>").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            <include refid=\"com.brt.bref.common.dao.CommonDao.mapLike\"/>").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            <include refid=\"com.brt.bref.common.dao.CommonDao.dataScope\"/>").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        </where>").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    </select>").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("</mapper>").getBytes());
				fop.write(newLine.getBytes());

				fop.flush();
				fop.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void daoXmlUseMap() {
		try {
			// 加载驱动
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// 连接数据库
			Connection conn = DriverManager.getConnection(url);
			DatabaseMetaData metaData = conn.getMetaData();
			// sys_user对应表名,使用%表示所有表
			ResultSet tables = metaData.getTables(database, "%", table, new String[]{"TABLE"});
			while(tables.next()) {
				FileOutputStream fop = null;
				File file;
				// sys_user
				String tableName = tables.getString("TABLE_NAME");
				// User
				String name = UnderlineToHump2(tableName.substring(tableName.indexOf("_") + 1));

				file = new File("D:/" + name + "Dao.xml");
				fop = new FileOutputStream(file);
				if (!file.exists()) {
					file.createNewFile();
				}

				fop.write(("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">").getBytes());
				fop.write(newLine.getBytes());

				fop.write(("<mapper namespace=\"" + daoPath + "." + name + "Dao\">").getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    <resultMap id=\"resultMap\" type=\"java.util.Map\">").getBytes());
				fop.write(newLine.getBytes());
				ResultSet columns = metaData.getColumns(database, "%", tableName, "%");
				while(columns.next()) {
					String columnName = columns.getString("COLUMN_NAME");
					if(columnName.equals("id")) {
						fop.write(("        <id property=\"id\" column=\"id\" />").getBytes());
					}else if(columnName.equals("del")) {
						continue;
					}else {
						fop.write(("        <result property=\"" + UnderlineToHump(columnName) + "\" column=\"" + columnName + "\" />").getBytes());
					}
					fop.write(newLine.getBytes());
				}
				fop.write(("    </resultMap>").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("    <sql id=\"defaultColumn\">").getBytes());
				columns = metaData.getColumns(database, "%", tableName, "%");
				boolean tag = false;
				while(columns.next()) {
					String columnName = columns.getString("COLUMN_NAME");
					if(columnName.equals("del")) {
						continue;
					}
					if(tag) {
						fop.write((",").getBytes());
						fop.write(newLine.getBytes());
					}else {
						fop.write(newLine.getBytes());
						tag = true;
					}
					fop.write(("        " + tableName + "." + columnName + "").getBytes());
				}
				fop.write(newLine.getBytes());
				fop.write(("    </sql>").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("    <insert id=\"insert\">").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        INSERT INTO " + tableName + " <include refid=\"com.brt.bref.common.dao.CommonDao.mapInsert\"/>").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    </insert>").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("    <update id=\"deleteById\">").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        UPDATE " + tableName + " SET del = 1 WHERE id = #{id}").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    </update>").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("    <update id=\"update\">").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        UPDATE " + tableName + " SET <include refid=\"com.brt.bref.common.dao.CommonDao.mapUpdate\"/> WHERE id = #{id}").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    </update>").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("    <select id=\"getById\" resultMap=\"resultMap\">").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        SELECT <include refid=\"defaultColumn\"/>").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        FROM " + tableName).getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        WHERE " + tableName + ".id = #{id}").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    </select>").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("    <select id=\"list\" resultMap=\"resultMap\">").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        SELECT <include refid=\"com.brt.bref.common.dao.CommonDao.dataSchema\"/>").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        FROM " + tableName).getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        <where>").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            <include refid=\"com.brt.bref.common.dao.CommonDao.mapEqual\"/>").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            <include refid=\"com.brt.bref.common.dao.CommonDao.mapLike\"/>").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            <include refid=\"com.brt.bref.common.dao.CommonDao.dataScope\"/>").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        </where>").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    </select>").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("</mapper>").getBytes());
				fop.write(newLine.getBytes());

				fop.flush();
				fop.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public static void serviceUseEntity() {
		try {
			// 加载驱动
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// 连接数据库
			Connection conn = DriverManager.getConnection(url);
			DatabaseMetaData metaData = conn.getMetaData();
			// sys_user对应表名,使用%表示所有表
			ResultSet tables = metaData.getTables(database, "%", table, new String[]{"TABLE"});
			while(tables.next()) {
				FileOutputStream fop = null;
				File file;
				// sys_user
				String tableName = tables.getString("TABLE_NAME");
				String tableRemarks = tables.getString("REMARKS");
				// User
				String name = UnderlineToHump2(tableName.substring(tableName.indexOf("_") + 1));

				file = new File("D:/" + name + "Service.java");
				fop = new FileOutputStream(file);
				if (!file.exists()) {
					file.createNewFile();
				}

				fop.write(("package " + servicePath + ";").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("import java.util.Map;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("import com.github.pagehelper.Page;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(("import " + entityPath + "." + name + "Entity;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("/**").getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @author " + author).getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @date " + getYmd()).getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @description " + tableRemarks + "Service接口").getBytes());
				fop.write(newLine.getBytes());
				fop.write((" */").getBytes());
				fop.write(newLine.getBytes());

				fop.write(("public interface " + name + "Service {").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("    int insert(Map<String, Object> mapInsert);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    int deleteById(String id);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    int update(Map<String, Object> mapUpdate, String id);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    " + name + "Entity getById(String id);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    Page<" + name + "Entity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("}").getBytes());
				fop.write(newLine.getBytes());

				fop.flush();
				fop.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void serviceUseMap() {
		try {
			// 加载驱动
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// 连接数据库
			Connection conn = DriverManager.getConnection(url);
			DatabaseMetaData metaData = conn.getMetaData();
			// sys_user对应表名,使用%表示所有表
			ResultSet tables = metaData.getTables(database, "%", table, new String[]{"TABLE"});
			while(tables.next()) {
				FileOutputStream fop = null;
				File file;
				// sys_user
				String tableName = tables.getString("TABLE_NAME");
				String tableRemarks = tables.getString("REMARKS");
				// User
				String name = UnderlineToHump2(tableName.substring(tableName.indexOf("_") + 1));

				file = new File("D:/" + name + "Service.java");
				fop = new FileOutputStream(file);
				if (!file.exists()) {
					file.createNewFile();
				}

				fop.write(("package " + servicePath + ";").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("import java.util.Map;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("import com.github.pagehelper.Page;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("/**").getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @author " + author).getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @date " + getYmd()).getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @description " + tableRemarks + "Service接口").getBytes());
				fop.write(newLine.getBytes());
				fop.write((" */").getBytes());
				fop.write(newLine.getBytes());

				fop.write(("public interface " + name + "Service {").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("    int insert(Map<String, Object> mapInsert);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    int deleteById(String id);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    int update(Map<String, Object> mapUpdate, String id);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    Map<String, Object> getById(String id);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    Page<Map<String, Object>> list(Map<String, Object> mapEqual, Map<String, Object> mapLike);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("}").getBytes());
				fop.write(newLine.getBytes());

				fop.flush();
				fop.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public static void serviceImplUseEntity() {
		try {
			// 加载驱动
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// 连接数据库
			Connection conn = DriverManager.getConnection(url);
			DatabaseMetaData metaData = conn.getMetaData();
			// sys_user对应表名,使用%表示所有表
			ResultSet tables = metaData.getTables(database, "%", table, new String[]{"TABLE"});
			while(tables.next()) {
				FileOutputStream fop = null;
				File file;
				// sys_user
				String tableName = tables.getString("TABLE_NAME");
				String tableRemarks = tables.getString("REMARKS");
				// User
				String name = UnderlineToHump2(tableName.substring(tableName.indexOf("_") + 1));

				file = new File("D:/" + name + "ServiceImpl.java");
				fop = new FileOutputStream(file);
				if (!file.exists()) {
					file.createNewFile();
				}

				fop.write(("package " + servicePath + ".impl;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("import java.util.Map;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("import org.springframework.beans.factory.annotation.Autowired;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import org.springframework.stereotype.Service;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("import com.github.pagehelper.Page;").getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(newLine.getBytes());

				fop.write(("import " + entityPath + "." + name + "Entity;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import " + daoPath + "." + name + "Dao;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import " + servicePath + "." + name + "Service;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("/**").getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @author " + author).getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @date " + getYmd()).getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @description " + tableRemarks + "Service接口实现类").getBytes());
				fop.write(newLine.getBytes());
				fop.write((" */").getBytes());
				fop.write(newLine.getBytes());

				fop.write(("@Service").getBytes());
				fop.write(newLine.getBytes());

				fop.write(("public class " + name + "ServiceImpl implements " + name + "Service {").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("    @Autowired").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    private " + name + "Dao " + toLowerCaseFirstOne(name) + "Dao;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("    @Override").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    public int insert(Map<String, Object> mapInsert) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        return " + toLowerCaseFirstOne(name) + "Dao.insert(mapInsert);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    @Override").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    public int deleteById(String id) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        return " + toLowerCaseFirstOne(name) + "Dao.deleteById(id);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    @Override").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    public int update(Map<String, Object> mapUpdate, String id) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        return " + toLowerCaseFirstOne(name) + "Dao.update(mapUpdate, id);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    @Override").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    public " + name + "Entity getById(String id) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        return " + toLowerCaseFirstOne(name) + "Dao.getById(id);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    @Override").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    public Page<" + name + "Entity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        return " + toLowerCaseFirstOne(name) + "Dao.list(mapEqual, mapLike, null, null);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("}").getBytes());
				fop.write(newLine.getBytes());

				fop.flush();
				fop.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void serviceImplUseMap() {
		try {
			// 加载驱动
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// 连接数据库
			Connection conn = DriverManager.getConnection(url);
			DatabaseMetaData metaData = conn.getMetaData();
			// sys_user对应表名,使用%表示所有表
			ResultSet tables = metaData.getTables(database, "%", table, new String[]{"TABLE"});
			while(tables.next()) {
				FileOutputStream fop = null;
				File file;
				// sys_user
				String tableName = tables.getString("TABLE_NAME");
				String tableRemarks = tables.getString("REMARKS");
				// User
				String name = UnderlineToHump2(tableName.substring(tableName.indexOf("_") + 1));

				file = new File("D:/" + name + "ServiceImpl.java");
				fop = new FileOutputStream(file);
				if (!file.exists()) {
					file.createNewFile();
				}

				fop.write(("package " + servicePath + ".impl;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("import java.util.Map;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("import org.springframework.beans.factory.annotation.Autowired;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import org.springframework.stereotype.Service;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("import com.github.pagehelper.Page;").getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(newLine.getBytes());

				fop.write(("import " + daoPath + "." + name + "Dao;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import " + servicePath + "." + name + "Service;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("/**").getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @author " + author).getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @date " + getYmd()).getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @description " + tableRemarks + "Service接口实现类").getBytes());
				fop.write(newLine.getBytes());
				fop.write((" */").getBytes());
				fop.write(newLine.getBytes());

				fop.write(("@Service").getBytes());
				fop.write(newLine.getBytes());

				fop.write(("public class " + name + "ServiceImpl implements " + name + "Service {").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("    @Autowired").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    private " + name + "Dao " + toLowerCaseFirstOne(name) + "Dao;").getBytes());
				fop.write(newLine.getBytes());

				fop.write(newLine.getBytes());

				fop.write(("    @Override").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    public int insert(Map<String, Object> mapInsert) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        return " + toLowerCaseFirstOne(name) + "Dao.insert(mapInsert);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    @Override").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    public int deleteById(String id) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        return " + toLowerCaseFirstOne(name) + "Dao.deleteById(id);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    @Override").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    public int update(Map<String, Object> mapUpdate, String id) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        return " + toLowerCaseFirstOne(name) + "Dao.update(mapUpdate, id);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    @Override").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    public Map<String, Object> getById(String id) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        return " + toLowerCaseFirstOne(name) + "Dao.getById(id);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("    @Override").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    public Page<Map<String, Object>> list(Map<String, Object> mapEqual, Map<String, Object> mapLike) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        return " + toLowerCaseFirstOne(name) + "Dao.list(mapEqual, mapLike, null, null);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());

				fop.write(("}").getBytes());
				fop.write(newLine.getBytes());

				fop.flush();
				fop.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void controllerUseMap() {
		try {
			// 加载驱动
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// 连接数据库
			Connection conn = DriverManager.getConnection(url);
			DatabaseMetaData metaData = conn.getMetaData();
			// sys_user对应表名,使用%表示所有表
			ResultSet tables = metaData.getTables(database, "%", table, new String[]{"TABLE"});
			while(tables.next()) {
				FileOutputStream fop = null;
				File file;
				// sys_user
				String tableName = tables.getString("TABLE_NAME");
				String tableRemarks = tables.getString("REMARKS");
				// User
				String name = UnderlineToHump2(tableName.substring(tableName.indexOf("_") + 1));
				
				file = new File("D:/" + name + "Controller.java");
				fop = new FileOutputStream(file);
				if (!file.exists()) {
					file.createNewFile();
				}
				
				fop.write(("package " + controllerPath + ";").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(("import java.util.HashMap;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import java.util.Map;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(("import org.apache.commons.lang3.StringUtils;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import org.springframework.beans.factory.annotation.Autowired;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import org.springframework.web.bind.annotation.PathVariable;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import org.springframework.web.bind.annotation.RequestMapping;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import org.springframework.web.bind.annotation.RequestMethod;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import org.springframework.web.bind.annotation.RequestParam;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import org.springframework.web.bind.annotation.RestController;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(("import com.alibaba.fastjson.JSON;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import com.alibaba.fastjson.JSONObject;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import com.github.pagehelper.Page;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import com.github.pagehelper.PageHelper;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(("import com.brt.bref.common.util.ResponseUtil;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import com.brt.bref.common.util.ValidateUtil;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("import " + servicePath + "." + name + "Service;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(("/**").getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @author " + author).getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @date " + getYmd()).getBytes());
				fop.write(newLine.getBytes());
				fop.write((" * @description " + tableRemarks + "Controller层").getBytes());
				fop.write(newLine.getBytes());
				fop.write((" */").getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(("@RestController").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("public class " + name + "Controller {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(("    @Autowired").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    private " + name + "Service " + toLowerCaseFirstOne(name) + "Service;").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(("    /**").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @author " + author).getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @date " + getYmd()).getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @param info " + tableRemarks + "信息").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @return 是否成功").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @description 新增" + tableRemarks).getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     */").getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(("    @RequestMapping(value = \"/" + toLowerCaseFirstOne(name) + "\", method = RequestMethod.POST)").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    public JSONObject insert(String info) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        // 验证参数不为空").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        if(StringUtils.isBlank(info)) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        return ResponseUtil.responseResult(false, \"参数[info]不能为空\", null);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        }else {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            JSONObject infoJson = JSON.parseObject(info);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            Map<String,Object> mapInsert = new HashMap<>();").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            if(" + toLowerCaseFirstOne(name) + "Service.insert(mapInsert) > 0) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("                return ResponseUtil.responseResult(true, \"新增成功\", null);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            }else {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("                return ResponseUtil.responseResult(false, \"新增失败\", null);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(("    /**").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @author " + author).getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @date " + getYmd()).getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @param id " + tableRemarks + "id").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @return 是否成功").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @description 根据id软删除" + tableRemarks).getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     */").getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(("    @RequestMapping(value = \"/" + toLowerCaseFirstOne(name) + "/{id}\", method = RequestMethod.DELETE)").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    public JSONObject delete(@PathVariable(\"id\") String id) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        if(!ValidateUtil.checkLength(id, 1, 36)) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            return ResponseUtil.responseResult(false, \"参数[id]不符合规则\", null);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        }else {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            if(" + toLowerCaseFirstOne(name) + "Service.deleteById(id) > 0) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("                return ResponseUtil.responseResult(true, \"删除成功\", null);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            }else {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("                return ResponseUtil.responseResult(false, \"删除失败\", null);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(("    /**").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @author " + author).getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @date " + getYmd()).getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @param info 需要修改的信息").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @param id " + tableRemarks + "id").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @return 是否成功").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @description 根据id修改" + tableRemarks).getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     */").getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(("    @RequestMapping(value = \"/" + toLowerCaseFirstOne(name) + "/{id}\", method = RequestMethod.PUT)").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    public JSONObject update(@RequestParam(\"info\") String info, @PathVariable(\"id\") String id) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        if(StringUtils.isBlank(info)) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            return ResponseUtil.responseResult(false, \"参数[info]不能为空\", null);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        }else if(!ValidateUtil.checkLength(id, 1, 36)){").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            return ResponseUtil.responseResult(false, \"参数[id]不符合规则\", null);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        }else {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            JSONObject infoJson = JSON.parseObject(info);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            Map<String, Object> mapUpdate = new HashMap<>();").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            if(" + toLowerCaseFirstOne(name) + "Service.update(mapUpdate, id) > 0) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("                return ResponseUtil.responseResult(true, \"修改成功\", null);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            }else {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("                return ResponseUtil.responseResult(false, \"修改失败\", null);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(("    /**").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @author " + author).getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @date " + getYmd()).getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @param id " + tableRemarks + "id").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @return " + tableRemarks).getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @description 根据id查询" + tableRemarks).getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     */").getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(("    @RequestMapping(value = \"/" + toLowerCaseFirstOne(name) + "/{id}\", method = RequestMethod.GET)").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    public JSONObject getById(@PathVariable(\"id\") String id) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        if(!ValidateUtil.checkLength(id, 1, 36)) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            return ResponseUtil.responseResult(false, \"参数[id]不符合规则\", null);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        }else {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            Map<String, Object> " + toLowerCaseFirstOne(name) + " = " + toLowerCaseFirstOne(name) + "Service.getById(id);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            if(" + toLowerCaseFirstOne(name) + " == null) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("                return ResponseUtil.responseResult(false, \"查询失败\", null);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            }else {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("                return ResponseUtil.responseResult(true, \"查询成功\", " + toLowerCaseFirstOne(name) + ");").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    }").getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(("    /**").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @author " + author).getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @date " + getYmd()).getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @param searchParam 搜索参数").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @param pageParam 分页参数").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @return " + tableRemarks + "集合").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     * @description 分页搜索" + tableRemarks).getBytes());
				fop.write(newLine.getBytes());
				fop.write(("     */").getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(("    @RequestMapping(value = \"/" + toLowerCaseFirstOne(name) + "\", method = RequestMethod.GET)").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    public JSONObject list(String searchParam, String pageParam) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        // 验证pageParam参数是否合法").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        if(StringUtils.isNotBlank(pageParam)) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            JSONObject pageJson = JSON.parseObject(pageParam);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            if(!pageJson.containsKey(\"pageNo\") || pageJson.getInteger(\"pageNo\") < 1) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("                return ResponseUtil.responseResult(false, \"参数[pageParam.pageNo]不符合规则\", null);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            if(!pageJson.containsKey(\"pageSize\") || pageJson.getInteger(\"pageSize\") < 1) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("                return ResponseUtil.responseResult(false, \"参数[pageParam.pageSize]不符合规则\", null);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            PageHelper.startPage(pageJson.getInteger(\"pageNo\"),pageJson.getInteger(\"pageSize\"));").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        Map<String,Object> mapEqual = new HashMap<>();").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        Map<String,Object> mapLike = new HashMap<>();").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        // 验证searchParam参数是否合法").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        if(StringUtils.isNotBlank(searchParam)) {").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("            JSONObject searchJson = JSON.parseObject(searchParam);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        Page<Map<String,Object>> list = " + toLowerCaseFirstOne(name) + "Service.list(mapEqual, mapLike);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("        return ResponseUtil.responseResult(true, \"查询成功\", list);").getBytes());
				fop.write(newLine.getBytes());
				fop.write(("    }").getBytes());
				fop.write(newLine.getBytes());
				fop.write(newLine.getBytes());
				
				fop.write(("}").getBytes());
				fop.write(newLine.getBytes());
				
				fop.flush();
				fop.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public static String toLowerCaseFirstOne(String s){
		if(Character.isLowerCase(s.charAt(0))) {
			return s;
		} else {
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
		}
	}
	
	public static String UnderlineToHump(String para){
		StringBuilder result=new StringBuilder();
		String a[]=para.split("_");
		for(String s:a){
			if(result.length()==0){
				result.append(s.toLowerCase());
			}else{
				result.append(s.substring(0, 1).toUpperCase());
				result.append(s.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}
	
	public static String UnderlineToHump2(String para){
		StringBuilder result=new StringBuilder();
		String a[]=para.split("_");
		for(String s:a){
			result.append(s.substring(0, 1).toUpperCase());
			result.append(s.substring(1).toLowerCase());
		}
		return result.toString();
	}
	
	public static String getYmd() {
		System.setProperty("user.timezone","Asia/Shanghai");
		Calendar now = Calendar.getInstance();
		String ymd = now.get(Calendar.YEAR) + "年" + (now.get(Calendar.MONTH) + 1) + "月" + now.get(Calendar.DAY_OF_MONTH) + "日";
		return ymd;
	}
}
