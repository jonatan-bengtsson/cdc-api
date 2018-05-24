#!/bin/bash -e

# set terraform remote state

s3key_part="enter your s3key part for terraform remote state"

function usage() {
    echo "Usage: s3key_part set for terraform remote state"
    echo "i.e app_name/environment (cdc-api/test)"
    echo
}

# Ensure script console output is separated by blank line at top and bottom to improve readability
trap echo EXIT
echo

# Validate the input arguments
if [ "$#" -ne 1 ]; then
    usage
    exit 1
fi

s3key_part="$1"

# Run terrform init
echo "Run terraform init to set key"
rm -rf ./.terraform
terraform init -backend-config="key=application/$s3key_part/custom-resources.tfstate"
