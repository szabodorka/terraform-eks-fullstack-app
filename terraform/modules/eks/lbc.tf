data "aws_caller_identity" "current" {}

resource "aws_iam_role" "lb_controller" {
  name = "ihaq-lb-controller-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Principal = {
          Federated = var.openid_connect_provider
        }
        Action = "sts:AssumeRoleWithWebIdentity"
        Condition = {
          StringEquals = {
            "${replace(var.cluster_oidc_issuer, "https://", "")}:aud" = "sts.amazonaws.com"
            "${replace(var.cluster_oidc_issuer, "https://", "")}:sub" = "system:serviceaccount:kube-system:aws-load-balancer-controller"
          }
        }
      }
    ]
  })
}

resource "aws_iam_policy" "alb_controller" {
  name   = "AWSLoadBalancerControllerIAMPolicy-v2_13_4"
  policy = file("${path.module}/lbc_policy.json")
}

resource "aws_iam_role_policy_attachment" "lb_controller_policy" {
  role       = aws_iam_role.lb_controller.name
  policy_arn = aws_iam_policy.alb_controller.arn
}

resource "helm_release" "lb_controller" {
  name            = "aws-load-balancer-controller"
  repository      = "https://aws.github.io/eks-charts"
  chart           = "aws-load-balancer-controller"
  namespace       = "kube-system"
  timeout         = 900
  wait            = true
  wait_for_jobs   = true
  atomic          = true # in case of failure, auto-uninstall will apply
  cleanup_on_fail = true

  set = [
    {
      name  = "clusterName"
      value = var.cluster_name
    },
    {
      name  = "serviceAccount.create"
      value = "true"
    },
    {
      name  = "serviceAccount.name"
      value = "aws-load-balancer-controller"
    },
    {
      name  = "serviceAccount.annotations.eks\\.amazonaws\\.com/role-arn"
      value = aws_iam_role.lb_controller.arn
    },
    {
      name = "vpcId"
      value = var.vpc_id
    },
    {
      name = "region"
      value = "eu-central-1"
    }
  ]

  depends_on = [
    aws_iam_role_policy_attachment.lb_controller_policy
  ]
}