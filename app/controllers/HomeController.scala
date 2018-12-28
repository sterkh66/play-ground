package controllers

import java.time.LocalDate

import anorm._
import anorm.{ Macro, RowParser }

import javax.inject._
import play.api.mvc._
import play.api.db._

import models.User

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(db: Database, cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
  
  def explore() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.explore())
  }
  
  def tutorial() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.tutorial())
  }

  def hello(name: String, age: Int) = Action { implicit request: Request[AnyContent] =>
    val date = LocalDate.now().toString

    db.withConnection { implicit c =>
      val parser: RowParser[User] = Macro.namedParser[User]
      val users: List[User] = SQL("select id, username from auth_user").as(parser.*)
      println(users)
      Ok(views.html.hello(name, age, date, users))
    }
  }
}

