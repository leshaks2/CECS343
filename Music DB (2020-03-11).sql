-- This is the basic structure we need to start the phase 1 implementation
-- You can simple open in MySQL and run to create the table structures
-- For the songs I included a column to save a music file and a column for a location to provide flexibility


CREATE TABLE `user_login` (
  `login_name` varchar(100) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`login_name`),
  UNIQUE KEY `login_name_UNIQUE` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `genres` (
  `genre_name` varchar(30) NOT NULL,
  `genre_id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`genre_id`),
  UNIQUE KEY `genre_name_UNIQUE` (`genre_name`),
  UNIQUE KEY `genre_id_UNIQUE` (`genre_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `songs` (
  `song_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `artist` varchar(100) NOT NULL,
  `genre` int NOT NULL,
  `album` varchar(45) NOT NULL,
  `song_file` blob,
  `song_location` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`song_id`),
  UNIQUE KEY `song_id_UNIQUE` (`song_id`),
  KEY `song_genre_fk_01_idx` (`genre`),
  CONSTRAINT `song_genre_fk_01` FOREIGN KEY (`genre`) REFERENCES `genres` (`genre_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_favorites` (
  `login_name` varchar(100) NOT NULL,
  `song_id` int NOT NULL,
  PRIMARY KEY (`login_name`,`song_id`),
  KEY `user_favorites_songs_fk_01_idx` (`song_id`),
  CONSTRAINT `user_favorites_songs_fk_01` FOREIGN KEY (`song_id`) REFERENCES `songs` (`song_id`),
  CONSTRAINT `user_favorites_user_login_fk_01` FOREIGN KEY (`login_name`) REFERENCES `user_login` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
