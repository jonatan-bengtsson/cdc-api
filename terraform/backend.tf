### backend

terraform {
  backend "s3" {
    bucket         = "tingcore-master-terraform-backend"
    region         = "eu-west-1"
    dynamodb_table = "terraform_lock"
    acl            = "bucket-owner-full-control"
  }
}
