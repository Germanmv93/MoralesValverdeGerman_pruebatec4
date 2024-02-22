-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-02-2024 a las 10:47:45
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `pruebatec4`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `booking_hotel`
--

CREATE TABLE `booking_hotel` (
  `id` bigint(20) NOT NULL,
  `check_in` date DEFAULT NULL,
  `check_out` date DEFAULT NULL,
  `hotel_code` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `nights` int(11) NOT NULL,
  `number_of_guest` int(11) NOT NULL,
  `total_price` double DEFAULT NULL,
  `room_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `booking_hotel`
--

INSERT INTO `booking_hotel` (`id`, `check_in`, `check_out`, `hotel_code`, `location`, `nights`, `number_of_guest`, `total_price`, `room_id`) VALUES
(1, '2024-01-01', '2024-01-02', 'H10', 'Cordoba', 1, 3, 60, 38),
(2, '2024-01-02', '2024-01-08', 'H10', 'Cordoba', 6, 3, 360, 37);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight`
--

CREATE TABLE `flight` (
  `flight_code` varchar(255) NOT NULL,
  `date` date DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `price_per_seat` double DEFAULT NULL,
  `seat_type` varchar(255) DEFAULT NULL,
  `seats` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `flight`
--

INSERT INTO `flight` (`flight_code`, `date`, `destination`, `origin`, `price_per_seat`, `seat_type`, `seats`) VALUES
('Air-45', '2024-04-15', 'Paris', 'Madrid', 85, 'Economy', 20);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight_booking`
--

CREATE TABLE `flight_booking` (
  `id` bigint(20) NOT NULL,
  `number_of_passenger` int(11) NOT NULL,
  `total_price` double DEFAULT NULL,
  `flight_code` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `flight_booking`
--

INSERT INTO `flight_booking` (`id`, `number_of_passenger`, `total_price`, `flight_code`) VALUES
(2, 2, 170, 'Air-45'),
(3, 2, 170, 'Air-45');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hotel`
--

CREATE TABLE `hotel` (
  `hotel_code` varchar(255) NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `hotel`
--

INSERT INTO `hotel` (`hotel_code`, `location`, `name`) VALUES
('ARTS001', 'Barcelona', 'Hotel Arts Barcelona'),
('CADIZPAR001', 'Cádiz', 'Parador de Cádiz'),
('EU-1', 'Cordoba', 'Eurostars Azahar'),
('GHM', 'Málaga', 'Gran Hotel Miramar'),
('GRANPAR001', 'Granada', 'Parador de Granada'),
('H10', 'Cordoba', 'H10 Palacio Colomera'),
('HM', 'Madrid', 'Hotel Mayorazgo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `passenger`
--

CREATE TABLE `passenger` (
  `dni` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sur_name` varchar(255) DEFAULT NULL,
  `booking_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `passenger`
--

INSERT INTO `passenger` (`dni`, `name`, `sur_name`, `booking_id`) VALUES
('126A', 'Juan', 'Pérez', 2),
('25688556A', 'Germán', 'Morales', 3),
('6564212B', 'Sandra', 'García', 3),
('871B', 'Ana', 'García', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `room`
--

CREATE TABLE `room` (
  `id` bigint(20) NOT NULL,
  `capacity` int(11) NOT NULL,
  `is_available` bit(1) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `price_per_night` double DEFAULT NULL,
  `room_number` varchar(255) DEFAULT NULL,
  `room_type` varchar(255) DEFAULT NULL,
  `hotel_hotel_code` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `room`
--

INSERT INTO `room` (`id`, `capacity`, `is_available`, `is_deleted`, `price_per_night`, `room_number`, `room_type`, `hotel_hotel_code`) VALUES
(1, 2, b'1', b'0', 150, '101', 'Double', 'ARTS001'),
(2, 2, b'1', b'0', 150, '102', 'Double', 'ARTS001'),
(3, 2, b'1', b'0', 150, '103', 'Double', 'ARTS001'),
(4, 2, b'1', b'0', 150, '104', 'Double', 'ARTS001'),
(5, 3, b'1', b'0', 150, '105', 'Triple', 'ARTS001'),
(6, 3, b'1', b'0', 150, '106', 'Triple', 'ARTS001'),
(7, 2, b'1', b'0', 190, '101', 'Double', 'CADIZPAR001'),
(8, 2, b'1', b'0', 190, '102', 'Double', 'CADIZPAR001'),
(9, 2, b'1', b'0', 190, '103', 'Double', 'CADIZPAR001'),
(10, 2, b'1', b'0', 190, '104', 'Double', 'CADIZPAR001'),
(11, 2, b'1', b'0', 190, '105', 'Double', 'CADIZPAR001'),
(12, 3, b'1', b'0', 250, '106', 'Triple', 'CADIZPAR001'),
(13, 3, b'1', b'0', 250, '107', 'Triple', 'CADIZPAR001'),
(14, 3, b'1', b'0', 80, '106', 'Triple', 'EU-1'),
(15, 3, b'1', b'0', 80, '105', 'Triple', 'EU-1'),
(16, 2, b'1', b'0', 50, '102', 'Double', 'EU-1'),
(17, 2, b'1', b'0', 50, '103', 'Double', 'EU-1'),
(18, 2, b'1', b'0', 50, '104', 'Double', 'EU-1'),
(19, 2, b'1', b'0', 70, '101', 'Double', 'GHM'),
(20, 2, b'1', b'0', 70, '102', 'Double', 'GHM'),
(21, 2, b'1', b'0', 70, '103', 'Double', 'GHM'),
(22, 2, b'1', b'0', 70, '104', 'Double', 'GHM'),
(23, 2, b'1', b'0', 70, '105', 'Double', 'GHM'),
(24, 3, b'1', b'0', 100, '106', 'Triple', 'GHM'),
(25, 3, b'1', b'0', 100, '107', 'Triple', 'GHM'),
(26, 2, b'1', b'0', 100, '101', 'Double', 'GRANPAR001'),
(27, 2, b'1', b'0', 100, '102', 'Double', 'GRANPAR001'),
(28, 2, b'1', b'0', 100, '103', 'Double', 'GRANPAR001'),
(29, 2, b'1', b'0', 100, '104', 'Double', 'GRANPAR001'),
(30, 3, b'1', b'0', 125, '105', 'Triple', 'GRANPAR001'),
(31, 3, b'1', b'0', 125, '106', 'Triple', 'GRANPAR001'),
(32, 2, b'1', b'0', 45, '101', 'Double', 'H10'),
(33, 2, b'1', b'0', 45, '102', 'Double', 'H10'),
(34, 2, b'1', b'0', 45, '103', 'Double', 'H10'),
(35, 2, b'1', b'0', 45, '104', 'Double', 'H10'),
(36, 2, b'1', b'0', 45, '105', 'Double', 'H10'),
(37, 3, b'1', b'0', 60, '106', 'Triple', 'H10'),
(38, 3, b'1', b'0', 60, '107', 'Triple', 'H10'),
(39, 2, b'1', b'0', 250, '101', 'Double', 'HM'),
(40, 2, b'1', b'0', 250, '102', 'Double', 'HM'),
(41, 2, b'1', b'0', 250, '103', 'Double', 'HM'),
(42, 2, b'1', b'0', 250, '104', 'Double', 'HM'),
(43, 3, b'1', b'0', 300, '105', 'Triple', 'HM'),
(44, 3, b'1', b'0', 300, '106', 'Triple', 'HM'),
(45, 1, b'1', b'0', 30, '201', 'Individual', 'HM'),
(46, 1, b'1', b'0', 30, '201', 'Individual', 'H10'),
(47, 1, b'1', b'0', 30, '201', 'Individual', 'GRANPAR001'),
(48, 1, b'1', b'0', 50, '201', 'Individual', 'GHM');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `booking_hotel`
--
ALTER TABLE `booking_hotel`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKn7cee3royjc6c085ph8galb1f` (`room_id`);

--
-- Indices de la tabla `flight`
--
ALTER TABLE `flight`
  ADD PRIMARY KEY (`flight_code`);

--
-- Indices de la tabla `flight_booking`
--
ALTER TABLE `flight_booking`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8bcont35346oho228l9wvoht0` (`flight_code`);

--
-- Indices de la tabla `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`hotel_code`);

--
-- Indices de la tabla `passenger`
--
ALTER TABLE `passenger`
  ADD PRIMARY KEY (`dni`),
  ADD KEY `FK2p9uul0hm80q3ey0nyp5ggppv` (`booking_id`);

--
-- Indices de la tabla `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK91y61n4vrxejxaxd086mwqb84` (`hotel_hotel_code`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `booking_hotel`
--
ALTER TABLE `booking_hotel`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `flight_booking`
--
ALTER TABLE `flight_booking`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `room`
--
ALTER TABLE `room`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `booking_hotel`
--
ALTER TABLE `booking_hotel`
  ADD CONSTRAINT `FKn7cee3royjc6c085ph8galb1f` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`);

--
-- Filtros para la tabla `flight_booking`
--
ALTER TABLE `flight_booking`
  ADD CONSTRAINT `FK8bcont35346oho228l9wvoht0` FOREIGN KEY (`flight_code`) REFERENCES `flight` (`flight_code`);

--
-- Filtros para la tabla `passenger`
--
ALTER TABLE `passenger`
  ADD CONSTRAINT `FK2p9uul0hm80q3ey0nyp5ggppv` FOREIGN KEY (`booking_id`) REFERENCES `flight_booking` (`id`);

--
-- Filtros para la tabla `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `FK91y61n4vrxejxaxd086mwqb84` FOREIGN KEY (`hotel_hotel_code`) REFERENCES `hotel` (`hotel_code`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
