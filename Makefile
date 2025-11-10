dev:
	docker compose up --watch

prod:
	docker compose up --build -d

stop:
	docker compose down --rmi local --remove-orphans
	docker image prune -f
