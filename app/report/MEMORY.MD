# Memória

Fizemos a análise do uso de memória usando o Android Profiler durante ambos os fluxos do app.

## Ações tomadas

- Uso do [Leak Canary](./LEAK_CANARY.md) para evitar uso descontrolado de memória
- Desalocação e uso de referências fracas para possibilitar o bom funcionamento do GC.

### [Fluxo do Cliente](https://drive.google.com/file/d/1UEV88f2h8doDl95HRJmVo9_zFRPSnWEw/view?usp=sharing)

### [Fluxo do Carregador](https://drive.google.com/file/d/1vIwWESAQBIQfh1J1Qg4pkOtOv1OuR8KM/view?usp=sharing)

## Resultados

Apesar da dificuldade em achar aplicativos comparáveis com o nosso, tomamos parametros absolutos como base. Por exemplo, queríamos ver uma queda brusca entre as transições das Activities, o que poderia significar que os recursos estão sendo desalocados corretamente.

Além disso, o Leak Canary foi fundamental para validarmos que nosso uso de memória estava coerente.