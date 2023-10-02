package edu.schoolapp

class PrimaryMenuModel(
    val menuImageResource: Int,
    val menuName: String? = null,
    val menuType: PrimaryMenuType? = null,
    var isSelected: Boolean = true
)