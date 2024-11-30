import 'dart:convert';
import 'dart:typed_data';

extension BytesListExtension on List<int> {
  //将数组转为 Uint8List
  Uint8List toU8() => Uint8List.fromList(this);

  //将数组转为字符串
  String toStr() {
    try {
      return utf8.decode(toU8());
    } catch (e) {
      print("e:$e");
    }
    return "";
  }

  //将数组转为16进制字符串
  String toHex() {
    int length;
    Uint8List bArr = toU8();
    if ((length = bArr.length) <= 0) {
      return "";
    }
    Uint8List cArr = Uint8List(length << 1);
    int i = 0;
    for (int i2 = 0; i2 < length; i2++) {
      int i3 = i + 1;
      var cArr2 = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'];
      var index = (bArr[i2] >> 4) & 15;
      cArr[i] = cArr2[index].codeUnitAt(0);
      i = i3 + 1;
      cArr[i3] = cArr2[bArr[i2] & 15].codeUnitAt(0);
    }
    return String.fromCharCodes(cArr);
  }

  //打印16进制字符串，隔1个字节加，
  String log1Byte() => _log(byteCount: 2);

  //打印16进制字符串，隔2个字节加，
  String log2Byte() => _log(byteCount: 4);

  String _log({int byteCount = 2}) {
    String str = toHex();
    String back = "";
    for (int i = 0; i < str.length; i += byteCount) {
      back += "${str.substring(i, i + byteCount)},";
    }
    if (back.isEmpty) return "";
    return back.substring(0, back.length - 1);
  }
}
