package com.nky.sdk_flutter.nkysdk.protocal.proUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nky.sdk_flutter.nkysdk.protocal.Param;
import com.nky.sdk_flutter.nkysdk.protocal.unimodule.ProtocolCallback;
import com.nky.sdk_flutter.nkysdk.protocal.util.AESCBCUtil;
import com.nky.sdk_flutter.nkysdk.protocal.version6.Protocol0X18;
import com.nky.sdk_flutter.nkysdk.protocal.version6.Protocol0X19;
import com.nky.sdk_flutter.nkysdk.protocal.version7.Protocol0X26;

import java.util.ArrayList;
import java.util.List;


public class ProtocolTool {

    /**
     *
     * @param options 设置18命令参数
     * @param callback 回调
     */
    public static void setDatalogerByP0x18(JSONObject options, ProtocolCallback callback) {
        List<Param> paramList = new ArrayList<>();
        JSONArray param18Obj = options.getJSONArray("param18Obj");
        for (int i = 0; i < param18Obj.size(); i++) {
            JSONObject jsonObject = param18Obj.getJSONObject(i);
            paramList.add(Param.newInstance(jsonObject.getIntValue("paramId"), jsonObject.getString("param")));
        }

        //设置AES密钥
        String aesKey = options.getString("AESKey");
        String aeSive = options.getString("AESive");
        String protocol = options.getString("protocol");
        String key = options.getString("key");
        if (aesKey!=null){
            AESCBCUtil.SECRET_KEY  = aesKey;
        }
        if (aeSive!=null){
            AESCBCUtil.iv = aeSive;
        }


        Protocol0X18 protocol0X18 = Protocol0X18.newInstance(paramList,Byte.parseByte(protocol));
        callback.invoke(protocol0X18.getBytes());

    }




    public static void parserPro0x18(byte[] bytes, ProtocolCallback callback) {

        callback.invoke(Protocol0X18.isSetSuccess(bytes));

    }






    public static void getDatalogerByP0x19(JSONObject options, ProtocolCallback callback) {
        JSONArray param19Obj = options.getJSONArray("param19Obj");
        int[] params=new int[param19Obj.size()];
        for (int i = 0; i < param19Obj.size(); i++) {
            params[i]= param19Obj.getIntValue(i);
        }

        //设置AES密钥
        String aesKey = options.getString("AESKey");
        String aeSive = options.getString("AESive");
        String protocol = options.getString("protocol");
        String key = options.getString("key");
        if (aesKey!=null){
            AESCBCUtil.SECRET_KEY  = aesKey;
        }
        if (aeSive!=null){
            AESCBCUtil.iv = aeSive;
        }

        byte[] data = Protocol0X19.newInstance(params,Byte.parseByte(protocol)).getBytes();
        callback.invoke(data);
    }



    public static void parserPro0x19(byte[] bytes, ProtocolCallback callback) {
        List<Param> paramList = Protocol0X19.readParams(bytes);
        callback.invoke(paramList);

    }

    public static void parserPro0x26(byte[] bytes, ProtocolCallback callback) {
        Protocol0X26.Result parse = Protocol0X26.parse(bytes);
        callback.invoke(parse);
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
