# Track Debts

## To build jar file and docker image then publish docker image in docker hub execute: 

```
./gradlew build && docker build . -t freefly19/track-debts && docker push freefly19/track-debts
```

## How to deploy on server?

Spin up container with:
```
docker network create track-debts-net
docker run -d -v ./data:/var/lib/postgresql/data -e POSTGRES_DB=track-debts --network track-debts-net --name track-debts-pg postgres
docker run -d --network track-debts-net -e "APP_SECRET=<secret>" -e DB_HOST=track-debts-pg -e DB_PORT=5432  --name track-debts freefly19/track-debts
```
_**secret** should be longer than 86 chars_

**Enjoy :)**