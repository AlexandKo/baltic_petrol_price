docker build -t petrol_postgres -f Docker_postgres ./..
docker build -t petrol_service -f Docker_service ./..
docker network create petrol_net