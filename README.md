<a id="readme-top"></a>

<div align="center">
  <img src="./frontend/images/logo.PNG" alt="Project Logo" width="275" height="200">
  <br>
  <br>
  <h1>Terraform EKS Fullstack App – IHaQ (I Have a Question)</h1>
  <p align="center">
    A StackOverflow-like fullstack web application deployed on AWS EKS with Terraform infrastructure automation.
    <br />
    <a href="#about-the-project"><strong>Explore the documentation »</strong></a>
    <br />
    <br />
    <a href="#architecture-overview">View Architecture</a>
    ·
    <a href="#getting-started">Setup Guide</a>
    ·
    <a href="https://github.com/szabodorka/terraform-eks-fullstack-app/issues">Report Issue</a>
  </p>
</div>

---

## Table of Contents

1. [About the Project](#about-the-project)
2. [Architecture Overview](#architecture-overview)
3. [Tech Stack](#tech-stack)
4. [Getting Started](#getting-started)
5. [Future Plans](#future-plans)

## About the Project

**IHaQ – “I Have a Question”** is a **StackOverflow-like** platform where users can:

- Post questions
- Answer other users’ questions
- Earn points for contributions

This application was originally developed as a team project, the backend and frontend source code (including all commits) come from that earlier collaborative repository.

In this current project, **my focus was entirely on the DevOps and cloud infrastructure side**:

- **Dockerized** the existing Java Spring Boot backend and React frontend
- Created a **Docker Compose** setup for local development
- Designed and implemented a **Terraform-based AWS infrastructure** including
  - VPC, Subnets, NAT Gateway
  - EKS Cluster and Node Groups
  - RDS PostgreSQL instance
  - IAM roles and OIDC setup
  - AWS Load Balancer Controller
  - ECR repository with automated build & push of frontend and backend images
- Deployed workloads via **Kubernetes manifests** on EKS

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Architecture Overview

The project runs on **AWS Cloud**:

- **EKS (Elastic Kubernetes Service)** handles container orchestration
- **RDS (Relational Database Service)** hosts the PostgreSQL database
- **ALB (Application Load Balancer)** routes frontend and backend traffic
- **ECR (Elastic Container Registry)** stores built Docker images

Infrastructure is **fully provisioned with Terraform**, and the app is deployed through **Kubernetes manifests** for backend, frontend, database migration, and ingress routing.

<!-- _(Network diagram could be inserted here.)_ -->

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Tech Stack

| Layer                                | Technologies                                                                                                                                                                                                                                                                                |
| ------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Frontend**                         | [![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)](https://react.dev)[![Nginx](https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white)](https://nginx.org)                                             |
| **Backend**                          | [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot) [![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com)  |
| **Database**                         | [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org) [![AWS RDS](https://img.shields.io/badge/AWS%20RDS-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white)](https://aws.amazon.com/rds/) |
| **Infrastructure**                   | ![Terraform](https://img.shields.io/badge/Terraform-844FBA?style=for-the-badge&logo=terraform&logoColor=white) [![AWS](https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white)](https://aws.amazon.com)                                                 |
| **Containerization & Orchestration** | [![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com) [![Kubernetes](https://img.shields.io/badge/Kubernetes-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white)](https://kubernetes.io)                  |

## Getting Started

### 1. Fork the project and clone the repository

```bash
git clone https://github.com/<your-github-username>/<your-repo-name>.git
```

### 2. Provision infrastructure with Terraform

```bash
cd terraform
terraform init
terraform apply
```

This will:

- Create a VPC with public/private subnets
- Deploy an EKS cluster
- Deploy an RDS PostgreSQL database
- Set up IAM roles and the AWS Load Balancer Controller

⚠️ Prerequisites:

- AWS CLI configured with credentials
- Terraform installed
- kubectl + helm installed

### 3. Deploy Application to Kubernetes

Insert RDS Endpoint from output into backend.yaml and db-init-job-yaml files.

Apply the Kubernetes manifests:

```bash
kubectl apply -f k8s/backend.yaml
kubectl apply -f k8s/frontend-deployment.yaml
kubectl apply -f k8s/ingress.yaml
```

### 4. Run Database Migration

Upload and apply the initial SQL script:

```bash
kubectl create configmap ihaq-migrations --from-file=startup.sql
kubectl apply -f k8s/db-init-job.yaml
```

### 5. Access the Application

Once the ALB is created, check the ingress:

```bash
kubectl get ingress ihaq-ingress -o wide
```

Open the given ALB URL in your browser

### 6. Cleanup

- Remove all Kubernetes objects:

```bash
kubectl delete -f k8s
kubectl get ingress # must be empty
```

Monitor on AWS EC2 Console if Load Balancer, Target Group and Network Interface is removed

- Destroy Terraform resources:

```bash
terraform destroy
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Future plans

- Add CI/CD pipeline (GitHub Actions or GitLab CI)
- Implement TLS certificates for HTTPS
- Add autoscaling for backend and frontend
- Add monitoring with Prometheus & Grafana
