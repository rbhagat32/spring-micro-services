dev:
	docker compose -f compose.dev.yaml up --watch

prod:
	docker compose up --build -d

clean:
	docker compose -f compose.dev.yaml down --rmi local --remove-orphans
	docker image prune -f
	docker volume prune -f
