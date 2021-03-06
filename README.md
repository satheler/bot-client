# Client Bot - Engenharia de Software

## Objetivos
Este projeto tem como objetivo implementar um componente servidor para um sistema simplificado de **Bot**.
Em geral, este servidor deverá responder questões básicas sobre o curso de Engenharia de Software a partir de perguntas enviadas pelos usuários. 

Um usuário poderá acessar este bot através de um [Server](https://github.com/gustavosatheler/bot-server) para enviar e receber perguntas para/do Servidor. Este servidor deverá:
1. receber e/ou aguardar mensagens;
2. processar mensagens, e;
3. responder adequadamente para o Cliente.

## Tipos de comandos
Este servidor tem 3 tipos de comandos. Cada comando possui uma complexidade distinta (básico, intermediário ou avançado), conforme apresentada a seguir.
1. **Nivel básico:** Não requer consulta externa, apenas local e não requer passagem de parâmetros.
2. **Nivel intermediário:** Requer consulta externa e não exige passagem de parâmetros.
3. **Nivel avançado:** Requer consulta externa e passagem de parâmetros.

## Uso
O usuário atráves do [Cliente](https://github.com/gustavosatheler/client-server) deverá informar um comando iniciando com “\” (backslash) para que o servidor possa identificar cada caso.

*Caso o usuário envie uma mensagem sem o caractere “\”, este servidor irá apenas repetir a mensagem para o cliente, sem efetuar nenhuma ação adicional.*
