# api gateway custom domain dns record

resource "aws_route53_record" "this" {
  provider = "aws.sf"
  zone_id  = "${module.route53.public_zone_id}"

  name = "${aws_api_gateway_domain_name.this.domain_name}"
  type = "A"

  alias {
    name                   = "${aws_api_gateway_domain_name.this.cloudfront_domain_name}"
    zone_id                = "${aws_api_gateway_domain_name.this.cloudfront_zone_id}"
    evaluate_target_health = true
  }
}
