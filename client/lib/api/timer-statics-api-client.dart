import 'package:dio/dio.dart';
import 'package:gdsc_timer/domain/emoji.dart';
import 'package:retrofit/retrofit.dart';
import 'package:json_annotation/json_annotation.dart';

part 'timer-statics-api-client.g.dart';

@RestApi(baseUrl: "https://emotimer.ml")
abstract class TimerStaticsApiClient {
  factory TimerStaticsApiClient(Dio dio, {String baseUrl}) = _TimerStaticsApiClient;

  @GET("/api/timer/statistics")
  Future<List<TimerStatistics>> getStatics(
    @Query("year") int year,
    @Query("month") int? month,
    @Query("week") int? week,
  );
}

@JsonSerializable()
class TimerStatistics {
  int? month;
  int? week;
  String? dayOfWeek;
  List<UsageRecord> usageRecords;

  TimerStatistics({this.month, this.week, this.dayOfWeek, required this.usageRecords});

  factory TimerStatistics.fromJson(Map<String, dynamic> json) => _$TimerStatisticsFromJson(json);

  Map<String, dynamic> toJson() => _$TimerStatisticsToJson(this);
}

@JsonSerializable()
class UsageRecord {
  Emoji emoji;
  int totalTimeSeconds;

  UsageRecord({required this.emoji, required this.totalTimeSeconds});

  factory UsageRecord.fromJson(Map<String, dynamic> json) => _$UsageRecordFromJson(json);

  Map<String, dynamic> toJson() => _$UsageRecordToJson(this);
}
