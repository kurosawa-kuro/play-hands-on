package controllers

//コピペ
import javax.inject._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._
import services._
//コピペ

//入力
class TodoController @Inject()(todoService: TodoService, mcc: MessagesControllerComponents) extends MessagesAbstractController(mcc) {

  def helloworld() = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok("Hello World")
  }

//  def list() = Action { implicit request: MessagesRequest[AnyContent] =>
//    val message: String = "ここにリストを表示"
//    Ok(views.html.list(message))
//  }

//  def list() = Action { implicit request: MessagesRequest[AnyContent] =>
//    val items: Seq[Todo] = Seq(Todo("Todo1"), Todo("Todo2"))
//    Ok(views.html.list(items))
//  }

  def list() = Action { implicit request: MessagesRequest[AnyContent] =>
    val items: Seq[Todo] = todoService.list()
    Ok(views.html.list(items))
  }

  val todoForm: Form[String] = Form("name" -> nonEmptyText)

  def todoNew = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.createForm(todoForm))
  }

  def todoAdd() = Action { implicit request: MessagesRequest[AnyContent] =>
    val name: String = todoForm.bindFromRequest().get
    todoService.insert(Todo(name))
    Redirect(routes.TodoController.list())
  }
}
//入力