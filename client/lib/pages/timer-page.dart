import 'dart:async';
import 'dart:math';

import 'package:confetti/confetti.dart';
import 'package:flutter/material.dart';
import 'package:flutter_mobx/flutter_mobx.dart';
import 'package:gdsc_timer/shared/app_colors.dart';
import 'package:provider/provider.dart';
import 'package:sleek_circular_slider/sleek_circular_slider.dart';

import '../store/timer-store.dart';

final appearance = CircularSliderAppearance(
  angleRange: 360,
  startAngle: 270,
  animationEnabled: true,
  size: 300,
  customWidths: CustomSliderWidths(progressBarWidth: 12, trackWidth: 12, handlerSize: 12, shadowWidth: 2),
  customColors: CustomSliderColors(
    progressBarColors: [
      Color(0xff242934),
      AppColors.primary,
    ],
    dotColor: AppColors.primary,
    trackColor: Colors.black12,
  ),
);
final ConfettiController _confettiController = ConfettiController(
  duration: Duration(seconds: 5),
);

class TimerPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final timerStore = Provider.of<TimerStore>(context);
    return Observer(builder: (_) {
      var maximumSeconds = 3600;

      OnChange? onChange = (double value) {
        var seconds = (value * maximumSeconds).toInt();
        timerStore.duration = Duration(seconds: max(seconds - seconds % 5, 5));
      };
      var displayTime = timerStore.durationTime;
      var currentValue = timerStore.duration.inSeconds.toDouble() / maximumSeconds;

      if (timerStore.status != TimerStatus.ready) {
        onChange = null;
        displayTime = timerStore.remainedTime;
        currentValue = timerStore.percent;
      }

      return Scaffold(
          body: Center(
              child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          SleekCircularSlider(
              appearance: appearance,
              min: 0,
              max: 1,
              innerWidget: (value) {
                return Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    ConfettiWidget(
                      confettiController: _confettiController,
                      blastDirectionality: BlastDirectionality.explosive,
                      shouldLoop: false,
                      emissionFrequency: 0.05,
                      colors: const [Colors.green, Colors.blue, Colors.pink, Colors.orange, Colors.purple],
                      // createParticlePath: drawStar, // define a custom shape/path.
                    ),
                    Center(child: Text("💯", style: const TextStyle(fontSize: 24))),
                    Text(displayTime, style: const TextStyle(fontSize: 48, color: AppColors.primary, fontWeight: FontWeight.bold)),
                    Padding(padding: EdgeInsets.all(8))
                  ],
                );
              },
              initialValue: currentValue,
              onChange: onChange),
          Padding(
            padding: const EdgeInsets.all(24),
            child: TimerButton(
              onStart: () {
                timerStore.start(onFinish: () {
                  _confettiController.play();
                  Timer(const Duration(seconds: 1), () {
                    _confettiController.stop();
                  });
                });
              },
              onReset: () => timerStore.reset(),
              onResume: () => timerStore.restart(),
              onPause: () => timerStore.pause(),
            ),
          )
        ],
      )));
    });
  }
}

class TimerButton extends StatelessWidget {
  VoidCallback onStart;
  VoidCallback onPause;
  VoidCallback onResume;
  VoidCallback onReset;

  TimerButton({
    required this.onStart,
    required this.onPause,
    required this.onResume,
    required this.onReset,
  });

  @override
  Widget build(BuildContext context) {
    final timer = Provider.of<TimerStore>(context);
    return Observer(builder: (_) {
      if (timer.status == TimerStatus.ready) {
        return IconButton(
          icon: const Icon(Icons.play_arrow, color: AppColors.primary),
          iconSize: 48,
          onPressed: onStart,
        );
      } else {
        return Center(
            child: Row(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisSize: MainAxisSize.min,
          children: [
            timer.status == TimerStatus.paused
                ? IconButton(
                    icon: const Icon(Icons.play_arrow, color: AppColors.primary),
                    iconSize: 48,
                    onPressed: onResume,
                  )
                : IconButton(
                    icon: const Icon(Icons.pause, color: AppColors.primary),
                    iconSize: 48,
                    onPressed: onPause,
                  ),
            IconButton(
              icon: const Icon(Icons.stop, color: AppColors.primary),
              iconSize: 48,
              onPressed: onReset,
            )
          ],
        ));
      }
    });
  }
}
