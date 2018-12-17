# Descrição do projeto

## Motivação
O projeto foi motivado por familiares que compartilharam que, em Goiânia (Goiás), existe uma regiao chamada Rua 44 e é uma região extremamente comercial, onde grandes compras de atacado são feitas por hotéis, restaurantes e varejistas dos mais diversos setores.
A maioria desses compradores de quantidade precisa transportar o que foi comprado daquela região até as rodoviárias ou estabelecimentos mas não vão com transporte de carga, e para isso, existem os chamados carregadores, que são trabalhadores em sua maioria informal que se disponiblizam a levar as compras para os clientes.
Encontrar um carregador é um golpe de sorte hoje em dia, pois não estão centralizados em um lugar, e quando foi comentado sobre esse fato, resolvemos criar o Leva Pra Mim.

## O que é
O Leva Pra Mim é um aplicativo que permite a centralização dos carregadores para facilitar o encontro com clientes. Nele, ao fazer login, o cliente pode escolher um ponto de referência da lista pré-determinada dos locais mais comuns para tal, bem como dizer também para onde deseja ir.
Já para o carregador, ele poderá ver todas as solicitaçoes de viagens ao mesmo tempo e escolher qual deseja realizar.

## Credenciais

Temos 2 usuários cadastrados atualmente:
 - Cliente: Usuário: dgmneto@gmail.com / Senha: 123456
 - Carregador: Usuário: dgmn@cin.ufpe.br / Senha: 123456

## Fluxo
Para o cliente o fluxo é:
- Fazer Login
- Solicitar a corrida escolhendo o local de origem e destino a viagem
- Esperar o carregador
- Seguir viagem ao encontrar com carregador
- Clicar em finalizar no Dialog que aparece o valor final da corrida
Não precisando de mais nenhuma ação após a solicitação da corrida
Para melhor compreensão do fluxo do cliente, temos um [vídeo](https://drive.google.com/file/d/1l21x8F5WoJhqeSE8mjfzILv9sfPog76Z/view?usp=sharing)

Já para o carregador o fluxo é:
- Fazer Login
- Escolher a corrida que deseja realizar
- Anunciar que chegou ao ponto de encontro para o cliente
- Iniciar a corrida
- Indicar que chegou ao destino
- Clicar em finalizar no Dialog que aparece o valor final da corrida
Para melhor compreensão do fluxo do cliente, temos um [vídeo](https://drive.google.com/file/d/1uETxwV79MGKZuTHPEAEusY1unOQOwz7t/view?usp=sharing)

## Análises

Fizemos as mais diversas análises utilizando o Android Profiler e o Leak Canary. No final das contas, foi necessária uma refatoração para retificar um vazamento de memória relacionado ao uso de `inner class`.
- [CPU](./report/CPU.md)
- [Leak Canary](./report/LEAK_CANARY.md)
- [Memória](./report/MEMORY.md)
- [Largura de Banda](./report/BAND_WIDTH.md)
- [Bateria](./report/BATTERY.md)