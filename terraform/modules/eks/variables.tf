variable "vpc_id" {
    type = string
}

variable "private_subnet_ids" {
  type = list(string)
}

variable "public_subnet_ids" {
  type = list(string)
}

variable "cluster_oidc_issuer" {
    type = string
}

variable "cluster_name" {
    type = string
}

variable "openid_connect_provider" {
    type = string
}