### OUTPUTS
### ---------------------------------------------------------------------------
output "api_gw_id" {
  value = "${aws_api_gateway_rest_api.this.id}"
}

output "api_gw_domain" {
  value = "${aws_route53_record.this.fqdn}"
}
