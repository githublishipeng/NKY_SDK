package com.nky.sdk_flutter.nkysdk.protocal.src.main.java.com.nky.protocal;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nky.protocal.util.ByteUtils;
import com.nky.protocal.version6.Protocol;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数格式
 */
public class Param {

    /**
     * 参数编号常量
     */
    public static class ParamNoConstant {
        //密钥
        public static final int PARAM_NO_SECRET_KEY = 54;
        //默认密钥
        public static final String PARAM_DEFAULT_NO_SECRET_KEY_VALUE = "growatt_iot_device_common_key_01";

        //查询WiFi状态，"0"-成功，其它失败
        public static final int PARAM_NO_WIFI_STATUS = 55;
        //设置WiFi名称
        public static final int PARAM_NO_WIFI_NAME = 56;
        //设置WiFi密码
        public static final int PARAM_NO_WIFI_PASSWORD = 57;
        //查询采集器与服务器通讯状态，"16"、"3"、"4"代表成功，其它失败
        public static final int PARAM_NO_SERVER_STATUS = 60;
        //获取周围的WIFI列表
        public static final int PARAM_NO_WIFI_LIST = 75;
    }

    /**
     * 参数长度占2个字节
     */
    public static final int PARAM_LENGTH = 2;


    /**
     * 2个字节，参数编号
     */
    private byte[] _paramNo;

    /**
     * 参数编号值
     */
    private int paramNo;

    /**
     * 2个字节，参数长度
     */
    private byte[] _paramLength;

    /**
     * 参数长度值
     */
    private int paramLength;

    /**
     * 参数数据
     */
    private byte[] _param;

    private Param() {
    }

    public static Param newInstance(int paramNo, String paramStr) {
        Param param = new Param();
        param.paramNo = paramNo;
        param._paramNo = ByteUtils.intTo2Byte(paramNo);
        param._param = ByteUtils.stringToBytes(paramStr);
        param.paramLength = param._param.length;
        param._paramLength = ByteUtils.intTo2Byte(param._param.length);
        return param;
    }

    public static Param newInstance(byte[] _paramNo, int paramNo, byte[] _paramLength, int paramLength, byte[] _param) {
        return new Param(_paramNo, paramNo, _paramLength, paramLength, _param);
    }

    private Param(byte[] _paramNo, int paramNo, byte[] _paramLength, int paramLength, byte[] _param) {
        this._paramNo = _paramNo;
        this.paramNo = paramNo;
        this._paramLength = _paramLength;
        this.paramLength = paramLength;
        this._param = _param;
    }

    public byte[] getBytes() {
        return ByteUtils.join(_paramNo, _paramLength, _param);
    }

    /**
     * 参数长度（参数编号长度+参数长度长度+参数值长度）
     */
    public int getLength() {
        return _paramNo.length + _paramLength.length + _param.length;
    }

    public int getParamNo() {
        return paramNo;
    }

    public String readParamValueText() {
        if (paramNo == 75) {//获取周围WIFI列表
            //遍历取出有效数据
            int paramsLength = _param.length - 1;
            byte[] allWifiList = new byte[paramsLength];
            System.arraycopy(_param, 1, allWifiList, 0, paramsLength);

            List<WiFiInfo> paramNames = new ArrayList<>();

            int readPosition = 0;
            while (readPosition < paramsLength) {
                //wifi字符长度
                byte[] _paramLength = new byte[1];
                System.arraycopy(allWifiList, readPosition, _paramLength, 0, 1);
                int paramLength = ByteUtils.byteToUnsignedInt(_paramLength[0]);
                //wifi字符串
                readPosition += _paramLength.length;
                byte[] _param = new byte[paramLength];
                System.arraycopy(allWifiList, readPosition, _param, 0, paramLength);
                String wifiName = ByteUtils.bytesToString(_param);

                //wifi信号值
                readPosition += _param.length;
                byte[] singalStrength = new byte[1];
                System.arraycopy(allWifiList, readPosition, singalStrength, 0, singalStrength.length);
                int level = ByteUtils.byteToUnsignedInt(singalStrength[0]);
                readPosition += singalStrength.length;
                paramNames.add(new WiFiInfo(wifiName, level));
            }
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < paramNames.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ssid", paramNames.get(i).ssid);
                jsonObject.put("signalStrength", paramNames.get(i).signalStrength);
                jsonArray.add(jsonObject);
            }
            return jsonArray.toJSONString();
        } else {
            return ByteUtils.bytesToString(_param);
        }
    }


    public class WiFiInfo {
        private String ssid;
        private int signalStrength;

        public WiFiInfo(String ssid, int signalStrength) {
            this.ssid = ssid;
            this.signalStrength = signalStrength;
        }
    }


}
