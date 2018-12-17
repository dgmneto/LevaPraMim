# CPU

Fizemos a análise do uso de CPU usando o Android Profiler durante ambos os fluxos do app.

## Ações tomadas

Desde o começo do desenvolvimento foram todamas ações para evitar o consumo descontrolado do uso de CPU.

O padrão singleton com injeção de dependência foi usado para compartilhar threads, que são de inicialização custoso e consumem grandes quantidades de CPU e de memória. A referência para a thread principal também foi compartilhada com os interactors para evitar mai sincronizações com os resultados das regas de negócio.

### [Fluxo do Cliente](https://drive.google.com/file/d/1rwEq_tkLn_MMgiDOE4Da3uVW7J5bsD2a/view?usp=sharing)

### [Fluxo do Carregador](https://drive.google.com/file/d/1Ozd0ToH8onUs4SjQrmZC5JEmymL5p2oN/view?usp=sharing)

## Resultado

Como todo aplicativo de tal simplicidade deve ser, tivemos um uso de CPU extremamente baixo. O aplicativo se mantem idle praticamente 100% do tempo, usando no máximo 40% de CPU em um dado instante.

De forma bastante objetiva, não achamos nenhum ponto de melhoria em se tratando do uso de CPU.