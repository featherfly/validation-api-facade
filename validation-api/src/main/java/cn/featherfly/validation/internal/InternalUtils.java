
/*
 * All rights Reserved, Designed By zhongj
 * @Description:
 * @author: zhongj
 * @date: 2025-04-26 00:39:26
 * @Copyright: 2025 www.featherfly.cn Inc. All rights reserved.
 */
package cn.featherfly.validation.internal;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * InternalUtils.
 *
 * @author zhongj
 */
public class InternalUtils {

    public static String getMethodDescp(Method method) {
        StringBuilder sb = new StringBuilder();
        sb.append(method.getName()).append("(");
        boolean params = false;
        for (Parameter parameter : method.getParameters()) {
            sb.append(parameter.getType().getSimpleName()).append(" ").append(parameter.getName()).append(", ");
            params = true;
        }
        if (params) {
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.append(")").toString();
    }
}
