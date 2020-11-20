package services

import dto.{FindDustDto, FindDustResponseDto}
import javax.inject.Inject
import play.api.libs.json._
import play.api.libs.ws.{WSClient, WSRequest}

import scala.concurrent.Await._
import scala.concurrent.duration._


class FineDustService @Inject() (ws: WSClient){

  implicit val findDustReads: Reads[FindDustDto] = Json.reads[FindDustDto]
  implicit val findDustResponseDtoReads: Reads[FindDustResponseDto] = Json.reads[FindDustResponseDto]

  def getFineDust(): FindDustDto = {
    println("getFineDust() method")
    requestOpenApi()
  }

  def requestOpenApi(): FindDustDto = {
    val request: WSRequest = ws.url("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=Ek7i9vK%2FukNMjmVykoansisObXnIpq62WjuAvuE2tcjrm9nI7a0uG21ifnbvHW2RalfeYH2x3tZtF8yyHR3igQ%3D%3D&numOfRows=10&pageNo=1&stationName=%EC%A2%85%EB%A1%9C%EA%B5%AC&dataTerm=DAILY&ver=1.3&_returnType=json")
    val response = result(request.get(), atMost = Duration.create(3, MINUTES)).json.validate[FindDustResponseDto]
    response.get.list(0)
  }
}
