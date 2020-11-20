package controllers

import dto.{FindDustDto, LineResponse}
import javax.inject._
import play.api.libs.json.Json
import play.api.mvc._
import services.{FineDustService, LineMessageService}


@Singleton
class WebHookController @Inject()(cc: ControllerComponents,
                               findDustService :FineDustService,
                               lineMessageService: LineMessageService) extends AbstractController(cc) {

  implicit val responseFormat = Json.format[LineResponse]

  def index = Action {
    val nowFindDust = findDustService.getFineDust()
    println(nowFindDust)
    val response = makeLineMessageResponse(nowFindDust)
    Ok(Json.toJson(response))
  }

  def makeLineMessageResponse(findDust : FindDustDto): LineResponse = {
    val lineMessage = StringBuilder.newBuilder
    lineMessage.append(findDust.toString + "\n")
    LineResponse(200, lineMessage.toString())
  }
}
