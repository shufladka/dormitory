-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Время создания: Апр 27 2025 г., 15:52
-- Версия сервера: 10.4.32-MariaDB-log
-- Версия PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `dormitory`
--

DELIMITER $$
--
-- Процедуры
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `charge_rent_for_active_contracts` ()   BEGIN
	-- Обновляем балансы для активных контрактов (где status_id = 7)
    UPDATE balances b
    JOIN residents r ON r.balance_id = b.id
    JOIN residents_contracts rc ON rc.resident_id = r.id
    JOIN contracts c ON c.id = rc.contract_id
    SET b.amount = b.amount - c.rent_price
    WHERE c.status_id = 7 AND b.id IS NOT NULL;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Структура таблицы `accounts`
--

CREATE TABLE `accounts` (
  `id` int(8) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Дамп данных таблицы `accounts`
--

INSERT INTO `accounts` (`id`, `username`, `password`) VALUES
(9, 'qwetyu', '$2a$10$29yQ4DvdZCi6UoOm2W4gZ.mVDHJ8jjeQAxZu8VMuvGhxLHTe4NZEy'),
(10, '123123123', '$2a$10$PQ4gX80L8JQ8/QfCqYIxbOoyqe6pcY5qkO/klZ8ekj/g6lpMG2tUC'),
(11, '123123124', '$2a$10$ILsHvBKUrlPP7pcowlKQQu6.srUrHO1IBUidP3CmRttoqAZn55it6'),
(12, '123123121', '$2a$10$RiAvi8A6moEgw4vkFPBQleLe6yaKVSGzwxd/3QEcHDdZtka3kW6C2'),
(13, '1231231233', '$2a$10$vaPIkhBbw94Fy8.Jf8YUmeb41vnbyTsd6ReFnuOyOxlBzLNlbyYNO'),
(14, '111222333', '$2a$10$QUc0cSWFUvzxdblmDxd1OuBfeB0IzwU907bVSWeT3TEL3BUTUWj/2'),
(17, '12312312334', '$2a$10$4jPCpqwjuqOiatKGJ1meqeh1STQ9FbGiHNPsqBV4/UUC.Upcxw.f2'),
(18, '123123111', '$2a$10$/phBssOIefFob.m9Ja0uuOEq.4RxdZS3AS1G69VSjZSL30w4cu4hO'),
(21, 'qweqweqwe', '$2a$10$nnObj8aCFczzd2MZku0fGeU0FhCwYLTzfYdxg8BFsf0g1kmhbKSl.'),
(22, 'zxczxczxc', '$2a$10$cf..2gUJU4ewSPybv0Q4e./JUPt1U6GslCMQOS1F3WRJI0ZFWd58i'),
(23, 'user1', '$2a$10$JdCXQt2RvK2xfxoE2Gbdy.K7u6Mh7Lf.ZKme84iOWzMRg9chuTNkK');

-- --------------------------------------------------------

--
-- Структура таблицы `accounts_roles`
--

CREATE TABLE `accounts_roles` (
  `account_id` int(8) NOT NULL,
  `role_id` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='Триггер на удаление';

--
-- Дамп данных таблицы `accounts_roles`
--

INSERT INTO `accounts_roles` (`account_id`, `role_id`) VALUES
(9, 1),
(10, 2),
(10, 3),
(11, 1),
(21, 2),
(22, 2),
(22, 3),
(23, 2);

--
-- Триггеры `accounts_roles`
--
DELIMITER $$
CREATE TRIGGER `trg_accounts_roles_delete` AFTER DELETE ON `accounts_roles` FOR EACH ROW BEGIN
    DECLARE passportId INT;

    -- Получаем passportId для account_id
    SELECT id INTO passportId FROM passports WHERE account_id = OLD.account_id LIMIT 1;

    -- Удаляем запись из residents или employees в зависимости от role_id
    IF OLD.role_id = 2 THEN
        DELETE FROM residents WHERE passport_id = passportId;
    ELSEIF OLD.role_id = 3 THEN
        DELETE FROM employees WHERE passport_id = passportId;
    END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `trg_accounts_roles_insert` AFTER INSERT ON `accounts_roles` FOR EACH ROW BEGIN
    DECLARE passportId INT;
    DECLARE new_balance_id INT;

    -- Получаем passportId для account_id
    SELECT id INTO passportId FROM passports WHERE account_id = NEW.account_id LIMIT 1;

    -- Вставляем запись в таблицу residents или employees в зависимости от role_id
    IF NEW.role_id = 2 THEN
		-- Только для жильцов создаём баланс
        INSERT INTO balances (amount) VALUES (0);
        SET new_balance_id = LAST_INSERT_ID();
        
        INSERT INTO residents (passport_id, balance_id) VALUES (passportId, new_balance_id);
    ELSEIF NEW.role_id = 3 THEN
        INSERT INTO employees (passport_id) VALUES (passportId);
    END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `trg_accounts_roles_update` AFTER UPDATE ON `accounts_roles` FOR EACH ROW BEGIN
    DECLARE passportId INT;
    DECLARE new_balance_id INT;

    -- Получаем passportId для account_id
    SELECT id INTO passportId FROM passports WHERE account_id = NEW.account_id LIMIT 1;

    -- Проверяем, нужно ли вставить запись в residents или employees
    IF NEW.role_id = 2 THEN
		-- Только для жильцов создаём баланс
        INSERT INTO balances (amount) VALUES (0);
        SET new_balance_id = LAST_INSERT_ID();
        
        INSERT INTO residents (passport_id, balance_id) VALUES (passportId, new_balance_id);
    ELSEIF NEW.role_id = 3 THEN
        INSERT INTO employees (passport_id) VALUES (passportId);
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Структура таблицы `accounts_seq`
--

CREATE TABLE `accounts_seq` (
  `next_not_cached_value` bigint(21) NOT NULL,
  `minimum_value` bigint(21) NOT NULL,
  `maximum_value` bigint(21) NOT NULL,
  `start_value` bigint(21) NOT NULL COMMENT 'start value when sequences is created or value if RESTART is used',
  `increment` bigint(21) NOT NULL COMMENT 'increment value',
  `cache_size` bigint(21) UNSIGNED NOT NULL,
  `cycle_option` tinyint(1) UNSIGNED NOT NULL COMMENT '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
  `cycle_count` bigint(21) NOT NULL COMMENT 'How many cycles have been done'
) ENGINE=InnoDB;

--
-- Дамп данных таблицы `accounts_seq`
--

INSERT INTO `accounts_seq` (`next_not_cached_value`, `minimum_value`, `maximum_value`, `start_value`, `increment`, `cache_size`, `cycle_option`, `cycle_count`) VALUES
(1, 1, 9223372036854775806, 1, 50, 0, 0, 0);

-- --------------------------------------------------------

--
-- Структура таблицы `addresses`
--

CREATE TABLE `addresses` (
  `id` int(8) NOT NULL,
  `is_city` tinyint(1) NOT NULL,
  `settlement` varchar(30) NOT NULL,
  `street` varchar(60) NOT NULL,
  `building_number` int(4) NOT NULL,
  `building_index` varchar(255) DEFAULT NULL,
  `flat_number` int(4) DEFAULT NULL,
  `zip` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Дамп данных таблицы `addresses`
--

INSERT INTO `addresses` (`id`, `is_city`, `settlement`, `street`, `building_number`, `building_index`, `flat_number`, `zip`) VALUES
(1, 1, 'Витебск', 'Ленина', 10, '1234', 101, '123457'),
(2, 1, 'Минск', 'Советская', 15, '5678', 202, '654321'),
(3, 1, 'Слуцк', 'Лесная', 5, '0001', 103, '111222'),
(4, 1, 'Орша', 'Московская', 3, '4321', 204, '222333'),
(5, 1, 'Петриков', 'Центральная', 7, '1001', 105, '333444'),
(6, 1, 'Микашевичи', 'Ленина', 9, '9876', 106, '444555'),
(7, 0, 'Козловичи', 'Новая', 11, '3333', 107, '555666'),
(8, 1, 'Фаниполь', 'Шевченко', 8, '6666', 108, '666777'),
(9, 1, 'Минск', 'Сурганова', 12, '9996', 109, '777888'),
(10, 1, 'Брест', 'Пушкина', 14, '5555', 110, '888999'),
(11, 1, 'Слуцк', 'Ленина', 10, '1234', 101, '123457'),
(12, 1, 'Лида', 'Неизвестная', 10, '1234', 101, '123457'),
(13, 1, 'Минск', 'Шевченко', 10, '1234', 101, '123457'),
(14, 1, 'Гродно', 'Советская', 12, '9999', 109, '777888'),
(15, 0, 'Быков', 'Луговая', 12, '9999', 109, '777888'),
(16, 1, 'Солигорск', 'Козлова', 18, 'тест', 18, '223710'),
(17, 1, 'Солигорск', 'Козлова', 12, NULL, 12, '223710'),
(18, 1, 'Гомель', 'Советская', 1, NULL, 1, '22222');

-- --------------------------------------------------------

--
-- Структура таблицы `balances`
--

CREATE TABLE `balances` (
  `id` int(8) NOT NULL,
  `amount` decimal(8,2) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='update_balances_timestamp проставляет дату обновления записи';

--
-- Дамп данных таблицы `balances`
--

INSERT INTO `balances` (`id`, `amount`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 564.52, '2025-04-02 23:30:07', '2025-04-27 14:40:24', NULL),
(6, 0.00, '0000-00-00 00:00:00', NULL, NULL),
(7, 0.00, '2025-04-20 12:50:00', NULL, NULL),
(11, -100.00, '2025-04-20 13:12:08', NULL, '2025-04-20 13:34:15'),
(12, -528.00, '2025-04-20 13:34:15', '2025-04-20 14:30:04', NULL),
(15, 0.00, '2025-04-27 14:35:48', '2025-04-27 14:40:40', NULL);

-- --------------------------------------------------------

--
-- Структура таблицы `blocks`
--

CREATE TABLE `blocks` (
  `id` int(8) NOT NULL,
  `dormitory_id` int(4) NOT NULL,
  `number` int(2) NOT NULL,
  `is_gasified` tinyint(1) NOT NULL,
  `is_bathroom_separated` tinyint(1) NOT NULL,
  `capacity` int(2) NOT NULL,
  `floor` int(3) NOT NULL,
  `room_number` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Дамп данных таблицы `blocks`
--

INSERT INTO `blocks` (`id`, `dormitory_id`, `number`, `is_gasified`, `is_bathroom_separated`, `capacity`, `floor`, `room_number`) VALUES
(1, 1, 22, 0, 1, 2, 3, 0),
(2, 1, 21, 0, 1, 2, 3, 0);

-- --------------------------------------------------------

--
-- Структура таблицы `contacts`
--

CREATE TABLE `contacts` (
  `id` int(8) NOT NULL,
  `provider` varchar(20) NOT NULL,
  `code` varchar(10) NOT NULL,
  `phone_number` varchar(7) NOT NULL,
  `email` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Дамп данных таблицы `contacts`
--

INSERT INTO `contacts` (`id`, `provider`, `code`, `phone_number`, `email`) VALUES
(1, 'A1', '44', '7301654', 'olegolegolegoleg88@gmail.com'),
(2, 'A1', '44', '123456', 'test1@gmail.com'),
(3, 'A1', '29', '7301525', 'test2@gmail.com'),
(4, 'A1', '44', '123456', 'test3@gmail.com'),
(5, 'A1', '29', '1234567', 'test4@gmail.com'),
(6, 'A1', '33', '1234567', 'test5@gmail.com'),
(7, 'A1', '29', '1234567', 'test6@mail.com');

-- --------------------------------------------------------

--
-- Структура таблицы `contracts`
--

CREATE TABLE `contracts` (
  `id` int(8) NOT NULL,
  `block_id` int(8) NOT NULL,
  `status_id` int(3) NOT NULL,
  `rent_price` decimal(8,2) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='(AFTER INSERT) "Мягкое удаление" остальных активных контрактов текущего резидента при вставке status_id 7;(BEFORE UPDATE) "Мягкое удаление" остальных активных контрактов текущего резидента при обновлении status_id с null на 7. При обновлении status_id контракта на 8 происходит его "мягкое удаление" ;';

--
-- Дамп данных таблицы `contracts`
--

INSERT INTO `contracts` (`id`, `block_id`, `status_id`, `rent_price`, `created_at`, `updated_at`, `deleted_at`) VALUES
(24, 2, 8, NULL, '2025-04-07 23:37:52', '2025-04-08 00:04:33', '2025-04-08 00:04:33'),
(25, 1, 8, NULL, '2025-04-07 23:37:54', '2025-04-07 23:38:13', '2025-04-07 23:41:42'),
(26, 1, 8, NULL, '2025-04-07 23:37:58', '2025-04-07 23:38:14', '2025-04-07 23:41:42'),
(27, 2, 8, NULL, '2025-04-07 23:37:58', '2025-04-07 23:57:44', '2025-04-08 00:04:49'),
(28, 1, 8, NULL, '2025-04-07 23:39:10', '2025-04-07 23:39:19', '2025-04-07 23:41:42'),
(29, 1, 8, NULL, '2025-04-07 23:39:11', '2025-04-07 23:59:55', '2025-04-08 00:02:11'),
(30, 1, 8, NULL, '2025-04-07 23:57:31', '2025-04-08 00:03:57', '2025-04-08 00:03:57'),
(31, 1, 8, NULL, '2025-04-07 23:57:38', '2025-04-08 00:04:34', '2025-04-08 00:04:34'),
(32, 2, 8, NULL, '2025-04-08 00:05:03', '2025-04-08 00:09:14', '2025-04-08 00:09:14'),
(33, 2, 8, NULL, '2025-04-08 00:05:07', '2025-04-08 00:09:16', '2025-04-08 00:09:16'),
(34, 2, 8, NULL, '2025-04-08 00:05:30', '2025-04-08 00:05:35', '2025-04-08 00:07:20'),
(35, 2, 8, NULL, '2025-04-08 00:07:00', '2025-04-08 00:09:15', '2025-04-08 00:09:15'),
(36, 2, 8, NULL, '2025-04-08 00:09:22', '2025-04-08 00:11:24', '2025-04-08 00:11:24'),
(37, 2, 8, NULL, '2025-04-08 00:09:37', '2025-04-08 00:17:15', '2025-04-08 00:17:15'),
(38, 2, 8, NULL, '2025-04-08 00:09:48', '2025-04-08 00:26:18', '2025-04-08 00:26:18'),
(39, 2, 8, NULL, '2025-04-08 00:11:34', '2025-04-08 00:14:22', '2025-04-08 00:14:22'),
(40, 2, 8, NULL, '2025-04-08 00:14:28', '2025-04-08 00:14:47', '2025-04-08 00:14:47'),
(41, 2, 8, NULL, '2025-04-08 00:14:53', '2025-04-08 00:21:34', '2025-04-08 00:21:34'),
(42, 2, 8, NULL, '2025-04-08 00:17:25', '2025-04-08 00:21:34', '2025-04-08 00:21:34'),
(43, 2, 8, NULL, '2025-04-08 00:21:39', '2025-04-08 00:26:19', '2025-04-08 00:26:19'),
(44, 2, 8, NULL, '2025-04-08 00:21:49', '2025-04-08 00:49:36', '2025-04-08 00:49:36'),
(45, 2, 8, NULL, '2025-04-08 00:26:26', '2025-04-08 00:26:42', '2025-04-08 00:26:42'),
(46, 2, 8, NULL, '2025-04-08 00:26:30', '2025-04-08 00:52:00', '2025-04-08 00:52:00'),
(47, 2, 4, 200.00, '2025-04-08 00:43:46', NULL, '2025-04-08 00:47:53'),
(48, 1, 8, NULL, '2025-04-08 00:43:50', '2025-04-08 00:47:48', '2025-04-08 00:47:48'),
(49, 2, 8, NULL, '2025-04-08 00:47:55', '2025-04-08 01:31:18', '2025-04-08 01:31:18'),
(50, 2, 7, 205.00, '2025-04-08 00:51:44', '2025-04-08 00:52:00', NULL),
(51, 1, 7, 220.00, '2025-04-08 01:31:24', '2025-04-08 01:32:26', NULL),
(52, 1, 7, 210.00, '2025-04-08 01:34:32', '2025-04-08 01:34:41', NULL),
(53, 2, 8, 150.00, '2025-04-20 13:36:01', '2025-04-27 14:36:18', '2025-04-27 14:36:18'),
(54, 2, 8, NULL, '2025-04-27 14:36:00', '2025-04-27 14:38:31', '2025-04-27 14:38:31'),
(55, 2, 8, NULL, '2025-04-27 14:38:38', '2025-04-27 14:38:48', '2025-04-27 14:38:48'),
(56, 2, 7, 60.00, '2025-04-27 14:39:51', '2025-04-27 14:40:16', NULL);

--
-- Триггеры `contracts`
--
DELIMITER $$
CREATE TRIGGER `trg_insert_contract_status_before_update` BEFORE INSERT ON `contracts` FOR EACH ROW BEGIN
    DECLARE activeContracts INT;
    DECLARE blockCapacity INT;

    -- Логика для статуса 7 (Заселение)
    IF (NEW.status_id = 7) THEN
        -- Получаем вместимость блока
        SELECT capacity INTO blockCapacity
        FROM blocks
        WHERE id = NEW.block_id;

        -- Считаем количество активных контрактов с статусом 7 в этом блоке
        SELECT COUNT(*) INTO activeContracts
        FROM contracts
        WHERE status_id = 7
          AND block_id = NEW.block_id
          AND deleted_at IS NULL;

        -- Если количество активных контрактов превышает или равно вместимости блока
        IF activeContracts >= blockCapacity THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Capacity exceeded for block';
        END IF;
    END IF;

    -- Логика для статуса 8 (Выселен)
    IF (NEW.status_id = 8) THEN
        -- Обновляем deleted_at для контракта, если его статус изменился на 8 (Выселен)
        SET NEW.deleted_at = NOW();
    END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `trg_update_contract_status_before_update` BEFORE UPDATE ON `contracts` FOR EACH ROW BEGIN
    DECLARE activeContracts INT;
    DECLARE blockCapacity INT;

    -- Логика для статуса 7 (Заселение)
    IF (NEW.status_id = 7 AND (OLD.status_id IS NULL OR OLD.status_id <> NEW.status_id)) THEN
        -- Получаем вместимость блока
        SELECT capacity INTO blockCapacity
        FROM blocks
        WHERE id = NEW.block_id;

        -- Считаем количество активных контрактов с статусом 7 в этом блоке
        SELECT COUNT(*) INTO activeContracts
        FROM contracts
        WHERE status_id = 7
          AND block_id = NEW.block_id
          AND deleted_at IS NULL;

        -- Если количество активных контрактов превышает или равно вместимости блока
        IF activeContracts >= blockCapacity THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Capacity exceeded for block';
        END IF;
    END IF;

    -- Логика для статуса 8 (Выселен)
    IF (NEW.status_id = 8 AND OLD.status_id <> NEW.status_id) THEN
        -- Обновляем deleted_at для контракта, если его статус изменился на 8 (Выселен)
        SET NEW.deleted_at = NOW();
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Структура таблицы `databasechangelog`
--

CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Дамп данных таблицы `databasechangelog`
--

INSERT INTO `databasechangelog` (`ID`, `AUTHOR`, `FILENAME`, `DATEEXECUTED`, `ORDEREXECUTED`, `EXECTYPE`, `MD5SUM`, `DESCRIPTION`, `COMMENTS`, `TAG`, `LIQUIBASE`, `CONTEXTS`, `LABELS`, `DEPLOYMENT_ID`) VALUES
('1743712549083-42', 'klezo', 'db/changelog/03-02-changelog.xml', '2025-04-03 23:36:14', 1, 'EXECUTED', '9:8b4b8820ac184cb780bea44a74a89104', 'dropForeignKeyConstraint baseTableName=accounts_roles, constraintName=accounts_roles_ibfk_1', '', NULL, '4.23.1', NULL, NULL, '3712573994'),
('1743712549083-43', 'klezo', 'db/changelog/03-02-changelog.xml', '2025-04-03 23:36:14', 2, 'EXECUTED', '9:451cd8ac155cabaf7cde8fb42af22c6d', 'dropForeignKeyConstraint baseTableName=accounts_roles, constraintName=accounts_roles_ibfk_2', '', NULL, '4.23.1', NULL, NULL, '3712573994'),
('1743712549083-44', 'klezo', 'db/changelog/03-02-changelog.xml', '2025-04-03 23:36:14', 3, 'EXECUTED', '9:441aef9ec66713dfaf4095e9b5099358', 'dropForeignKeyConstraint baseTableName=residents_contracts, constraintName=residents_contracts_ibfk_1', '', NULL, '4.23.1', NULL, NULL, '3712573994'),
('1743712549083-45', 'klezo', 'db/changelog/03-02-changelog.xml', '2025-04-03 23:36:14', 4, 'EXECUTED', '9:add53f84f128b0415624ff291a0a75df', 'dropForeignKeyConstraint baseTableName=residents_contracts, constraintName=residents_contracts_ibfk_2', '', NULL, '4.23.1', NULL, NULL, '3712573994');

-- --------------------------------------------------------

--
-- Структура таблицы `databasechangeloglock`
--

CREATE TABLE `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Дамп данных таблицы `databasechangeloglock`
--

INSERT INTO `databasechangeloglock` (`ID`, `LOCKED`, `LOCKGRANTED`, `LOCKEDBY`) VALUES
(1, b'0', NULL, NULL);

-- --------------------------------------------------------

--
-- Структура таблицы `dormitories`
--

CREATE TABLE `dormitories` (
  `id` int(4) NOT NULL,
  `address_id` int(8) DEFAULT NULL,
  `dormitory_type_id` int(4) DEFAULT NULL,
  `floors` int(2) NOT NULL,
  `blocks` int(5) NOT NULL,
  `has_lift` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Дамп данных таблицы `dormitories`
--

INSERT INTO `dormitories` (`id`, `address_id`, `dormitory_type_id`, `floors`, `blocks`, `has_lift`) VALUES
(1, 1, 4, 5, 21, 1);

-- --------------------------------------------------------

--
-- Структура таблицы `dormitory_types`
--

CREATE TABLE `dormitory_types` (
  `id` int(4) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Дамп данных таблицы `dormitory_types`
--

INSERT INTO `dormitory_types` (`id`, `name`) VALUES
(4, 'Студенческое'),
(5, 'Семейное');

-- --------------------------------------------------------

--
-- Структура таблицы `employees`
--

CREATE TABLE `employees` (
  `id` int(8) NOT NULL,
  `passport_id` int(8) NOT NULL,
  `position_id` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Дамп данных таблицы `employees`
--

INSERT INTO `employees` (`id`, `passport_id`, `position_id`) VALUES
(8, 6, NULL),
(13, 10, NULL);

-- --------------------------------------------------------

--
-- Дублирующая структура для представления `full_name`
-- (См. Ниже фактическое представление)
--
CREATE TABLE `full_name` (
`name` varchar(30)
,`surname` varchar(40)
,`patronymic` varchar(20)
);

-- --------------------------------------------------------

--
-- Структура таблицы `logs`
--

CREATE TABLE `logs` (
  `id` int(12) NOT NULL,
  `table_name` varchar(40) NOT NULL,
  `action` varchar(20) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Дамп данных таблицы `logs`
--

INSERT INTO `logs` (`id`, `table_name`, `action`, `created_at`) VALUES
(1, 'test', 'test', '2025-04-19 18:51:26'),
(2, 'contracts', 'UPDATE', '2025-04-19 18:58:13'),
(3, 'contracts', 'UPDATE', '2025-04-20 12:39:52'),
(4, 'contracts', 'UPDATE', '2025-04-20 12:39:52'),
(5, 'contracts', 'UPDATE', '2025-04-20 12:39:52'),
(6, 'contracts', 'UPDATE', '2025-04-20 12:39:52'),
(7, 'contracts', 'INSERT', '2025-04-20 13:36:01'),
(8, 'contracts', 'UPDATE', '2025-04-20 13:36:11'),
(9, 'contracts', 'UPDATE', '2025-04-20 13:43:43');

-- --------------------------------------------------------

--
-- Структура таблицы `passports`
--

CREATE TABLE `passports` (
  `id` int(8) NOT NULL,
  `name` varchar(30) NOT NULL,
  `surname` varchar(40) NOT NULL,
  `patronymic` varchar(20) DEFAULT NULL,
  `birth_date` date NOT NULL,
  `address_id` int(8) DEFAULT NULL,
  `contact_id` int(8) DEFAULT NULL,
  `account_id` int(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Дамп данных таблицы `passports`
--

INSERT INTO `passports` (`id`, `name`, `surname`, `patronymic`, `birth_date`, `address_id`, `contact_id`, `account_id`) VALUES
(4, 'Иван', 'Иванов', 'Иванович', '2002-01-14', NULL, NULL, NULL),
(5, 'Иван', 'Иванов', 'Иванович', '2001-11-19', NULL, NULL, NULL),
(6, 'Иван', 'Иванов', 'Иванович', '2011-11-11', 9, 7, 10),
(7, 'Иван', 'Иванов', 'Иванович', '2011-11-11', NULL, NULL, NULL),
(8, 'Иван', 'Иванов', 'Иванович', '2011-11-11', NULL, NULL, NULL),
(9, 'Петр', 'Петров', 'Петрович', '2022-12-22', 17, NULL, 21),
(10, 'qwe', 'qwe', 'qwe', '1111-11-11', 18, NULL, 22),
(11, 'Станислав', 'Кулага', 'Михайлович', '2001-05-12', NULL, NULL, 23);

-- --------------------------------------------------------

--
-- Структура таблицы `payments`
--

CREATE TABLE `payments` (
  `id` int(8) NOT NULL,
  `balance_id` int(8) NOT NULL,
  `amount` decimal(8,2) NOT NULL,
  `bank_name` varchar(30) NOT NULL,
  `code` varchar(40) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Дамп данных таблицы `payments`
--

INSERT INTO `payments` (`id`, `balance_id`, `amount`, `bank_name`, `code`, `created_at`, `deleted_at`) VALUES
(1, 1, 400.00, 'PRIOR', '1234', '2025-04-20 15:32:41', NULL),
(4, 1, 200.00, 'PRIOR', '1231', '2025-04-27 13:57:54', NULL),
(5, 1, 1.00, 'PRIOR', '1234EE', '2025-04-27 14:02:32', NULL),
(6, 1, 1.00, 'PRIOR', '1231RR', '2025-04-27 14:02:37', NULL),
(7, 1, 1.00, 'PRIOR', 'TEST1', '2025-04-27 14:03:27', NULL),
(8, 1, 1.00, 'PRIOR', '1234EE', '2025-04-27 14:16:34', NULL),
(9, 15, 60.00, 'PRIOR', '1245EER1', '2025-04-27 14:40:40', NULL);

--
-- Триггеры `payments`
--
DELIMITER $$
CREATE TRIGGER `update_balance_after_payment` AFTER INSERT ON `payments` FOR EACH ROW BEGIN
	-- Увеличиваем значение поля amount в таблице balances
    UPDATE balances b
    SET b.amount = b.amount + NEW.amount
    WHERE b.id = NEW.balance_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Структура таблицы `positions`
--

CREATE TABLE `positions` (
  `id` int(5) NOT NULL,
  `name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Дамп данных таблицы `positions`
--

INSERT INTO `positions` (`id`, `name`) VALUES
(1, 'Администратор'),
(2, 'Охранник');

-- --------------------------------------------------------

--
-- Структура таблицы `residents`
--

CREATE TABLE `residents` (
  `id` int(8) NOT NULL,
  `passport_id` int(8) NOT NULL,
  `balance_id` int(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='Триггер before_delete_resident проставляет текущую дату и время в поле deleted_at в записи balances перед удалением записи в residents.';

--
-- Дамп данных таблицы `residents`
--

INSERT INTO `residents` (`id`, `passport_id`, `balance_id`) VALUES
(3, 4, NULL),
(10, 6, 1),
(20, 9, NULL),
(30, 10, 12),
(31, 11, 15);

--
-- Триггеры `residents`
--
DELIMITER $$
CREATE TRIGGER `before_delete_resident` BEFORE DELETE ON `residents` FOR EACH ROW BEGIN
    UPDATE balances
    SET deleted_at = NOW()
    WHERE id = OLD.balance_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Структура таблицы `residents_contracts`
--

CREATE TABLE `residents_contracts` (
  `resident_id` int(8) NOT NULL,
  `contract_id` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Дамп данных таблицы `residents_contracts`
--

INSERT INTO `residents_contracts` (`resident_id`, `contract_id`) VALUES
(10, 51),
(30, 53),
(31, 56);

-- --------------------------------------------------------

--
-- Структура таблицы `roles`
--

CREATE TABLE `roles` (
  `id` int(5) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Дамп данных таблицы `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'Тест'),
(2, 'Проживающий'),
(3, 'Работник');

-- --------------------------------------------------------

--
-- Структура таблицы `statuses`
--

CREATE TABLE `statuses` (
  `id` int(3) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Дамп данных таблицы `statuses`
--

INSERT INTO `statuses` (`id`, `name`) VALUES
(1, 'Создано'),
(2, 'В работе'),
(3, 'Обработан'),
(4, 'Ошибка'),
(5, 'Готово'),
(6, 'В очереди'),
(7, 'Заселение'),
(8, 'Выселен');

-- --------------------------------------------------------

--
-- Структура для представления `full_name`
--
DROP TABLE IF EXISTS `full_name`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `full_name`  AS SELECT `passports`.`name` AS `name`, `passports`.`surname` AS `surname`, `passports`.`patronymic` AS `patronymic` FROM `passports` ;

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKk8h1bgqoplx0rkngj01pm1rgp` (`username`);

--
-- Индексы таблицы `accounts_roles`
--
ALTER TABLE `accounts_roles`
  ADD PRIMARY KEY (`account_id`,`role_id`),
  ADD KEY `FKpwest19ib22ux5gk54esw9qve` (`role_id`);

--
-- Индексы таблицы `addresses`
--
ALTER TABLE `addresses`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `balances`
--
ALTER TABLE `balances`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `blocks`
--
ALTER TABLE `blocks`
  ADD PRIMARY KEY (`id`),
  ADD KEY `dormitory_id` (`dormitory_id`);

--
-- Индексы таблицы `contacts`
--
ALTER TABLE `contacts`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `contracts`
--
ALTER TABLE `contracts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `block_id` (`block_id`),
  ADD KEY `status_id` (`status_id`);

--
-- Индексы таблицы `databasechangeloglock`
--
ALTER TABLE `databasechangeloglock`
  ADD PRIMARY KEY (`ID`);

--
-- Индексы таблицы `dormitories`
--
ALTER TABLE `dormitories`
  ADD PRIMARY KEY (`id`),
  ADD KEY `address_id` (`address_id`),
  ADD KEY `dormitory_type_id` (`dormitory_type_id`);

--
-- Индексы таблицы `dormitory_types`
--
ALTER TABLE `dormitory_types`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`id`),
  ADD KEY `passport_id` (`passport_id`),
  ADD KEY `position_id` (`position_id`);

--
-- Индексы таблицы `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `passports`
--
ALTER TABLE `passports`
  ADD PRIMARY KEY (`id`),
  ADD KEY `address_id` (`address_id`),
  ADD KEY `contact_id` (`contact_id`),
  ADD KEY `account_id` (`account_id`);

--
-- Индексы таблицы `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `balance_id` (`balance_id`);

--
-- Индексы таблицы `positions`
--
ALTER TABLE `positions`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `residents`
--
ALTER TABLE `residents`
  ADD PRIMARY KEY (`id`),
  ADD KEY `passport_id` (`passport_id`),
  ADD KEY `balance_id` (`balance_id`);

--
-- Индексы таблицы `residents_contracts`
--
ALTER TABLE `residents_contracts`
  ADD PRIMARY KEY (`resident_id`,`contract_id`),
  ADD KEY `FKr6o1blenm50jjkfkjq3bglc4` (`contract_id`);

--
-- Индексы таблицы `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `statuses`
--
ALTER TABLE `statuses`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `accounts`
--
ALTER TABLE `accounts`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT для таблицы `addresses`
--
ALTER TABLE `addresses`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT для таблицы `balances`
--
ALTER TABLE `balances`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT для таблицы `blocks`
--
ALTER TABLE `blocks`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT для таблицы `contacts`
--
ALTER TABLE `contacts`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT для таблицы `contracts`
--
ALTER TABLE `contracts`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT для таблицы `dormitories`
--
ALTER TABLE `dormitories`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT для таблицы `dormitory_types`
--
ALTER TABLE `dormitory_types`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT для таблицы `employees`
--
ALTER TABLE `employees`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT для таблицы `logs`
--
ALTER TABLE `logs`
  MODIFY `id` int(12) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT для таблицы `passports`
--
ALTER TABLE `passports`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT для таблицы `payments`
--
ALTER TABLE `payments`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT для таблицы `positions`
--
ALTER TABLE `positions`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT для таблицы `residents`
--
ALTER TABLE `residents`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT для таблицы `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT для таблицы `statuses`
--
ALTER TABLE `statuses`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `accounts_roles`
--
ALTER TABLE `accounts_roles`
  ADD CONSTRAINT `FKpwest19ib22ux5gk54esw9qve` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  ADD CONSTRAINT `FKt44duw96d6v8xrapfo4ff2up6` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`);

--
-- Ограничения внешнего ключа таблицы `blocks`
--
ALTER TABLE `blocks`
  ADD CONSTRAINT `blocks_ibfk_1` FOREIGN KEY (`dormitory_id`) REFERENCES `dormitories` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `contracts`
--
ALTER TABLE `contracts`
  ADD CONSTRAINT `FKiu0b7ttfqkvh6ggx8pmxhpbe0` FOREIGN KEY (`block_id`) REFERENCES `blocks` (`id`),
  ADD CONSTRAINT `FKp50lh3b6mebn8nwofv9du9vcm` FOREIGN KEY (`status_id`) REFERENCES `statuses` (`id`);

--
-- Ограничения внешнего ключа таблицы `dormitories`
--
ALTER TABLE `dormitories`
  ADD CONSTRAINT `dormitories_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `dormitories_ibfk_2` FOREIGN KEY (`dormitory_type_id`) REFERENCES `dormitory_types` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `employees`
--
ALTER TABLE `employees`
  ADD CONSTRAINT `employees_ibfk_1` FOREIGN KEY (`passport_id`) REFERENCES `passports` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `employees_ibfk_2` FOREIGN KEY (`position_id`) REFERENCES `positions` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `passports`
--
ALTER TABLE `passports`
  ADD CONSTRAINT `passports_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `passports_ibfk_2` FOREIGN KEY (`contact_id`) REFERENCES `contacts` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `passports_ibfk_3` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `FK9fwfpys35c15ejo2nisdyscr7` FOREIGN KEY (`balance_id`) REFERENCES `balances` (`id`);

--
-- Ограничения внешнего ключа таблицы `residents`
--
ALTER TABLE `residents`
  ADD CONSTRAINT `residents_ibfk_1` FOREIGN KEY (`passport_id`) REFERENCES `passports` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `residents_ibfk_2` FOREIGN KEY (`balance_id`) REFERENCES `balances` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `residents_contracts`
--
ALTER TABLE `residents_contracts`
  ADD CONSTRAINT `FK4o58asdvll0lffwe6586rfw6m` FOREIGN KEY (`resident_id`) REFERENCES `residents` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FKr6o1blenm50jjkfkjq3bglc4` FOREIGN KEY (`contract_id`) REFERENCES `contracts` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
