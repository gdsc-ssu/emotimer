import 'package:flutter/material.dart';
import 'package:gdsc_timer/pages/statics/monthly-statics.dart';
import 'package:gdsc_timer/pages/statics/weelky-statics.dart';
import 'package:gdsc_timer/pages/statics/yearly-statics.dart';
import 'package:gdsc_timer/shared/app_colors.dart';
import 'package:gdsc_timer/shared/common-components.dart';

class StaticsPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: Components.appBar("Statics"),
      body: DefaultTabController(
        length: 3,
        initialIndex: 0,
        child: Column(
          children: <Widget>[
            const TabBar(indicatorColor: Colors.transparent, unselectedLabelColor: Colors.grey, labelColor: AppColors.primary, tabs: [
              Tab(text: "Weekly"),
              Tab(text: "Monthly"),
              Tab(text: "Yearly"),
            ]),
            Expanded(
              child: TabBarView(
                children: [
                  WeeklyStaticsPage(),
                  MonthlyStaticsPage(),
                  YearlyStaticsPage(),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
