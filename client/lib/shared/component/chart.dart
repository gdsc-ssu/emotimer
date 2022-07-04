import 'dart:math';

import 'package:fl_chart/fl_chart.dart';
import 'package:flutter/material.dart';
import 'package:gdsc_timer/shared/app_colors.dart';

class StaticsChart extends StatefulWidget {
  const StaticsChart({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => StaticsChartState();
}

class StaticsChartState extends State<StaticsChart> {
  final Color dark = const Color(0xff34487B);
  final Color normal = const Color(0xff6DAFDE);
  final Color light = const Color(0xff4369B1);


  final titles = {
    0: "S",
    1: "M",
    2: "T",
    3: "W",
    4: "T",
    5: "F",
    6: "S",
  };

  Widget bottomTitles(double value, TitleMeta meta) {
    return SideTitleWidget(
      child: Padding(padding: EdgeInsets.only(right: 8), child: Text(titles[value] ?? "NULL", style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold, color: AppColors.primary), textAlign: TextAlign.center)),
      space: 5,
      axisSide: meta.axisSide,
      angle: -(pi / 2),
    );
  }

  @override
  Widget build(BuildContext context) {
    return AspectRatio(
      aspectRatio: 1.5,
      child: Padding(padding: const EdgeInsets.symmetric(horizontal: 8), child: RotatedBox(
        quarterTurns: 1,
        child: BarChart(
          BarChartData(
            alignment: BarChartAlignment.center,
            barTouchData: BarTouchData(enabled: false),
            titlesData: FlTitlesData(
              show: true,
              bottomTitles: AxisTitles(
                sideTitles: SideTitles(
                  showTitles: true,
                  reservedSize: 28,
                  getTitlesWidget: bottomTitles,
                ),
              ),
              leftTitles: AxisTitles(sideTitles: SideTitles(showTitles: false)),
              topTitles: AxisTitles(sideTitles: SideTitles(showTitles: false)),
              rightTitles: AxisTitles(sideTitles: SideTitles(showTitles: false)),
            ),
            gridData: FlGridData(show: false),
            borderData: FlBorderData(show: false),
            groupsSpace: 8,
            // maxY: 50000000000,
            barGroups: getData(),
          ),
        ),
      )),
    );
  }

  List<BarChartGroupData> getData() {
    return [
      barChartGroupData(0, 8, 40, 35),
      barChartGroupData(2, 3, 15, 10),
      barChartGroupData(3, 4, 20, 15),
      barChartGroupData(4, 5, 25, 20),
      barChartGroupData(5, 6, 30, 25),
      barChartGroupData(1, 2, 10, 5),
      barChartGroupData(6, 7, 35, 30),
    ];
  }

  BarChartGroupData barChartGroupData(int x, double first, double second, double third) {
    var total = first + second + third;
    return BarChartGroupData(
      x: x,
      barsSpace: 4,
      barRods: [
        BarChartRodData(
            width: 24,
            toY: total,
            rodStackItems: [
              BarChartRodStackItem(0, total, dark),
              BarChartRodStackItem(first, total, normal),
              BarChartRodStackItem(first + second, total, light),
            ],
            borderRadius: const BorderRadius.all(Radius.circular(4))),
      ],
    );
  }
}
