### Api gateway resources

resource "aws_api_gateway_rest_api" "this" {
  name        = "${var.environment}-${var.app_name}"
  description = "Api gateway proxy integration for ${var.app_name} ${var.environment} environment"
}

resource "aws_api_gateway_resource" "this" {
  rest_api_id = "${aws_api_gateway_rest_api.this.id}"
  parent_id   = "${aws_api_gateway_rest_api.this.root_resource_id}"
  path_part   = "{proxy+}"
}

resource "aws_api_gateway_authorizer" "this" {
  name          = "CognitoUserPoolAuthorizer"
  type          = "COGNITO_USER_POOLS"
  rest_api_id   = "${aws_api_gateway_rest_api.this.id}"
  provider_arns = ["${data.aws_cognito_user_pools.this.arns}"]
}

resource "aws_api_gateway_vpc_link" "this" {
  name        = "${var.environment}-${var.app_name}"
  target_arns = ["${aws_lb.this.arn}"]
}

# method request ANY
resource "aws_api_gateway_method" "any" {
  rest_api_id   = "${aws_api_gateway_rest_api.this.id}"
  resource_id   = "${aws_api_gateway_resource.this.id}"
  http_method   = "ANY"
  authorization = "COGNITO_USER_POOLS"
  authorizer_id = "${aws_api_gateway_authorizer.this.id}"

  request_parameters = {
    "method.request.path.proxy" = true
  }
}

# integration request ANY
resource "aws_api_gateway_integration" "any" {
  rest_api_id             = "${aws_api_gateway_rest_api.this.id}"
  resource_id             = "${aws_api_gateway_resource.this.id}"
  http_method             = "${aws_api_gateway_method.any.http_method}"
  integration_http_method = "ANY"
  type                    = "HTTP_PROXY"
  connection_type         = "VPC_LINK"
  connection_id           = "${aws_api_gateway_vpc_link.this.id}"
  uri                     = "${local.integration_uri}"

  request_parameters = {
    "integration.request.path.proxy"                                   = "method.request.path.proxy"
    "integration.request.header.cd-claims-user-id"                     = "context.authorizer.claims.userId"
    "integration.request.header.cd-claims-cognito-username"            = "context.authorizer.claims.cognito:username"
    "integration.request.header.cd-claims-organization"                = "context.authorizer.claims.organization"
    "integration.request.header.cd-claims-accessible-organization-ids" = "context.authorizer.claims.accessibleOrganizationIds"
    "integration.request.header.cd-claims-roles"                       = "context.authorizer.claims.roles"
  }
}

# method request OPTIONS
resource "aws_api_gateway_method" "options" {
  rest_api_id   = "${aws_api_gateway_rest_api.this.id}"
  resource_id   = "${aws_api_gateway_resource.this.id}"
  http_method   = "OPTIONS"
  authorization = "NONE"

  request_parameters = {
    "method.request.path.proxy" = true
  }
}

# integration request OPTIONS
resource "aws_api_gateway_integration" "options" {
  rest_api_id             = "${aws_api_gateway_rest_api.this.id}"
  resource_id             = "${aws_api_gateway_resource.this.id}"
  http_method             = "${aws_api_gateway_method.options.http_method}"
  integration_http_method = "OPTIONS"
  type                    = "HTTP_PROXY"
  connection_type         = "VPC_LINK"
  connection_id           = "${aws_api_gateway_vpc_link.this.id}"
  uri                     = "${local.integration_uri}"

  request_parameters = {
    "integration.request.path.proxy" = "method.request.path.proxy"
  }
}

# resources for /public
resource "aws_api_gateway_resource" "public" {
  rest_api_id = "${aws_api_gateway_rest_api.this.id}"
  parent_id   = "${aws_api_gateway_rest_api.this.root_resource_id}"
  path_part   = "public"
}

resource "aws_api_gateway_resource" "public_proxy" {
  rest_api_id = "${aws_api_gateway_rest_api.this.id}"
  parent_id   = "${aws_api_gateway_resource.public.id}"
  path_part   = "{proxy+}"
}

resource "aws_api_gateway_method" "public_proxy_any" {
  rest_api_id   = "${aws_api_gateway_rest_api.this.id}"
  resource_id   = "${aws_api_gateway_resource.public_proxy.id}"
  http_method   = "ANY"
  authorization = "NONE"

  request_parameters = {
    "method.request.path.proxy" = true
  }
}

# integration request ANY for /public
resource "aws_api_gateway_integration" "public_proxy_any" {
  rest_api_id             = "${aws_api_gateway_rest_api.this.id}"
  resource_id             = "${aws_api_gateway_resource.public_proxy.id}"
  http_method             = "${aws_api_gateway_method.public_proxy_any.http_method}"
  integration_http_method = "ANY"
  type                    = "HTTP_PROXY"
  connection_type         = "VPC_LINK"
  connection_id           = "${aws_api_gateway_vpc_link.this.id}"
  uri                     = "${local.integration_uri}"
}

# method setting for logging, metrics etc for all methods
resource "aws_api_gateway_method_settings" "any" {
  rest_api_id = "${aws_api_gateway_rest_api.this.id}"
  stage_name  = "${aws_api_gateway_stage.this.stage_name}"
  method_path = "*/*"

  settings {
    metrics_enabled = true
    logging_level   = "INFO"
  }
}

# api gateway stage
resource "aws_api_gateway_stage" "this" {
  stage_name    = "${var.environment}"
  rest_api_id   = "${aws_api_gateway_rest_api.this.id}"
  deployment_id = "${aws_api_gateway_deployment.this.id}"

  variables = {
    baseurl = "${local.baseurl}"
  }

  # as we don't update api-gateway itself frequently
  # so we ignore changes on deployment_id
  # do remember to manually deploy in case of any change
  lifecycle = {
    ignore_changes = ["deployment_id"]
  }
}

# api gateway stage deployment
# 1. https://github.com/terraform-providers/terraform-provider-aws/issues/2918
# there is issue of circular dependency between deployment and stage
# providing an empty/dummy stage name is currently a perfect workaround/solution
# TODO: 2. https://github.com/hashicorp/terraform/issues/6613
# stage_description change will force to create new resource
# on recreation. empty stage_name would cause failure
resource "aws_api_gateway_deployment" "this" {
  rest_api_id = "${aws_api_gateway_rest_api.this.id}"
  stage_name  = ""

  # stage_description = "Deployed at: ${timestamp()}"

  depends_on = [
    "aws_api_gateway_method.any",
    "aws_api_gateway_integration.any",
    "aws_api_gateway_method.options",
    "aws_api_gateway_integration.options",
  ]
}

# api gateway custom domain
resource "aws_api_gateway_domain_name" "this" {
  domain_name     = "${local.custom_domain}"
  certificate_arn = "${module.account.apigw_cert_arn}"
}

# api gateway base path mapping for default path /
resource "aws_api_gateway_base_path_mapping" "root" {
  api_id      = "${aws_api_gateway_rest_api.this.id}"
  stage_name  = "${aws_api_gateway_stage.this.stage_name}"
  domain_name = "${aws_api_gateway_domain_name.this.domain_name}"
}

# enable cloudwatch logging for api gateway
resource "aws_api_gateway_account" "api_gw_cloudwatch_logs" {
  cloudwatch_role_arn = "${data.aws_iam_role.cloudwatch.arn}"
}
