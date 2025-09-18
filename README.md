# Terraform EKS Fullstack App

## Overview

This project is a **full-stack Java Spring Boot + React application** with a PostgreSQL database.  
It is fully containerized using Docker and orchestrated locally with Docker Compose.

## Architecture

- **Frontend**: React, built with Node, served by Nginx (with reverse proxy for `/api` requests).
- **Backend**: Java Spring Boot, containerized with a multi-stage Maven build.
- **Database**: PostgreSQL, initialized with a startup SQL script.
- **Local Orchestration**: Docker Compose (for local dev and testing).
- **Production Deployment**: Kubernetes manifests + Terraform on AWS (in progress).

## Repository Structure

.
├── backend/ # Spring Boot app (pom.xml inside)
│ └── Dockerfile
├── frontend/ # React app + Nginx config
│ ├── Dockerfile
│ └── nginx.conf
├── database/ # PostgreSQL init scripts
│ ├── Dockerfile
│ └── startup.sql
├── docker-compose.yml # Local dev orchestration
├── .env.example # Example environment variables
└── README.md

## Getting Started

### Prerequisites

- Docker & Docker Compose installed
- Java 17 (for local builds)
- Node.js + npm (for local frontend development)

### Environment Variables

Copy the .env.example file and edit as needed.

Run locally:

```bash
docker compose up --build
```

Frontend: http://localhost

Backend API: http://localhost:8080 or proxied via http://localhost/api

Stop and clean up:

```bash
# Stop containers + remove networks
docker compose down

# Stop and remove everything (including volumes and images)
docker compose down -v --rmi all
```

## Next Steps

- Add Terraform modules for AWS (VPC, RDS, EKS, ECR, etc.)
- Add Kubernetes manifests for deployment
- Configure GitHub Actions CI/CD pipeline
- Add monitoring with Prometheus/Grafana
