# Track Debts

## How to build jar?

```
./gradlew build
```

## How to build docker image?

```
docker build . -t freefly19/track-debts:<tag>
```

## How to publish to docker hub?

```
docker push freefly19/track-debts:<tag>
```

## How to deploy/redeploy on server?

To stop running container and drop image:
```
docker rm -f track-debts
docker pull freefly19/track-debts:<tag>
```
Spin up container with:
```
docker run -d -p 80:8080 --name track-debts freefly19/track-debts:<tag>
```

**Enjoy :)**