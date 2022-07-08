// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'timer-statics-api-client.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

TimerStatistics _$TimerStatisticsFromJson(Map<String, dynamic> json) =>
    TimerStatistics(
      month: json['month'] as int?,
      week: json['week'] as int?,
      dayOfWeek: json['dayOfWeek'] as String?,
      usageRecords: (json['usageRecords'] as List<dynamic>)
          .map((e) => UsageRecord.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$TimerStatisticsToJson(TimerStatistics instance) =>
    <String, dynamic>{
      'month': instance.month,
      'week': instance.week,
      'dayOfWeek': instance.dayOfWeek,
      'usageRecords': instance.usageRecords,
    };

UsageRecord _$UsageRecordFromJson(Map<String, dynamic> json) => UsageRecord(
      emoji: $enumDecode(_$EmojiEnumMap, json['emoji']),
      totalTimeSeconds: json['totalTimeSeconds'] as int,
    );

Map<String, dynamic> _$UsageRecordToJson(UsageRecord instance) =>
    <String, dynamic>{
      'emoji': _$EmojiEnumMap[instance.emoji]!,
      'totalTimeSeconds': instance.totalTimeSeconds,
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

// **************************************************************************
// RetrofitGenerator
// **************************************************************************

// ignore_for_file: unnecessary_brace_in_string_interps

class _TimerStaticsApiClient implements TimerStaticsApiClient {
  _TimerStaticsApiClient(this._dio, {this.baseUrl}) {
    baseUrl ??= 'https://emotimer.ml';
  }

  final Dio _dio;

  String? baseUrl;

  @override
  Future<List<TimerStatistics>> getStatics(year, month, week) async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{
      r'year': year,
      r'month': month,
      r'week': week
    };
    queryParameters.removeWhere((k, v) => v == null);
    final _headers = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.fetch<List<dynamic>>(
        _setStreamType<List<TimerStatistics>>(
            Options(method: 'GET', headers: _headers, extra: _extra)
                .compose(_dio.options, '/api/timer/statistics',
                    queryParameters: queryParameters, data: _data)
                .copyWith(baseUrl: baseUrl ?? _dio.options.baseUrl)));
    var value = _result.data!
        .map((dynamic i) => TimerStatistics.fromJson(i as Map<String, dynamic>))
        .toList();
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
