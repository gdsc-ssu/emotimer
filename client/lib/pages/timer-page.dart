import 'dart:math';

import 'package:flutter/material.dart';
import 'package:gdsc_timer/shared/app_colors.dart';
import 'package:percent_indicator/circular_percent_indicator.dart';
import 'package:provider/provider.dart';
import 'package:flutter_mobx/flutter_mobx.dart';

import '../store/timer-store.dart';

class TimerPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final timerStore = Provider.of<TimerStore>(context);
    return Observer(builder: (_) {
      return Scaffold(
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
              progressColor: AppColors.primary,
              center: Center(
                  child: ListView(
                shrinkWrap: true,
                padding: const EdgeInsets.all(20),
                children: [
                  Padding(padding: EdgeInsets.only(bottom: 8), child: Center(child: Text("💯", style: const TextStyle(fontSize: 24)))),
                  Center(
                      child:
                          Text(timerStore.remainedTime, style: const TextStyle(fontSize: 40, color: AppColors.primary, fontWeight: FontWeight.bold))),
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
      );
    });
  }
}

class TimerButton extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final timer = Provider.of<TimerStore>(context);
    return Observer(builder: (_) {
      if (timer.status == TimerStatus.ready) {
        return IconButton(
          icon: const Icon(Icons.play_arrow, color: AppColors.primary),
          iconSize: 48,
          onPressed: () {
            timer.start(1200);
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
                    icon: const Icon(Icons.play_arrow, color: AppColors.primary),
                    iconSize: 48,
                    onPressed: () => timer.restart(),
                  )
                : IconButton(
                    icon: const Icon(Icons.pause, color: AppColors.primary),
                    iconSize: 48,
                    onPressed: () => timer.pause(),
                  ),
            IconButton(
              icon: const Icon(Icons.stop, color: AppColors.primary),
              iconSize: 48,
              onPressed: () => timer.reset(),
            )
          ],
        ));
      }
    });
  }
}
