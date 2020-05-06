package com.barrage.api.builders;


import com.barrage.api.dao.CommonDto;
import com.barrage.api.enums.AuthEnums;
import com.barrage.api.enums.BarrageExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author TomShiDi
 * @Description
 * @Date 2020/3/25
 **/
public class ResponseDtoBuilder<T extends CommonDto> {

    /**
     * 最后将要返回的构造后的结果
     */
    private T result = null;

    private Class<T> clazz = null;

    private final Logger logger = LoggerFactory.getLogger(ResponseDtoBuilder.class);

    /**
     * 构造器 执行建造器的初始化工作
     * @param clazz 要生成的传输对象类型
     */
    public ResponseDtoBuilder(Class<T> clazz) {
        try {
            this.clazz = clazz;
            result = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("创建Builder失败,原因为: {}", e.getMessage());
        }
    }

    /**
     * 设置code字段
     * @param code 要设置的值
     * @return 返回当前类对象
     */
    public ResponseDtoBuilder code(Integer code) {
        result.setCode(code);
        return this;
    }

    /**
     * 设置message字段值
     * @param message 待设置的值
     * @return 返回当前类对象
     */
    public ResponseDtoBuilder message(String message) {
        result.setMessage(message);
        return this;
    }

    /**
     * 设置data字段值
     * @param data 待设置的值
     * @return 返回当前类对象
     */
    public ResponseDtoBuilder data(Object data) {
        result.setData(data);
        return this;
    }

    /**
     * 返回构造结构
     * @return 传输类对象
     */
    public T build() {
        return result;
    }

    /**
     * 枚举类型设值
     * @param barrageExceptionEnum 异常信息枚举入参
     * @return 当前类对象
     */
    public ResponseDtoBuilder enumSet(BarrageExceptionEnum barrageExceptionEnum) {
        result.setCode(barrageExceptionEnum.getCode());
        result.setMessage(barrageExceptionEnum.getMessage());
        return this;
    }

    /**
     * 枚举类型设值
     * @param authEnums 授权信息
     * @return
     */
    public ResponseDtoBuilder enumSet(AuthEnums authEnums) {
        result.setCode(authEnums.getCode());
        result.setMessage(authEnums.getMessage());
        return this;
    }

    /**
     * 针对子类的其他属性设值
     * @param paramName 属性名
     * @param value 待设值
     * @param paramTypes 值类型
     * @return 返回当前类对象
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public ResponseDtoBuilder exBuildMethod(String paramName, Object value, Class<?>... paramTypes)
            throws NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException {
        Method method = clazz.getMethod(buildMethodName(paramName), paramTypes);
        method.invoke(result, value);
        return this;
    }

    private String buildMethodName(String paramName) {
        return "set" + (paramName.charAt(0) + "").toUpperCase() + paramName.substring(1);
    }
}
