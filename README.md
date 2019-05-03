# spring-service-draft
Черновой вариант продукта
1. nginx распределяет нагрузку на инстансы service-oapi 
2. service-oapi принимает http запросы на регистрацию, сохраняет в бд и отправляет задание в rabbitMq
3. service-srv получает задание из rabbitMq и выполняет его
