package edu.schoolapp.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class PrimaryMenuDao(
    var name: String?,
    @JvmField var type: String?,
    @JvmField var isEnable: Boolean
)