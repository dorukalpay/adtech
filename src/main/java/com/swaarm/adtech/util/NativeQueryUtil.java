package com.swaarm.adtech.util;


import com.swaarm.adtech.exception.BaseException;
import com.swaarm.adtech.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import javax.persistence.Tuple;
import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.util.*;

@Slf4j
public final class NativeQueryUtil {

    private NativeQueryUtil() {
    }

    public static <T> List<T> convertTupleResult(Iterable<Tuple> queryResult, Class<T> type) {
        if (queryResult != null && type != null) {
            try {
                List<Field> orderedFields = getFields(type);
                if (!orderedFields.isEmpty()) {
                    List<T> ret = new ArrayList<>();
                    for (Tuple columnValues : queryResult) {
                        ret.add(mapToDTO(type, orderedFields, columnValues));
                    }
                    return ret;
                }
            } catch (SecurityException | InstantiationException | IllegalAccessException ex) {
                throw new BaseException(ErrorCode.UNEXPECTED_ERROR, ex);
            }
            return Collections.emptyList();
        } else {
            throw new IllegalArgumentException("Parameters cannot be null.");
        }
    }

    private static <T> List<Field> getFields(Class<T> type) {
        Field[] fields = type.getDeclaredFields();
        List<Field> orderedFields = new LinkedList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            orderedFields.add(field);
        }
        return orderedFields;
    }

    private static <T> T mapToDTO(Class<T> type, List<Field> orderedFields, Tuple columnValues) throws InstantiationException, IllegalAccessException {
        T t = type.newInstance();
        for (Field field : orderedFields) {
            Object value = null;
            try {
                value = columnValues.get(field.getName());
            } catch (Exception ignored) {
            }
            value = value != null ? field.getType().isAssignableFrom(value.getClass()) ? value : convertColumnValue(value, field.getType()) : null;
            ReflectionUtils.setField(field, t, value);
        }
        return t;
    }


    private static <T> T convertColumnValue(Object value, Class<T> type) {
        if (type != null) {
            Object result;
            if (value != null) {

                if (Integer.class == type) {
                    result = Integer.parseInt(value.toString());
                } else if (Long.class == type) {
                    result = Long.parseLong(value.toString());
                } else if (String.class == type) {
                    result = value.toString();
                } else if (OffsetDateTime.class == type) {
                    result = OffsetDateTime.parse((String) value);
                } else if (UUID.class == type) {
                    result = UUID.fromString(value.toString());
                } else {
                    throw new UnsupportedOperationException(String.format("Conversion to %s is not implemented yet.", type.getName()));
                }
                return (T) result;
            }
            return null;
        } else {
            throw new IllegalArgumentException("Parameter type cannot be null.");
        }
    }

}

