import 'dart:async';

import 'package:flutter/material.dart';
import 'package:mobx/mobx.dart';

part 'timer-store.g.dart';

class TimerStore = _TimerStore with _$TimerStore;

const defaultDuration = Duration(minutes: 25);

abstract class _TimerStore with Store {
  Timer? _timer;

  _TimerStore() {
    _dispose = reaction((_) => duration, (Duration newValue) {
      remainedSeconds = newValue.inSeconds;
    });
  }

  @observable
  bool isPaused = true;

  @observable
  DateTime? startTime;

  @observable
  Duration duration = defaultDuration;

  late ReactionDisposer _dispose;

  @observable
  int remainedSeconds = defaultDuration.inSeconds;

  @computed
  String get remainedTime {
    return Duration(seconds: remainedSeconds).toString().split(".")[0];
  }

  @computed
  String get durationTime {
    return duration.toString().split(".")[0];
  }

  @computed
  TimerStatus get status {
    return isPaused
        ? remainedSeconds == duration.inSeconds
            ? TimerStatus.ready
            : TimerStatus.paused
        : TimerStatus.running;
  }

  @computed
  double get percent {
    print(1 - remainedSeconds / duration.inSeconds);
    return 1 - remainedSeconds / duration.inSeconds;
  }

  @action
  void start({int? sessionSeconds, VoidCallback? onFinish}) {
    print(remainedSeconds);
    if (_timer != null) {
      return;
    }

    isPaused = false;
    startTime = DateTime.now();
    if (sessionSeconds != null) {
      duration = Duration(seconds: sessionSeconds);
      remainedSeconds = duration.inSeconds;
    }
    _timer = Timer.periodic(const Duration(seconds: 1), (timer) {
      print("TIMER: $remainedTime, paused: $isPaused");
      if (isPaused) {
        return;
      }

      if (remainedSeconds == 0) {
        reset();
        onFinish?.call();
        return;
      }
      remainedSeconds = remainedSeconds - 1;
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
