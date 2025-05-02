import React, { useState } from 'react';
import AddContactForm from './components/AddContactForm'; 
import AddPhoneNumberForm from './components/AddPhoneNumberForm';
import ContactList from './components/ContactList';
import DeleteContactPage from './components/DeleteContactPage';
import DeletePhoneNumberPage from './components/DeletePhoneNumberPage';
function App() {
  const [activeTab, setActiveTab] = useState('contact');

  const renderTab = () => {
    switch (activeTab) {
      case 'contact':
        return <div><AddContactForm /></div>;
      case 'phone':
        return <div><AddPhoneNumberForm /></div>;
      case 'list':
        return <div><ContactList /></div>;
      case 'phone-delete':
        return <div><DeletePhoneNumberPage /></div>;
      case 'contact-delete':
        return <div><DeleteContactPage /></div>;
      default:
        return null;
    }
  };

  return (
    <div>
      <nav>
        <button onClick={() => setActiveTab('contact')}>Lisää henkilötieto</button>
        <button onClick={() => setActiveTab('phone')}>Lisää puhelinnumero</button>
        <button onClick={() => setActiveTab('list')}>Listaa puhelinnumerot ja henkilötiedot</button>
        <button onClick={() => setActiveTab('phone-delete')}>Poista puhelinnumeroita</button>
        <button onClick={() => setActiveTab('contact-delete')}>Poista kontakteja ja niihin liittyviä puhelinnueroita</button>
      </nav>
      <hr />
      {renderTab()}
    </div>
  );
}

export default App;
