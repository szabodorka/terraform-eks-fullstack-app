# Terraform EKS Fullstack App

IHaQ - I Have a Question is a **StackOverflow-like web application** where users can:

- Post questions
- Answer other users' questions
- Earn points for contributions

The project is built with:

- **Java + Spring Boot** backend
- **React + Nginx** frontend
- **PostgreSQL** database

## Architecture Overview

The application is deployed on **AWS** using:

- **EKS (Elastic Kubernetes Service)** for container orchestration
- **RDS (Relational Database Service)** for PostgreSQL
- **ALB (Application Load Balancer)** managed by the AWS Load Balancer Controller

Infrastructure provisioning is automated with **Terraform**, and workloads are deployed using **Kubernetes manifests**.

## Tech Stack

- **Backend**: Java, Spring Boot
- **Frontend**: React, Nginx
- **Database**: PostgreSQL (AWS RDS)
- **Infrastructure**: Terraform, AWS (EKS, RDS, ALB, IAM, VPC)
- **Kubernetes**: Deployments, Services, ConfigMaps, Secrets, Ingress

## Getting Started

### 1. Clone the repository

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

**Identical with the following commands:
kubectl delete -f ingress.yaml
kubectl delete -f frontend-deployment.yaml
kubectl delete -f backend-deployment.yaml
kubectl delete -f db-init-job.yaml
kubectl delete configmap ihaq-config
kubectl delete secret ihaq-secret
kubectl delete configmap ihaq-migrations**

Monitor on EC2 Console if Load Balancer, Target Group and Network Interface is removed

- Destroy Terraform resources:

```bash
terraform destroy
```

## Configuration

- Database connection is configured via:
  - ConfigMap (for URL, driver class)
  - Secret (for username/password)
- Backend health checks: /actuator/health
- Frontend served via Nginx at /
- API exposed under /api

## Future plans

- Add CI/CD pipeline (GitHub Actions or GitLab CI)
- Implement TLS certificates for HTTPS
- Add autoscaling for backend and frontend
- Add monitoring with Prometheus & Grafana
