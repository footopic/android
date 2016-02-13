#!/bin/bash
curl -F "file=@/bitrise/deploy/app-debug.apk" -F "token=$DEPLOY_GATE_TOKEN" -F "message=$GIT_CLONE_COMMIT_MESSAGE_SUBJECT $GIT_CLONE_COMMIT_MESSAGE_BODY" https://deploygate.com/api/users/Nshiba/apps
