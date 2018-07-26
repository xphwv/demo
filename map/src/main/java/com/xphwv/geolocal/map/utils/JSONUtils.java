package com.xphwv.geolocal.map.utils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class JSONUtils {
	private JSONUtils() {
	}

	public static Object parse(String text) {
		return JSON.parse(text);
	}

	public static JSONArray parseArray(String text) {
		return JSON.parseArray(text);
	}

	public static <T> List<T> parseArray(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz);
	}

	public static List<Object> parseArray(String text, Type[] types) {
		return JSON.parseArray(text, types);
	}

	public static JSONObject parseObject(String text) {
		return JSON.parseObject(text);
	}

	public static <T> T parseObject(String text, Class<T> clazz) {
		return JSON.parseObject(text, clazz);
	}

	public static <T> T toJavaObject(JSON json, Class<T> clazz) {
		return JSON.toJavaObject(json, clazz);
	}

	public static Object toJSON(Object javaObject) {
		return JSON.toJSON(javaObject);
	}

	public static String toJSONString(Object object) {
		return JSON.toJSONString(object);
	}

	public final static String toWSJSONString(Object object) {
		SerializeWriter out = new SerializeWriter();
		try {
			// 此处必须new一个SerializeConfig,防止修改默认的配置
			JSONSerializer serializer = new JSONSerializer(out, JSSerializeConfig.serializeConfig);
			serializer.write(object);
			return out.toString();
		} finally {
			out.close();
		}
		// return JSON.toJSONString(object, JSSerializeConfig.serializeConfig);
	}

	public static <T> T parseObject(String text, TypeReference<T> type, Feature... features) {
		return JSON.parseObject(text, type, features);
	}

	private final static class JSSerializeConfig {

		private final static class LongSerializer implements ObjectSerializer {
			@SuppressWarnings("unused")
			public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType) throws IOException {
				SerializeWriter out = serializer.getWriter();
				if (object == null) {
					out.write('0');
				}
				out.writeString(object.toString());
			}

			public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
				SerializeWriter out = serializer.getWriter();
				if (object == null) {
					out.write('0');
				}
				out.writeString(object.toString());
			}
		}

		private static com.alibaba.fastjson.serializer.SerializeConfig serializeConfig = new com.alibaba.fastjson.serializer.SerializeConfig();

		static {
			serializeConfig.put(Long.class, new LongSerializer());
			serializeConfig.put(long.class, new LongSerializer());
			serializeConfig.put(Integer.class, new LongSerializer());
			serializeConfig.put(int.class, new LongSerializer());
		}
	}
}
