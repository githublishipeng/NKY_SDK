package com.nky.protocal.unimodule;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nky.protocal.Param;
import com.nky.protocal.version6.Protocol0X17;
import com.nky.protocal.version6.Protocol0X18;
import com.nky.protocal.version6.Protocol0X19;

import java.util.ArrayList;
import java.util.List;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.feature.uniapp.common.UniModule;

public class ProtocolModule extends UniModule {
    /**
     *
     * @param options 设置18命令参数
     * @param callback 回调
     */
    @UniJSMethod(uiThread = true)
    public void setDatalogerByP0x18(JSONObject options, UniJSCallback callback) {
        List<Param> paramList = new ArrayList<>();
        JSONArray param18Obj = options.getJSONArray("param18Obj");
        for (int i = 0; i < param18Obj.size(); i++) {
            JSONObject jsonObject = param18Obj.getJSONObject(i);
            paramList.add(Param.newInstance(jsonObject.getIntValue("paramId"), jsonObject.getString("param")));
        }
        Protocol0X18 protocol0X18 = Protocol0X18.newInstance(paramList);
        JSONObject result = new JSONObject();
        try {
            result.put("code", 0);
            result.put("data",bytesToHexString(protocol0X18.getBytes()));
        } catch (Exception e) {
            result.put("code", 1);
        }
        callback.invoke(result.toString());

    }




    @UniJSMethod(uiThread = true)
    public void parserPro0x18(String data, UniJSCallback callback) {
        byte[] bytes = hexStringToByteArray(data);
        JSONObject result = new JSONObject();
        try {
            if (Protocol0X18.isSetSuccess(bytes)) {
                result.put("code", 0);
                result.put("data", true);
            } else {
                result.put("code", 1);
                result.put("data", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        callback.invoke(result.toString());

    }






    @UniJSMethod(uiThread = true)
    public void getDatalogerByP0x19(JSONObject options, UniJSCallback callback) {
        JSONArray param19Obj = options.getJSONArray("param19Obj");
        int[] params=new int[param19Obj.size()];
        for (int i = 0; i < param19Obj.size(); i++) {
            params[i]= param19Obj.getIntValue(i);
        }
        byte[] data = Protocol0X19.newInstance(params).getBytes();
        JSONObject result = new JSONObject();
        try {
            result.put("code", 0);
            result.put("data",bytesToHexString(data));
        } catch (Exception e) {
            result.put("code", 1);
        }

        callback.invoke(result.toString());
    }



    @UniJSMethod(uiThread = true)
    public void parserPro0x19(String data, UniJSCallback callback) {
        byte[] bytes = hexStringToByteArray(data);
        List<Param> paramList = Protocol0X19.readParams(bytes);
        JSONArray jsonArray= new JSONArray();
        for (int i = 0; i < paramList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("paramNo", paramList.get(i).getParamNo());
            jsonObject.put("value", paramList.get(i).readParamValueText());
            jsonArray.add(jsonObject);
        }
        JSONObject result = new JSONObject();
        try {
            result.put("code", 0);
            result.put("data", jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        callback.invoke(result.toString());

    }


    /**
     * 0x17获取参数  读取0304
     * @param options
     * @param callback
     */

    @UniJSMethod(uiThread = true)
    public void getDatalogerByP0x17_0304(JSONObject options, UniJSCallback callback) {
        JSONObject param17Obj = options.getJSONObject("param17Obj");
        int registerAddress = param17Obj.getIntValue("registerAddress");
        int count = param17Obj.getIntValue("count");
        int cmd = param17Obj.getIntValue("cmd");
        byte[] data = Protocol0X17.newInstanceFor0X03(registerAddress, count).getBytes();
        if (cmd==4){
            data=Protocol0X17.newInstanceFor0X04(registerAddress, count).getBytes();
        }
        JSONObject result = new JSONObject();
        try {
            result.put("code", 0);
            result.put("data",bytesToHexString(data));
        } catch (Exception e) {
            result.put("code", 1);
        }

        callback.invoke(result.toString());
    }







    /**
     * 0x17获取参数  读取0610
     * @param options
     * @param callback
     */

    @UniJSMethod(uiThread = true)
    public void getDatalogerByP0x17_06(JSONObject options, UniJSCallback callback) {
        JSONObject param17Obj = options.getJSONObject("param17Obj");
        int registerAddress = param17Obj.getIntValue("registerAddress");
        String value = param17Obj.getString("value");
        byte[] bytes = hexStringToByteArray(value);
        byte[] pro = Protocol0X17.newInstanceForSet06(registerAddress, bytes).getBytes();
        JSONObject result = new JSONObject();
        try {
            result.put("code", 0);
            result.put("data",bytesToHexString(pro));
        } catch (Exception e) {
            result.put("code", 1);
        }

        callback.invoke(result.toString());
    }





    /**
     * 0x17获取参数  读取10
     * @param options
     * @param callback
     */

    @UniJSMethod(uiThread = true)
    public void getDatalogerByP0x17_10(JSONObject options, UniJSCallback callback) {
        JSONObject param17Obj = options.getJSONObject("param17Obj");
        int registerAddress = param17Obj.getIntValue("registerAddress");
        String value = param17Obj.getString("value");
        byte[] bytes = hexStringToByteArray(value);
        byte[] pro = Protocol0X17.newInstanceForSet(registerAddress, bytes).getBytes();
        JSONObject result = new JSONObject();
        try {
            result.put("code", 0);
            result.put("data",bytesToHexString(pro));
        } catch (Exception e) {
            result.put("code", 1);
        }

        callback.invoke(result.toString());
    }





    @UniJSMethod(uiThread = true)
    public void parserPro0x17(String data, UniJSCallback callback) {
        byte[] bytes = hexStringToByteArray(data);
        List<byte[]> values = Protocol0X17.getValues(bytes);
        JSONArray jsonArray= new JSONArray();
        jsonArray.addAll(values);
        JSONObject result = new JSONObject();
        try {
            result.put("code", 0);
            result.put("data", jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        callback.invoke(result.toString());

    }






    @UniJSMethod(uiThread = true)
    public void parserPro0x17_0610(String data, UniJSCallback callback) {
        byte[] bytes = hexStringToByteArray(data);
        JSONObject result = new JSONObject();
        try {
            if (Protocol0X17.isSetSuccess(bytes)) {
                result.put("code", 0);
                result.put("data", true);
            } else {
                result.put("code", 1);
                result.put("data", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        callback.invoke(result.toString());

    }


    /**
     * byte数组转16进制字符串
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }



    public byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }


}
