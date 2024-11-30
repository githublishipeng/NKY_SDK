//
//  AECCSetnetManager.h
//  AECC-Setnet-SDK
//
//  Created by 啊清 on 2024/6/26.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN
typedef void(^enCodeBlock)(NSDictionary *encryptionDataDic);
typedef void(^deCodeBlock)(NSDictionary *decryptDataDic);

@interface AECCSetnetManager : NSObject
+ (instancetype)sharedInstance;


/*加密传入数据格式:
 18:
 NSDictionary * params = @{
   @"commandId":@"18",//命令类型,设置为18,读取为19
   @"param18Obj":@[@{@"paramId":寄存器地址,@"param":传输的数据},...,@{@"paramId":寄存器地址,@"param":传输的数据}];    //数据类型,18 param18Obj,读取为19 param19Obj //数组里面装字典
 }
 19:
 NSDictionary * params = @{
     @"commandId":@"19",//命令类型,设置为18,读取为19
     @"param19Obj":@[寄存器地址,...,寄存器地址] //数据类型,18 param18Obj,读取为19 param19Obj
 };
 
 17:读取
 NSDictionary * params = @{
     @"commandId":@"17",//命令类型17
     @"commandType":@"0",//0读取,1设置
     @"readType":@"03"//读取的寄存器类型03或者04
     @"readResStart":@"100",//开始地址
     @"readLength":@"6",//读取长度
 };
 17:设置
 NSDictionary * params = @{
     @"commandId":@"17",//命令类型17
     @"commandType":@"1",//0读取,1设置
     @"setResStart":@"188",//开始地址
     @"param17Obj":@[@"xxx",@"ddd",@"ggg",@"hhh"]//数据,写入多少个代表从开始寄存器写入188,189,190...
 };
 26:下发
 NSDictionary * params = @{
     @"commandId":@"26",//命令类型26
     @"packData":data,//升级包一包的数据
     @"AllNumb":@"256",//总包数
     @"NowNumb":@"20",当前第几包
 };
 方法调用:
 [[AECCSetnetManager sharedInstance] enCodeWithParams:params block:^(NSDictionary * _Nonnull encryptionDataDic) {
     
 }];
 encryptionDataDic
 返回数据格式:
 encryptionBlock = @{@"result":@"0",//0成功,1失败
                     @"msg":@"Data packaging successful",//提示
                     @"data":data //data为打包好的数据
                    };
 */
//加密
- (void)enCodeWithParams:(NSDictionary *)params block:(enCodeBlock)encryptionBlock;


/*解密格式:
 传入:
 data 传入采集器返回的数据,NSData类型
 
 返回数据格式:
 decryptBlock = @{@"result":@"0",//0成功,1失败
                  @"msg":@"Read successful",//提示18 Set successfully,19 Read successful
                  @"data":@{@"57":xxxxx,@"58":xxxxx},//解析成功后返回的数据格式为:寄存器地址:解析数据
                  @"commandId":@"19" //命令类型
                 }
*/
//解密
- (void)DeCodeWithInputData:(NSData *)data block:(deCodeBlock)decryptBlock;


@end

NS_ASSUME_NONNULL_END
