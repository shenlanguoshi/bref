package com.brt.bref.common.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class CollectionUtil {

	/**
	 * @author 蒋润
	 * @date 2018年11月24日
	 * @param list
	 * @return 去重后的list
	 * @description 去除list的重复数据
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List removeDuplicate(List list) {   
	    HashSet h = new HashSet(list);   
	    list.clear();   
	    list.addAll(h);   
	    return list;   
	}
	
	/**
	 * @author 蒋润
	 * @date 2018年11月20日
	 * @param map
	 * @return Map
	 * @description 遍历Map集合,将所有的Key值转为小写
	 */
	public static Map<String,Object> getMapLowerCase(Map<String,Object> map) {		 		
		Map<String,Object> newMap =  new HashMap<String, Object>();		
		Set<String> set = map.keySet();	
		for (Iterator<String> it = set.iterator(); it.hasNext();) {			
			String key = (String) it.next();			
			Object value = map.get(key);			
			newMap.put(key.toLowerCase(), value);		
		}		
		return newMap;	
	}
	
	/**
	 * @author 蒋润
	 * @date 2018年11月20日
	 * @param map
	 * @return Map
	 * @description 遍历Map集合,将所有的Key值转为大写
	 */
	public static Map<String,Object> getMapUpperCase(Map<String,Object> map) {				
		Map<String,Object> newMap =  new HashMap<String,Object>();		
		Set<String> set = map.keySet();				
		for (Iterator<String> it = set.iterator(); it.hasNext();) {			
			String key = (String) it.next();			
			Object value = map.get(key);			
			newMap.put(key.toUpperCase(), value);		
		}		
		return newMap;	
	}
	
	/**
	 * @author 蒋润
	 * @date 2018年11月20日
	 * @param map 待替换的Map
	 * @param keyName Key值
	 * @param renameKeyName 替换key值
	 * @return map
	 * @description 遍历Map集合,替换单个Key值
	 */
	public static  Map<String,Object> getMapChangeKey(Map<String,Object> map,String keyName,String renameKeyName) {				
		Map<String,Object> newMap =  new HashMap<String, Object>();		
		Set<String> set = map.keySet();				
		for (Iterator<String> it = set.iterator(); it.hasNext();) {			
			String key = (String) it.next();			
			Object value = map.get(key);			
			if (keyName.trim().equals(key)) {				
				key=renameKeyName;			
			}			
			newMap.put(key.toLowerCase(), value);		
		}		
		return newMap;	
	}
	
	/**
	 * @author 蒋润
	 * @date 2018年11月20日
	 * @param map 待替换的Map
	 * @param keyName Key值	
	 * @param renameKeyName 替换key值
	 * @return map
	 * @description 遍历Map集合,替换单个Key值
	 */
	public static  Map<String,Object> getMapChangeKeyNoLowerCase(Map<String,Object> map,String keyName,String renameKeyName) {				
		Map<String,Object> newMap =  new HashMap<String,Object>();		
		Set<String> set = map.keySet();				
		for (Iterator<String> it = set.iterator(); it.hasNext();) {			
			String key = (String) it.next();			
			Object value = map.get(key);			
			if (keyName.trim().equals(key)) {				
				key=renameKeyName;			
			}			
			newMap.put(key, value);		
		}		
		return newMap;	
	}
	
	/**
	 * @author 蒋润
	 * @date 2018年11月20日
	 * @param map 待替换的Map
	 * @param reMap Map<key值,替换Key值 >
	 * @return map
	 * @description 遍历Map集合,替换Key值
	 */
	public static  Map<String,Object> getMapChangeKey(Map<String,Object> map,Map<String,String > reMap) {				
		Map<String , Object> newMap =  new HashMap<String, Object>();		
		Set<String> set = map.keySet();				
		for (Iterator<String> it = set.iterator(); it.hasNext();) {			
			String key = (String) it.next();			
			Object value = map.get(key);			
			String reName=reMap.get(key)+"".trim();			
			if (!reName.equals("null")) {				
				key=reName;			
			}			
			newMap.put(key.toLowerCase(), value);		
		}		
		return newMap;	
	}
	
	/**
	 * @author 蒋润
	 * @date 2019年1月11日
	 * @param list JSONArray list
	 * @param key 需要组装的key 一般为id
	 * @return String[]
	 * @description 获取list的某个字段组装成string[]
	 */
	public static String[] getKeys(JSONArray list, String key) {
		String[] keys = new String[list.size()];
		for(int i = 0; i < list.size(); i++) {
			JSONObject item = list.getJSONObject(i);
			keys[i] = item.getString(key);
		}
		return keys;
	}
}
