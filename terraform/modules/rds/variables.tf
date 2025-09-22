variable "security_group_id" {
  type = string
}

variable "vpc_id" {
  type = string
}

variable "private_subnet_ids" {
  type = list(string)
}

variable "db_username" {
    type = string
    default = "dbuser"
    sensitive = true
}

variable "db_password" {
    type = string
    default = "supersecret"
    sensitive = true
}

variable "cluster_security_group_id" {
    type = string
}