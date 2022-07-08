import 'dart:async';
import 'dart:math';

import 'package:flutter/material.dart';
import 'package:mobx/mobx.dart';

part 'timer-store.g.dart';

class TimerStore = _TimerStore with _$TimerStore;

const defaultDuration = Duration(minutes: 25);

abstract class _TimerStore with Store {
  Timer? _timer;
  late Atom _atom;
  VoidCallback? _onFinish;

  _TimerStore() {
    _dispose = reaction((_) => duration, (Duration newValue) => sessionSeconds = newValue.inSeconds);
    _atom = Atom(
        name: "Seconds ticker",
        onObserved: () {
          _timer = Timer.periodic(const Duration(milliseconds: 100), (Timer timer) {
            if (remainedSeconds <= 0) {
              Timer(const Duration(milliseconds: 300), () {
                _onFinish?.call();
                reset();
              });
              _timer?.cancel();
            }
            _atom.reportChanged();
          });
        },
        onUnobserved: () {
          _timer?.cancel();
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
  int sessionSeconds = defaultDuration.inSeconds;

  @computed
  int get remainedSeconds {
    _atom.reportObserved();
    return startTime == null ? sessionSeconds : sessionSeconds - DateTime.now().difference(startTime!).inSeconds;
  }

  @computed
  String get remainedTime {
    _atom.reportObserved();
    return Duration(seconds: remainedSeconds).toString().split(".")[0];
  }

  @computed
  String get durationTime {
    return duration.toString().split(".")[0];
  }

  @computed
  TimerStatus get status {
    return isPaused
        ? sessionSeconds == duration.inSeconds
            ? TimerStatus.ready
            : TimerStatus.paused
        : TimerStatus.running;
  }

  @computed
  double get percent {
    _atom.reportObserved();
    return min(1 - remainedSeconds / duration.inSeconds, 1);
  }

  @action
  void start({int? durationSeconds, VoidCallback? onFinish}) {
    _onFinish = onFinish;
    isPaused = false;
    startTime = DateTime.now();
    if (durationSeconds != null) {
      duration = Duration(seconds: durationSeconds);
      sessionSeconds = duration.inSeconds;
    }
  }

  @action
  void pause() {
    sessionSeconds = remainedSeconds;
    isPaused = true;
    startTime = null;
  }

  @action
  void resume() {
    isPaused = false;
    startTime = DateTime.now();
  }

  @action
  void reset() {
    _onFinish = null;
    isPaused = true;
    startTime = null;
    sessionSeconds = duration.inSeconds;
    _timer?.cancel();
    _timer = null;
  }
}

enum TimerStatus {
  running,
  paused,
  ready,
}
