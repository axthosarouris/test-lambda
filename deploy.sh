#!/usr/bin/env bash

##gradle clean build
sam build -b deployment

##sam package --template-file template.yaml --s3-bucket orestis-b1 --output-template-file packaged.yaml

sam deploy --template-file ./deployment/template.yaml \
--s3-bucket orestis-b1 \
--stack-name test-lambda-dev \
--capabilities CAPABILITY_IAM \
--region eu-west-1