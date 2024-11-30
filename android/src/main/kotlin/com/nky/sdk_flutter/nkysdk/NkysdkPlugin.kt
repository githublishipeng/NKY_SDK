package com.nky.sdk_flutter.nkysdk


import android.util.Log
import com.alibaba.fastjson.JSONObject
import com.nky.sdk_flutter.nkysdk.protocal.Param
import com.nky.sdk_flutter.nkysdk.protocal.proUtil.ProtocolTool
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** NkysdkPlugin */
class NkysdkPlugin: FlutterPlugin, MethodCallHandler {
   private lateinit var channel : MethodChannel

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "nkysdk")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    try {
      when (call.method) {
        "getPlatformVersion" -> result.success("Android ${android.os.Build.VERSION.RELEASE}")
        "setDatalogerByP0x18" -> setDatalogerByP0x18(call.arguments as HashMap<String, Any>, result)
        "parserPro0x18" -> parserPro0x18(call.arguments as ByteArray, result)
        "setDatalogerByP0x19" -> getDatalogerByP0x19(call.arguments as HashMap<String, Any>, result)
        "parserPro0x19" -> parserPro0x19(call.arguments as ByteArray, result)
        else -> result.notImplemented()
      }
    } catch (e: Exception) {
      Log.e("TTT", "e:${e.message}")
    }
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  private fun setDatalogerByP0x18(map: HashMap<String, Any>, res: Result) {
    ProtocolTool.setDatalogerByP0x18(JSONObject(map)) {
      res.success(it)
    }
  }

  private fun parserPro0x18(msg: ByteArray, res: Result) {
    ProtocolTool.parserPro0x18(msg) {
      res.success(it)
    }
  }

  private fun getDatalogerByP0x19(map: HashMap<String, Any>, res: Result) {
    ProtocolTool.getDatalogerByP0x19(JSONObject(map)) {
      res.success(it)
    }
  }

  private fun parserPro0x19(msg: ByteArray, res: Result) {
    ProtocolTool.parserPro0x19(msg) { back ->
      val ddData = mutableListOf<HashMap<String, Any>>()
      (back as ArrayList<Param>?)?.forEach() {
        val map = HashMap<String, Any>()
        map["length"] = it.length
        map["paramNo"] = it.paramNo
        map["bytes"] = it.bytes.toIntList()
        ddData.add(map)
      }
      res.success(ddData)
    }
  }

  private fun ByteArray.toIntList(): List<Int> {
    val list = mutableListOf<Int>()
    this.forEach { list.add(it.toMyInt()) }
    return list
  }

  private fun Byte.toMyInt(): Int = (this.toInt() and 0xFF)

}
