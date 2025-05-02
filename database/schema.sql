CREATE TABLE IF NOT EXISTS country_code (
    country_code VARCHAR(10) PRIMARY KEY,
    country_name VARCHAR(100) NOT NULL
);

-- Create the personal_data table
CREATE TABLE IF NOT EXISTS personal_data (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL
);

-- Create the phonenumber table
CREATE TABLE IF NOT EXISTS phonenumber (
    phonenumber VARCHAR(20) PRIMARY KEY,
    userid INTEGER REFERENCES personal_data(id) ON DELETE CASCADE,
    country_code VARCHAR(10) REFERENCES country_code(country_code)
);