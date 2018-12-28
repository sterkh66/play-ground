package controllers

import anorm.{Macro, RowParser, _}
import javax.inject._
import models.User
import play.api.db._
import play.api.mvc._
import play.api.libs.json.Json

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's users JSON API.
 */
@Singleton
class UsersController @Inject()(db: Database, cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  implicit val userFormat = Json.format[User]

  def get(id: Int) = Action {
    db.withConnection { implicit c =>
      val user: User = getUser(id)
      Ok(Json.toJson(user))
    }
  }

  def getAll = Action {
    db.withConnection { implicit c =>
      val users: List[User] = getUsers
      Ok(Json.toJson(users))
    }
  }

  def save = Action(parse.json) { request =>
    val user = request.body.as[User]
    saveUser(user)
    Created("User was created")
  }

  def getUsers = {
    db.withConnection { implicit c =>
      val parser: RowParser[User] = Macro.namedParser[User]
      val users: List[User] = SQL("select id, username from auth_user")
        .as(parser.*)
      println(users)
      users
    }
  }

  def getUser(id: Int) = {
    db.withConnection { implicit c =>
      val parser: RowParser[User] = Macro.namedParser[User]
      val users: List[User] = SQL("select id, username from auth_user where id = {id}")
        .on("id" -> id)
        .as(parser.*)
      println(users)
      users.head
    }
  }

  def saveUser(user: User) = {
    db.withConnection { implicit c =>
      val parser: RowParser[User] = Macro.namedParser[User]
      val users: List[User] = SQL("insert into auth_user(id, username) values({id}, {username}")
        .on("id" -> user.id, "username" -> user.username)
        .as(parser.*)
      println(users)
    }
  }
}

