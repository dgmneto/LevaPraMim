# carrega-me

## Proposta
### Qual é a sua ideia de aplicativo? Inclua uma breve justificativa.
Queremos construir uma espécie de Uber para carregadores de compras em feiras. A motivação veio de um parente, dono de uma loja na avenida 44 de Goiânia, uma das maiores feiras do país. Nas feiras existem carregadores para grandes compras que cobram entre 15 e 20 reais para carregar compras no valor de 5 a 10 mil reais para hotéis próximos a feira. O movimento é muito intenso na região e é difícil ir até o local onde os carregadores ficam para solicitar o serviço. Quase sempre dependem da sorte de algum estar passando.
### Quem usará seu aplicativo e por que eles o usarão?
Temos 2 principais usuários em mente:
 - Compradores da feira, que usarão para conseguir carregadores depois de fazerem as compras.
 - Carregadores que usarão para aumentar sua visibilidade para o público.
### Existe um aplicativo similar? Se sim, como o seu será diferente?
Em termos de negócio, não. Em termos técnicos, o próprio uber seria muito semelhante, mas temos outro objetivo e outro mercado em mente. Além de que pretendemos construir uma primeira versão mais simples, que só permita origens e destinos pré definidos.
### Como sua aplicação será estruturada? Quais telas o usuário irá interagir, e o que elas fazem? 
Existem 2 fluxos principais no aplicativo. O dos carregadores e o dos compradores.
Compradores devem ter telas:
 - Tela principal: nesta tela o comprador deverá poder ver um mapa e uma lista das lojas vinculadas, que inicialmente serão poucas. O comprador poderá selecionar a loja na qual se encontra. Em seguida uma lista dos destinos cadastrados aparecerá para que o usuário selecione o destino. Por fim, o comprador confirma o pedido e acompanha no mapa o chamado.
 - Tela de perfil: nesta tela o comprador poderá gerenciar suas informações de pagamento, além de nome, email e outras informações pessoais, talvez.
Carregadores devem ter telas:
 - Tela principal: nesta tela o carregador verá os chamados em abertos mais próximos para que ele possa responder. Ao selecionar o chamado, o carregador verá a loja e o destino.
 - Tela de perfil: nesta tela o carregador poderá editar suas informações pessoais.
### Qual é o fluxo de trabalho?
A primeira etapa é modelar os dados.
Em seguida partiremos para configurar nossa camada de persistência, o firebase
Em seguida iremos desenvolver o app android
Por fim, iremos publicá-lo na play store
### Quais componentes Android serão utilizados, além de classes, bibliotecas de terceiros, paradigmas de design, etc? Sua aplicação deve usar pelo menos 3 componentes básicos de Android.
Componentes:
 - Usaremos services para atualizar o estados dos eventos de forma não bloqueante
 - Usaremos broadcast receiver para notificar eventos a activities
 - Usaremos activities para fazer as telas

Biblioteca de terceiros:
 - Usaremos o SDK do firebase para acesso ao sistema de persistência da google.
 - Usaremos o SDK do google maps.
 - Usaremos dagger 2

Paradigmas de design:
 - Usaremos injeção de dependência
 - Tentaremos usar abstract factories para compartilhar classes entre os diferentes fluxos
### Se for feito em dupla, como será dividido o trabalho?
Como temos 2 fluxos quase independentes, cada pessoa fará um fluxo.
