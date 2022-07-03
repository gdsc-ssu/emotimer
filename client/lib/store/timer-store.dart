

import 'dart:async';

import 'package:mobx/mobx.dart';

part 'timer-store.g.dart';


class TimerStore = _TimerStore with _$TimerStore;

const defaultDuration = Duration(minutes: 25);

abstract class _TimerStore with Store {
  Timer? _timer;

  _TimerStore();

  @observable
  bool isPaused = true;

  @observable
  DateTime? startTime;

  @observable
  Duration duration = defaultDuration;

  @observable
  int remainedSeconds = defaultDuration.inSeconds;

  @computed
  String get remainedTime {
    return Duration(seconds: remainedSeconds).toString().split(".")[0];
  }

  @computed
  TimerStatus get status {
    return isPaused ?
        remainedSeconds == duration.inSeconds
          ? TimerStatus.ready
          : TimerStatus.paused
        : TimerStatus.running;
  }

  @computed
  double get percent {
    return 1 - remainedSeconds / duration.inSeconds;
  }


  @action
  void start(int sessionSeconds) {
    print(remainedSeconds);
    if( _timer != null) {
      return;
    }

    isPaused = false;
    startTime = DateTime.now();
    duration = Duration(seconds: sessionSeconds);
    remainedSeconds = duration.inSeconds;
    _timer = Timer.periodic(const Duration(seconds: 1), (timer) {
      print("TIMER: $remainedTime, paused: $isPaused");
      if (isPaused) {
        return;
      }

      remainedSeconds = remainedSeconds - 1;
      if (remainedSeconds == 0) {
        reset();
      }
    });
  }




  @action
  void pause() {
    isPaused = true;
  }

  @action
  void restart() {
    isPaused = false;
  }

  @action
  void reset() {
    isPaused = true;
    startTime = null;
    remainedSeconds = duration.inSeconds;
    _timer?.cancel();
    _timer = null;
  }

}

enum TimerStatus {
  running,
  paused,
  ready,
}
