import 'dart:typed_data';

import 'nkysdk_platform_interface.dart';

class Nkysdk {


  Future<String?> getPlatformVersion() => NkysdkPlatform.instance.getPlatformVersion();

  Future<Uint8List?> setDatalogerByP0x18(Map<String, dynamic> map) async {
    List<int>? back = await NkysdkPlatform.instance.setDatalogerByP0x18(map);
    return back == null ? null : Uint8List.fromList(back);
  }

  dynamic parserPro0x18(Uint8List bytes) => NkysdkPlatform.instance.parserPro0x18(bytes);

  Future<Uint8List?> setDatalogerByP0x19(Map<String, dynamic> map) async {
    List<int>? back = await NkysdkPlatform.instance.setDatalogerByP0x19(map);
    return back == null ? null : Uint8List.fromList(back);
  }

  dynamic parserPro0x19(Uint8List bytes) => NkysdkPlatform.instance.parserPro0x19(bytes);


  // dynamic setDatalogerByP0x17(Map<String, dynamic> map) => NkysdkPlatform.instance.setDatalogerByP0x17(map);

}
