-- Create Game table
CREATE TABLE "Cheuk Man_To_game" (
                                     game_id NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, -- Auto-increment primary key
                                     game_title VARCHAR2(100) NOT NULL
);

-- Create Player table
CREATE TABLE "Cheuk Man_To_player" (
                                       player_id NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, -- Auto-increment primary key
                                       first_name VARCHAR2(100) NOT NULL,
                                       last_name VARCHAR2(100) NOT NULL,
                                       address VARCHAR2(500),
                                       postal_code VARCHAR2(20),
                                       province VARCHAR2(50),
                                       phone_number VARCHAR2(15)
);

-- Create PlayerAndGame table
CREATE TABLE "Cheuk Man_To_player_and_game" (
                                                player_game_id NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, -- Auto-increment primary key
                                                game_id NUMBER NOT NULL,
                                                player_id NUMBER NOT NULL,
                                                playing_date DATE DEFAULT SYSDATE, -- Defaults to the current date
                                                score NUMBER(10, 2) DEFAULT 0, -- Defaults to 0
                                                CONSTRAINT fk_game FOREIGN KEY (game_id) REFERENCES "Cheuk Man_To_game"(game_id) ON DELETE CASCADE,
                                                CONSTRAINT fk_player FOREIGN KEY (player_id) REFERENCES "Cheuk Man_To_player"(player_id) ON DELETE CASCADE
);