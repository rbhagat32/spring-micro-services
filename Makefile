dev:
	docker compose -f compose.dev.yaml up --watch

clean:
	docker compose -f compose.dev.yaml down --rmi local --remove-orphans
	docker image prune -f
	docker volume prune -f

build:
	docker build -t rbhagat32/micro-api-gateway ./api-gateway
	docker build -t rbhagat32/micro-auth-service ./auth-service
	docker build -t rbhagat32/micro-user-service ./user-service
	docker build -t rbhagat32/micro-email-service ./email-service
	docker build -t rbhagat32/micro-client --build-arg VITE_BACKEND_URL=http://localhost:8080 ./client

push:
	docker push rbhagat32/micro-api-gateway
	docker push rbhagat32/micro-auth-service
	docker push rbhagat32/micro-user-service
	docker push rbhagat32/micro-email-service
	docker push rbhagat32/micro-client