import 'dart:convert';
import 'dart:typed_data';

///字节数组工具
class ByteUtil {
  ///int转字节
  static Uint8List int2Byte(int v) {
    var b = ByteData(2);
    b.setInt16(0, v);
    return b.buffer.asUint8List();
  }

  ///int转字节
  static Uint8List int2Byte1(int v) {
    var b = ByteData(1);
    b.setInt8(0, v);
    return b.buffer.asUint8List();
  }

  /// 复制数组
  /// useArr 提供数据的数组
  /// useStartIndex 从提供数据数组第几位开始取值
  /// enterArr 被插入的数组
  /// enterStartIndex 被插入数组的位置
  /// length 插入数据的长度
  static arrayCopy(List<int> useArr, int useStartIndex, List<int> enterArr,
      int enterStartIndex, int length) {
    try {
      for (var j = useStartIndex; j < useStartIndex + length; j++) {
        enterArr[enterStartIndex + j - useStartIndex] = useArr[j];
      }
    } catch (e) {
      // print("arrayCopy:$e");
    }
  }

  /// int数组转16进制字符串
  static list2Hex(List<int> list) => byteArray2Hex(Uint8List.fromList(list));

  /// 字节数组转16进制字符串
  static byteArray2Hex(Uint8List bArr) {
    int length;
    if ((length = bArr.length) <= 0) {
      return "";
    }
    Uint8List cArr = Uint8List(length << 1);
    int i = 0;
    for (int i2 = 0; i2 < length; i2++) {
      int i3 = i + 1;
      var cArr2 = [
        '0',
        '1',
        '2',
        '3',
        '4',
        '5',
        '6',
        '7',
        '8',
        '9',
        'A',
        'B',
        'C',
        'D',
        'E',
        'F'
      ];
      var index = (bArr[i2] >> 4) & 15;
      cArr[i] = cArr2[index].codeUnitAt(0);
      i = i3 + 1;
      cArr[i3] = cArr2[bArr[i2] & 15].codeUnitAt(0);
    }
    return String.fromCharCodes(cArr);
  }

  /// 从数组中复制一部分数组数据
  static Uint8List copyOfRange(Uint8List data, int start, int end) {
    var newList = Uint8List(end - start);
    for (var i = start; i < end; i++) {
      newList[i - start] = data[i];
    }
    return newList;
  }

  /// 字节数组转字符串
  static String byte2String(Uint8List? bytes) {
    if (bytes == null) {
      return "";
    }
    try{
      return utf8.decode(bytes);
    }catch(e){
      // print("e:$e");
    }
    return "";
    // Log.e(utf8.decode(bytes));
    // Log.e(bytes);
    // var ss = "";
    // for (var i = 0; i < bytes.length; i++) {
    //   if (bytes[i].toInt() != 0) {
    //     // ss += int2Char(bytes[i]);
    //     ss += bytes[i].toInt().toString();
    //   } else {
    //     break;
    //   }
    // }
    // return ss;
  }

  /// int 转 ascii 字符，flutter中没有char类型，可以通过Uint8List来转换
  static String int2Char(int a){
    int figure = a + 97;
    // Log.e('数字${figure.toString()}');
    var listInt = [figure];
    // Log.e('listInt = ${listInt.toString()}');
    var int2utf8 = Uint8List.fromList(listInt);
    // Log.e('Uint8List = ${int2utf8.toString()}');
    var character = const Utf8Codec().decode(int2utf8);
    // Log.e('character = ${character}');
    // print('数字${figure.toString()}=${character}');
    return character;
  }

  /// 字节数组转Int
  static int bytes2Int(Uint8List bytes) {
    //将每个byte依次搬运到int相应的位置
    var bd = ByteData.view(bytes.buffer);
    // bd.setInt8(0, 2);
    return bd.getInt16(0);
  }

  static Uint8List toByteArray(String str) {
    return Uint8List.fromList(utf8.encode(str));
  }

// static hex(int c) {
//   if (c >= '0'.codeUnitAt(0) && c <= '9'.codeUnitAt(0)) {
//     return c - '0'.codeUnitAt(0);
//   }
//   if (c >= 'A'.codeUnitAt(0) && c <= 'F'.codeUnitAt(0)) {
//     return (c - 'A'.codeUnitAt(0)) + 10;
//   }
// }
}
