package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import repositories.ContactRepository
import models._
import scala.concurrent.{ExecutionContext, Future}
import play.api.mvc.PlayBodyParsers

@Singleton
class ContactController @Inject()(cc: ControllerComponents, contactRepo: ContactRepository)(implicit ec: ExecutionContext) 
  extends AbstractController(cc) {

  // Implicit JSON formats for the case classes
  implicit val countryFormat: OFormat[Country] = Json.format[Country]
  implicit val phoneNumberFormat: OFormat[PhoneNumber] = Json.format[PhoneNumber]
  implicit val phoneNumberWithCountryFormat: OFormat[PhoneNumberWithCountry] = Json.format[PhoneNumberWithCountry]
  implicit val contactWithPhoneNumberFormat: OFormat[ContactWithPhoneNumber] = Json.format[ContactWithPhoneNumber]

  // Endpoint to list contacts with their phone numbers and country info
  def listContactsWithPhoneNumbers = Action.async {
    contactRepo.listContactsWithPhoneNumbers().map { contacts =>
      Ok(Json.toJson(contacts))  // Convert the result to JSON
    }
  }
  def createContact: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Contact].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> "Invalid contact data", "errors" -> errors.mkString))),
      contact => contactRepo.insert(contact).map { _ =>
        Created(Json.toJson(contact))
      }
    )
  }

  def deleteContact(id: Long): Action[AnyContent] = Action.async {
    contactRepo.delete(id).map { _ =>
      Ok(Json.obj("message" -> s"Contact with id $id deleted"))
    }
  }
}
