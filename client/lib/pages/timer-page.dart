import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:percent_indicator/circular_percent_indicator.dart';

class TimerPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: SizedBox(
          child: CircularPercentIndicator(
            radius: 130,
            animation: true,
            percent: 0.2,
            lineWidth: 10.0,
            circularStrokeCap: CircularStrokeCap.round,
            progressColor: Colors.blue,
            center: Center(
                child: ListView(
              shrinkWrap: true,
              padding: const EdgeInsets.all(20),
              children: const [
                Center(child: Text("💯", style: TextStyle(fontSize: 24))),
                Center(
                    child: Text("50:00:00",
                        style: TextStyle(fontSize: 40, color: Colors.blue))),
              ],
            )),
            footer: Padding(
              padding: const EdgeInsets.all(20),
              child: Padding(
                padding: const EdgeInsets.all(8.0),
                child: IconButton(
                  icon: const Icon(Icons.play_arrow, color: Colors.blue),
                  iconSize: 48,
                  onPressed: () {},
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }
}

class CircleIndicatorPaint extends CustomPainter {
  final double progress;
  final double strokeWidth;
  final Color color;

  CircleIndicatorPaint(this.progress, this.strokeWidth, this.color);

  @override
  void paint(Canvas canvas, Size size) {
    final paint = Paint()
      ..strokeWidth = strokeWidth
      ..color = color
      ..strokeCap = StrokeCap.round
      ..style = PaintingStyle.stroke;

    var rect = Rect.fromCenter(
        center: Offset(size.width / 2, size.height / 2),
        width: size.width,
        height: size.height);

    var startAngle = pi / 180 * 0;
    var sweepAngle = pi / 180 * 359;

    canvas.drawArc(rect, startAngle, sweepAngle, false, paint);
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) {
    // TODO: implement shouldRepaint
    return true;
  }
}
