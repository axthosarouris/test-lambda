#!/usr/bin/env bash


##sam build

sam package --template-file template.yaml --s3-bucket orestis-b1 --output-template-file packaged.yaml

aws cloudformation deploy --template-file ./packaged.yaml --stack-name test-lambda-dev --capabilities CAPABILITY_IAM --region eu-west-1