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
      - Devolverá una lista de todos los hoteles registrados en la base de datos. Se incluye información acerca de su hotelCode, name, location y numberOfRooms. Las habitaciones que están en estado IsDeleted(Borrado lógico) no se incluirán en numberOfRoom y por consecuencia tampoco en availableRooms hasta que no se reactiven.
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
              
    - TraerHabitaciones: /agency/rooms
      - Devolverá una lista de todos las habitaciones registradas en la base de datos. Se incluye información acerca de su id, roomNumber, pricePerNight, roomType, isAvailable, isDeleted y hotelCode.
      - Si se quiere traer un hotel por su hoteCode solo es necesario añadir /{hotelcode}. Ejemplo agency/hotels/{hotelCode}

    - EliminarHabitacion: /agency/rooms/{roomNumber}/hotel/{hotelCode}
      - Si se elimina correctamente devuelve un mensaje: Room number {roomNumber} successfully deleted from hotel {hotelCode}
      - Si la habitación o el codigo del hotel no exite devuelve un mensaje: Sorry, room with number {roomNumer} not found in hotel {hotelCode}
      - Hay que tener en cuenta que la habitación no se borra fisicamente de la base de datos. Se quedara con isAvaiable en false y el isDeleted en true.
        
    - ReactivarHabitacion:
      - Si se reactiva correctamente devuelve un mensaje: Room number {roomNumber} in hotel {hotelCode} has been successfully reactivated.
      - Si la habitación o el codigo del hotel no exite devuelve un mensaje: Sorry, room with number {roomNumer} not found in hotel {hotelCode}
        
  - **Reservar habitacio en hotel**
    
    - CrearReserva: /agency/hotel-booking/new
      - Todos los datos se han introducido de forma correcta: Devuelve un mensaje con los datos de la reserva.
      - Todos los datos se han introducido de forma correcta pero no hay disponibilidad: Devuelve el mensaje Sorry, there are no rooms available for the selected dates and room type.
      - Se introduce mal el {hotelCode}: Devuelve el mensaje Hotel with code {hotelCode} not found.
      - Se introduce mal la localizacion: Devuelve el mensaje Sorry, the hotel's location does not match the one registered in the database for this hotel.
      - Se introduce mal el tipo de habitación: Devuelve el mensaje Unknown room type: {roomType} The allowed room types are: Individual. Double. Triple.
      - Si se introduce las fechas del Check-in y Check-out pero no concuerdan con el número de noches introducido: Devuelve el mensaje The number of nights does not match the dates provided. (Check-in/Check-Out). The correct number of nights is X. (Calcula el número correcto que deberías introducir según las fechas proporcionadas).
      - Si la habitación  es de tipo Double y se introducen tres personas, devuelve: The selected room does not have enough capacity for the number of guests.
        
    - TraerReservas: /agency/hotel-booking
      - Si hay reserva en la base de datos, devolverá una lista de todas las reservas con sus caracteristicas y el precio total.
      - Si no hay reservas en la base de datos devuelve el mensaje: No bookings found
        
    - EliminarReserva: /agency/hotel-booking/{bookingId}
      - Si existe la reserva en la base de datos, devuelve el mensaje: Reservation cancelled and the room is now available.
      - Si no encuentra la reserva (por ejemplo, porque se ha puesto mal el id) devuelve el mensaje: Sorry, reservation not found. Please check the details and try again.
  - **Creacion de vuelos**
    
    - CrearVuelo:/agency/flights
      - Si el vuelo no existe en la base de datos y se han introducido bien, los datos devuelve las características del vuelo creado.
      - Si el vuelo ya existe en la base de datos, devuelve el mensaje: The flight with flight code {flightCode} already exists.
    - TraerVuelos:
      - Si existen vuelos en la base de datos, traerá la lista de vuelos con sus características y asientos disponibles.
      - Si no hay vuelos en la base de datos, devuelve el mensaje: No flights found for the specified criteria.
    - EliminarVuelos:
      - Si el vuelo existe en la base de datos, devuelve el mensaje: The flight with flight code {flightCode} has been successfully deleted from the database.
      - Si se ha introducido mal el {flightCode} devuelve el mensaje: The flight with code {flightCode} does not exist.
        
  - **Reserva de vuelos**
    
    - CrearReservaDeUnVuelo:
    
      - Si todos los datos introducidos son correctos creara la reserva incluido el precio total de la reserva. Existen validacion para que el numberOfPassenger tenga que ser igual al numero de persona introducidas en la lista. El origin y el destination deben de coincidir con los datos del vuelo.
        
    - EliminarReservaDeUnVuelo:
      - Si el id de la reserva existe en la base de datos devuelve el mensaje: Your flight booking has been successfully deleted.
      - Si el id de la reserva no existe en la base de datos devuelve el mensaje: No flight booking found with id: {bookingId}

## Contacto

- **Nombre**: Germán Morales
- **Link del Proyecto**: [MoralesValverdeGerman_pruebatec4](https://github.com/Germanmv93/MoralesValverdeGerman_pruebatec4)

El programa funciona correctamente para mi, en caso de cualquier duda o problema puede contactarme de las siguientes formas:
  
- **Correo Electrónico**: [germanmv1993@gmail.com](mailto:germanmv1993@gmail.com)
- **LinkedIn**: [Perfil de LinkedIn](https://www.linkedin.com/in/germ%C3%A1n-morales-942100254/)
