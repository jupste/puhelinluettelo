package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models._
import repositories.PhoneNumberRepository

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PhoneNumberController @Inject()(val controllerComponents: ControllerComponents, phoneNumberRepo: PhoneNumberRepository)(implicit ec: ExecutionContext) extends BaseController {

  implicit val phoneNumberFormat: Format[PhoneNumber] = Json.format[PhoneNumber]

  def createPhoneNumber: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[PhoneNumber].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> "Invalid phone number data"))),
      phoneNumber => phoneNumberRepo.insert(phoneNumber).map(_ => Created(Json.toJson(phoneNumber)))
    )
  }

  def deletePhoneNumber(phonenumber: String): Action[AnyContent] = Action.async {
    phoneNumberRepo.delete(phonenumber).map(_ => Ok(Json.obj("message" -> s"Phone number $phonenumber deleted")))
  }
}
