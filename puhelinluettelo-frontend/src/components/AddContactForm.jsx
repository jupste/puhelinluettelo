import React, { useState } from 'react';

export default function AddContactForm() {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [message, setMessage] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    const contact = {
      firstName,
      lastName
    };

    try {
      const response = await fetch('http://localhost:9000/contacts', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(contact)
      });

      if (response.ok) {
        setMessage('Henkilötieto lisätty!');
        setFirstName('');
        setLastName('');
      } else {
        const err = await response.json();
        setMessage('Virhe: ' + JSON.stringify(err));
      }
    } catch (err) {
      setMessage('Virhe yhteydessä palvelimeen: ' + err.message);
    }
  };

  return (
    <div>
      <h2>Lisää henkilötieto</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Etunimi:</label>
          <input
            type="text"
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Sukunimi:</label>
          <input
            type="text"
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
            required
          />
        </div>
        <button type="submit">Lisää</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
}
