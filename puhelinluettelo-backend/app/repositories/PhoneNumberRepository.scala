package repositories

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import models._

import scala.concurrent.{Future, ExecutionContext}

class PhoneNumberRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private val phoneNumbers = TableQuery[PhoneNumberTable]
  def list(): Future[Seq[PhoneNumber]] = db.run(phoneNumbers.result)
  
  def insert(phoneNumber: PhoneNumber): Future[Unit] = db.run(phoneNumbers += phoneNumber).map(_ => ())

  def delete(phonenumber: String): Future[Unit] = db.run(phoneNumbers.filter(_.phonenumber === phonenumber).delete).map(_ => ())
}
