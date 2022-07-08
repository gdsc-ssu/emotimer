// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'timer-api-client.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

SetTimerRequest _$SetTimerRequestFromJson(Map<String, dynamic> json) =>
    SetTimerRequest(
      userId: json['userId'] as String,
      totalTime: json['totalTime'] as int,
      emoji: $enumDecodeNullable(_$EmojiEnumMap, json['emoji']),
    );

Map<String, dynamic> _$SetTimerRequestToJson(SetTimerRequest instance) =>
    <String, dynamic>{
      'userId': instance.userId,
      'totalTime': instance.totalTime,
      'emoji': _$EmojiEnumMap[instance.emoji],
    };

const _$EmojiEnumMap = {
  Emoji.RED_HEART: 'RED_HEART',
  Emoji.CLOSED_BOOK: 'CLOSED_BOOK',
  Emoji.SLIGHTLY_SMILING_FACE: 'SLIGHTLY_SMILING_FACE',
  Emoji.RICE: 'RICE',
  Emoji.JOYSTICK: 'JOYSTICK',
  Emoji.CRYING_FACE: 'CRYING_FACE',
  Emoji.FLEXED_BICEPS: 'FLEXED_BICEPS',
  Emoji.ONE_HUNDRED: 'ONE_HUNDRED',
  Emoji.ANGER_SYMBOL: 'ANGER_SYMBOL',
  Emoji.ONCOMING_FIST: 'ONCOMING_FIST',
};

SetTimerResponse _$SetTimerResponseFromJson(Map<String, dynamic> json) =>
    SetTimerResponse(
      startedAt: json['startedAt'] == null
          ? null
          : DateTime.parse(json['startedAt'] as String),
      totalTimeSeconds: json['totalTimeSeconds'] as int,
      remainedSeconds: json['remainedSeconds'] as int,
      emoji: $enumDecodeNullable(_$EmojiEnumMap, json['emoji']),
    );

Map<String, dynamic> _$SetTimerResponseToJson(SetTimerResponse instance) =>
    <String, dynamic>{
      'startedAt': instance.startedAt?.toIso8601String(),
      'totalTimeSeconds': instance.totalTimeSeconds,
      'remainedSeconds': instance.remainedSeconds,
      'emoji': _$EmojiEnumMap[instance.emoji],
    };

GetTimerResponse _$GetTimerResponseFromJson(Map<String, dynamic> json) =>
    GetTimerResponse(
      startedAt: DateTime.parse(json['startedAt'] as String),
      totalTimeSeconds: json['totalTimeSeconds'] as int,
      remainedSeconds: json['remainedSeconds'] as int,
      emoji: $enumDecode(_$EmojiEnumMap, json['emoji']),
      timerStatus: $enumDecode(_$TimerStatusEnumMap, json['timerStatus']),
    );

Map<String, dynamic> _$GetTimerResponseToJson(GetTimerResponse instance) =>
    <String, dynamic>{
      'startedAt': instance.startedAt.toIso8601String(),
      'totalTimeSeconds': instance.totalTimeSeconds,
      'remainedSeconds': instance.remainedSeconds,
      'emoji': _$EmojiEnumMap[instance.emoji]!,
      'timerStatus': _$TimerStatusEnumMap[instance.timerStatus]!,
    };

const _$TimerStatusEnumMap = {
  TimerStatus.running: 'running',
  TimerStatus.paused: 'paused',
  TimerStatus.ready: 'ready',
};

// **************************************************************************
// RetrofitGenerator
// **************************************************************************

// ignore_for_file: unnecessary_brace_in_string_interps

class _TimerApiClient implements TimerApiClient {
  _TimerApiClient(this._dio, {this.baseUrl}) {
    baseUrl ??= 'http://localhost:8080';
  }

  final Dio _dio;

  String? baseUrl;

  @override
  Future<SetTimerResponse> setTimer(request) async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _headers = <String, dynamic>{};
    final _data = <String, dynamic>{};
    _data.addAll(request.toJson());
    final _result = await _dio.fetch<Map<String, dynamic>>(
        _setStreamType<SetTimerResponse>(
            Options(method: 'POST', headers: _headers, extra: _extra)
                .compose(_dio.options, '/v1/timer/set',
                    queryParameters: queryParameters, data: _data)
                .copyWith(baseUrl: baseUrl ?? _dio.options.baseUrl)));
    final value = SetTimerResponse.fromJson(_result.data!);
    return value;
  }

  @override
  Future<GetTimerResponse> getTimer(userId) async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _headers = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.fetch<Map<String, dynamic>>(
        _setStreamType<GetTimerResponse>(
            Options(method: 'GET', headers: _headers, extra: _extra)
                .compose(_dio.options, '/v1/timer/${userId}',
                    queryParameters: queryParameters, data: _data)
                .copyWith(baseUrl: baseUrl ?? _dio.options.baseUrl)));
    final value = GetTimerResponse.fromJson(_result.data!);
    return value;
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
