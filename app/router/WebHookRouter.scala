package router

import controllers.WebHookController
import javax.inject.Inject
import play.api.routing.{Router, SimpleRouter}
import play.api.routing.sird._

class WebHookRouter @Inject()(webHookController: WebHookController) extends SimpleRouter{


  override def withPrefix(prefix: String): Router = super.withPrefix(prefix)

  override def routes: Router.Routes = {
    case GET(p"/") =>
      webHookController.index
  }

}
