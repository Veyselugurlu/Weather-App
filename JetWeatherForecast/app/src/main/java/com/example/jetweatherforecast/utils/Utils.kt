import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(timestamp: Int): String {
    val sdf = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
    val date = Date(timestamp.toLong() * 1000)
    return sdf.format(date)
}

fun formatDateTime(timestamp: Int): String {
    val sdf = SimpleDateFormat("hh:mm a")
    val date = Date(timestamp.toLong() * 1000)
    return sdf.format(date)
}

fun formatDecimals(item:Double):String{
    return "%.0f".format(item)
}
