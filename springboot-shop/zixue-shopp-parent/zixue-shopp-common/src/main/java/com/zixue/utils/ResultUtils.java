package com.zixue.utils;

import java.util.Map;

import com.zixue.constants.BaseApiConstants;

/**
 * 
 * @author houdo 获取结果包里面的data数据
 */
public class ResultUtils {

	public static Object getResultMap(Map<String, Object> reusltItemDesc) {
		Integer code = (Integer) reusltItemDesc.get(BaseApiConstants.HTTP_CODE_NAME);
		if (code.equals(BaseApiConstants.HTTP_200_CODE)) {
			Object object = reusltItemDesc.get("data");
			return object;
		}
		return null;
	}
	public static Object getResultMsg(Map<String, Object> reusltItemDesc) {
		Integer code = (Integer) reusltItemDesc.get(BaseApiConstants.HTTP_CODE_NAME);
		if (code.equals(BaseApiConstants.HTTP_200_CODE)) {
			Object object = reusltItemDesc.get("msg");
			return object;
		}
		return null;
	}
}
