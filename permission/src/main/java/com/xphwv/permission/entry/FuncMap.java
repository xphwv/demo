package com.xphwv.permission.entry;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class FuncMap implements Serializable {
	private static final long serialVersionUID = -5420578907392924951L;
	private Map<String, Boolean> funcCodeMap = new Hashtable<String, Boolean>();
	private Map<Integer, Boolean> funcIdMap = new Hashtable<Integer, Boolean>();

	/**
	 * 传入功能列表
	 * 
	 * @param funcIds
	 */
	public FuncMap(List<Func> funcs) {
		if (funcs != null) {
			for (Func func : funcs) {
				if (func != null && func.getCode() != null) {
					funcCodeMap.put(func.getCode(), true);
					funcIdMap.put(func.getFuncId(), true);
				}
			}
		}
	}

	/**
	 * 检查权限
	 * 
	 * @param funcId
	 * @return
	 */
	public boolean checkFunc(Integer funcId) {
		if (funcIdMap.containsKey(funcId)) {
			return funcIdMap.get(funcId);
		} else {
			return false;
		}
	}

	/**
	 * 只要有一个为真则返回true
	 * 
	 * @param funcIds
	 * @return
	 */
	public boolean checkFuncOr(Integer[] funcIds) {
		for (Integer funcId : funcIds) {
			if (checkFunc(funcId))
				return true;
		}
		return false;
	}

	/**
	 * 只要有一个为假则返回false
	 * 
	 * @param funcIds
	 * @return
	 */
	public boolean checkFuncAnd(Integer[] funcIds) {
		for (Integer funcId : funcIds) {
			if (!checkFunc(funcId))
				return false;
		}
		return true;
	}

	/**
	 * 检查权限
	 * 
	 * @param funcCode
	 * @return
	 */
	public boolean checkFunc(String funcCode) {
		if (funcCodeMap.containsKey(funcCode)) {
			return funcCodeMap.get(funcCode);
		} else {
			return false;
		}
	}

	/**
	 * 只要有一个为真则返回true
	 * 
	 * @param funcCodes
	 * @return
	 */
	public boolean checkFuncOr(String[] funcCodes) {
		for (String funcCode : funcCodes) {
			if (checkFunc(funcCode))
				return true;
		}
		return false;
	}

	/**
	 * 只要有一个为假则返回false
	 * 
	 * @param funcCodes
	 * @return
	 */
	public boolean checkFuncAnd(String[] funcCodes) {
		for (String funcCode : funcCodes) {
			if (!checkFunc(funcCode))
				return false;
		}
		return true;
	}

	public Map<String, Boolean> getFuncCodeMap() {
		return funcCodeMap;
	}

	public Map<Integer, Boolean> getFuncIdMap() {
		return funcIdMap;
	}
}
