package com.nky.sdk_flutter.nkysdk.protocal.version6;



import com.shuoxinda.bluetooth.protocal.modbus.Modbus;
import com.shuoxinda.bluetooth.protocal.modbus.Modbus06;
import com.shuoxinda.bluetooth.protocal.modbus.ModbusRead;
import com.shuoxinda.bluetooth.protocal.modbus.ModbusSet;
import com.shuoxinda.bluetooth.protocal.util.AESCBCUtil;
import com.shuoxinda.bluetooth.protocal.util.ByteUtils;
import com.shuoxinda.bluetooth.protocal.util.CRC16Util;

import java.util.ArrayList;
import java.util.List;

/**
 * 0X17命令，透传命令
 * 对逆变器进行读取或者设置
 * 数据区=数据采集器序列号（10字节）+透传数据区的数据长度（2字节）+透传数据区（Modbus协议内容）+AES加密补0区
 */
public class Protocol0X17 extends Protocol {

    /**
     * 03功能码：holding
     * 读取一个寄存器值
     */
    public static Protocol0X17 newInstanceFor0X03(int registerAddress,byte protocolVersion) {
        return newInstanceFor0X03(registerAddress, 1,protocolVersion);
    }

    /**
     * 04功能码：input
     * 读取一个寄存器值
     */
    public static Protocol0X17 newInstanceFor0X04(int registerAddress,byte protocolVersion) {
        return newInstanceFor0X04(registerAddress, 1,protocolVersion);
    }

    /**
     * 03功能码：holding
     * 读取多个连续地址寄存器的值
     */
    public static Protocol0X17 newInstanceFor0X03(int registerAddress, int count,byte protocolVersion) {
        return newInstanceForRead(0X03, registerAddress, count,protocolVersion);
    }

    /**
     * 04功能码：input
     * 读取多个连续地址寄存器的值
     */
    public static Protocol0X17 newInstanceFor0X04(int registerAddress, int count,byte protocolVersion) {
        return newInstanceForRead(0X04, registerAddress, count,protocolVersion);
    }

    /**
     * @param functionCode         功能码：0X03或者0X04
     * @param startRegisterAddress 开始寄存器地址
     * @param count                数据读取个数
     */
    private static Protocol0X17 newInstanceForRead(int functionCode, int startRegisterAddress, int count, byte protocolVersion) {
        ModbusRead modbusRead = ModbusRead.newInstance(functionCode, startRegisterAddress, count);
        return newInstance(modbusRead.getBytes(),protocolVersion);
    }

    /**
     * 单个设置与批量设置连续寄存器地址的值
     *
     * @param startRegisterAddress 开始寄存器地址
     * @param _setValues           设置的数据
     */
    public static Protocol0X17 newInstanceForSet(int startRegisterAddress, byte[] _setValues) {
        ModbusSet modbusSet = ModbusSet.newInstance(startRegisterAddress, _setValues);
        return newInstance(modbusSet.getBytes(),(byte) 0x06);
    }



    /**
     * 单个设置06
     *
     * @param startRegisterAddress 开始寄存器地址
     * @param _setValues           设置的数据
     */
    public static Protocol0X17 newInstanceForSet06(int startRegisterAddress, byte[] _setValues) {
        Modbus06 modbusSet = Modbus06.newInstance(startRegisterAddress, _setValues);
        return newInstance(modbusSet.getBytes(), (byte) 0x06);
    }

    /**
     * @param _modbusData 透传数据区数据，即modbus数据
     */
    private static Protocol0X17 newInstance(byte[] _modbusData,byte protocolVersion) {
        Protocol0X17 protocol = new Protocol0X17();
        protocol._protocolVersion= new byte[]{0x00, protocolVersion};


        protocol._modbusData = _modbusData;
        protocol._modbusDataLength = ByteUtils.intTo2Byte(protocol._modbusData.length);

        //数据区长度（未包含补0）
        int length = ProtocolConstant.DATA_LOGGING_SN_LENGTH + MODBUS_DATA_LENGTH + protocol._modbusData.length;
        if (length % 16 == 0) {
            protocol._aesEncryptZero = new byte[0];
        }else {
            protocol._aesEncryptZero = new byte[16 - length % 16];
        }

        //数据长度=设备地址长度+功能码长度+数据区长度（不包含补0区）
        protocol.dataLength = ProtocolConstant.DEVICE_ADDRESS_LENGTH + ProtocolConstant.FUNCTION_CODE_LENGTH + length;
        protocol._dataLength = ByteUtils.intTo2Byte(protocol.dataLength);

        //未加密的数据区数据
        protocol._dataArea = ByteUtils.join(protocol._dataloggingSn, protocol._modbusDataLength, protocol._modbusData, protocol._aesEncryptZero);
        protocol._dataAreaWithAES = AESCBCUtil.encode(protocol._dataArea);

        //算出总长度
        protocol.totalLength = ProtocolConstant.PROTOCOL_VERSION_LENGTH + ProtocolConstant.DATA_LENGTH + protocol.dataLength + protocol._aesEncryptZero.length + ProtocolConstant.CRC_16_LENGTH;
        protocol._totalLength = ByteUtils.intTo2Byte(protocol.totalLength);

        //crc16检验内容
        byte[] crc16CalcContent = ByteUtils.join(protocol._totalLength, protocol._protocolVersion, protocol._dataLength, new byte[]{protocol._deviceAddress}, new byte[]{protocol.get_functionCode()}, protocol._dataAreaWithAES);
        protocol.crc16 = CRC16Util.calcCrc16(crc16CalcContent);
        protocol._crc16 = ByteUtils.intTo2Byte(protocol.crc16);

        return protocol;
    }

    /**
     * 数据区中内容：2个字节，透传数据区的数据长度
     */
    private byte[] _modbusDataLength;
    private static final int MODBUS_DATA_LENGTH = 2;

    /**
     * 数据区中内容：透传数据区数据
     */
    private byte[] _modbusData;

    @Override
    public byte get_functionCode() {
        return 0X17;
    }

    /**
     * 返回来寄存器值，每个寄存器的值占2个字节
     */
    public static List<byte[]> getValues(byte[] rawResponse) {
        List<byte[]> params = new ArrayList<>();
        byte[] _modbusData = getModbusData(rawResponse);
        if (_modbusData != null) {
            //字节数目
            int byteCount = ByteUtils.byteToUnsignedInt(_modbusData[Modbus.ADDRESS_LENGTH + Modbus.FUNCTION_CODE_LENGTH]);
            //寄存器值
            byte[] _values = new byte[byteCount];
            System.arraycopy(_modbusData, Modbus.ADDRESS_LENGTH + Modbus.FUNCTION_CODE_LENGTH + 1, _values, 0, byteCount);
            int currentPosition = 0;
            while (currentPosition < byteCount) {
                params.add(new byte[]{_values[currentPosition], _values[currentPosition + 1]});
                currentPosition += 2;
            }
        }
        return params;
    }

    /**
     * @return modbus数据, null代表错误响应，否则是正确响应
     */
    public static byte[] getModbusData(byte[] rawResponse) {
        if (CRC16Util.crc16Verify(rawResponse)) {
            byte[] dataArea = getDecodeDataArea(rawResponse);
            byte[] _modbusDataLength = new byte[MODBUS_DATA_LENGTH];
            System.arraycopy(dataArea, ProtocolConstant.DATA_LOGGING_SN_LENGTH, _modbusDataLength, 0, _modbusDataLength.length);
            int modbusDataLength = ByteUtils.convert2BytesToUnsignedInt(_modbusDataLength);
            //地址(1个字节)+功能码或者错误码(1个字节)+字节数目(1个字节)+数据+crc(2个字节)
            byte[] _modbusData = new byte[modbusDataLength];
            System.arraycopy(dataArea, ProtocolConstant.DATA_LOGGING_SN_LENGTH + MODBUS_DATA_LENGTH, _modbusData, 0, modbusDataLength);
            if (CRC16Util.crc16VerifyForModbus(_modbusData)) {
                boolean isSuccess = !(ByteUtils.byteToUnsignedInt(_modbusData[Modbus.ADDRESS_LENGTH]) > 0X80);
                if (isSuccess) {
                    return _modbusData;
                }
            }
        }
        return null;
    }

    public static boolean isSetSuccess(byte[] rawResponse) {
        return getModbusData(rawResponse) != null;
    }

}
