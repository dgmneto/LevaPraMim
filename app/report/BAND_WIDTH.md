# Largura de Banda

Fizemos a análise do uso de rede usando o Android Profiler durante ambos os fluxos do app.

## Ações tomadas

Toda a parte de comunicação do nosso projeto é feita com por 2 libs principais:
- Google Maps: responsável pela renderização dos mapas e do serviço de localização, o maps faz uso de caches locais para as camadas renderizadas nos mapas.
- Firebase Firestore: é a camada de persistência adotada no projeto. Adota um modelo de armazenamento mobile first, que permite otimizações medianas relacionadas ao uso de rede, mas otimizações drásticas relacionadas a latência.

### [Fluxo do Cliente](https://drive.google.com/file/d/130iSNSrS7WdanTZiQMj5yKr_Ay5KSE5d/view?usp=sharing)

### [Fluxo do Carregador](https://drive.google.com/file/d/1affpbFu3Dpvmf8rWV-eI2w9XHyhE8s8d/view?usp=sharing)

## Resultado

Tomados como base a velocidade média das bandas 2G, aproximadamente 150Kbits/s.

É largura de banda muito estreita, mas, olhando os gráficos do profiler, podemos ver que ambos os fluxos consomem abaixo disso. Vale notar que o fluxo do cliente teve picos de consumo quanto a corrida terminou, o que pode ser explica pelo cold start da cache do maps. Em execuções consecutivas, essa curva não mais é vista nos gráficos.
