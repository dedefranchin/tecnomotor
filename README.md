# Desafio Tecnomotor

Este é um projeto de aplicativo Android desenvolvido como parte do desafio técnico da Tecnomotor. O aplicativo funciona como um catálogo de montadoras e veículos, permitindo a visualização e edição dos dados mesmo estando offline.

## Descrição do Projeto

O aplicativo permite ao usuário gerenciar um catálogo de montadoras de automóveis e seus veículos. Os dados são inicialmente sincronizados de uma API remota e armazenados localmente, permitindo que o usuario use o aplicativo mesmo sem internet, após sucesso na primeira sincronização.

### Funcionalidades Implementadas:

*   **Sincronização de Dados**: Na primeira execução, o aplicativo solicita a sincronização dos dados das montadoras e dos veículos a partir de uma API remota.
*   **Listagem de Montadoras**: Exibe a lista de montadoras cadastradas.
*   **CRUD de Montadoras**:
    *   **Criação**: Adicionar novas montadoras.
    *   **Leitura**: Visualizar os detalhes.
    *   **Atualização**: Editar o nome de montadoras existentes.
    *   **Exclusão**: Remover montadoras (utilizando soft delete).
*   **Listagem de Veículos**: Ao selecionar uma montadora, exibe a lista de veículos associados a ela e permite busca pelo modelo do veículo.
*   **Listagem de Veículos com filtros**: Exibe a lista de veiculos e permite filtrar por montadora e/ou por modelo do veículo.
*   **CRUD de Veículos**:
    *   **Criação**: Adicionar novos veículos para uma montadora.
    *   **Leitura**: Visualizar os detalhes (modelo, ano, motorização).
    *   **Atualização**: Editar as informações de um veículo existente.
    *   **Exclusão**: Remover veículos (utilizando soft delete).
*   **Status de Rede**: Um indicador visual na interface mostra o status atual da conexão com a internet (online/offline).

## Tecnologias, Bibliotecas e Decisões Técnicas

*   **Linguagem**: [Kotlin]
*   **Arquitetura**: MVVM (Model-View-ViewModel).
*   **Persistência de Dados**: [Room].



## Configurações do ambiente

*   **Versão do sdk: 21.0.6**:



### Decisões de Arquitetura:

*   **Offline-First**: A aplicação foi projetada para funcionar primariamente com os dados locais. A rede é usada para sincronizar apenas no primeiro uso, não bloqueando o uso do app mesmo sem internet após essa primeira sincronização.
*   **Soft Delete**: Em vez de excluir registros permanentemente, eles são marcados como inativos (`fl_ativo = 0`). 

## Instruções para Compilação e Execução

### Pré-requisitos

*   [ANDROID STUDIO]
*   [SDK]

### Passos

1.  **Clonar o repositório:**
    ```bash
    git clone https://github.com/dedefranchin/tecnomotor.git
    ```

2.  **Abrir no Android Studio:**
    *   Inicie o Android Studio.
    *   Selecione "Open an existing Project".
    *   Navegue até o diretório onde o projeto foi clonado e selecione-o.

3.  **Compilar e Executar:**
    *   Aguarde o Android Studio sincronizar o projeto. O Gradle fará o download de todas as dependências necessárias.
    *   Selecione um dispositivo (emulador ou físico) na barra de ferramentas.
    *   Clique no botão "Run 'app'" (ícone de play verde).

4.  **Execução via Linha de Comando (Opcional):**
    *   Para compilar o APK de debug, navegue até a raiz do projeto e execute:
        ```bash
        ./gradlew assembleDebug
        ```
    *   O APK gerado estará em `app/build/outputs/apk/debug/`. 

5.  **Problemas ao compilar o app**
    *   Verifique se está usando um sdk compativel. 
    **Versão do sdk ultilizado: 21.0.6**:
