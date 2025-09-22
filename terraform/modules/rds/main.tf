resource "aws_db_instance" "ihaq_db" {
  allocated_storage    = 20
  storage_type         = "gp3"
  engine               = "postgres"
  engine_version       = "16"
  instance_class       = "db.t3.micro"
  db_name              = "ihaq"
  username             = var.db_username
  password             = var.db_password
  parameter_group_name = "default.postgres16"
  publicly_accessible  = false
  vpc_security_group_ids = [aws_security_group.db.id]
  db_subnet_group_name = aws_db_subnet_group.ihaq.name
  skip_final_snapshot  = true
  tags = {
    Name = "ihaq-db"
  }
}

resource "aws_db_subnet_group" "ihaq" {
  name       = "ihaq-db-subnet-group"
  subnet_ids = var.private_subnet_ids
}

resource "aws_security_group" "db" {
  vpc_id = var.vpc_id
  name   = "ihaq-rds-sg"

    egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_security_group_rule" "db_ingress_from_nodes" {
  type                     = "ingress"
  from_port                = 5432
  to_port                  = 5432
  protocol                 = "tcp"
  security_group_id        = aws_security_group.db.id
  source_security_group_id = var.security_group_id
}

resource "aws_security_group_rule" "db_ingress_from_eks" {
  type              = "ingress"
  from_port         = 5432
  to_port           = 5432
  protocol          = "tcp"
  security_group_id = aws_security_group.db.id
  source_security_group_id = var.cluster_security_group_id
}