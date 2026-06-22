ToolsChallenge
Pequeno serviço Spring Boot para gerenciamento de pagamentos — exemplos de entidade, repositório e testes.

Requisitos

Java 17
Maven (ou use uma IDE que suporte Maven)
Build e execução

Compilar: mvn -U clean package
Executar: mvn spring-boot:run
Testes

Executar todos os testes: mvn -U test
Executar teste específico: mvn -Dtest=PagamentoRepositoryTest test
Observações

H2 é usado como banco em tempo de execução para desenvolvimento/testes.
Se a IDE não reconhecer anotações de teste (ex.: DataJpaTest), reimporte o projeto Maven (Reimport/Refresh) e execute mvn -U test na linha de comando.
Contato

Autor: Welinton Borges
