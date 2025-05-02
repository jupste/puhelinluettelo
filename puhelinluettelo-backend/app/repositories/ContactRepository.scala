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

  // List all contacts with their phone numbers and country codes{
  def listContactsWithPhoneNumbers(): Future[Seq[(Contact, Seq[PhoneNumber])]] = {
    val query = for {
      (contact, phoneNumberOpt) <- contacts joinLeft phoneNumbers on (_.id === _.userId)
    } yield (contact, phoneNumberOpt)

    db.run(query.result).map { result =>
      result.groupBy(_._1).map {
        case (contact, groupedRows) =>
          val phoneNumbers = groupedRows.flatMap(_._2)
          (contact, phoneNumbers)
      }.toSeq
    }
  }

  def listContacts(): Future[Seq[Contact]] = {
    db.run(contacts.result)
  }
  
  def insert(contact: Contact): Future[Unit] = {
    val query = contacts += contact
    db.run(query).map(_ => ())
  }
  
  def delete(id: Long): Future[Unit] = {
    db.run(contacts.filter(_.id === id).delete).map(_ => ())
  }
}
