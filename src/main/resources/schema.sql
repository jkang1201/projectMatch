SET FOREIGN_KEY_CHECKS=0;
-- DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS position;
DROP TABLE IF EXISTS technology_stack_category;
DROP TABLE IF EXISTS technology_stack;
DROP TABLE IF EXISTS trust_grade;
DROP TABLE IF EXISTS project_member_auth;
DROP TABLE IF EXISTS trust_score_type;

-- CREATE TABLE `user` (
--     `user_id` bigint NOT NULL,
--     `create_date` datetime(6) DEFAULT NULL,
--     `update_date` datetime(6) DEFAULT NULL,
--     `email` varchar(255) DEFAULT NULL,
--     `intro` varchar(255) DEFAULT NULL,
--     `nickname` varchar(255) DEFAULT NULL,
--     `password` varchar(255) DEFAULT NULL,
--     `profile_img_src` varchar(255) DEFAULT NULL,
--     `role` varchar(255) DEFAULT NULL,
--     `position_id` bigint DEFAULT NULL,
--     PRIMARY KEY (`user_id`),
--     FOREIGN KEY (`position_id`) REFERENCES `position` (`position_id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `position` (
    `position_id` bigint NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`position_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `technology_stack_category` (
     `technology_stack_category_id` bigint NOT NULL,
     `name` varchar(255) DEFAULT NULL,
     PRIMARY KEY (`technology_stack_category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `technology_stack` (
    `technology_stack_id` bigint NOT NULL,
    `technology_stack_category_id` bigint DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`technology_stack_id`),
    FOREIGN KEY (`technology_stack_category_id`) REFERENCES `technology_stack_category` (`technology_stack_category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `trust_grade` (
   `trust_grade_id` bigint NOT NULL,
   `create_date` datetime(6) DEFAULT NULL,
   `update_date` datetime(6) DEFAULT NULL,
   `name` varchar(255) DEFAULT NULL,
   `minimum_score` int NOT NULL,
   `maximum_score` int NOT NULL,
   PRIMARY KEY (`trust_grade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `project_member_auth` (
   `project_member_auth_id` bigint NOT NULL,
   `milestone_change_yn` bit(1) DEFAULT NULL,
   `project_member_auth_name` varchar(255) DEFAULT NULL,
   `work_change_yn` bit(1) DEFAULT NULL,
   PRIMARY KEY (`project_member_auth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
SET FOREIGN_KEY_CHECKS=1;

CREATE TABLE `trust_score_type` (
    `trust_score_type_id` BIGINT NOT NULL,
    `up_trust_score_type_id` BIGINT NULL,
    `trust_score_type_name` VARCHAR(40) NULL,
    `trust_grade_name` VARCHAR(20) NULL,
    `score` INT NULL,
    `gubun_code` VARCHAR(1) NULL,
    `delete_status` VARCHAR(1) NULL,
    `create_date` DATE NOT NULL,
    `update_date` DATE NOT NULL,
     PRIMARY KEY (`trust_score_type_id`),
     FOREIGN KEY (`up_trust_score_type_id`) REFERENCES `trust_score_type` (`trust_score_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
