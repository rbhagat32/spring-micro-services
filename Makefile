dev:
	docker compose -f compose.dev.yaml up --watch

clean:
	docker compose -f compose.dev.yaml down --rmi local --remove-orphans
	docker image prune -f
	docker volume prune -f

prod:
	docker build -t rbhagat32/api-gateway:latest ./api-gateway
	docker build -t rbhagat32/auth-service:latest ./auth-service
	docker build -t rbhagat32/user-service:latest ./user-service
	docker build -t rbhagat32/email-service:latest ./email-service
	docker build -t rbhagat32/client:latest --build-arg VITE_BACKEND_URL=http://localhost:8080 ./client