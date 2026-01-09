dev:
	docker compose -f compose.dev.yaml up --watch

clean:
	docker compose -f compose.dev.yaml down --rmi local --remove-orphans
	docker image prune -f
	docker volume prune -f

build:
	docker build -t rbhagat32/api-gateway ./api-gateway
	docker build -t rbhagat32/auth-service ./auth-service
	docker build -t rbhagat32/user-service ./user-service
	docker build -t rbhagat32/email-service ./email-service
	docker build -t rbhagat32/client --build-arg VITE_BACKEND_URL=http://localhost:8080 ./client