#!/usr/bin/env bash
docker pull freefly19/track-debts
docker pull freefly19/track-debts-frontend

docker rm -f track-debts-frontend track-debts track-debts-pg nginx-proxy nginx-proxy-letsencrypt
docker network remove track-debts-net

docker network create track-debts-net
docker run -d -v /root/data:/var/lib/postgresql/data -e POSTGRES_DB=track-debts --network track-debts-net --name track-debts-pg postgres
docker run -d --network track-debts-net -e "LIQPAY_PUBLIC_KEY=<...>" -e "LIQPAY_PRIVATE_KEY=<...>" -e DB_HOST=track-debts-pg -e DB_PORT=5432  --name track-debts freefly19/track-debts
docker run -d --network track-debts-net -e "VIRTUAL_HOST=track-debts.com" -e "LETSENCRYPT_HOST=track-debts.com" -e "LETSENCRYPT_EMAIL=oleksandr.melnyk19@gmail.com" --name track-debts-frontend freefly19/track-debts-frontend
docker run -d --network track-debts-net -p 80:80 -p 443:443 -v /root/nginx/certs:/etc/nginx/certs -v /root/nginx/vhost.d:/etc/nginx/vhost.d -v /usr/share/nginx/html -v /var/run/docker.sock:/tmp/docker.sock:ro --name nginx-proxy jwilder/nginx-proxy
docker run -d --network track-debts-net --volumes-from nginx-proxy -v /var/run/docker.sock:/var/run/docker.sock:ro --name nginx-proxy-letsencrypt jrcs/letsencrypt-nginx-proxy-companion