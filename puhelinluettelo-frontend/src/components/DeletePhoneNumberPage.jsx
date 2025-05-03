import React, { useEffect, useState } from 'react';

const DeletePhoneNumberPage = () => {
  const [phoneNumbers, setPhoneNumbers] = useState([]);

  const fetchPhoneNumbers = () => {
    fetch('http://localhost:9000/phonenumbers')
      .then(res => res.json())
      .then(setPhoneNumbers)
      .catch(err => console.error("Virhe haettaessa numeroita:", err));
  };

  useEffect(() => {
    fetchPhoneNumbers();
  }, []);

  const handleDelete = (userId, number) => {
    fetch(`http://localhost:9000/phonenumbers/${userId}/${encodeURIComponent(number)}`, {
      method: 'DELETE',
    })
      .then(res => {
        if (res.ok) {
          fetchPhoneNumbers(); // refresh after delete
        } else {
          console.error("Poisto epäonnistui");
        }
      })
      .catch(err => console.error("Virhe poistossa:", err));
  };

  return (
    <div>
      <h2>Poista puhelinnumeroita</h2>
      <ul>
        {phoneNumbers.map((pn, index) => (
          <li key={index}>
            {pn.countryCode} {pn.phonenumber} (User ID: {pn.userId})
            <button onClick={() => handleDelete(pn.userId, pn.phonenumber)} style={{ marginLeft: '1rem' }}>
              Poista
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default DeletePhoneNumberPage;
