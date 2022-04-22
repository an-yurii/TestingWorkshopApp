package ru.yurii.testingworkshopapp.data

import ru.yurii.testingworkshopapp.R

object Palette {
    private val colors: Map<Int, Int> by lazy {
        mapOf(
            30 to R.color.berry_red,
            31 to R.color.red,
            32 to R.color.orange,
            33 to R.color.yellow,
            34 to R.color.olive_green,
            35 to R.color.lime_green,
            36 to R.color.green,
            37 to R.color.mint_green,
            38 to R.color.teal,
            39 to R.color.sky_blue,
            40 to R.color.light_blue,
            41 to R.color.blue,
            42 to R.color.grape,
            43 to R.color.violet,
            44 to R.color.lavender,
            45 to R.color.magenta,
            46 to R.color.salmon,
            47 to R.color.charcoal,
            48 to R.color.grey,
            49 to R.color.taupe,
        )
    }

    fun getColorByCode(code: Int): Int = colors[code] ?: R.color.black
    fun getDefaultColor(): Int = R.color.black

    fun getColorByPriority(priority: Int): Int {
        return when (priority) {
            4 -> R.color.orange
            3 -> R.color.olive_green
            2 -> R.color.light_blue
            else -> getDefaultColor()
        }
    }
}
