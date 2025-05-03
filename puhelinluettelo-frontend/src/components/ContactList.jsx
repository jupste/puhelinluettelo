import React, { useEffect, useState } from 'react';

const ContactList = () => {
  const [contacts, setContacts] = useState([]);

  useEffect(() => {
    fetch('http://localhost:9000/contacts/list') // Replace with your actual endpoint
      .then(response => response.json())
      .then(data => setContacts(data))
      .catch(error => console.error("Virhe ladattaessa yhteystietoja:", error));
  }, []);

  return (
    <div>
      <h2>Puhelinluettelo</h2>
      {contacts.map(([contact, numbers]) => (
        <div key={contact.id} style={{ marginBottom: '1rem', borderBottom: '1px solid #ccc', paddingBottom: '0.5rem' }}>
          <strong>{contact.first_name} {contact.last_name}</strong>
          {numbers.length > 0 ? (
            <ul>
              {numbers.map((number, index) => (
                <li key={index}>
                  {number.countryCode} {number.phonenumber}
                </li>
              ))}
            </ul>
          ) : (
            <p>Ei puhelinnumeroita</p>
          )}
        </div>
      ))}
    </div>
  );
};

export default ContactList;