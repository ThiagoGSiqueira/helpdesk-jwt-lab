# Rotas da API (base: http://localhost:8080)

| Método | URL | Body | Resposta |
|--------|-----|------|----------|
| POST | /chamados | JSON abaixo | 201 + chamado |
| GET | /chamados | — | 200 + lista |
| GET | /chamados/{id} | — | 200 ou 404 |
| PUT | /chamados/{id} | `status` e/ou `descricao` | 200 ou 404 |
| DELETE | /chamados/{id} | — | 204 ou 404 |

## POST /chamados
```json
{
  "titulo": "Computador não liga",
  "descricao": "A máquina da recepção não dá sinal de vídeo.",
  "categoria": "HARDWARE",
  "prioridade": "ALTA"
}
```

## PUT /chamados/{id}
```json
{
  "status": "EM_ANDAMENTO",
  "descricao": "Descrição atualizada (opcional)"
}
```

## Valores aceitos
- categoria: HARDWARE, SOFTWARE, REDE, ACESSO, OUTROS
- prioridade: BAIXA, MEDIA, ALTA, CRITICA
- status: ABERTO, EM_ANDAMENTO, CONCLUIDO

Console H2: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:chamadosdb`, user `sa`, senha vazia)
