#import "NkysdkPlugin.h"
#import <AECC_Setnet_SDK/AECCSetnetManager.h>

@implementation NkysdkPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"nkysdk"
            binaryMessenger:[registrar messenger]];
  NkysdkPlugin* instance = [[NkysdkPlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
  if ([@"getPlatformVersion" isEqualToString:call.method]) {
    result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
   } else if([@"enCodeWithParams" isEqualToString:call.method]){
        [
            [AECCSetnetManager sharedInstance] enCodeWithParams:call.arguments
                block:^(NSDictionary * _Nonnull encryptionDataDic)
                {
                    result(encryptionDataDic);
                }
        ];
    } else if([@"DeCodeWithInputData" isEqualToString:call.method]){
        FlutterStandardTypedData *typedData = call.arguments;
        NSData *ttdata = typedData.data;
        [
            [AECCSetnetManager sharedInstance] DeCodeWithInputData:ttdata
            block:^(NSDictionary * _Nonnull decryptDataDic) {
                //NSLog(@"-s-%@",decryptDataDic);
                result(decryptDataDic);
            }
        ];
    } else {
    result(FlutterMethodNotImplemented);
  }
}

@end
