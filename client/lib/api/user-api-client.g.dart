// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user-api-client.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

GetUserResponse _$GetUserResponseFromJson(Map<String, dynamic> json) =>
    GetUserResponse(
      username: json['username'] as String,
      email: json['email'] as String,
    );

Map<String, dynamic> _$GetUserResponseToJson(GetUserResponse instance) =>
    <String, dynamic>{
      'username': instance.username,
      'email': instance.email,
    };

GetUserSettingResponse _$GetUserSettingResponseFromJson(
        Map<String, dynamic> json) =>
    GetUserSettingResponse(
      timerDuration: json['timerDuration'] as int,
      restDuration: json['restDuration'] as int,
      restAutoStart: json['restAutoStart'] as bool,
    );

Map<String, dynamic> _$GetUserSettingResponseToJson(
        GetUserSettingResponse instance) =>
    <String, dynamic>{
      'timerDuration': instance.timerDuration,
      'restDuration': instance.restDuration,
      'restAutoStart': instance.restAutoStart,
    };

UserSettingRequest _$UserSettingRequestFromJson(Map<String, dynamic> json) =>
    UserSettingRequest(
      timerDuration: json['timerDuration'] as int,
      restDuration: json['restDuration'] as int,
      restAutoStart: json['restAutoStart'] as bool,
    );

Map<String, dynamic> _$UserSettingRequestToJson(UserSettingRequest instance) =>
    <String, dynamic>{
      'timerDuration': instance.timerDuration,
      'restDuration': instance.restDuration,
      'restAutoStart': instance.restAutoStart,
    };

// **************************************************************************
// RetrofitGenerator
// **************************************************************************

// ignore_for_file: unnecessary_brace_in_string_interps

class _UserApiClient implements UserApiClient {
  _UserApiClient(this._dio, {this.baseUrl}) {
    baseUrl ??= 'https://emotimer.ml';
  }

  final Dio _dio;

  String? baseUrl;

  @override
  Future<GetUserResponse> getUser() async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _headers = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.fetch<Map<String, dynamic>>(
        _setStreamType<GetUserResponse>(
            Options(method: 'GET', headers: _headers, extra: _extra)
                .compose(_dio.options, '/api/users/me',
                    queryParameters: queryParameters, data: _data)
                .copyWith(baseUrl: baseUrl ?? _dio.options.baseUrl)));
    final value = GetUserResponse.fromJson(_result.data!);
    return value;
  }

  @override
  Future<GetUserSettingResponse> getUserSetting() async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _headers = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.fetch<Map<String, dynamic>>(
        _setStreamType<GetUserSettingResponse>(
            Options(method: 'GET', headers: _headers, extra: _extra)
                .compose(_dio.options, '/api/users/me/setting',
                    queryParameters: queryParameters, data: _data)
                .copyWith(baseUrl: baseUrl ?? _dio.options.baseUrl)));
    final value = GetUserSettingResponse.fromJson(_result.data!);
    return value;
  }

  @override
  Future<void> patchUserSetting(request) async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _headers = <String, dynamic>{};
    final _data = <String, dynamic>{};
    await _dio.fetch<void>(_setStreamType<void>(
        Options(method: 'PATCH', headers: _headers, extra: _extra)
            .compose(_dio.options, '/api/users/me/setting',
                queryParameters: queryParameters, data: _data)
            .copyWith(baseUrl: baseUrl ?? _dio.options.baseUrl)));
    return null;
  }

  RequestOptions _setStreamType<T>(RequestOptions requestOptions) {
    if (T != dynamic &&
        !(requestOptions.responseType == ResponseType.bytes ||
            requestOptions.responseType == ResponseType.stream)) {
      if (T == String) {
        requestOptions.responseType = ResponseType.plain;
      } else {
        requestOptions.responseType = ResponseType.json;
      }
    }
    return requestOptions;
  }
}
