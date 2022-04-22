package ru.yurii.testingworkshopapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author y.anisimov
 */
@Parcelize
data class Project(
    val id: Long,
    val title: String,
    val order: Int
) : Parcelable
