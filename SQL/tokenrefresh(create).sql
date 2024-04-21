CREATE TABLE `tokenrefresh` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiration` varchar(255) DEFAULT NULL,
  `refresh` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
