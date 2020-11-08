package controllers

import dto.Response
import javax.inject._
import play.api.libs.json.Json
import play.api.mvc._
import services.{FineDustService, LineMessageService}


@Singleton
class WebHookController @Inject()(cc: ControllerComponents,
                               findDustService :FineDustService,
                               lineMessageService: LineMessageService) extends AbstractController(cc) {

  implicit val responseFormat = Json.format[Response]

  def index = Action {
    findDustService.getFineDust()
    lineMessageService.sendBroadcast("Hello world")
    val response = new Response(200, "hello world")
    Ok(Json.toJson(response))
  }

}
