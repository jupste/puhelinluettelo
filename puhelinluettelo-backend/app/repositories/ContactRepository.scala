package repositories
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.PlayBodyParsers
import slick.jdbc.JdbcProfile
import models._
import scala.concurrent.{Future, ExecutionContext}

class ContactRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private val contacts = TableQuery[ContactTable]
  private val phoneNumbers = TableQuery[PhoneNumberTable]
  private val countries = TableQuery[CountryTable]

  // List all contacts with their phone numbers and country codes
  def listContactsWithPhoneNumbers(): Future[Seq[(Contact, Seq[(PhoneNumber, Country)])]] = {
    val query = for {
      (contact, phoneNumber) <- contacts joinLeft phoneNumbers on (_.id === phoneNumber.userId)
      country <- countries if phoneNumber.map(_.countryCode).contains(country.id)
    } yield (contact, phoneNumber, country)

    db.run(query.result).map { result =>
      result.groupBy(_._1).map {
        case (contact, contactWithPhones) =>
          (contact, contactWithPhones.flatMap {
            case (phoneNumber, country) =>
              phoneNumber.map(pn => (pn, country))
          })
      }.toSeq
    }
  }

  def insert(contact: Contact): Future[Unit] = {
    val query = contacts += contact
    db.run(query).map(_ => ())
  
  def delete(id: Long): Future[Unit] = {
    db.run(contacts.filter(_.id === id).delete).map(_ => ())
  }
}
}
