import 'dart:math';

import 'package:fl_chart/fl_chart.dart';
import 'package:flutter/material.dart';
import 'package:gdsc_timer/shared/app_colors.dart';

class ChartData {
  String label;
  double first;
  double second;
  double third;

  ChartData({required this.label, required this.first, required this.second, required this.third});
}

class StaticsChart extends StatefulWidget {


  List<ChartData> data;

  StaticsChart({Key? key, required this.data}) : super(key: key);

  @override
  State<StatefulWidget> createState() => StaticsChartState();
}

class StaticsChartState extends State<StaticsChart> {
  final Color dark = const Color(0xff34487B);
  final Color normal = const Color(0xff6DAFDE);
  final Color light = const Color(0xff4369B1);


  late final titles = widget.data.map((e) => e.label).toList().asMap();


  Widget bottomTitles(double value, TitleMeta meta) {
    return SideTitleWidget(
      child: Padding(padding: EdgeInsets.only(right: 8),
          child: Text(titles[value.toInt()] ?? "NULL", style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold, color: AppColors.primary),
              textAlign: TextAlign.center)),
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
    var data = widget.data;
    return [
      for (var i = 0; i < data.length; i++)
        barChartGroupData(i, data[i].first, data[i].second, data[i].third),
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
