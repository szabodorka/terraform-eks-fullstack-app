# EKS cluster
resource "aws_eks_cluster" "this" {
  name     = "ihaq-eks-cluster"
  version  = "1.33"
  role_arn = aws_iam_role.eks_cluster.arn

  bootstrap_self_managed_addons = true

  vpc_config {
    endpoint_private_access = false
    endpoint_public_access = true

    subnet_ids         = concat(var.private_subnet_ids, var.public_subnet_ids)
    security_group_ids = [aws_security_group.eks_cluster.id]
  }

  access_config {
    authentication_mode = "API"
    bootstrap_cluster_creator_admin_permissions = true
  }

  enabled_cluster_log_types = ["api", "audit", "authenticator", "controllerManager", "scheduler"]

  depends_on = [
    aws_iam_role_policy_attachment.eks_cluster_policy,
    aws_iam_role_policy_attachment.eks_service_policy
  ]
}

# Node Group
resource "aws_eks_node_group" "ihaq_nodes" {
  cluster_name    = aws_eks_cluster.this.name
  version         = aws_eks_cluster.this.version
  node_group_name = "ihaq-nodes"
  node_role_arn   = aws_iam_role.eks_node.arn
  subnet_ids      = var.private_subnet_ids

  scaling_config {
    desired_size = 1
    max_size     = 2
    min_size     = 1
  }

  capacity_type = "ON_DEMAND"
  instance_types = ["t3.medium"]

  labels = {
    "node-type" = "ihaq-nodes"
  }

  tags = {
    Name = "ihaq-nodes"
  }

  depends_on = [
    aws_iam_role_policy_attachment.eks_worker_policy,
    aws_iam_role_policy_attachment.eks_vpc_resource_controller,
    aws_iam_role_policy_attachment.ecr_read_policy,
    aws_iam_role_policy_attachment.eks_cni_policy
  ]

  lifecycle {
    ignore_changes = [ scaling_config[0].desired_size ]
  }
}

# Configure kubectl
resource "terraform_data" "configure_kubectl" {
  depends_on = [aws_eks_cluster.this]

  provisioner "local-exec" {
    command = "aws eks update-kubeconfig --region ${var.region} --name ${aws_eks_cluster.this.name} --profile default"
  }
}

data "tls_certificate" "cluster" {
  url = aws_eks_cluster.this.identity[0].oidc[0].issuer
}

resource "aws_iam_openid_connect_provider" "eks" {
  url             = aws_eks_cluster.this.identity[0].oidc[0].issuer
  client_id_list  = ["sts.amazonaws.com"]
  thumbprint_list = [data.tls_certificate.cluster.certificates[0].sha1_fingerprint]
}

data "aws_eks_cluster_auth" "this" {
  name = aws_eks_cluster.this.name
}