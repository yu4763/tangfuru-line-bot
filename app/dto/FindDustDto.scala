package dto

case class FindDustDto (pm10Value24: String, pm25Value24: String, pm10Grade: String, pm25Grade: String) {
  override def toString: String = {
    val stringBuilder = StringBuilder.newBuilder

    val pm10GradeString = convertGradeToString(pm10Grade.toInt)
    val pm25GradeString = convertGradeToString(pm25Grade.toInt)

    stringBuilder.append(s"미세먼지: $pm10Value24㎛ $pm10GradeString ")
    stringBuilder.append(s"초미세먼지: $pm25Value24㎍ $pm25GradeString")

    stringBuilder.toString()
  }

  def convertGradeToString(grade: Int): String = {
    if(grade == 1) {
      "좋음"
    } else if(grade == 2) {
      "보통"
    } else if(grade == 3) {
      "약간 나쁨"
    } else {
      "나쁨"
    }
  }
}
