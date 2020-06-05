package dev.beriashvili.practice.timer.models

object Time {
    object Hour {
        const val MIN = 0
        const val MAX = 24
    }

    object Minute {
        const val MIN = 0
        const val MAX = 60
    }

    object Second {
        const val MIN = 0
        const val MAX = 60
    }

    fun format(hour: Int, minute: Int, second: Int): String {
        var time = ""

        time += if (hour < 10) "0$hour:" else "$hour"
        time += if (minute < 10) "0$minute:" else "$minute"
        time += if (second < 10) "0$second" else "$second"

        return time
    }
}