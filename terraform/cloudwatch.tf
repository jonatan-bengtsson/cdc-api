resource "aws_cloudwatch_log_group" "this" {
  name = "API-Gateway-Execution-Logs_${aws_api_gateway_rest_api.this.id}/${aws_api_gateway_stage.this.stage_name}"

  retention_in_days = "${var.environment == "prod" ? 365 : 90}"
}
