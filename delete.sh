#!/usr/bin/env bash

aws --profile nva-sandbox cloudformation delete-stack --stack-name test-lambda --region eu-west-1
