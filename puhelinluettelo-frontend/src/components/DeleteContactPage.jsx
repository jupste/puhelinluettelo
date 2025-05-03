import React, { useEffect, useState } from 'react';

const DeleteContactPage = () => {
  const [contacts, setContacts] = useState([]);

  const fetchContacts = () => {
    fetch('http://localhost:9000/contacts') // Your endpoint for listing contacts
      .then(response => response.json())
      .then(data => setContacts(data))
      .catch(error => console.error("Virhe ladattaessa yhteystietoja:", error));
  };

  useEffect(() => {
    fetchContacts();
  }, []);

  const handleDelete = (id) => {
    fetch(`http://localhost:9000/contacts/${id}`, {
      method: 'DELETE',
    })
      .then((response) => {
        if (response.ok) {
          setContacts(contacts.filter(contact => contact.id !== id));
        } else {
          console.error("Poisto epäonnistui");
        }
      })
      .catch(error => console.error("Virhe poistettaessa:", error));
  };

  return (
    <div>
      <h2>Poista käyttäjiä</h2>
      {contacts.length === 0 ? (
        <p>Ei käyttäjiä</p>
      ) : (
        <ul>
          {contacts.map(contact => (
            <li key={contact.id}>
              {contact.first_name} {contact.last_name}
              <button onClick={() => handleDelete(contact.id)} style={{ marginLeft: '1rem' }}>
                Poista
              </button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default DeleteContactPage;
