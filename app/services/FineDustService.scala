package services

import dto.FindDustDto
import javax.inject.Inject
import play.api.libs.json._
import play.api.libs.ws.{WSClient, WSRequest}

import scala.concurrent.Await._
import scala.concurrent.duration._


class FineDustService @Inject() (ws: WSClient){

  implicit val findDustReads: Reads[FindDustDto] = Json.reads[FindDustDto]

  def getFineDust(): Unit = {
    println("getFineDust() method")
//    requestOpenApi()
  }

  def requestOpenApi(): FindDustDto = {
    val serviceKey = "m7W1fHtvWMhzK1RpVT6U17j83Uu55aVoUGlS4dCCsKG2u92hTT1PlqYO0HJgw" + "%" + "2B49hHWGiAoOh6VePKcOLTRMFQ" + "%" + "3D" + "%" + "3D";
    val request: WSRequest = ws.url("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty")
      .addQueryStringParameters("_returnType" -> "json",
        "serviceKey" -> serviceKey,
        "numOfRows" -> "1",
        "stationName" -> "종로구",
        "dateTerm" -> "DAILY",
        "ver" -> "1.3",
        "pageNo" -> "1")
    val response = result(request.get(), atMost = Duration.create(3, MINUTES)).json.validate[FindDustDto]
    response.get
  }
}
