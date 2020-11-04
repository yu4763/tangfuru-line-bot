package services

import com.google.inject.Inject
import play.api.libs.json.{JsObject, Json}
import play.api.libs.ws.{WSClient, WSRequest}

import scala.concurrent.Await.result
import scala.concurrent.duration.{Duration, MINUTES}

class LineMessageService @Inject()(ws: WSClient) {

  // https://developers.line.biz/en/reference/messaging-api/#send-reply-message
  def sendReply(message: String, replyToken: String): Unit = {
    println("sendReply() method. text: %s, replyToken: %s\n", message, replyToken)
    val requestBody = Json.obj(
      "replyToken" -> replyToken,
      "notificationDisabled" -> "false",
      "messages" -> List.fill(1)(Json.obj(
        "type" -> "text",
        "text" -> message
      ))
    )

    requestLineApi("https://api.line.me/v2/bot/message/reply", requestBody)
  }

  // https://developers.line.biz/en/reference/messaging-api/#send-broadcast-message
  def sendBroadcast(message: String): Unit = {
    printf("sendBroadcast() method. text: %s\n", message)
    val requestBody = Json.obj(
      "messages" -> List.fill(1)(Json.obj(
        "type" -> "text",
        "text" -> message
      ))
    )

    requestLineApi("https://api.line.me/v2/bot/message/broadcast", requestBody)
  }

  def requestLineApi(url: String, requestBody: JsObject): Unit = {
    val request: WSRequest = ws.url(url)
      .addHttpHeaders("Content-Type" -> "application/json",
        "Authorization" -> "Bearer yyAlkOACvPyWiUQ7pPExYnP/sIv3s8zrrE0BdcO7k7D+5uvVRteiGDwH4m4gGJkTjDznSUlY1Ex/CeMpG7Cmr8xTn/7Qj+tujIfF93XDPHyWyUd5Yi1UAYBTo/a4XjFmXp5ov3m6qLMHxUFZT50LBwdB04t89/1O/w1cDnyilFU=")

    val response = result(request.post(requestBody), atMost = Duration.create(3, MINUTES))

    val requestMessage = requestBody.value.get("messages")
    if (response.status == 200 && response.body == "{}") {
      printf("Success to send LINE msg. test: %s\n", requestMessage)
    } else {
      printf("fail to send LINE msg. test: %s\n", requestMessage)
    }
  }
}
