module "vpc" {
    source = "./modules/vpc"
}

module "eks" {
  source                  = "./modules/eks"
  vpc_id                  = module.vpc.vpc_id
  private_subnet_ids      = module.vpc.private_subnet_ids
  public_subnet_ids       = module.vpc.public_subnet_ids
  cluster_oidc_issuer     = module.eks.cluster_oidc_issuer
  cluster_name            = module.eks.cluster_name
  openid_connect_provider = module.eks.openid_connect_provider_arn
}

module "rds" {
  source                    = "./modules/rds"
  vpc_id                    = module.vpc.vpc_id
  private_subnet_ids        = module.vpc.db_subnet_ids
  security_group_id         = module.eks.node_security_group_id
  cluster_security_group_id = module.eks.cluster_security_group_id
}

module "ecr" {
  source = "./modules/ecr"
  region = var.region
}


