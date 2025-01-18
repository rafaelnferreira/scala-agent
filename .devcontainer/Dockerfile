FROM openjdk:21-jdk-slim

# Install Scala
RUN apt-get update && \
    apt-get install -y curl && \
    curl -fL https://github.com/coursier/coursier/releases/latest/download/cs-x86_64-pc-linux.gz | gzip -d > cs && chmod +x cs && ./cs setup --yes \
    apt-get clean
