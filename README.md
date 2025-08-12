# Spring-Ai-Ollama-LLM-React
Spring AI + Ollama + Fine Tune Model + Embedding: mxbai-embed-large + LLM Chat Model: gemma3:latest + Gen AI + Spring Boot + React JS
Spring-Ai-Ollama-LLM-React
This project is a full-stack application that demonstrates the integration of a React frontend, a Spring Boot backend, and a locally running Ollama server to power a Large Language Model (LLM). This setup is ideal for local development, providing a secure and self-contained environment for building AI-powered applications without relying on external cloud services.
# UI Outlook
<img width="800" height="900" alt="image" src="https://github.com/user-attachments/assets/d8ff3def-a032-497d-a8b1-9d828513aedb" />

#  🚀 Features
React Frontend: A modern, responsive user interface for interacting with the LLM.

Spring Boot Backend: A RESTful API built with Java and Spring AI to serve as the bridge between the frontend and the LLM.

Ollama Integration: Uses Ollama to run open-source LLMs locally, ensuring privacy and full control over your data.

Local Development Setup: All components (frontend, backend, and LLM) are configured to run on your local machine.

#  ⚙️ Prerequisites
Before you begin, ensure you have the following installed:

Java 17+: The Spring Boot backend requires a recent version of the Java Development Kit.

Maven or Gradle: The build tool for the Spring Boot project.

Node.js and npm: Required for running the React frontend.

Ollama: The server for running LLMs locally. Download and install it from the official Ollama website.

#  📦 Local Setup
Follow these steps to get the project up and running on your local machine.

#  Step 1: Set up Ollama
Install Ollama: If you haven't already, install Ollama on your system.

macOS: brew install ollama

Linux: curl -fsSL https://ollama.com/install.sh | sh

Windows: Download the installer from the Ollama website.

Run the Ollama Server: Open a terminal and start the Ollama server.
ollama serve

Pull a Model: Pull a large language model (LLM) that you want to use. We'll use gemma3:latest as an example.
For embedding We'll use mxbai-embed-large:latest

**ollama pull gemma3:latest**

**ollama pull mxbai-embed-large:latest**

#  Step 2: Set up the Spring Boot Backend
Navigate to the backend directory: cd project/src

Configure Spring AI: In your application.properties or application.yml file, configure the Spring AI starter to point to your local Ollama instance.
Run the Spring Boot application: Use your build tool to run the application.
Maven: 

**mvn spring-boot:run**

The backend should now be running, typically on http://localhost:8080.

#  Step 3: Set up the React Frontend
Navigate to the frontend directory: cd project/frontend

Install dependencies: Install the necessary Node.js packages.

**npm install**

Start the React application:

**npm start**

The frontend will start and should be accessible in your browser at http://localhost:3000.
