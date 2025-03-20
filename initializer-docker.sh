#!/bin/bash

echo "Startando o Docker para configurar o ambiente..."
start "" "C:\Program Files\Docker\Docker\Docker Desktop.exe" > /dev/null 2>&1
sleep 2

echo "Construindo a imagem Docker..."
cd barbershopmanagement/barbershopmanagement/
docker build -t barbershop-management . > /dev/null 2>&1
sleep 2

echo "Subindo os containers com Docker Compose..."
docker compose up -d > /dev/null 2>&1

echo "Realizando os ultimos ajustes para a aplicação subir corretamente..."
sleep 30

echo "Aplicação já está no ar! Você pode testá-la através de localhost:8080/swagger-ui/index.html"