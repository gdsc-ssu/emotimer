import 'package:flutter/material.dart';
import 'package:gdsc_timer/shared/app_colors.dart';

class Components {
  static PreferredSizeWidget appBar(String title) {
    return AppBar(
      title: Text(title),
      centerTitle: true,
      titleTextStyle: const TextStyle(color: AppColors.primary, fontWeight: FontWeight.bold, fontSize: 16),
      backgroundColor: Colors.white,
      foregroundColor: Colors.black,
      shadowColor: Colors.black12,
    );
  }
}
