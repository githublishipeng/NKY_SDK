package com.nky.protocal.proUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nky.protocal.Param;
import com.nky.protocal.unimodule.ProtocalCallback;
import com.nky.protocal.version6.Protocol0X18;
import com.nky.protocal.version6.Protocol0X19;

import java.util.ArrayList;
import java.util.List;


public class ProtocolTool {

    /**
     *
     * @param options 设置18命令参数
     * @param callback 回调
     */
    public static void setDatalogerByP0x18(JSONObject options, ProtocalCallback callback) {
        List<Param> paramList = new ArrayList<>();
        JSONArray param18Obj = options.getJSONArray("param18Obj");
        for (int i = 0; i < param18Obj.size(); i++) {
            JSONObject jsonObject = param18Obj.getJSONObject(i);
            paramList.add(Param.newInstance(jsonObject.getIntValue("paramId"), jsonObject.getString("param")));
        }
        Protocol0X18 protocol0X18 = Protocol0X18.newInstance(paramList);
        callback.invoke(protocol0X18.getBytes());

    }




    public static void parserPro0x18(byte[] bytes, ProtocalCallback callback) {

        callback.invoke(Protocol0X18.isSetSuccess(bytes));

    }






    public static void getDatalogerByP0x19(JSONObject options, ProtocalCallback callback) {
        JSONArray param19Obj = options.getJSONArray("param19Obj");
        int[] params=new int[param19Obj.size()];
        for (int i = 0; i < param19Obj.size(); i++) {
            params[i]= param19Obj.getIntValue(i);
        }
        byte[] data = Protocol0X19.newInstance(params).getBytes();
        callback.invoke(data);
    }



    public static void parserPro0x19(byte[] bytes, ProtocalCallback callback) {
        List<Param> paramList = Protocol0X19.readParams(bytes);
        callback.invoke(paramList);

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



    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

}
