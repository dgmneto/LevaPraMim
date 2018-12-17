# Bateria

Fizemos a análise do uso de bateria usando o Android Profiler durante ambos os fluxos do app.

## Ações tomadas

A princípio, dado a complexidade envolvida em otimizar o uso da bateria, tomamos somente uma ação com esse foco:
- Consultar a localização do usuário em intervalos grandes de tempo*

### [Fluxo do Cliente](https://drive.google.com/file/d/1vBtYrd06xqimOuzFcCrJdDkT3NQwEFsF/view?usp=sharing)**

### [Fluxo do Carregador](https://drive.google.com/file/d/19eqdQilsv0rQbd7eFn7MqyQR0KSjjaBS/view?usp=sharing)**

## Resultado

O uso de batéria ficou além do que esperávamos no experimento. Todavia, como está clarificado a seguir, não tivemos acesso a um dado mais confiavel do uso de bateria para justificar uma refatoração, dado que estávamos adotando boas práticas e frameworks consolidados no mercado.

\*durante o processo de desenvolvimento, observamos que o recurso de my location da própria biblioteca do maps tinha um consumo comparável, nos levando a adotar essa funcionalidade. Todavia, todos os resutaldos aqui apresentados foram utilizando a consulta manual.

**o profiler não tem o recurso de análise de memória para androids antes do 8. Fomos forçados a usar o AVD, o que nos deu resultados super inflados.