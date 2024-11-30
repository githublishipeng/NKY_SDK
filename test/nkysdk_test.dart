import 'package:flutter_test/flutter_test.dart';
import 'package:nkysdk/nkysdk.dart';
import 'package:nkysdk/nkysdk_platform_interface.dart';
import 'package:nkysdk/nkysdk_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockNkysdkPlatform
    with MockPlatformInterfaceMixin
    implements NkysdkPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final NkysdkPlatform initialPlatform = NkysdkPlatform.instance;

  test('$MethodChannelNkysdk is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelNkysdk>());
  });

  test('getPlatformVersion', () async {
    Nkysdk nkysdkPlugin = Nkysdk();
    MockNkysdkPlatform fakePlatform = MockNkysdkPlatform();
    NkysdkPlatform.instance = fakePlatform;

    expect(await nkysdkPlugin.getPlatformVersion(), '42');
  });
}