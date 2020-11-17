curl --request POST \
  --url http://localhost:8084/userProfileService/v1/register \
  --header 'Content-Type: application/json' \
  --data '{
    "username":"salehamir70",
    "fullName":"amir saleh",
    "mobile":"09382113983",
		"password":"123456"
}'

curl --request POST \
  --url http://localhost:8084/userProfileService/v1/addCard \
  --header 'Content-Type: application/json' \
  --data '{
    "cardNumber":"6037291066541224",
    "user":{"username":"salehamir70"},
    "bankName":"mellat",
		"expDate":"0002"
}'

curl --request POST \
  --url http://localhost:8080/paymentService/v1/transfer \
  --header 'Content-Type: application/json' \
  --data '{
    "sourceCardNumber":"6037291066541224",
    "user":{"username":"salehamir70"},
    "targetCardNumber":"6063431056105879",
		"cvv2":"457",
		"expDate":"0002",
		"pin":"123456",
		"amount":50000
}'