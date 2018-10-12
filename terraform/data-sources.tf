### data sources

data "aws_caller_identity" "current" {}

module "account" {
  source = "git::ssh://git@bitbucket.tingcore-infra.com/aws/terraform-data-source.git//account"

  business_unit = "${var.business_unit}"
  environment   = "${var.environment}"
}

module "cloudfront_certificate" {
  source = "git::ssh://git@bitbucket.tingcore-infra.com/aws/terraform-data-source.git//certificates/cloudfront"

  business_unit = "${var.business_unit}"
  environment   = "${var.environment}"
}

module "route53" {
  source = "git::ssh://git@bitbucket.tingcore-infra.com/aws/terraform-data-source.git//route53"

  providers = {
    aws = "aws.sf"
  }

  environment = "${var.environment}"
}

module "vpc" {
  source = "git::ssh://git@bitbucket.tingcore-infra.com/aws/terraform-data-source.git//vpc"

  unit_alias = "${module.account.unit_alias}"

  environment = "${var.environment}"
}

data "aws_cognito_user_pools" "this" {
  name = "${title(var.environment)}${var.cognito_user_pool_name}"
}

data "aws_iam_role" "auth" {
  name = "${var.environment}-charge-drive-cognito-auth"
}

data "aws_iam_role" "cloudwatch" {
  name = "${local.unit_alias}-${var.environment}-apigw-cloudwatch"
}
