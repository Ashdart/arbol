# ARBOL API

The functionality of this API is to implement te basic operations of a n-ary tree structure.

## Contents

- [Requirements](#requirements)
- [Utilization](#utilization)
- [Swagger](#swagger)

## Requirements

- Java 8
- Lombok
- Maven
- Git
- MongoDB

## Utilization

Now with the app running what you can do is:

- Create/Delete a tree
- Remove/Insert a child node.
- Get the parent/brother/firstchild of a node.
- Get the root of a tree.
- Get the information of a tree in preorder mode.

### CREATE A TREE

```
REQUEST:
    METHOD: POST
    HEADER: NONE
    BODY:
        {
            "id": 0,
            "root": {
                "children": [
                    null
                ],
                "data": generic type,
                "id": 0
            }
        }

RESPONSE:
    STATUS: CREATED
```

### INSERT NODE

```
REQUEST:
    METHOD: POST
    HEADER: ARBOL ID & NODO ID
    BODY:
        {
            "data": generic type,
            "id": 0
        }

RESPONSE:
    STATUS_CODE:
    	200: OK
        400: BAD REQUEST
    	404: ARBOL not found
```

### PREORDER MODE

```
REQUEST:
    METHOD: GET
    HEADER: ARBOL ID
    BODY: NONE

RESPONSE:
    BODY:
        List of Object (Generic Type) content information
```

## Swagger

This api include Swagger. Swagger is a set of open-source tools built around the OpenAPI Specification that can help you design, build, document and consume REST APIs. The major Swagger tools include:

- Swagger Editor – browser-based editor where you can write OpenAPI specs.
- Swagger Codegen – generates server stubs and client libraries from an OpenAPI spec.
- Swagger UI – renders OpenAPI specs as interactive API documentation.

To access enter to the next url: `http://localhost:{your port*}/swagger-ui.html`

- The port specified in the application.properties folder. If you want to change it change `server.port={other port}`
