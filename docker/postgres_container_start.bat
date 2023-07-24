docker run --net petrol_net ^
            -p 5436:5432 ^
            -e POSTGRES_USER=postgres ^
            -e POSTGRES_PASSWORD=password ^
            -e POSTGRES_DB=petrol_price  ^
            petrol_postgres