#!/usr/bin/env bash
#This is script is meant only for testing purposes. It is not a proper deployment method.

sam build -b .aws-sam

sam deploy --profile nva-sandbox  --template-file ./.aws-sam/template.yaml \
--s3-bucket orestis-b2 \
--stack-name test-lambda \
--capabilities CAPABILITY_IAM \
--region eu-west-1