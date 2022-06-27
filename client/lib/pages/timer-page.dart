import 'dart:math';

import 'package:flutter/material.dart';
import 'package:percent_indicator/circular_percent_indicator.dart';
import 'package:provider/provider.dart';
import 'package:flutter_mobx/flutter_mobx.dart';

import '../store/timer-store.dart';

final timerStore = TimerStore();

class TimerPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Provider<TimerStore>(
        create: (_) => timerStore,
        child: Observer(
            builder: (_) => Scaffold(
                  body: Center(
                    child: SizedBox(
                      child: CircularPercentIndicator(
                        radius: 130,
                        animation: true,
                        animateFromLastPercent: true,
                        animationDuration: 400,
                        curve: Curves.ease,
                        percent: timerStore.percent,
                        lineWidth: 10.0,
                        circularStrokeCap: CircularStrokeCap.round,
                        progressColor: Colors.blue,
                        center: Center(
                            child: ListView(
                          shrinkWrap: true,
                          padding: const EdgeInsets.all(20),
                          children: [
                            Center(child: Text("💯", style: const TextStyle(fontSize: 24))),
                            Center(child: Text(timerStore.renamedTime, style: const TextStyle(fontSize: 40, color: Colors.blue))),
                          ],
                        )),
                        footer: Padding(
                          padding: const EdgeInsets.all(20),
                          child: Padding(
                            padding: const EdgeInsets.all(8.0),
                            child: TimerButton(),
                          ),
                        ),
                      ),
                    ),
                  ),
                )));
  }
}

class TimerButton extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final timer = Provider.of<TimerStore>(context);
    return Observer(builder: (_) {
      if (timer.status == TimerStatus.ready) {
        return IconButton(
          icon: const Icon(Icons.play_arrow, color: Colors.blue),
          iconSize: 48,
          onPressed: () {
            timer.start(10);
          },
        );
      } else {
        return Center(
            child: Row(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisSize: MainAxisSize.min,
          children: [
            timer.status == TimerStatus.paused
                ? IconButton(
                    icon: const Icon(Icons.play_arrow, color: Colors.blue),
                    iconSize: 48,
                    onPressed: () => timer.restart(),
                  )
                : IconButton(
                    icon: const Icon(Icons.pause, color: Colors.blue),
                    iconSize: 48,
                    onPressed: () => timer.pause(),
                  ),
            IconButton(
              icon: const Icon(Icons.stop, color: Colors.blue),
              iconSize: 48,
              onPressed: () => timer.reset(),
            )
          ],
        ));
      }
    });
  }
}
