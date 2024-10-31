
# Delys Nort Snack
=====================
:sparkles:  :heart: **Bienvenidos a Delys Nort Snack** :heart:  :sparkles:

## Sobre nosotros
---------------
**Nuestra historia**
Delys Nort Snack es una empresa dedicada a la venta de snacks de alta calidad y variedad. Nuestra misión es brindar a nuestros clientes una experiencia de compra única y satisfactoria.

**Nuestros valores**
:heart: **Calidad**: ofrecemos solo los mejores snacks para nuestros clientes.
:smile: **Servicio**: nuestro equipo de atención al cliente está siempre listo para ayudarte.
:gift: **Variedad**: tenemos una amplia selección de snacks para todos los gustos.

## Productos
------------

### Snacks salados
#### :fries: :peanuts: **Snacks clásicos**

* ofrecemos una amplia variedad de snacks salados, desde clásicos como papas fritas y cacahuetes hasta opciones más gourmet como snacks de queso y cebolla.

### Snacks dulces
#### :cake:  **Dulces deliciosos**

* nuestros snacks dulces son perfectos para aquellos con un dulce. Desde galletas y brownies hasta frutas secas y chocolates.

### Snacks saludables
#### :apple: :carrot: **Opciones saludables**

* también ofrecemos opciones saludables para aquellos que buscan una alternativa más nutritiva. Nuestros snacks saludables incluyen opciones como frutas frescas, nueces y semillas.

## Servicios
------------

### Entrega a domicilio
#### :house: :truck: **Entrega rápida y segura**

* ofrecemos entrega a domicilio en un plazo de 24 horas en áreas seleccionadas.

### Servicio al cliente
#### :phone: :email: **Atención al cliente**

* nuestro equipo de atención al cliente está disponible para ayudarte con cualquier pregunta o inquietud que tengas.
# Funcionalidades Principales 


   - Control automatizado del stock de productos.
   - Actualización en tiempo real del inventario a medida que se realizan ventas.
   - Notificaciones de bajo inventario para facilitar la reposición oportuna de productos.


   - Carrito de compras en línea que permita a los clientes seleccionar múltiples productos y finalizar su compra.
   - Procesamiento seguro de pagos con diferentes métodos (tarjeta de crédito, débito, transferencias, etc.).
   - Sistema de seguimiento de pedidos que informe a los clientes sobre el estado de sus compras.
# Objetivos del Proyecto:



   - Automatizar el control del stock de productos para evitar faltantes o excedentes.
   - Facilitar el seguimiento del flujo de productos en tiempo real.


   - Desarrollar una plataforma de comercio electrónico que permita a los clientes realizar compras en línea de manera sencilla y rápida.
   - Implementar estrategias de marketing digital para aumentar la visibilidad de los productos.
   ## Tecnologías Utilizadas

- CSS
- ORACLE
- JSP


## La Base de Datos contiene: 
### Tablas
1.  carrito

* id_carrito (Clave primaria): Identificador único para cada carrito.

* Campos: id_usuario, fecha_creacion.

2. carrito_productos

* id_carrito_producto (Clave primaria): Identificador único para cada producto en el carrito.
* Campos: id_carrito, id_producto, cantidad.

3. detalle_pedidos

* id_detalle_pedido (Clave primaria): Identificador único para cada detalle de pedido.
* Campos: id_pedido, id_producto, cantidad, precio_unitario.

4. historial_ordenes

* id_historial (Clave primaria): Identificador único para cada registro del historial de órdenes.
*Campos: id_usuario, id_pedido, estado_anterior, estado_nuevo, fecha_modificacion.
5. pedidos

* id_pedido (Clave primaria): Identificador único para cada pedido.
* Campos: id_usuario, fecha_pedido, total, estado.
6. productos

* id_producto (Clave primaria): Identificador único para cada producto.
* Campos: nombre_producto, descripcion, precio, stock, imagen_url.
7. roles

* id_rol (Clave primaria): Identificador único para cada rol.
* Campos: nombre_rol.
8. trabajadores

* id_trabajador (Clave primaria): Identificador único para cada trabajador.
* Campos: id_usuario, puesto, salario.
9. usuarios

* id_usuario (Clave primaria): Identificador único para cada usuario.
* Campos: nombre, correo, contrasena, direccion, fecha_registro.
10. usuario_roles

* id_usuario_rol (Clave primaria): Identificador único para cada relación usuario-rol.
* Campos: id_usuario, id_rol.




## Contacto
------------

### Teléfono
#### :phone: **Llámanos**

* +51 924 401 057

### Correo electrónico
#### :email: **Escríbenos**

* delisnort2233@gmail.com

### :computer: Redes sociales
#### Tik Tok
[https://www.tiktok.com/@delys.north?_t=8q3iSZHu44D&_r=1](https://)


## Contribuciones

Si deseas contribuir al desarrollo de este proyecto, por favor sigue los lineamientos para crear un fork y enviar un pull request.
## ¡Gracias por visitarnos!

