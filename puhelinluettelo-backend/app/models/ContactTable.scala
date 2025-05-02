package models

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class ContactTable(tag: Tag) extends Table[Contact](tag, "personal_data") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def firstName = column[String]("first_name")
  def lastName = column[String]("last_name")

  def * = (id, firstName, lastName) <> (Contact.tupled, Contact.unapply)
}
