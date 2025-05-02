# Use the official PostgreSQL image from the Docker Hub
FROM postgres:latest

# Set environment variables for the database
ENV POSTGRES_USER=myuser
ENV POSTGRES_PASSWORD=mypassword
ENV POSTGRES_DB=mydatabase

# Copy the SQL script to initialize the database schema
COPY init.sql schema.sql /docker-entrypoint-initdb.d/

# Expose the PostgreSQL port
EXPOSE 5432