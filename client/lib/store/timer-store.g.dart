// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'timer-store.dart';

// **************************************************************************
// StoreGenerator
// **************************************************************************

// ignore_for_file: non_constant_identifier_names, unnecessary_brace_in_string_interps, unnecessary_lambdas, prefer_expression_function_bodies, lines_longer_than_80_chars, avoid_as, avoid_annotating_with_dynamic, no_leading_underscores_for_local_identifiers

mixin _$TimerStore on _TimerStore, Store {
  Computed<String>? _$renamedTimeComputed;

  @override
  String get remainedTime =>
      (_$renamedTimeComputed ??= Computed<String>(() => super.remainedTime,
              name: '_TimerStore.renamedTime'))
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

  late final _$remainedSecondsAtom =
      Atom(name: '_TimerStore.remainedSeconds', context: context);

  @override
  int get remainedSeconds {
    _$remainedSecondsAtom.reportRead();
    return super.remainedSeconds;
  }

  @override
  set remainedSeconds(int value) {
    _$remainedSecondsAtom.reportWrite(value, super.remainedSeconds, () {
      super.remainedSeconds = value;
    });
  }

  late final _$_TimerStoreActionController =
      ActionController(name: '_TimerStore', context: context);

  @override
  void start(int sessionSeconds) {
    final _$actionInfo =
        _$_TimerStoreActionController.startAction(name: '_TimerStore.start');
    try {
      return super.start(sessionSeconds);
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
  void restart() {
    final _$actionInfo =
        _$_TimerStoreActionController.startAction(name: '_TimerStore.restart');
    try {
      return super.restart();
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
remainedSeconds: ${remainedSeconds},
renamedTime: ${remainedTime},
status: ${status},
percent: ${percent}
    ''';
  }
}
