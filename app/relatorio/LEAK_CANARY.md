# Leak Canary

Usamos o Leak Canary para detectar vazamentos de memória na aplicação.

Com o uso da ferramenta, encontramos [um vazamento](./leak_canary/leak.txt).

## Descrição

O vazamento encontrando é bastante sutil, o que faz dele uma das fontes mais comuns de memory leakage no desenvolvimento android. O erro está relactionado ao uso de `inner class` (ou sub classes não estáticas em Java).

O problema é causado no seguinte cenário:

```kotlin
class LeakingClass {

    //...

    private val innerLeakingClass = InnerLeakingClass()

    // ...

    inner class InnerLeakingClass : SomeInterface {

        // ...

        fun onEvent() {

            // ...

            this@LeakingClass.doSomething()

            // ...

        }
    }

    // ...
}
```

Nesse cenário, existe uma dependência cíclica muit discreta entre `LeakingClass` e `InnerLeakingClass`. `InnerLeakingClass`, por baixo dos panos, guarda uma refência forte para `Leaking Class`, não permitindo que a mesma seja desalocada pelo GC. Sendo assim, o objeto `innerLeakingClass` também nunca será desalocado.

## Solução

Uma solução simples é tornar essa refência de `LeakingClass` em `InnerLeakingClass` fraca. Com a refatoração, o código fica assim:

```kotlin
class LeakingClass {

    //...

    private val innerLeakingClass = InnerLeakingClass(this)

    // ...

    class InnerLeakingClass() : SomeInterface {

        // ...

        private lateinit var ref : WeakReference<LeakingClass>
        constructor(leakingClass : LeakingClass) : this() {
            this.ref = WeakReference(leakingReference)
        }

        // ...

        fun onEvent() {

            // ...

            this.ref.get()!!.doSomething()

            // ...

        }
    }

    // ...
}
```

A referência fraca não impede a `LeakingClass` de ser desalocada e `InnerLeakingClass` não tem mais uma refência implícita para `LeakingClass`.