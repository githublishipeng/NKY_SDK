#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html.
# Run `pod lib lint nkysdk.podspec` to validate before publishing.
#
Pod::Spec.new do |s|
  s.name             = 'nkysdk'
  s.version          = '0.0.1'
  s.summary          = 'nkySdk_flutter插件'
  s.description      = <<-DESC
nkySdk_flutter插件
                       DESC
  s.homepage         = 'http://example.com'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Your Company' => 'email@example.com' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.public_header_files = 'Classes/**/*.h'
  s.dependency 'Flutter'
  s.platform = :ios, '12.0'

  # Flutter.framework does not contain a i386 slice.
  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES', 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'i386' }

  # If your plugin requires a privacy manifest, for example if it uses any
  # required reason APIs, update the PrivacyInfo.xcprivacy file to describe your
  # plugin's privacy impact, and then uncomment this line. For more information,
  # see https://developer.apple.com/documentation/bundleresources/privacy_manifest_files
  # s.resource_bundles = {'nkysdk_privacy' => ['Resources/PrivacyInfo.xcprivacy']}

    # framework文件路径
    s.vendored_frameworks = 'Framework/AECC_Setnet_SDK.framework'
    # a文件路径
    s.vendored_libraries = 'Framework/*.a'
    # bundle资源文件路径
    s.resource ='Framework/*.bundle'
    s.public_header_files = 'Classes/**/*.h'
end
