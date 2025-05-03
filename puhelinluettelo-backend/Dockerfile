# Use an official SBT runtime as a parent image
FROM sbtscala/scala-sbt:eclipse-temurin-17.0.4_1.7.1_3.2.0


# Ensure SBT is in the PATH
ENV PATH=$PATH:/root/.local/share/coursier/bin

# Set the working directory in the container
WORKDIR /opt/docker

# Copy the application files to the container
COPY . .

# Expose the port the app runs on
EXPOSE 9000

# Command to run the application
CMD ["sbt", "run"]
