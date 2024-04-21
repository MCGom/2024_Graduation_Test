CREATE TABLE `user` (
  `email` varchar(200) NOT NULL,
  `username` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `nickname` varchar(200) DEFAULT NULL,
  `addr` varchar(400) DEFAULT NULL,
  `birth` datetime(6) DEFAULT NULL,
  `phone` varchar(150) DEFAULT NULL,
  `profile_img` varchar(300) DEFAULT NULL,
  `gender` int DEFAULT NULL,
  `genre1` varchar(100) DEFAULT NULL,
  `genre2` varchar(100) DEFAULT NULL,
  `genre3` varchar(100) DEFAULT NULL,
  `is_suspended` varchar(255) DEFAULT NULL,
  `is_secessioned` varchar(255) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
