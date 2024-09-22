package edu.schoolapp.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class TodoModel(
    @JvmField var title: String?,
    @JvmField var description: String?,
    @JvmField var date: String?
)