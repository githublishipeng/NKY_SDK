package com.nky.sdk_flutter.nkysdk.protocal.modbus;

import com.nky.sdk_flutter.nkysdk.protocal.util.ByteUtils;
import com.nky.sdk_flutter.nkysdk.protocal.util.CRC16Util;

public class Modbus06 extends Modbus {


    public static Modbus06 newInstance(int start, byte[] _setValues) {
        Modbus06 modbus = new Modbus06();
        modbus._start = ByteUtils.intTo2Byte(start);
        modbus._values = ByteUtils.join(modbus._start, _setValues);
        int crc16 = CRC16Util.calcCrc16(modbus.getBytesWithoutCrc());
        modbus._crc16 = ByteUtils.intTo2Byte(crc16);
        return modbus;
    }
    /**
     * 寄存器
     */
    private byte[] _start;




    @Override
    byte get_FunctionCode() {
        return 0X06;
    }
}
