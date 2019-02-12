#!/usr/bin/env bash
docker exec -t track-debts-pg pg_dumpall -c -U postgres > dumps/dump_`date +%d-%m-%Y"_"%H_%M_%S`.sql
docker run --rm -v /root/dumps:/backup \
    -e COMPRESSOR=none \
    -e AWS_ACCESS_KEY=<access-key> \
    -e AWS_SECRET_KEY=<secret-key> \
    -e S3_PATH=freefly19/track-debts \
    -e S3CMD_OPTIONS="--host=ams3.digitaloceanspaces.com --host-bucket=$(bucket).ams3.digitaloceanspaces.com" \
    freefly19/dir-backup-s3