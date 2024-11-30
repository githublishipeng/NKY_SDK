import 'dart:typed_data';

import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'nkysdk_method_channel.dart';

abstract class NkysdkPlatform extends PlatformInterface {
  /// Constructs a NkysdkPlatform.
  NkysdkPlatform() : super(token: _token);

  static final Object _token = Object();

  static NkysdkPlatform _instance = MethodChannelNkysdk();

  /// The default instance of [NkysdkPlatform] to use.
  ///
  /// Defaults to [MethodChannelNkysdk].
  static NkysdkPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [NkysdkPlatform] when
  /// they register themselves.
  static set instance(NkysdkPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<List<int>?> setDatalogerByP0x18(Map<String, dynamic> map);

  Future<dynamic> parserPro0x18(Uint8List hex);

  Future<List<int>?> setDatalogerByP0x19(Map<String, dynamic> map);

  Future<dynamic> parserPro0x19(Uint8List hex);
}
