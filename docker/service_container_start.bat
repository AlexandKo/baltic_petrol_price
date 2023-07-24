 docker run --net petrol_net ^
           -e DB_URL=jdbc:postgresql://192.168.0.37:5436/petrol_price ^
           -p 9000:9000 ^
           petrol_service