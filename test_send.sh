#!/bin/bash
curl -F "file=@app/build/outputs/apk/app-debug.apk" -F "token=53cd83a3b0d5b3d2c1f7ac84a949fc0e3a90115c" -F "message=test upload" https://deploygate.com/api/users/Nshiba/apps
