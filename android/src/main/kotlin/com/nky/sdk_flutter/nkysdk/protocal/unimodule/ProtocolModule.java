package com.nky.sdk_flutter.nkysdk.protocal.unimodule;




import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nky.sdk_flutter.nkysdk.protocal.Param;
import com.nky.sdk_flutter.nkysdk.protocal.unimodule.ProtocolCallback;
import com.nky.sdk_flutter.nkysdk.protocal.util.AESCBCUtil;
import com.nky.sdk_flutter.nkysdk.protocal.util.UpdateDatalogUtils;
import com.nky.sdk_flutter.nkysdk.protocal.version6.Protocol0X17;
import com.nky.sdk_flutter.nkysdk.protocal.version6.Protocol0X18;
import com.nky.sdk_flutter.nkysdk.protocal.version6.Protocol0X19;
import com.nky.sdk_flutter.nkysdk.protocal.version7.Protocol0X26;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ProtocolModule {
    /**
     *
     * @param options 设置18命令参数
     * @param callback 回调
     */
    public void setDatalogerByP0x18(JSONObject options, ProtocolCallback callback) {
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
        JSONObject result = new JSONObject();
        try {
            result.put("code", 0);
            result.put("data",bytesToHexString(protocol0X18.getBytes()));
        } catch (Exception e) {
            result.put("code", 1);
        }
        callback.invoke(result.toString());

    }




    public void parserPro0x18(String data, ProtocolCallback callback) {
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






    public void getDatalogerByP0x19(JSONObject options, ProtocolCallback callback) {
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
        JSONObject result = new JSONObject();
        try {
            result.put("code", 0);
            result.put("data",bytesToHexString(data));
        } catch (Exception e) {
            result.put("code", 1);
        }

        callback.invoke(result.toString());
    }



    public void parserPro0x19(String data, ProtocolCallback callback) {
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

    public void getDatalogerByP0x17_0304(JSONObject options, ProtocolCallback callback) {
        JSONObject param17Obj = options.getJSONObject("param17Obj");
        int registerAddress = param17Obj.getIntValue("registerAddress");
        int count = param17Obj.getIntValue("count");
        int cmd = param17Obj.getIntValue("cmd");

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


        byte[] data = Protocol0X17.newInstanceFor0X03(registerAddress, count, Byte.parseByte(protocol)).getBytes();
        if (cmd==4){
            data=Protocol0X17.newInstanceFor0X04(registerAddress, count,Byte.parseByte(protocol)).getBytes();
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

    public void getDatalogerByP0x17_06(JSONObject options, ProtocolCallback callback) {
        JSONObject param17Obj = options.getJSONObject("param17Obj");
        int registerAddress = param17Obj.getIntValue("registerAddress");
        String value = param17Obj.getString("value");


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

    public void getDatalogerByP0x17_10(JSONObject options, ProtocolCallback callback) {
        JSONObject param17Obj = options.getJSONObject("param17Obj");
        int registerAddress = param17Obj.getIntValue("registerAddress");
        String value = param17Obj.getString("value");

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





    public void parserPro0x17(String data, ProtocolCallback callback) {
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






    public void parserPro0x17_0610(String data, ProtocolCallback callback) {
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






//    /**
//     * 0x26获取参数  读取10
//     * @param options
//     * @param callback
//     */
//
//    public void setDatalogerByP0x26(JSONObject options, ProtocolCallback callback) {
//        try {
//            String path = options.getString("path");
//            int upgradeNowstep = options.getIntValue("upgradeNowStep");
//            int mtu = options.getIntValue("packagesize");
//            long offset = options.getLong("offset");
//
//
//            //设置AES密钥
//            String aesKey = options.getString("AESKey");
//            String aeSive = options.getString("AESive");
//
//
//            if (aesKey!=null){
//                AESCBCUtil.SECRET_KEY  = aesKey;
//            }
//            if (aeSive!=null){
//                AESCBCUtil.iv = aeSive;
//            }
//
//
//            if (upgradeNowstep==1){//取前20个字节
//                byte[] bytes = UpdateDatalogUtils.readFromPosition(path, 0, 20);
//                Protocol0X26 protocol0X26 = Protocol0X26.newInstance(getFileSizeTraditional(path), 0, bytes);
//                byte[] pro =  protocol0X26.getBytes();
//                JSONObject result = new JSONObject();
//                try {
//                    result.put("result", 0);
//                    result.put("data",bytesToHexString(pro));
//                    result.put("未加密数据",bytesToHexString(protocol0X26.get_dataArea()));
//                } catch (Exception e) {
//                    result.put("result", 1);
//                    result.put("msg", "第一步取前20个字节出错："+e.getMessage());
//                }
//
//                callback.invoke(result.toString());
//            }else if (upgradeNowstep==2){
//                //偏移量加20字节
//                byte[] bytes = UpdateDatalogUtils.readFromPosition(path, offset+20, mtu);
//                JSONObject result = new JSONObject();
//                try {
//                    long current = offset+mtu+20;
//                    result.put("result", 0);
//                    result.put("data",bytesToHexString(bytes));
//                    result.put("msg","Data packaging successful");
//                    result.put("upgradeNowStep","2");
//                    result.put("total",getFileSizeTraditional(path));
//                    result.put("offset",current);
//                    result.put("process",dividePrecise(current, getFileSizeTraditional(path)));
//                } catch (Exception e) {
//                    result.put("result", 1);
//                    result.put("msg", "第二步取文件指定位置出错："+e.getMessage());
//                }
//
//                callback.invoke(result.toString());
//            }else {
//                JSONObject result = new JSONObject();
//                result.put("result", 1);
//                result.put("msg", "第三步步骤出错");
//
//                callback.invoke(result.toString());
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//
//
//    }
//


    /**
     * 使用BigDecimal进行精确除法，保留1位小数
     */
    public BigDecimal dividePrecise(long dividend, long divisor) {
        BigDecimal bd1 = new BigDecimal(dividend);
        BigDecimal bd2 = new BigDecimal(divisor);

        return bd1.divide(bd2, 1, RoundingMode.HALF_UP);
    }


    public void parserPro0x26(String data, ProtocolCallback callback) {
        byte[] bytes = hexStringToByteArray(data);
        Protocol0X26.Result parse = Protocol0X26.parse(bytes);
        JSONObject jsonObject = (JSONObject) JSON.toJSON(parse);
        JSONObject result = new JSONObject();
        try {
            result.put("reslut", 0);
            result.put("data", jsonObject);
            result.put("msg", parse);
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
