package models
import play.api.libs.json._
case class Contact(id: Long, first_name: String, last_name: String)
case class CreateContact(firstName: String, lastName: String)

object Contact {
  implicit val contactFormat: Format[Contact] = Json.format[Contact]
}

object CreateContact {
  implicit val createContactFormat: Format[CreateContact] = Json.format[CreateContact]
}