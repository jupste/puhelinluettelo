import React, { useEffect, useState } from 'react';

function AddPhoneNumberForm() {
  const [countries, setCountries] = useState([]);
  const [phoneNumber, setPhoneNumber] = useState('');
  const [contacts, setContacts] = useState([]);
  const [selectedContactId, setSelectedContactId] = useState('');
  const [selectedCountryCode, setSelectedCountryCode] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
  
    const data = {
      userId: parseInt(selectedContactId),
      countryCode: selectedCountryCode,
      phonenumber: phoneNumber
    };
    try {
      const response = await fetch('http://localhost:9000/phonenumbers', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
      });
  
      if (response.ok) {
        console.log("Phone number added successfully");
        // Optionally reset form or show a success message
      } else {
        const errorData = await response.json();
        console.error("Error adding phone number:", errorData);
      }
    } catch (error) {
      console.error("Virhe yhteydessä palvelimeen:", error);
    }
  };


  useEffect(() => {
    fetch('http://localhost:9000/countries')
      .then((res) => res.json())
      .then((data) => setCountries(data))
      .catch((err) => console.error("Failed to fetch countries:", err));
  }, []);
  

  useEffect(() => {
    fetch('http://localhost:9000/contacts')
      .then(res => res.json())
      .then(data => {
        const formatted = data.map(contact => ({
          id: contact.id,
          label: `${contact.first_name} ${contact.last_name}`
        }));
        setContacts(formatted);
      })
      .catch(err => {
        console.error("Error fetching contacts:", err);
      });
  }, []);


  return (
    <form onSubmit={handleSubmit}>
    <label>Valitse henkilö:</label>
      <select value={selectedContactId} onChange={e => setSelectedContactId(e.target.value)} required>
        {contacts.map(contact => (
          <option key={contact.id} value={contact.id}>
            {contact.label}
          </option>
        ))}
      </select>
      <br />
      <label>Valitse maakoodi:</label>
      <select value={selectedCountryCode} onChange={e => setSelectedCountryCode(e.target.value)} required>
        {countries.map(([code, name]) => (
          <option key={code} value={code}>
            {code} {name}
          </option>
        ))}
      </select>
      <br />
      <label>
        Puhelinnumero:
        <input
          type="text"
          value={phoneNumber}
          onChange={e => setPhoneNumber(e.target.value)}
          required
        />
      </label>
      <button type="submit">Lisää</button>
    </form>
  );
}

export default AddPhoneNumberForm;
