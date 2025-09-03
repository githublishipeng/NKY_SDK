import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'nkysdk_platform_interface.dart';

class MethodChannelNkysdk extends NkysdkPlatform {
  @visibleForTesting
  final methodChannel = const MethodChannel('nkysdk');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
  @override
  Future<List<int>?> setDatalogerByP0x18(Map<String, dynamic> map) async {
    try {
      if (Platform.isAndroid) {
        return (await methodChannel.invokeMethod('setDatalogerByP0x18', map)) as List<int>;
      } else {
        var back = (await methodChannel.invokeMapMethod("enCode19Or18WithParams", map));
        return back?["data"] as List<int>;
      }
    } catch (e) {
      // print("e:$e");
      return null;
    }
  }

  @override
  Future<dynamic> parserPro0x18(Uint8List bytes) async {
    try {
      if (Platform.isAndroid) {
        return await methodChannel.invokeMethod('parserPro0x18', bytes);
      } else {
        var back = (await methodChannel.invokeMapMethod("deCodeWithParams", bytes));
        return back;
      }
    } catch (e) {
      // print("e:$e");
      return null;
    }
  }

  @override
  Future<List<int>?> setDatalogerByP0x19(Map<String, dynamic> map) async {
    try {
      if (Platform.isAndroid) {
        return (await methodChannel.invokeMethod('getDatalogerByP0x19', map)) as List<int>;
      } else {
        var back = (await methodChannel.invokeMapMethod("enCode19Or18WithParams", map));
        return back?["data"] as List<int>;
      }
    } catch (e) {
      return null;
    }
  }

  @override
  Future<dynamic> parserPro0x19(Uint8List bytes) async {
    try {
      if (Platform.isAndroid) {
        return await methodChannel.invokeMethod('parserPro0x19', bytes);
      } else {
        var back = (await methodChannel.invokeMapMethod("deCodeWithParams", bytes));
        return back;
      }
    } catch (e) {
      // print("e:$e");
      return null;
    }
  }

  @override
  Future setDatalogerByP0x17(Map<String, dynamic> map) async{
    try{
      if (Platform.isAndroid) {
        return await methodChannel.invokeMethod('setDatalogerByP0x17', map);
      } else {
        var back = (await methodChannel.invokeMapMethod("setDatalogerByP0x17", map));
        return back;
      }
    }catch(e){
      return null;
    }
  }
}
