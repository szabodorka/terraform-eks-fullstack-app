locals {
  repo   = var.ecr_url
  db_url = "jdbc:postgresql://${var.rds_endpoint}/${var.db_name}"
}

resource "kubernetes_config_map" "ihaq_migrations" {
  metadata {
    name      = "ihaq-migrations"
  }
  data = {
    "startup.sql" = file(var.db_init_script_path)
  }
}

resource "kubectl_manifest" "backend" {
  yaml_body = templatefile("${path.module}/k8s/backend.yaml.tmpl", {
    repo    = local.repo
    db_url  = local.db_url
    db_name = var.db_name
  })

  depends_on = [
    aws_eks_node_group.ihaq_nodes,
    kubernetes_config_map.ihaq_migrations,
  ]
}


resource "kubectl_manifest" "frontend" {
  yaml_body = templatefile("${path.module}/k8s/frontend-deployment.yaml.tmpl", {
    repo = local.repo
  })

  depends_on = [
    aws_eks_node_group.ihaq_nodes,
  ]
}

resource "kubectl_manifest" "ingress" {
  yaml_body = templatefile("${path.module}/k8s/ingress.yaml.tmpl", {
  })

  depends_on = [
    kubectl_manifest.backend,
    kubectl_manifest.frontend,
    helm_release.lb_controller
  ]
}

resource "kubectl_manifest" "db_init_job" {
  yaml_body = templatefile("${path.module}/k8s/db-init-job.yaml.tmpl", {
    db_host = var.rds_endpoint
  })

  depends_on = [
    kubernetes_config_map.ihaq_migrations,
    kubectl_manifest.backend
  ]
}