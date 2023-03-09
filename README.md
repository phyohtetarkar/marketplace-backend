# Shoppingcenter Backend

## Docker setup

#### Create .env file with the following contents:

```ini
# Username and password for postgres
POSTGRES_USER=
POSTGRES_PASSWORD=
POSTGRES_VERSION=15.2-alpine

# Password for the 'elastic' user (at least 6 characters)
ELASTIC_PASSWORD=

# Version of Elastic products
STACK_VERSION=8.5.3

# Port to expose Elasticsearch HTTP API to the host
ES_PORT=9200
#ES_PORT=127.0.0.1:9200

# Set to 'basic' or 'trial' to automatically start the 30-day trial
LICENSE=basic

# Increase or decrease based on the available host memory (in bytes)
MEM_LIMIT=1073741824
```

#### Run only first time for elasticsearch's certs resource creation in docker volumes

```bash
docker-compose -f create-certs.yml run --rm create_certs
```

#### Docker compose instructions

```bash
# create and start containers
docker-compose up -d

docker-compose stop

docker-compose start

# this command remove all containers and netowrks
docker-compose down

# this command remove all containers and netowrks including volumes
docker-compose down -v
```
