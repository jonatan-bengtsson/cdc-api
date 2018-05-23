### variables

variable "assume_role_name" {
  default = "jenkins-agent"
}

variable "assume_role_name_sf" {
  default = "jenkins-agent"
}

### providers

provider "aws" {
  allowed_account_ids = ["${local.account_id}"]
  region              = "eu-west-1"

  assume_role {
    role_arn = "arn:aws:iam::${local.account_id}:role/${var.assume_role_name}"
  }
}

# provider for route53 in spring-flexipower

provider "aws" {
  alias               = "sf"
  allowed_account_ids = ["438898015039"]
  region              = "eu-west-1"

  assume_role {
    role_arn = "arn:aws:iam::438898015039:role/${var.assume_role_name_sf}"
  }
}
