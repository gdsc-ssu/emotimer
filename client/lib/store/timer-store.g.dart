// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'timer-store.dart';

// **************************************************************************
// StoreGenerator
// **************************************************************************

// ignore_for_file: non_constant_identifier_names, unnecessary_brace_in_string_interps, unnecessary_lambdas, prefer_expression_function_bodies, lines_longer_than_80_chars, avoid_as, avoid_annotating_with_dynamic, no_leading_underscores_for_local_identifiers

mixin _$TimerStore on _TimerStore, Store {
  Computed<int>? _$remainedSecondsComputed;

  @override
  int get remainedSeconds =>
      (_$remainedSecondsComputed ??= Computed<int>(() => super.remainedSeconds,
              name: '_TimerStore.remainedSeconds'))
          .value;
  Computed<String>? _$remainedTimeComputed;

  @override
  String get remainedTime =>
      (_$remainedTimeComputed ??= Computed<String>(() => super.remainedTime,
              name: '_TimerStore.remainedTime'))
          .value;
  Computed<String>? _$durationTimeComputed;

  @override
  String get durationTime =>
      (_$durationTimeComputed ??= Computed<String>(() => super.durationTime,
              name: '_TimerStore.durationTime'))
          .value;
  Computed<TimerStatus>? _$statusComputed;

  @override
  TimerStatus get status => (_$statusComputed ??=
          Computed<TimerStatus>(() => super.status, name: '_TimerStore.status'))
      .value;
  Computed<double>? _$percentComputed;

  @override
  double get percent => (_$percentComputed ??=
          Computed<double>(() => super.percent, name: '_TimerStore.percent'))
      .value;

  late final _$isPausedAtom =
      Atom(name: '_TimerStore.isPaused', context: context);

  @override
  bool get isPaused {
    _$isPausedAtom.reportRead();
    return super.isPaused;
  }

  @override
  set isPaused(bool value) {
    _$isPausedAtom.reportWrite(value, super.isPaused, () {
      super.isPaused = value;
    });
  }

  late final _$startTimeAtom =
      Atom(name: '_TimerStore.startTime', context: context);

  @override
  DateTime? get startTime {
    _$startTimeAtom.reportRead();
    return super.startTime;
  }

  @override
  set startTime(DateTime? value) {
    _$startTimeAtom.reportWrite(value, super.startTime, () {
      super.startTime = value;
    });
  }

  late final _$durationAtom =
      Atom(name: '_TimerStore.duration', context: context);

  @override
  Duration get duration {
    _$durationAtom.reportRead();
    return super.duration;
  }

  @override
  set duration(Duration value) {
    _$durationAtom.reportWrite(value, super.duration, () {
      super.duration = value;
    });
  }

  late final _$sessionSecondsAtom =
      Atom(name: '_TimerStore.sessionSeconds', context: context);

  @override
  int get sessionSeconds {
    _$sessionSecondsAtom.reportRead();
    return super.sessionSeconds;
  }

  @override
  set sessionSeconds(int value) {
    _$sessionSecondsAtom.reportWrite(value, super.sessionSeconds, () {
      super.sessionSeconds = value;
    });
  }

  late final _$_TimerStoreActionController =
      ActionController(name: '_TimerStore', context: context);

  @override
  void start({int? durationSeconds, VoidCallback? onFinish}) {
    final _$actionInfo =
        _$_TimerStoreActionController.startAction(name: '_TimerStore.start');
    try {
      return super.start(durationSeconds: durationSeconds, onFinish: onFinish);
    } finally {
      _$_TimerStoreActionController.endAction(_$actionInfo);
    }
  }

  @override
  void pause() {
    final _$actionInfo =
        _$_TimerStoreActionController.startAction(name: '_TimerStore.pause');
    try {
      return super.pause();
    } finally {
      _$_TimerStoreActionController.endAction(_$actionInfo);
    }
  }

  @override
  void resume() {
    final _$actionInfo =
        _$_TimerStoreActionController.startAction(name: '_TimerStore.resume');
    try {
      return super.resume();
    } finally {
      _$_TimerStoreActionController.endAction(_$actionInfo);
    }
  }

  @override
  void reset() {
    final _$actionInfo =
        _$_TimerStoreActionController.startAction(name: '_TimerStore.reset');
    try {
      return super.reset();
    } finally {
      _$_TimerStoreActionController.endAction(_$actionInfo);
    }
  }

  @override
  String toString() {
    return '''
isPaused: ${isPaused},
startTime: ${startTime},
duration: ${duration},
sessionSeconds: ${sessionSeconds},
remainedSeconds: ${remainedSeconds},
remainedTime: ${remainedTime},
durationTime: ${durationTime},
status: ${status},
percent: ${percent}
    ''';
  }
}
