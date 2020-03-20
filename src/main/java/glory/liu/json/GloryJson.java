package glory.liu.json;

import glory.liu.execption.JsonIsNullExecption;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 步骤：
 * 将对象传到
 * 规则：
 * key值用双引号
 * value:
 * String,char 双引号
 * bool:不用引号
 * 其他基本数据类型 不用引号
 * 数组key:[]
 * map key:[{k,v},{}...,{k,v}]
 */

public final class GloryJson {
    private final static int BASE_DATA = 1;
    private final static int STRING = 2;
    private final static int CHAR = 3;
    private final static int ARRAY = 4;
    private final static int LIST = 5;
    private final static int MAP = 6;
    private final static int OTHER = 7;//自定义
    private static boolean flag = false;//单纯的数组为true，其他情况为false

    private static int indexJson = 2;//这个是Json转对象是字符串索引

    public static String toGloryJsonString(Object obj) {
        flag = false;
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        Class clazz = obj.getClass();
        sb = objectToJson(obj, clazz, sb);
        sb.replace(sb.length() - 1, sb.length(), "}");
        return sb.toString();
    }

    private static StringBuilder objectToJson(Object obj, Class<?> clazz, StringBuilder sb) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
            if (m.getName().startsWith("get") || m.getName().startsWith("is")) {
                String mName = m.getName();
                Class<?> mReturn = m.getReturnType();
                String key;
                if (m.getName().startsWith("get"))
                    //字段名
                    key = String.format("%c%s", mName.charAt(3) + 32, mName.substring(4));
                else
                    key = String.format("%c%s", mName.charAt(2) + 32, mName.substring(3));
//                System.out.println(key);
                try {
                    Object value = m.invoke(obj);
                    String classTypeName = mReturn.getSimpleName();
                    if (mReturn.isArray()) {//返回的是数组
                        sb.append(String.format("\"%s\":", key));
                        int len = Array.getLength(value);
                        if (len > 0) {
                            sb.append(makeDataToJson(ARRAY, value));
                            //sb.replace(sb.length()-1,sb.length(),"],");//去最后的逗号加上]}
                        }
                    } else if (classTypeName.equals("char")) {
                        sb.append(String.format("\"%s\":\"%s\",", key, String.valueOf(value)));
                    } else if (mReturn.isPrimitive()) {//基本数据类型
                        sb.append(String.format("\"%s\":%s,", key, String.valueOf(value)));
                    } else if (classTypeName.equals("String")) {
                        sb.append(String.format("\"%s\":\"%s\",", key, value));
                    } else if (classTypeName.equals("List") || classTypeName.equals("ArrayList")) {
                        sb.append(String.format("\"%s\":", key));
                        sb.append(makeDataToJson(LIST, value));
                        sb.replace(sb.length() - 1, sb.length(), ",");
                    } else if (classTypeName.equals("Map")) {
                        sb.append(String.format("\"%s\":", key));
                        sb.append(makeDataToJson(MAP, value));
                        sb.replace(sb.length() - 1, sb.length(), ",");
                    } else if (!mReturn.isLocalClass()) {//自定义类
                        sb.append(String.format("\"%s\":%s", key, makeDataToJson(OTHER, value)));
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }
        return sb;
    }

    //对自定义类的处理
    private static StringBuilder handleSelfObject(Object sObj) {
        StringBuilder newSb = new StringBuilder();
        Class cls = sObj.getClass();
        newSb.append("{");
        newSb = objectToJson(sObj, cls, newSb);
        newSb.replace(newSb.length() - 1, newSb.length(), "},");
        return newSb;
    }

    /**
     * 对应方法与数据进行转Json
     */
    private static StringBuilder makeDataToJson(int dataType, Object value) {
        StringBuilder dataStr = new StringBuilder();
        int dType = 0;
        switch (dataType) {
            case BASE_DATA:
                if (flag)
                    dataStr.append(String.format("%s,", value));
                else
                    dataStr.append(String.format("%s", value));
                break;
            case STRING:
                dataStr.append(String.format("\"%s\",", value));
                break;
            case CHAR:
                dataStr.append(String.format("\"%s\",", value));
                break;
            case ARRAY:
                flag = true;
                int len1 = Array.getLength(value);
                if (len1 > 0) {
                    dType = getDataType(Array.get(value, 0));
                }
                dataStr.append("[");
                for (int i = 0; i < len1; i++) {
                    dataStr.append(makeDataToJson(dType, Array.get(value, i)));
                }
                dataStr.replace(dataStr.length() - 1, dataStr.length(), "],");
                break;
            case LIST:
                flag = true;
                List<?> data = (List<?>) value;
                int len2 = data.size();
                if (len2 > 0) {
                    dType = getDataType(data.get(0));
                }
                dataStr.append("[");
                for (int i = 0; i < len2; i++) {
                    dataStr.append(makeDataToJson(dType, data.get(i)));
                }
                dataStr.replace(dataStr.length() - 1, dataStr.length(), "],");
                break;
            case MAP:
                flag = false;
                int dt = 0;
                System.out.println(value);
                Map<?, ?> map = (Map<?, ?>) value;
                for (Object key : map.keySet()) {
                    Object v = map.get(key);
                    dt = getDataType(v);
                    break;
                }
                dataStr.append("{");
                for (Object k : map.keySet()) {
                    dataStr.append(String.format("\"%s\":%s,", k, makeDataToJson(dt, map.get(k)).toString()));
                }
                dataStr.replace(dataStr.length() - 1, dataStr.length(), "},");
                break;
            case OTHER:
                flag = false;
                dataStr = handleSelfObject(value);
                break;
        }
        return dataStr;
    }

    //获取数据类型
    private static int getDataType(Object data) {
        int dType = 0;
        if (data instanceof String || data instanceof Character)
            dType = STRING;
        else if (data instanceof Byte || data instanceof Short
                || data instanceof Integer || data instanceof Long
                || data instanceof Boolean || data instanceof Float
                || data instanceof Double)
            dType = BASE_DATA;
        else if (data instanceof List)
            dType = LIST;
        else if (data instanceof Array)
            dType = ARRAY;
        else if (data instanceof Map)
            dType = MAP;
        else if (!data.getClass().isLocalClass())
            dType = OTHER;
        return dType;
    }

    /**
     * 将json字符串转对象
     *
     * @param json 字符串
     * @return 转换的对象
     */
    public static Object gloryJsonToObject(String json, Class<?> clazz) {
        return muxJsonAndObjec(json,clazz);
    }

    /**
     * @param json
     * @param clazz
     */
    private static Object muxJsonAndObjec(String json, Class<?> clazz) {
        Map<String, Class<?>> mapParamClass = handleClassAndParam(clazz);
        Map<String, String> jsonKeyAndValue = handleJson(json);
        assert jsonKeyAndValue != null;
        Object resObject = null;
        try {
            resObject = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for (String key : jsonKeyAndValue.keySet()) {
            String value = jsonKeyAndValue.get(key);
            Class<?> clss = mapParamClass.get(key);

            String methodName = "set" + (char) (key.charAt(0) - 32) + key.substring(1);
            System.out.println("safsk   "+methodName);
            try {
                Method method = clazz.getMethod(methodName, clss);
                String classTypeName = clss.getSimpleName();
                //判断clss类型
                if (clss.isPrimitive()) {//基本类型
                    if (classTypeName.equals("byte")) {
                        method.invoke(resObject, Byte.valueOf(value));
                    } else if (classTypeName.equals("boolean")) {
                        method.invoke(resObject, Boolean.valueOf(value));
                    } else if (classTypeName.equals("short")) {
                        method.invoke(resObject, Short.valueOf(value));
                    } else if (classTypeName.equals("int")) {
                        method.invoke(resObject, Integer.valueOf(value));
                    } else if (classTypeName.equals("float")) {
                        method.invoke(resObject, Float.valueOf(value));
                    } else if (classTypeName.equals("long")) {
                        method.invoke(resObject, Long.valueOf(value));
                    } else if (classTypeName.equals("double")) {
                        method.invoke(resObject, Double.valueOf(value));
                    } else if (classTypeName.equals("char")) {
                        method.invoke(resObject, value.charAt(0));
                    }
                } else {
                    if (classTypeName.equals("String")){//本地类
                        method.invoke(resObject, value);
                    } else if (classTypeName.equals("List")) {
                        System.out.println("先不解决");
                    } else if (clss.isArray()) {
                        //去[]号
                        Object res = handleArray(value, clss);
                        //这里为什么要将数组再次转换为Object
                        //原因：直接传数组会报参数个数错误，和invoke中的方法冲突
                        method.invoke(resObject,res);
                    } else if (clss.isMemberClass()) {
                        System.out.println("isMemberClass");
                    } else if (clss.isAnnotation()) {

                    } else { //自定义类
                        method.invoke(resObject, muxJsonAndObjec(value,clss));
                    }
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return resObject;
    }

    private static Object[] handleArray(String arrays, Class<?> clazz) {
        Object[] arrayRes = null;
        int start = 1;
        int index = start + 1;
        char ch = arrays.charAt(start);
        if (ch == '{') {
            List<Object> arrayObject = new ArrayList<Object>();
            int flag = 1;
            do {
                ch = arrays.charAt(index++);
                if (ch == '{') {
                    flag++;
                    continue;
                }
                if (ch == '}') {
                    if ((--flag) == 0) {
                        arrayObject.add(muxJsonAndObjec(arrays.substring(start, index), clazz.getComponentType()));
                        start = ++index;
                    }
                }
            } while (index < arrays.length() - 1);
            arrayRes = (Object[]) Array.newInstance(clazz.getComponentType(),arrayObject.size());
            for (int i = 0; i < arrayObject.size(); i++){
                arrayRes[i] = arrayObject.get(i);
            }
        } else if (ch == '[') {
            int flag = 1;
            List<Object[]> arrayObject = new ArrayList<Object[]>();
            while(true){
                ch = arrays.charAt(index++);
                if(ch == '[') {
                    flag++;
                    continue;
                }
                if(ch == ']'){
                    if((--flag) == 0){
                        arrayObject.add(handleArray(arrays.substring(start,index),clazz.getComponentType()));
                        start = index;
                    }
                }
                if(index == arrays.length() - 1)
                    break;
            }
            arrayRes = (Object[]) Array.newInstance(clazz,arrayObject.size());
            for(int i = 0; i < arrayObject.size(); i++){
                arrayRes[i] = arrayObject.get(i);
            }
        }else{
            Class resClass = clazz.getComponentType();
            String resClassName = resClass.getSimpleName();
            if(resClass.isPrimitive()){
                if(resClassName.equals("byte")){
                    String[] data = arrays.split("[^0-9]");
                    arrayRes= (Object[]) Array.newInstance(clazz,data.length);
                    for(int i = 0; i < data.length; i++){
                        arrayRes[i] = Byte.valueOf(data[i]);
                    }
                }else if (resClassName.equals("boolean")) {
                    String[] data = arrays.split("[^a-z]");
                    arrayRes= (Object[]) Array.newInstance(clazz,data.length);
                    for(int i = 0; i < data.length; i++){
                        arrayRes[i] = Boolean.valueOf(data[i]);
                    }
                } else if (resClassName.equals("short")) {
                    String[] data = arrays.split("[^0-9]");
                    arrayRes= (Object[]) Array.newInstance(clazz,data.length);
                    for(int i = 0; i < data.length; i++){
                        arrayRes[i] = Short.valueOf(data[i]);
                    }
                } else if (resClassName.equals("int")) {
                    String[] data = arrays.split("[^0-9]");
                    arrayRes= (Object[]) Array.newInstance(clazz,data.length);
                    for(int i = 0; i < data.length; i++){
                        arrayRes[i] = Integer.valueOf(data[i]);
                    }
                } else if (resClassName.equals("float")) {
                    String[] data = arrays.split("[^0-9.]");
                    arrayRes= (Object[]) Array.newInstance(clazz,data.length);
                    for(int i = 0; i < data.length; i++){
                        arrayRes[i] = Float.valueOf(data[i]);
                    }
                } else if (resClassName.equals("long")) {
                    String[] data = arrays.split("[^0-9]");
                    arrayRes= (Object[]) Array.newInstance(clazz,data.length);
                    for(int i = 0; i < data.length; i++){
                        arrayRes[i] = Long.valueOf(data[i]);
                    }
                } else if (resClassName.equals("double")) {
                    String[] data = arrays.split("[^0-9.]");
                    arrayRes= (Object[]) Array.newInstance(clazz,data.length);
                    for(int i = 0; i < data.length; i++){
                        arrayRes[i] = Double.valueOf(data[i]);
                    }
                } else if (resClassName.equals("char")) {
                    String[] data = arrays.split("[^a-z]");
                    arrayRes= (Object[]) Array.newInstance(clazz,data.length);
                    for(int i = 0; i < data.length; i++){
                        arrayRes[i] = data[i].charAt(0);
                    }
                }
            }else{
                if(resClassName.equals("String")){
                    String[] data = arrays.split("[^a-z0-9_]");
                    arrayRes= (Object[]) Array.newInstance(clazz,data.length);
                    for(int i = 0; i < data.length; i++){
                        arrayRes[i] = data[i];
                    }
                }
            }
        }
        return arrayRes;
    }

    /**
     * 将类中的成员变量通过方法Get方法获取，并得到变量的类型
     * @param clazz 类类型
     * @return
     */
    private static Map<String, Class<?>> handleClassAndParam(Class<?> clazz) {
        Map<String, Class<?>> mapParamClass = new HashMap<String, Class<?>>();//保存字段名及字段的类型
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
            if (m.getName().startsWith("get") || m.getName().startsWith("is")) {
                String mName = m.getName();
                Class<?> mReturn = m.getReturnType();//字段类型
                String key;//字段名
                if (m.getName().startsWith("get"))
                    key = String.format("%c%s", mName.charAt(3) + 32, mName.substring(4));
                else
                    key = String.format("%c%s", mName.charAt(2) + 32, mName.substring(3));
                mapParamClass.put(key, mReturn);
            }
        }
        return mapParamClass;
    }


    /**
     * 处理Json字符串
     * 将json中的key做map中的key
     * json中的value以字符串的形式存储
     * @param json
     */
    private static Map<String, String> handleJson(String json) {
        try {
            if (json == null || json.equals("")) {
                throw new JsonIsNullExecption();
            }
        } catch (JsonIsNullExecption e) {
            System.err.println("Json串为空");
            return null;
        }
        indexJson = 2;
        Map<String, String> result = new HashMap<String, String>();//用来保存json串中的key-value;
        int len = json.length();//indexOf(0)=='{',indexOf(1)=='\"'
        System.out.println(json);
        while (indexJson < len) {
            String key = handleJsonKey(json);
            if (null == key)
                break;
            String value = handleJsonValue(json);
//            System.out.println(key+ ":: " +value);
            result.put(key, value);
        }
        return result;
    }

    /**
     * 获取串中的key值
     * @param json
     * @return
     */
    private static String handleJsonKey(String json) {
        int start = indexJson;
        char ch = json.charAt(indexJson);
        if (ch == ',') {
            indexJson += 2;
            start = indexJson;
        } else if (ch == '\"') {
            start = ++indexJson;
        } else if (ch == '}') {
            return null;
        }

        while (json.charAt(indexJson++) != '\"') ;
        indexJson++;
        return json.substring(start, indexJson - 2);//现在索引在冒号后位置
    }

    /**
     * 获取串中的value值
     * @param json 字符串
     * @return 返回value
     */
    private static String handleJsonValue(String json) {
        int start = indexJson;
        char ch = json.charAt(indexJson);
        if (ch == '{') {//value是对象,可能出现嵌套对象
            int flag = 1;
            while (true) {
                ch = json.charAt(++indexJson);
                if (ch == '{')
                    flag++;
                else if (ch == '}') {
                    if ((--flag) == 0) {
                        return json.substring(start, ++indexJson);//返回对象那一节字符串数据
                    }
                }
            }
        } else if (ch == '[') {//数组
            int flag = 1;
            while (true) {
                ch = json.charAt(++indexJson);
                if (ch == '[') {
                    flag++;
                } else if (ch == ']') {
                    if ((--flag) == 0) {
                        return json.substring(start, ++indexJson);//返回对象那一节字符串数据
                    }
                }
            }

        } else if (ch == '\"') {//字符串
            start = ++indexJson;
            while (json.charAt(indexJson++) != '\"') ;
            return json.substring(start, indexJson - 1);
        } else {//其他类型
            ch = json.charAt(indexJson++);
            while (true) {
                if (ch == ',')
                    break;
                if (ch == '}')
                    break;
                ch = json.charAt(indexJson++);
            }
            return json.substring(start, indexJson - 1);
        }
    }
}
