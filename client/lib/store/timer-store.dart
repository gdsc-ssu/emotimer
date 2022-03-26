

import 'dart:async';

import 'package:mobx/mobx.dart';

part 'timer-store.g.dart';


class TimerStore = _TimerStore with _$TimerStore;

abstract class _TimerStore with Store {
  late final Timer _timer;


  _TimerStore() {
    _timer = Timer.periodic(const Duration(seconds: 1), (timer) {
      if (!pause) {
        remainedSeconds -= 1;
      }
    });
  }
  
  
  @observable
  bool pause = true;

  @observable
  DateTime? startTime;

  @observable
  int remainedSeconds = 0;

  @action
  void start(int sessionSeconds) {
    pause = false;
    startTime = DateTime.now();
    remainedSeconds = sessionSeconds;
  }

  @action
  void stop() {
    pause = true;
  }

  @action
  void reset() {
    pause = true;
    startTime = null;
  }
}