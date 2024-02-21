![Hack a Boss Logo](https://d92mrp7hetgfk.cloudfront.net/images/sites/misc/Hack_a_Boss/original.png?1623097457)

# Prueba Técnica Numero Cuatro.

El objetivo de este desafío es aplicar los contenidos dados hasta el momento durante el BOOTCAMP (Git, Java, Spring Boot, Testing, JPA + Hibernate, Spring Security y JWT) en la implementación de una API REST a partir de un enunciado propuesto, una especificación de requisitos técnico-funcionales y documentación anexada.

## Requisistos Tecnicos:
  - IntelliJ.
  - Oracle OpenJDK 17.
  - Xampp PhpMyAdmin (Base da datos con nombre "pruebatec4".

## Archivos proporcionados:
  - Proyecto pruebatec4.
  - Base de datos pruebatec4.sql
  - Archivo colección de Postman.

## Instalación y uso del proyecto:

Para lanzar el proyecto se deben seguir los siguientes pasos:

1. Clonar o descargar este repositorio.
2. Importarlo en el IDE IntelliJ.
3. Realizar un Clean and build del proyecto para asegurarse de que todas las dependencias se descarguen e instalen correctamente.
4. Crear la base de datos "pruebatec4" en PhpMyAdmin o subir el archivo pruebatec4.sql que encontrara aquí. (Poner Url de la base de datos.)
5. Lanzar el proyecto con el botón correspondiente del IDE.

## Postman

  - **Hotel**
    - CrearHotel: /agency/hotels/new
      - Se creará un hotel con los campos hotelcode, name, location. Si el hotel ya existe, manejará una excepción personalizada devolviendo el mensaje "Hotel already exists." en caso contrario creará el hotel con los datos especificados y devolverá el mensaje "Hotel created successfully.".
    - TraerHotel: agency/hotels
      - Devolverá una lista de todos los hoteles registrados en la base de datos. Se incluye información acerca de su hotelCode, name, location, numberOfRooms, availableRooms. Las habitaciones que están en estado IsDeleted(Borrado lógico) no se incluirán en numberOfRoom y por consecuencia tampoco en availableRooms hasta que no se reactiven.
      - Si se quiere traer un hotel por su hoteCode solo es necesario añadir /{hotelcode}. Ejemplo agency/hotels/{hotelCode}
    - BorrarHotel: /agency/hotels/delete/{hotelCode}
      - Al indicar el hotelCode se eliminará de forma definitiva el hotel de la base de datos, si este hotel contiene habitaciones con reservas asignadas primero deberán eliminarse las reservas para poder eliminar el hotel. Si el hotel se elimina, devuelve el siguiente mensaje "The hotel {hotelCode} has been successfully deleted." Si el hotel que se intenta eliminar no existe, devuelve el mensaje "Hotel not found with code: {hotelCode}".
    - ModificarHotel: /agency/hotels/{hotelCode}
      - Se contemplan  los siguientes casos. Los ejemplos del Json podrian ser:
        - Caso 1: { "name":"Hotel Cordoba", "location": "Cordoba" } Modifica tanto el nombre como la localización del hoteCode introducido.
        - Caso 2: { "name":"Hotel Cordoba" } Modifica el nombre del hoteCode introducido.
        - Caso 3: { "location": "Cordoba" } Modifica la localización del hoteCode introducido.
    
  - **Habitación**
    - CrearHabitacion: /agency/rooms/new
        -Para la creación de habitaciones tenemos varios aspectos/casos a considerar:
        - La habitación no existe en el hotel y todos los datos están correctos: Devuelve los datos de la habitación creada.
        - La habitación ya existe en el hotel: Devuelve un mensaje "Room with number {roomNumber} already exists in hotel {hotelCode}."
        - Introducción errónea del tipo de habitación: Devuelve el mensaje
            
                    Unknown room type: {roomType}. 
                    The allowed room types are:
                    -Individual.
                    -Double.
                    -Triple.
              
        - El hotelCode introducido no existe en la base de datos: Devuelve el mensaje Hotel not found with code: {hotelCode}
              
    - TraerHabitaciones(Incluir luego por id tambien)
    - EliminarHabitacion
    - TraerHabitacionPorId
    - ReactivarHabitacion

## Contacto

- **Nombre**: Germán Morales
- **Link del Proyecto**: [MoralesValverdeGerman_pruebatec2](https://github.com/Germanmv93/MoralesValverdeGerman_pruebatec2)

El programa funciona correctamente para mi, en caso de cualquier duda o problema puede contactarme de las siguientes formas:
  
- **Correo Electrónico**: [germanmv1993@gmail.com](mailto:germanmv1993@gmail.com)
- **LinkedIn**: [Perfil de LinkedIn](https://www.linkedin.com/in/germ%C3%A1n-morales-942100254/)

