package edu.schoolapp.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class AssignmentModel(
    @JvmField var title: String?,
    @JvmField var description: String?,
    @JvmField var unit: String?,
    @JvmField var mark: Int,
    @JvmField var markObtained: Int
)