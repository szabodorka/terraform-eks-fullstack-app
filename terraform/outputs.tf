output "eks_cluster_name" {
    value = module.eks.cluster_name
}

output "rds_endpoint" {
    value = module.rds.rds_endpoint
}

output "kubeconfig_command" {
    value = "aws eks update-kubeconfig --name ${module.eks.cluster_name} --region ${var.region}"
}

output "ecr_url" {
    value = module.ecr.ecr_url
}