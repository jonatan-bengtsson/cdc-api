### variables

variable "app_name" {
  default = "cdc-api"
}

variable "business_unit" {
  default = "charge-and-drive"
}

variable "environment" {}

variable "availability" {
  default = "private"
}

variable "cognito_user_pool_name" {
  default = "ChargeDrive"
}

# locals

locals {
  unit_alias              = "${module.account.unit_alias}"
  account_id              = "${module.account.account_id}"
  baseurl                 = "${var.availability == "private" ? "${var.app_name}.tingcore-${var.environment}.com" : "${replace("${var.app_name}-${var.environment}.tingcore-infra.com", "-prod", "")}"}"
  custom_domain           = "${var.environment == "prod" ? "${var.app_name}-gw.${replace(module.route53.public_zone_name, "/[.]$/", "")}" : "${var.app_name}-gw-${var.environment}.${replace(module.route53.public_zone_name, "/[.]$/", "")}"}"
  integration_uri         = "${var.availability == "private" ? "http" : "https"}://$${stageVariables.baseurl}/{proxy}"
  integration_uri_public  = "${var.availability == "private" ? "http" : "https"}://$${stageVariables.baseurl}/public/{proxy}"
}
