# create iam role policy
resource "aws_iam_role_policy" "api_gateway" {
  name = "api-gateway-invocation-${var.app_name}-${var.environment}"
  role = "${data.aws_iam_role.auth.id}"

  policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "execute-api:Invoke"
      ],
      "Resource": [
        "arn:aws:execute-api:*:*:${aws_api_gateway_rest_api.this.id}/*"
      ]
    }
  ]
}
EOF
}
