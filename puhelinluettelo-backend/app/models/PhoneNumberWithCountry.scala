package models
import models._
case class PhoneNumberWithCountry(phoneNumber: PhoneNumber, country: Country)
case class ContactWithPhoneNumber(contact: Contact, phoneNumbers: Seq[PhoneNumberWithCountry])
