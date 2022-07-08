import 'package:flutter/material.dart';
import 'package:gdsc_timer/pages/statics/indexed-widget-builder.dart';
import 'package:gdsc_timer/pages/statics/list-ranking.dart';
import 'package:gdsc_timer/shared/component/chart.dart';
import 'package:gdsc_timer/shared/component/no-content.dart';
import 'package:gdsc_timer/shared/skeleton/src/stylings.dart';
import 'package:gdsc_timer/shared/skeleton/src/widgets.dart';

class Statics extends StatelessWidget {
  late final bool isLoading;
  late final List<ChartData> data;
  late final List<ListRankingItem> ranking;
  late final IndexedTitleBuilder pageTitleRenderer;
  late final String listRankingTitle;
  late final int pageCount;
  late final OnChangePage onChangePage;
  late final int skeletonChartBarCount;

  Statics({
    Key? key,
    required this.isLoading,
    required this.data,
    required this.listRankingTitle,
    required this.ranking,
    required this.pageCount,
    required this.pageTitleRenderer,
    required this.onChangePage,
    required this.skeletonChartBarCount,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    if (data.isEmpty) {
      return NoContent();
    }

    return Padding(
      padding: const EdgeInsets.all(24),
      child: SingleChildScrollView(
        child: Column(
          children: [
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 20),
              child: ArrowPageNavigator(
                currentPage: 0,
                maxPage: pageCount,
                pageTitleRenderer: pageTitleRenderer,
                onChangePage: onChangePage,
              ),
            ),
            Skeleton(
              isLoading: isLoading,
              duration: const Duration(milliseconds: 1000),
              skeleton: Padding(padding: const EdgeInsets.symmetric(vertical: 16), child: SkeletonListView(
                  shrinkWrap: true,
                  scrollable: false,
                  item: SkeletonListTile(
                    hasSubtitle: false,
                    hasLeading: true,
                    padding: const EdgeInsets.symmetric(horizontal: 8,vertical: 4),
                    titleStyle: SkeletonLineStyle(height: 24.toDouble(), randomLength: true),
                    leadingStyle: const SkeletonAvatarStyle(height: 20, width: 20),
                  ),
                  padding: EdgeInsets.zero,
                  itemCount: skeletonChartBarCount)),
              child: StaticsChart(data: data),
            ),
            const Divider(height: 32, thickness: 1),
            ListRanking(title: listRankingTitle, items: ranking.take(3).toList(), isLoading: isLoading),
            const Divider(height: 32, thickness: 1),
            ListRanking(title: "Others", items: ranking.skip(3).toList(), isLoading: isLoading),
          ],
        ),
      ),
    );
  }
}
