package com.brt.bref.common.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author 方杰
 * @date 2019年1月11日
 * @description redis数据权限过滤工具
 */
public class RedisDataPermissionUtil {
	
	/**
	 * @author 方杰
	 * @date 2019年1月11日
	 * @param tree 树
	 * @param dataSchema 数据权限列
	 * @param dataScope 数据权限行
	 * @return JSONObject {list tree ids}
	 * @description 对树进行数据权限过滤,目前只能过滤行id
	 */
	public static JSONObject filterData(JSONArray tree, String dataSchema, Map<String,List<Map<String, Object>>> dataScope) {
		JSONArray listResult;
		if(dataScope == null) {
			listResult = TreeUtil.treeToJSONArray(tree, "children");
		}else {
			listResult = dataScope(tree,dataScope);
		}
		if(listResult != null && !listResult.isEmpty()) {
			if(StringUtils.isNotBlank(dataSchema)) {
				listResult = dataSchema(listResult, dataSchema);
			}
			JSONArray treeResult = TreeUtil.jsonArrayToTree(listResult, "id", "parentId");
			String[] ids = CollectionUtil.getKeys(listResult, "id");
			JSONObject result = new JSONObject();
			result.put("list", listResult);
			result.put("tree", treeResult);
			result.put("ids", ids);
			return result;
		}else {
			return null;
		}
	}
	
	/**
	 * @author 方杰
	 * @date 2019年1月11日
	 * @param list 集合
	 * @param dataSchema 列权限
	 * @return 过滤后的list集合
	 * @description 过滤数据权限列权限
	 */
	private static JSONArray dataSchema(JSONArray list, String dataSchema) {
		JSONArray result = new JSONArray();
		String[] columns = StringUtil.analysisDataSchema(dataSchema);
		for(int i = 0; i < list.size(); i++) {
			JSONObject item = list.getJSONObject(i);
			JSONObject newItem = new JSONObject();
			for(int j = 0; j < columns.length; j++) {
				String column = columns[j];
				if(item.containsKey(column)) {
					newItem.put(column, item.get(column));
				}
			}
			result.add(newItem);
		}
		return result;
	}
	
	/**
	 * @author 方杰
	 * @date 2019年1月11日
	 * @param tree 树
	 * @param dataScope
	 * @return 过滤后的list
	 * @description 对树进行数据行权限过滤返回list
	 */
	private static JSONArray dataScope(JSONArray tree, Map<String,List<Map<String, Object>>> dataScope) {
		JSONArray listResult = new JSONArray();
		for(int i = 0; i < tree.size(); i++) {
			JSONObject node = tree.getJSONObject(i);
			int tag = 0;
			for(Entry<String, List<Map<String, Object>>> column: dataScope.entrySet()) {
				String columnName = StringUtil.analysisColumn(column.getKey());
				if(columnName.equals("id")) {
					for(Map<String, Object> item : column.getValue()) {
						String operator = (String) item.get("operator");
						String dataScopeContent = (String) item.get("dataScope");
						if("=".equals(operator) && tag < 1) {
							if(EqualSign(node, columnName, dataScopeContent)) {
								tag = 1;
							}
						}else if("in".equalsIgnoreCase(operator) && tag < 1) {
							if(InSign(node, columnName, dataScopeContent)) {
								tag = 1;
							}
						}else if("like".equalsIgnoreCase(operator) && tag < 2) {
							if(LikeSign(node, columnName, dataScopeContent)) {
								tag = 2;
								break;
							}
						}
					}
				}
			}
			if(tag == 0) {
				if(node.containsKey("children") && node.getJSONArray("children") != null) {
					listResult.addAll(dataScope(node.getJSONArray("children"),dataScope));
				}
			}else if(tag == 1) {
				if(node.containsKey("children") && node.getJSONArray("children") != null) {
					listResult.addAll(dataScope(node.getJSONArray("children"),dataScope));
				}
				JSONObject newNode = new JSONObject();
				newNode = node;
				newNode.remove("children");
				listResult.add(newNode);
			}else if(tag == 2) {
				JSONObject newNode = new JSONObject();
				newNode = node;
				if(node.containsKey("children") && node.getJSONArray("children") != null) {
					listResult.addAll(TreeUtil.treeToJSONArray(node.getJSONArray("children"), "children"));
				}
				newNode.remove("children");
				listResult.add(newNode);
			}
		}
		return listResult;
	}
	
	/**
	 * @author 方杰
	 * @date 2019年1月11日
	 * @param node JSONObject对象
	 * @param columnName 字段名
	 * @param dataScopeContent 数据权限内容
	 * @return 是否匹配
	 * @description operator为=时，验证是否匹配
	 */
	private static boolean EqualSign(JSONObject node, String columnName, String dataScopeContent) {
		if(dataScopeContent.replaceAll("'", "").equals(node.getString(columnName))) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * @author 方杰
	 * @date 2019年1月11日
	 * @param node JSONObject对象
	 * @param columnName 字段名
	 * @param dataScopeContent 数据权限内容
	 * @return 是否匹配
	 * @description operator为in时，验证是否匹配
	 */
	private static boolean InSign(JSONObject node, String columnName, String dataScopeContent) {
		String[] strs = dataScopeContent.trim().replaceAll("'|\\(|\\)", "").split(",");
		String str = node.getString(columnName);
		for(int i = 0; i < strs.length; i++) {
			if(str.equals(strs[i])) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @author 方杰
	 * @date 2019年1月11日
	 * @param node JSONObject对象
	 * @param columnName 字段名
	 * @param dataScopeContent 数据权限内容
	 * @return 是否匹配
	 * @description operator为like时，验证是否匹配
	 */
	private static boolean LikeSign(JSONObject node, String columnName, String dataScopeContent) {
		if(dataScopeContent.replaceAll("'|%", "").equals(node.getString(columnName))) {
			return true;
		}else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		
	}
}
