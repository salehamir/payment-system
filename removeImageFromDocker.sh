sudo docker container prune
sudo docker image rm payment-system_wiremock-standalone payment-system_notification-service payment-system_user-service payment-system_payment-service
sudo docker-compose up