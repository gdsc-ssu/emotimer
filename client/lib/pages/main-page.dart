import 'package:flutter/material.dart';
import 'package:flutter_mobx/flutter_mobx.dart';
import 'package:gdsc_timer/pages/settings-page.dart';
import 'package:gdsc_timer/pages/statics-page.dart';
import 'package:gdsc_timer/pages/timer-page.dart';
import 'package:gdsc_timer/shared/app_colors.dart';
import 'package:gdsc_timer/store/auth-store.dart';
import 'package:gdsc_timer/store/timer-store.dart';
import 'package:provider/provider.dart';


class Page {
  final String title;
  final IconData icon;
  final Widget page;

  Page(this.title, this.icon, this.page);
}

class MainPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    var pages = [
      Page("Timer", Icons.access_time, TimerPage(),),
      Page("Statics", Icons.bar_chart, StaticsPage(),),
      Page("Settings", Icons.settings, SettingsPage(),),
    ];
    var authStore = Provider.of<AuthStore>(context);

    if (!authStore.isLoggedIn)
      return Container();

    var timerStore = TimerStore(authStore.makeDio());
    return Provider<TimerStore>(
      create: (_) => timerStore,
      child: DefaultTabController(
        initialIndex: 0,
        length: 3,
        child: Scaffold(
          bottomNavigationBar: TabBar(
            indicatorColor: Colors.transparent, // indicator 없애기
            unselectedLabelColor: Colors.grey, // 선택되지 않은 tab 색
            labelColor: AppColors.primary, // 선택된 tab의 색
            tabs: pages.map((p) => Tab(icon: Icon(p.icon))).toList(),
          ),
          body: TabBarView(
            children: pages.map((p) => p.page).toList(),
          ),
        ),
      )
    );
  }
}
