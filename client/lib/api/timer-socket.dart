import 'package:dio/dio.dart';
import 'package:gdsc_timer/api/timer-api-client.dart';
import 'package:gdsc_timer/domain/emoji.dart';
import 'package:stomp_dart_client/stomp.dart';
import 'package:stomp_dart_client/stomp_config.dart';
import 'package:stomp_dart_client/stomp_frame.dart';

class TimerConnector {
  static StompClient? client;

  static void connect() {
    client = StompClient(
        config: StompConfig.SockJS(
            url: 'https://emotimer.ml/ws-timer',
            onConnect: (StompFrame frame) {
              print('connected');
              client!.subscribe(
                  destination: '/sub/timer/abc',
                  callback: (frame) {
                    // Received a frame for this subscription
                    print(frame.body);
                  });
            },
            onDebugMessage: print,
            onStompError: print)

    );

    client!.activate();
  }

  static Future<SetTimerResponse> test() async {
    var client = TimerApiClient(Dio());
    return await client.setTimer(SetTimerRequest(totalTime: 25 * 60, emoji: Emoji.ANGER_SYMBOL));
  }
}
