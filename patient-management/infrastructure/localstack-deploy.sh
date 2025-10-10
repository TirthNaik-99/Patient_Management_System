#!/bin/bash

set -e # Stops the script if any command fails
#if changes are made in stack, run these commands below in comments
#aws --endpoint-url=http://localhost:4566 cloudformation delete-stack \
#    --stack-name patient-management

aws --endpoint-url=http://localhost:4566 cloudformation deploy \
    --stack-name patient-management \
    --template-file "./cdk.out/localstack.template.json" \
    --capabilities CAPABILITY_IAM CAPABILITY_NAMED_IAM \
    --s3-bucket cdk-deployment-bucket

aws --endpoint-url=http://localhost:4566 elbv2 describe-load-balancers \
    --query "LoadBalancers[0].DNSName" --output text
