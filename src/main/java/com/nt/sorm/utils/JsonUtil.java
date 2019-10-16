package com.nt.sorm.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	/**
	 * 获取json底层节点字符串内容
	 * 
	 * @param key
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public static String getStrFromJson(String key, String jsonStr) {
		ObjectMapper mapper = new ObjectMapper();
		String result = null;
		try {
			JsonNode rootNode = mapper.readTree(jsonStr);
			result = rootNode.path(key).asText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取json底层节点int内容
	 * 
	 * @param key
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public static Integer getIntFromJson(String key, String jsonStr) {
		ObjectMapper mapper = new ObjectMapper();
		Integer result = null;
		try {
			JsonNode rootNode = mapper.readTree(jsonStr);
			result = rootNode.path(key).asInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}



}
