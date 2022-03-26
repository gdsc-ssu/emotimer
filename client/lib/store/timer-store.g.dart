// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'timer-store.dart';

// **************************************************************************
// StoreGenerator
// **************************************************************************

// ignore_for_file: non_constant_identifier_names, unnecessary_brace_in_string_interps, unnecessary_lambdas, prefer_expression_function_bodies, lines_longer_than_80_chars, avoid_as, avoid_annotating_with_dynamic

mixin _$TimerStore on _TimerStore, Store {
  final _$pauseAtom = Atom(name: '_TimerStore.pause');

  @override
  bool get pause {
    _$pauseAtom.reportRead();
    return super.pause;
  }

  @override
  set pause(bool value) {
    _$pauseAtom.reportWrite(value, super.pause, () {
      super.pause = value;
    });
  }

  final _$startTimeAtom = Atom(name: '_TimerStore.startTime');

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

  final _$remainedSecondsAtom = Atom(name: '_TimerStore.remainedSeconds');

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

  final _$_TimerStoreActionController = ActionController(name: '_TimerStore');

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
  void stop() {
    final _$actionInfo =
        _$_TimerStoreActionController.startAction(name: '_TimerStore.stop');
    try {
      return super.stop();
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
pause: ${pause},
startTime: ${startTime},
remainedSeconds: ${remainedSeconds}
    ''';
  }
}
